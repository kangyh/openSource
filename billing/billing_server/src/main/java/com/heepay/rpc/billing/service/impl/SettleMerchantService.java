package com.heepay.rpc.billing.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.ClearProfitRecordMapper;
import com.heepay.billing.dao.SettleMerchantRecordMapper;
import com.heepay.billing.dao.SettleProfitBathMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.ClearProfitRecord;
import com.heepay.billing.entity.SettleDicItem;
import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.billing.entity.SettleProfitBath;
import com.heepay.billingutils.IdBatch;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.CurrencyType;
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.enums.billing.SettleDifferIsProfit;
import com.heepay.rpc.billing.producer.SettleMerchatProducerSender;
import com.heepay.rpc.billing.service.ISettleMerchantService;
import com.heepay.vo.ClearMerchantDetailMessage;

/***
 * 
* 
* 描    述：商户侧清结算汇总(T+1,分润)
*
* 创 建 者： xuangang
* 创建时间：  2016年9月28日下午10:03:34
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

@Service
public class SettleMerchantService implements ISettleMerchantService{

private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	SettleMerchantRecordMapper settleMerchantRecordDaoImpl;
	
	@Autowired
	ClearMerchantRecordMapper  clearMerchantRecordDaoImpl;
	
	@Autowired
	SettleMerchatProducerSender settleMerchatProducerSender;
		
	@Autowired
	ClearProfitRecordMapper clearProfitRecordMapperDaoImpl;
	
	@Autowired
	SettleProfitBathMapper settleProfitBathMapperDaoImpl;
		
	/**
	 * @author xuangang
	 * @date 2016-11-10
	 * @描述 清结算数据存储、发送账务系统，若失败，
	 * 则只回滚该条结算数据，不影响下一条结算数据
	 * 
	 * @param map 汇总完毕的一条结算数据
	 * @param settleDicItem 会计日期
	 */	
	@Transactional()
	@Override
	public void saveAndSendSettleMsg(Map map, SettleDicItem settleDicItem, String settleCyc){
		
		String settleBathNo = null;
		try{			
			settleBathNo  = IdBatch.getRandomString(1);             //结算批次
			logger.info("------存储、发送商户侧T+1结算数据开始,批次号：{}", settleBathNo);
			
			SettleMerchantRecord record = new SettleMerchantRecord();
			record.setMerchantId((Integer)(map.get("merchantId")));  			  //用户编码
			record.setMerchantType(StringUtil.getString(map.get("merchantType"))); //用户类型				
			record.setTransType(StringUtil.getString(map.get("transType")));      //交易类型
			record.setCurrency(CurrencyType.RMB.getValue());    				  //币种人民币
			record.setPayNum((Integer)map.get("num"));     						  //总笔数
			record.setTotalAmount((BigDecimal)map.get("totalRequestAmount"));	  //总金额
			record.setSettleAmount((BigDecimal)map.get("totalPlanAmout"));        //应结算总金额 
			record.setTotalFee((BigDecimal)map.get("totalFee"));				  //总手续费totalFee
			record.setSettleTime(DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));  //清结算日期（入库日期）
			record.setSettleCyc(settleCyc);                              	      //交易结算周期 
			
			record.setSettleBath(settleBathNo);   								  //结算批次
			record.setCheckStatus(Constants.Clear.CHECK_STATUS_Y);                //对账状态:已对			
			record.setSettleStatus(Constants.Clear.SETTLE_STATUS_D);      		  //结算中
			record.setCheckTime(DateUtils.getStrDate(DateUtils.getPreDate(new Date()),"yyyy-MM-dd"));   //会计日期, 前一天
			
			record.setMerchantName(StringUtil.getString(map.get("merchantName")));  //商户名称 added by xuangang 20170310
			
			settleMerchantRecordDaoImpl.insert(record);                           //保存T+1结算数据
			
			Map<String, Object> paraMap = new HashMap<String, Object>();
			
			paraMap.put("settleBath", settleBathNo);
			paraMap.put("merchantId", (Integer)(map.get("merchantId"))); 		   			   //用户编码
			paraMap.put("update_settleStatus", Constants.Clear.SETTLE_STATUS_D);               //结算状态：结算中
			paraMap.put("transType", StringUtil.getString(map.get("transType")));              //交易类型
			paraMap.put("checkStatus", Constants.Clear.CHECK_STATUS_Y);		                   //对账状态：已对账
			paraMap.put("settleStatus", Constants.Clear.SETTLE_STATUS_N);		               //结算状态：未结算
			paraMap.put("checkFlg", BillingYCheckStatus.BCFQSTS.getValue());     			   //平账  
			paraMap.put("date", DateUtils.getStrDate(DateUtils.getDateStr(new Date(), "yyyy-MM-dd "+settleDicItem.getValue()), "yyyy-MM-dd hh:mm:ss"));     //会计日期区间
			paraMap.put("settleCyc", settleCyc);         								//交易结算周期 为 
			paraMap.put("checkBatch", map.get("checkBath"));      						//对账批次		
			paraMap.put("isProfit", SettleDifferIsProfit.ACCOUNTFLGN.getValue());      //分润状态:不分润
			
			clearMerchantRecordDaoImpl.updateAfterSettle(paraMap);  //更新清结算表		
			
			logger.info("向账务系统发送商户侧结算数据开始， 结算批次号：{}",settleBathNo);
			//查询商户侧结算表
			SettleMerchantRecord settleDetail = settleMerchantRecordDaoImpl.querySettleMerchantByBatchNo(settleBathNo);
			//根据批次号查询清算明细
		    List<ClearMerchantDetailMessage> clearMessageList = new ArrayList<ClearMerchantDetailMessage>();
		    //汇总后的结算批次+清算明细
			SettleMerchantMessage settleMessage = new SettleMerchantMessage();
			settleMessage.setSettleMerchantRecord(settleDetail);         //结算批次
			settleMessage.setClearMerchantMessage(clearMessageList);     //清算明细 （清结算系统不再给账务系统推送结算明细modified by xuangang 2017-07-07）
			
			//将结算数据发送到队列
			settleMerchatProducerSender.sendDataToQueue(settleMessage);
			
			logger.info("------存储、发送商户侧结算数据结束，结算周期：{},批次号：{}", settleCyc, settleBathNo);
		}catch(Exception e){
			logger.error("商户侧汇总清算记录,发送结算数据异常结算周期：{}，结算批次：{}",settleCyc, settleBathNo, e);
			throw new RuntimeException();
		}		
	}
	
	/**
	 * @author xuangang
	 * @date 2016-11-10
	 * @描述 向账务系统发送分润明细结算数据
	 * @param transNo 清算订单号
	 */
	@Transactional()
	@Override
	public void sendProfitMessage(String transNo){
		
		if(StringUtil.isBlank(transNo))
			return;
		try{
			logger.info("商户侧分润汇总,向账务系统发送数据开始：{}", transNo);
			String profitBath  = IdBatch.getRandomString(1);    //生成结算批次		
			
			Map<String, Object> paraMap = new HashMap<String, Object>();
						
			paraMap.clear();		
			paraMap.put("profitBath", profitBath);       					//分润结算批次
			paraMap.put("profitTime", new Date());        				    //处理结束时间：当前时间
			paraMap.put("transNo", transNo);                                //交易订单号
			paraMap.put("profitStatus", SettleDifferIsProfit.ACCOUNTFLGN.getValue());       //未分润
			paraMap.put("settleProfitStatus",SettleDifferIsProfit.ACCOUNTFLGY.getValue());  //已分润		
			
			//更新分润明细表为已分润				
			clearProfitRecordMapperDaoImpl.updateAfterSettle(paraMap);
			
			paraMap.clear();
			paraMap.put("settleBath", profitBath);       					//结算批次
			paraMap.put("finishTime", new Date());        				    //处理结束时间：当前时间
			paraMap.put("transNo", transNo);                                //交易订单号
			paraMap.put("profitStatus", SettleDifferIsProfit.ACCOUNTFLGY.getValue());  //分润状态：分润
			paraMap.put("settleStatus", Constants.Clear.SETTLE_STATUS_D);              //结算状态：结算中
			paraMap.put("oldSettleStatus", Constants.Clear.SETTLE_STATUS_N);           //结算状态：未结算
			
			//更新清算分润记录，结算状态、批次、处理时间
			clearMerchantRecordDaoImpl.updateProfitSettleStatusByTransNo(paraMap);
			
			//查询清算表
			ClearMerchantRecord clearMerchantRecord = clearMerchantRecordDaoImpl.queryProfitRecordByTransNo(paraMap);			

			paraMap.clear();
			paraMap.put("transNo", transNo);                     						//交易订单号
			paraMap.put("profitStatus",SettleDifferIsProfit.ACCOUNTFLGY.getValue());    //分润
			paraMap.put("profitBath", profitBath);                                      //分润结算批次

			//查询当前订单号、且需要分润的所有分润明细记录
			List<ClearProfitRecord> profitList = clearProfitRecordMapperDaoImpl.queryByTransNo(paraMap);
			
			SettleProfitMessage settleProfitMessage = new SettleProfitMessage();		
			settleProfitMessage.setClearMerchantRecord(clearMerchantRecord);
			settleProfitMessage.setClearProfitRecordList(profitList);			
	
			
			//将分润明细存入分润汇总表
			for(int i=0, length = profitList.size(); i<length; i++){
				
				ClearProfitRecord clearProfitRecord = profitList.get(i);            //分润明细数据
				
				SettleProfitBath settleProfitBath = new SettleProfitBath();
				
				settleProfitBath.setMerchantId(clearProfitRecord.getMerchantId());  //商户Id
				settleProfitBath.setTransType(clearProfitRecord.getTransType());    //交易类型
				settleProfitBath.setCurrency(CurrencyType.RMB.getValue());          //币种
				settleProfitBath.setProfitBath(profitBath);                         //分润结算批次
				settleProfitBath.setProfitDate(new Date());                         //分润日期
				settleProfitBath.setMerchantOrderNo(clearProfitRecord.getMerchantOrderNo());  //商户交易流水号

				settleProfitBath.setTransNo(clearProfitRecord.getTransNo());        //交易订单号
				settleProfitBath.setProfitAmount(clearProfitRecord.getProfitAmount());  //订单金额   
				settleProfitBath.setFee(clearProfitRecord.getProfitFee());              //分润手续费  
				settleProfitBath.setProfitStatus(Constants.Clear.SETTLE_STATUS_D);      //分润状态（N：未处理 D：处理中 Y：已处理）
				
				settleProfitBathMapperDaoImpl.insert(settleProfitBath);            //将分润明细数据存储到分润汇总表
			}			
			//发送分润数据到账务系统
			settleMerchatProducerSender.sendProfitDataToQueue(settleProfitMessage);
			
			logger.info("商户侧分润汇总,向账务系统发送数据成功：{}", transNo);
		}
		catch(Exception e){
			logger.error("商户侧分润汇总异常:{}", transNo, e);
			throw new RuntimeException();
		}		
	}	

	
    
	private class SettleMerchantMessage implements Serializable {

		private static final long serialVersionUID = 4946173065449810591L;
	    //结算记录
		private SettleMerchantRecord settleMerchantRecord;
		//清算明细
		private List<ClearMerchantDetailMessage> clearMerchantMessage;
		public SettleMerchantRecord getSettleMerchantRecord() {
			return settleMerchantRecord;
		}
		public void setSettleMerchantRecord(SettleMerchantRecord settleMerchantRecord) {
			this.settleMerchantRecord = settleMerchantRecord;
		}
		public List<ClearMerchantDetailMessage> getClearMerchantMessage() {
			return clearMerchantMessage;
		}
		public void setClearMerchantMessage(
				List<ClearMerchantDetailMessage> clearMerchantMessage) {
			this.clearMerchantMessage = clearMerchantMessage;
		}		
	}
    /**
     *    
    * 
    * 描    述：清结算分润明细数据结构   
    *
    * 创 建 者： xuangang
    * 创建时间：  2016年11月7日上午11:48:47
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
	private class SettleProfitMessage implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		private ClearMerchantRecord clearMerchantRecord;   
		
		private List<ClearProfitRecord> clearProfitRecordList;

		public ClearMerchantRecord getClearMerchantRecord() {
			return clearMerchantRecord;
		}

		public void setClearMerchantRecord(ClearMerchantRecord clearMerchantRecord) {
			this.clearMerchantRecord = clearMerchantRecord;
		}

		public List<ClearProfitRecord> getClearProfitRecordList() {
			return clearProfitRecordList;
		}

		public void setClearProfitRecordList(
				List<ClearProfitRecord> clearProfitRecordList) {
			this.clearProfitRecordList = clearProfitRecordList;
		}	
		
	}


}
