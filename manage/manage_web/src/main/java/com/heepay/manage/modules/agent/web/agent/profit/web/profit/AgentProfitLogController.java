/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.agent.profit.web.profit;

import com.google.common.collect.Lists;
import com.heepay.agent.common.enums.AgentFundTransType;
import com.heepay.agent.common.enums.AgentProfitStatus;
import com.heepay.agent.common.enums.AgentTransDirection;
import com.heepay.agent.rpc.thrift.AgentFundObject;
import com.heepay.agent.rpc.thrift.AgentProfitObject;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.agent.entity.profit.AgentProfit;
import com.heepay.manage.modules.agent.profit.entity.profit.AgentProfitLog;
import com.heepay.manage.modules.agent.profit.service.profit.AgentProfitLogService;
import com.heepay.manage.modules.agent.service.profit.AgentProfitService;
import com.heepay.manage.modules.agent.web.rpc.AgentFundThriftClient;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * 描    述：分润申请记录Controller
 *
 * 创 建 者： @author shixp
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
@RequestMapping(value = "${adminPath}/profit/profit/agentProfitLog")
public class AgentProfitLogController extends BaseController {

	@Autowired
	private AgentProfitLogService agentProfitLogService;

	@Autowired
	private AgentProfitService agentProfitService;

	@Autowired
	private AgentFundThriftClient agentFundThriftClient;
	
	@ModelAttribute
	public AgentProfitLog get(@RequestParam(required=false) String id) {
		AgentProfitLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = agentProfitLogService.get(id);
		}
		if (entity == null){
			entity = new AgentProfitLog();
		}
		return entity;
	}
	
	@RequiresPermissions("profit:profit:agentProfitLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(AgentProfitLog agentProfitLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AgentProfitLog> page = agentProfitLogService.findPage(new Page<AgentProfitLog>(request, response), agentProfitLog);
		List<AgentProfitStatus> apsList= EnumUtils.getEnumList(AgentProfitStatus.class);
		//AgentProfitStatus agentProfitStatus= EnumUtils.getEnum(AgentProfitStatus.class,"");

		List<AgentProfitLog> list= Lists.newArrayList();
		for(AgentProfitLog afl:page.getList()){
			//考虑有可能是空指针
			AgentProfitStatus aps=EnumUtils.getEnum(AgentProfitStatus.class,afl.getStatus());
			if(aps != null){
				afl.setStatus(aps.getValue());
			}
			afl.setInvoices(RandomUtil.getFastDfs(afl.getInvoices()));
			list.add(afl);
		}
		page.setList(list);
		model.addAttribute("page", page);
		model.addAttribute("agentProfitStatusList", apsList);
		return "modules/agent/profit/profit/agentProfitLogList";
	}

	@RequiresPermissions("profit:profit:agentProfitLog:view")
	@RequestMapping(value = "form")
	public String form(AgentProfitLog agentProfitLog, Model model) {
		List<AgentProfitStatus> apsList= EnumUtils.getEnumList(AgentProfitStatus.class);
		model.addAttribute("agentProfitLog", agentProfitLog);
		model.addAttribute("agentProfitStatusList", apsList);
		return "modules/agent/profit/profit/agentProfitLogForm";
	}

	@RequiresPermissions("profit:profit:agentProfitLog:edit")
	@RequestMapping(value = "save")
	public String save(AgentProfitLog agentProfitLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, agentProfitLog)){
			return form(agentProfitLog, model);
		}
		List<AgentProfitStatus> apsList= EnumUtils.getEnumList(AgentProfitStatus.class);
		model.addAttribute("agentProfitStatusList", apsList);
		agentProfitLogService.save(agentProfitLog);
		addMessage(redirectAttributes, "保存分润申请成功");
		return "redirect:"+Global.getAdminPath()+"/profit/profit/agentProfitLog/?repage";
	}
	
	@RequiresPermissions("profit:profit:agentProfitLog:edit")
	@RequestMapping(value = "delete")
	public String delete(AgentProfitLog agentProfitLog, RedirectAttributes redirectAttributes) {
		agentProfitLogService.delete(agentProfitLog);
		addMessage(redirectAttributes, "删除分润申请成功");
		return "redirect:"+Global.getAdminPath()+"/profit/profit/agentProfitLog/?repage";
	}

	@RequiresPermissions("profit:profit:agentProfitLog:view")
	@RequestMapping(value = {"verifyList"})
	public String verifyList(AgentProfitLog agentProfitLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AgentProfitLog> page = agentProfitLogService.findPage(new Page<AgentProfitLog>(request, response), agentProfitLog);
		List<AgentProfitStatus> apsList= EnumUtils.getEnumList(AgentProfitStatus.class);
		List<AgentProfitLog> list= Lists.newArrayList();
		for(AgentProfitLog afl:page.getList()){
			//考虑有可能是空指针
			AgentProfitStatus aps=EnumUtils.getEnum(AgentProfitStatus.class,afl.getStatus());
			if(aps != null){
				afl.setStatus(aps.getValue());
			}
			afl.setInvoices(RandomUtil.getFastDfs(afl.getInvoices()));
			list.add(afl);
		}
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();   //当前登录用户
		model.addAttribute("currentUserId", principal.getId());
		page.setList(list);
		model.addAttribute("agentProfitStatusList", apsList);
		model.addAttribute("page", page);
		return "modules/agent/profit/profit/agentProfitVerifyList";
	}

	@RequiresPermissions("profit:profit:agentProfitLog:edit")
	@RequestMapping(value = "verify")
	public String verify(AgentProfitLog agentProfitLog, RedirectAttributes redirectAttributes, Model model) throws TException{
		String handleStr="REJECT".equals(agentProfitLog.getStatus())?"驳回":"通过";
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();   //当前登录用户
		//复审通过的话fund表里面要存数据
		if("FINISH".equals(agentProfitLog.getStatus())){
			agentFundThriftClient.profitFund(agentProfitLog.getAgentProfitId().toString());
			agentProfitLog.setRechekUserId(principal.getId());
		}else if("RECHEK".equals(agentProfitLog.getStatus())){
			agentProfitLog.setInichkUserId(principal.getId());
		}else if("REJECT".equals(agentProfitLog.getStatus())){
			//审核拒绝的话把申请分润的钱退回到分润主表里
			AgentProfitLog agentProfitLogTemp = agentProfitLogService.get(agentProfitLog.getId());
			AgentProfit agentProfit = agentProfitService.get(agentProfitLog.getAgentProfitId().toString());
			agentProfit.setApplyAmount("0.0000");
			agentProfit.setAvaiApplyAmount(agentProfitLogTemp.getProfitAmount());
			agentProfitService.save(agentProfit);
		}
		agentProfitLogService.changeStatus(agentProfitLog);
		List<AgentProfitStatus> apsList= EnumUtils.getEnumList(AgentProfitStatus.class);
		model.addAttribute("agentProfitStatusList", apsList);
		addMessage(redirectAttributes, handleStr+"分润申请成功");
		return "redirect:"+Global.getAdminPath()+"/profit/profit/agentProfitLog/verifyList?repage";
	}
}