package com.heepay.tpds.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.heepay.tpds.entity.TpdsBankWithdrawExample;
import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsBankWithdrawMapper;
import com.heepay.tpds.entity.TpdsBankWithdraw;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月16日 上午9:50:08
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
public class TpdsBankWithdrawDaoImpl extends BaseDaoImpl<TpdsBankWithdraw,TpdsBankWithdrawExample> implements TpdsBankWithdrawMapper {

	// 默认构造方法设置命名空间
	public TpdsBankWithdrawDaoImpl() {
			super.setNs("com.heepay.tpds.dao.TpdsBankWithdrawMapper");
		}

	@Override
	public TpdsBankWithdraw selectByBusinessOrderNo(String businessOrderNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessOrderNo", businessOrderNo);
		return super.getSqlSession().selectOne(this.getNs()+".selectByBusinessOrderNo",map);
	}
}

 