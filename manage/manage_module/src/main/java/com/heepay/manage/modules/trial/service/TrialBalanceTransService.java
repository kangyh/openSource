/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.trial.entity.TrialBalanceTrans;
import com.heepay.manage.modules.trial.dao.TrialBalanceTransDao;

/**
 *
 * 描    述：试算平衡-交易维度Service
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
public class TrialBalanceTransService extends CrudService<TrialBalanceTransDao, TrialBalanceTrans> {

  @Autowired
  private TrialBalanceTransDao trialBalanceTransDao;
  
	public TrialBalanceTrans get(String id) {
		return super.get(id);
	}
	
	public List<TrialBalanceTrans> findList(TrialBalanceTrans trialBalanceTrans) {
		return super.findList(trialBalanceTrans);
	}
	
	public Page<TrialBalanceTrans> findPage(Page<TrialBalanceTrans> page, TrialBalanceTrans trialBalanceTrans) {
		return super.findPage(page, trialBalanceTrans);
	}
	
	@Transactional(readOnly = false)
	public void save(TrialBalanceTrans trialBalanceTrans) {
		super.save(trialBalanceTrans,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(TrialBalanceTrans trialBalanceTrans) {
		super.delete(trialBalanceTrans);
	}
	
	@Transactional(readOnly = false)
	public int updateTrialBalanceTrans(TrialBalanceTrans trialBalanceTrans){
	  return trialBalanceTransDao.updateTrialBalanceTrans(trialBalanceTrans);
	}
	
	
}