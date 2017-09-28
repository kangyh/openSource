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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.risk.entity.RiskBlockInfo;
import com.heepay.manage.modules.risk.entity.RiskIpbase;
import com.heepay.manage.modules.risk.service.RiskIpbaseService;


/**
 *
 * 描    述：风控ip库Controller
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
@RequestMapping(value = "${adminPath}/risk/riskIpbase")
public class RiskIpbaseController extends BaseController {

	@Autowired
	private RiskIpbaseService riskIpbaseService;
	
	@ModelAttribute
	public RiskIpbase get(@RequestParam(required=false) String id) {
		RiskIpbase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = riskIpbaseService.get(id);
		}
		if (entity == null){
			entity = new RiskIpbase();
		}
		return entity;
	}
	
	@RequiresPermissions("risk:riskIpbase:view")
	@RequestMapping(value = {"list", ""})
	public String list(RiskIpbase riskIpbase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RiskIpbase> page = riskIpbaseService.findPage(new Page<RiskIpbase>(request, response), riskIpbase); 
		model.addAttribute("page", page);
		return "modules/riskManage/riskIpbaseList";
	}

	@RequiresPermissions("risk:riskIpbase:view")
	@RequestMapping(value = "form")
	public String form(RiskIpbase riskIpbase, Model model) {
		model.addAttribute("riskIpbase", riskIpbase);
		return "modules/riskManage/riskIpbaseForm";
	}

	@RequiresPermissions("risk:riskIpbase:edit")
	@RequestMapping(value = "save")
	public String save(RiskIpbase riskIpbase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, riskIpbase)){
			return form(riskIpbase, model);
		}
		riskIpbaseService.save(riskIpbase);
		addMessage(redirectAttributes, "保存风控ip库成功");
		return "redirect:"+Global.getAdminPath()+"/risk/riskIpbase/?repage";
	}
	
	@RequiresPermissions("risk:riskIpbase:edit")
	@RequestMapping(value = "delete")
	public String delete(RiskIpbase riskIpbase, RedirectAttributes redirectAttributes) {
		riskIpbaseService.delete(riskIpbase);
		addMessage(redirectAttributes, "删除风控ip库成功");
		return "redirect:"+Global.getAdminPath()+"/risk/riskIpbase/?repage";
	}
	
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/checkIp")
	public String chanageValue(@RequestParam("ip") String ip) {
		RiskIpbase riskIpbase = riskIpbaseService.get(ip);
		if(riskIpbase == null){
			return 1+"";
		}else{
			return 0+"";
		}
		
	}

}