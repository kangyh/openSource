package com.heepay.manage.modules.risk.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskOrderUnfreeze;


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
public interface RiskOrderUnfreezeDao extends CrudDao<RiskOrderUnfreeze>{


	int insert(RiskOrderUnfreeze riskOrderUnfreeze);

	
}