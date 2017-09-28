/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromBindingDao;
import com.heepay.prom.modules.prom.entity.PromBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：promService
 *
 * 创 建 者： @author wj
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
@Transactional(readOnly=false)
public class PromBindingService extends CrudService<PromBindingDao, PromBinding> {
	
	@Autowired
	private PromBindingDao promBindingDao;
	public PromBinding get(String id) {
		return super.get(id);
	}
	
	public List<PromBinding> findList(PromBinding promBinding) {
		return super.findList(promBinding);
	}
	
	public Page<PromBinding> findPage(Page<PromBinding> page, PromBinding promBinding) {
		return super.findPage(page, promBinding);
	}
	
	public void save(PromBinding promBinding) {
		super.save(promBinding,false);
	}
	
	public void delete(PromBinding promBinding) {
		super.delete(promBinding);
	}
	
	@Transactional(readOnly=false)
	public int update(PromBinding promBinding){
		return promBindingDao.updateEntity(promBinding);
	}
	
}