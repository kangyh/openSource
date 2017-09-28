/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年4月19日下午5:20:37
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
package com.heepay.tpds.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsBankH5Mapper;
import com.heepay.tpds.entity.TpdsBankH5;
import com.heepay.tpds.entity.TpdsBankH5Example;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年4月19日下午5:20:37
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
public class TpdsBankH5DaoImpl extends BaseDaoImpl<TpdsBankH5, TpdsBankH5Example>
implements TpdsBankH5Mapper{

	// 默认构造方法设置命名空间
	public TpdsBankH5DaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsBankH5Mapper");
	}
	@Override
	public int insert(TpdsBankH5 record) {
		return this.getSqlSession().insert(this.getNs() + ".insert", record);
	}

	/* (non-Javadoc)
	 * @see com.heepay.tpds.dao.TpdsBankH5Mapper#deleteByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.heepay.tpds.dao.TpdsBankH5Mapper#selectByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public TpdsBankH5 selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateBybusinessSeqNo(Map map){
		this.getSqlSession().update(this.getNs() + ".updateBybusinessSeqNo", map);
	}

}
