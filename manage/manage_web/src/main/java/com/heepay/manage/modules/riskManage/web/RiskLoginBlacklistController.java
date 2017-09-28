/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

import java.util.Date;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.risk.entity.RiskLoginBlacklist;
import com.heepay.manage.modules.risk.service.RiskLoginBlacklistService;
import com.heepay.manage.modules.riskManage.rpc.client.RiskLoginClient;


/**
 *
 * 描    述：risk_login_blacklistController
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
@RequestMapping(value = "${adminPath}/risk/riskLoginBlacklist")
public class RiskLoginBlacklistController extends BaseController {

	@Autowired
	private RiskLoginBlacklistService riskLoginBlacklistService;
	
	@Autowired
	private RiskLoginClient riskLoginClient;
	
	private static final Logger log = LogManager.getLogger();
	
	@ModelAttribute
	public RiskLoginBlacklist get(@RequestParam(required=false) String id) {
		RiskLoginBlacklist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = riskLoginBlacklistService.get(id);
		}
		if (entity == null){
			entity = new RiskLoginBlacklist();
		}
		return entity;
	}
	
	@RequiresPermissions("risk:riskLoginBlacklist:view")
	@RequestMapping(value = {"list", ""})
	public String list(RiskLoginBlacklist riskLoginBlacklist, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RiskLoginBlacklist> page = riskLoginBlacklistService.findPage(new Page<RiskLoginBlacklist>(request, response), riskLoginBlacklist); 
		model.addAttribute("page", page);
		return "modules/riskManage/riskLoginBlacklistList";
	}

	@RequiresPermissions("risk:riskLoginBlacklist:view")
	@RequestMapping(value = "form")
	public String form(RiskLoginBlacklist riskLoginBlacklist, Model model) {
		model.addAttribute("riskLoginBlacklist", riskLoginBlacklist);
		return "modules/riskManage/riskLoginBlacklistForm";
	}

	@RequiresPermissions("risk:riskLoginBlacklist:edit")
	@RequestMapping(value = "save")
	public String save(RiskLoginBlacklist riskLoginBlacklist, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, riskLoginBlacklist)){
			return form(riskLoginBlacklist, model);
		}
		
		if(riskLoginBlacklist.getBlackId() != null){
			String msg = riskLoginClient.editRiskLoginBlacklist(JsonMapperUtil.nonEmptyMapper().toJson(riskLoginBlacklist));
			log.info("修改风控黑名单商户返回{}",msg);
			if("1".equals(msg)){
				addMessage(redirectAttributes, "修改成功");
			}
		}else{
			riskLoginBlacklist.setCreateTime(new Date());
			String msg = riskLoginClient.addRiskLoginBlacklist(JsonMapperUtil.nonEmptyMapper().toJson(riskLoginBlacklist));
			log.info("添加风控黑名单商户返回{}",msg);
			if("1".equals(msg)){
				addMessage(redirectAttributes, "添加成功");
			}
		}
		return "redirect:"+Global.getAdminPath()+"/risk/riskLoginBlacklist/?repage";
	}
	
	@RequiresPermissions("risk:riskLoginBlacklist:edit")
	@RequestMapping(value = "delete")
	public String delete(RiskLoginBlacklist riskLoginBlacklist, RedirectAttributes redirectAttributes) {
		String msg = riskLoginClient.deleteRiskLoginBlacklist(JsonMapperUtil.nonEmptyMapper().toJson(riskLoginBlacklist));
		log.info("删除风控黑名单商户返回{}",msg);
		if("1".equals(msg)){
			addMessage(redirectAttributes, "删除成功");
		}
		return "redirect:"+Global.getAdminPath()+"/risk/riskLoginBlacklist/?repage";
	}

}