package com.heepay.manage.modules.riskManage.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.billingutils.IsNumeric;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.risk.RiskFreezeRemark;
import com.heepay.enums.risk.RiskFreezeType;
import com.heepay.enums.risk.RiskStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
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
* 描    述：商户冻结/解冻表 Controller
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
@RequestMapping(value = "${adminPath}/riskManage/RiskMerchantFreezeQueryJd")
public class RiskMerchantFreezeControllerJd extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private  RiskMerchantFreezeService  riskMerchantFreezeService;
	
	@Autowired
	private RiskOrederRpcClient riskOrederRpcClient;
	
	
	/**
	 * 
	 * @方法说明：商户冻结信息查询
	 * @时间：2016年10月26日 下午3:26:17
	 * @创建人：wangl
	 */
	@RequiresPermissions("riskManage:riskMerchantFreeze:view")
	@RequestMapping(value = { "list", "" })
	public String list(RiskMerchantFreeze riskMerchantFreeze, 
					   HttpServletRequest request,
					   HttpServletResponse response, Model model) {
		try {
			logger.info("商户冻结信息查询----->{}"+riskMerchantFreeze.toString());
			
			riskMerchantFreeze.setStatus(RiskStatus.RISK_STATUS_3.getValue());//解冻待审核
			model = riskMerchantFreezeService.findRiskMerchantProductQuotaPage(new Page<RiskMerchantFreeze>(request, response), riskMerchantFreeze,model);
			
			return "modules/riskManage/riskMerchantFreezeListJd";
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController list has a error:", e);
		}
		return "error/500";
	}
	
	/**
	 * 
	 * @方法说明：解冻
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/jd/{freezeId}")
	public String djEntity(RedirectAttributes redirectAttributes,
						   RiskMerchantFreeze riskMerchant,
						   @PathVariable("freezeId") int freezeId,
						   @RequestParam("checkFlg") String checkFlg,
						   @RequestParam("pageNo") int pageNo,
						   @RequestParam("referer") String referer,
						   Model model) throws ParseException {
		
		logger.info("冻结操作-------->{}"+freezeId);
		
		if("Y".equals(checkFlg)){
			//获取整个对象
			RiskMerchantFreeze riskMerchantFreeze=riskMerchantFreezeService.getEntity(freezeId);
			
			String thawMessages = riskMerchant.getThawMessages();          //解冻申请理由
			String merchantCode = riskMerchantFreeze.getMerchantCode();    //商户编码
			String freezeNo = riskMerchantFreeze.getFreezeNo();        //冻结单号 freeze_no
			
			String defreezeNoNum = DefreezeNo.getRandomString();
			riskMerchant.setDefreezeNo(defreezeNoNum);		//生成解冻单号
			
			//冻结服务调取   没有交易单号  
			MerchantFrozenRes merchantFrozenRes = riskOrederRpcClient.thawMerchantAmount(merchantCode,  freezeNo, defreezeNoNum, thawMessages);
			int code = merchantFrozenRes.getCode();
			String msg = merchantFrozenRes.getMsg();
			
			if(InterfaceStatus.ACCOUNT_THAW_SUCCESS.getValue()==code){  //解冻 账户成功 5700
				riskMerchant.setStatus(RiskStatus.RISK_STATUS_4.getValue());//更新状态    解冻审核通过
				riskMerchant.setFreezeType(RiskFreezeType.RISK_FREEZE_TYPE_2.getValue());//freeze_type
				
				User user = UserUtils.getUser();
				//String name = user.getLoginName();
				String name = user.getName();
				
				logger.info("当前登录用户-------->{}"+name);
				riskMerchant.setUpdateAuthor(name);
				riskMerchant.setUpdateTime(new Date());
				int num=riskMerchantFreezeService.updateEntity(riskMerchant);
				
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+num);
			}else if(InterfaceStatus.ACCOUNT_THAW_FAILURE.getValue()==code){ // 5701, "冻结账户失败"
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
				
				
			}else if(InterfaceStatus.MERCHANTID_PARAME_ERROR.getValue()==code){ // 5801, "商户Id不存在"
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
			}else{
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
			}
			
			
		}else{
			
			riskMerchant.setStatus(RiskStatus.RISK_STATUS_5.getValue());//更新状态  解冻审核拒绝
			riskMerchant.setFreezeType(RiskFreezeType.RISK_FREEZE_TYPE_2.getValue());//freeze_type
			
			User user = UserUtils.getUser();
			//String name = user.getLoginName();
			String name = user.getName();
			
			logger.info("当前登录用户-------->{}"+name);
			riskMerchant.setUpdateAuthor(name);
			riskMerchant.setUpdateTime(new Date());
			int num=riskMerchantFreezeService.updateEntity(riskMerchant);
			
			addMessage(redirectAttributes, "解冻审核确认成功");
			logger.info("冻结操作完成-------->{}"+num);
		}
		
		// 页面跳转
		return refererMethod(pageNo, referer);
		
	}
	
	
	
	
	/**
	 * 
	 * @方法说明：查询操作
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/tojd/{freezeId}/{pageNo}")
	public String toUpdateEntity(RedirectAttributes redirectAttributes,
			                     @PathVariable("freezeId") int freezeId,
			                     @PathVariable("pageNo") int pageNo,
			                     HttpServletRequest request,
			                     Model model) throws ParseException {
		
		String referer = request.getHeader("referer");
		logger.info("查询操作-------->{}"+freezeId);
		RiskMerchantFreeze riskMerchantFreeze = riskMerchantFreezeService.getEntity(freezeId);
		//冻结/解冻状态 0:待审核 1：已冻结 2：已解冻 3：冻结失败 4：解冻失败
		if(StringUtils.isNotBlank(riskMerchantFreeze.getStatus())){
			riskMerchantFreeze.setStatus(RiskStatus.labelOf(riskMerchantFreeze.getStatus()));
		}
		//冻结类型 1：冻结 2：解冻
		if(StringUtils.isNotBlank(riskMerchantFreeze.getFreezeType())){
			riskMerchantFreeze.setFreezeType(RiskFreezeType.labelOf(riskMerchantFreeze.getFreezeType()));
		}
		//冻结/解冻原因 freeze_remark
		if(StringUtils.isNotBlank(riskMerchantFreeze.getFreezeRemark())){
			riskMerchantFreeze.setFreezeRemark(RiskFreezeRemark.labelOf(riskMerchantFreeze.getFreezeRemark()));
		}
		
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
		model.addAttribute("referer", referer);
		model.addAttribute("pageNo", pageNo);
		logger.info("查询操作完成-------->{}"+riskMerchantFreeze.toString());
		//return "modules/riskManage/riskMerchantFreezeDj";
	
		return "modules/riskManage/riskMerchantFreezeJd";
	}
	
	
	
	// 根据消息请求头的地址返回原来的地址
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
