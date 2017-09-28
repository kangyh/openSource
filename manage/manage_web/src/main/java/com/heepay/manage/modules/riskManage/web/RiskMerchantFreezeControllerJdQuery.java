package com.heepay.manage.modules.riskManage.web;

import java.text.ParseException;
import java.util.Date;

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

import com.heepay.billingutils.IsNumeric;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.risk.RiskFreezeRemark;
import com.heepay.enums.risk.RiskFreezeType;
import com.heepay.enums.risk.RiskStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.risk.entity.RiskMerchantFreeze;
import com.heepay.manage.modules.risk.service.RiskMerchantFreezeService;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;


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
@RequestMapping(value = "${adminPath}/riskManage/RiskMerchantFreezeQueryJd/query")
public class RiskMerchantFreezeControllerJdQuery extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private  RiskMerchantFreezeService  riskMerchantFreezeService;
	
	
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
			
			//冻结审核通过' '解冻审核拒绝'
			riskMerchantFreeze.setStatusthree(RiskStatus.RISK_STATUS_1.getValue());//冻结审核通过
			riskMerchantFreeze.setStatusTwo(RiskStatus.RISK_STATUS_5.getValue());//解冻审核拒绝
			riskMerchantFreeze.setYes("Y");//表示需要开启多条件
			
			model = riskMerchantFreezeService.findRiskMerchantProductQuotaPage(new Page<RiskMerchantFreeze>(request, response), riskMerchantFreeze,model);
			
			return "modules/riskManage/riskMerchantFreezeListJdQuery";
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController list has a error:", e);
		}
		return "error/500";
	}
	
	/**
	 * 
	 * @方法说明：解冻申请页面跳转
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/tojdquery/{freezeId}/{pageNo}")
	public String updateEntity(RedirectAttributes redirectAttributes,
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
		
		model.addAttribute("riskMerchantFreeze", riskMerchantFreeze);
		model.addAttribute("referer", referer);
		model.addAttribute("pageNo", pageNo);
		logger.info("查询操作完成-------->{}"+riskMerchantFreeze.toString());
		
		return "modules/riskManage/riskMerchantFreezeJdQuery";
	}
	
	/**
	 * 
	 * @方法说明：解冻申请
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/tojd/{freezeId}")
	public String toUpdateEntity(RedirectAttributes redirectAttributes,
									RiskMerchantFreeze riskMerchantFreeze, 
			                        @PathVariable("freezeId") int freezeId,
								    @RequestParam("pageNo") int pageNo,
								    @RequestParam("referer") String referer,
			                        HttpServletRequest request,
			                        Model model) throws ParseException {
		
		
		
		logger.info("解冻申请操作-------->{}"+freezeId);
		
		riskMerchantFreeze.setFreezeId(freezeId);
		riskMerchantFreeze.setStatus(RiskStatus.RISK_STATUS_3.getValue());
		
		User user = UserUtils.getUser();
		//String name = user.getLoginName();
		String name = user.getName();
		
		logger.info("当前登录用户-------->{}"+name);
		riskMerchantFreeze.setUpdateAuthor(name);
		riskMerchantFreeze.setUpdateTime(new Date());
		
		int num = riskMerchantFreezeService.updateEntity(riskMerchantFreeze);
		
		if(num!=0){
			logger.info("解冻申请操作完成-------->{}"+num);
			addMessage(redirectAttributes, "解冻申请操作完成");
		}else{
			logger.info("解冻申请操作异常-------->{}"+num);
			addMessage(redirectAttributes, "解冻申请操作异常");
		}
	
		//页面跳转
		return refererMethod(pageNo, referer);
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
