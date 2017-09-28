package com.heepay.tpds.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.tpds.dao.TpdsCustomerAccountMapper;
import com.heepay.tpds.entity.TpdsCustomerAccount;
import com.heepay.tpds.entity.TpdsCustomerAccountExample;
import com.heepay.tpds.enums.Customerstatus;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年2月14日下午6:47:32
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
public class TpdsCustomerAccountDaoImpl extends BaseDaoImpl<TpdsCustomerAccount,TpdsCustomerAccountExample> implements TpdsCustomerAccountMapper{
	
	// 默认构造方法设置命名空间
	public TpdsCustomerAccountDaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsCustomerAccountMapper");
	}

	@Override
	public List<TpdsCustomerAccount> selectAccountRecordByStatus(String status) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		return super.getSqlSession().selectList(this.getNs()+".selectAccountRecordByStatus",map);
	}

	/*
	 * 查询会员编码
	 * (non-Javadoc)
	 * @see com.heepay.tpds.dao.TpdsCustomerAccountMapper#selectCustomerNoByMerchantNo(com.heepay.tpds.entity.TpdsCustomerAccount)
	 */
	@Override
	public TpdsCustomerAccount selectCustomerNoByMerchantNo(TpdsCustomerAccount record) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", record.getMerchantNo());
		map.put("bankAccNo", record.getBankAccNo());
		map.put("status", Customerstatus.ENABLE.getValue());
		List<TpdsCustomerAccount> list = super.getSqlSession().selectList(this.getNs()+".selectCustomerNoByMerchantNo",map);
		if(null != list && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int updateEntityByAcc(TpdsCustomerAccount record) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankAccNo", record.getBankAccNo());
		map.put("secBankAccNo", record.getSecBankAccNo());
		return super.getSqlSession().update(this.getNs()+".updateEntityByAcc",map);
	}
}
