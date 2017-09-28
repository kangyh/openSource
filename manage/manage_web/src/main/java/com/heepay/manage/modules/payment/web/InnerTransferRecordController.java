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
import com.heepay.manage.modules.payment.entity.InnerTransferRecord;
import com.heepay.manage.modules.payment.service.InnerTransferRecordService;


/**
 *
 * 描    述：会聚财转账查询Controller
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
@RequestMapping(value = "${adminPath}/payment/innerTransferRecord")
public class InnerTransferRecordController extends BaseController {

	@Autowired
	private InnerTransferRecordService innerTransferRecordService;
	
	@ModelAttribute
	public InnerTransferRecord get(@RequestParam(required=false) String id) {
		InnerTransferRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = innerTransferRecordService.get(id);
		}
		if (entity == null){
			entity = new InnerTransferRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:innerTransferRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(InnerTransferRecord innerTransferRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if("R1".equals(innerTransferRecord.getProductCode())){
			innerTransferRecord.setProductCode(null);
		}

		if("R1".equals(innerTransferRecord.getStatus())){
			innerTransferRecord.setStatus(null);
		}
		
		Page<InnerTransferRecord> page = innerTransferRecordService.findPage(new Page<InnerTransferRecord>(request, response), innerTransferRecord); 
		model.addAttribute("page", page);
		return "modules/payment/innerTransferRecordList";
	}

	@RequiresPermissions("payment:innerTransferRecord:view")
	@RequestMapping(value = "form")
	public String form(InnerTransferRecord innerTransferRecord, Model model) {
		model.addAttribute("innerTransferRecord", innerTransferRecord);
		return "modules/payment/innerTransferRecordForm";
	}

	@RequiresPermissions("payment:innerTransferRecord:edit")
	@RequestMapping(value = "save")
	public String save(InnerTransferRecord innerTransferRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, innerTransferRecord)){
			return form(innerTransferRecord, model);
		}
		innerTransferRecordService.save(innerTransferRecord);
		addMessage(redirectAttributes, "保存会聚财转账查询成功");
		return "redirect:"+Global.getAdminPath()+"/payment/innerTransferRecord/?repage";
	}
	
	@RequiresPermissions("payment:innerTransferRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(InnerTransferRecord innerTransferRecord, RedirectAttributes redirectAttributes) {
		innerTransferRecordService.delete(innerTransferRecord);
		addMessage(redirectAttributes, "删除会聚财转账查询成功");
		return "redirect:"+Global.getAdminPath()+"/payment/innerTransferRecord/?repage";
	}

}