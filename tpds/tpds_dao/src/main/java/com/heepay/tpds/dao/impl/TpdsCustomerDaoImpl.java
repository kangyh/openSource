package com.heepay.tpds.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsCustomerMapper;
import com.heepay.tpds.entity.TpdsCustomer;
import com.heepay.tpds.entity.TpdsCustomerExample;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangyinglei
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
public class TpdsCustomerDaoImpl extends BaseDaoImpl<TpdsCustomer,TpdsCustomerExample> implements TpdsCustomerMapper{
	
	// 默认构造方法设置命名空间
	public TpdsCustomerDaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsCustomerMapper");
	}

	@Override
	public int deleteByPrimaryKey(Integer customerId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TpdsCustomer selectByPrimaryKey(Integer customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
