/**
 *  
 */
package com.heepay.manage.modules.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.UserCenterDao;
import com.heepay.manage.modules.payment.entity.UserCenterRecord;

/**
 * 
* 
* 描    述：个人用户体系
*
* 创 建 者： yanxb  
* 创建时间： 2017年4月6日 上午10:27:12 
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
@Service
@Transactional(readOnly = true)
public class UserCenterService extends CrudService<UserCenterDao, UserCenterRecord> {

	@Autowired
	UserCenterDao userCenterDao;

	public UserCenterRecord get(String id) {
		return super.get(id);
	}
	public Page<UserCenterRecord> findPage(Page<UserCenterRecord> page, UserCenterRecord userCenterRecord) {
		return super.findPage(page, userCenterRecord);
	}
	public Page<UserCenterRecord> findChargePage(Page<UserCenterRecord> page, UserCenterRecord userCenterRecord) {
		return userCenterDao.findChargePage(page,userCenterRecord);
	}
	public Page<UserCenterRecord> findWithdrawPage(Page<UserCenterRecord> page, UserCenterRecord userCenterRecord) {
		return userCenterDao.findWithdrawPage(page,userCenterRecord);
	}
	public Page<UserCenterRecord> findAllPage(Page<UserCenterRecord> page, UserCenterRecord userCenterRecord) {
		return userCenterDao.findAllPage(page,userCenterRecord);
	}
	
}