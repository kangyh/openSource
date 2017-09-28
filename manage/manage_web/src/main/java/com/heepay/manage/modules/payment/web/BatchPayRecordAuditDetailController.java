/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.BatchPayStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.BatchPayRecord;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetail;
import com.heepay.manage.modules.payment.entity.TransferAccountReason;
import com.heepay.manage.modules.payment.service.BatchPayRecordDetailService;
import com.heepay.manage.modules.payment.service.BatchPayRecordService;
import com.heepay.manage.modules.payment.service.TransferAccountReasonService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 批量付款管理Controller
 * @author zjx
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/batchPayRecordAuditDetail")
public class BatchPayRecordAuditDetailController extends BaseController {

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
	 * 转账理由Service
	 */
	@Autowired
	private TransferAccountReasonService transferAccountReasonService;

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
			return "modules/payment/batchPayRecordReasonAuditList";
		}
		//查询转账待审核列表
		batchPayRecordDetail.setBatchId(request.getParameter("batchId"));
		batchPayRecordDetail.setStatus("R1");
		Page<BatchPayRecordDetail> page = batchPayRecordDetailService.findPage(new Page<BatchPayRecordDetail>(request, response), batchPayRecordDetail);
		model.addAttribute("page", page);
		return "modules/payment/batchPayRecordAuditDetail";
	}

	@RequestMapping(value = "executiveAudit")
	public String executiveAudit(BatchPayRecordDetail batchPayRecordDetail, HttpServletRequest request, HttpServletResponse response,Model model,
								 RedirectAttributes redirectAttributes) {
		String ckValues = request.getParameter("checkboxValues");
		String batchId = request.getParameter("batchId");
		String pass = request.getParameter("pass");
		List<String> checkBoxValueList = Arrays.asList(ckValues.split(","));
		String auditPerson = UserUtils.getUser().getName();
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
		batchPayRecordDetail.setBatchId(batchId);
		batchPayRecordDetail.setStatus("R1");
		List<BatchPayRecordDetail> batchPayRecordDetails = batchPayRecordDetailService.findList(batchPayRecordDetail);

		if(batchPayRecordDetails.size() == 0){
			BatchPayRecord batchPayRecord = new BatchPayRecord();
			batchPayRecord.setBatchId(batchId);
			//更新转账批次状态
			batchPayRecordService.auditPass(batchPayRecord);
			addMessage(redirectAttributes, "处理完成");
		}
		return "redirect:"+Global.getAdminPath()+"/payment/batchPayRecordAudit/?repage";
	}

	/**
	 * 去除重复转账理由,并返回新的理由列表
	 * @param batchPayRecordDetailList
	 * @return
	 */
	private List<TransferAccountReason>  getDuplicateReasonRemovalList(List<BatchPayRecordDetail> batchPayRecordDetailList){
		List<TransferAccountReason> transferAccountReasons = new ArrayList<TransferAccountReason>();
		List<String> reasonList = new ArrayList<String>();
		if(batchPayRecordDetailList != null){
			for(BatchPayRecordDetail bprecordDetail : batchPayRecordDetailList){
				if(!reasonList.contains(bprecordDetail.getPayReason().trim())){
					TransferAccountReason tfaReason = new TransferAccountReason();
					tfaReason.setMerchantId(bprecordDetail.getMerchantId());
					tfaReason.setTransferReason(bprecordDetail.getPayReason());
//					tfaReason.setCreateTime();
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
}