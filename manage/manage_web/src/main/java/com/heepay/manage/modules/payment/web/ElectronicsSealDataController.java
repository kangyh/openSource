/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.heepay.manage.modules.payment.entity.ElectronicsSealData;
import com.heepay.manage.modules.payment.service.ElectronicsSealDataService;


/**
 *
 * 描    述：签宝账户印章模板Controller
 *
 * 创 建 者： @author zjx
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
@RequestMapping(value = "${adminPath}/payment/electronicsSealData")
public class ElectronicsSealDataController extends BaseController {

	@Autowired
	private ElectronicsSealDataService electronicsSealDataService;
	
	@ModelAttribute
	public ElectronicsSealData get(@RequestParam(required=false) String id) {
		ElectronicsSealData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = electronicsSealDataService.get(id);
		}
		if (entity == null){
			entity = new ElectronicsSealData();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:electronicsSealData:view")
	@RequestMapping(value = {"list", ""})
	public String list(ElectronicsSealData electronicsSealData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ElectronicsSealData> page = electronicsSealDataService.findPage(new Page<ElectronicsSealData>(request, response), electronicsSealData); 
		model.addAttribute("page", page);
		return "modules/payment/electronicsSealDataList";
	}

	@RequiresPermissions("payment:electronicsSealData:view")
	@RequestMapping(value = "form")
	public String form(ElectronicsSealData electronicsSealData, Model model) {
		model.addAttribute("electronicsSealData", electronicsSealData);
		return "modules/payment/electronicsSealDataForm";
	}

	@RequiresPermissions("payment:electronicsSealData:edit")
	@RequestMapping(value = "save")
	public String save(ElectronicsSealData electronicsSealData, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, electronicsSealData)){
			return form(electronicsSealData, model);
		}
		electronicsSealDataService.save(electronicsSealData);
		addMessage(redirectAttributes, "保存签宝账户印章模板成功");
		return "redirect:"+Global.getAdminPath()+"/payment/electronicsSealData/?repage";
	}
	
	@RequiresPermissions("payment:electronicsSealData:edit")
	@RequestMapping(value = "delete")
	public String delete(ElectronicsSealData electronicsSealData, RedirectAttributes redirectAttributes) {
		electronicsSealDataService.delete(electronicsSealData);
		addMessage(redirectAttributes, "删除签宝账户印章模板成功");
		return "redirect:"+Global.getAdminPath()+"/payment/electronicsSealData/?repage";
	}

}