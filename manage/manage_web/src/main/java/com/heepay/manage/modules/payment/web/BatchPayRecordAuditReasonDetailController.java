/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.BatchPayStatus;
import com.heepay.enums.TransType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.BatchPayRecordClient;
import com.heepay.manage.modules.merchant.rpc.client.WithdrawRecordClient;
import com.heepay.manage.modules.payment.entity.BatchPayRecord;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetail;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetailSummary;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.TransferAccountReason;
import com.heepay.manage.modules.payment.service.BatchPayRecordDetailService;
import com.heepay.manage.modules.payment.service.BatchPayRecordService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.payment.service.TransferAccountReasonService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 批量付款管理Controller
 * @author zjx
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/batchPayRecordReasonAuditDetail")
public class BatchPayRecordAuditReasonDetailController extends BaseController {

	/**
	 * 转账明细Service
	 */
	@Autowired
	private BatchPayRecordDetailService batchPayRecordDetailService;

	/**
	 * 转账批次Service
	 */
	@Autowired
	private BatchPayRecordService batchPayRecordService;
	
	/**
	 * 支付单Service
	 */
	@Autowired
	private PaymentRecordService paymentRecordService;

	/**
	 * 转账理由Service
	 */
	@Autowired
	private TransferAccountReasonService transferAccountReasonService;
	
	@Autowired
	private WithdrawRecordClient withdrawRecordClient;
	
	@Autowired
	private BatchPayRecordClient batchPayRecordClient;

	@ModelAttribute
	public BatchPayRecordDetail get(@RequestParam(required=false) String id) {
		BatchPayRecordDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = batchPayRecordDetailService.get(id);
		}
		if (entity == null){
			entity = new BatchPayRecordDetail();
		}
		return entity;
	}

	/**
	 * 待审核列表
	 * @param batchPayRecordDetail
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAudit")
	public String toAudit(BatchPayRecordDetail batchPayRecordDetail, HttpServletRequest request, HttpServletResponse response,Model model) {
		if(UserUtils.getUser().isAdmin()){
			return "";
		}
		//查询转账待审核列表
		batchPayRecordDetail.setBatchId(request.getParameter("batchId"));
//		batchPayRecordDetail.setStatus("R1");
		Page<BatchPayRecordDetail> page = batchPayRecordDetailService.findPage(new Page<>(request, response), batchPayRecordDetail);
		model.addAttribute("page", page);
		return "modules/payment/batchPayRecordReasonAuditDetail";
	}

	@RequestMapping(value = "executiveAudit")
	public String executiveAudit(BatchPayRecordDetail batchPayRecordDetail, HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) throws TException {
		String ckValues = request.getParameter("checkboxValues");
		String batchId = request.getParameter("batchId");
		String pass = request.getParameter("pass");
		String auditPerson = UserUtils.getUser().getName();
		List<String> checkBoxValueList = Arrays.asList(ckValues.split(","));
		if(pass.equals("yes")){
			for(String batchPayId:checkBoxValueList){
				BatchPayRecordDetail detail = new BatchPayRecordDetail();
				detail.setBatchPayId(batchPayId);
				detail.setAuditPerson(auditPerson);
				detail.setStatus(BatchPayStatus.PREPAY.getValue());
				batchPayRecordDetailService.updateAuditState(detail);
			}
		}else{
			for(String batchPayId:checkBoxValueList){
				BatchPayRecordDetail detail = new BatchPayRecordDetail();
				detail.setBatchPayId(batchPayId);
				detail.setAuditPerson(auditPerson);
				detail.setStatus(BatchPayStatus.AUDREJ.getValue());
				batchPayRecordDetailService.updateAuditState(detail);
			}
			//更新支付单状态为"失败"
			for(String transNo:checkBoxValueList){
				PaymentRecord paymentRecord = paymentRecordService.getOne(transNo);
				paymentRecordService.updatePaymentRecordFail(paymentRecord.getPaymentId());
				//撤账
				withdrawRecordClient.revokeAccount(paymentRecord.getMerchantId(), transNo,TransType.BATCHPAY.getValue());
				//记录风控日志
				batchPayRecordClient.recordRiskLog(String.valueOf(paymentRecord.getMerchantId()), transNo);
			}
		}
		batchPayRecordDetail.setBatchId(batchId);
		batchPayRecordDetail.setStatus(BatchPayStatus.PREAUD.getValue());
		List<BatchPayRecordDetail> batchPayRecordDetails = batchPayRecordDetailService.findList(batchPayRecordDetail);
		//明细都审核完之后再更新批次表
		if(batchPayRecordDetails.size() == 0){
			BatchPayRecord batchPayRecord = new BatchPayRecord();
			batchPayRecord.setBatchId(batchId);
			//更新转账批次状态
			if(pass.equals("yes")){
				batchPayRecordService.auditPass(batchPayRecord);
			}else{
				BatchPayRecordDetail detail = new BatchPayRecordDetail();
				detail.setBatchId(batchId);
				detail.setStatus(BatchPayStatus.PREPAY.getValue());
				List<BatchPayRecordDetail> detailList = batchPayRecordDetailService.findList(detail);
				if(detailList == null || detailList.isEmpty()){
					batchPayRecord.setStatus(BatchPayStatus.FINISH.getValue());
				}else{
					batchPayRecord.setStatus(BatchPayStatus.PENDNG.getValue());
				}
				batchPayRecordService.auditReject(batchPayRecord);
			}
			addMessage(redirectAttributes, "处理完成");
		}
		if(!pass.equals("yes")){
			return "redirect:"+Global.getAdminPath()+"/payment/batchPayRecordReasonList/?repage";
		}
		//查询当前选中得转账明细(商户,理由)
		List<BatchPayRecordDetail> batchPayRecordDetailList = batchPayRecordDetailService.findListByBatchPayId(checkBoxValueList);
		//去除重复转账理由,并返回新的理由列表
		List<TransferAccountReason> transferAccountNewReasonList = getDuplicateReasonRemovalList(batchPayRecordDetailList);
		//查询当前商户的所有理由
		String merchantId = request.getParameter("merchantId");
		TransferAccountReason transferAccountReason = new TransferAccountReason();
		transferAccountReason.setMerchantId(Long.parseLong(merchantId));
		List<TransferAccountReason> transferAccountSavedReasons = transferAccountReasonService.findList(transferAccountReason);
		//对比商户已存储理由和新理由去重
		List<TransferAccountReason> transferAccountRedayReasons = getDuplicateReasonRemovalList(transferAccountNewReasonList,
				transferAccountSavedReasons);
		//存储商户新理由
		if(transferAccountRedayReasons.size()!=0){
			transferAccountReasonService.batchInsert(transferAccountRedayReasons);
		}
		return "redirect:"+Global.getAdminPath()+"/payment/batchPayRecordReasonList/?repage";
	}

	/**
	 * 去除重复转账理由,并返回新的理由列表
	 * @param batchPayRecordDetailList
	 * @return
	 */
	private List<TransferAccountReason>  getDuplicateReasonRemovalList(List<BatchPayRecordDetail> batchPayRecordDetailList){
		List<TransferAccountReason> transferAccountReasons = new ArrayList<>();
		List<String> reasonList = new ArrayList<String>();
		if(batchPayRecordDetailList != null){
			for(BatchPayRecordDetail bprecordDetail : batchPayRecordDetailList){
				if(!reasonList.contains(bprecordDetail.getPayReason().trim())){
					TransferAccountReason tfaReason = new TransferAccountReason();
					tfaReason.setMerchantId(bprecordDetail.getMerchantId());
                    tfaReason.setMerchantName(bprecordDetail.getMerchantLoginName());
                    tfaReason.setMerchantCompany(bprecordDetail.getMerchantCompany());
					tfaReason.setTransferReason(bprecordDetail.getPayReason());
                    tfaReason.setStatus(BatchPayStatus.SUCCESS.getValue());
                    tfaReason.setAutiter(bprecordDetail.getAuditPerson());
                    tfaReason.setRemark(bprecordDetail.getAuditReason());
                    tfaReason.setAuditNeed(bprecordDetail.getAuditFlag());
					tfaReason.setCreateTime(new Date());
					transferAccountReasons.add(tfaReason);
					reasonList.add(bprecordDetail.getPayReason());
				}
			}
		}
		return transferAccountReasons;
	}

	/**
	 *
	 * @param transferAccountNewReasonList
	 * @param transferAccountSavedReasons
	 * @return
	 */
	private List<TransferAccountReason>  getDuplicateReasonRemovalList(List<TransferAccountReason> transferAccountNewReasonList,
			List<TransferAccountReason> transferAccountSavedReasons) {
		List<TransferAccountReason> redayTransferAccountReasons = new ArrayList<TransferAccountReason>();
		List<String> redayReasonList = new ArrayList<String>();
		for(TransferAccountReason transferAccountReason : transferAccountSavedReasons){
			redayReasonList.add(transferAccountReason.getTransferReason());
		}
		for(TransferAccountReason transferAccountReason : transferAccountNewReasonList){
			if(!redayReasonList.contains(transferAccountReason.getTransferReason().trim())){
				redayTransferAccountReasons.add(transferAccountReason);
			}
		}
		return redayTransferAccountReasons;
	}
	
	@RequestMapping(value = {"detailList"})
	public String detailList(BatchPayRecordDetail batchPayRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		batchPayRecordDetail.setBatchId(request.getParameter("batchId"));
		Page<BatchPayRecordDetail> page = batchPayRecordDetailService.findPage(new Page<BatchPayRecordDetail>(request, response), batchPayRecordDetail);
		model.addAttribute("page", page);
		BatchPayRecordDetailSummary batchPayRecordSummary = getSummaryBatchPayRecordDetailResult(page);
		model.addAttribute("batchPayRecordDetailSummary", batchPayRecordSummary);
		return "modules/payment/batchPayRecordAuditDetail";
	}
	
	/**
	 *
	 * @param page
	 * @return
	 */
	private BatchPayRecordDetailSummary getSummaryBatchPayRecordDetailResult(Page<BatchPayRecordDetail> page){

		BatchPayRecordDetailSummary bpayRecordDetailSummary = new BatchPayRecordDetailSummary();
		String tempTotal;
		for(BatchPayRecordDetail bpRecordDetail : page.getList()){
			//转账总金额
			tempTotal = StringUtil.isBlank(bpRecordDetail.getPayAmount())?"0.0000":bpRecordDetail.getPayAmount();
			bpayRecordDetailSummary.setTotalAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getTotalAmount()).add(new BigDecimal(tempTotal))));

			if("SUCCES".equals(bpRecordDetail.getStatus())){
				//转账成功金额
				tempTotal = StringUtil.isBlank(bpRecordDetail.getSuccessAmount())?"0.0000":bpRecordDetail.getPayAmount();
				bpayRecordDetailSummary.setSuccessTotalAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getSuccessTotalAmount()).add(new BigDecimal(tempTotal))));
			}
			if("SUCCES".equals(bpRecordDetail.getStatus())){
				//转账成功手续费
				tempTotal = StringUtil.isBlank(bpRecordDetail.getFeeAmount())?"0.0000":bpRecordDetail.getFeeAmount();
				bpayRecordDetailSummary.setTotalFeeAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getTotalFeeAmount()).add(new BigDecimal(tempTotal))));
			}
			if("FAILED".equals(bpRecordDetail.getStatus())){
				//失败总金额
				bpayRecordDetailSummary.setFailTotalAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getFailTotalAmount()).add(new BigDecimal(bpRecordDetail.getPayAmount()))));
			}
		}

		logger.info("转账汇总:{}",bpayRecordDetailSummary.toString());

		return bpayRecordDetailSummary;
	}
}