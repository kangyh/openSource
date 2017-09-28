/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.account.entity.InternalAccountMpr;
import com.heepay.manage.modules.account.rpc.AccountOpClient;
import com.heepay.manage.modules.account.rpc.IAccountClient;
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
import com.heepay.manage.modules.account.service.InternalAccountMprService;


/**
 *
 * 描    述：内部账户映射Controller
 *
 * 创 建 者： @author zjx
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
@RequestMapping(value = "${adminPath}/account/internalAccountMpr")
public class InternalAccountMprController extends BaseController {

	@Autowired
	private InternalAccountMprService internalAccountMprService;
	

	@Autowired
	private AccountOpClient accountOpClient;

	@Autowired
	private IAccountClient iAccountClient;

	@ModelAttribute
	public InternalAccountMpr get(@RequestParam(required=false) String id) {
		InternalAccountMpr entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = internalAccountMprService.get(id);
		}
		if (entity == null){
			entity = new InternalAccountMpr();
		}
		return entity;
	}
	
	@RequiresPermissions("account:internalAccountMpr:view")
	@RequestMapping(value = {"list", ""})
	public String list(InternalAccountMpr internalAccountMpr, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InternalAccountMpr> page = internalAccountMprService.findPage(new Page<InternalAccountMpr>(request, response), internalAccountMpr);
		model.addAttribute("page", page);
		return "modules/account/internalAccountMprList";
	}

	@RequiresPermissions("account:internalAccountMpr:view")
	@RequestMapping(value = "form")
	public String form(InternalAccountMpr internalAccountMpr, Model model) {
		model.addAttribute("internalAccount", internalAccountMpr);
		return "modules/account/internalAccountMprForm";
	}

	@RequiresPermissions("account:internalAccountMpr:edit")
	@RequestMapping(value = "save")
	public String save(InternalAccountMpr internalAccountMpr, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, internalAccountMpr)){
			return form(internalAccountMpr, model);
		}
		internalAccountMprService.save(internalAccountMpr);
		accountOpClient.refRefreshInterAccount();
		iAccountClient.refRefreshInterAccount();
		addMessage(redirectAttributes, "保存内部账户映射成功");
		return "redirect:"+Global.getAdminPath()+"/account/internalAccountMpr/?repage";
	}
	
	@RequiresPermissions("account:internalAccountMpr:edit")
	@RequestMapping(value = "delete")
	public String delete(InternalAccountMpr internalAccountMpr, RedirectAttributes redirectAttributes) {
		internalAccountMprService.delete(internalAccountMpr);
		accountOpClient.refRefreshInterAccount();
		iAccountClient.refRefreshInterAccount();
		addMessage(redirectAttributes, "删除内部账户映射成功");
		return "redirect:"+Global.getAdminPath()+"/account/internalAccountMpr/?repage";
	}

}