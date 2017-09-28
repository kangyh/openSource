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
import com.heepay.manage.modules.payment.entity.AuthenticationRecord;
import com.heepay.manage.modules.payment.service.AuthenticationRecordService;


/**
 *
 * 描    述：鉴权记录Controller
 *
 * 创 建 者： @author ld
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
@RequestMapping(value = "${adminPath}/payment/authenticationRecord")
public class AuthenticationRecordController extends BaseController {

	@Autowired
	private AuthenticationRecordService authenticationRecordService;
	
	@ModelAttribute
	public AuthenticationRecord get(@RequestParam(required=false) String id) {
		AuthenticationRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = authenticationRecordService.get(id);
		}
		if (entity == null){
			entity = new AuthenticationRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:authenticationRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(AuthenticationRecord authenticationRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AuthenticationRecord> fpage = new Page<>(request, response);
		fpage.setOrderBy("pay_time desc");
		Page<AuthenticationRecord> page = authenticationRecordService.findPage(fpage, authenticationRecord);
		model.addAttribute("page", page);
		return "modules/payment/authenticationRecordList";
	}

	@RequiresPermissions("payment:authenticationRecord:view")
	@RequestMapping(value = "form")
	public String form(AuthenticationRecord authenticationRecord, Model model) {
		model.addAttribute("authenticationRecord", authenticationRecord);
		return "modules/payment/authenticationRecordForm";
	}

	@RequiresPermissions("payment:authenticationRecord:edit")
	@RequestMapping(value = "save")
	public String save(AuthenticationRecord authenticationRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, authenticationRecord)){
			return form(authenticationRecord, model);
		}
		authenticationRecordService.save(authenticationRecord);
		addMessage(redirectAttributes, "保存鉴权记录成功");
		return "redirect:"+Global.getAdminPath()+"/payment/authenticationRecord/?repage";
	}
	
	@RequiresPermissions("payment:authenticationRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(AuthenticationRecord authenticationRecord, RedirectAttributes redirectAttributes) {
		authenticationRecordService.delete(authenticationRecord);
		addMessage(redirectAttributes, "删除鉴权记录成功");
		return "redirect:"+Global.getAdminPath()+"/payment/authenticationRecord/?repage";
	}

}