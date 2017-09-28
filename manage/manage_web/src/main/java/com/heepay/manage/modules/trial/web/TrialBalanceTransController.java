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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.alibaba.fastjson.JSONObject;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.trial.entity.TrialBalanceTrans;
import com.heepay.manage.modules.trial.service.TrialBalanceTransService;


/**
 *
 * 描    述：试算平衡-交易维度Controller
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
@RequestMapping(value = "${adminPath}/trial/trialBalanceTrans")
public class TrialBalanceTransController extends BaseController {

	@Autowired
	private TrialBalanceTransService trialBalanceTransService;
	
	@ModelAttribute
	public TrialBalanceTrans get(@RequestParam(required=false) String id) {
		TrialBalanceTrans entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = trialBalanceTransService.get(id);
		}
		if (entity == null){
			entity = new TrialBalanceTrans();
		}
		return entity;
	}
	
	@RequiresPermissions("trial:trialBalanceTrans:view")
	@RequestMapping(value = {"list", ""})
	public String list(TrialBalanceTrans trialBalanceTrans, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TrialBalanceTrans> page = trialBalanceTransService.findPage(new Page<TrialBalanceTrans>(request, response), trialBalanceTrans); 
		model.addAttribute("page", page);
		page.setOrderBy("create_time desc");
		return "modules/trial/trialBalanceTransList";
	}

	@RequiresPermissions("trial:trialBalanceTrans:view")
	@RequestMapping(value = "form")
	public String form(TrialBalanceTrans trialBalanceTrans, Model model) {
		model.addAttribute("trialBalanceTrans", trialBalanceTrans);
		return "modules/trial/trialBalanceTransForm";
	}

	@RequiresPermissions("trial:trialBalanceTrans:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(TrialBalanceTrans trialBalanceTrans, Model model, RedirectAttributes redirectAttributes) {

		trialBalanceTransService.save(trialBalanceTrans);
		JSONObject json=new JSONObject();
		json.put("status", 1000);
		return json.toJSONString();
	}
	
	@RequiresPermissions("trial:trialBalanceTrans:edit")
	@RequestMapping(value = "delete")
	public String delete(TrialBalanceTrans trialBalanceTrans, RedirectAttributes redirectAttributes) {
		trialBalanceTransService.delete(trialBalanceTrans);
		addMessage(redirectAttributes, "删除试算平衡-交易维度成功");
		return "redirect:"+Global.getAdminPath()+"/trial/trialBalanceTrans/?repage";
	}

}