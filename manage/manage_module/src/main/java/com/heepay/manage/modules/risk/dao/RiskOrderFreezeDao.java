package com.heepay.manage.modules.risk.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskOrderFreeze;


/***
 * 
* 
* 描    述：订单冻结/解冻表
*
* 创 建 者：wangl
* 创建时间：  Nov 18, 20167:03:30 PM
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
public interface RiskOrderFreezeDao extends CrudDao<RiskOrderFreeze>{

	/**
	 * 
	 * @方法说明：添加冻结订单
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	int saveEntity(RiskOrderFreeze riskOrderFreeze);

	/**
	 * 
	 * @方法说明：订单冻结冻结和取消操作
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	int updateEntity(RiskOrderFreeze riskOrderFreeze);

	/**
	 * 
	 * @方法说明：根据id查询整个对象
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	RiskOrderFreeze getEntityById(int freezeId);

	/**
	 * 
	 * @方法说明：订单冻结解冻操作
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	int updateEntityToJd(RiskOrderFreeze riskOrderFreeze);

	int updateOrder(RiskOrderFreeze riskOrderFreeze);

}