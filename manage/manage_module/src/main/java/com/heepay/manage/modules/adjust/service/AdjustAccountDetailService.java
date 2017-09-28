/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.adjust.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.adjust.entity.AdjustAccountDetail;
import com.heepay.manage.modules.adjust.dao.AdjustAccountDetailDao;

/**
 *
 * 描    述：调账明细Service
 *
 * 创 建 者： @author 王亚洪
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
public class AdjustAccountDetailService extends CrudService<AdjustAccountDetailDao, AdjustAccountDetail> {

  @Autowired
  private AdjustAccountDetailDao adjustAccountDetailDao;
  
	public AdjustAccountDetail get(String id) {
		return super.get(id);
	}
	
	public List<AdjustAccountDetail> findList(AdjustAccountDetail adjustAccountDetail) {
		return super.findList(adjustAccountDetail);
	}
	
	public Page<AdjustAccountDetail> findPage(Page<AdjustAccountDetail> page, AdjustAccountDetail adjustAccountDetail) {
		return super.findPage(page, adjustAccountDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(AdjustAccountDetail adjustAccountDetail) {
		super.save(adjustAccountDetail,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdjustAccountDetail adjustAccountDetail) {
		super.delete(adjustAccountDetail);
	}
	
	
	public List<AdjustAccountDetail> getListByAdjustId(Long adjustId){
	  return adjustAccountDetailDao.getListByAdjustId(adjustId);
	}
	
}