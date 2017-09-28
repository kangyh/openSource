/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.service;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.dao.RiskIncomeQuotaDao;
import com.heepay.manage.modules.risk.entity.RiskIncomeQuota;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

/**
 *
 * 描    述：商户出入金限额Service
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
public class RiskIncomeQuotaService extends CrudService<RiskIncomeQuotaDao, RiskIncomeQuota> {

	@Autowired
	private RiskIncomeQuotaDao riskIncomeQuotaDao;

	public RiskIncomeQuota get(String id) {
		return super.get(id);
	}
	
	public List<RiskIncomeQuota> findList(RiskIncomeQuota riskIncomeQuota) {
		return super.findList(riskIncomeQuota);
	}
	
	public Page<RiskIncomeQuota> findPage(Page<RiskIncomeQuota> page, RiskIncomeQuota riskIncomeQuota) {
		return super.findPage(page, riskIncomeQuota);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskIncomeQuota riskIncomeQuota) {
		if (null != riskIncomeQuota.getQuotaId()){
			riskIncomeQuota.setUpdateAuthor(UserUtils.getUser().getName());
			super.update(riskIncomeQuota,false);
		}else{
			riskIncomeQuota.setCreateAuthor(UserUtils.getUser().getName());
			super.save(riskIncomeQuota,false);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskIncomeQuota riskIncomeQuota) {
		super.delete(riskIncomeQuota);
	}

    public Model findRiskIncomeQuotaPage(Page<RiskIncomeQuota> page, RiskIncomeQuota riskIncomeQuota, Model model) throws Exception{
		try {
			Page<RiskIncomeQuota> pageRiskIncomeQuota = findPage(page, riskIncomeQuota);
			List<RiskIncomeQuota> infoList = Lists.newArrayList();
			//循环翻译部分字段
			for (RiskIncomeQuota quota : pageRiskIncomeQuota.getList()) {
				if (StringUtils.isNotBlank(quota.getIncomeDirection())) {
					quota.setIncomeDirection(StringUtils.equals(quota.getIncomeDirection(), "in") ? "入金" : "出金");
				}
				if (StringUtils.isNotBlank(quota.getStatus())) {
					quota.setStatus(BillingBecomeEffect.labelOf(quota.getStatus()));
				}
				infoList.add(quota);
			}
			pageRiskIncomeQuota.setList(infoList);

			model.addAttribute("page", pageRiskIncomeQuota);
			model.addAttribute("riskIncomeQuota",riskIncomeQuota);
			return model;
		} catch (Exception e) {
			logger.error("SettleIncomeInfoService findSettleIncomeInfoPage has a error:{查询出入金限额list出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
    }

	public Integer findExistInfo(RiskIncomeQuota riskIncomeQuota) {
		return riskIncomeQuotaDao.findExistInfo(riskIncomeQuota);
	}
}