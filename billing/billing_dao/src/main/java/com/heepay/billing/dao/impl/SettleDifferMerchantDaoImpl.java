package com.heepay.billing.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleDifferMerchantMapper;
import com.heepay.billing.entity.SettleDifferMerchant;
import com.heepay.billing.entity.SettleDifferMerchantExample;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.SettleDifferCheckStatus;

/**
 * 
 * 
 * 描    述：商户侧差错账汇总dao实现类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月2日下午2:52:20 
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
public class SettleDifferMerchantDaoImpl extends BaseDaoImpl<SettleDifferMerchant, SettleDifferMerchantExample> implements SettleDifferMerchantMapper {
	//默认构造方法设置命名空间
	public SettleDifferMerchantDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleDifferMerchantMapper");
	}
	
	@Override
	public int insert(SettleDifferMerchant record) {
		return super.getSqlSession().insert(this.getNs() + ".insert", record);
	}

	/**
	 * 
	 * @方法说明： 汇总差错记录完成，更新差错状态
	 * @author chenyanming
	 * @param errorBath
	 * @时间：2016年11月3日下午3:45:56
	 */
	@Override
	public void updateErrorStatusByErrorBath(String errorBath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorBath", errorBath);
		map.put("errorStatus", BillingBillStatus.BBSTATUSY.getValue());
		map.put("dealTime", new Date());
		map.put("checkStatus", SettleDifferCheckStatus.STATUSS.getValue());
		super.getSqlSession().update(this.getNs() + ".updateErrorStatusByErrorBath", map);
		
	}

	/**
	 * 
	 * @方法说明： 查询出所有审核通过的批次记录
	 * @author chenyanming
	 * @param checkStatus
	 * @return
	 * @时间：2016年11月15日下午4:08:59
	 */
	@Override
	public List<SettleDifferMerchant> querySuccessSettleDifferMerchantData(String checkStatus, String errorStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", checkStatus);
		map.put("errorStatus", errorStatus);
		return super.getSqlSession().selectList(this.getNs() + ".querySuccessSettleDifferMerchantData", map);
	}

	@Override
	public void updateErrorStatusAndDateByErrorBath(String errorBath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorBath", errorBath);
		map.put("errorStatus", BillingBillStatus.BBSTATUSY.getValue());
		map.put("dealTime", new Date());
		super.getSqlSession().update(this.getNs() + ".updateErrorStatusAndDateByErrorBath", map);
		
	}
	/**
	 * 
	 * @方法说明：通过交易订单号查询商户差异记录
	 * @author xuangang
	 * @param transNo
	 * @return
	 * @时间：2017年5月26日下午3:01:51
	 */
	public SettleDifferMerchant queryDifferMerchantByTransNo(String transNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transNo", transNo);
		return super.getSqlSession().selectOne(this.getNs() + ".querySettleDifferMerchantByTransNo", map);
	}	
	
}
