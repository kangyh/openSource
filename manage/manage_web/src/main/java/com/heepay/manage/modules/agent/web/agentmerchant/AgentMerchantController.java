/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.agentmerchant;

import com.google.common.collect.Lists;
import com.heepay.agent.common.enums.AgentMerchantStatus;
import com.heepay.agent.common.enums.BankAccountType;
import com.heepay.agent.common.enums.BusiType;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.MerchantType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.agent.entity.agentmerchant.AgentMerchant;
import com.heepay.manage.modules.agent.service.agentmerchant.AgentMerchantService;
import org.apache.commons.lang3.EnumUtils;
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
import java.util.List;


/**
 *
 * 描    述：代理商商户功能Controller
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
@RequestMapping(value = "${adminPath}/agent/agentmerchant/agentMerchant")
public class AgentMerchantController extends BaseController {

	@Autowired
	private AgentMerchantService agentMerchantService;
	
	@ModelAttribute
	public AgentMerchant get(@RequestParam(required=false) String id) {
		AgentMerchant entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = agentMerchantService.get(id);
		}
		if (entity == null){
			entity = new AgentMerchant();
		}
		return entity;
	}
	
	@RequiresPermissions("agent:agentmerchant:agentMerchant:view")
	@RequestMapping(value = {"list", ""})
	public String list(AgentMerchant agentMerchant, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AgentMerchant> page = new Page<AgentMerchant>(request, response);
		//page.setOrderBy(" a.update_time desc");
		page = agentMerchantService.findPage(page, agentMerchant);
		List<AgentMerchantStatus> agentMerchantStatusList = EnumUtils.getEnumList(AgentMerchantStatus.class);
		List<AgentMerchant> list= Lists.newArrayList();
		for(AgentMerchant info:page.getList()){
			//考虑有可能是空指针
			AgentMerchantStatus agentMerchantStatus = EnumUtils.getEnum(AgentMerchantStatus.class,info.getAgentMerchantStatus());
			MerchantType merchantType = EnumUtils.getEnum(MerchantType.class,info.getMerchantType());
			if(agentMerchantStatus!=null){
				info.setAgentMerchantStatus(agentMerchantStatus.getValue());
			}
			if(merchantType!=null){
				info.setMerchantType(merchantType.getContent());
			}
			list.add(info);
		}
		//把修改内容重新赋值给页面
		page.setList(list);
		model.addAttribute("page", page);
		model.addAttribute("agentMerchantStatusList", agentMerchantStatusList);
		return "modules/agent/agentmerchant/agentMerchantList";
	}

	@RequiresPermissions("agent:agentmerchant:agentMerchant:view")
	@RequestMapping(value = "form")
	public String form(AgentMerchant agentMerchant, Model model) {
		model.addAttribute("agentMerchant", agentMerchant);
		return "modules/agent/agentmerchant/agentMerchantForm";
	}

	@RequiresPermissions("agent:agentmerchant:agentMerchant:view")
	@RequestMapping(value = "detail")
	public String detail(AgentMerchant agentMerchant, Model model) {
		if(agentMerchant.getAgentMerchantStatus()!=null){
			agentMerchant.setAgentMerchantStatus(EnumUtils.getEnum(AgentMerchantStatus.class,agentMerchant.getAgentMerchantStatus()).getValue());
		}
		if(agentMerchant.getMerchantType()!=null){
			agentMerchant.setMerchantType(EnumUtils.getEnum(MerchantType.class,agentMerchant.getMerchantType()).getContent());
		}
		BusiType busiType = EnumUtils.getEnum(BusiType.class, agentMerchant.getBusiType());
		if (busiType != null) {
			agentMerchant.setBusiType(busiType.getValue());
		}
		if(agentMerchant.getBankAccountType()!=null){
			agentMerchant.setBankAccountType(EnumUtils.getEnum(BankAccountType.class,agentMerchant.getBankAccountType()).getValue());
		}
		if (StringUtils.isNotBlank(agentMerchant.getIdcardFaceImage())) {
			agentMerchant.setIdcardFaceImage(RandomUtil.getFastDfs(agentMerchant.getIdcardFaceImage()));
		}
		if (StringUtils.isNotBlank(agentMerchant.getIdcardBackImage())) {
			agentMerchant.setIdcardBackImage(RandomUtil.getFastDfs(agentMerchant.getIdcardBackImage()));
		}
		if (StringUtils.isNotBlank(agentMerchant.getAgreementFile())) {
			agentMerchant.setAgreementFile(RandomUtil.getFastDfs(agentMerchant.getAgreementFile()));
		}
		if (StringUtils.isNotBlank(agentMerchant.getBusiImage())) {
			agentMerchant.setBusiImage(RandomUtil.getFastDfs(agentMerchant.getBusiImage()));
		}
		if (StringUtils.isNotBlank(agentMerchant.getTaxRegImage())) {
			agentMerchant.setTaxRegImage(RandomUtil.getFastDfs(agentMerchant.getTaxRegImage()));
		}
		if (StringUtils.isNotBlank(agentMerchant.getOrgInstImage())) {
			agentMerchant.setOrgInstImage(RandomUtil.getFastDfs(agentMerchant.getOrgInstImage()));
		}
		if (StringUtils.isNotBlank(agentMerchant.getOtherGenaImage())) {
			agentMerchant.setOtherGenaImage(RandomUtil.getFastDfs(agentMerchant.getOtherGenaImage()));
		}
		model.addAttribute("agentMerchant", agentMerchant);
		return "modules/agent/agentmerchant/agentMerchantDetail";
	}

	@RequiresPermissions("agent:agentmerchant:agentMerchant:edit")
	@RequestMapping(value = "save")
	public String save(AgentMerchant agentMerchant, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, agentMerchant)){
			return form(agentMerchant, model);
		}
		agentMerchantService.save(agentMerchant);
		addMessage(redirectAttributes, "保存代理商商户成功");
		return "redirect:"+Global.getAdminPath()+"/agent/agentmerchant/agentMerchant/?repage";
	}
	
	@RequiresPermissions("agent:agentmerchant:agentMerchant:edit")
	@RequestMapping(value = "delete")
	public String delete(AgentMerchant agentMerchant, RedirectAttributes redirectAttributes) {
		agentMerchantService.delete(agentMerchant);
		addMessage(redirectAttributes, "删除代理商商户成功");
		return "redirect:"+Global.getAdminPath()+"/agent/agentmerchant/agentMerchant/?repage";
	}

}