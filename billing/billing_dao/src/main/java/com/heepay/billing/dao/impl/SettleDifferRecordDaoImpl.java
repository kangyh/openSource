package com.heepay.billing.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.billing.entity.SettleDifferRecordExample;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingDealOpinion;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.SettleDifferIsProfit;

@Repository
public class SettleDifferRecordDaoImpl extends BaseDaoImpl<SettleDifferRecord, SettleDifferRecordExample> implements SettleDifferRecordMapper {

	// 默认构造方法设置命名空间
	public SettleDifferRecordDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleDifferRecordMapper");
	}
	@Override
	public int updateSettleDifferRecordByDifferId(SettleDifferRecord sdr) {
		return super.getSqlSession().update(this.getNs() + ".updateSettleDifferRecordByDifferId", sdr);
	}
	
	/**
	 * @方法说明：插入差异记录表
	 * @author wangdong
	 * @时间：2016年9月25日10:50:11
	 */
	@Override
	public int insertSettleDifferRecord(SettleDifferRecord record) {
		return super.getSqlSession().insert(this.getNs() + ".insertSettleDifferRecord", record);
	}

	@Override
	public int updateByPrimaryKey(SettleDifferRecord record) {
		return super.getSqlSession().update(this.getNs() + ".updateByPrimaryKey", record);
	}

	//查询差异类型为未知的差异记录
	@Override
	public List<SettleDifferRecord> selectByDifferType(String differType) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("differType", differType);
		map.put("handleResult", ClearingCheckStatus.CHECKSTATUSN.getValue());
		return super.getSqlSession().selectList(this.getNs()+".selectByDifferType",map);
		
	}
	
	
	//更改差异表差异类型为未知状态
	@Override
	public int updateSettleDifferRecord(SettleDifferRecord sdr) {
		return super.getSqlSession().update(this.getNs() + ".updateSettleDifferRecord", sdr);

	}

	@Override
	public void updateSettleDifferRecordByPaymentId(String paymentId ,String handleResult) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentId", paymentId);
		map.put("handleResult", handleResult);
		super.getSqlSession().update(this.getNs() + ".updateSettleDifferRecordByPaymentId", map);
		
	}

	/**
	 * 
	 * @方法说明： 查询所有未记账的差异记录
	 * @author chenyanming
	 * @param value
	 * @return
	 * @时间：2016年10月28日上午11:28:02
	 */
	@Override
	public List<SettleDifferRecord> querySettleDifferRecordData(String isBill) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isProfit", SettleDifferIsProfit.ACCOUNTFLGN.getValue());
		map.put("isBill", isBill);
		map.put("dealBD", BillingDealOpinion.BILLINGDEALBD.getValue());
		map.put("dealCD", BillingDealOpinion.BILLINGDEALCD.getValue());
		map.put("handleResult", BillingBillStatus.BBSTATUSY.getValue());
		return super.getSqlSession().selectList(this.getNs() + ".querySettleDifferRecordData", map);
	}

	/**
	 * 
	 * @方法说明： 汇总差错记录完成，更新记账状态
	 * @author chenyanming
	 * @param differId
	 * @时间：2016年11月3日下午3:06:54
	 */
	@Override
	public void updateIsBillStatus(String paymentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentId", paymentId);
		map.put("isBill", ClearingCheckStatus.CHECKSTATUSY.getValue());
		super.getSqlSession().update(this.getNs() + ".updateIsBillStatus", map);
	}

	/**
	 * 
	 * @方法说明： 根据交易单号查询差错记录
	 * @author chenyanming
	 * @param transNo
	 * @return
	 * @时间：2016年11月15日下午5:23:04
	 */
	@Override
	public SettleDifferRecord querySettleDifferRecordByTransNo(String transNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transNo", transNo);
		return super.getSqlSession().selectOne(this.getNs() + ".querySettleDifferRecordByTransNo", map);
	}
	
	/**
	 * 
	 */
	@Override
	public List<SettleDifferRecord> selectByDifferTypeAndHandleResult(String differType, String handleResult) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("differType", differType);
		map.put("handleResult", handleResult);
		return super.getSqlSession().selectList(this.getNs() + ".selectByDifferTypeAndHandleResult", map);
	}
	
	
	@Override
	public void updateStatusBySettleBath(String settleBath,String handleResult) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settleBath", settleBath);
		map.put("handleResult", handleResult);
		//map.put("operationDate", operationDate);
		super.getSqlSession().update(this.getNs() + ".updateStatusBySettleBath", map);
		
	}
	
	/**
	 * 插入其他差异
	 */
	@Override
	public int insertOtherSettleDifferRecord(SettleDifferRecord record) {
		return super.getSqlSession().insert(this.getNs() + ".insertOtherSettleDifferRecord", record);
	}
    /**
     * 
     * @方法说明：
     * @author xuangang
     * @param map
     * @return
     * @时间：2017年6月26日下午4:46:14
     */
	@Override
	public int countDifferRecordNum(Map map){
		return super.getSqlSession().selectOne(this.getNs() + ".queryDifferRecordNumBytransNo", map);		
	}
	/**
	 * 
	 * @方法说明：通过支付单号统计差异数据总数
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年6月28日下午3:37:43
	 */
	public int countDifferRecordNumByPaymentId(Map map){
		return super.getSqlSession().selectOne(this.getNs() + ".queryDifferRecordNumByPaymentId", map);	
	}
	
}
