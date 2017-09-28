package com.heepay.rpc.billing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleDicItemMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleDicItem;
import com.heepay.billing.entity.SettleDicType;
import com.heepay.common.util.Constants;
import com.heepay.date.DateUtils;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.enums.billing.BillingCheckStatus;
import com.heepay.enums.billing.BillingSettleStatus;
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.SettleDicTypeEnum;
import com.heepay.enums.billing.SettleDifferIsProfit;
import com.heepay.enums.billing.SettleInterval;
import com.heepay.rpc.billing.model.SettleMerchantMessageModel;
import com.heepay.rpc.billing.model.SettleMerchantRecordModel;
import com.heepay.rpc.billing.service.ISettleMerchantService;
import com.heepay.rpc.service.RpcService;

/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年9月18日上午10:01:27
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
@RpcService(name="SettleMerchantRecordServiceImpl",processor=com.heepay.rpc.billing.service.SettleMerchantRecordService.Processor.class)
public class SettleMerchantRecordServiceImpl implements com.heepay.rpc.billing.service.SettleMerchantRecordService.Iface{
	
	private static final Logger logger = LogManager.getLogger();
	
//	@Autowired
//	SettleMerchantService settleMerchantService;	
	
	@Autowired
	ClearMerchantRecordMapper  clearMerchantRecordDaoImpl;
	
	@Autowired
	SettleDicItemMapper settleDicItemMapperImpl;
	
	@Autowired
	ISettleMerchantService settleMerchantService;
		
	@Override
	public void saveSettleMerchantRecord(
			SettleMerchantRecordModel settleMerchantRecordModel)
			throws TException {
		
	}
    /**
     * @author xuangang
     * 查询结算批次和清算明细
     */
	@Override
	public List<SettleMerchantMessageModel> querySettleMessage(
			SettleMerchantRecordModel settleMerchantRecordModel)
			throws TException {
		
		List<SettleMerchantMessageModel> list = new ArrayList<SettleMerchantMessageModel>();
		
		//所有已对账的T+1、平账、未结算的对账批次
		List<String> checkBatchList = queryCheckBatchNo();
		
		if(null == checkBatchList || checkBatchList.size() == 0){
			logger.info("商户侧T+1无需要汇总的对账批次！");
			return list;
		}
	
		try{
			profitSummarizing(checkBatchList, Constants.Clear.T1);    
		}
		catch(Exception e){
			logger.error("商户侧T+1分润汇总异常！{}",checkBatchList, e);
		}
		
		try{
			clearMerchantSummarizing(checkBatchList, Constants.Clear.T1);			
		}
		catch(Exception e){
			logger.error("商户侧T+1清算汇总信息异常,{}",checkBatchList, e);
		}	
		
		try{
			profitSummarizing(checkBatchList, Constants.Clear.TX);    
		}
		catch(Exception e){
			logger.error("商户侧T+X分润汇总异常！{}",checkBatchList, e);
		}
		
		try{
			clearMerchantSummarizing(checkBatchList, Constants.Clear.TX);			
		}
		catch(Exception e){
			logger.error("商户侧T+X清算汇总信息异常,{}",checkBatchList, e);
		}	

		return list;
	}	

	@SuppressWarnings("rawtypes")
	private void clearMerchantSummarizing(List checkBatchList, String settleCyc){		
		
		if(null == checkBatchList || checkBatchList.size() == 0){
			logger.info("------------商户侧T+1汇总，不存在需要汇总的对账批次！--------");	
			return ;
		}
		
		try{
			logger.info("商户侧T+1汇总清算记录开始, 对账批次{}", checkBatchList);			
			//查询会计区间			
			SettleDicType settleDicType = new SettleDicType();			

			settleDicType.setText(SettleInterval.NTERVALMV.getValue());			//结算区间类型:商户
			settleDicType.setCode(SettleDicTypeEnum.SETTLEAREA.getValue());     //编码			
			settleDicType.setStatus(BillingBecomeEffect.BBEY.getValue()); 		//状态：启用：enable、禁用：disable 
			//查询商户的会计区间
			SettleDicItem settleDicItem = settleDicItemMapperImpl.queryItemByTypeCode(settleDicType);
			
			if(settleDicItem == null || "".equals(settleDicItem.getValue())){
				logger.info("商户侧T+1汇总清算记录, 获取会计区间失败！");	
				return ;
			}			

			Map<String, Object> sumParaMap = new HashMap<String, Object>();
			
			sumParaMap.put("checkStatus", ClearingCheckStatus.CHECKSTATUSY.getValue());   //对账状态：已对账
			sumParaMap.put("settleStatus", BillingSettleStatus.SETTLESTATUSN.getValue()); //未结算
			sumParaMap.put("checkFlg", BillingYCheckStatus.BCFQSTS.getValue());           //平账
			sumParaMap.put("settleCyc", settleCyc);                              //订单周期 t+1			
			sumParaMap.put("successTime", DateUtils.getStrDate(DateUtils.getDateStr(new Date(), "yyyy-MM-dd "+settleDicItem.getValue()), "yyyy-MM-dd hh:mm:ss"));   //会计日期区间
						
			sumParaMap.put("checkBatchList", checkBatchList);                             //对账批次List
			sumParaMap.put("isProfit", SettleDifferIsProfit.ACCOUNTFLGN.getValue());      //分润状态:不分润
		
			//获取商户侧清算汇总信息
			List list = clearMerchantRecordDaoImpl.Summarizing(sumParaMap);
			
			if(null == list || list.size() == 0){
				return ;
			}
			logger.info("商户侧T+1汇总生成结算批次共"+list.size()+"条!");
			
			for(int i = 0, length = list.size(); i< length; i++){
				Map map = (Map)list.get(i);	
				try{
					settleMerchantService.saveAndSendSettleMsg(map, settleDicItem, settleCyc); //保存、发送商户侧T+1结算数据到账务系统
				}catch(Exception e){
					logger.info("商户侧汇总清算记录,保存、发送失败,结算周期{}，汇总信息{}",settleCyc, map, e);
				}				
			}
			logger.info("商户侧结算周期{}汇总清算记录结束！", settleCyc);
		} catch (Exception e) {
			 //TODO: handle exception
			logger.error("商户侧T+1汇总清算记录异常{}", checkBatchList, e);			
		}		
	}
	
	/**
	 * @author xuangang
	 * @date 2016-11-10
	 * @描述 清结算分润汇总，
	 * @param checkBatchList 对账批次list
	 * @param settleCyc      订单结算周期
	 */
	@SuppressWarnings("rawtypes")
	private void profitSummarizing(List checkBatchList, String settleCyc){
		
		if(null == checkBatchList || checkBatchList.size() == 0){
			return;
		}
		logger.info("------------分润数据汇总开始！对账批次{}", checkBatchList);
		//循环遍历对账批次，查询需要分润的清算记录
		for(int i=0, length=checkBatchList.size(); i<length; i++){
			Map<String, Object> paraMap = new HashMap<String, Object>();
			
			paraMap.put("checkBath", checkBatchList.get(i));   //对账批次
			paraMap.put("isProfit", SettleDifferIsProfit.ACCOUNTFLGY.getValue()); //分润
			paraMap.put("settleStatus", BillingSettleStatus.SETTLESTATUSN.getValue()); //未结算
			paraMap.put("checkFlg", BillingYCheckStatus.BCFQSTS.getValue());           //平账
			paraMap.put("settleCyc", settleCyc);                              		   //订单周期 			
			//查询当前批次，且需要分润的所有清结算记录
			List<ClearMerchantRecord> list = clearMerchantRecordDaoImpl.queryProfitRecordByCheckBatch(paraMap);
			
			for(int j=0, listLength = list.size(); j < listLength; j++){
				String transNo = "";
				try{
					ClearMerchantRecord clearMerchantRecord = list.get(j);	//清算记录			
					transNo = clearMerchantRecord.getTransNo();    		    //订单号
					
					settleMerchantService.sendProfitMessage(transNo); 		//将分润数据发送到账务系统
				}catch(Exception e){
					logger.error("发送分润数据到账务系统失败{}",transNo, e);
				}						
			}			
		}		
	}
	
	/**
	 * @描述 查询所有已对账结束的、且订单周期为t+1（或T+X）、平账、未结算的对账批次
	 * @author xuangang
	 * @return
	 */
	private List<String> queryCheckBatchNo(){		
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("checkStatus", ClearingCheckStatus.CHECKSTATUSY.getValue());   //已对账
		paraMap.put("settleCycT1", Constants.Clear.T1);                             //订单周期t+1
		paraMap.put("settleCycTx", Constants.Clear.TX);                             //订单周期tX
		paraMap.put("checkFlg", BillingYCheckStatus.BCFQSTS.getValue());          //平账
		paraMap.put("settleStatus", BillingSettleStatus.SETTLESTATUSN.getValue()); //未结算
		paraMap.put("channleLogCheckStatus", BillingCheckStatus.CHECKSTATUSCE.getValue());  //对账日志表，对账完成		
		
		return clearMerchantRecordDaoImpl.queryCheckBatchNo(paraMap);		
	}
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void updateSettleMerchantRecord(
			SettleMerchantRecordModel settleMerchantRecordModel)
			throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSettleMerchantRecord(
			SettleMerchantRecordModel settleMerchantRecordModel)
			throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SettleMerchantRecordModel> query(
			SettleMerchantRecordModel settleMerchantRecordModel)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}	

}
