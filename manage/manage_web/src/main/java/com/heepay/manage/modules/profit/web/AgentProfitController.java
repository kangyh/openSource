/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.profit.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.google.common.collect.Lists;
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
import com.heepay.manage.modules.agent.entity.profit.AgentProfit;
import com.heepay.manage.modules.agent.service.profit.AgentProfitService;
import com.heepay.manage.modules.agent.entity.agent.AgentInfo;
import com.heepay.manage.modules.agent.service.agent.AgentInfoService;
import com.heepay.manage.modules.agent.service.agent.AgentInfoOtherService;

/**
 *
 * 描    述：代理商分润管理Controller
 *
 * 创 建 者： @author yangliang
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
@RequestMapping(value = "${adminPath}/agent/profit/agentProfit")
public class AgentProfitController extends BaseController {

	@Autowired
	private AgentProfitService agentProfitService;
	@Autowired
	private AgentInfoService agentInfoService;
	@Autowired
	private AgentInfoOtherService agentInfoOtherService;
	
	@ModelAttribute
	public AgentProfit get(@RequestParam(required=false) String id) {
		AgentProfit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = agentProfitService.get(id);
		}
		if (entity == null){
			entity = new AgentProfit();
		}
		return entity;
	}
	
	@RequiresPermissions("agent:profit:agentProfit:view")
	@RequestMapping(value = {"list", ""})
	public String list(AgentProfit agentProfit, HttpServletRequest request, HttpServletResponse response, Model model) {

		if(StringUtils.isNotEmpty(agentProfit.getAgentName()))
		{
			AgentInfo infowhere =new AgentInfo();
			infowhere.setAgentName(agentProfit.getAgentName());
			AgentInfo entity = agentInfoOtherService.getNameToAgent(infowhere);
//			agentProfit.setAgentId(entity.getMerchantId());
		}
		Page<AgentProfit> page = agentProfitService.findPage(new Page<AgentProfit>(request, response), agentProfit);
		List<AgentProfit> list= Lists.newArrayList();
		for(AgentProfit profit:page.getList()){
			//根据代理商代码查询代理商
			AgentInfo entity=null;
			entity = agentInfoService.get(profit.getAgentId());
//			profit.setAgentName(entity.getAgentName());
			list.add(profit);
		}
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/agent/profit/agentProfitList";
	}

	@RequiresPermissions("agent:profit:agentProfit:view")
	@RequestMapping(value = "form")
	public String form(AgentProfit agentProfit, Model model) {
		model.addAttribute("agentProfit", agentProfit);
		return "modules/agent/profit/agentProfitForm";
	}

	@RequiresPermissions("agent:profit:agentProfit:edit")
	@RequestMapping(value = "save")
	public String save(AgentProfit agentProfit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, agentProfit)){
			return form(agentProfit, model);
		}
		agentProfitService.save(agentProfit);
		addMessage(redirectAttributes, "保存生成分润结算表成功");
		return "redirect:"+Global.getAdminPath()+"/agent/profit/agentProfit/?repage";
	}
	
	@RequiresPermissions("agent:profit:agentProfit:edit")
	@RequestMapping(value = "delete")
	public String delete(AgentProfit agentProfit, RedirectAttributes redirectAttributes) {
		agentProfitService.delete(agentProfit);
		addMessage(redirectAttributes, "删除生成分润结算表成功");
		return "redirect:"+Global.getAdminPath()+"/agent/profit/agentProfit/?repage";
	}

}