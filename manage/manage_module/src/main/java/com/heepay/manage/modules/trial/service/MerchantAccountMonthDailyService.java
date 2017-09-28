/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.trial.entity.MerchantAccountMonthDaily;
import com.heepay.manage.modules.trial.dao.MerchantAccountMonthDailyDao;

/**
 *
 * 描    述：商户出入金月汇总Service
 *
 * 创 建 者： @author yangcl
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
public class MerchantAccountMonthDailyService extends CrudService<MerchantAccountMonthDailyDao, MerchantAccountMonthDaily> {

	@Autowired
	private MerchantAccountMonthDailyDao merchantAccountMonthDailyDao;
	
	public MerchantAccountMonthDaily get(String id) {
		return super.get(id);
	}
	
	public List<MerchantAccountMonthDaily> findList(MerchantAccountMonthDaily merchantAccountMonthDaily) {
		return super.findList(merchantAccountMonthDaily);
	}
	
	public Page<MerchantAccountMonthDaily> findPage(Page<MerchantAccountMonthDaily> page, MerchantAccountMonthDaily merchantAccountMonthDaily) {
		return super.findPage(page, merchantAccountMonthDaily);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantAccountMonthDaily merchantAccountMonthDaily) {
		super.save(merchantAccountMonthDaily,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantAccountMonthDaily merchantAccountMonthDaily) {
		super.delete(merchantAccountMonthDaily);
	}
	
	@Transactional(readOnly = false)
	public int batchInsert(List<MerchantAccountMonthDaily> list) {
		return merchantAccountMonthDailyDao.batchInsert(list);
	}
	@Transactional(readOnly = false)
	public int deleteByCreateTime(Date createTime) {
		return merchantAccountMonthDailyDao.deleteByCreateTime(createTime);
	}
}