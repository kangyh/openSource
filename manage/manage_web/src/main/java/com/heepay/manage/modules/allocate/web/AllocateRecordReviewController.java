/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.allocate.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.heepay.common.util.Constants.AllocateRecordType;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AllocateStatus;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.allocate.entity.AllocateRecord;
import com.heepay.manage.modules.allocate.rpc.client.AllocateServiceClient;
import com.heepay.manage.modules.allocate.service.AllocateRecordService;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.util.ExcelUtil;
import com.heepay.manage.modules.util.ExcelUtil2007;


/**
 *
 * 描    述：审核备付金调拨Controller
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
@RequestMapping(value = "${adminPath}/allocate/allocateRecordReview")
public class AllocateRecordReviewController extends BaseController {

	@Autowired
	private AllocateRecordService allocateRecordService;
	@Autowired
	private AllocateServiceClient allocateServiceClient;
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
	
	@RequiresPermissions("allocate:allocateRecordReview:view")
	@RequestMapping(value = {"list", ""})
	public String list(AllocateRecord allocateRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
	  if(StringUtil.isBlank(allocateRecord.getSortOrder())){
      allocateRecord.setSortOrder(SortOrderType.DESC.getValue());
    }
	  Page<AllocateRecord> page = allocateRecordService.findPage(new Page<AllocateRecord>(request, response), allocateRecord); 
		List<AllocateRecord> list = page.getList();
    if(list != null && !list.isEmpty()){
      for(AllocateRecord record : list){
        constructAllocateRecord(record, true);
      }
    }
		model.addAttribute("page", page);
		return "modules/allocate/allocateRecordReviewList";
	}

	@RequiresPermissions("allocate:allocateRecordReview:edit")
	@RequestMapping(value = "review")
	public String review(String allocateId, String remark, String flag, Model model, RedirectAttributes redirectAttributes) {
	  AllocateRecord allocateRecord = allocateRecordService.get(allocateId);
	  allocateRecord.setRemark(remark);
	  Principal principal = UserUtils.getPrincipal();
	  allocateRecord.setAuditor(principal.getLoginName());
	  allocateRecord.setUpdateTime(new Date());
	  String message = "";
	  if(StringUtils.equals(flag, AllocateRecordType.REVIEW_PASS)){
	    allocateRecord.setStatus(AllocateStatus.ADOPT.getValue());
	    message = "审核成功";
	    //审核成功，记账
      allocateServiceClient.allocationAccount(allocateRecord.getTransNo(), allocateRecord.getReserveOutAccountId(),
          allocateRecord.getReserveInAccountId(), allocateRecord.getAmount());
	  }else{
	    allocateRecord.setStatus(AllocateStatus.AUDREJ.getValue());
	    message = "审核拒绝";
	  }
	  addMessage(redirectAttributes, message);
		allocateRecordService.updateAllocateRecord(allocateRecord);
		return "redirect:"+Global.getAdminPath()+"/allocate/allocateRecordReview/?repage";
	}
	
	@RequiresPermissions("allocate:allocateRecordReview:view")
  @RequestMapping(value = "details")
  public String details(String allocateId, HttpServletResponse response,Model model) {
	    AllocateRecord allocateRecord = allocateRecordService.get(allocateId);
	    model.addAttribute("allocateRecord", constructAllocateRecord(allocateRecord, true));
      return "modules/allocate/allocateRecordReviewDetails";
  }
	
	@RequiresPermissions("allocate:allocateRecordReview:view")
  @RequestMapping(value = "toReviewPage")
  public String toReviewPage(String allocateId, HttpServletRequest request, HttpServletResponse response,Model model) {
	    AllocateRecord allocateRecord = allocateRecordService.get(allocateId);
      model.addAttribute("allocateRecord", constructAllocateRecord(allocateRecord, true));
      Principal principal = UserUtils.getPrincipal();
      model.addAttribute("loginName", principal.getLoginName());
      model.addAttribute("auditStatus", request.getParameter("auditStatus"));
      return "modules/allocate/allocateRecordReviewReview";
  }
	
	
	@RequestMapping(value="exportExcel")
	public void exportExcel(String allocateId, String status, HttpServletRequest request, HttpServletResponse response) throws Exception {
	  AllocateRecord allocateRecord = new AllocateRecord();
	  if(!StringUtil.isBlank(allocateId)){
	    allocateRecord.setAllocateId(Long.parseLong(allocateId));
	  }
	  if(!StringUtils.isBlank(status)){
	    allocateRecord.setStatus(status);
	  }
	  List<AllocateRecord> list = allocateRecordService.findList(allocateRecord);
	  if(list != null && !list.isEmpty()){
      for(AllocateRecord record : list){
        MerchantAccount inMerchantAccount = merchantAccountService.get(record.getReserveInAccountId().toString());
        MerchantAccount outMerchantAccount = merchantAccountService.get(record.getReserveOutAccountId().toString());
        record.setReserveInAccountName(inMerchantAccount.getAccountName());
        record.setReserveOutAccountName(outMerchantAccount.getAccountName());
      }
    }
	  
    String title = "头寸调拨明细";
    String[] headers = new String[] { "调拨流水号", "出账的备付金账户", "入账的备付金账户", "调拨金额", "原因", "审核状态", "操作人", "创建时间", "审核时间",
        "备注" };
    List<String[]> tableList = new ArrayList<String[]>();
    list.stream().forEach(row -> {
      String[] dataRow = new String[headers.length];
      dataRow[0] = String.valueOf(row.getTransNo());
      dataRow[1] = row.getReserveOutAccountName();
      dataRow[2] = row.getReserveInAccountName();
      dataRow[3] = row.getAmount();
      dataRow[4] = row.getReason();
      dataRow[5] = AllocateStatus.labelOf(row.getStatus());
      dataRow[6] = row.getCreator();
      dataRow[7] = DateUtil.dateToString(row.getCreateTime(), DateUtil.DATETIME_FORMAT);
      dataRow[8] = DateUtil.dateToString(row.getUpdateTime(), DateUtil.DATETIME_FORMAT);
      dataRow[9] = row.getRemark();
      tableList.add(dataRow);
    });

    String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
    ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, tableList);
	  
	}
	
	@RequestMapping(value="isAdminUser")
  public @ResponseBody boolean isAdminUser(){
    return UserUtils.getUser().isAdmin();
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