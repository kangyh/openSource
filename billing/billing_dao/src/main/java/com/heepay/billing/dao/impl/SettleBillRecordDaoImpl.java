package com.heepay.billing.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleBillRecordMapper;
import com.heepay.billing.entity.SettleBillRecord;
import com.heepay.billing.entity.SettleBillRecordExample;
import com.heepay.enums.billing.BillingBillStatus;

 /***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年9月25日上午10:18:52
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
public class SettleBillRecordDaoImpl extends BaseDaoImpl<SettleBillRecord, SettleBillRecordExample> implements SettleBillRecordMapper{

	
	// 默认构造方法设置命名空间
	public SettleBillRecordDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleBillRecordMapper");
	}
	
	
	
	@Override
	public int insert(SettleBillRecord record) {
		return super.getSqlSession().insert(this.getNs() + ".insert", record);
		
	}
	
	/*
	 * 更新账单明细表（settle_bill_record）：账单状态  变为已处理
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.impl.BaseDaoImpl#updateByPrimaryKeySelective(java.lang.Object)
	 */
	public int updateSettleBillRecordByPrimaryKey(SettleBillRecord record){
		Map<String, Object> map = new HashMap<String,Object>();
		if(null != record){
			if(null != record.getBillDetailId()){
				map.put("billDetailId", record.getBillDetailId());
			}
			if(null != record.getBillStatus()){
				map.put("billStatus", record.getBillStatus());
			}
		}
		return super.getSqlSession().update(this.getNs()+".updateSettleBillRecordByPrimaryKey",map);
	}
	
	/**
	 * @author xuangang
	 * @param
	 * 根据对账批次查询对账明细
	 */
	@Override
	public List<SettleBillRecord> queryBillRecordDetailByBatchNo(SettleBillRecord record) {
		Map<String, Object> map = new HashMap<String, Object>();
		//上游流水号
		map.put("checkBathNo", record.getCheckBathNo());
		map.put("billStatus", BillingBillStatus.BBSTATUSN.getValue());
		return super.getSqlSession().selectList(this.getNs()+".queryBillRecordDetailByBatchNo",map);
	}
	
	//根据payment_id查询SettleBillRecord
	@Override
	public SettleBillRecord selectByPaymentId(String  paymentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//上游流水号
		map.put("paymentId", paymentId);
		SettleBillRecord settleBillRecord= super.getSqlSession().selectOne(this.getNs()+".selectByPaymentId",map);
		return settleBillRecord;
	}



	@Override
	public SettleBillRecord selectByChannelNo(String channleNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//上游流水号
		map.put("channleNo", channleNo);
		SettleBillRecord settleBillRecord= super.getSqlSession().selectOne(this.getNs()+".selectByChannelNo",map);
		return settleBillRecord;
	}
}
