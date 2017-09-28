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

import com.heepay.common.util.StringUtils;
import com.heepay.enums.BatchPayReasonStatus;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.TransferAccountReason;
import com.heepay.manage.modules.payment.service.TransferAccountReasonService;


/**
 *
 * 描    述：转账理由Controller
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
@RequestMapping(value = "${adminPath}/payment/batchPayRecordReasonAudit1")
public class TransferAccountReasonController extends BaseController {

	@Autowired
	private TransferAccountReasonService transferAccountReasonService;
	
	@ModelAttribute
	public TransferAccountReason get(@RequestParam(required=false) String id) {
		TransferAccountReason entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = transferAccountReasonService.get(id);
		}
		if (entity == null){
			entity = new TransferAccountReason();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TransferAccountReason transferAccountReason, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(transferAccountReason.getSortOrder() == null){
			transferAccountReason.setSortOrder(SortOrderType.ASC.getValue());
		}
		Page<TransferAccountReason> page = transferAccountReasonService.findPage(new Page<>(request, response), transferAccountReason);
		model.addAttribute("page", page);
		return "modules/payment/transferAccountReasonList";
	}

	@RequiresPermissions("payment:transferAccountReason:view")
	@RequestMapping(value = "form")
	public String form(TransferAccountReason transferAccountReason, Model model) {
		model.addAttribute("transferAccountReason", transferAccountReason);
		return "modules/payment/transferAccountReasonForm";
	}

//	@RequiresPermissions("payment:transferAccountReason:view")
	@RequestMapping(value = "audrej")
	public String audrej(TransferAccountReason transferAccountReason, Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String transferReasonId = request.getParameter("id");
		transferAccountReason.setTransferReasonId(Long.parseLong(transferReasonId));
		transferAccountReason.setStatus(BatchPayReasonStatus.AUDREJ.getValue());
//		transferAccountReason.setUpdateTime(new Date());
		transferAccountReasonService.updateStatus(transferAccountReason);
		addMessage(redirectAttributes, "审核拒绝");
		return "redirect:"+Global.getAdminPath()+"/payment/batchPayRecordReasonAudit/?repage";
	}

//	@RequiresPermissions("payment:transferAccountReason:view")
	@RequestMapping(value = "adopt")
	public String adopt(TransferAccountReason transferAccountReason, Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String transferReasonId = request.getParameter("id");
		transferAccountReason.setTransferReasonId(Long.parseLong(transferReasonId));
		transferAccountReason.setStatus(BatchPayReasonStatus.ADOPT.getValue());
//		transferAccountReason.setUpdateTime(new Date());
		transferAccountReasonService.updateStatus(transferAccountReason);
		addMessage(redirectAttributes, "审核通过");
		return "redirect:"+Global.getAdminPath()+"/payment/batchPayRecordReasonAudit/?repage";
	}
	@RequestMapping(value = "detail")
	public String detail(TransferAccountReason transferAccountReason, Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		String transferReasonId = request.getParameter("id");
		transferAccountReason = transferAccountReasonService.get(transferReasonId);
		model.addAttribute("transferAccountReason", transferAccountReason);
		return "modules/payment/transferAccountReasonForm";
	}

	@RequiresPermissions("payment:transferAccountReason:edit")
	@RequestMapping(value = "save")
	public String save(TransferAccountReason transferAccountReason, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, transferAccountReason)){
			return form(transferAccountReason, model);
		}
		transferAccountReasonService.save(transferAccountReason);
		addMessage(redirectAttributes, "保存转账理由成功");
		return "redirect:"+Global.getAdminPath()+"/payment/transferAccountReason/?repage";
	}
	
	@RequiresPermissions("payment:transferAccountReason:edit")
	@RequestMapping(value = "delete")
	public String delete(TransferAccountReason transferAccountReason, RedirectAttributes redirectAttributes) {
		transferAccountReasonService.delete(transferAccountReason);
		addMessage(redirectAttributes, "删除转账理由成功");
		return "redirect:"+Global.getAdminPath()+"/payment/transferAccountReason/?repage";
	}

}