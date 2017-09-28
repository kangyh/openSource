package com.heepay.billing.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleDifferChannelMapper;
import com.heepay.billing.entity.SettleDifferChannel;
import com.heepay.billing.entity.SettleDifferChannelExample;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.SettleDifferCheckStatus;

/**
 * 
 * 
 * 描    述：通道侧差错账汇总dao实现类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月2日下午2:48:17 
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
public class SettleDifferChannelDaoImpl extends BaseDaoImpl<SettleDifferChannel, SettleDifferChannelExample> implements SettleDifferChannelMapper {
	//默认构造方法设置命名空间
	public SettleDifferChannelDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleDifferChannelMapper");
	}
	
	@Override
	public int insert(SettleDifferChannel record) {
		return super.getSqlSession().insert(this.getNs() + ".insert", record);
	}

	/**
	 * 
	 * @方法说明： 撤账或补账完成，更新批次记录状态为已处理
	 * @author chenyanming
	 * @param errorBath
	 * @时间：2016年11月3日下午3:28:45
	 */
	@Override
	public void updateErrorStatusByErrorBath(String errorBath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorBath", errorBath);
		map.put("errorStatus", BillingBillStatus.BBSTATUSY.getValue());
		map.put("dealTime", new Date());
		super.getSqlSession().update(this.getNs() + ".updateErrorStatusByErrorBath", map);
		
	}
	
	/**
	 * 
	 * @方法说明： 撤账或补账完成，更新批次记录状态为已处理   添加更新checkStatus字段
	 * @author chenyanming
	 * @param errorBath
	 * @时间：2016年11月3日下午3:28:45
	 */
	@Override
	public void updateErrorStatusAndCheckStatusByErrorBath(String errorBath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorBath", errorBath);
		map.put("errorStatus", BillingBillStatus.BBSTATUSY.getValue());
		map.put("dealTime", new Date());
		map.put("checkStatus", SettleDifferCheckStatus.STATUSS.getValue());
		super.getSqlSession().update(this.getNs() + ".updateErrorStatusAndCheckStatusByErrorBath", map);
		
	}
	
	/**
	 * 
	 * @方法说明： 根据支付单号查询通道侧差错批次数据
	 * @author chenyanming
	 * @param paymentId
	 * @return
	 * @时间：2016年11月15日下午6:07:44
	 */
	@Override
	public SettleDifferChannel querySettleDifferChannelByPaymentId(String paymentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentId", paymentId);
		return super.getSqlSession().selectOne(this.getNs() + ".querySettleDifferChannelByPaymentId", map);
	}
	
	/**
	 * 查询人工审核未通过且未处理的通道侧批次汇总数据
	 * @param checkStatus
	 * @param errorStatus
	 * @return
	 */
	public List<SettleDifferChannel> querySuccessSettleDifferChannelData(String checkStatus, String errorStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", checkStatus);
		map.put("errorStatus", errorStatus);
		return super.getSqlSession().selectList(this.getNs() + ".querySuccessSettleDifferChannelData", map);
	}
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param transNo
	 * @return
	 * @时间：2017年5月26日下午3:32:58
	 */
	public SettleDifferChannel querySettleDifferChannelByTransNo(String transNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transNo", transNo);
		return super.getSqlSession().selectOne(this.getNs() + ".querySettleDifferChannelByTransNo", map);
	}
}
