/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.heepay.enums.WithdrawStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.WithdrawRecordClient;
import com.heepay.manage.modules.merchant.rpc.client.WithdrawRecordServiceClient;
import com.heepay.manage.modules.payment.entity.WithdrawRecord;
import com.heepay.manage.modules.payment.service.WithdrawRecordService;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 提现管理Controller
 * @author hs
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/withdrawRecord")
public class WithdrawRecordController extends BaseController {

	@Autowired
	private WithdrawRecordService withdrawRecordService;
	
	@Autowired
	private WithdrawRecordClient withdrawRecordClient;
	
	@Autowired
	private WithdrawRecordServiceClient withdrawRecordServiceClient;
	
	//记录日志
	private static final Logger log = LogManager.getLogger();
	
	@ModelAttribute
	public WithdrawRecord get(@RequestParam(required=false) String id) {
		WithdrawRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = withdrawRecordService.get(id);
		}
		if (entity == null){
			entity = new WithdrawRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:withdrawRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(WithdrawRecord withdrawRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(withdrawRecord.getStatus() == null){
			withdrawRecord.setStatus("R1");
		}
		if(withdrawRecord.getSortOrder() == null){
			//默认按照创建时间升序
			withdrawRecord.setSortOrder(SortOrderType.DESC.getValue());
		}
		Page<WithdrawRecord> page = withdrawRecordService.findPage(new Page<WithdrawRecord>(request, response), withdrawRecord); 
		model.addAttribute("page", page);
		return "modules/payment/withdrawRecordList";
	}

	@RequiresPermissions("payment:withdrawRecord:view")
	@RequestMapping(value = "form")
	public String form(WithdrawRecord withdrawRecord, Model model) {
		
/*		PaymentRecord paymentRecord = paymentRecordService.getOne(String.valueOf(withdrawRecord.getWithdrawId()));
		if(paymentRecord!=null){
			model.addAttribute("paymentRecord", paymentRecord);
		}*/
		model.addAttribute("withdrawRecord", withdrawRecord);
		return "modules/payment/withdrawRecordForm";
	}

	@RequiresPermissions("payment:withdrawRecord:edit")
	@RequestMapping(value = "save")
	public String save(WithdrawRecord withdrawRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, withdrawRecord)){
			return form(withdrawRecord, model);
		}
		withdrawRecordService.save(withdrawRecord);
		addMessage(redirectAttributes, "保存提现管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/withdrawRecord/?repage";
	}
	
	@RequiresPermissions("payment:withdrawRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(WithdrawRecord withdrawRecord, RedirectAttributes redirectAttributes) {
		withdrawRecordService.delete(withdrawRecord);
		addMessage(redirectAttributes, "删除提现管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/withdrawRecord/?repage";
	}

	@RequiresPermissions("payment:withdrawRecord:view")
	@RequestMapping(value = "audit")
	public String audit(WithdrawRecord withdrawRecord, HttpServletRequest request, HttpServletResponse response ,Model model) {
		model.addAttribute("auditStatus", request.getParameter("auditStatus"));
		model.addAttribute("withdrawRecord", withdrawRecord);
		if(UserUtils.getUser().isAdmin()){
			return "";
		}
		return "modules/payment/withdrawRecordForm";
	}

	@RequestMapping(value = "auditCtrl")
	public String auditCtrl(WithdrawRecord withdrawRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, withdrawRecord)){
			return form(withdrawRecord, model);
		}
		User user = UserUtils.getUser();
		withdrawRecord.setAuditPerson(user.getName());
		withdrawRecord.setAuditTime(new Date());
		withdrawRecordService.auditCtrl(withdrawRecord);
		//审核拒绝需要撤账
		if(WithdrawStatus.AUDREJ.getValue().equals(withdrawRecord.getStatus())){
			log.info("商户{}提现{}审核，审核拒绝，调用撤账接口参数：merchantId={},withdrawId={},transType={}",withdrawRecord.getMerchantId(),withdrawRecord.getWithdrawId(),TransType.WITHDRAW.getValue());
			withdrawRecordClient.revokeAccount(withdrawRecord.getMerchantId(), withdrawRecord.getWithdrawId(),TransType.WITHDRAW.getValue());
		    try{
		    	withdrawRecordServiceClient.recordRiskLog(String.valueOf(withdrawRecord.getMerchantId()),withdrawRecord.getWithdrawId());
		      log.info("商户{}提现{}审核，推送审核失败日志给清结算完成",withdrawRecord.getMerchantId(),withdrawRecord.getWithdrawId());
		    }catch(Exception e){
		    	log.info("商户{}提现{}审核，推送审核失败日志给清结算出错，错误信息={}",withdrawRecord.getMerchantId(),withdrawRecord.getWithdrawId(),e.getMessage());
		    }
		}
		log.info("商户{}提现{}审核,审核为{}完成",withdrawRecord.getMerchantId(), withdrawRecord.getWithdrawId(),withdrawRecord.getStatus());
		addMessage(redirectAttributes, "更新审核状态成功");
		return "redirect:"+Global.getAdminPath()+"/payment/withdrawRecord/?repage";
	}
}