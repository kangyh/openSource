/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.trial.entity.TrialBalanceAccount;
import com.heepay.manage.modules.trial.service.TrialBalanceAccountService;


/**
 *
 * 描    述：试算平衡-账户维度Controller
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
@Controller
@RequestMapping(value = "${adminPath}/trial/trialBalanceAccount")
public class TrialBalanceAccountController extends BaseController {

	@Autowired
	private TrialBalanceAccountService trialBalanceAccountService;
	
	@ModelAttribute
	public TrialBalanceAccount get(@RequestParam(required=false) String id) {
		TrialBalanceAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = trialBalanceAccountService.get(id);
		}
		if (entity == null){
			entity = new TrialBalanceAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("trial:trialBalanceAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(TrialBalanceAccount trialBalanceAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TrialBalanceAccount> page = trialBalanceAccountService.findPage(new Page<TrialBalanceAccount>(request, response), trialBalanceAccount); 
		model.addAttribute("page", page); 
		return "modules/trial/trialBalanceAccountList";
	}

	@RequiresPermissions("trial:trialBalanceAccount:view")
	@RequestMapping(value = "form")
	public String form(TrialBalanceAccount trialBalanceAccount, Model model) {
		model.addAttribute("trialBalanceAccount", trialBalanceAccount);
		return "modules/trial/trialBalanceAccountForm";
	}

	@RequiresPermissions("trial:trialBalanceAccount:edit")
	@RequestMapping(value = "save")
	public String save(TrialBalanceAccount trialBalanceAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, trialBalanceAccount)){
			return form(trialBalanceAccount, model);
		}
		trialBalanceAccountService.save(trialBalanceAccount);
		addMessage(redirectAttributes, "保存试算平衡-账户维度成功");
		return "redirect:"+Global.getAdminPath()+"/trial/trialBalanceAccount/?repage";
	}
	
	@RequiresPermissions("trial:trialBalanceAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(TrialBalanceAccount trialBalanceAccount, RedirectAttributes redirectAttributes) {
		trialBalanceAccountService.delete(trialBalanceAccount);
		addMessage(redirectAttributes, "删除试算平衡-账户维度成功");
		return "redirect:"+Global.getAdminPath()+"/trial/trialBalanceAccount/?repage";
	}

}