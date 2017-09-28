/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.allocate.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heepay.common.util.Constants.AllocateRecordType;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AllocateStatus;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.allocate.entity.AllocateRecord;
import com.heepay.manage.modules.allocate.service.AllocateRecordService;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.heepay.manage.modules.sys.utils.UserUtils;


/**
 *
 * 描    述：备付金调拨Controller
 *
 * 创 建 者： @author 王亚洪
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
@RequestMapping(value = "${adminPath}/allocate/allocateRecord")
public class AllocateRecordController extends BaseController {

	@Autowired
	private AllocateRecordService allocateRecordService;
	@Autowired
	private MerchantAccountService merchantAccountService;
	
	
	@ModelAttribute
	public AllocateRecord get(@RequestParam(required=false) String id) {
	  AllocateRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = allocateRecordService.get(id);
		}
		if (entity == null){
			entity = new AllocateRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("allocate:allocateRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(AllocateRecord allocateRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
	  if(StringUtil.isBlank(allocateRecord.getSortOrder())){
	    allocateRecord.setSortOrder(SortOrderType.DESC.getValue());
	  }
		Page<AllocateRecord> page = allocateRecordService.findPage(new Page<AllocateRecord>(request, response), allocateRecord); 
		List<AllocateRecord> list = page.getList();
		if(list != null && !list.isEmpty()){
		  for(AllocateRecord record : list){
		    constructAllocateRecord(record, false);
		  }
		}
		model.addAttribute("page", page);
		return "modules/allocate/allocateRecordList";
	}

	@RequiresPermissions("allocate:allocateRecord:view")
	@RequestMapping(value = "form")
	public String form(AllocateRecord allocateRecord, Model model) {
	  //获取入账备付金账户
	  List<MerchantAccount> merchantAccountList = Lists.newArrayList();
	  List<MerchantAccount> merchantAccountOneList = getMerchantAccountList(MerchantStatus.NORMAL.getValue(), AllocateRecordType.ACCOUNT_CODE_START,  AllocateRecordType.ACCOUNT_CODE_END);
	  List<MerchantAccount> merchantAccountTwoList = getMerchantAccountList(MerchantStatus.NORMAL.getValue(), AllocateRecordType.ACCOUNT_CODE_OTHER_START,  AllocateRecordType.ACCOUNT_CODE_OTHER_END);
	  if(merchantAccountOneList != null && !merchantAccountOneList.isEmpty()){
	    for(MerchantAccount merchantAccountOne : merchantAccountOneList){
	      merchantAccountList.add(merchantAccountOne);
	    }
	  }
	  if(merchantAccountTwoList != null && !merchantAccountTwoList.isEmpty()){
      for(MerchantAccount merchantAccountTwo : merchantAccountTwoList){
        merchantAccountList.add(merchantAccountTwo);
      }
    }
	  model.addAttribute("merchantAccountList", merchantAccountList);
		model.addAttribute("allocateRecord", allocateRecord);
		return "modules/allocate/allocateRecordForm";
	}

	@RequiresPermissions("allocate:allocateRecord:edit")
	@RequestMapping(value = "save")
	public String save(AllocateRecord allocateRecord, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
	  if (!beanValidator(model, allocateRecord)){
			return form(allocateRecord, model);
		}
	  String transNo = "15" + DateUtil.dateToString(new Date(), "yyyyMMddHHmm") + this.getSixRandomNum();
	  allocateRecord.setTransNo(transNo);
	  allocateRecord.setReserveInAccountId(allocateRecord.getReserveInAccountId());
	  Principal principal = UserUtils.getPrincipal();
	  allocateRecord.setCreator(principal.getLoginName());
	  MerchantAccount merchantAccount = merchantAccountService.get(allocateRecord.getReserveInAccountId().toString());
	  allocateRecord.setMerchantId(merchantAccount.getMerchantId());
	  allocateRecord.setStatus(AllocateStatus.AUDING.getValue());
	  allocateRecord.setCreateTime(new Date());
		allocateRecordService.save(allocateRecord);
		addMessage(redirectAttributes, "保存调拨成功");
		return "redirect:"+Global.getAdminPath()+"/allocate/allocateRecord/?repage";
	}
	
	
	@RequiresPermissions("allocate:allocateRecord:view")
  @RequestMapping(value = "details")
  public String details(String allocateId, HttpServletRequest request, HttpServletResponse response,Model model) {
	    AllocateRecord allocateRecord = allocateRecordService.get(allocateId);
	    model.addAttribute("allocateRecord", constructAllocateRecord(allocateRecord, true));
      return "modules/allocate/allocateRecordDetails";
  }
	
	@RequiresPermissions("allocate:allocateRecord:view")
	@RequestMapping(value = "getAmountByAccountId")
	public @ResponseBody String getAmountByAccountId(String accountId){
	  MerchantAccount merchantAccount = merchantAccountService.get(accountId);
	  return merchantAccount.getBalanceAmount();
	  
	}
	
	
	@RequestMapping(value="isAdminUser")
  public @ResponseBody boolean isAdminUser(){
    return UserUtils.getUser().isAdmin();
  }
	
	
	/**
	 * 
	* @description 获取备付金账户
	* @author  王亚洪       
	* @created 2016年12月15日 下午5:47:46     
	* @param status
	* @param accountCodeStart
	* @param accountCodeEnd
	* @return
	 */
	private List<MerchantAccount> getMerchantAccountList(String status, String accountCodeStart, String accountCodeEnd){
	  Map<String, Object> paramsMap = Maps.newHashMap();
    paramsMap.put("status", status);
    paramsMap.put("accountCodeStart", accountCodeStart);
    paramsMap.put("accountCodeEnd", accountCodeEnd);
    return merchantAccountService.getReserveAccount(paramsMap);
	}
	
	/**
   * 
  * @description 获取6位随机数字
  * @author 王亚洪       
  * @created 2016年12月23日 下午1:47:48     
  * @return
   */
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
	
	
	/**
	  * @description 构造AllocateRecord
	  * @author 王亚洪       
	  * @created 2016年12月15日 下午7:45:42     
	  * @param allocateId
	  * @return
	   */
	  private AllocateRecord constructAllocateRecord(AllocateRecord allocateRecord, boolean flag){
	    MerchantAccount inMerchantAccount = merchantAccountService.get(allocateRecord.getReserveInAccountId().toString());
	    MerchantAccount outMerchantAccount = merchantAccountService.get(allocateRecord.getReserveOutAccountId().toString());
	    allocateRecord.setReserveInAccountName(inMerchantAccount.getAccountName());
	    allocateRecord.setReserveOutAccountName(outMerchantAccount.getAccountName());
	    if(flag){
	      allocateRecord.setMerchantName(StringUtil.isBlank(inMerchantAccount.getMerchantLoginName()) ? "" : inMerchantAccount.getMerchantLoginName());
	    }
	    return allocateRecord;
	  }
	

}