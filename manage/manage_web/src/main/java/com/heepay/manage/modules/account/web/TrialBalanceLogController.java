/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.web;

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
import com.heepay.manage.modules.account.entity.TrialBalanceLog;
import com.heepay.manage.modules.account.service.TrialBalanceLogService;


/**
 *
 * 描    述：试算平衡Controller
 *
 * 创 建 者： @author ycl
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
@RequestMapping(value = "${adminPath}/account/trialBalanceLog")
public class TrialBalanceLogController extends BaseController {

	@Autowired
	private TrialBalanceLogService trialBalanceLogService;
	
	@ModelAttribute
	public TrialBalanceLog get(@RequestParam(required=false) String id) {
		TrialBalanceLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = trialBalanceLogService.get(id);
		}
		if (entity == null){
			entity = new TrialBalanceLog();
		}
		return entity;
	}
	
	@RequiresPermissions("account:trialBalanceLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TrialBalanceLog trialBalanceLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TrialBalanceLog> page = trialBalanceLogService.findPage(new Page<TrialBalanceLog>(request, response), trialBalanceLog); 
		model.addAttribute("page", page);
		return "modules/account/trialBalanceLogList";
	}

	@RequiresPermissions("account:trialBalanceLog:view")
	@RequestMapping(value = "form")
	public String form(TrialBalanceLog trialBalanceLog, Model model) {
		model.addAttribute("trialBalanceLog", trialBalanceLog);
		return "modules/account/trialBalanceLogForm";
	}

	@RequiresPermissions("account:trialBalanceLog:edit")
	@RequestMapping(value = "save")
	public String save(TrialBalanceLog trialBalanceLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, trialBalanceLog)){
			return form(trialBalanceLog, model);
		}
		trialBalanceLogService.save(trialBalanceLog);
		addMessage(redirectAttributes, "保存试算平衡成功");
		return "redirect:"+Global.getAdminPath()+"/account/trialBalanceLog/?repage";
	}
	
	@RequiresPermissions("account:trialBalanceLog:edit")
	@RequestMapping(value = "delete")
	public String delete(TrialBalanceLog trialBalanceLog, RedirectAttributes redirectAttributes) {
		trialBalanceLogService.delete(trialBalanceLog);
		addMessage(redirectAttributes, "删除试算平衡成功");
		return "redirect:"+Global.getAdminPath()+"/account/trialBalanceLog/?repage";
	}

}