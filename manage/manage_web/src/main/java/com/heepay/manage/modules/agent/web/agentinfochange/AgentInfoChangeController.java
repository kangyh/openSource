/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.agentinfochange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.heepay.agent.common.enums.AgentStatus;
import com.heepay.agent.common.enums.AgentType;
import com.heepay.manage.common.agentinfochange.AgentInfoChangeUtil;
import com.heepay.manage.modules.agent.entity.agent.AgentInfo;
import com.heepay.manage.modules.agent.service.agent.AgentInfoService;
import org.apache.commons.lang3.EnumUtils;
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
import com.heepay.manage.modules.agent.entity.agentinfochange.AgentInfoChange;
import com.heepay.manage.modules.agent.service.agentinfochange.AgentInfoChangeService;

import java.util.List;


/**
 *
 * 描    述：代理商信息变更表Controller
 *
 * 创 建 者： @author TangWei
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
@RequestMapping(value = "${adminPath}/agent/agentinfochange/agentInfoChange")
public class AgentInfoChangeController extends BaseController {

	@Autowired
	private AgentInfoChangeService agentInfoChangeService;

	@Autowired
	private AgentInfoService agentInfoService;
	
	@ModelAttribute
	public AgentInfoChange get(@RequestParam(required=false) String id) {
		AgentInfoChange entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = agentInfoChangeService.get(id);
		}
		if (entity == null){
			entity = new AgentInfoChange();
		}
		return entity;
	}
	
	@RequiresPermissions("agent:agentinfochange:agentInfoChange:view")
	@RequestMapping(value = {"list", ""})
	public String list(AgentInfoChange agentInfoChange, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AgentInfoChange> page = agentInfoChangeService.findPage(new Page<AgentInfoChange>(request, response), agentInfoChange); 
		model.addAttribute("page", page);
		return "modules/agent/agentinfochange/agentInfoChangeList";
	}

	@RequiresPermissions("agent:agentinfochange:agentInfoChange:view")
	@RequestMapping(value = {"checkList"})
	public String checkList(AgentInfoChange agentInfoChange, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AgentInfoChange> page = agentInfoChangeService.findCheckPage(new Page<AgentInfoChange>(request, response), agentInfoChange);
		//将类型的value展示出来
		List<AgentInfoChange> list = Lists.newArrayList();
		for(AgentInfoChange info:page.getList()){
			//考虑有可能是空指针
			AgentType agentType= EnumUtils.getEnum(AgentType.class,info.getAgentType());
			if(agentType != null){
				info.setAgentType(agentType.getValue());
			}
			list.add(info);
		}
		//把修改内容重新赋值给页面
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/agent/agentinfochange/agentInfoChangeCheckList";
	}

	@RequiresPermissions("agent:agentinfochange:agentInfoChange:view")
	@RequestMapping(value = "checkDetail")
	public String checkDetail(AgentInfoChange agentInfoChange, Model model) {
		AgentInfoChangeUtil.agentInfoChangeEnumValue(agentInfoChange);
		AgentInfoChangeUtil.changeAgentInfoChange(agentInfoChange);
		AgentStatus agentStatus=EnumUtils.getEnum(AgentStatus.class,agentInfoChange.getAgentChangeStatus());
		if(agentStatus != null){
			agentInfoChange.setAgentChangeStatus(agentStatus.getValue());
		}
		model.addAttribute("agentInfoChange", agentInfoChange);
		return "modules/agent/agentinfochange/agentInfoChangeCheckDetail";
	}

	@RequiresPermissions("agent:agentinfochange:agentInfoChange:edit")
	@RequestMapping(value = "doCheck")
	public String doCheck(AgentInfoChange agentInfoChange, Model model) {
		//审核通过;
		agentInfoChange.setAgentChangeType(agentInfoChange.getAgentChangeStatus());
		agentInfoChangeService.save(agentInfoChange);

		//将infochange的信息更新到info中；
		if("NORMAL".equals(agentInfoChange.getAgentChangeStatus())) {  //审核通过需要将
			agentInfoChange.setId(agentInfoChange.getAgentId());
			agentInfoService.updateInfoFromChange(agentInfoChange);
		}
		else{
			AgentInfo agentInfo =agentInfoService.get(agentInfoChange.getAgentId());
			agentInfo.setUpdatedStatus(agentInfoChange.getAgentChangeStatus());
			agentInfoService.save(agentInfo);

		}
		return "redirect:"+Global.getAdminPath()+"/agent/agentinfochange/agentInfoChange/checkList?repage";
	}

	@RequiresPermissions("agent:agentinfochange:agentInfoChange:view")
	@RequestMapping(value = "form")
	public String form(AgentInfoChange agentInfoChange, Model model) {
		model.addAttribute("agentInfoChange", agentInfoChange);
		return "modules/agent/agentinfochange/agentInfoChangeForm";
	}

	@RequiresPermissions("agent:agentinfochange:agentInfoChange:edit")
	@RequestMapping(value = "save")
	public String save(AgentInfoChange agentInfoChange, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, agentInfoChange)){
			return form(agentInfoChange, model);
		}
		agentInfoChangeService.save(agentInfoChange);
		addMessage(redirectAttributes, "保存代理商信息变更成功");
		return "redirect:"+Global.getAdminPath()+"/agent/agentinfochange/agentInfoChange/?repage";
	}
	
	@RequiresPermissions("agent:agentinfochange:agentInfoChange:edit")
	@RequestMapping(value = "delete")
	public String delete(AgentInfoChange agentInfoChange, RedirectAttributes redirectAttributes) {
		agentInfoChangeService.delete(agentInfoChange);
		addMessage(redirectAttributes, "删除代理商信息变更成功");
		return "redirect:"+Global.getAdminPath()+"/agent/agentinfochange/agentInfoChange/?repage";
	}

}