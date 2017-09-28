package com.heepay.billing.dao.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearChannelRecordExample;
import com.heepay.billing.entity.SettleChannelRecord;
import com.heepay.billing.entity.SettleChannelRecordVo;
import com.heepay.billingutils.util.Constants;
import com.heepay.date.DateUtils;
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.enums.billing.ClearingCheckStatus;

/**
 * 
 * 
 * 描 述：通道侧dao层实现
 *
 * 创 建 者：chenyanming 创建时间： 2016年9月9日上午9:12:35 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Repository
public class ClearChannelRecordDaoImpl extends BaseDaoImpl<ClearChannelRecord, ClearChannelRecordExample>
		implements ClearChannelRecordMapper {
	// 默认构造方法设置命名空间
	public ClearChannelRecordDaoImpl() {
		super.setNs("com.heepay.billing.dao.ClearChannelRecordMapper");
	}

	@Override
	public int insert(ClearChannelRecord record) {
		return super.getSqlSession().insert(this.getNs() + ".insert", record);
	}

	/**
	 * 查询通道侧清算流水数据
	 */
	@Override
	public List<ClearChannelRecord> queryClearChannelRecord() {

		return super.getSqlSession().selectOne(this.getNs() + ".queryClearChannelRecordId");
	}

	@Override
	public List<SettleChannelRecordVo> queryClearChannelRecordVo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", Constants.CHECK_STATUS_Y);
		map.put("checkFlg", BillingYCheckStatus.BCFQSTS.getValue());
		return super.getSqlSession().selectList(this.getNs() + ".queryClearChannelRecordId", map);
	}

	/**
	 * 对账完成，修改对账状态
	 */
	@Override
	public void updateClearChannelRecordStatus() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("finishTime", DateUtils.getDate());
		map.put("checkStatus", Constants.CHECK_STATUS_Y);
		map.put("settleStatus", Constants.CHECK_STATUS_Y);
		map.put("checkTime", DateUtils.getDate());
		map.put("checkFlg", BillingYCheckStatus.BCFQSTS.getValue());
		map.put("channelTime", DateUtils.getDate());
		super.getSqlSession().update(this.getNs() + ".updateClearChannelRecordStatus", map);
	}

	/**
	 * 查询清算明细，支付单号集合
	 */
	@Override
	public List<ClearChannelRecord> queryClearChannelRecords(SettleChannelRecord settleChannelRecord, String transType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelCode", settleChannelRecord.getChannelCode());
		map.put("channelType", settleChannelRecord.getChannelType());
		map.put("checkStatus", Constants.CHECK_STATUS_Y);
		map.put("checkFlg", BillingYCheckStatus.BCFQSTS.getValue());
		map.put("transType", transType);
		return super.getSqlSession().selectList(this.getNs() + ".queryClearChannelRecordIds", map);
	}

	/**
	 * 日终对账开始，修改对账状态
	 */
	@Override
	public void UpdateChannelStatus() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", Constants.CHECK_STATUS_Y);

		super.getSqlSession().update(this.getNs() + ".UpdateChannelStatus", map);

	}

	// 修改实时通道侧的清算表为状态为 结算中
	@Override
	public void UpdateChannelSettleStatus() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("settleStatus", Constants.SETTLE_STATUS_D);

		super.getSqlSession().update(this.getNs() + ".UpdateChannelSettleStatus", map);
	}

	/**
	 * 根据支付单号将对应的批次号设置到清算表中
	 */
	@Override
	public void updateSettleNoToClearChannelRecord(SettleChannelRecord settleChannelRecord,String transType, String settleStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelCode", settleChannelRecord.getChannelCode());
		map.put("channelType", settleChannelRecord.getChannelType());
		map.put("checkStatus", Constants.CHECK_STATUS_Y);
		map.put("checkFlg", BillingYCheckStatus.BCFQSTS.getValue());
		map.put("transType", transType);
		map.put("settleBath", settleChannelRecord.getSettleBath());
		map.put("settleStatus", settleStatus);
		super.getSqlSession().update(this.getNs() + ".updateSettleNoToClearChannelRecord", map);

	}

	// 修改清算表的状态
	@Override
	public int updateByPrimaryKey(ClearChannelRecord record) {

		super.getSqlSession().update(this.getNs() + ".updateChannelByPrimaryKey", record);
		return 0;
	}

	// 核账完成，修改清算表中完成时间和结算状态
	@Override
	public void updateClearChannelRecord(String settleBath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settleBath", settleBath);
		map.put("settleStatus", Constants.CHECK_STATUS_Y);
		map.put("finishTime", new Date());
		super.getSqlSession().update(this.getNs() + ".updateClearChannelRecord", map);
	}

	/**
	 * 通过支付单号，查询通道侧清算记录
	 * 
	 * @author xuangang
	 * @param paymentId
	 * @return
	 */
	public ClearChannelRecord queryClearChannelRecordByPaymentId(String paymentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentId", paymentId);
		
		List<ClearChannelRecord> list = super.getSqlSession()
				.selectList(this.getNs() + ".queryClearChannelRecordByPaymentId", map);

		if (list != null && list.size() > 0)
			return (ClearChannelRecord) (list.get(0));

		return null;
	}
	
	/**
	 * 查询告警记录
	 * 
	 */
	@Override
	public List<ClearChannelRecord> queryClearMerchantRecords(ClearChannelRecord settleChannelRecord) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("check_status", "N");
		
		List<ClearChannelRecord> list = super.getSqlSession()
				.selectList(this.getNs() + ".queryAlarmRecord", map);
		return list;
	}
	
	/*
	 * 根据支付单号查询通道侧清算记录中为未对账的记录
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.ClearChannelRecordMapper#queryClearChannelRecordByPaymentIdAndCheckStatus(java.lang.String)
	 */
	@Override
	public ClearChannelRecord queryClearChannelRecordByPaymentIdAndCheckStatus(ClearChannelRecord record) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentId", record.getPaymentId());
		map.put("bankSeq", record.getBankSeq());
		map.put("checkStatus", ClearingCheckStatus.CHECKSTATUSN.getValue());
		List<ClearChannelRecord> list = super.getSqlSession()
				.selectList(this.getNs() + ".queryClearChannelRecordByPaymentIdAndCheckStatus", map);

		if (list != null && list.size() > 0){
			return (ClearChannelRecord) (list.get(0));
		}
		return null;
	}
	
	/*
	 * 根据通道侧清算记录表ID（clearing_id）   更新通道侧清算记录表（clearing_channel_record）：  对账状态、通道对账日期、对账标记、对账批次号,对账次数
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.ClearChannelRecordMapper#updateClearChannelRecordByPrimaryKey(com.heepay.billing.entity.ClearChannelRecord)
	 */
	@Override
	public void updateClearChannelRecordByPrimaryKey(ClearChannelRecord record){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", record.getCheckStatus());//对账状态
		map.put("checkNum", record.getCheckNum());//对账次数
		map.put("channelTime", record.getChannelTime());//通道对账日期
		map.put("checkFlg", record.getCheckFlg());//对账标记
		map.put("checkBath", record.getCheckBath());//对账批次
		map.put("clearingId", record.getClearingId());//清算ID
		map.put("costAmount", record.getCostAmount());//成本
		super.getSqlSession().update(this.getNs() + ".updateClearChannelRecordByPrimaryKey", map);
	}
	
	/**
	 * 
	 * @方法说明：根据交易订单号（支付单号）  查询  结算状态（N：未结算，D：结算中 Y：已结算）'
	 * @时间：Nov 7, 2016
	 * @创建人：wangl   通用方法
	 */
	@Override
	public ClearChannelRecord queryClearChannelRecordByTranNo(String tranNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tranNo", tranNo);
		
		ClearChannelRecord clearChannelRecord=super.getSqlSession()
		.selectOne(this.getNs() + ".queryClearChannelRecordByTranNo", map);
		
		return clearChannelRecord;
	}

	@Override
	public List<ClearChannelRecord> queryPayTime(String checkStatus,String channelCode,String channelType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", checkStatus);
		map.put("channelCode", channelCode);
		map.put("channelType", channelType);
		
		List<ClearChannelRecord> list = super.getSqlSession().selectList(this.getNs() + ".queryPayTime", map);
		return list;
	}
	
	
	@Override
	public void updateClearChannelRecordByTransNo(String tranNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", ClearingCheckStatus.CHECKSTATUSY.getValue());
		map.put("checkFlg", BillingYCheckStatus.BCFQMQT.getValue());
		map.put("transNo", tranNo);
		try {
			map.put("channelTime", DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));
		} catch (ParseException e) {
			//xiugai
		}
		super.getSqlSession().update(this.getNs() + ".updateClearChannelRecordByTransNo", map);
		
	}

	/**
	 * 
	 * @方法说明：判断是否重复发送的数据
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	@Override
	public int getBooleanExist(String paymentId) {
		
		return super.getSqlSession().selectOne(this.getNs() + ".getBooleanExist", paymentId);
	}

	@Override
	public List<ClearChannelRecord> queryChannelRecord(String dateTimePlan) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settleTimePlan", dateTimePlan);
		return super.getSqlSession().selectList(this.getNs()+".queryChannelRecord",map);
	}
	
	/**
	 * 
	 * @方法说明：查询字段为null的
	 * @author xuangang
	 * @return
	 * @时间：2017年3月29日上午11:34:49
	 */
	public List<Map<String, String>> queryValIsNull(){
		return super.getSqlSession().selectList(this.getNs()+".queryValIsNull",null);
	}
	/**
	 * 	
	 * @方法说明：更新历史数据
	 * @author xuangang
	 * @param map
	 * @时间：2017年3月29日下午1:50:28
	 */
	public void updateOldRecord(Map map){
		super.getSqlSession().update(this.getNs() + ".updateOldRecord", map);
	}
	
	public int queryChannelByPaymentIdTransNo(Map map){
		
		return super.getSqlSession().selectOne(this.getNs()+".queryChannelByPaymentIdTransNo", map);		 
	}
	
	
	@Override
	public void updateNumByTransNO(String transNo) {
		super.getSqlSession().update(this.getNs() + ".updateNumByTransNO", transNo);
	}

	@Override
	public List<ClearChannelRecord> queryHandleBill(String checkStatus, String channelCode, String channelType,
			String checkNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", checkStatus);
		map.put("channelCode", channelCode);
		map.put("channelType", channelType);
		map.put("checkNum", checkNum);
		
		List<ClearChannelRecord> list = super.getSqlSession().selectList(this.getNs() + ".queryHandleBill", map);
		return list;
	}

	@Override
	public List<String> querySettleBatch(String checkBath) {
		return super.getSqlSession().selectList(this.getNs()+".querySettleBatch", checkBath);	
	}

	@Override
	public List<ClearChannelRecord> querySettleBathMsgBySettleBath(Map<String, Object> paraMap) {
		return super.getSqlSession().selectList(this.getNs() + ".querySettleBathMsgBySettleBath", paraMap);
	}
}
