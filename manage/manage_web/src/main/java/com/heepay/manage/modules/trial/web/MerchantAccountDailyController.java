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

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.trial.entity.MerchantAccountDaily;
import com.heepay.manage.modules.trial.service.MerchantAccountDailyService;


/**
 *
 * 描    述：试算平衡-账户日汇总Controller
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
@RequestMapping(value = "${adminPath}/trial/merchantAccountDaily")
public class MerchantAccountDailyController extends BaseController {

	@Autowired
	private MerchantAccountDailyService merchantAccountDailyService;
	
	@ModelAttribute
	public MerchantAccountDaily get(@RequestParam(required=false) String id) {
		MerchantAccountDaily entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantAccountDailyService.get(id);
		}
		if (entity == null){
			entity = new MerchantAccountDaily();
		}
		return entity;
	}
	
	@RequiresPermissions("trial:merchantAccountDaily:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantAccountDaily merchantAccountDaily, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MerchantAccountDaily> page = merchantAccountDailyService.findPage(new Page<MerchantAccountDaily>(request, response), merchantAccountDaily); 
		model.addAttribute("page", page);
		return "modules/trial/merchantAccountDailyList";
	}

	@RequiresPermissions("trial:merchantAccountDaily:view")
	@RequestMapping(value = "form")
	public String form(MerchantAccountDaily merchantAccountDaily, Model model) {
		model.addAttribute("merchantAccountDaily", merchantAccountDaily);
		return "modules/trial/merchantAccountDailyForm";
	}

	@RequiresPermissions("trial:merchantAccountDaily:edit")
	@RequestMapping(value = "save")
	public String save(MerchantAccountDaily merchantAccountDaily, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantAccountDaily)){
			return form(merchantAccountDaily, model);
		}
		merchantAccountDailyService.save(merchantAccountDaily);
		addMessage(redirectAttributes, "保存试算平衡-账户日汇总成功");
		return "redirect:"+Global.getAdminPath()+"/trial/merchantAccountDaily/?repage";
	}

}