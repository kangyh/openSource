/**
 *  
 */
package com.heepay.manage.modules.payment.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.UserCenterRecord;

/**
 * 
* 
* 描    述：个人用户体系Dao
*
* 创 建 者： yanxb  
* 创建时间： 2017年4月6日 上午10:30:55 
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
public interface UserCenterDao extends CrudDao<UserCenterRecord> {

	Page<UserCenterRecord> findChargePage(Page<UserCenterRecord> page, UserCenterRecord userCenterRecord);

	Page<UserCenterRecord> findWithdrawPage(Page<UserCenterRecord> page, UserCenterRecord userCenterRecord);

	Page<UserCenterRecord> findAllPage(Page<UserCenterRecord> page, UserCenterRecord userCenterRecord);
	
}