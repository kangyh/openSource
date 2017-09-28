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
import com.heepay.manage.modules.payment.entity.StatisticsWarning;
import com.heepay.manage.modules.payment.service.StatisticsWarningService;


/**
 *
 * 描    述：商户成功率监控Controller
 *
 * 创 建 者： @author tyq
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
@RequestMapping(value = "${adminPath}/payment/statisticsWarning")
public class StatisticsWarningController extends BaseController {

	@Autowired
	private StatisticsWarningService statisticsWarningService;
	
	@ModelAttribute
	public StatisticsWarning get(@RequestParam(required=false) String id) {
		StatisticsWarning entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = statisticsWarningService.get(id);
		}
		if (entity == null){
			entity = new StatisticsWarning();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:statisticsWarning:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsWarning statisticsWarning, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StatisticsWarning> page = statisticsWarningService.findPage(new Page<StatisticsWarning>(request, response), statisticsWarning); 
		model.addAttribute("page", page);
		return "modules/payment/statisticsWarningList";
	}

	@RequiresPermissions("payment:statisticsWarning:view")
	@RequestMapping(value = "form")
	public String form(StatisticsWarning statisticsWarning, Model model) {
		model.addAttribute("statisticsWarning", statisticsWarning);
		return "modules/payment/statisticsWarningForm";
	}

	//@RequiresPermissions("payment:statisticsWarning:edit")
	@RequestMapping(value = "save")
	public String save(StatisticsWarning statisticsWarning, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, statisticsWarning)){
			return form(statisticsWarning, model);
		}
		statisticsWarningService.save(statisticsWarning);
		addMessage(redirectAttributes, "保存商户成功率监控成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsWarning/?repage";
	}
	
	@RequiresPermissions("payment:statisticsWarning:edit")
	@RequestMapping(value = "delete")
	public String delete(StatisticsWarning statisticsWarning, RedirectAttributes redirectAttributes) {
		statisticsWarningService.delete(statisticsWarning);
		addMessage(redirectAttributes, "删除商户成功率监控成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsWarning/?repage";
	}

}