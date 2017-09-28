/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.entity.RiskIpbase;
import com.heepay.manage.modules.risk.dao.RiskIpbaseDao;

/**
 *
 * 描    述：风控ip库Service
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
public class RiskIpbaseService extends CrudService<RiskIpbaseDao, RiskIpbase> {

	public RiskIpbase get(String id) {
		return super.get(id);
	}
	
	public List<RiskIpbase> findList(RiskIpbase riskIpbase) {
		return super.findList(riskIpbase);
	}
	
	public Page<RiskIpbase> findPage(Page<RiskIpbase> page, RiskIpbase riskIpbase) {
		return super.findPage(page, riskIpbase);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskIpbase riskIpbase) {
		super.save(riskIpbase,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskIpbase riskIpbase) {
		super.delete(riskIpbase);
	}
	
}