package com.heepay.rpc.billing.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.route.FetchRouteMessage;
import com.heepay.billingutils.IdClear;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.FeeDeductType;
import com.heepay.enums.billing.SettleDifferIsProfit;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.billing.service.IMerchantClearing;
import com.heepay.rpc.billing.service.merchant.MerchantHandle;
import com.heepay.rpc.client.ManagerServerClient;
import com.heepay.vo.MerchantProductVO;

/**
 * 
 * 
 * 描 述：用户侧出款类业务实现类，包括批付，退款，提现
 *
 * 创 建 者：chenyanming 
 * 创建时间： 2016年9月9日下午2:33:43 
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
public class MerchantExpendImpl extends MerchantHandle implements IMerchantClearing {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	ClearMerchantRecordMapper clearMerchantRecordMapperDao;
	
	@Autowired
	ManagerServerClient managerServerClient;

	@Override
	public void billingHandle(ClearMerchantRecordModel model) {
		
		logger.info("此处代码已废弃！");		
	}
    /**
     * 描    述：出款类商户侧清算数据处理
     * @author xuangang
     * @date 2017-05-22
     */
	@Override
	public void billingHandle(Map<String, String> map) {

		logger.info("出款类（退款、提现、批付）商户侧清算入库开始！交易订单号：{}", map.get("TransNo"));

		// 判断是否重复发送的数据
		String transNo = map.get("transNo");

		if (transNo == null || "".equals(transNo)) {
			logger.info("商户交易订单号为空，transNo：{}", transNo);
			return;
		}

		int flag = clearMerchantRecordMapperDao.getBooleanExist(transNo);
		if (flag == 0) {
			try {
				ClearMerchantRecord cRecord = getClearMerchantRecord(map);				
				if(cRecord == null){
					 map.put("status", "02");   //商户侧处理失败
			         saveClearExceptionData(map);
					 return;
				}				
				
				//通过thrift 获取商户侧路由信息
				String merchantVo = managerServerClient.getMerchantProductVO(String.valueOf(cRecord.getMerchantId()), cRecord.getProductCode());
				
				//获取路由商户信息
				MerchantProductVO merVo = FetchRouteMessage.getMerchantMessage(merchantVo);
				
				String feeSettleCyc = "";  //手续费结算周期
				if(merVo == null){
					logger.info("获取商户侧路由信息异常，商户id{}, 产品编码:{}",cRecord.getMerchantId(), cRecord.getProductCode());					
				}else{
					feeSettleCyc = merVo.getFeeSettleCyc();  //手续费结算周期（路由系统查询获取）
					//modified by xuangang 2017-03-09  从路由系统获取产品名称
					cRecord.setProductName(merVo.getProductName());  //产品名称
				}				
				
				String settleCyc = cRecord.getSettleCyc();        //订单结算周期(交易系统提供)				
				try {
					if(StringUtil.notBlank(settleCyc)){
						cRecord.setSettleTimePlan(DateUtils.getInternalDateByDay(DateUtils.getStr2Date(map.get("successTime")), new Integer(settleCyc))); // 订单应结算日期（订单结算周期）)
					}				
					
				} catch (Exception e) {
					logger.error("获取商户侧清算订单应结算日期失败！{}", settleCyc, e);	
				}
				
				try{
					if(StringUtil.notBlank(feeSettleCyc)) {
						int temp = new Integer(feeSettleCyc);
						cRecord.setFeeTime(DateUtils.getInternalDateByDay(DateUtils.getStr2Date(map.get("payTime")), temp)); //手续费结算日期 
					}
				}catch(Exception e){
					logger.error("获取商户侧清算手续费结算日期 失败！{}", feeSettleCyc, e);	
				}
				
				cRecord.setFeeSettleCyc(feeSettleCyc);                    //手续费结算周期(路由系统可以提供)				
				cRecord.setSettleNo(IdClear.getRandomString(1));          ///清算流水
				cRecord.setCheckStatus(Constants.Clear.CHECK_STATUS_N);	  //未对账				
				cRecord.setSettleStatus(Constants.Clear.SETTLE_STATUS_Y); //已结算
				
				clearMerchantRecordMapperDao.insertSelective(cRecord);    //清算记录入库				

				logger.info("商户侧清算入库成功！交易订单号：{}", transNo);
				
			} catch (Exception e) {
				logger.error("商户侧清结算数据处理异常：{}", map, e);
				map.put("status", "02");      //商户侧处理异常
				saveClearExceptionData(map);     
			}
		}else{
			logger.info("商户侧清算数据已存在！交易订单号：{}", transNo);
			map.put("status", "04");      //清算数据已存在
			saveClearExceptionData(map);
		}
	}	
}
