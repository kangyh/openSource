/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.adjust.web;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heepay.common.util.Constants.AllocateRecordType;
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
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.util.FastDFSUtils;


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
@RequestMapping(value = "${adminPath}/adjust/adjustAccount")
public class AdjustAccountController extends BaseController {

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
	
	@RequiresPermissions("adjust:adjustAccount:view")
	@RequestMapping(value = {"list", ""})
	public String list(AdjustAccount adjustAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
	  if(StringUtil.isBlank(adjustAccount.getSortOrder())){
	    adjustAccount.setSortOrder(SortOrderType.DESC.getValue());
	  }
	  adjustAccount.setType(CommonStatus.NO.getValue());
		Page<AdjustAccount> page = adjustAccountService.findPage(new Page<AdjustAccount>(request, response), adjustAccount); 
		List<AdjustAccount> list = page.getList();
		if(list != null && !list.isEmpty()){
		  for(AdjustAccount account : list){
		    account.setStatus(AdjustAccountStatus.labelOf(account.getStatus()));
		  }
		}
		model.addAttribute("page", page);
		return "modules/adjust/adjustAccountList";
	}

	@RequiresPermissions("adjust:adjustAccount:view")
	@RequestMapping(value = "form")
	public String form(AdjustAccount adjustAccount, Model model) {
		model.addAttribute("adjustAccount", adjustAccount);
		return "modules/adjust/adjustAccountForm";
	}

	@RequiresPermissions("adjust:adjustAccount:edit")
	@RequestMapping(value = "save")
	public String save(AdjustAccount adjustAccount, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, adjustAccount)){
			return form(adjustAccount, model);
		}
		
		String[] senos = request.getParameterValues("seno");
		String[] accountIds = request.getParameterValues("accountId");
		String[] balanceDirections = request.getParameterValues("balanceDirection");
		String[] amounts = request.getParameterValues("amount");
		String[] remarks = request.getParameterValues("remark");
		String reason = request.getParameter("reason");
		int serialNum = senos.length;
		
		String dateStr = "14" + DateUtil.dateToString(new Date(), "yyyyMMddHHmm") + this.getSixRandomNum();
		Principal principal = UserUtils.getPrincipal();
		adjustAccount.setSerialNo(dateStr);
		adjustAccount.setSerialNum(String.valueOf(serialNum));
		adjustAccount.setStatus(AdjustAccountStatus.AUDING.getValue());
		adjustAccount.setType(CommonStatus.NO.getValue());
		adjustAccount.setReason(reason);
		adjustAccount.setCreator(principal.getLoginName());
		adjustAccount.setCreateTime(new Date());
		adjustAccount.setUpdateTime(adjustAccount.getCreateTime());
		adjustAccountService.save(adjustAccount);
		for(int i=0;i<serialNum;i++){
		  AdjustAccountDetail adjustAccountDetail = new AdjustAccountDetail();
		  adjustAccountDetail.setAdjustId(adjustAccount.getAdjustId());
		  adjustAccountDetail.setSeNo(senos[i]);
		  adjustAccountDetail.setAccountid(Long.parseLong(accountIds[i]));
		  adjustAccountDetail.setBalanceDirection(balanceDirections[i]);
		  adjustAccountDetail.setAmount(amounts[i]);
		  adjustAccountDetail.setRemark(remarks[i]);
		  adjustAccountDetail.setCreateTime(adjustAccount.getCreateTime());
		  adjustAccountDetail.setUpdateTime(adjustAccount.getCreateTime());
		  adjustAccountDetailService.save(adjustAccountDetail);
		}
		
		addMessage(redirectAttributes, "保存调账成功");
		return "redirect:"+Global.getAdminPath()+"/adjust/adjustAccount/?repage";
	}
	
	
	@RequiresPermissions("adjust:adjustAccount:view")
  @RequestMapping(value="details", method=RequestMethod.GET)
	public String details(String adjustId, HttpServletRequest request, HttpServletResponse response, Model model){
	  AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
	  List<AdjustAccountDetail> list = adjustAccountDetailService.getListByAdjustId(Long.parseLong(adjustId));
	  constructAdjustAccountDetail(list);
	  setImagePath(adjustAccount);
	  model.addAttribute("adjustAccount", adjustAccount);
	  model.addAttribute("list", list);
	  return "modules/adjust/adjustAccountDetails";
	}
	
	
  @RequiresPermissions("adjust:adjustAccount:view")
  @RequestMapping(value="getMerchantAccountByAccountIdAndType", method=RequestMethod.POST)
  public @ResponseBody MerchantAccount getMerchantAccountByAccountIdAndType(Long accountId, String type){
    return merchantAccountService.getMerchantAccountByAccountIdAndType(accountId, type);
    
  }
  
  @RequiresPermissions("adjust:adjustAccount:reviewlist")
  @RequestMapping(value="toReviewList")
  public String toReviewList(AdjustAccount adjustAccount, HttpServletRequest request, HttpServletResponse response, Model model){
    if(StringUtil.isBlank(adjustAccount.getSortOrder())){
      adjustAccount.setSortOrder(SortOrderType.DESC.getValue());
    }
    adjustAccount.setType(CommonStatus.NO.getValue());
    Page<AdjustAccount> page = adjustAccountService.findPage(new Page<AdjustAccount>(request, response), adjustAccount); 
    model.addAttribute("page", page);
    return "modules/adjust/adjustAccountReviewList";
  }
  
  @RequiresPermissions("adjust:adjustAccount:reviewlist")
  @RequestMapping(value="reviewDetails", method=RequestMethod.GET)
  public String reviewDetails(String adjustId, HttpServletRequest request, HttpServletResponse response, Model model){
    AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
    List<AdjustAccountDetail> list = adjustAccountDetailService.getListByAdjustId(Long.parseLong(adjustId));
    constructAdjustAccountDetail(list);
    setImagePath(adjustAccount);
    model.addAttribute("adjustAccount", adjustAccount);
    model.addAttribute("list", list);
    return "modules/adjust/adjustAccountReviewDetails";
  }
  
  @RequiresPermissions("adjust:adjustAccount:reviewlist")
  @RequestMapping(value="toReviewPage", method=RequestMethod.GET)
  public String toReviewPage(String adjustId, HttpServletRequest request, HttpServletResponse response, Model model){
    AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
    List<AdjustAccountDetail> list = adjustAccountDetailService.getListByAdjustId(Long.parseLong(adjustId));
    constructAdjustAccountDetail(list);
    setImagePath(adjustAccount);
    Principal principal = UserUtils.getPrincipal();
    model.addAttribute("adjustAccount", adjustAccount);
    model.addAttribute("list", list);
    model.addAttribute("loginName", principal.getLoginName());
    model.addAttribute("auditStatus", request.getParameter("auditStatus"));
    return "modules/adjust/adjustAccountReview";
  }
  
  
  
  @RequiresPermissions("adjust:adjustAccount:review")
  @RequestMapping(value="review")
  public String review(String adjustId, String flag, HttpServletRequest request){
    AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
    if(flag.equals(AllocateRecordType.REVIEW_PASS)){
      adjustAccount.setStatus(AdjustAccountStatus.ADOPT.getValue());
    }else{
      adjustAccount.setStatus(AdjustAccountStatus.REVOKE.getValue());
    }
    Principal principal = UserUtils.getPrincipal();
    adjustAccount.setAuditor(principal.getLoginName());
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
      adjustAccountServiceClient.adjustAccount(adjustAccount.getSerialNo(), adjustAccountMap);
    }
    return "redirect:"+Global.getAdminPath()+"/adjust/adjustAccount/toReviewList/?repage";
  }
  
  
  @RequiresPermissions("adjust:adjustAccount:reviewlist")
  @RequestMapping(value="toUploadPage", method=RequestMethod.GET)
  public String toUploadPage(String adjustId, Model model){
    AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
    model.addAttribute("adjustAccount", adjustAccount);
    return "modules/adjust/adjustAccountUpload";
  }
  
  @RequiresPermissions("adjust:adjustAccount:reviewlist")
  @RequestMapping(value="uploadFile")
  public String uploadFile(String adjustId, @RequestParam("file") MultipartFile file, HttpServletRequest request,
      HttpServletResponse response, RedirectAttributes redirectAttributes, Model model) throws Exception {
    AdjustAccount adjustAccount = adjustAccountService.get(adjustId);
    Principal principal = UserUtils.getPrincipal();
    // 上传图片
    String path;
    try {
      path = FastDFSUtils.uploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
    } catch (Exception e) {
      logger.error("{}上传图片时出错", adjustId, e);
      addMessage(redirectAttributes, "上传照片失败");
      return "redirect:"+Global.getAdminPath()+"/adjust/adjustAccount/?repage";
    }
    
    //路径保存到adjustAccount  -- 可以保存多张图片  用#隔开
    if(StringUtil.isBlank(adjustAccount.getFilePath())){
      adjustAccount.setFilePath(path);
    }else{
      adjustAccount.setFilePath(adjustAccount.getFilePath().concat("#").concat(path));
    }
    adjustAccount.setUpdateTime(new Date());
    adjustAccountService.updateAdjustAccount(adjustAccount);
    logger.info("用户{}上传了一张图片，地址为{}", principal.getLoginName(), domain + File.separator + path);
    addMessage(redirectAttributes, "上传照片成功");
    return "redirect:"+Global.getAdminPath()+"/adjust/adjustAccount/?repage";

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
        detail.setBalanceAmount(merchantAccount.getBalanceAmount());
        detail.setDirection(BalanceDirection.labelOf(detail.getBalanceDirection()));
        detail.setAmount(detail.getAmount().substring(0, detail.getAmount().length()-2));
      }
    }
  }
  
  
  /**
   * 
  * @description 设置filepath
  * @author 王亚洪       
  * @created 2016年12月26日 下午4:43:48     
  * @param adjustAccount
   */
  private void setImagePath(AdjustAccount adjustAccount){
    String filePath = adjustAccount.getFilePath();
    if(!StringUtil.isBlank(filePath)){
      List<String> images = Lists.newArrayList();
      String[] files = filePath.split("#");
      for(int i=0; i<files.length; i++){
        images.add(RandomUtil.getFastDfs(files[i]));
      }
      adjustAccount.setImages(images);
    }
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