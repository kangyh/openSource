/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.enums.ProductType;
import com.heepay.enums.SortOrderType;
import com.heepay.enums.TransType;
import com.heepay.manage.modules.payment.entity.*;
import com.heepay.manage.modules.payment.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.SortOrderType;
import com.heepay.enums.TransType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.BatchCollectionRecordDetail;
import com.heepay.manage.modules.payment.entity.BatchPayRecord;
import com.heepay.manage.modules.payment.entity.ChargeRecord;
import com.heepay.manage.modules.payment.entity.GatewayRecord;
import com.heepay.manage.modules.payment.entity.InnerTransferRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.RefundRecord;
import com.heepay.manage.modules.payment.entity.WithdrawRecord;
import com.heepay.manage.modules.payment.service.BatchCollectionRecordDetailService;
import com.heepay.manage.modules.payment.service.BatchCollectionRecordService;
import com.heepay.manage.modules.payment.service.BatchPayRecordService;
import com.heepay.manage.modules.payment.service.ChargeRecordService;
import com.heepay.manage.modules.payment.service.GatewayRecordService;
import com.heepay.manage.modules.payment.service.InnerTransferRecordService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.payment.service.RefundRecordService;
import com.heepay.manage.modules.payment.service.WithdrawRecordService;


/**
 *
 * 描    述：交易管理Controller
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
@RequestMapping(value = "${adminPath}/payment/paymentRecord")
public class PaymentRecordController extends BaseController {

	@Autowired
	private PaymentRecordService paymentRecordService;

	/**
	 * 充值Service
	 */
	@Autowired
	private ChargeRecordService chargeRecordService;

	/**
	 * 委托收款Service
	 */
	@Autowired
	private BatchCollectionRecordService batchCollectionRecordService;

	/**
	 * 转账明细Service
	 */
	@Autowired
	private BatchCollectionRecordDetailService batchCollectionRecordDetailService;

	/**
	 * 网关Service
	 */
	@Autowired
	private GatewayRecordService gatewayRecordService;

	/**
	 * 提现Service
	 */
	@Autowired
	private WithdrawRecordService withdrawRecordService;

	/**
	 * 转账Service
	 */
	@Autowired
	private BatchPayRecordService batchPayRecordService;

	/**
	 * 退款Service
	 */
	@Autowired
	private RefundRecordService refundRecordService;


	/**
	 * 内转Service
	 */
	@Autowired
	private InnerTransferRecordService innerTransferRecordService;


	@ModelAttribute
	public PaymentRecord get(@RequestParam(required=false) String id) {
		PaymentRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = paymentRecordService.get(id);
		}
		if (entity == null){
			entity = new PaymentRecord();
		}
		return entity;
	}
	
//	@RequiresPermissions("payment:paymentRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(PaymentRecord paymentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(paymentRecord.getSortOrder() == null){
			//默认按照创建时间升序
			paymentRecord.setSortOrder(SortOrderType.ASC.getValue());
		}
		//商户订单管理只要充值，消费,退款，转账
		if(paymentRecord.getTransType() == null){
			paymentRecord.setTransType("R0");
		}
		if(paymentRecord.getStatus() == null){
			paymentRecord.setStatus("R0");
		}
		Page<PaymentRecord> page = paymentRecordService.findPage(new Page<PaymentRecord>(request, response), paymentRecord);
		model.addAttribute("page", page);
		return "modules/payment/paymentRecordList";
	}


	@RequestMapping(value = {"detail"})
	public String detail(PaymentRecord paymentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {

		String paymentId = request.getParameter("paymentId");
		String transNo = request.getParameter("transNo");
		String payMentType = request.getParameter("payMentType");
		paymentRecord = paymentRecordService.get(paymentId);
		model.addAttribute("paymentRecord", paymentRecord);
		if(TransType.CHARGE.getValue().equals(payMentType)){
			ChargeRecord chargeRecord = chargeRecordService.get(transNo);
			model.addAttribute("chargeRecord", chargeRecord);
			return "modules/payment/paymentRecordChargeDetail";
		}else if(TransType.REFUND.getValue().equals(payMentType)){
			RefundRecord refundRecord = refundRecordService.get(transNo);
			model.addAttribute("refundRecord", refundRecord);
			return "modules/payment/paymentRecordRefundDetail";
		}else if(TransType.BATCHPAY.getValue().equals(payMentType)){
			BatchCollectionRecordDetail batchCollectionRecordDetail = batchCollectionRecordDetailService.get(transNo);
			model.addAttribute("batchCollectionRecordDetail", batchCollectionRecordDetail);
			return "modules/payment/paymentRecordBatCollectionDetail";
		}else if(TransType.PAY.getValue().equals(payMentType)){
			GatewayRecord gatewayRecord= gatewayRecordService.get(transNo);
			model.addAttribute("gatewayRecord", gatewayRecord);
			return "modules/payment/paymentRecordGareWayDetail";
		}else if(TransType.WITHDRAW.getValue().equals(payMentType)){
			WithdrawRecord withdrawRecord = withdrawRecordService.get(transNo);
			model.addAttribute("withdrawRecord", withdrawRecord);
			return "modules/payment/paymentRecordWithDrawDetail";
		}else if(TransType.BATCHCOLLECTION.getValue().equals(transNo)){
			BatchPayRecord batol = batchPayRecordService.get(transNo);
			model.addAttribute("batchPayRecord", batol);
			return "modules/payment/paymentRecordBatPayDetail";
		}else if(TransType.TRANSFER.getValue().equals(transNo)){
			InnerTransferRecord innerTransferRecord = innerTransferRecordService.get(transNo);
			model.addAttribute("innerTransferRecord", innerTransferRecord);
			return "modules/payment/paymentRecordInterTransferDetail";
		}
		return "modules/payment/paymentRecordUnkownDetail";
	}


	@RequiresPermissions("payment:paymentRecord:view")
	@RequestMapping(value = "form")
	public String form(PaymentRecord paymentRecord, Model model) {
		model.addAttribute("paymentRecord", paymentRecord);
		return "modules/payment/paymentRecordForm";
	}

	@RequiresPermissions("payment:paymentRecord:edit")
	@RequestMapping(value = "save")
	public String save(PaymentRecord paymentRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, paymentRecord)){
			return form(paymentRecord, model);
		}
		paymentRecordService.save(paymentRecord);
		addMessage(redirectAttributes, "保存交易管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/paymentRecord/?repage";
	}
	
	@RequiresPermissions("payment:paymentRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(PaymentRecord paymentRecord, RedirectAttributes redirectAttributes) {
		paymentRecordService.delete(paymentRecord);
		addMessage(redirectAttributes, "删除交易管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/paymentRecord/?repage";
	}

}