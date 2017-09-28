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
import com.heepay.manage.modules.risk.dao.RiskRuleDao;
import com.heepay.manage.modules.risk.entity.RiskRule;
import com.heepay.manage.modules.settle.service.util.CommonUtil;

/**
 * 
 *
 * 描    述：规则Service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月29日 下午8:52:44
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
public class RiskRuleService extends CrudService<RiskRuleDao, RiskRule> {

	@Autowired
	private RiskRuleDao riskRuleDao;
	
	/**
	 * @方法说明：根据id获取规则信息
	 * @author wangdong
	 * @时间： 2016年11月18日17:27:49
	 */
	public RiskRule get(Long id) {
		return riskRuleDao.get(id);
	}
	
	/**
	 * @方法说明：获取规则信息List
	 * @author wangdong
	 * @时间：2016年11月18日17:27:53
	 */
	public List<RiskRule> findList(RiskRule riskRule) {
		return super.findList(riskRule);
	}
	
	/**
	 * 
	 * @throws Exception 
	 * @方法说明：规则查询List
	 * @时间：2016年11月18日 下午5:24:50
	 * @创建人：wangdong
	 */
	public Model findRiskRulePage(Page<RiskRule> page, RiskRule riskRule, Model model) throws Exception {
		try {
			Page<RiskRule> pageRiskRule = findPage(page, riskRule);
			List<RiskRule> riskRuleList = Lists.newArrayList();
			for(RiskRule rR : pageRiskRule.getList()){
				
				riskRuleList.add(rR);
			}
			pageRiskRule.setList(riskRuleList);
			
			CommonUtil.enumsShow(model,Constants.Clear.RISK);
			model.addAttribute("page", pageRiskRule);
			model.addAttribute("riskRule",riskRule);
			return model;
		} catch (Exception e) {
			logger.error("RiskRuleDetailService findRiskRulePage has a error:{规则查询List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：查询去重的规则
	 * @时间：2016年11月30日 上午10:17:09
	 * @创建人：wangdong
	 */
	public List<RiskRule> findDistructRule() {
		return riskRuleDao.findDistructRule();
	}
	
}
