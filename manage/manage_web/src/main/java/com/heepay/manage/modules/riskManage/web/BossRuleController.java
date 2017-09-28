package com.heepay.manage.modules.riskManage.web;

import java.util.Date;
import java.util.List;

/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */

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

import com.google.common.collect.Lists;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.risk.JobStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.risk.entity.BossRule;
import com.heepay.manage.modules.risk.service.BossRuleService;
import com.heepay.manage.modules.riskManage.rpc.client.BossRpcCLient;
import com.heepay.manage.modules.sys.utils.UserUtils;


/**
 *
 * 描    述：报表规则配置Controller
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
@RequestMapping(value = "${adminPath}/risk/bossRule")
public class BossRuleController extends BaseController {

	@Autowired
	private BossRuleService bossRuleService;
	
	@Autowired
	private BossRpcCLient bossRpcClient;
	
	@ModelAttribute
	public BossRule get(@RequestParam(required=false) String id) {
		BossRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bossRuleService.get(id);
		}
		if (entity == null){
			entity = new BossRule();
		}
		return entity;
	}
	
	@RequiresPermissions("risk:bossRule:view")
	@RequestMapping(value = {"list", ""})
	public String list(BossRule bossRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BossRule> page = bossRuleService.findPage(new Page<BossRule>(request, response), bossRule); 
		for (BossRule bossRules : page.getList()) {
			//通道管理生效标识类型effect_flg
			if(StringUtils.isNotBlank(bossRules.getJobStatus())){
				bossRules.setJobStatus(JobStatus.labelOf(bossRules.getJobStatus()));
			}
			
		}
		model.addAttribute("page", page);
		return "modules/riskManage/bossRuleList";
	}

	@RequiresPermissions("risk:bossRule:view")
	@RequestMapping(value = "form")
	public String form(BossRule bossRule, Model model) {
		if(null != bossRule){
			bossRule = bossRuleService.get(bossRule.getRuleId().toString());
		}
		List<EnumBean> jobStatus = Lists.newArrayList();
		for (JobStatus checkFlg : JobStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			jobStatus.add(ct);
		}
		model.addAttribute("jobStatus", jobStatus);	
		model.addAttribute("bossRule", bossRule);
		return "modules/riskManage/bossRuleForm";
	}
	
	@RequiresPermissions("risk:bossRule:view")
	@RequestMapping(value = "add")
	public String add(BossRule bossRule, Model model) {
		
		//生效标识
		List<EnumBean> jobStatus = Lists.newArrayList();
		for (JobStatus checkFlg : JobStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			jobStatus.add(ct);
		}
		model.addAttribute("jobStatus", jobStatus);		
		model.addAttribute("bossRule", bossRule);
		return "modules/riskManage/bossRuleAdd";
	}

	@RequiresPermissions("risk:bossRule:edit")
	@RequestMapping(value = "save")
	public String save(BossRule bossRule, Model model, RedirectAttributes redirectAttributes) {
		
		JsonMapperUtil jsonUtil = JsonMapperUtil.buildNonDefaultMapper();
		
		if(null != bossRule){
			if(null !=bossRule.getRuleId()){//修改
				bossRule.setUpdateTime(DateUtil.getDate());
				bossRule.setUpdateAuthor(UserUtils.getUser().getName());
				String msg = bossRpcClient.updateRule(jsonUtil.toJson(bossRule));
				logger.info(jsonUtil.toJson(bossRule));
				if("1".equals(msg)){
		        	addMessage(redirectAttributes, "更新成功");
		        }else{
		        	addMessage(redirectAttributes, "更新失败");
		        }
			}else{//添加
				bossRule.setCreateTime(DateUtil.getDate());
				bossRule.setCreatAuthor(UserUtils.getUser().getName());
				//bossRule.setJobStatus(JobStatus.PREDO.getValue());
				logger.info(jsonUtil.toJson(bossRule));
				String msg = bossRpcClient.insertRule(jsonUtil.toJson(bossRule));
				if("1".equals(msg)){
	            	addMessage(redirectAttributes, "添加成功");
	            }else{
	            	addMessage(redirectAttributes, "添加失败");
	            }
			}
		}
		return "redirect:"+Global.getAdminPath()+"/risk/bossRule?repage";
	}
	
	@RequiresPermissions("risk:bossRule:edit")
	@RequestMapping(value = "delete")
	public String delete(BossRule bossRule, RedirectAttributes redirectAttributes) {
		bossRuleService.delete(bossRule);
		addMessage(redirectAttributes, "删除报表规则配置成功");
		return "redirect:"+Global.getAdminPath()+"modules/riskManage/bossRuleList";
	}
	
	// 
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/checkStatus")
	public String CheckStatus(@RequestParam("status") String status) {
		BossRule bossRule = bossRuleService.selectByStatus(status);
		if (bossRule == null) {
			return 1 + "";
		} else {
			return 0 + "";
		}
	}

}