package com.heepay.manage.modules.riskManage.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.billingutils.IsNumeric;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.risk.RiskFreezeRemark;
import com.heepay.enums.risk.RiskStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.risk.entity.RiskMerchantFreeze;
import com.heepay.manage.modules.risk.service.RiskMerchantFreezeService;
import com.heepay.manage.modules.riskManage.rpc.client.RiskOrederRpcClient;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.risk.util.DefreezeNo;
import com.heepay.rpc.account.model.MerchantFrozenRes;


/***
 * 
* 
* 描    述：商户冻结申请
*
* 创 建 者：wangl
* 创建时间：  Nov 18, 20167:14:38 PM
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
@RequestMapping(value = "${adminPath}/riskManage/RiskMerchantFreezeQuery")
public class RiskMerchantFreezeControllerAdd extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private  RiskMerchantFreezeService  riskMerchantFreezeService;
	
	@Autowired
	private RiskOrederRpcClient riskOrederRpcClient;
	
	@Autowired
	private MerchantCService merchantCService;
	
	
	
	
	// 添加的方法跳转
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/add")
	public String addEntity(RiskMerchantFreeze riskMerchantFreeze, 
							RedirectAttributes redirectAttributes,
							HttpServletRequest request,
							Model model) throws ParseException {
		
		//冻结/解冻原因 freeze_remark
		List<EnumBean> riskFreezeRemark = Lists.newArrayList();
		for (RiskFreezeRemark checkFlg : RiskFreezeRemark.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			riskFreezeRemark.add(ct);
		}
		model.addAttribute("riskFreezeRemark", riskFreezeRemark);
		model.addAttribute("riskMerchantFreeze", riskMerchantFreeze);
		return "modules/riskManage/riskMerchantFreezeAdd";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/add/ajax")
	public String ajaxaEntity(@RequestParam("merchantCode") String merchantCode){
		
		logger.info("根据编码查询商户是否存在-------->{}"+merchantCode);
		//String merchantJson="";
		
		Merchant merchant = merchantCService.get(merchantCode);
		/*try {
			merchantJson = client.getMerchantVO(merchantCode);
		} catch (Exception e) {
			logger.error("查询对象为空无法添加商户冻结-------->{消息为空}");
			
			return "风控服务链接异常";
		}*/
		
		//Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(merchantJson, Map.class);
		if(merchant !=null){
			//String merchantCompany = map.get("merchantCompany");
			String companyName = merchant.getCompanyName();
			if(StringUtils.isNotBlank(companyName)){
				
				return companyName+"+1";
			}else{
				return "查询对象为空无法添加商户冻结";
			}
		}else{
			logger.error("查询对象为空无法添加商户冻结-------->{查询错误,无法冻结}");
			return "商户编码不存在,无法冻结";
		}
	};
	
	
	// 账户余额冻结申请的方法
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/saveEntity")
	public String saveEntity(RiskMerchantFreeze riskMerchantFreeze, RedirectAttributes redirectAttributes,
							   HttpServletRequest request,Model model) throws Exception {
		
		String referer = request.getParameter("referer");
		String pageNo = request.getParameter("pageNo");
		
		logger.info("冻结操作-------->{}"+riskMerchantFreeze.getFreezeId());
		String remark1 = riskMerchantFreeze.getRemark1();
		String remark2 = riskMerchantFreeze.getRemark2();
		String remark3 = riskMerchantFreeze.getRemark3();
		String remark4 = riskMerchantFreeze.getRemark4();
		
		riskMerchantFreeze.setFreezeNo(DefreezeNo.getRandomString());//冻结编号 defreeze_no的随机数
		
		String merchantCode = riskMerchantFreeze.getMerchantCode();    //商户编码
		BigDecimal transAmount = riskMerchantFreeze.getTransAmount();  //冻结金额
		String freezeNo = riskMerchantFreeze.getFreezeNo();        //冻结单号
		String freezeRemark = riskMerchantFreeze.getFreezeRemark();    //冻结原因
		
		String applyNotes=remark1+","+remark2+","+remark3+","+remark4;    //申请备注可以添加交易单号
		
		User user = UserUtils.getUser();
		String name = user.getName();
		//String name = user.getLoginName();
		logger.info("当前登录用户-------->{}"+name);

		//String merchantJson="";
		
		Merchant merchant = merchantCService.get(merchantCode);
		String companyName ="";
		if(merchant != null){
			 //companyName = merchant.getCompanyName();
			 companyName = merchant.getEmail();
		}else{
			logger.error("查询对象为空无法添加商户冻结-------->{消息为空}");
			addMessage(redirectAttributes, "查询对象为空无法添加商户冻结");
			return "redirect:"+Global.getAdminPath()+"/riskManage/RiskMerchantFreezeQuery/"; 
		}
		
		/*try {
			merchantJson = client.getMerchantVO(merchantCode);
		} catch (Exception e) {
			logger.error("查询对象为空无法添加商户冻结-------->{消息为空}");
			addMessage(redirectAttributes, "查询对象为空无法添加商户冻结");
			return "redirect:"+Global.getAdminPath()+"/riskManage/RiskMerchantFreezeQuery/"; 
		}*/
		
		if(StringUtils.isNotBlank(companyName)){
			
			//冻结服务调取   没有交易单号  
			MerchantFrozenRes merchantFrozenRes = riskOrederRpcClient.getFrozenMerchantAmount(merchantCode,  freezeNo, transAmount.toString(),applyNotes, freezeRemark);
			int code = merchantFrozenRes.getCode();
			String msg = merchantFrozenRes.getMsg();
			
			if(InterfaceStatus.ACCOUNT_FROZEN_SUCCESS.getValue()==code){  //冻结账户成功 5600
				
				logger.info("调取方法查询对象商户冻结-------->{}"+companyName);
				//	Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(merchantJson, Map.class);
				//	String merchantName = map.get("merchantLoginName");
				
				logger.info("添加数据-------->{}"+riskMerchantFreeze.toString());
				riskMerchantFreeze.setCreateAuthor(name);//添加当前操作的用户名   
				riskMerchantFreeze.setMerchantName(companyName);//merchant_name
				
				riskMerchantFreeze.setStatus(RiskStatus.RISK_STATUS_0.getValue());// 0:冻结待审核 1：冻结审核通过  2：冻结审核拒绝 3:解冻待审核 4：解冻审核通过 5：解冻审核拒绝
				riskMerchantFreeze.setCreateTime(new Date());
				int num=riskMerchantFreezeService.saveEntity(riskMerchantFreeze);
				logger.info("添加数据成功-------->{}"+num);
				
				//addMessage(redirectAttributes, "添加成功");
				
			}else if(InterfaceStatus.ACCOUNT_FROZEN_FAILURE.getValue()==code){ // 5601, "冻结账户失败"
				//addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
				
			}else if(InterfaceStatus.MERCHANTID_PARAME_ERROR.getValue()==code){ // 5801, "商户Id不存在"
				//addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
			}
			
			addMessage(redirectAttributes, msg);
			//return "redirect:"+Global.getAdminPath()+"/riskManage/RiskMerchantFreezeQuery/add"; 
			
		}else{
			logger.info("查询对象为空无法添加商户冻结-------->{消息为空}");
			addMessage(redirectAttributes, "查询对象为空无法添加商户冻结");
		}
		
		if(StringUtils.isNotBlank(referer) && StringUtils.isNotBlank(pageNo)){
			
			//int pageNoTwo=Integer.parseInt(pageNo);
			//跳转地址
			//return refererMethod(pageNoTwo,referer);
			return "redirect:"+Global.getAdminPath()+"/riskManage/RiskMerchantFreezeOrderQuery/view";  
		}
		return "redirect:"+Global.getAdminPath()+"/riskManage/RiskMerchantFreezeQuery/add";  
		
	}
		
	// 根据消息请求头的地址返回原来的地址
	@SuppressWarnings("unused")
	private String refererMethod(int pageNo, String referer) {
		// 消息请求头的地址
		String substring = referer.substring(referer.length() - 1, referer.length());
		String toReferer = referer.substring(0, referer.length() - 1);
		
		
		// 判断这个是不是数字
		boolean flag = IsNumeric.isNumeric(substring);
		if (flag) {
			return "redirect:" + referer;
		}else if(!substring.equals("/")){
			return "redirect:" + referer + "?pageNo=" + pageNo;
		} else {
			return "redirect:" + toReferer + "?pageNo=" + pageNo;
		}
	}
}
