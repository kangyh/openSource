/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.MerchantAccountType;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * 描    述：账户管理Controller
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
@RequestMapping(value = "${adminPath}/account/interAccountQuery")
public class InterAccountController extends BaseController {

	@Autowired
	private MerchantAccountService merchantAccountService;
	
	@ModelAttribute
	public MerchantAccount get(@RequestParam(required=false) String id) {
		MerchantAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantAccountService.get(id);
		}
		if (entity == null){
			entity = new MerchantAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("account:interAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantAccount merchantAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtil.isBlank(merchantAccount.getType())){
			merchantAccount.setType(MerchantAccountType.INTERNAL_ACCOUNT.getValue());
		}
		if(merchantAccount.getSortOrder() == null){
			//默认按照创建时间降序
			merchantAccount.setSortOrder(SortOrderType.DESC.getValue());
		}
		
		String accountCodesHidden = merchantAccount.getAccountCodesHidden();
    List<String> accCodesList = Lists.newArrayList();
    if(StringUtil.notBlank(accountCodesHidden)){
      String[] accountCodes = accountCodesHidden.split(",");
      accCodesList = Arrays.asList(accountCodes);
    }
    merchantAccount.setAccCodes(accCodesList);
		Page<MerchantAccount> page = merchantAccountService.findPage(new Page<MerchantAccount>(request, response), merchantAccount);
		model.addAttribute("page", page);
		return "modules/account/interAccountList";
	}

	@RequiresPermissions("account:interAccount:view")
	@RequestMapping(value = "form")
	public String form(MerchantAccount merchantAccount, Model model) {
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/interAccountDetail";
	}

	@RequiresPermissions("account:interAccount:view")
	@RequestMapping(value = "toThaw")
	public String toThaw(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountThaw";
	}

	@RequiresPermissions("account:interAccount:view")
	@RequestMapping(value = "toFrezed")
	public String toFrezed(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountFrezed";
	}

	@RequiresPermissions("account:interAccount:view")
	@RequestMapping(value = "toEnable")
	public String toEnable(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountEnable";
	}

	@RequiresPermissions("account:interAccount:view")
	@RequestMapping(value = "toClosed")
	public String toClosed(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountClosed";
	}

	@RequiresPermissions("account:interAccount:view")
	@RequestMapping(value = "toFrezedBalanceAmount")
	public String toFrezedBalanceAmount(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountClosed";
	}

	@RequiresPermissions("account:interAccount:view")
	@RequestMapping(value = "toWhawBalanceAmount")
	public String toWhawBalanceAmount(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountClosed";
	}

	@RequestMapping(value = "frezed")
	public String frezed(MerchantAccount merchantAccount, Model model,RedirectAttributes redirectAttributes) {
		merchantAccount.setStatus(MerchantStatus.FREEZED.getValue());
		merchantAccountService.updateStatus(merchantAccount);
		addMessage(redirectAttributes, "账户冻结成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountQuery/?repage";
	}

	@RequestMapping(value = "enable")
	public String enable(MerchantAccount merchantAccount, Model model,RedirectAttributes redirectAttributes) {
		merchantAccount.setStatus(MerchantStatus.NORMAL.getValue());
		merchantAccountService.updateStatus(merchantAccount);
		addMessage(redirectAttributes, "账户启用成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountQuery/?repage";
	}
	@RequestMapping(value = "thaw")
	public String thaw(MerchantAccount merchantAccount, Model model,RedirectAttributes redirectAttributes) {
		merchantAccount.setStatus(MerchantStatus.NORMAL.getValue());
		merchantAccountService.updateStatus(merchantAccount);
		addMessage(redirectAttributes, "账户解冻成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountQuery/?repage";
	}

	@RequestMapping(value = "closed")
	public String closed(MerchantAccount merchantAccount, Model model,RedirectAttributes redirectAttributes) {
		merchantAccount.setStatus(MerchantStatus.CLOSED.getValue());
		merchantAccountService.updateStatus(merchantAccount);
		addMessage(redirectAttributes, "账户关闭成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountQuery/?repage";
	}

	@RequiresPermissions("account:interAccount:edit")
	@RequestMapping(value = "save")
	public String save(MerchantAccount merchantAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantAccount)){
			return form(merchantAccount, model);
		}
		merchantAccountService.save(merchantAccount);
		addMessage(redirectAttributes, "保存账户管理成功");
		return "redirect:"+Global.getAdminPath()+"/account/merchantAccount/?repage";
	}
	
	@RequiresPermissions("account:interAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantAccount merchantAccount, RedirectAttributes redirectAttributes) {
		merchantAccountService.delete(merchantAccount);
		addMessage(redirectAttributes, "删除账户管理成功");
		return "redirect:"+Global.getAdminPath()+"/account/merchantAccount/?repage";
	}

}