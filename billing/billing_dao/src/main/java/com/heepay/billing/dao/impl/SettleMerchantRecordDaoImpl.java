package com.heepay.billing.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleMerchantRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.billing.entity.SettleMerchantRecordExample;
import com.heepay.billingutils.util.Constants;


/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年9月14日上午9:32:07
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
@Repository
public class SettleMerchantRecordDaoImpl extends BaseDaoImpl<SettleMerchantRecord, SettleMerchantRecordExample> implements SettleMerchantRecordMapper{

	// 默认构造方法设置命名空间
			public SettleMerchantRecordDaoImpl() {
				super.setNs("com.heepay.billing.dao.SettleMerchantRecordMapper");
			}
		//用户侧结算表修改状态
		@Override
		public void updateMerchantStatus(SettleMerchantRecord record) {
			
			record.setCheckStatus(Constants.SETTLE_STATUS_Y);
			record.setCheckTime(new Date());
			
			super.getSqlSession().selectList(this.getNs()+".updateMerchantStatus",record);
			
		}
		//修改用户结算表的状态
		@Override
		public void batchUpdateMerchantStatus(SettleMerchantRecord record) {
			
			super.getSqlSession().selectList(this.getNs()+".batchUpdateMerchantStatus",record);
			
		}
		/**
		 * 汇总商户端清算流水，生成结算批次
		 */
		@Override
		public List<Map> Summarizing(Date date) {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("checkStatus", Constants.SETTLE_STATUS_Y);
			map.put("settleStatus", Constants.SETTLE_STATUS_Y);
			map.put("date", date);
			return super.getSqlSession().selectList(this.getNs()+".summarizingClearMerchat",map);
			
		}
		
		public List<SettleMerchantRecord> querySettleMerchantDetail(Date checkDate, String productCode, String settleStatus, String checkStatus){
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("checkDate", checkDate);
			map.put("productCode", productCode);
			map.put("settleStatus", settleStatus);
			map.put("checkStatus", checkStatus);
			return super.getSqlSession().selectList(this.getNs()+".querySettleMerchantDetail",map);
		}
		/**
		 * 通过结算批次查询商户侧结算表
		 */
		//xiugai
		@Override
		public SettleMerchantRecord querySettleMerchantByBatchNo(
				String batchNo) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("batchNo", batchNo);
			
			 List<SettleMerchantRecord> list = super.getSqlSession().selectList(this.getNs()+".querySettleMerchantByBatchNo",map);
			 
			 if(list != null && list.size() > 0){
				 return list.get(0);
			 }
			 return null;
		}
		/**
		 * 更新结算表状态
		 * @param record
		 */
		public void updateSettleMerchantByBathcNo(SettleMerchantRecord record){
			 super.getSqlSession().update(this.getNs() + ".updateSettleMerchantByBathcNo", record);
		}
		@Override
		public void upateByFeeWay(SettleMerchantRecord record) {
			 super.getSqlSession().update(this.getNs() + ".upateByFeeWay", record);
		}
		@Override
		public List<SettleMerchantRecord> getSettleMerchantRecordByStatus(String checkStatus, String settleStatus,String date) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("checkStatus", checkStatus);
			map.put("settleStatus", settleStatus);
			map.put("settleTime", date);
			return super.getSqlSession().selectList(this.getNs()+".getSettleMerchantRecordByStatus",map);
		}
		
		/**
		 * 
		 * @方法说明：
		 * @author xuangang
		 * @param settleStatus 已结算
		 * @param settleDate 结算日期
		 * @return
		 * @时间：2017年3月14日下午3:15:29
		 */
		@Override
		public List<SettleMerchantRecord> queryAgentforSettle(String settleStatus, Date settleDate, String transType){
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("settleStatus", settleStatus);
			map.put("settleDate", settleDate);
			map.put("transType", transType);
			
			return super.getSqlSession().selectList(this.getNs()+".queryAgentforSettle",map);
			
		}
		
		/* (non-Javadoc)
		 * @see com.heepay.billing.dao.SettleMerchantRecordMapper#querySettleMerchantRecord(java.util.Map)
		 */
		@Override
		public List<SettleMerchantRecord> querySettleMerchantRecord(
				Map<String, Object> paraMap) {
			// TODO Auto-generated method stub
			return super.getSqlSession().selectList(this.getNs()+".querySettleMerchantRecord", paraMap);
		}
		/**
		 * 
		 * @方法说明： 根据商户编码查询结算数据
		 * @author chenyanming
		 * @param merchantId
		 * @return
		 * @时间：2017年6月22日下午2:14:43
		 */
		@Override
		public List<SettleMerchantRecord> querySettleMerchantByMerchantId(String merchantId, String date, String status) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("merchantId", merchantId);
			map.put("settleTime", date);
			map.put("settleStatus", status);
			return super.getSqlSession().selectList(this.getNs()+".querySettleMerchantByMerchantId", map);
		}
		@Override
		public List<String> queryAllSettleData(String start) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("settleTime", start);
			return super.getSqlSession().selectList(this.getNs()+".queryAllSettleData", map);
		}
}
