/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

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
import com.heepay.manage.modules.risk.entity.RiskChannelOrderConversionRatio;
import com.heepay.manage.modules.risk.service.RiskChannelOrderConversionRatioService;


/**
 *
 * 描    述：风控通道转化率Controller
 *
 * 创 建 者： @author xch
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
@RequestMapping(value = "${adminPath}/risk/riskChannelOrderConversionRatio")
public class RiskChannelOrderConversionRatioController extends BaseController {

	@Autowired
	private RiskChannelOrderConversionRatioService riskChannelOrderConversionRatioService;
	
	@ModelAttribute
	public RiskChannelOrderConversionRatio get(@RequestParam(required=false) String id) {
		RiskChannelOrderConversionRatio entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = riskChannelOrderConversionRatioService.get(id);
		}
		if (entity == null){
			entity = new RiskChannelOrderConversionRatio();
		}
		return entity;
	}
	
	@RequiresPermissions("risk:riskChannelOrderConversionRatio:view")
	@RequestMapping(value = {"list", ""})
	public String list(RiskChannelOrderConversionRatio riskChannelOrderConversionRatio, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page page1 = new Page<RiskChannelOrderConversionRatio>(request, response);
		page1.setOrderBy("create_time DESC ");
		Page<RiskChannelOrderConversionRatio> page = riskChannelOrderConversionRatioService.findPage(page1, riskChannelOrderConversionRatio);
		model.addAttribute("page", page);
		return "modules/riskManage/riskChannelOrderConversionRatioList";
	}

	@RequiresPermissions("risk:riskChannelOrderConversionRatio:view")
	@RequestMapping(value = "form")
	public String form(RiskChannelOrderConversionRatio riskChannelOrderConversionRatio, Model model) {
		model.addAttribute("riskChannelOrderConversionRatio", riskChannelOrderConversionRatio);
		return "modules/riskManage/riskChannelOrderConversionRatioForm";
	}

	@RequiresPermissions("risk:riskChannelOrderConversionRatio:edit")
	@RequestMapping(value = "save")
	public String save(RiskChannelOrderConversionRatio riskChannelOrderConversionRatio, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, riskChannelOrderConversionRatio)){
			return form(riskChannelOrderConversionRatio, model);
		}
		riskChannelOrderConversionRatioService.save(riskChannelOrderConversionRatio);
		addMessage(redirectAttributes, "保存风控通道转化率成功");
		return "redirect:"+Global.getAdminPath()+"/risk/riskChannelOrderConversionRatio/?repage";
	}
	
	@RequiresPermissions("risk:riskChannelOrderConversionRatio:edit")
	@RequestMapping(value = "delete")
	public String delete(RiskChannelOrderConversionRatio riskChannelOrderConversionRatio, RedirectAttributes redirectAttributes) {
		riskChannelOrderConversionRatioService.delete(riskChannelOrderConversionRatio);
		addMessage(redirectAttributes, "删除风控通道转化率成功");
		return "redirect:"+Global.getAdminPath()+"/risk/riskChannelOrderConversionRatio/?repage";
	}

}