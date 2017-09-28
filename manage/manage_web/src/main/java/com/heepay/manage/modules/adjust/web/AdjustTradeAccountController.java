/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.adjust.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.TrialBalanceTransStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heepay.common.util.Constants.AllocateRecordType;
import com.heepay.common.util.Constants.BillType;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AdjustAccountStatus;
import com.heepay.enums.BalanceDirection;
import com.heepay.enums.CommonStatus;
import com.heepay.enums.MerchantAccountType;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.adjust.entity.AdjustAccount;
import com.heepay.manage.modules.adjust.entity.AdjustAccountDetail;
import com.heepay.manage.modules.adjust.rpc.client.AdjustAccountServiceClient;
import com.heepay.manage.modules.adjust.service.AdjustAccountDetailService;
import com.heepay.manage.modules.adjust.service.AdjustAccountService;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.trial.entity.TrialBalanceTrans;
import com.heepay.manage.modules.trial.service.TrialBalanceTransService;


/**
 *
 * 描    述：调账Controller
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
@RequestMapping(value = "${adminPath}/adjust/adjustTradeAccount")
public class AdjustTradeAccountController extends BaseController {

  @Value("#{task['fastdfs.image.host']}")
  private String domain;
	@Autowired
	private AdjustAccountService adjustAccountService;
	@Autowired
	private AdjustAccountDetailService adjustAccountDetailService;
	@Autowired
	private MerchantAccountService merchantAccountService;
	@Autowired
	private AdjustAccountServiceClient adjustAccountServiceClient;
	@Autowired
  private TrialBalanceTransService trialBalanceTransService;
	@Autowired
	private MerchantLogService merchantLogService;
	
	@ModelAttribute
	public AdjustAccount get(@RequestParam(required=false) String id) {
		AdjustAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = adjustAccountService.get(id);
		}
		if (entity == null){
			entity = new AdjustAccount();
		}
		return entity;
	}
	
	@RequiresPermissions("adjust:adjustTradeAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdjustAccount adjustAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
	  if(StringUtil.isBlank(adjustAccount.getSortOrder())){
	    adjustAccount.setSortOrder(SortOrderType.DESC.getValue());
	  }
		Page<AdjustAccount> page = adjustAccountService.findPage(new Page<AdjustAccount>(request, response), adjustAccount); 
		List<AdjustAccount> list = page.getList();
		if(list != null && !list.isEmpty()){
		  for(AdjustAccount account : list){
		    account.setStatus(AdjustAccountStatus.labelOf(account.getStatus()));
		  }
		}
		model.addAttribute("page", page);
		return "modules/adjust/adjustTradeAccountList";
	}

	@RequiresPermissions("adjust:adjustTradeAccount:view")
	@RequestMapping(value = "form")
	public String form(AdjustAccount adjustAccount, String trialBalanceTransId, Model model) {
	  TrialBalanceTrans trialBalanceTrans = trialBalanceTransService.get(trialBalanceTransId);
	  List<MerchantLog> merchantLogList = getMerchantLogList(trialBalanceTrans.getLogIds());
	  //最终的需要调账的MerchantLog集合
	  List<MerchantLog> resultList = Lists.newArrayList();
	  Map<String, List<MerchantLog>> mapList = Maps.newHashMap();
	  List<MerchantLog> mLogList;
	  //过滤
	  for(MerchantLog mLog : merchantLogList){
	    String accountMark = mLog.getAccountMark();
	    if(mapList.containsKey(accountMark)){
	      mapList.get(accountMark).add(mLog);
	    }else{
	      mLogList = Lists.newArrayList();
	      mLogList.add(mLog);
	      mapList.put(accountMark, mLogList);
	    }
	  }
	  //校验借贷是否相等
	  for(Map.Entry<String, List<MerchantLog>> entry : mapList.entrySet()){   
	    List<MerchantLog> entryList = entry.getValue();
	    BigDecimal outAmount = BigDecimal.ZERO;
	    BigDecimal inAmount = BigDecimal.ZERO;
	    for(MerchantLog mLogEntry : entryList){
	      if(mLogEntry.getBalanceDirection().equals(BillType.DEBIT)){
	        outAmount = outAmount.add(new BigDecimal(mLogEntry.getBalanceAmountChanges()));
	      }else{
	        inAmount = inAmount.add(new BigDecimal(mLogEntry.getBalanceAmountChanges()));
	      }
	    }
	    if(outAmount.compareTo(inAmount) != 0){
	      resultList.addAll(entryList);
	    }
	  }   
	  
	  //存储对应的logid，以便以后使用
	  List<String> logidList = Lists.newArrayList();
	  BigDecimal dAmount = BigDecimal.ZERO;
	  BigDecimal cAmount = BigDecimal.ZERO;
	  if(resultList != null && !resultList.isEmpty()){
	    for(MerchantLog merchantLogVo : resultList){
	      logidList.add(String.valueOf(merchantLogVo.getLogId()));
	      merchantLogVo.setBalanceAmount(new BigDecimal(merchantLogVo.getBalanceAmount()).setScale(2, RoundingMode.DOWN).toString());
	      merchantLogVo.setBalanceAmountChanges(new BigDecimal(merchantLogVo.getBalanceAmountChanges()).setScale(2, RoundingMode.DOWN).toString());
	      merchantLogVo.setType(merchantLogVo.getMerchantType());
        if(merchantLogVo.getBalanceDirection().equals(BillType.DEBIT)){
          dAmount = dAmount.add(new BigDecimal(merchantLogVo.getBalanceAmountChanges()));
        }else{
          cAmount = cAmount.add(new BigDecimal(merchantLogVo.getBalanceAmountChanges()));
        }
	    }
	  }
	  
	  model.addAttribute("dAmount", dAmount.setScale(2, RoundingMode.DOWN));
	  model.addAttribute("cAmount", cAmount.setScale(2, RoundingMode.DOWN));
	  model.addAttribute("totalCount", resultList.size() + 1);
	  model.addAttribute("merchantLogList", resultList);
	  model.addAttribute("logIds", StringUtil.join(",", logidList));
	  model.addAttribute("trialBalanceTransId", trialBalanceTransId);
		model.addAttribute("adjustAccount", adjustAccount);
		return "modules/adjust/adjustTradeAccountForm";
	}

	@RequiresPermissions("adjust:adjustTradeAccount:edit")
	@RequestMapping(value = "save")
	public String save(AdjustAccount adjustAccount, String trialBalanceTransId, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, adjustAccount)){
			return form(adjustAccount, trialBalanceTransId, model);
		}
		
		String[] senos = request.getParameterValues("seno");
		String[] accountIds = request.getParameterValues("accountId");
		String[] balanceDirections = request.getParameterValues("balanceDirection");
		String[] amounts = request.getParameterValues("amount");
		String reason = request.getParameter("reason");
		String logIds = request.getParameter("logIds");
		String totalCount = request.getParameter("totalCount");
		int startInt = Integer.parseInt(totalCount); 
		int serialNum = senos.length;
		MerchantLog merchantLog = merchantLogService.get(StringUtils.split(logIds, ",")[0]);
		String dateStr = "14" + DateUtil.dateToString(new Date(), "yyyyMMddHHmm") + this.getSixRandomNum();
		Principal principal = UserUtils.getPrincipal();
		adjustAccount.setSerialNo(dateStr);
		adjustAccount.setSerialNum(String.valueOf(serialNum-startInt));
		adjustAccount.setStatus(AdjustAccountStatus.AUDING.getValue());
		adjustAccount.setReason(reason);
		adjustAccount.setType(CommonStatus.YES.getValue());
		adjustAccount.setAccountMark(merchantLog.getAccountMark());
		adjustAccount.setTransNo(StringUtil.isBlank(merchantLog.getTransNo()) ? merchantLog.getSettleId() : merchantLog.getTransNo());
		adjustAccount.setTrialBalanceTransId(trialBalanceTransId);
		adjustAccount.setCreator(principal.getLoginName());
		adjustAccount.setCreateTime(new Date());
		adjustAccount.setUpdateTime(adjustAccount.getCreateTime());
		adjustAccountService.save(adjustAccount);
		for(int i=startInt;i<serialNum;i++){
		  AdjustAccountDetail adjustAccountDetail = new AdjustAccountDetail();
		  adjustAccountDetail.setAdjustId(adjustAccount.getAdjustId());
		  adjustAccountDetail.setSeNo(senos[i]);
		  adjustAccountDetail.setAccountid(Long.parseLong(accountIds[i]));
		  adjustAccountDetail.setBalanceDirection(balanceDirections[i]);
		  adjustAccountDetail.setAmount(amounts[i]);
		  adjustAccountDetail.setLogIds(logIds);
		  adjustAccountDetail.setCreateTime(adjustAccount.getCreateTime());
		  adjustAccountDetail.setUpdateTime(adjustAccount.getCreateTime());
		  adjustAccountDetailService.save(adjustAccountDetail);
		}
		
		TrialBalanceTrans trialBalanceTrans = trialBalanceTransService.get(trialBalanceTransId);
		trialBalanceTrans.setStatus(TrialBalanceTransStatus.INHAND.getValue());
		trialBalanceTrans.setUpdateTime(new Date());
		trialBalanceTransService.updateTrialBalanceTrans(trialBalanceTrans);
		addMessage(redirectAttributes, "保存调账成功");
		return "redirect:"+Global.getAdminPath()+"/adjust/adjustTradeAccountQuery/?repage";
	}
	
	
	@RequiresPermissions("adjust:adjustTradeAccount:view")
  @RequestMapping(value="details", method=RequestMethod.GET)
	public String details(String adjustId, HttpServletRequest request, HttpServletResponse response, Model model){
	  AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
	  List<AdjustAccountDetail> list = adjustAccountDetailService.getListByAdjustId(Long.parseLong(adjustId));
	  constructAdjustAccountDetail(list);
	  model.addAttribute("adjustAccount", adjustAccount);
	  model.addAttribute("list", list);
	  return "modules/adjust/adjustAccountDetails";
	}
	
	
  @RequiresPermissions("adjust:adjustTradeAccount:view")
  @RequestMapping(value="getMerchantAccountByAccountIdAndType", method=RequestMethod.POST)
  public @ResponseBody MerchantAccount getMerchantAccountByAccountIdAndType(Long accountId, String type){
    return merchantAccountService.getMerchantAccountByAccountIdAndType(accountId, type);
  }
  
  @RequiresPermissions("adjust:adjustTradeAccount:reviewlist")
  @RequestMapping(value="toReviewList")
  public String toReviewList(AdjustAccount adjustAccount, HttpServletRequest request, HttpServletResponse response, Model model){
    if(StringUtil.isBlank(adjustAccount.getSortOrder())){
      adjustAccount.setSortOrder(SortOrderType.DESC.getValue());
    }
    adjustAccount.setType(CommonStatus.YES.getValue());
    Page<AdjustAccount> page = adjustAccountService.findPage(new Page<AdjustAccount>(request, response), adjustAccount); 
    model.addAttribute("page", page);
    return "modules/adjust/adjustTradeAccountReviewList";
  }
  
  @RequiresPermissions("adjust:adjustTradeAccount:reviewlist")
  @RequestMapping(value="reviewDetails", method=RequestMethod.GET)
  public String reviewDetails(String adjustId, HttpServletRequest request, HttpServletResponse response, Model model){
    AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
    List<AdjustAccountDetail> list = adjustAccountDetailService.getListByAdjustId(Long.parseLong(adjustId));
    constructAdjustAccountDetail(list);
    model.addAttribute("adjustAccount", adjustAccount);
    model.addAttribute("list", list);
    return "modules/adjust/adjustTradeAccountReviewDetails";
  }
  
  @RequiresPermissions("adjust:adjustTradeAccount:reviewlist")
  @RequestMapping(value="toReviewPage", method=RequestMethod.GET)
  public String toReviewPage(String adjustId, HttpServletRequest request, HttpServletResponse response, Model model){
    AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
    List<AdjustAccountDetail> list = adjustAccountDetailService.getListByAdjustId(Long.parseLong(adjustId));
    constructAdjustAccountDetail(list);
    //查找对应的merchantLog记录--正常记账
    List<MerchantLog> merchantLogList = getMerchantLogList(list.get(0).getLogIds());
    int i = 1;
    for(MerchantLog merchantLog : merchantLogList){
      AdjustAccountDetail adjustAccountDetail = new AdjustAccountDetail();
      adjustAccountDetail.setSeNo(String.valueOf(i++));
      adjustAccountDetail.setAccountid(merchantLog.getAccountId());
      MerchantAccount merchantAccount = merchantAccountService.get(String.valueOf(merchantLog.getAccountId()));
      adjustAccountDetail.setAccountType(MerchantAccountType.labelOf(merchantAccount.getType()));
      adjustAccountDetail.setAccountName(merchantLog.getAccountName());
      adjustAccountDetail.setBalanceAmount(merchantLog.getBalanceAmount().substring(0, merchantLog.getBalanceAmount().length()-2));
      adjustAccountDetail.setDirection(BalanceDirection.labelOf(merchantLog.getBalanceDirection()));
      adjustAccountDetail.setAmount(merchantLog.getBalanceAmountChanges().substring(0, merchantLog.getBalanceAmountChanges().length()-2));
      adjustAccountDetail.setExplain("正常记账");
      list.add(adjustAccountDetail);
    }
    Collections.sort(list);
    model.addAttribute("adjustAccount", adjustAccount);
    model.addAttribute("list", list);
    Principal principal = UserUtils.getPrincipal();
    model.addAttribute("loginName", principal.getLoginName());
    model.addAttribute("auditStatus", request.getParameter("auditStatus"));
    return "modules/adjust/adjustTradeAccountReview";
  }
  
  @RequiresPermissions("adjust:adjustTradeAccount:review")
  @RequestMapping(value="review")
  public String review(String adjustId, String flag, String auditRemarkHidden, HttpServletRequest request){
    AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
    if(flag.equals(AllocateRecordType.REVIEW_PASS)){
      adjustAccount.setStatus(AdjustAccountStatus.ADOPT.getValue());
    }else{
      adjustAccount.setStatus(AdjustAccountStatus.REVOKE.getValue());
    }
    Principal principal = UserUtils.getPrincipal();
    adjustAccount.setAuditor(principal.getLoginName());
    adjustAccount.setAuditRemark(auditRemarkHidden);
    adjustAccount.setUpdateTime(new Date());
    adjustAccountService.updateAdjustAccount(adjustAccount);
    //更新后 判断状态是不是复核成功
    if(adjustAccount.getStatus().equals(AdjustAccountStatus.ADOPT.getValue())){
      Map<Long, String> adjustAccountMap = Maps.newHashMap();
      List<AdjustAccountDetail> list = adjustAccountDetailService.getListByAdjustId(Long.parseLong(adjustId));
      for(AdjustAccountDetail adjustAccountDetail : list){
        adjustAccountMap.put(adjustAccountDetail.getAccountid(),
        adjustAccountDetail.getBalanceDirection().concat("_").concat(adjustAccountDetail.getAmount()));
      }
      adjustAccountServiceClient.adjustAccountTransDimension(adjustAccount.getSerialNo(), adjustAccount.getTransNo(), adjustAccountMap, adjustAccount.getAccountMark());
      
      //调账成功，修改对应的试算平衡表
      TrialBalanceTrans trialBalanceTrans = trialBalanceTransService.get(adjustAccount.getTrialBalanceTransId());
      trialBalanceTrans.setStatus(TrialBalanceTransStatus.FINISH.getValue());
      trialBalanceTrans.setUpdateTime(new Date());
      trialBalanceTransService.updateTrialBalanceTrans(trialBalanceTrans);
      
    }else if(adjustAccount.getStatus().equals(AdjustAccountStatus.REVOKE.getValue())){//审核拒绝
      //审核拒绝，修改对应的试算平衡表
      TrialBalanceTrans trialBalanceTrans = trialBalanceTransService.get(adjustAccount.getTrialBalanceTransId());
      trialBalanceTrans.setStatus(TrialBalanceTransStatus.REJECT.getValue());
      trialBalanceTrans.setUpdateTime(new Date());
      trialBalanceTransService.updateTrialBalanceTrans(trialBalanceTrans);
    }
    return "redirect:"+Global.getAdminPath()+"/adjust/adjustTradeAccount/toReviewList/?repage";
  }
  
  
  @RequestMapping(value="isAdminUser")
  public @ResponseBody boolean isAdminUser(){
    return UserUtils.getUser().isAdmin();
  }
  
  
  
  /**
   * 
  * @description 构造AdjustAccountDetail
  * @author 王亚洪       
  * @created 2016年12月23日 上午10:19:27     
  * @param list
   */
  private void constructAdjustAccountDetail(List<AdjustAccountDetail> list){
    if(list != null && !list.isEmpty()){
      for(AdjustAccountDetail detail : list){
        MerchantAccount merchantAccount = merchantAccountService.get(String.valueOf(detail.getAccountid()));
        detail.setAccountType(MerchantAccountType.labelOf(merchantAccount.getType()));
        detail.setAccountName(merchantAccount.getAccountName());
        detail.setBalanceAmount(merchantAccount.getBalanceAmount().substring(0, merchantAccount.getBalanceAmount().length()-2));
        detail.setDirection(BalanceDirection.labelOf(detail.getBalanceDirection()));
        detail.setAmount(detail.getAmount().substring(0, detail.getAmount().length()-2));
        detail.setExplain("手动补账");
      }
    }
  }
  
  
  /*  
   * int compare(AdjustAccountDetail o1, AdjustAccountDetail o2) 返回一个基本类型的整型，  
   * 返回负数表示：o1 小于o2，  
   * 返回0 表示：o1和o2相等，  
   * 返回正数表示：o1大于o2。  
   */  
  public int compare(AdjustAccountDetail o1, AdjustAccountDetail o2) {  
      
      int seNo1 = Integer.parseInt(o1.getSeNo());
      int seNo2 = Integer.parseInt(o2.getSeNo());
      
      //按照学生的年龄进行升序排列  
      if(seNo1 > seNo2){  
          return 1;  
      }  
      if(seNo1 == seNo2){  
          return 0;  
      }  
      return -1;  
  }  
  
  
  
  /**
   * 
  * @description 根据id查询merchantLog
  * @author 王亚洪       
  * @created 2017年1月17日 上午10:12:44     
  * @param logId
  * @return
   */
  private List<MerchantLog> getMerchantLogList(String logId){
    String[] logIdStr = StringUtils.split(logId, ",");
    List<Long> logIds = Lists.newArrayList();
    for(String str : logIdStr){
      logIds.add(Long.parseLong(str));
    }
    Map<String, Object> paramsMap = Maps.newHashMap();
    paramsMap.put("logIds", logIds);
    return merchantLogService.getListByLogIds(paramsMap);
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
  

}