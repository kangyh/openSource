package com.heepay.rpc.billing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleMerchantRecordMapper;
import com.heepay.billing.dao.SettleProfitBathMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.common.util.Constants;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.TransTypeUtil;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.billing.service.IClearChannelRecordService;
import com.heepay.rpc.billing.service.IMerchantClearing;
import com.heepay.rpc.service.RpcService;


/***
 * 
* 
* 描    述：server端接收notify数据（入款、出款、消费）
*
* 创 建 者： xuangang
* 创建时间：  2016年9月9日上午9:34:51
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
@RpcService(name="ClearMerchantRecordServiceImpl",processor=com.heepay.rpc.billing.service.ClearMerchantRecordService.Processor.class)
public class ClearMerchantRecordServiceImpl implements com.heepay.rpc.billing.service.ClearMerchantRecordService.Iface {
	
	private static final Logger logger = LogManager.getLogger();

    @Autowired
	IMerchantClearing merchantExpendImpl;
    
    @Autowired
   	IMerchantClearing rechargeMerchantImpl;
    
	@Autowired
	ClearMerchantRecordMapper ClearMerchantRecordDaoImpl;
	
	@Autowired
	SettleMerchantRecordMapper settleMerchantRecordDaoImpl;
	
	@Autowired
	SettleProfitBathMapper settleProfitBathMapperDaoImpl;
	
	
	@Autowired
	IClearChannelRecordService channelExpendImpl;
	
	/**
	 * @author xuangang
	 * @date 2016-09-26
	 * @描述 处理交易系统数据（入款、出款、消费）
	 * 
	 */
	@Override
    public int saveClearMerchantRecord(ClearMerchantRecordModel model) throws TException {
		
		logger.info("此段代码废弃！");
		
		return 0;
	}

	/**
	 * @author xuangang
	 * @date 2016-11-12
	 * @描述 账务系统返回结算批次处理结果，更新结算结果
	 */
	@Override
	public int updateClearMerchantRecord(ClearMerchantRecordModel clearMerchantRecordModel) throws TException {
		
		if(clearMerchantRecordModel == null){
			return 0;
		}
		logger.info("更新商户侧结算批次！{}", clearMerchantRecordModel.getSettleBath());
		ClearMerchantRecord clearRecord = new ClearMerchantRecord();
		try{
			String profitFlg = clearMerchantRecordModel.getTransType();          //分润标识
			
			clearRecord.setSettleBath(clearMerchantRecordModel.getSettleBath());  //对账批次号
			clearRecord.setSettleStatus(Constants.Clear.SETTLE_STATUS_Y);         //已结算
			clearRecord.setFinishTime(new Date());                                //处理结束时间 
			ClearMerchantRecordDaoImpl.updateClearMerchantByBathcNo(clearRecord);
			
			if(profitFlg != null && "profit".equals(profitFlg)){
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("profitBath", clearMerchantRecordModel.getSettleBath());       //分润结算批次
				map.put("profitStatus", Constants.Clear.SETTLE_STATUS_Y);              //已结算
				map.put("dealTime", new Date());                                       //处理时间
				settleProfitBathMapperDaoImpl.updateSettleProfitBatchBySettleNo(map);
			}
			else{
				SettleMerchantRecord settleRecord= new SettleMerchantRecord();
				settleRecord.setSettleStatus(Constants.Clear.SETTLE_STATUS_Y);  		//已结算
				settleRecord.setSettleBath(clearMerchantRecordModel.getSettleBath());   //对账批次号
				settleMerchantRecordDaoImpl.updateSettleMerchantByBathcNo(settleRecord);
			}
			return 1;
		}catch(Exception e){
			logger.info("更新商户侧结算批次失败{}", clearMerchantRecordModel.getSettleBath(), e);
		}

		return 0;
	}

	@Override
	public void deleClearMerchantRecord(ClearMerchantRecordModel clearMerchantRecordModel) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ClearMerchantRecordModel> queryByConditions(ClearMerchantRecordModel clearMerchantRecordModel)
			throws TException {
		// TODO Auto-generated method stub
		System.out.println("------");
		return null;
	}

	@Override
	public String getClearMerchantRecordMessage(ClearMerchantRecordModel clearMerchantRecordModel)throws TException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getClearMerchantRecord() throws TException {
		// TODO Auto-generated method stub
		
	}

    /**
     * @author xuangang
     * @date  2017-05-19
     * @描述 通道、商户合并为同一消息队列处理清算数据
     */
	@SuppressWarnings("unchecked")	
	public void settleDataSave(String msg) throws TException {
		
		try {
			logger.info("清结算数据处理开始:{}", msg);
			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(msg, Map.class);

			//通道侧清算数据处理
			try {
				channelExpendImpl.billingHandle(map);
			} catch (Exception e) {
				logger.error("通道侧处理清算数据异常:{}", map, e);
			}
			//商户侧清算数据处理
			try {
				String transType = map.get("transType");        // 交易类型
				if (transType == null || "".equals(transType)) {
					logger.info("server 接收交易系统数据，交易类型为空:{}", map);
					return;
				}				
					// 充值、消费(将充值、消费合并处理、存管充值)
				if (TransTypeUtil.isIncome(transType)) {
					rechargeMerchantImpl.billingHandle(map);
				}
				// 出款类，包括退款，批付，提现、实名认证（新增实名认证交易类型）、存管提现
				else if(TransTypeUtil.isOutcome(transType)) {
					merchantExpendImpl.billingHandle(map);
				}
				
			} catch (Exception e) {
				logger.error("商户侧处理清算数据异常！", map, e);
			}

		} catch (Exception e) {
			logger.error("清结算数据处理异常：{}", msg, e);
		}		
	}

	/**
	 * @author xuangang
	 * @date  2017-08-10
	 * @描述 交易清算异常数据存储
	 */
	@SuppressWarnings("unused")
	@Override
	public void saveClearExceptionData(String msg) throws TException {
		
		try{		
			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(msg, Map.class);
			
			map.put("status", "01");   //清算数据存在空值
			rechargeMerchantImpl.saveClearExceptionData(map);	
			
		}catch(Exception e){
			logger.error("清结算异常数据保存失败:{}", msg, e);
		}		
	}
}
