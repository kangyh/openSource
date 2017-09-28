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
import com.heepay.manage.modules.payment.entity.PreUnionpayRecord;
import com.heepay.manage.modules.payment.service.PreUnionpayRecordService;


/**
 *
 * 描    述：银联扫码预下单Controller
 *
 * 创 建 者： @author ld
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
@RequestMapping(value = "${adminPath}/payment/preUnionpayRecord")
public class PreUnionpayRecordController extends BaseController {

	@Autowired
	private PreUnionpayRecordService preUnionpayRecordService;
	
	@ModelAttribute
	public PreUnionpayRecord get(@RequestParam(required=false) String id) {
		PreUnionpayRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = preUnionpayRecordService.get(id);
		}
		if (entity == null){
			entity = new PreUnionpayRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:preUnionpayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(PreUnionpayRecord preUnionpayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PreUnionpayRecord> page = new Page<>(request, response);
		page.setOrderBy("create_time desc");
		page = preUnionpayRecordService.findPage(page, preUnionpayRecord);
		model.addAttribute("page", page);
		return "modules/payment/preUnionpayRecordList";
	}

	@RequiresPermissions("payment:preUnionpayRecord:view")
	@RequestMapping(value = "form")
	public String form(PreUnionpayRecord preUnionpayRecord, Model model) {
		model.addAttribute("preUnionpayRecord", preUnionpayRecord);
		return "modules/payment/preUnionpayRecordForm";
	}

	@RequiresPermissions("payment:preUnionpayRecord:edit")
	@RequestMapping(value = "save")
	public String save(PreUnionpayRecord preUnionpayRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, preUnionpayRecord)){
			return form(preUnionpayRecord, model);
		}
		preUnionpayRecordService.save(preUnionpayRecord);
		addMessage(redirectAttributes, "保存银联扫码预下单成功");
		return "redirect:"+Global.getAdminPath()+"/payment/preUnionpayRecord/?repage";
	}
	
	@RequiresPermissions("payment:preUnionpayRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(PreUnionpayRecord preUnionpayRecord, RedirectAttributes redirectAttributes) {
		preUnionpayRecordService.delete(preUnionpayRecord);
		addMessage(redirectAttributes, "删除银联扫码预下单成功");
		return "redirect:"+Global.getAdminPath()+"/payment/preUnionpayRecord/?repage";
	}

}