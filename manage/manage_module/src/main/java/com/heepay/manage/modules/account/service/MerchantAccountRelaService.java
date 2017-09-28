/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.account.entity.MerchantAccountRela;
import com.heepay.manage.modules.account.dao.MerchantAccountRelaDao;

/**
 *
 * 描    述：账务关联账户Service
 *
 * 创 建 者： @author zjx
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
public class MerchantAccountRelaService extends CrudService<MerchantAccountRelaDao, MerchantAccountRela> {

	public MerchantAccountRela get(String id) {
		return super.get(id);
	}
	
	public List<MerchantAccountRela> findList(MerchantAccountRela merchantAccountRela) {
		return super.findList(merchantAccountRela);
	}
	
	public Page<MerchantAccountRela> findPage(Page<MerchantAccountRela> page, MerchantAccountRela merchantAccountRela) {
		return super.findPage(page, merchantAccountRela);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantAccountRela merchantAccountRela) {
		super.save(merchantAccountRela,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantAccountRela merchantAccountRela) {
		super.delete(merchantAccountRela);
	}
	
}