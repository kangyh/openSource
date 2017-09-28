package com.heepay.rpc.billing.service.impl;

import java.math.BigDecimal;
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
import com.heepay.enums.TransType;
import com.heepay.enums.billing.SettleDifferIsProfit;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.billing.service.IMerchantClearing;
import com.heepay.rpc.billing.service.merchant.MerchantHandle;
import com.heepay.rpc.client.ManagerServerClient;
import com.heepay.vo.MerchantProductVO;


/***
 * 
* 
* 描    述：用户侧充值、消费(周期、实时)清算
*
* 创 建 者： xuangang
* 创建时间：  2016年9月12日下午4:22:50
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
public class RechargeMerchantImpl extends MerchantHandle implements IMerchantClearing{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	ClearMerchantRecordMapper ClearMerchantRecordDaoImpl;
	
	@Autowired
	ManagerServerClient managerServerClient;
	
	@Override
	public void billingHandle(ClearMerchantRecordModel model) {
		// TODO Auto-generated method stub
		logger.info("此处代码已废弃！");		
	}

    /**
     * @author xuangang
     * @
     */
	@Override
	public void billingHandle(Map<String, String> map) {		
		
		//判断是否重复发送的数据
		String transNo = map.get("transNo");
		logger.info("用户侧充值、消费(周期、实时)清算开始, 交易订单号：{}", transNo);
		
		if(transNo == null || "".equals(transNo)){
			logger.info("商户交易订单号为空，transNo：{}", transNo);
			return;
		}	
		
		int flag = ClearMerchantRecordDaoImpl.getBooleanExist(transNo);
		if(flag==0){			
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
				
				String feeSettleCyc = "";  //手续费结算周期（路由系统查询获取）
				if(merVo == null){
					logger.info("获取商户路由信息失败, 商户编码:{}, 产品编码：{}", cRecord.getMerchantId(), cRecord.getProductCode());
				}else{
					feeSettleCyc = merVo.getFeeSettleCyc();  //手续费结算周期（路由系统查询获取）
					//modified by xuangang 2017-03-09  从路由系统获取产品名称
					cRecord.setProductName(merVo.getProductName());  //产品名称
				}				
				 
				String settleCyc = cRecord.getSettleCyc();        //订单结算周期（交易系统提供）
				
				//如果订单结算周期是T+X，则按照T+1处理
				if(Constants.Clear.TX.equals(settleCyc)){
					//订单应结算日期
					cRecord.setSettleTimePlan(DateUtils.getInternalDateByDay(DateUtils.getStr2Date(map.get("successTime")), 1));
				}else{
					try {
						if(StringUtil.notBlank(settleCyc)){
							cRecord.setSettleTimePlan(DateUtils.getInternalDateByDay(DateUtils.getStr2Date(map.get("successTime")), new Integer(settleCyc))); // 订单应结算日期（订单结算周期）)
						}						
					} catch (Exception e) {
						logger.error("获取商户侧清算订单应结算日期失败！{}, {}", settleCyc, map, e);	
					}
				}
				
				cRecord.setFeeSettleCyc(feeSettleCyc);                   //手续费结算周期(路由系统可以提供)				
				cRecord.setSettleNo(IdClear.getRandomString(1));         ///清算流水
				cRecord.setCheckStatus(Constants.Clear.CHECK_STATUS_N);	 //未对账				

				//如果交易类型为充值
				if(StringUtil.notBlank(map.get("transType")) && TransType.CHARGE.getValue().equals(map.get("transType"))){
					cRecord.setSettleStatus(Constants.Clear.SETTLE_STATUS_Y); //已结算
				} else{
					cRecord.setSettleStatus(Constants.Clear.SETTLE_STATUS_N); //未结算
				}
				
				ClearMerchantRecordDaoImpl.insertSelective(cRecord);           //清算记录入库
				
				logger.info("商户侧清算数据入库成功！交易订单号：{}", transNo);
				
			}catch(Exception e){
				logger.error("商户侧清算数据处理异常：{}", map, e);
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
