package com.heepay.tpds.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsMerchantMsgMapper;
import com.heepay.tpds.entity.TpdsMerchantMsg;
import com.heepay.tpds.entity.TpdsMerchantMsgExample;

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
public class TpdsMerchantMsgDaoImpl extends BaseDaoImpl<TpdsMerchantMsg,TpdsMerchantMsgExample> implements TpdsMerchantMsgMapper{
	
	// 默认构造方法设置命名空间
	public TpdsMerchantMsgDaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsMerchantMsgMapper");
	}

	/*
	 * 更新响应码
	 * (non-Javadoc)
	 * @see com.heepay.tpds.dao.TpdsMerchantMsgMapper#updateRespCodeBySystemNo(com.heepay.tpds.entity.TpdsMerchantMsg)
	 */
	@Override
	public int updateRespCodeBySystemNo(TpdsMerchantMsg record) {
		return this.getSqlSession().update(this.getNs()+".updateRespCodeBySystemNo",record);
	}

	@Override
	public int updateRespCodeByBusinessSeqNo(TpdsMerchantMsg record) {
		return this.getSqlSession().update(this.getNs()+".updateRespCodeByBusinessSeqNo",record);
	}

}
