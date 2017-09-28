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
import com.heepay.manage.modules.payment.entity.AlipayRecord;
import com.heepay.manage.modules.payment.service.AlipayRecordService;


/**
 *
 * 描    述：支付宝扫码支付Controller
 *
 * 创 建 者： @author tyq
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
@RequestMapping(value = "${adminPath}/payment/alipayRecord")
public class AlipayRecordController extends BaseController {

	@Autowired
	private AlipayRecordService alipayRecordService;
	
	@ModelAttribute
	public AlipayRecord get(@RequestParam(required=false) String id) {
		AlipayRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = alipayRecordService.get(id);
		}
		if (entity == null){
			entity = new AlipayRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:alipayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(AlipayRecord alipayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AlipayRecord> page = alipayRecordService.findPage(new Page<AlipayRecord>(request, response), alipayRecord); 
		model.addAttribute("page", page);
		return "modules/payment/alipayRecordList";
	}

	@RequiresPermissions("payment:alipayRecord:view")
	@RequestMapping(value = "form")
	public String form(AlipayRecord alipayRecord, Model model) {
		model.addAttribute("alipayRecord", alipayRecord);
		return "modules/payment/alipayRecordForm";
	}

	@RequiresPermissions("payment:alipayRecord:edit")
	@RequestMapping(value = "save")
	public String save(AlipayRecord alipayRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, alipayRecord)){
			return form(alipayRecord, model);
		}
		alipayRecordService.save(alipayRecord);
		addMessage(redirectAttributes, "保存支付宝扫码支付成功");
		return "redirect:"+Global.getAdminPath()+"/payment/alipayRecord/?repage";
	}
	
	@RequiresPermissions("payment:alipayRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(AlipayRecord alipayRecord, RedirectAttributes redirectAttributes) {
		alipayRecordService.delete(alipayRecord);
		addMessage(redirectAttributes, "删除支付宝扫码支付成功");
		return "redirect:"+Global.getAdminPath()+"/payment/alipayRecord/?repage";
	}

}