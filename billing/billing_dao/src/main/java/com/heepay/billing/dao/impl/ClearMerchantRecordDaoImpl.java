package com.heepay.billing.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.ClearMerchantRecordExample;
import com.heepay.billing.entity.ClearMerchantRecordVo;
import com.heepay.billing.entity.SettleMerchantRecordVo;
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.vo.ClearMerchantDetailMessage;

@Repository
public class ClearMerchantRecordDaoImpl extends BaseDaoImpl<ClearMerchantRecord, ClearMerchantRecordExample>
		implements ClearMerchantRecordMapper {

	// 默认构造方法设置命名空间
	public ClearMerchantRecordDaoImpl() {
		super.setNs("com.heepay.billing.dao.ClearMerchantRecordMapper");
	}

	@Override
	public int insert(ClearMerchantRecord record) {
		return super.getSqlSession().insert(this.getNs() + ".insert", record);
	}

	@Override
	public List<SettleMerchantRecordVo> queryClearChant(ClearMerchantRecord record) {
		
	return super.getSqlSession().selectList(this.getNs()+".queryClearChant",record);
	}

	//接收到系统对账通知修改用户侧的状态
	@Override
	public void updateMerchantStatus(ClearMerchantRecord record) {
		
		super.getSqlSession().selectList(this.getNs()+".updateMerchantStatus",record);
	}

	
	/**
	 * 结算批次生成后，更新清算表状态, 批次号
	 * @author xuangang
	 */
	public int updateAfterSettle(Map record){
		return super.getSqlSession().update(this.getNs() + ".updateClearMerchantAfterSettle", record);
	}

	@Override
	public void updateClearMerchantRecord(ClearMerchantRecord record) {
		super.getSqlSession().update(this.getNs() + ".updateClearMerchantRecord", record);
	}

	@Override
	public void batchUpdateMerchantStatus(SettleMerchantRecordVo record) {
		
		super.getSqlSession().selectList(this.getNs()+".batchUpdateMerchantStatus",record);
		
	}

	/**
	 * 汇总商户端清算流水，生成结算批次
	 * @author xuangang
	 * @param map
	 */
	@Override
	public List<Map> Summarizing(Map<String, Object> map) {
		// TODO Auto-generated method stub
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("checkStatus", record.getCheckStatus());
//		map.put("settleStatus", record.getSettleStatus());
//		map.put("successTime", record.getSuccessTime());
//		map.put("settleCyc", record.getSettleCyc());
//		map.put("checkFlg", record.getCheckFlg());
		return super.getSqlSession().selectList(this.getNs()+".summarizingClearMerchat",map);
		
	}
	/**
	 * 通过结算批次查询清算明细
	 * @author xuangang
	 * @param settleBatch
	 * @return
	 */
	@Override
	public List<ClearMerchantDetailMessage> queryClearDetailBySettleBatch(String settleBatch) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("settleBatch", settleBatch);
		return super.getSqlSession().selectList(this.getNs()+".queryClearDetailBySettleBatch",map);		 
	}
	//根据结算批次查询明细
	@Override
	public List<SettleMerchantRecordVo> getEntity(String record) {
		
		return super.getSqlSession().selectList(this.getNs()+".getEntity",record);	
	}
	
	/*
	 * 根据用户侧清算记录表ID（trans_no）   更新用户侧清算记录表（clearing_merchant_record）：对账状态、对账标记、对账批次号,对账次数
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.ClearMerchantRecordMapper#updateClearMerchantRecordByPrimaryKey(com.heepay.billing.entity.ClearMerchantRecord)
	 */
	@Override
	public void updateClearMerchantRecordByPrimaryKey(ClearMerchantRecord record) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", record.getCheckStatus());
		map.put("checkNum", record.getCheckNum());
		map.put("checkFlg", record.getCheckFlg());
		map.put("checkBath", record.getCheckBath());
		map.put("transNo", record.getTransNo());
		super.getSqlSession().update(this.getNs() + ".updateClearMerchantRecordByPrimaryKey", map);
	}

	/*
	 * 根据交易订单号查询用户侧清算记录表
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.ClearMerchantRecordMapper#findClearMerchantRecordByTansNo(java.lang.String)
	 */
	@Override
	public ClearMerchantRecord findClearMerchantRecordByTansNo(String tansNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transNo", tansNo);
		return super.getSqlSession().selectOne(this.getNs() + ".findClearMerchantRecordByTansNo", map);
	}

	/**
	 * 更新清算表状态
	 * @param record
	 */
	public void updateClearMerchantByBathcNo(ClearMerchantRecord record){
		 super.getSqlSession().update(this.getNs() + ".updateClearMerchantByBathcNo", record);
	}

	/**
	 * 
	 * @方法说明：根据交易订单号（支付单号）  查询  结算状态（N：未结算，D：结算中 Y：已结算）'
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	@Override
	public ClearMerchantRecord selectByTranNo(String tranNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tranNo", tranNo);
		return super.getSqlSession().selectOne(this.getNs() + ".selectByTranNo", map);
		
	}
	
	@Override
	public void updateClearMerchantRecordByTransNo(String tranNo) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("checkStatus", ClearingCheckStatus.CHECKSTATUSY.getValue());
		map.put("checkFlg", BillingYCheckStatus.BCFQMQT.getValue());
		map.put("transNo", tranNo);
		super.getSqlSession().update(this.getNs() + ".updateClearMerchantRecordByTransNo", map);
		
	}

	@Override
	public ClearMerchantRecord getSettleMerchantl(ClearMerchantRecord record) {
		
		return super.getSqlSession().selectOne(this.getNs() + ".getSettleMerchantl", record);
	}
	
	/**
	 * 查询对账批次（已对、未结、平账、t+1， 对账结束）
	 * @author xuangang
	 * @param map
	 * @return
	 */
	@Override
	public List<String> queryCheckBatchNo(Map<String, Object> map){
		
		 return super.getSqlSession().selectList(this.getNs()+".queryCheckBatchNo", map);	
		
	}

	/*
	 * 根据  商户号  交易单号  查询相应的商户侧清算记录  更新是否为分润
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.ClearMerchantRecordMapper#updateIsProfitByMerChantIdAndTransNo(com.heepay.billing.entity.ClearMerchantRecord)
	 */
	@Override
	public void updateIsProfitByMerChantIdAndTransNo(ClearMerchantRecord clearMerchantRecord) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", clearMerchantRecord.getMerchantId());
		map.put("transNo", clearMerchantRecord.getTransNo().toString());
		map.put("isProfit", clearMerchantRecord.getIsProfit());
		super.getSqlSession().update(this.getNs() + ".updateIsProfitByMerChantIdAndTransNo", map);
	}
	/**
	 * @author xuangang
	 * @描述 查询所有已对账、需要分润的清算单
	 * @param map
	 */
	@Override
	public List<ClearMerchantRecord> queryProfitRecordByCheckBatch(Map<String, Object> map){		

		return super.getSqlSession().selectList(this.getNs() + ".queryProfitRecordByCheckBatch", map);
	}
	/**
	 * 更新清算表分润记录结算状态、批次号、处理时间
	 * @author xuangang
	 * @param map
	 */
	@Override
	public void updateProfitSettleStatusByTransNo(Map map){		
		super.getSqlSession().update(this.getNs() + ".updateProfitSettleStatusByTransNo", map);		
	}
	
	/**
	 * @描述 通过订单号，查询分润的清算记录
	 * @author xuangang
	 * @param map
	 * @return
	 */
	@Override
	public ClearMerchantRecord queryProfitRecordByTransNo(Map map){
		List<ClearMerchantRecord> list = super.getSqlSession().selectList(this.getNs()+".queryProfitRecordByTransNo", map);
		
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * @方法说明：判断是否重复发送的数据
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	@Override
	public int getBooleanExist(String transNo) {
		
		return super.getSqlSession().selectOne(this.getNs() + ".getBooleanExist", transNo);		
	}


    /**
     * 
     * @方法说明：
     * @author xuangang
     * @param merchantId
     * @param settleStatus
     * @param transType
     * @return
     * @时间：2017年3月14日下午4:38:51
     */
	@Override
	public List<ClearMerchantRecord> queryAgentforSettle(Integer merchantId, String settleStatus, String transType, Date settleTime){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", merchantId);
		map.put("settleStatus", settleStatus);
		map.put("transType", transType);
		map.put("settleTime", settleTime);
		
		List<ClearMerchantRecord> list = super.getSqlSession().selectList(this.getNs() + ".queryAgentforSettle", map);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.heepay.billing.dao.ClearMerchantRecordMapper#queryClearMerchantRecord(java.util.Map)
	 */
	@Override
	public List<ClearMerchantRecord> queryClearMerchantRecord(
			Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		 List<ClearMerchantRecord> list = super.getSqlSession().selectList(this.getNs() + ".queryClearMerchantRecord", paraMap);
		 return list;
	}
	
	public List<Map<String, String>> queryClearMerchantRecord(){
		return null;
	}
	/**
	 * 
	 * @方法说明：更新历史数据
	 * @author xuangang
	 * @param map
	 * @时间：2017年3月29日下午2:11:53
	 */
	@Override
	public void updateOldClearingmerchant(Map map){
		super.getSqlSession().update(this.getNs() + ".updateOldClearingmerchant", map);		
	}

	/* (non-Javadoc)
	 * @see com.heepay.billing.dao.ClearMerchantRecordMapper#queryAgentforSettle(java.util.Map)
	 */
	@Override
	public List<ClearMerchantRecord> queryAgentforSettle(Map map) {
		return super.getSqlSession().selectList(this.getNs() + ".queryClearAgentforSettle", map);	
	}
	
	
	public List<ClearMerchantRecord> queryAgentforSettlePayment(Integer merchantId, String settleStatus, String transType, Date settleTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", merchantId);
		map.put("settleStatus", settleStatus);
		map.put("transType", transType);
		map.put("settleTime", settleTime);
		return super.getSqlSession().selectList(this.getNs() + ".queryAgentforSettlePayment", map);	
	}	
	
	
	
	@Override
	public List<ClearMerchantRecord> queryPayTime(String checkStatus,String channelCode,String channelType,String checkNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", checkStatus);
		map.put("channelCode", channelCode);
		map.put("channelType", channelType);
		map.put("checkNum", checkNum);
		
		List<ClearMerchantRecord> list = super.getSqlSession().selectList(this.getNs() + ".queryPayTime", map);
		return list;
	}
	
		@Override
	public void updateNumByTransNO(String transNo) {
		super.getSqlSession().update(this.getNs() + ".updateNumByTransNO", transNo);
		
	}

	@Override
	public List<ClearMerchantRecord> queryHandleBill(String checkStatus, String channelCode, String channelType,
			String checkNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", checkStatus);
		map.put("channelCode", channelCode);
		map.put("channelType", channelType);
		map.put("checkNum", checkNum);
		
		List<ClearMerchantRecord> list = super.getSqlSession().selectList(this.getNs() + ".queryHandleBill", map);
		return list;
	}

	/**
	 * 
	 * @方法说明： 商户侧结算汇总
	 * @author chenyanming
	 * @param startBefor
	 * @param endBefor
	 * @return
	 * @时间：2017年6月22日下午1:23:13
	 */
	@Override
	public List<ClearMerchantRecordVo> getMerchantClearData(String startBefor, String endBefor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startBefor", startBefor);
		map.put("endBefor", endBefor);
		return super.getSqlSession().selectList(this.getNs() + ".getMerchantClearData", map);
	}
	
	/**
	 * 
	 * @方法说明：分页查询清算数据
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年7月7日上午11:32:12
	 */
	public List<ClearMerchantDetailMessage> queryClearDataByPage(Map map){
		
		return super.getSqlSession().selectList(this.getNs() + ".queryClearDataByPage", map);		
	}
}