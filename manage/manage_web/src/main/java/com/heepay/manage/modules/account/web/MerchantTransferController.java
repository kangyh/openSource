/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.*;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.rpc.IAccountClient;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.util.FastDFSUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.account.entity.MerchantTransfer;
import com.heepay.manage.modules.account.service.MerchantTransferService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 *
 * 描    述：账户转账Controller
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
@RequestMapping(value = "${adminPath}/account/merchantTransfer")
public class MerchantTransferController extends BaseController {

	@Autowired
	private MerchantTransferService merchantTransferService;
	@Autowired
	private MerchantAccountService merchantAccountService;
	@Autowired
	private IAccountClient iAccountClient;
	@ModelAttribute
	public MerchantTransfer get(@RequestParam(required=false) String id) {
		MerchantTransfer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantTransferService.get(id);
		}
		if (entity == null){
			entity = new MerchantTransfer();
		}
		return entity;
	}
	
	@RequiresPermissions("account:merchantTransfer:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantTransfer merchantTransfer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page initPage = new Page<MerchantTransfer>(request, response);
		initPage.setOrderBy("create_time DESC");
		if(null == merchantTransfer.getBeginCreateTime() && null == merchantTransfer.getEndCreateTime()){
			merchantTransfer.setBeginCreateTime(new Date());
			merchantTransfer.setEndCreateTime(new Date());
		}
		Page<MerchantTransfer> page = merchantTransferService.findPage(initPage, merchantTransfer);
		model.addAttribute("page", page);
		return "modules/account/merchantTransferList";
	}

	@RequiresPermissions("account:merchantTransfer:view")
	@RequestMapping(value = "form")
	public String form(MerchantTransfer merchantTransfer, Model model) {
		model.addAttribute("merchantTransfer", merchantTransfer);
		return "modules/account/merchantTransferForm";
	}

	private MerchantAccount getMerchantAccount(String queryType,String queryCondition,String merchantType){
		MerchantAccount merchantAccount =null;
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("type", merchantType);
		if("1".equals(queryType)){//商户编码
			paramsMap.put("merchantId", queryCondition.trim());
		}
		if("2".equals(queryType)){//邮箱
			paramsMap.put("merchantLoginName", queryCondition.trim());
		}
		merchantAccount = merchantAccountService.getMerchantByType(paramsMap);
		return merchantAccount;
	}

	@RequiresPermissions("account:merchantTransfer:edit")
	@RequestMapping(value = "saveApply")
	public String saveApply(@RequestParam("file") MultipartFile file, HttpServletRequest request,
							HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
		String DqueryType = request.getParameter("DqueryType");
		String CqueryType = request.getParameter("CqueryType");
		String DqueryCondition = request.getParameter("DqueryCondition");
		String CqueryCondition = request.getParameter("CqueryCondition");
		String transferAmount = request.getParameter("transferAmount");
		String updWithDraw = request.getParameter("updWithDraw");
		String transferReason = request.getParameter("transferReason");

		//转账申请单
		MerchantTransfer merchantTransfer = new MerchantTransfer();
		String transferId = "14" + DateUtil.dateToString(new Date(), "yyyyMMddHHmm") + this.getSixRandomNum();
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		merchantTransfer.setTransferId(transferId);
		merchantTransfer.setTransferAmount(transferAmount);
		if(StringUtil.notBlank(updWithDraw)){
			merchantTransfer.setWithdrawFlag("0");
		}else{
			merchantTransfer.setWithdrawFlag("1");
		}
		merchantTransfer.setStatus(MerchantTransferStatus.AUDING.getValue());
		merchantTransfer.setCreator(principal.getLoginName());
		merchantTransfer.setCreateTime(new Date());
		merchantTransfer.setUpdateDate(new Date());
		merchantTransfer.setTransferReason(transferReason);
		MerchantAccount DMerchantAccount = getMerchantAccount(DqueryType, DqueryCondition, MerchantAccountType.MERCHANT_ACCOUNT.getValue());
		if(DMerchantAccount == null){
			logger.error("转出方账户不存在,{},申请失败", DqueryCondition);
			addMessage(redirectAttributes, "转出方编码("+DMerchantAccount.getMerchantId()+")账户不存在,申请失败");
			return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
		}
		if(MerchantStatus.CLOSED.getValue().equals(DMerchantAccount.getStatus())){
			logger.error("转出方账户已关闭,商户编码:{},申请失败", DMerchantAccount.getMerchantId());
			addMessage(redirectAttributes, "转出方编码("+DMerchantAccount.getMerchantId()+")账户已关闭,申请失败");
			return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
		}
		if(MerchantStatus.FREEZED.getValue().equals(DMerchantAccount.getStatus())){
			logger.error("转出方账户已冻结,商户编码:{},申请失败", DMerchantAccount.getMerchantId());
			addMessage(redirectAttributes, "转出方编码("+DMerchantAccount.getMerchantId()+")账户已冻结,申请失败");
			return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
		}
		if(new BigDecimal(DMerchantAccount.getBalanceAvailableAmount()).compareTo(new BigDecimal(transferAmount)) == -1){
			logger.error("转出方账户可用金额不足,商户编码:{},申请失败", DMerchantAccount.getMerchantId());
			addMessage(redirectAttributes, "转出方编码("+DMerchantAccount.getMerchantId()+")账户可用金额不足,申请失败");
			return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
		}
		merchantTransfer.setdMerchantId(String.valueOf(DMerchantAccount.getMerchantId()));
		merchantTransfer.setdAccountId(String.valueOf(DMerchantAccount.getAccountId()));
		merchantTransfer.setdAccountName(DMerchantAccount.getAccountName());
		MerchantAccount CMerchantAccount = getMerchantAccount(CqueryType, CqueryCondition, MerchantAccountType.MERCHANT_ACCOUNT.getValue());
		if(CMerchantAccount == null){
			logger.error("转入方账户不存在,申请失败", CqueryCondition);
			addMessage(redirectAttributes, "转入方("+CMerchantAccount.getMerchantId()+")账户不存在,申请失败");
			return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
		}
		if(MerchantStatus.CLOSED.getValue().equals(CMerchantAccount.getStatus())){
			logger.error("转入方账户已关闭,商户编码:{},申请失败", CMerchantAccount.getMerchantId());
			addMessage(redirectAttributes, "转入方编码("+CMerchantAccount.getMerchantId()+")账户已关闭,申请失败");
			return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
		}
		if(MerchantStatus.FREEZED.getValue().equals(CMerchantAccount.getStatus())){
			logger.error("转入方账户已冻结,商户编码:{},申请失败", CMerchantAccount.getMerchantId());
			addMessage(redirectAttributes, "转入方编码("+CMerchantAccount.getMerchantId()+")账户已冻结,申请失败");
			return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
		}
		merchantTransfer.setcMerchantId(String.valueOf(CMerchantAccount.getMerchantId()));
		merchantTransfer.setcAccountId(String.valueOf(CMerchantAccount.getAccountId()));
		merchantTransfer.setcAccountName(CMerchantAccount.getAccountName());
		if(CMerchantAccount.getMerchantId() == DMerchantAccount.getMerchantId()){
			logger.error("转入方和转出方商户编码不能是同一个,商户编码:({},{}),申请失败", DMerchantAccount.getMerchantId(),CMerchantAccount.getMerchantId());
			addMessage(redirectAttributes, "转入方和转出方商户编码不能是同一个,申请失败");
			return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
		}
		// 上传图片
		String path="";
		if(file.getSize() != 0){
			try {
				path = FastDFSUtils.uploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
			} catch (Exception e) {
				logger.error("{}上传图片时出错", transferId, e);
				addMessage(redirectAttributes, "上传照片失败");
				return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
			}
		}
		merchantTransfer.setFilePath(path);
		merchantTransfer.setOpResource(AllowSystemType.MANAGE_WEB.getValue());
		merchantTransferService.save(merchantTransfer);
		addMessage(redirectAttributes, "保存商户转账申请成功");
		return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
	}


	@RequiresPermissions("account:merchantTransfer:view")
	@RequestMapping(value = "getByAdjustId")
	public String getByAdjustId(MerchantTransfer merchantTransfer, HttpServletRequest request, HttpServletResponse response, Model model) {
		String transferId = request.getParameter("id");
		merchantTransfer.setTransferId(transferId);
		String adjust = request.getParameter("adjust");
		if ("true".equals(adjust)) {
			model.addAttribute("adjust", true);
		}
		merchantTransfer = get(transferId);
		model.addAttribute("merchantTransfer", merchantTransfer);
		return "modules/account/merchantTransferDetail";
	}

	@RequiresPermissions("account:merchantTransfer:edit")
	@RequestMapping(value = "save")
	public String save(MerchantTransfer merchantTransfer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantTransfer)){
			return form(merchantTransfer, model);
		}
		merchantTransferService.save(merchantTransfer);
		addMessage(redirectAttributes, "保存账户转账成功");
		return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
	}

	@RequiresPermissions("account:merchantTransfer:edit")
	@RequestMapping(value = "adjust")
	public String adjust(MerchantTransfer merchantTransfer, HttpServletRequest request,Model model, RedirectAttributes redirectAttributes) {
		String transferId = request.getParameter("transferId");
		String auditRemark = request.getParameter("auditRemark");
		String creator = request.getParameter("creator");
		String status = request.getParameter("status");
		merchantTransfer.setTransferId(transferId);
		merchantTransfer.setAuditRemark(auditRemark);
		merchantTransfer.setAuditTime(new Date());
		merchantTransfer.setStatus(status);
		SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
		merchantTransfer.setAuditor(principal.getLoginName());
		if(principal.getLoginName().equals(creator)){
			addMessage(redirectAttributes, "单据"+transferId+"审核被拒绝,发起人和审核人不能是同一人");
		}else{
			MerchantTransfer merchantTransfer1 = get(transferId);
			if(MerchantTransferStatus.ADOPT.getValue().equals(status)){
				String result = iAccountClient.recordTransferSign(Long.parseLong(merchantTransfer1.getcMerchantId()),Long.parseLong(merchantTransfer1.getdMerchantId()),"",merchantTransfer1.getTransferId(),
						merchantTransfer1.getTransferAmount(),String.valueOf(BigDecimal.ZERO),true, AllowSystemType.MANAGE_WEB.getValue());
				if(String.valueOf(InterfaceStatus.SUCCESS.getValue()).equals(result) || String.valueOf(InterfaceStatus.ALREADY.getValue()).equals(result)){
					merchantTransferService.updateStatus(merchantTransfer);
					addMessage(redirectAttributes, "更新单据状态成功");
				}else{
					addMessage(redirectAttributes, "账务处理异常,更新单据状态失败");
				}
			}else{
				merchantTransferService.updateStatus(merchantTransfer);
				addMessage(redirectAttributes, "更新单据状态成功");
			}
		}
		return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
	}


	@RequiresPermissions("account:merchantTransfer:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantTransfer merchantTransfer, RedirectAttributes redirectAttributes) {
		merchantTransferService.delete(merchantTransfer);
		addMessage(redirectAttributes, "删除账户转账成功");
		return "redirect:"+Global.getAdminPath()+"/account/merchantTransfer/?repage";
	}

	private String getSixRandomNum(){
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < 6; i++) {
			result = result * 10 + array[i];
		}
		return String.valueOf(result);
	}

}