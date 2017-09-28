/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

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
import com.heepay.manage.modules.payment.entity.ElectronicsSealAccount;
import com.heepay.manage.modules.payment.service.ElectronicsSealAccountService;


/**
 *
 * 描    述：签宝账户管理Controller
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
@RequestMapping(value = "${adminPath}/payment/electronicsSealAccount")
public class ElectronicsSealAccountController extends BaseController {

	@Autowired
	private ElectronicsSealAccountService electronicsSealAccountService;
	
	@ModelAttribute
	public ElectronicsSealAccount get(@RequestParam(required=false) String id) {
		ElectronicsSealAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = electronicsSealAccountService.get(id);
		}
		if (entity == null){
			entity = new ElectronicsSealAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:electronicsSealAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(ElectronicsSealAccount electronicsSealAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ElectronicsSealAccount> page = electronicsSealAccountService.findPage(new Page<ElectronicsSealAccount>(request, response), electronicsSealAccount); 
		model.addAttribute("page", page);
		return "modules/payment/electronicsSealAccountList";
	}

	@RequiresPermissions("payment:electronicsSealAccount:view")
	@RequestMapping(value = "form")
	public String form(ElectronicsSealAccount electronicsSealAccount, Model model) {
		model.addAttribute("electronicsSealAccount", electronicsSealAccount);
		return "modules/payment/electronicsSealAccountForm";
	}

	@RequiresPermissions("payment:electronicsSealAccount:edit")
	@RequestMapping(value = "save")
	public String save(ElectronicsSealAccount electronicsSealAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, electronicsSealAccount)){
			return form(electronicsSealAccount, model);
		}
		electronicsSealAccountService.save(electronicsSealAccount);
		addMessage(redirectAttributes, "保存签宝账户管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/electronicsSealAccount/?repage";
	}
	
	@RequiresPermissions("payment:electronicsSealAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(ElectronicsSealAccount electronicsSealAccount, RedirectAttributes redirectAttributes) {
		electronicsSealAccountService.delete(electronicsSealAccount);
		addMessage(redirectAttributes, "删除签宝账户管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/electronicsSealAccount/?repage";
	}

}