/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.MerchantAccountType;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.SortOrderType;
import com.heepay.enums.SubjectIsLast;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.ZtreeNode;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.subject.entity.Subject;
import com.heepay.manage.modules.subject.service.SubjectService;

import java.util.*;


/**
 *
 * 描    述：账户管理Controller
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
@RequestMapping(value = "${adminPath}/account/accountQuery")
public class MerchantAccountController extends BaseController {

	@Autowired
	private MerchantAccountService merchantAccountService;
	@Autowired
	private SubjectService subjectService;
	
	@ModelAttribute
	public MerchantAccount get(@RequestParam(required=false) String id) {
		MerchantAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantAccountService.get(id);
		}
		if (entity == null){
			entity = new MerchantAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("account:merchantAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantAccount merchantAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
//		if(StringUtil.isBlank(merchantAccount.getType())){
//			merchantAccount.setType(MerchantAccountType.MERCHANT_ACCOUNT.getValue());
//		}
		if(merchantAccount.getSortOrder() == null){
			//默认按照创建时间降序
			merchantAccount.setSortOrder(SortOrderType.DESC.getValue());
		}
		
		String accountCodesHidden = merchantAccount.getAccountCodesHidden();
		List<String> accCodesList = Lists.newArrayList();
		if(StringUtil.notBlank(accountCodesHidden)){
		  String[] accountCodes = accountCodesHidden.split(",");
		  accCodesList = Arrays.asList(accountCodes);
		}
		merchantAccount.setAccCodes(accCodesList);
		Page<MerchantAccount> page = merchantAccountService.findPage(new Page<MerchantAccount>(request, response), merchantAccount);
		model.addAttribute("page", page);
		return "modules/account/merchantAccountList";
	}

	@RequiresPermissions("account:merchantAccount:splManualAccount")
	@RequestMapping(value = "onSupplementManualAccount", method= RequestMethod.GET)
	public String onSupplementManualAccount(MerchantAccount merchantAccount, Model model) {
		merchantAccountService.onSupplementManualAccount();
		return "redirect:"+Global.getAdminPath()+"/account/accountQuery/?repage";
	}

	@RequiresPermissions("account:merchantAccount:splManualAccount")
	@RequestMapping(value = "gotoSupplementAccount", method= RequestMethod.POST)
	public String gotoSupplementAccount(MerchantAccount merchantAccount, Model model) {
		List<MerchantAccount> merchantAccounts = merchantAccountService.getMerchantIdGroupByMerchantId();
		Map<String,String> map = new HashMap<String,String>();
		for(MerchantAccount mMerchantAccount : merchantAccounts){
			String sourceStr="";
			if(map.get(String.valueOf(mMerchantAccount.getMerchantId())) != null){
				sourceStr = map.get(String.valueOf(mMerchantAccount.getMerchantId()));
			}
			if(!"".equals(sourceStr)){
				sourceStr = sourceStr.concat(",");
			}
			sourceStr = sourceStr.concat(mMerchantAccount.getAccountId().toString()).concat(" ").concat(mMerchantAccount.getAccountName());
			map.put(String.valueOf(mMerchantAccount.getMerchantId()),sourceStr);
		}
		model.addAttribute("supplementAccountMap", map);
		return "modules/account/supplementAccountList";
	}

	@RequiresPermissions("account:merchantAccount:view")
	@RequestMapping(value = "form")
	public String form(MerchantAccount merchantAccount, Model model) {
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountDetail";
	}

	@RequiresPermissions("account:merchantAccount:view")
	@RequestMapping(value = "toThaw")
	public String toThaw(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountThaw";
	}

	@RequiresPermissions("account:merchantAccount:view")
	@RequestMapping(value = "toFrezed")
	public String toFrezed(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountFrezed";
	}

	@RequiresPermissions("account:merchantAccount:view")
	@RequestMapping(value = "toEnable")
	public String toEnable(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountEnable";
	}

	@RequiresPermissions("account:merchantAccount:view")
	@RequestMapping(value = "toClosed")
	public String toClosed(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountClosed";
	}

	@RequiresPermissions("account:merchantAccount:view")
	@RequestMapping(value = "toFrezedBalanceAmount")
	public String toFrezedBalanceAmount(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountClosed";
	}

	@RequiresPermissions("account:merchantAccount:view")
	@RequestMapping(value = "toWhawBalanceAmount")
	public String toWhawBalanceAmount(MerchantAccount merchantAccount, Model model,HttpServletRequest request, HttpServletResponse response) {
		String accountId = request.getParameter("accountId");
		merchantAccount = merchantAccountService.get(accountId);
		model.addAttribute("merchantAccount", merchantAccount);
		return "modules/account/merchantAccountClosed";
	}

	@RequestMapping(value = "frezed")
	public String frezed(MerchantAccount merchantAccount, Model model,RedirectAttributes redirectAttributes) {
		merchantAccount.setStatus(MerchantStatus.FREEZED.getValue());
		merchantAccountService.updateStatus(merchantAccount);
		addMessage(redirectAttributes, "账户冻结成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountQuery/?repage";
	}

	@RequestMapping(value = "enable")
	public String enable(MerchantAccount merchantAccount, Model model,RedirectAttributes redirectAttributes) {
		merchantAccount.setStatus(MerchantStatus.NORMAL.getValue());
		merchantAccountService.updateStatus(merchantAccount);
		addMessage(redirectAttributes, "账户启用成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountQuery/?repage";
	}
	@RequestMapping(value = "thaw")
	public String thaw(MerchantAccount merchantAccount, Model model,RedirectAttributes redirectAttributes) {
		merchantAccount.setStatus(MerchantStatus.NORMAL.getValue());
		merchantAccountService.updateStatus(merchantAccount);
		addMessage(redirectAttributes, "账户解冻成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountQuery/?repage";
	}

	@RequestMapping(value = "closed")
	public String closed(MerchantAccount merchantAccount, Model model,RedirectAttributes redirectAttributes) {
		merchantAccount.setStatus(MerchantStatus.CLOSED.getValue());
		merchantAccountService.updateStatus(merchantAccount);
		addMessage(redirectAttributes, "账户关闭成功");
		return "redirect:"+Global.getAdminPath()+"/account/accountQuery/?repage";
	}

	@RequiresPermissions("account:merchantAccount:edit")
	@RequestMapping(value = "save")
	public String save(MerchantAccount merchantAccount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantAccount)){
			return form(merchantAccount, model);
		}
		merchantAccountService.save(merchantAccount);
		addMessage(redirectAttributes, "保存账户管理成功");
		return "redirect:"+Global.getAdminPath()+"/account/merchantAccount/?repage";
	}
	
	@RequiresPermissions("account:merchantAccount:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantAccount merchantAccount, RedirectAttributes redirectAttributes) {
		merchantAccountService.delete(merchantAccount);
		addMessage(redirectAttributes, "删除账户管理成功");
		return "redirect:"+Global.getAdminPath()+"/account/merchantAccount/?repage";
	}

	
	/**
	 * 
	* @description 跳转到选择科目树形页面
	* @author 王亚洪  
	* @created 2017年1月5日 下午5:12:20     
	* @return
	 */
	@RequiresPermissions("account:merchantAccount:view")
  @RequestMapping(value = "toSelectSubjectTreePage")
	public String toSelectSubjectTreePage(){
	  return "modules/subject/subjectCodeList";
	}
	
	
	
	/**
	 * 
	* @description 获取科目数据--前端树形展示
	* @author 王亚洪       
	* @created 2017年1月5日 下午4:54:07     
	* @param model
	* @return
	 */
	@RequiresPermissions("account:merchantAccount:view")
  @RequestMapping(value = "getSubjectCodes", method=RequestMethod.POST)
	public @ResponseBody List<ZtreeNode> getSubjectCodes(Model model){
	  List<ZtreeNode> ztreeNodeList = Lists.newArrayList();
	  List<Subject> subjectList = subjectService.findList(null);
	  if(subjectList != null && !subjectList.isEmpty()){
	    for(Subject subject : subjectList){
	      ZtreeNode ztreeNode = new ZtreeNode();
	      long parentSubjectId = subject.getParentSubjectId();
	      if(parentSubjectId == 0){
	        ztreeNode.setpId(0);
	      }else{
	        Subject parentSubject = subjectService.get(String.valueOf(parentSubjectId));
	        ztreeNode.setpId(Integer.parseInt(parentSubject.getSubjectCode()));
	      }
	      String isLast = subject.getIsLast();
	      if(StringUtils.equals(isLast, SubjectIsLast.YES.getValue())){
	        ztreeNode.setNocheck(false);
	      }else{
	        ztreeNode.setNocheck(true);
	      }
	      ztreeNode.setId(Integer.parseInt(subject.getSubjectCode()));
	      ztreeNode.setName(subject.getSubjectName());
	      ztreeNodeList.add(ztreeNode);
	    }
	  }
	  model.addAttribute("ztreeNodeList", ztreeNodeList);
	  return ztreeNodeList;
	}

	@RequestMapping(value="getMerchantByType", method=RequestMethod.POST)
	public @ResponseBody MerchantAccount getMerchantByType(String queryType,String queryCondition,String merchantType){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("type", merchantType);
		if("1".equals(queryType)){//商户编码
			paramsMap.put("merchantId", queryCondition.trim());
		}
		if("2".equals(queryType)){//邮箱
			paramsMap.put("merchantLoginName", queryCondition.trim());
		}
		return merchantAccountService.getMerchantByType(paramsMap);
	}
}