/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.trial.entity.MerchantAccountDaily;
import com.heepay.manage.modules.trial.dao.MerchantAccountDailyDao;

/**
 *
 * 描    述：试算平衡-账户日汇总Service
 *
 * 创 建 者： @author 杨春龙
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
public class MerchantAccountDailyService extends CrudService<MerchantAccountDailyDao, MerchantAccountDaily> {
	@Autowired
	private MerchantAccountDailyDao merchantAccountDailyDao;
	
	public MerchantAccountDaily get(String id) {
		return super.get(id);
	}
	
	public List<MerchantAccountDaily> findList(MerchantAccountDaily merchantAccountDaily) {
		return super.findList(merchantAccountDaily);
	}
	
	public Page<MerchantAccountDaily> findPage(Page<MerchantAccountDaily> page, MerchantAccountDaily merchantAccountDaily) {
		return super.findPage(page, merchantAccountDaily);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantAccountDaily merchantAccountDaily) {
		super.save(merchantAccountDaily,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantAccountDaily merchantAccountDaily) {
		super.delete(merchantAccountDaily);
	}
	public List<MerchantAccountDaily> findByMerchantIds(Map<String,Object> map){
		return merchantAccountDailyDao.findByMerchantIds(map);
	}
	public List<MerchantAccountDaily> findSubjectFinanicalState(Map<String,Object> map){
		return merchantAccountDailyDao.findSubjectFinanicalState(map);
	}	
	
	public List<MerchantAccountDaily> findListBySubject(String subjectType){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("subjectType", subjectType);
		return merchantAccountDailyDao.findListBySubject(map);
	}	
	
	public Page<MerchantAccountDaily> findSumListBySubject(Page<MerchantAccountDaily> page, MerchantAccountDaily merchantAccountDaily){
		merchantAccountDaily.setPage(page);
		List<MerchantAccountDaily> list=merchantAccountDailyDao.findSumListBySubject(merchantAccountDaily);
		page.setList(list);
		return page;
	}
}