package com.heepay.manage.modules.risk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.dao.RiskRuleDetailDao;
import com.heepay.manage.modules.risk.entity.RiskRuleDetail;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
/**
 * 
 *
 * 描    述：规则明细service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月29日 下午8:38:04
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
public class RiskRuleDetailService extends CrudService<RiskRuleDetailDao, RiskRuleDetail> {
	
	@Autowired
	private RiskRuleDetailDao riskRuleDetailDao;
	
	/**
	 * @方法说明：根据id获取规则明细信息
	 * @author wangdong
	 * @时间： 2016年11月18日17:27:49
	 */
	public RiskRuleDetail get(Long id) {
		return riskRuleDetailDao.get(id);
	}
	
	/**
	 * @方法说明：获取规则明细信息List
	 * @author wangdong
	 * @时间：2016年11月18日17:27:53
	 */
	public List<RiskRuleDetail> findList(RiskRuleDetail riskRuleDetail) {
		return super.findList(riskRuleDetail);
	}
	
	/**
	 * 
	 * @throws Exception 
	 * @方法说明：规则明细查询List
	 * @时间：2016年11月18日 下午5:24:50
	 * @创建人：wangdong
	 */
	public Model findRiskRuleDetailPage(Page<RiskRuleDetail> page, RiskRuleDetail riskRuleDetail, Model model) throws Exception {
		try {
			Page<RiskRuleDetail> pageRiskRuleDetail = findPage(page, riskRuleDetail);
			List<RiskRuleDetail> riskRuleDetailList = Lists.newArrayList();
			for(RiskRuleDetail rRD : pageRiskRuleDetail.getList()){
				
				riskRuleDetailList.add(rRD);
			}
			pageRiskRuleDetail.setList(riskRuleDetailList);
			
			CommonUtil.enumsShow(model,Constants.Clear.RISK);
			model.addAttribute("page", pageRiskRuleDetail);
			model.addAttribute("riskRuleDetail",riskRuleDetail);
			return model;
		} catch (Exception e) {
			logger.error("RiskRuleDetailService findRiskProductQuotaPage has a error:{规则明细查询List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
}
