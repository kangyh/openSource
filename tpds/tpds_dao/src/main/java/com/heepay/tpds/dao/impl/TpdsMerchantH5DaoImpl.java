package com.heepay.tpds.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsMerchantH5Mapper;
import com.heepay.tpds.entity.TpdsMerchantH5;
import com.heepay.tpds.entity.TpdsMerchantH5Example;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang 创建时间： 2017年2月18日 下午4:25:46 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Repository
public class TpdsMerchantH5DaoImpl extends BaseDaoImpl<TpdsMerchantH5, TpdsMerchantH5Example>
		implements TpdsMerchantH5Mapper {

	// 默认构造方法设置命名空间
	public TpdsMerchantH5DaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsMerchantH5Mapper");
	}

	@Override
	public int insert(TpdsMerchantH5 record) {
		return this.getSqlSession().insert(this.getNs() + ".insert", record);
	}

	@Override
	public int update(TpdsMerchantH5 record) {
		return this.getSqlSession().update(this.getNs() + ".update", record);
	}

	/**
	 * 
	 * @方法说明：查询交易密码交易成功的记录
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年2月18日下午4:48:23
	 */
	public Integer queryMerchantH5BybusinessSeqNo(Map<String, String> map) {

		return super.getSqlSession().selectOne(this.getNs() + ".queryBybusinessSeqNo", map);
	}

	public void updateBybusinessSeqNo(TpdsMerchantH5 record) {
		super.getSqlSession().update(this.getNs() + ".updateBybusinessSeqNo", record);
	}
	
	/**
	 * 
	 * @方法说明：查询业务流水号是否已存在
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年4月17日下午4:54:11
	 */
	public Integer countMerchantH5BybusinessSeqNo(Map<String, String> map) {

		return super.getSqlSession().selectOne(this.getNs() + ".CountBybusinessSeqNo", map);
	}
	
	public TpdsMerchantH5 queryMerchantBybusinessSeqNo(Map<String, String> map){
		return super.getSqlSession().selectOne(this.getNs() + ".queryMerchantBybusinessSeqNo", map);
	}
	
	public int insertMerchantH5(TpdsMerchantH5 record) {
		return super.getSqlSession().insert(this.getNs()+".insertMerchantH5", record);
	}
}
