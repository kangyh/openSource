package com.heepay.rpc.billing.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.route.FetchRouteMessage;
import com.heepay.billingutils.IdClear;
import com.heepay.billingutils.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.TransTypeUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChannelPartner;
import com.heepay.enums.SettlePeriod;
import com.heepay.enums.TransType;
import com.heepay.rpc.billing.model.ClearChannelRecordModel;
import com.heepay.rpc.billing.service.IClearChannelRecordService;
import com.heepay.rpc.billing.service.merchant.MerchantHandle;
import com.heepay.rpc.client.PayChannelCacheServiceClient;
import com.heepay.vo.ChannelRouteMessage;

/**
 * 
 * 
 * 描 述：通道侧业务实现类，包括提现，退款，批付，充值，消费
 *
 * 创 建 者：chenyanming 
 * 创建时间： 2016年9月9日下午2:03:47 
 * 创建描述：
 * 
 * 修 改 者： 
 * 修改时间：
 * 修改描述：
 * 
 * 审 核 者： 
 * 审核时间：
 * 审核描述：
 *
 */
@Component
public class ChannelExpendImpl extends MerchantHandle implements IClearChannelRecordService {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	ClearChannelRecordMapper clearChannelRecordDao;
	@Autowired
	PayChannelCacheServiceClient payChannelCacheServiceClient;

	@Override
	public void billingHandle(ClearChannelRecordModel model) {
		
		logger.info("此处代码已废弃！");	
	}
    /**
     * 
     */
	@Override
	public void billingHandle(Map<String, String> map) {
		// 判断是否重复发送的数据
		String paymentId = map.get("paymentId");
		logger.info("获取消息队列中的paymentId:{}", paymentId);
		int flag = clearChannelRecordDao.getBooleanExist(paymentId);

		logger.info("根据paymentId查询的结果是---------->" + flag);

		if (flag == 0) {
			try {
				ClearChannelRecord clearChannelRecord = getClearChannelRecord(map);				
				if(clearChannelRecord == null){
					logger.info("获取通道侧清算数据异常：{}", map);
					map.put("status", "03");      //通道侧清算数据处理异常
					saveClearExceptionData(map);
					return;
				}				
				String channelByChannelCode;				
				//如果交易类型是实名认证
				if(TransType.REAL_NAME.getValue().equals(map.get("transType"))){
					 channelByChannelCode = payChannelCacheServiceClient.queryCertifyChannelByChannelCode(map.get("channelCode"));					 
				}
				else{
					channelByChannelCode = payChannelCacheServiceClient.getChannelByChannelCode(map.get("channelCode"));					
				}
				if(StringUtils.isBlank(channelByChannelCode)) {
					logger.error("通道侧从路由获取到的信息失败，channelCode为:{}", map.get("channelCode"));
					map.put("status", "03");      //通道侧清算数据处理异常
					saveClearExceptionData(map);
					return ;
				}
				logger.info("通道侧从路由获取到的信息为:{}" + channelByChannelCode);	
				//解析路由信息
				ChannelRouteMessage routeChannelMesage = FetchRouteMessage.getRouteChannelMesage(channelByChannelCode);
				if(routeChannelMesage == null) {
					logger.error("通道侧从路由获取到的信息解析失败:{}", channelByChannelCode);
					map.put("status", "03");      //通道侧清算数据处理异常
					saveClearExceptionData(map);
					return ;
				}
				if(StringUtils.isNotBlank(routeChannelMesage.getChannelPartnerCode())) {
					// 如果是直连银行，channelCode格式为  DIRCON-银行号，名称为银行名称；如果是第三方支付通道，channelCode为合作方编码，名称是合作方名称
					if(ChannelPartner.DIRECTCONNECTION.getValue().equals(routeChannelMesage.getChannelPartnerCode())) {
						clearChannelRecord.setChannelCode(ChannelPartner.DIRECTCONNECTION.getValue()+ "-" + routeChannelMesage.getBankNo());
						clearChannelRecord.setChannelName(routeChannelMesage.getBankName());
					}else {
						clearChannelRecord.setChannelCode(routeChannelMesage.getChannelPartnerCode());
						clearChannelRecord.setChannelName(routeChannelMesage.getChannelPartnerName());
					}
				}else {
					logger.error("通道侧从路由获取到的信息ChannelPartnerCode有误:{}", routeChannelMesage.getChannelPartnerCode());
					map.put("status", "03");      //通道侧清算数据处理异常
					saveClearExceptionData(map);
					return ;
				}				
				//modified by xuangang 2017-03-03 bigin  通道结算周期去路由系统获取
				clearChannelRecord.setSettleCyc(routeChannelMesage.getOrderSettlePeriod());// 通道结算周期				
				//modified by xuangang 2017-03-03 end
				clearChannelRecord.setChannelType(routeChannelMesage.getChannelTypeCode());	// 通道类型	
				clearChannelRecord.setChannelProvider(routeChannelMesage.getChannelPartnerName()); // 通道提供者
				clearChannelRecord.setBankName(routeChannelMesage.getBankName()); // 银行名称
				String settlePeriod = routeChannelMesage.getSettlePeriod();      //结算周期
				
				// 设置手续费结算周期
				if(SettlePeriod.T.getValue().equals(settlePeriod)) {
					clearChannelRecord.setCostSettleCyc(Constants.T1); // t1
					// 设置成本结算日期，支付时间+成本计算周期
					Date costTime = DateUtils.getInternalDateByDay(DateUtils.getStr2Date(map.get("payTime")), Integer.parseInt(clearChannelRecord.getCostSettleCyc()));
					String dateDir = DateUtils.getFormatDateDir(costTime, "yyyy-MM-dd");
					clearChannelRecord.setCostTime(DateUtils.getStr2Date(dateDir)); // 成本结算日期
				}else if(StringUtils.isBlank(settlePeriod)) {
					clearChannelRecord.setCostSettleCyc(Constants.T0); // t0
					// 设置成本结算日期，支付时间+成本计算周期
					Date costTime = DateUtils.getInternalDateByDay(DateUtils.getStr2Date(map.get("payTime")), Integer.parseInt(clearChannelRecord.getCostSettleCyc()));
					String dateDir = DateUtils.getFormatDateDir(costTime, "yyyy-MM-dd");
					clearChannelRecord.setCostTime(DateUtils.getStr2Date(dateDir)); // 成本结算日期
				}else if(SettlePeriod.MONTH.getValue().equals(settlePeriod)) {
					clearChannelRecord.setCostSettleCyc(SettlePeriod.MONTH.getValue()); // 月
					String month = DateUtils.getInternalTimeByMonth(DateUtils.getStrDate(map.get("payTime") ,"yyyy-MM-dd HH:mm:ss"), 1, "yyyy-MM-dd");
					clearChannelRecord.setCostTime(DateUtils.getStr2Date(month));// 成本结算日期
				}else if(SettlePeriod.SEASON.getValue().equals(settlePeriod)) {
					clearChannelRecord.setCostSettleCyc(SettlePeriod.SEASON.getValue()); // 季度
					String season = DateUtils.getInternalTimeByMonth(DateUtils.getStrDate(map.get("payTime"),"yyyy-MM-dd HH:mm:ss"), 3, "yyyy-MM-dd");
					clearChannelRecord.setCostTime(DateUtils.getStr2Date(season));// 成本结算日期
				}else if(SettlePeriod.YEAR.getValue().equals(settlePeriod)) {
					clearChannelRecord.setCostSettleCyc(SettlePeriod.YEAR.getValue()); // 年
					String year = DateUtils.getInternalTimeByMonth(DateUtils.getStrDate(map.get("payTime"),"yyyy-MM-dd HH:mm:ss"), 12, "yyyy-MM-dd");
					clearChannelRecord.setCostTime(DateUtils.getStr2Date(year));// 成本结算日期
				}	

				Date settleTimePlan;
				// 计算应结算日期，支付时间+订单结算周期，只有年月日  
				if ("X".equals(clearChannelRecord.getSettleCyc())) {
					settleTimePlan = DateUtils.getInternalDateByDay(DateUtils.getStr2Date(map.get("payTime")), 1);
					// modified by xuangang 2017-01-10 若是TX，那么结算周期按T+1来处理
					clearChannelRecord.setSettleCyc("1");// 结算周期
				} else {
					settleTimePlan = DateUtils.getInternalDateByDay(DateUtils.getStr2Date(map.get("payTime")), Integer.parseInt(clearChannelRecord.getSettleCyc()));
				}

				String formatDateDir = DateUtils.getFormatDateDir(settleTimePlan, "yyyy-MM-dd");
				clearChannelRecord.setSettleTimePlan(DateUtils.getStr2Date(formatDateDir)); // 订单应结算日期
				clearChannelRecord.setSettleTime(DateUtils.getDate()); 			// 清算日期
				clearChannelRecord.setSettleNo(IdClear.getRandomString(0)); 	// 清算流水号
				clearChannelRecord.setCheckStatus(Constants.CHECK_STATUS_N);    // 对账状态

				String transType = map.get("transType");

				// 如果是出款类，结算状态为已结算，消费和支付为为结算
				if (TransTypeUtil.isOutcome(transType)) {
					clearChannelRecord.setSettleStatus(Constants.SETTLE_STATUS_Y);
				} else {
					clearChannelRecord.setSettleStatus(Constants.SETTLE_STATUS_N);
				}
				clearChannelRecordDao.insertSelective(clearChannelRecord);
				logger.info("通道侧清算数据入库成功，支付单号：{}", paymentId);
			} catch (Exception e) {
				logger.error("通道侧清算入库失败！{}", map, e);
				map.put("status", "03");      //通道侧处理异常
				saveClearExceptionData(map);  
			}
		}else{
			logger.info("通道侧清算入库失败！------->原因是：存在重复数据不允许重复插入{}", map);
			map.put("status", "05");      //通道侧清算数据已存在
			saveClearExceptionData(map);
		}
	}
	/**
	 * 
	 * @方法说明：获取通道侧清算数据
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年5月22日下午5:45:04
	 */
	private ClearChannelRecord getClearChannelRecord(Map<String, String> map){
		try{
			ClearChannelRecord clearChannelRecord = new ClearChannelRecord();
			
			
			// 设置手续费扣除方式(坐扣、外扣)
			clearChannelRecord.setCostWay(map.get("costWay"));     //手续费扣除方式 
			
			clearChannelRecord.setPayTime(DateUtils.getStrDate(map.get("payTime"),"yyyy-MM-dd HH:mm:ss")); // 交易日期
			clearChannelRecord.setCurrency(map.get("currency")); 				// 币种(156:人民币)
			clearChannelRecord.setPaymentId(map.get("paymentId")); 				// 支付订单号
			clearChannelRecord.setPaymentIdOld(map.get("paymentIdOld")); 		// 原支付订单号
			clearChannelRecord.setTransNo(map.get("transNo")); 					// 交易订单号（订单号）
			clearChannelRecord.setTransNoOld(map.get("transNoOld")); 			// 原交易订单号（订单号）
			clearChannelRecord.setSuccessAmount(new BigDecimal(map.get("successAmount"))); // 交易金额
			clearChannelRecord.setTransType(map.get("transType")); 				// 交易类型
			clearChannelRecord.setBankCode(map.get("bankId")); 				    // 银行编码
//			clearChannelRecord.setBankName(map.get("bankName"));                // 银行名称
			clearChannelRecord.setBankSeq(map.get("bankSerialNo")); 			// 银行流水号					
			
			return clearChannelRecord;
		}catch(Exception e){
			logger.error("通道侧获取清算数据异常：{}", map, e);
		}
		
		return null;		
	}

}
