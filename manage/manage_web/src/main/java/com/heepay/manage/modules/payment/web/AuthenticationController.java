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
import com.heepay.manage.modules.payment.entity.Authentication;
import com.heepay.manage.modules.payment.service.AuthenticationService;


/**
 *
 * 描    述：鉴权信息Controller
 *
 * 创 建 者： @author zc
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
@RequestMapping(value = "${adminPath}/payment/authentication")
public class AuthenticationController extends BaseController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@ModelAttribute
	public Authentication get(@RequestParam(required=false) String id) {
		Authentication entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = authenticationService.get(id);
		}
		if (entity == null){
			entity = new Authentication();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:authentication:view")
	@RequestMapping(value = {"list", ""})
	public String list(Authentication authentication, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Authentication> page = authenticationService.findPage(new Page<Authentication>(request, response), authentication); 
		model.addAttribute("page", page);
		return "modules/payment/authenticationList";
	}

	@RequiresPermissions("payment:authentication:view")
	@RequestMapping(value = "form")
	public String form(Authentication authentication, Model model) {
		model.addAttribute("authentication", authentication);
		return "modules/payment/authenticationForm";
	}

	@RequiresPermissions("payment:authentication:edit")
	@RequestMapping(value = "save")
	public String save(Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, authentication)){
			return form(authentication, model);
		}
		authenticationService.save(authentication);
		addMessage(redirectAttributes, "保存鉴权信息成功");
		return "redirect:"+Global.getAdminPath()+"/payment/authentication/?repage";
	}
	
	@RequiresPermissions("payment:authentication:edit")
	@RequestMapping(value = "delete")
	public String delete(Authentication authentication, RedirectAttributes redirectAttributes) {
		authenticationService.delete(authentication);
		addMessage(redirectAttributes, "删除鉴权信息成功");
		return "redirect:"+Global.getAdminPath()+"/payment/authentication/?repage";
	}

}