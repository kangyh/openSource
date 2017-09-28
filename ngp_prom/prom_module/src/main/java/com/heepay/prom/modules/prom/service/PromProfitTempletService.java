/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromProfitTempletDao;
import com.heepay.prom.modules.prom.entity.PromProfitTemplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：分润比例模板管理Service
 *
 * 创 建 者： @author wangdong
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
public class PromProfitTempletService extends CrudService<PromProfitTempletDao, PromProfitTemplet> {
	
	@Autowired
	private PromProfitTempletDao promProfitTempletDao;
	
	public PromProfitTemplet get(String id) {
		return super.get(id);
	}
	
	public List<PromProfitTemplet> findList(PromProfitTemplet promProfitTemplet) {
		return super.findList(promProfitTemplet);
	}
	
	public Page<PromProfitTemplet> findPage(Page<PromProfitTemplet> page, PromProfitTemplet promProfitTemplet) {
		return super.findPage(page, promProfitTemplet);
	}
	
	@Transactional(readOnly = false)
	public void save(PromProfitTemplet promProfitTemplet) {
		super.save(promProfitTemplet,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(PromProfitTemplet promProfitTemplet) {
		super.delete(promProfitTemplet);
	}
	
	@Transactional(readOnly=false)
	public void updateEntity(PromProfitTemplet promProfitTemplet){
		 promProfitTempletDao.updateEntity(promProfitTemplet);
	}
	
}