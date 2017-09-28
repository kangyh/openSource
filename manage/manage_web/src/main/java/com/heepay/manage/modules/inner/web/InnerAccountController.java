package com.heepay.manage.modules.inner.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult.Status;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.enums.*;
import com.heepay.manage.modules.account.rpc.AccountOpClient;
import com.heepay.manage.modules.account.rpc.IAccountClient;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heepay.common.util.Constants.BillType;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.cache.PrimaryKeyCreator;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.inner.entity.InternalAccount;
import com.heepay.manage.modules.inner.service.InternalAccountService;
import com.heepay.manage.modules.subject.entity.Subject;
import com.heepay.manage.modules.subject.service.SubjectService;


/**
*
* 描    述：内部账户管理Controller
*
* 创 建 者：王亚洪
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
@RequestMapping(value = "${adminPath}/inner/innerAccount")
public class InnerAccountController extends BaseController {
  
  @Autowired
  private MerchantAccountService merchantAccountService;
  @Autowired
  private SubjectService subjectService;
  @Autowired
  private InternalAccountService internalAccountService;

  @Autowired
  private AccountOpClient accountOpClient;

  @Autowired
  private IAccountClient iAccountClient;

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
  
  
  @RequiresPermissions("inner:innerAccount:view")
  @RequestMapping(value = {"list", ""})
  public String list(MerchantAccount merchantAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
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
    
    //内部账户
    merchantAccount.setType(MerchantAccountType.INTERNAL_ACCOUNT.getValue());
    Page<MerchantAccount> page = merchantAccountService.findPage(new Page<MerchantAccount>(request, response), merchantAccount); 
    model.addAttribute("page", page);
    return "modules/inner/innerAccountList";
  }

  @RequiresPermissions("inner:innerAccount:view")
  @RequestMapping(value = "form")
  public String form(MerchantAccount merchantAccount, Model model) {
    model.addAttribute("merchantAccount", merchantAccount);
    return "modules/inner/innerAccountForm";
  }
  
  @RequiresPermissions("inner:innerAccount:view")
  @RequestMapping(value = "detail")
  public String detail(String accountId, Model model) {
    MerchantAccount merchantAccount = merchantAccountService.get(accountId);
    model.addAttribute("merchantAccount", merchantAccount);
    return "modules/inner/innerAccountDetail";
  }

  @RequiresPermissions("inner:innerAccount:edit")
  @RequestMapping(value = "save")
  public String save(MerchantAccount merchantAccount, String detailsTypeId, Model model, RedirectAttributes redirectAttributes) {
    if (!beanValidator(model, merchantAccount)){
      return form(merchantAccount, model);
    }
    
    String internalAccountTypeId = merchantAccount.getInternalAccountTypeId();
    String internalAccountDetailsTypeId = merchantAccount.getInternalAccountDetailsTypeId();
    String idStr;
    Date nowtime = new Date();
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    String accountCode = merchantAccount.getAccountCode();
    paramsMap.put("subjectCode", accountCode);
    Subject subject = subjectService.getByAccountCode(paramsMap);
    String startId = "6803";
    String endId = "6803";
    int level = Integer.parseInt(subject.getSubjectLevel());
    if(level == 2){
      String code = accountCode.substring(0, 3);
      startId = startId + code + "000000000";
      endId = endId + code + "999999999";
    }else{
      String code = accountCode.substring(0, 5);
      startId = startId + code + "0000000";
      endId = endId + code + "9999999";
    }
    paramsMap.clear();
    paramsMap.put("startId", startId);
    paramsMap.put("endId", endId);
    Map<String, Object> resultMap = internalAccountService.getMaxId(paramsMap);
    if(resultMap == null || resultMap.isEmpty()){
      idStr = "6803" + accountCode + merchantAccount.getBalanceDirection() + "156";
    }else{
      String serialNumber = (String) resultMap.get("internalAccountSerialNumber");
      String newSerial = serialNumber.substring(4, 12);
      if(level == 2){
        String firCode = newSerial.substring(0, 3);
        Integer secCode = Integer.parseInt(newSerial.substring(3, 5)) + 1;
        String lastCode = newSerial.substring(5, 8);
        idStr = "6803" + firCode + getNumString(String.valueOf(secCode)) + lastCode + merchantAccount.getBalanceDirection() + "156";
      }else{
        String code = String.valueOf(Integer.parseInt(newSerial) + 1);
        idStr = "6803" + code + merchantAccount.getBalanceDirection() + "156";
      }
    }
    
    merchantAccount.setAccountId(Long.parseLong(idStr));
    merchantAccount.setMerchantId(Long.parseLong(idStr));
    merchantAccount.setAccountTitle(subject.getSubjectName());    
    merchantAccount.setStatus(AccountStatus.NORMAL.getValue());
    merchantAccount.setVersion("0");
    if(InternalAccountType.BANK_ACCOUNT.getValue().equals(internalAccountTypeId) ||
            InternalAccountType.GENERATION_TECHNOLOGY.getValue().equals(internalAccountTypeId)){
      merchantAccount.setIsHot(CommonStatus.YES.getValue());
    }else{
      merchantAccount.setIsHot(CommonStatus.NO.getValue());
    }
    merchantAccount.setType(MerchantAccountType.INTERNAL_ACCOUNT.getValue());
    String initAmount = "0";
    merchantAccount.setBalanceAmount(initAmount);
    merchantAccount.setBalanceFreezedAmount(initAmount);
    merchantAccount.setBalanceAvailableAmount(initAmount);
    merchantAccount.setBalanceAvailableWithdrawAmount(initAmount);
    merchantAccount.setTotalInAmount(initAmount);
    merchantAccount.setTotalOutAmount(initAmount);
    merchantAccount.setRetainedAmount(initAmount);
    merchantAccount.setCreateTime(nowtime);
    merchantAccount.setUpdateTime(nowtime);
    merchantAccountService.save(merchantAccount);
    
    //插入InternalAccount表
    InternalAccount internalAccount = new InternalAccount();
    internalAccount.setInternalAccountSerialNumber(idStr);
    internalAccount.setInternalAccountName(merchantAccount.getAccountName());
    internalAccount.setInternalAccountTypeId(internalAccountTypeId);
    if(StringUtils.isEmpty(detailsTypeId)){
      internalAccount.setInternalAccountDetailsTypeId(internalAccountDetailsTypeId);
    }else{
      internalAccount.setInternalAccountDetailsTypeId(detailsTypeId);
    }
    internalAccount.setInternalAccountBalanceDirection(merchantAccount.getBalanceDirection());
    internalAccount.setCreateTime(nowtime);
    internalAccount.setUpdateTime(nowtime);
    internalAccount.setDescription(merchantAccount.getDescription());
    internalAccount.setRemark(internalAccount.getRemark());
    internalAccountService.save(internalAccount);

    //刷新内部账户缓存
    accountOpClient.refRefreshInterAccount();
    iAccountClient.refRefreshInterAccount();
    addMessage(redirectAttributes, "保存内部账户成功");
    return "redirect:"+Global.getAdminPath()+"/inner/innerAccount/?repage";
  }
  
  @RequiresPermissions("inner:innerAccount:edit")
  @RequestMapping(value = "delete")
  public String delete(MerchantAccount merchantAccount, RedirectAttributes redirectAttributes) {
    merchantAccountService.delete(merchantAccount);
    addMessage(redirectAttributes, "删除内部账户成功");
    return "redirect:"+Global.getAdminPath()+"/inner/innerAccount/?repage";
  }
  
  @RequiresPermissions("inner:innerAccount:view")
  @RequestMapping(value = "toUpdatePage")
  public String toUpdatePage(String accountId, Model model) {
    MerchantAccount merchantAccount = merchantAccountService.get(accountId);
    model.addAttribute("merchantAccount", merchantAccount);
    return "modules/inner/innerAccountUpdate";
  }
  
  @RequiresPermissions("inner:innerAccount:edit")
  @RequestMapping(value = "updateMerchantAccount")
  public String updateMerchantAccount(MerchantAccount merchantAccount, Model model, RedirectAttributes redirectAttributes){
    if (!beanValidator(model, merchantAccount)){
      return form(merchantAccount, model);
    }
    merchantAccount.setUpdateTime(new Date());
    merchantAccountService.updateMerchantAccount(merchantAccount);
    addMessage(redirectAttributes, "更新内部账户成功");
    return "redirect:"+Global.getAdminPath()+"/inner/innerAccount/?repage";
    
  }
  
  @RequiresPermissions("inner:innerAccount:edit")
  @RequestMapping(value = "updateMerchantAccountStatus")
  public String updateMerchantAccountStatus(String accountId, String flag, Model model, RedirectAttributes redirectAttributes){
    MerchantAccount merchantAccount = merchantAccountService.get(accountId);
    String message = null;
    if(StringUtils.equals(flag, AccountStatus.NORMAL.getValue())){
      message = "开启内部账户状成功";
      merchantAccount.setStatus(AccountStatus.NORMAL.getValue());
    }else{
      message = "禁用内部账户状成功";
      merchantAccount.setStatus(AccountStatus.CLOSED.getValue());
    }
    merchantAccount.setUpdateTime(new Date());
    merchantAccountService.updateMerchantAccount(merchantAccount);
    
    addMessage(redirectAttributes, message);
    return "redirect:"+Global.getAdminPath()+"/inner/innerAccount/?repage";
    
  }
  
  
  
  private String getNumString(String code){
    if(code.length() == 1){
      return "0" + code;
    }
    return code;
    
  }
  
  
}
