package com.heepay.rpc.billing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleChannelLogMapper;
import com.heepay.billing.dao.SettleChannelManagerMapper;
import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.dao.SettleWarnRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleChannelManager;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.CheckWay;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.rpc.billing.service.ICheckBillRecordService;
import com.heepay.rpc.billing.service.ISendMailService;
import com.heepay.rpc.client.BillingClearAPIClient;
import com.heepay.rpc.client.ManagerServerClient;
import com.heepay.rpc.client.PayChannelCacheServiceClient;
import com.heepay.rpc.client.WarningClient;
import com.heepay.rpc.service.RpcService;

/**
 * *
 * 
 * 
 * 描 述：查找差异类型为其他差异的数据，插入差异记录表，告警记录表，发送邮件提醒
 * 
 * 创 建 者： wangjie 创建时间： 2016年9月27日下午6:58:38 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */

@Component
@RpcService(name = "SendEmailServiceImplWD", processor = com.heepay.rpc.billing.service.SendEmailService.Processor.class)
public class SendEmailServiceImpl implements com.heepay.rpc.billing.service.SendEmailService.Iface {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	ClearChannelRecordMapper clearChannelRecordDaoImpl;

	@Autowired
	SettleDifferRecordMapper settleDifferRecordDaoImpl;

	@Autowired
	SettleChannelLogMapper settleChannelLogDaoImpl;

	@Autowired
	SettleChannelManagerMapper settleChannelManagerDaoImpl;

	@Autowired
	ClearMerchantRecordMapper clearMerchantRecordDaoImpl;

	@Autowired
	SettleWarnRecordMapper settleWarnRecordDaoImpl;

	@Autowired
	BillingClearAPIClient billingClearAPIClient;

	@Autowired
	private WarningClient warningClient;

	@Autowired
	ManagerServerClient managerServerClient;

	@Autowired
	ISendMailService sendEmailRecordServiceImpl;

	@Autowired
	PayChannelCacheServiceClient payChannelCacheServiceClient;
	
	@Autowired
	ISendMailService settleDifferRecordImpl;

	// 查找差异类型为其他差异的数据，插入差异记录表，告警记录表，发送邮件提醒
	@Override
	public void query() throws TException {

		/** 1、查询已经配置的有效对账通道类型 **/
		List<SettleChannelManager> channelManagers = settleChannelManagerDaoImpl
				.querySettleChannelManagerDetail(BillingBillStatus.BBSTATUSY.getValue());
		logger.info("检索有效的通道个数{}", channelManagers.size());

		/** 2、根据不同的通过类型来检索出最近一段时间没有对账过的清算记录（通道侧和商户侧） **/

		for (int index = 0; index < channelManagers.size(); index++) {
			if (CheckWay.HANDLE.getValue().equals(channelManagers.get(index).getSettleWay())) { // 手动对账
				
				checkHandleChannel(channelManagers.get(index));
				
			} else {				
					handleChannel(channelManagers.get(index));
					logger.info("其他差异重构代码执行完毕！");							
			}
		}
	}	

	// 手动对账其他差异处理流程
	public void checkHandleChannel(SettleChannelManager settleChannelManager) {

		// 商户侧有数据时，接收告警记录内容
		StringBuilder totalClearMerchantRecordBody = new StringBuilder();

		// 商户侧没有数据时，接收告警记录内容
		StringBuilder totalClearChannelRecordBody = new StringBuilder();

		JsonMapperUtil jsonUtil = new JsonMapperUtil();

		// 存放告警记录transNo的list集合
		List<String> list = new ArrayList<String>();

		// 查出最近三天未对账的通道侧数据且交易日期小于已对账最大日期
		List<ClearChannelRecord> clearChannelRecordList = clearChannelRecordDaoImpl.queryHandleBill(
				ClearingCheckStatus.CHECKSTATUSN.getValue(), settleChannelManager.getChannelCode(),
				settleChannelManager.getChannelType(),"0");

		// 查出最近三天未对账的商户侧数据且交易日期小于已对账最大日期
		List<ClearMerchantRecord> clearMerchantRecordList = clearMerchantRecordDaoImpl.queryHandleBill(
				ClearingCheckStatus.CHECKSTATUSN.getValue(), settleChannelManager.getChannelCode(),
				settleChannelManager.getChannelType(),"0");

		for (ClearChannelRecord clearChannelRecord1 : clearChannelRecordList) {
			list.add(clearChannelRecord1.getTransNo());
			//更新对账次数
			clearChannelRecordDaoImpl.updateNumByTransNO(clearChannelRecord1.getTransNo());
			String clearChannelRecordBody = "通道类型为" + clearChannelRecord1.getChannelType() + ",通道名称为"
					+ clearChannelRecord1.getChannelName() + ",交易订单号为" + clearChannelRecord1.getTransNo() + "的数据发生差异!";
			totalClearChannelRecordBody = totalClearChannelRecordBody.append(clearChannelRecordBody).append("<br>");
			
		}

		// 便历商户侧三天未对账的数据
		for (ClearMerchantRecord clearMerchantRecord : clearMerchantRecordList) {

			String transNo = clearMerchantRecord.getTransNo();
			clearMerchantRecordDaoImpl.updateNumByTransNO(transNo);
			if (list.contains(transNo)) {
				logger.info("通道侧已有该数据已入差异表,交易单号为{}", transNo);
			} else {
				String clearMerchantRecordBody = "用户编码为" + clearMerchantRecord.getMerchantId() + ",业务类型为"
						+ clearMerchantRecord.getProductCode() + ",交易订单号为" + clearMerchantRecord.getTransNo()
						+ "的数据发生差异!";
				totalClearMerchantRecordBody = totalClearMerchantRecordBody.append(clearMerchantRecordBody)
						.append("<br>");
			}

		}
		if (totalClearChannelRecordBody.length() > 0) {
			try { // 发送邮件商户侧没有数据时
				Map<String, Object> infoMap = new HashMap<String, Object>();
				String json = warningClient.selectGroupId("ngp");
				Map<String, Object> mapMessage = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if (mapMessage != null && mapMessage.containsKey("groupId")) {
					infoMap.put("groupId", Integer.parseInt(mapMessage.get("groupId").toString()));
					infoMap.put("type", "email");
					infoMap.put("msgHead", "其他差异数据");
					infoMap.put("msgBody", totalClearChannelRecordBody.toString());
					String result = warningClient.sendWaringMsg(jsonUtil.toJson(infoMap));
					logger.info("手动对账其他差异数据发送邮件返回{}", result);
				}
				totalClearChannelRecordBody.setLength(0);
			} catch (Exception e) {
				logger.error("发送邮件出现异常{}", e);
			}
		}

		if (totalClearMerchantRecordBody.length() > 0) {
			try { // 发送邮件商户侧有数据时
				Map<String, Object> infoMap = new HashMap<String, Object>();
				String json = warningClient.selectGroupId("ngp");
				Map<String, Object> mapMessage = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if (mapMessage != null && mapMessage.containsKey("groupId")) {
					infoMap.put("groupId", Integer.parseInt(mapMessage.get("groupId").toString()));
					infoMap.put("type", "email");
					infoMap.put("msgHead", "其他差异数据");
					infoMap.put("msgBody", totalClearMerchantRecordBody.toString());
					String result = warningClient.sendWaringMsg(jsonUtil.toJson(infoMap));
					logger.info("手动对账其他差异数据发送邮件返回{}", result);
				}
				// 发送成功，更新告警记录状态
				totalClearMerchantRecordBody.setLength(0);
			} catch (Exception e) {
				logger.error("发送邮件出现异常{}", e);
			}
		}

	}
	
	/**
	 * 
	 * @方法说明：通道字段对账，差异处理
	 * @author xuangang
	 * @param settleChannelManager
	 * @时间：2017年6月26日上午11:02:53
	 */
	private void handleChannel(SettleChannelManager cManager){
		// 查出最近三天未对账的通道侧数据且交易日期小于已对账最大日期
		List<ClearChannelRecord> cList = clearChannelRecordDaoImpl.queryPayTime(ClearingCheckStatus.CHECKSTATUSN.getValue(), cManager.getChannelCode(),
				cManager.getChannelType());
		
		// 查出最近三天未对账的商户侧数据且交易日期小于已对账最大日期
		List<ClearMerchantRecord> mList = clearMerchantRecordDaoImpl.queryPayTime(ClearingCheckStatus.CHECKSTATUSN.getValue(), cManager.getChannelCode(),
				cManager.getChannelType(),"0");
		
		// 商户侧有数据时，接收告警记录内容
		StringBuffer mbody = new StringBuffer();
		// 通道侧有，商户侧无数据时，接收告警记录内容
		StringBuffer cbody = new StringBuffer();
		
		Map<String, ClearMerchantRecord> mMap = new HashMap<String, ClearMerchantRecord>();

		for (ClearMerchantRecord mRecord : mList) {			
			mMap.put(mRecord.getTransNo(), mRecord);
		}
		
		for(ClearChannelRecord cRecord : cList){
			try{
				String transNo = cRecord.getTransNo();			
				ClearMerchantRecord mRecord = clearMerchantRecordDaoImpl.selectByTranNo(transNo);
				
				if(mRecord == null){  //通道侧存在，商户侧不存在
					settleDifferRecordImpl.settleDifferRecordChannel(transNo, cRecord);
					String clearRecordBody = "通道类型为" + cRecord.getChannelType() + ",通道名称为" + cRecord.getChannelName() 
							+ ",交易订单号为" + transNo	+ "的数据发生差异!";
					cbody = cbody.append(clearRecordBody).append("<br>");
				}else{
					settleDifferRecordImpl.settleDifferRecord(cRecord, mRecord);  //商户侧通道侧都存在
					String clearBody = "用户编码为" + mRecord.getMerchantId() + ",业务类型为"
							+ mRecord.getProductCode() + ",交易订单号为" + mRecord.getTransNo() + "的数据发生差异!";
					mbody = mbody.append(clearBody).append("<br>");
				}	
				mMap.remove(transNo);   //删除已经处理的通道记录				
				
			}catch(Exception e){
				logger.error("清结算处理其他差异发生异常，交易单号：{}", cRecord.getTransNo());
			}			
		}
				
		//遍历所有商户侧存在、通道侧不存在的未对账的清算记录
		for (ClearMerchantRecord mRecord: mMap.values()){
			try{
				String transNo = mRecord.getTransNo();
				clearMerchantRecordDaoImpl.updateNumByTransNO(transNo);  //更新对账次数
				
				String clearBody = "用户编码为" + mRecord.getMerchantId() + ",业务类型为"
						+ mRecord.getProductCode() + ",交易订单号为" + mRecord.getTransNo() + "的数据发生差异!";
				mbody = mbody.append(clearBody).append("<br>");
			}catch(Exception e){
				logger.error("");
			}
		}	
		if(cbody.length() > 0){
			sendMsg("ngp", "email", "其他差异数据", cbody.toString());
		}
		if(mbody.length() > 0){
			sendMsg("ngp", "email", "其他差异数据", mbody.toString());
		}
	}
	
	/**
	 * 
	 * @方法说明：发送信息
	 * @author xuangang
	 * @param groupId
	 * @param type
	 * @param header
	 * @param body
	 * @return
	 * @时间：2017年6月27日上午10:30:12
	 */
	@SuppressWarnings("unchecked")
	private String  sendMsg(String groupId, String type, String header, String body){
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		try { // 发送邮件商户侧有数据时
			Map<String, Object> infoMap = new HashMap<String, Object>();
			String json = warningClient.selectGroupId(groupId);
			Map<String, Object> mapMessage = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
			if (mapMessage != null && mapMessage.containsKey("groupId")) {
				infoMap.put("groupId", Integer.parseInt(mapMessage.get("groupId").toString()));
				infoMap.put("type", type);
				infoMap.put("msgHead", header);
				infoMap.put("msgBody", body);
				String result = warningClient.sendWaringMsg(jsonUtil.toJson(infoMap));
				logger.info("手动对账其他差异数据发送邮件返回{}", result);
				return result;
			}
			// 发送成功，更新告警记录状态
			
		} catch (Exception e) {
			logger.error("发送邮件出现异常{}", e);
		}
		return "";
		
	}
	
}
