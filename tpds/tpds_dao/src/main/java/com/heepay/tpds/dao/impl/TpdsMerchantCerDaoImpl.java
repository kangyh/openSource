package com.heepay.tpds.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsBindInterfaceMapper;
import com.heepay.tpds.dao.TpdsMerchantCerMapper;
import com.heepay.tpds.entity.TpdsBindInterface;
import com.heepay.tpds.entity.TpdsMerchantCer;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月17日 下午3:08:09
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
public class TpdsMerchantCerDaoImpl  extends BaseNewDaoImpl<TpdsMerchantCer> implements TpdsMerchantCerMapper {

	// 默认构造方法设置命名空间
		public TpdsMerchantCerDaoImpl() {
			super.setNs("com.heepay.tpds.dao.TpdsMerchantCerMapper");
		}

	@Override
	public int insert(TpdsMerchantCer record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public int update(TpdsMerchantCer record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(this.getNs()+".update",record);
	}

	/**
	 * 
	 * @方法说明： 根据商户编码查询key
	 * @author chenyanming
	 * @param merchantNo
	 * @return
	 * @时间：2017年3月2日上午10:28:18
	 */
	@Override
	public TpdsMerchantCer selectTpdsMerchantCerByMerchantNo(String merchantNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		return super.getSqlSession().selectOne(this.getNs()+".selectTpdsMerchantCerByMerchantNo",map);
	}

	@Override
	public List<TpdsMerchantCer> getlist() {
		return super.getSqlSession().selectList(this.getNs()+".getlist");
	}

	@Override
	public int deleteByMerchantNo(String merchantNo) {
		return this.getSqlSession().delete(this.getNs()+".deleteByMerchantNo",merchantNo);
	}
}

 