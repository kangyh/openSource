package com.heepay.tpds.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsMerchantAccountMapper;
import com.heepay.tpds.dao.TpdsProductKeyMapper;
import com.heepay.tpds.entity.TpdsMerchantAccount;
import com.heepay.tpds.entity.TpdsProductKey;
import com.heepay.tpds.entity.TpdsProductKeyExample;

/**
 * 
 * 
 * 描    述：商户与汇付宝签约key
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年3月3日上午10:05:05 
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
public class TpdsProductKeyDaoImpl extends BaseNewDaoImpl<TpdsProductKey> implements TpdsProductKeyMapper
{

	// 默认构造方法设置命名空间
	public TpdsProductKeyDaoImpl() {
			super.setNs("com.heepay.tpds.dao.TpdsProductKeyMapper");
	}
	
	@Override
	public int insert(TpdsProductKey record) {
		return super.getSqlSession().insert(this.getNs() + ".insert", record);
	}
	
	/**
	 * 
	 * @方法说明： 根据商户id和产品编码查询key
	 * @author chenyanming
	 * @param merchantNo
	 * @param productCode
	 * @return
	 * @时间：2017年3月3日上午10:07:45
	 */
	@Override
	public TpdsProductKey selectTpdsProductKeyByMerchantNo(String merchantNo, String productCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		map.put("productCode", productCode);
		return super.getSqlSession().selectOne(this.getNs()+".selectTpdsProductKeyByMerchantNo",map);
	}

	@Override
	public int updateByPrimaryKey(TpdsProductKey record) {
		return super.getSqlSession().insert(this.getNs() + ".updateByPrimaryKey", record);
	}

	@Override
	public List<TpdsProductKey> getList() {
		return super.getSqlSession().selectList(this.getNs() + ".getList");
	}

}
