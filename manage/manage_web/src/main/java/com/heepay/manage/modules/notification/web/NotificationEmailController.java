/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.notification.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.notification.entity.NotificationEmail;
import com.heepay.manage.modules.notification.service.NotificationEmailService;
import com.heepay.manage.modules.route.entity.BankInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 *
 * 描    述：邮件通知Controller
 *
 * 创 建 者： @author BHJ
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
@RequestMapping(value = "${adminPath}/notification/notificationEmail")
public class NotificationEmailController extends BaseController {

	@Autowired
	private NotificationEmailService notificationEmailService;
	
	@ModelAttribute
	public NotificationEmail get(@RequestParam(required=false) String id) {
		NotificationEmail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = notificationEmailService.get(id);
		}
		if (entity == null){
			entity = new NotificationEmail();
		}
		return entity;
	}
	
	@RequiresPermissions("notification:notificationEmail:view")
	@RequestMapping(value = {"list", ""})
	public String list(NotificationEmail notificationEmail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<NotificationEmail> page = notificationEmailService.findPage(new Page<NotificationEmail>(request, response), notificationEmail);
		//判断是否为空
		if(null!=page.getList() && !page.getList().isEmpty()) {
			page.setList(EnumView.notificationEmail(page.getList()));
		}
		model.addAttribute("page", page);
		return "modules/notification/notificationEmailList";
	}

	@RequiresPermissions("notification:notificationEmail:view")
	@RequestMapping(value = "form")
	public String form(NotificationEmail notificationEmail, Model model) {
		model.addAttribute("notificationEmail", notificationEmail);
		return "modules/notification/notificationEmailForm";
	}

	@RequiresPermissions("notification:notificationEmail:edit")
	@RequestMapping(value = "save")
	public String save(NotificationEmail notificationEmail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, notificationEmail)){
			return form(notificationEmail, model);
		}
		notificationEmailService.save(notificationEmail);
		addMessage(redirectAttributes, "保存邮件通知成功");
		return "redirect:"+Global.getAdminPath()+"/notification/notificationEmail/?repage";
	}
	
	@RequiresPermissions("notification:notificationEmail:edit")
	@RequestMapping(value = "delete")
	public String delete(NotificationEmail notificationEmail, RedirectAttributes redirectAttributes) {
		notificationEmailService.delete(notificationEmail);
		addMessage(redirectAttributes, "删除邮件通知成功");
		return "redirect:"+Global.getAdminPath()+"/notification/notificationEmail/?repage";
	}

	@RequiresPermissions("route:bankInfo:edit")
	@RequestMapping(value = "status")
	public String status(NotificationEmail notificationEmail,RedirectAttributes redirectAttributes) {
		notificationEmailService.status(notificationEmail);
		addMessage(redirectAttributes, "成功");
		return "redirect:"+Global.getAdminPath()+"/notification/notificationEmail/?repage";
	}
}