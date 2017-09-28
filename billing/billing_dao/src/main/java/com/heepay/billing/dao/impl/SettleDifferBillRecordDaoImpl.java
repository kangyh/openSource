package com.heepay.billing.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleDifferBillRecordMapper;
import com.heepay.billing.entity.SettleDifferBillRecord;
import com.heepay.billing.entity.SettleDifferBillRecordExample;

/**
 * 
 * 
 * 描    述：异常明细dao实现类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年7月27日下午2:09:00 
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
public class SettleDifferBillRecordDaoImpl extends BaseDaoImpl<SettleDifferBillRecord, SettleDifferBillRecordExample> 
			implements SettleDifferBillRecordMapper {

	// 默认构造方法设置命名空间
		public SettleDifferBillRecordDaoImpl() {
			super.setNs("com.heepay.billing.dao.SettleDifferBillRecordMapper");
		}

		@Override
		public int insert(SettleDifferBillRecord record) {
			return super.getSqlSession().insert(this.getNs() + ".insert", record);
		}

		@Override
		public SettleDifferBillRecord selectByPaymentId(String paymentId) {
			return super.getSqlSession().selectOne(this.getNs()+".selectByPaymentId",paymentId);
		}

}
