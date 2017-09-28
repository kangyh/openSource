package com.heepay.manage.modules.settle.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.settle.entity.SettleDicItem;
import com.heepay.manage.modules.settle.entity.SettleDicType;

/**
*
* 描    述：通道清算记录DAO接口
*
* 创 建 者： @author wangdong
* 创建时间：
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
@MyBatisDao
public interface SettleDicTypeDao extends CrudDao<SettleDicType> {

	/**
	 * @方法说明：查询是否存在相同的编码
	 * @时间：2016年10月13日
	 * @创建人：wangdong
	 */
	Integer findCodeTextBySettleDicTypeExist(SettleDicType settleDicType);

	/**
	 * 
	 * @方法说明：
	 * @时间：2016年10月25日 下午6:47:45
	 * @创建人：wangdong
	 */
	List<SettleDicType> findDistinctType();

	/**
	 * 
	 * @param settleDicItem 
	 * @方法说明：查询类型根据typeId和text去重
	 * @时间：2016年10月25日 下午7:54:19
	 * @创建人：wangdong
	 */
	List<SettleDicType> findDistinctTypeIdType(SettleDicItem settleDicItem);

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:10:31
	 * @创建人：wangdong
	 */
	SettleDicType get(Integer id);

}
