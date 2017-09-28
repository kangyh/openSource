/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.web;

import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.config.Global;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromProfitTemplet;
import com.heepay.prom.modules.prom.service.PromProfitTempletService;
import com.heepay.prom.modules.prom.utils.BindingId;
import com.heepay.prom.modules.sys.utils.UserUtils;
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
import java.util.Date;


/**
 *
 * 描    述：分润比例模板管理Controller
 *
 * 创 建 者： @author wangdong
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
@RequestMapping(value = "${adminPath}/prom/promProfitTemplet")
public class PromProfitTempletController extends BaseController {

	@Autowired
	private PromProfitTempletService promProfitTempletService;
	
	@ModelAttribute
	public PromProfitTemplet get(@RequestParam(required=false) String id) {
		PromProfitTemplet entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = promProfitTempletService.get(id);
		}
		if (entity == null){
			entity = new PromProfitTemplet();
		}
		return entity;
	}
	
	@RequiresPermissions("prom:promProfitTemplet:view")
	@RequestMapping(value = {"list", ""})
	public String list(PromProfitTemplet promProfitTemplet, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PromProfitTemplet> page = promProfitTempletService.findPage(new Page<PromProfitTemplet>(request, response), promProfitTemplet); 
		model.addAttribute("page", page);
		return "modules/prom/promProfitTempletList";
	}

	@RequiresPermissions("prom:promProfitTemplet:view")
	@RequestMapping(value = "form")
	public String form(PromProfitTemplet promProfitTemplet, Model model) {
		if (null != promProfitTemplet && null != promProfitTemplet.getPromId()) {
			promProfitTemplet = get(promProfitTemplet.getPromId().toString());
			model.addAttribute("promProfitTemplet", promProfitTemplet);
		}
		return "modules/prom/promProfitTempletForm";
	}

	@RequiresPermissions("prom:promProfitTemplet:edit")
	@RequestMapping(value = "save")
	public String save(PromProfitTemplet promProfitTemplet, Model model, RedirectAttributes redirectAttributes) {
		if(promProfitTemplet.getPromId() != null){
			promProfitTemplet.setUpdateTime(new Date());
			promProfitTemplet.setUpdatePeople(UserUtils.getUser().getName());
			promProfitTempletService.updateEntity(promProfitTemplet);
		}else{
			promProfitTemplet.setTempletId(BindingId.getTempletId());
			promProfitTemplet.setCreateTime(new Date());
			promProfitTemplet.setCreator(UserUtils.getUser().getName());
			promProfitTempletService.save(promProfitTemplet);
		}
		addMessage(redirectAttributes, "保存分润比例模板成功");
		return "redirect:"+Global.getAdminPath()+"/prom/promProfitTemplet/?repage";
	}
	
	
	@RequiresPermissions("prom:promProfitTemplet:edit")
	@RequestMapping(value = "delete")
	public String delete(PromProfitTemplet promProfitTemplet, RedirectAttributes redirectAttributes) {
		promProfitTempletService.delete(promProfitTemplet);
		addMessage(redirectAttributes, "删除分润比例模板成功");
		return "redirect:" + Global.getAdminPath() + "/prom/promProfitTemplet/?repage";
	}
	
}