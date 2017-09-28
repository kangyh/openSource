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
import com.heepay.manage.modules.risk.dao.RiskBlockInfoDao;
import com.heepay.manage.modules.risk.entity.RiskBlockInfo;

/**
 *
 * 描    述：阻断风险操作表Service
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
public class RiskBlockInfoService extends CrudService<RiskBlockInfoDao, RiskBlockInfo> {

	public RiskBlockInfo get(String id) {
		return super.get(id);
	}
	
	public List<RiskBlockInfo> findList(RiskBlockInfo riskBlockInfo) {
		return super.findList(riskBlockInfo);
	}
	
	public Page<RiskBlockInfo> findPage(Page<RiskBlockInfo> page, RiskBlockInfo riskBlockInfo) {
		return super.findPage(page, riskBlockInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskBlockInfo riskBlockInfo) {
		super.save(riskBlockInfo,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskBlockInfo riskBlockInfo) {
		super.delete(riskBlockInfo);
	}
	
}