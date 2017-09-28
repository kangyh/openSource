/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.web;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.manage.modules.merchant.rpc.client.CheckDifferenceClient;
import com.heepay.manage.modules.trial.entity.TrialBalancePaymentDifferent;
import com.heepay.manage.modules.trial.service.TrialBalancePaymentDifferentService;


/**
 *
 * 描    述：交易和账务数据日终校验Controller
 *
 * 创 建 者： @author yangcl
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
@RequestMapping(value = "${adminPath}/trial/trialBalancePaymentDifferent")
public class TrialBalancePaymentDifferentController extends BaseController {

	@Autowired
	private CheckDifferenceClient checkDifferenceClient;
	@Autowired
	private TrialBalancePaymentDifferentService trialBalancePaymentDifferentService;
	
	@ModelAttribute
	public TrialBalancePaymentDifferent get(@RequestParam(required=false) String id) {
		TrialBalancePaymentDifferent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = trialBalancePaymentDifferentService.get(id);
		}
		if (entity == null){
			entity = new TrialBalancePaymentDifferent();
		}
		return entity;
	}
	
	@RequiresPermissions("trial:trialBalancePaymentDifferent:view")
	@RequestMapping(value = {"list", ""})
	public String list(TrialBalancePaymentDifferent trialBalancePaymentDifferent, HttpServletRequest request, HttpServletResponse response, Model model) {
		String beginAccountDate=request.getParameter("beginAccountDate");
		String endAccountDate=request.getParameter("endAccountDate");
		Page<TrialBalancePaymentDifferent> page = trialBalancePaymentDifferentService.findPage(new Page<TrialBalancePaymentDifferent>(request, response), trialBalancePaymentDifferent); 
		for(TrialBalancePaymentDifferent defferent:page.getList()){
			defferent.setTransType(TransType.getContentByValue(defferent.getTransType()));
		}
		
		if(!StringUtil.isBlank(beginAccountDate)){
			model.addAttribute("beginAccountDate",DateUtil.stringToDate(beginAccountDate,DateUtil.DATE_FORMAT_YYYYMMDD));
		}
		if(!StringUtil.isBlank(endAccountDate)){
			model.addAttribute("endAccountDate",DateUtil.stringToDate(endAccountDate,DateUtil.DATE_FORMAT_YYYYMMDD));
		}
		model.addAttribute("page", page);
		return "modules/trial/trialBalancePaymentDifferentList";
	}

	@RequiresPermissions("trial:trialBalancePaymentDifferent:view")
	@RequestMapping(value = "form")
	public String form(TrialBalancePaymentDifferent trialBalancePaymentDifferent, Model model) {
		model.addAttribute("trialBalancePaymentDifferent", trialBalancePaymentDifferent);
		return "modules/trial/trialBalancePaymentDifferentForm";
	}

	@RequiresPermissions("trial:trialBalancePaymentDifferent:edit")
	@RequestMapping(value = "save")
	public String save(TrialBalancePaymentDifferent trialBalancePaymentDifferent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, trialBalancePaymentDifferent)){
			return form(trialBalancePaymentDifferent, model);
		}
		trialBalancePaymentDifferentService.save(trialBalancePaymentDifferent);
		addMessage(redirectAttributes, "保存交易和账务数据日终校验成功");
		return "redirect:"+Global.getAdminPath()+"/trial/trialBalancePaymentDifferent/?repage";
	}
	
	@RequiresPermissions("trial:trialBalancePaymentDifferent:edit")
	@RequestMapping(value = "delete")
	public String delete(TrialBalancePaymentDifferent trialBalancePaymentDifferent, RedirectAttributes redirectAttributes) {
		trialBalancePaymentDifferentService.delete(trialBalancePaymentDifferent);
		addMessage(redirectAttributes, "删除交易和账务数据日终校验成功");
		return "redirect:"+Global.getAdminPath()+"/trial/trialBalancePaymentDifferent/?repage";
	}

	@RequiresPermissions("trial:trialBalancePaymentDifferent:edit")
	@RequestMapping(value = "reset")
	public String reset(TrialBalancePaymentDifferent trialBalancePaymentDifferent, RedirectAttributes redirectAttributes) {
		trialBalancePaymentDifferentService.delete(trialBalancePaymentDifferent);
		addMessage(redirectAttributes, "删除交易和账务数据日终校验成功");
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(DateUtil.stringToDate(trialBalancePaymentDifferent.getBeginAccountDate(),DateUtil.DATE_FORMAT_YYYYMMDD));

		Date begin=cal.getTime();
		Date end=DateUtil.stringToDate(trialBalancePaymentDifferent.getEndAccountDate(),DateUtil.DATE_FORMAT_YYYYMMDD);
		
		String yestDay=DateUtil.getYesterDay(DateUtil.DATE_FORMAT_YYYYMMDD);
		Date yd=DateUtil.stringToDate(yestDay,DateUtil.DATE_FORMAT_YYYYMMDD);
		if(end.getTime()>yd.getTime()){
			end=yd;
		}
		while(begin.getTime()<=end.getTime()){
			
			String accountDate=DateUtil.dateToString(cal.getTime(),DateUtil.DATE_FORMAT_YYYYMMDD);
			//刪除数据
			TrialBalancePaymentDifferent trialDelete=new TrialBalancePaymentDifferent();
			trialDelete.setAccountDate(accountDate);
			trialBalancePaymentDifferentService.delete(trialDelete);
			//调取RPC服务重新生成
			checkDifferenceClient.checkDifferencePaymentAndAccount(accountDate);
			//下一天
			cal.add(Calendar.DAY_OF_MONTH, 1);
			begin=cal.getTime();
		}
		return "redirect:"+Global.getAdminPath()+"/trial/trialBalancePaymentDifferent/?repage";
	}	
	
}