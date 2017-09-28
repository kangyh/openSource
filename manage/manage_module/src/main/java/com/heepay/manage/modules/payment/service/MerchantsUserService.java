/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.MerchantsUserDao;
import com.heepay.manage.modules.merchant.vo.MerchantsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述： 商户用户Service
 *
 * 创 建 者： liudh
 * 创建时间： 2017.06.30 10:14:00
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
public class MerchantsUserService extends CrudService<MerchantsUserDao, MerchantsUser> {

	public MerchantsUser get(String id) {
		return super.get(id);
	}
	
	public List<MerchantsUser> findList(MerchantsUser merchantsUser) {
		return super.findList(merchantsUser);
	}
	
	public Page<MerchantsUser> findPage(Page<MerchantsUser> page, MerchantsUser merchantsUser) {
		return super.findPage(page, merchantsUser);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantsUser merchantsUser) {
		super.save(merchantsUser,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantsUser merchantsUser) {
		super.delete(merchantsUser);
	}
	
}