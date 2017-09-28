/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.risk.MonitorObject;
import com.heepay.enums.risk.QuotaType;
import com.heepay.enums.risk.RiskMerChantStatus;
import com.heepay.enums.risk.RiskOrderDealType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.risk.entity.RiskIpbase;
import com.heepay.manage.modules.risk.entity.RiskRulelist;
import com.heepay.manage.modules.risk.service.RiskRulelistService;
import com.heepay.manage.modules.riskManage.rpc.client.RiskRuleClient;
import com.heepay.manage.modules.sys.utils.UserUtils;


/**
 *
 * 描    述：风控规则列表Controller
 *
 * 创 建 者： @author wj
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
@RequestMapping(value = "${adminPath}/risk/riskRulelist")
public class RiskRulelistController extends BaseController {

	@Autowired
	private RiskRulelistService riskRulelistService;
	
	@Autowired
	private RiskRuleClient riskRuleClient;
	
	private static final Logger log = LogManager.getLogger();
	
	@ModelAttribute
	public RiskRulelist get(@RequestParam(required=false) String id) {
		RiskRulelist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = riskRulelistService.get(id);
		}
		if (entity == null){
			entity = new RiskRulelist();
		}
		return entity;
	}
	
	@RequiresPermissions("risk:riskRulelist:view")
	@RequestMapping(value = {"list", ""})
	public String list(RiskRulelist riskRulelist, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RiskRulelist> page = riskRulelistService.findPage(new Page<RiskRulelist>(request, response), riskRulelist); 
		for (RiskRulelist riskRule : page.getList()) {
			//通道管理生效标识类型effect_flg
			if(StringUtils.isNotBlank(riskRule.getRiskControlAction())){
				riskRule.setRiskControlAction(RiskOrderDealType.labelOf(riskRule.getRiskControlAction()));
			}
			//监控对象
			if(StringUtils.isNotBlank(riskRule.getMonitorObject())){
				riskRule.setMonitorObject(MonitorObject.labelOf(riskRule.getMonitorObject()));
			}
			//状态
			if(StringUtils.isNotBlank(riskRule.getStatus())){
				riskRule.setStatus(RiskMerChantStatus.labelOf(riskRule.getStatus()));
			}
			
		}
		
		List<EnumBean> actionList = Lists.newArrayList();  
		String[] actionvalue = {"预警","阻断"};
		String[] actionkey = {"WARN","BLOCK"};
		for(int i = 0; i < actionvalue.length; i++){
			EnumBean ct = new EnumBean();
			ct.setValue(actionkey[i]);
			ct.setName(actionvalue[i]);
			actionList.add(ct);
		}
		
		List<EnumBean> statusList = Lists.newArrayList();
		for (RiskMerChantStatus checkFlg : RiskMerChantStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			statusList.add(ct);
		}

        List<EnumBean> monitorList = Lists.newArrayList();
        for (MonitorObject checkFlg : MonitorObject.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            monitorList.add(ct);
        }
		
		model.addAttribute("monitorList", monitorList);
		model.addAttribute("statusList", statusList);
		model.addAttribute("actionList", actionList);
		model.addAttribute("page", page);
		
		return "modules/riskManage/riskRulelistList";
	}

	@RequiresPermissions("risk:riskRulelist:view")
	@RequestMapping(value = "form")
	public String form(RiskRulelist riskRulelist, Model model) {
		
		List<EnumBean> actionList = Lists.newArrayList();  
		String[] actionvalue = {"预警","阻断"};
		String[] actionkey = {"WARN","BLOCK"};
		for(int i = 0; i < actionvalue.length; i++){
			EnumBean ct = new EnumBean();
			ct.setValue(actionkey[i]);
			ct.setName(actionvalue[i]);
			actionList.add(ct);
		}
		
		List<EnumBean> quotaTypeList = Lists.newArrayList();
		for (MonitorObject checkFlg : MonitorObject.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			quotaTypeList.add(ct);
		}
		
		List<EnumBean> statusList = Lists.newArrayList();
		for (RiskMerChantStatus checkFlg : RiskMerChantStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			statusList.add(ct);
		}
		
		//通道管理生效标识类型effect_flg
		if(StringUtils.isNotBlank(riskRulelist.getRiskControlAction())){
			riskRulelist.setRiskControlAction(RiskOrderDealType.labelOf(riskRulelist.getRiskControlAction()));
		}
		//监控对象
		if(StringUtils.isNotBlank(riskRulelist.getMonitorObject())){
			riskRulelist.setMonitorObject(MonitorObject.labelOf(riskRulelist.getMonitorObject()));
		}
		//状态
		if(StringUtils.isNotBlank(riskRulelist.getStatus())){
			riskRulelist.setStatus(RiskMerChantStatus.labelOf(riskRulelist.getStatus()));
		}

		//还原状态
		if(Objects.equals(riskRulelist.getStatus(),"禁用")){
			riskRulelist.setStatus("DISABL");
		}else if(Objects.equals(riskRulelist.getStatus(),"启用")){
			riskRulelist.setStatus("ENABLE");
		}
		model.addAttribute("statusList", statusList);
		model.addAttribute("quotaTypeList", quotaTypeList);
		model.addAttribute("actionList", actionList);
		model.addAttribute("riskRulelist", riskRulelist);
		return "modules/riskManage/riskRulelistForm";
	}

	@RequiresPermissions("risk:riskRulelist:edit")
	@RequestMapping(value = "save")
	public String save(RiskRulelist riskRulelist, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, riskRulelist)){
			return form(riskRulelist, model);
		}

		//监控对象转化
		if(Objects.equals(riskRulelist.getMonitorObject(),"商户")){
			riskRulelist.setMonitorObject("MERCHANT");
		}else if(Objects.equals(riskRulelist.getMonitorObject(),"用户")){
			riskRulelist.setMonitorObject("USER");
		}

		//风控措施
		if(Objects.equals(riskRulelist.getRiskControlAction(),"阻断")){
			riskRulelist.setRiskControlAction("BLOCK");
		}else if(Objects.equals(riskRulelist.getRiskControlAction(),"预警")){
			riskRulelist.setRiskControlAction("WARN");
		}else if(Objects.equals(riskRulelist.getRiskControlAction(),"人工审核")){
			riskRulelist.setRiskControlAction("AUDIT");
		}

		if(riskRulelist.getRulelistId() != null){
			//修改
			riskRulelist.setOperator(UserUtils.getUser().getName());
			String msg = riskRuleClient.editRiskRule(JsonMapperUtil.nonEmptyMapper().toJson(riskRulelist));
			log.info("风控规则返回{}",msg);
			if("1".equals(msg)){
				addMessage(redirectAttributes, "修改成功");
			}
		}else{//添加
			riskRulelist.setCreateTime(new Date());
			riskRulelist.setOperator(UserUtils.getUser().getName());
			String msg = riskRuleClient.addRiskRule(JsonMapperUtil.nonEmptyMapper().toJson(riskRulelist));
			log.info("风控规则返回{}",msg);
			if("1".equals(msg)){
				addMessage(redirectAttributes, "添加成功");
			}
		}
		addMessage(redirectAttributes, "保存风控规则列表成功");
		return "redirect:"+Global.getAdminPath()+"/risk/riskRulelist/?repage";
	}
	
	@RequiresPermissions("risk:riskRulelist:edit")
	@RequestMapping(value = "delete")
	public String delete(RiskRulelist riskRulelist, RedirectAttributes redirectAttributes) {
		riskRulelistService.delete(riskRulelist);
		addMessage(redirectAttributes, "删除风控规则列表成功");
		return "redirect:"+Global.getAdminPath()+"/risk/riskRulelist/?repage";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/checkRuleId")
	public String chanageValue(@RequestParam("ruleId") String ruleId) {
		RiskRulelist riskRulelist = riskRulelistService.getByRuleId(ruleId);
		if(riskRulelist == null){
			return 1+"";
		}else{
			return 0+"";
		}
		
	}

}