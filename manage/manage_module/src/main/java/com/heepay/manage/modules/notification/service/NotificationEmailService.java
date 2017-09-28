/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.notification.service;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.notification.dao.NotificationEmailDao;
import com.heepay.manage.modules.notification.entity.NotificationEmail;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * 描    述：邮件通知Service
 *
 * 创 建 者： @author BHJ
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
@Service
@Transactional(readOnly = true)
public class NotificationEmailService extends CrudService<NotificationEmailDao, NotificationEmail> {

	@Autowired
	private NotificationEmailDao notificationEmailDao;

	public NotificationEmail get(String id) {
		return super.get(id);
	}
	
	public List<NotificationEmail> findList(NotificationEmail notificationEmail) {
		return super.findList(notificationEmail);
	}
	
	public Page<NotificationEmail> findPage(Page<NotificationEmail> page, NotificationEmail notificationEmail) {
		return super.findPage(page, notificationEmail);
	}
	
	@Transactional(readOnly = false)
	public void save(NotificationEmail notificationEmail) {
		User user = UserUtils.getUser();
		// 做更新
		if(StringUtils.isNotBlank(notificationEmail.getId())){
			notificationEmail.setUpdateTime(new Date());
			notificationEmail.setUpdateUser(user.getId());
		}else{
			//做插入
			notificationEmail.setCreateTime(new Date());
			notificationEmail.setCreateUser(user.getId());
		}
		super.save(notificationEmail,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(NotificationEmail notificationEmail) {
		super.delete(notificationEmail);
	}

	@Transactional(readOnly = false)
	public void status(NotificationEmail notificationEmail) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			notificationEmail.setUpdateTime(new Date());
			notificationEmail.setUpdateUser(user.getId());
		}
		notificationEmailDao.status(notificationEmail);
	}

	@Transactional(readOnly = false)
	public List<String> queryList(){
		return notificationEmailDao.queryList();
	}
}