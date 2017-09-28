/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.MerchantAccountDailyClient;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.param.AccountSubjectSum;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import com.heepay.manage.modules.trial.entity.MerchantAccountDaily;
import com.heepay.manage.modules.trial.entity.TrialBalanceDay;
import com.heepay.manage.modules.trial.service.MerchantAccountDailyService;
import com.heepay.manage.modules.trial.service.MerchantAccountMonthDailyService;
import com.heepay.manage.modules.trial.service.TrialBalanceDayService;
import com.heepay.payment.enums.AccountSubjects;
import com.heepay.payment.enums.MerchantAccountDirection;
import com.heepay.payment.vo.AccountSubjectVO;

/**
 *
 * 描 述：试算平衡-汇总Controller
 *
 * 创 建 者： @author 杨春龙 创建时间： 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/trial/trialBalanceDay")
public class TrialBalanceDayController extends BaseController {

	@Autowired
	private TrialBalanceDayService trialBalanceDayService;
	@Autowired
	private MerchantAccountDailyService merchantAccountDailyService;
	@Autowired
	private MerchantLogService merchantLogService;
	@Autowired
	private MerchantAccountMonthDailyService merchantAccountMonthDailyService;

	@Autowired
	private MerchantAccountDailyClient merchantAccountDailyClient;

	@ModelAttribute
	public TrialBalanceDay get(@RequestParam(required = false) String id) {
		TrialBalanceDay entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = trialBalanceDayService.get(id);
		}
		if (entity == null) {
			entity = new TrialBalanceDay();
		}
		return entity;
	}

	@RequiresPermissions("trial:trialBalanceDay:view")
	@RequestMapping(value = { "list", "" })
	public String list(TrialBalanceDay trialBalanceDay, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		Page<TrialBalanceDay> page = trialBalanceDayService.findPage(new Page<TrialBalanceDay>(request, response),
				trialBalanceDay);
		page.setOrderBy("id desc");
		model.addAttribute("page", page);
		model.addAttribute("beginAccountDate", DateUtil.stringToDate(trialBalanceDay.getBeginAccountDate(),"yyyy-MM-dd"));
		model.addAttribute("endAccountDate", DateUtil.stringToDate(trialBalanceDay.getEndAccountDate(),"yyyy-MM-dd"));
		return "modules/trial/trialBalanceDayList";
	}

	@RequiresPermissions("trial:trialBalanceDay:view")
	@RequestMapping(value = "form")
	public String form(MerchantAccountDaily merchantAccountDaily, Model model) {
		
		// 获取所有商户本期的 期初余额和期末余额
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (StringUtil.isBlank(merchantAccountDaily.getAccountDate())) {
			queryMap.put("accountDate", DateUtil.getYesterDay("yyyy-MM-dd"));
		} else {
			queryMap.put("accountDate", merchantAccountDaily.getAccountDate());
		}
		model.addAttribute("accountDate", queryMap.get("accountDate"));
		List<MerchantAccountDaily> subject_list = merchantAccountDailyService.findSubjectFinanicalState(queryMap);

		Map<String, MerchantAccountDaily> map = new HashMap<String, MerchantAccountDaily>();

		for (MerchantAccountDaily daily : subject_list) {
			// 获取账户的会计科目
			String type = daily.getAccountId().toString().substring(4, 7);
			MerchantAccountDaily mad = null;
			if (map.containsKey(type)) {
				mad = map.get(type);
			} else {
				mad = new MerchantAccountDaily();
				mad.setDebitEndingBalances("0");// 借方期末
				mad.setCreditEndingBalances("0");// 贷方期末
				mad.setDebitBeginningBalances("0");// 借方期末
				mad.setCreditBeginningBalances("0");// 贷方期初
				mad.setDebitCurrentAmount("0");// 借方期末
				mad.setCreditCurrentAmount("0");// 贷方期初
				map.put(type, mad);
			}
			BigDecimal debitEnding = new BigDecimal(daily.getDebitEndingBalances());// 借方期末
			BigDecimal creditEnding = new BigDecimal(daily.getCreditEndingBalances());// 贷方期末
			BigDecimal debitBegin = new BigDecimal(daily.getDebitBeginningBalances());// 借方期末
			BigDecimal creditBegin = new BigDecimal(daily.getCreditBeginningBalances());// 贷方期初
			BigDecimal debitCurrent = new BigDecimal(daily.getDebitCurrentAmount());// 借方发生额
			BigDecimal creditCurrent = new BigDecimal(daily.getCreditCurrentAmount());// 贷方发生额

			debitEnding = debitEnding.add(new BigDecimal(mad.getDebitEndingBalances()));
			creditEnding = creditEnding.add(new BigDecimal(mad.getCreditEndingBalances()));
			debitBegin = debitBegin.add(new BigDecimal(mad.getDebitBeginningBalances()));
			creditBegin = creditBegin.add(new BigDecimal(mad.getCreditBeginningBalances()));
			debitCurrent = debitCurrent.add(new BigDecimal(mad.getDebitCurrentAmount()));
			creditCurrent = creditCurrent.add(new BigDecimal(mad.getCreditCurrentAmount()));
			// 根据会计科目的类型 汇总期初和期末
			mad.setDebitEndingBalances(debitEnding.toString());// 借方期末
			mad.setCreditEndingBalances(creditEnding.toString());// 贷方期末
			mad.setDebitBeginningBalances(debitBegin.toString());// 借方期末
			mad.setCreditBeginningBalances(creditBegin.toString());// 贷方期初
			mad.setDebitCurrentAmount(debitCurrent.toString());// 借方期末
			mad.setCreditCurrentAmount(creditCurrent.toString());// 贷方期初
			mad.setSubjectType(type);
		}

		List<AccountSubjectSum> result = new ArrayList<AccountSubjectSum>();
		//余额  
		BigDecimal assetClass=BigDecimal.ZERO;//资产
		BigDecimal debtClass=BigDecimal.ZERO;//负债
		BigDecimal commonClassD=BigDecimal.ZERO;	//共同 借	
		BigDecimal commonClassC=BigDecimal.ZERO;	//共同  贷			
		Set<String> set = map.keySet();
		for (String key : set) {
			// 获取汇总数据
			MerchantAccountDaily daily = map.get(key);

			AccountSubjectSum sum = new AccountSubjectSum();
			// 计算借贷方向
			AccountSubjectVO subject = AccountSubjects.getContentByValue(key);
			if (subject == null) {
				logger.error("会计科目维度余额汇总，遗漏数据：{}", key);
				continue;
			}

			sum.setType(key);
			sum.setLendingDirection(subject.getLendingDirection());
			sum.setSubjectType(subject.getSubjectType());
			logger.info("{}#{}#{}#{}#{}", key, daily.getDebitBeginningBalances(), daily.getDebitEndingBalances(),
					daily.getCreditBeginningBalances(), daily.getCreditEndingBalances());

			sum.setDebitBeginBalances(daily.getDebitBeginningBalances());
			sum.setDebitEndingBalances(daily.getDebitEndingBalances());
			sum.setCreditBeginBalances(daily.getCreditBeginningBalances());
			sum.setCreditEndingBalances(daily.getCreditEndingBalances());

			sum.setDebitCurrentAmount(daily.getDebitCurrentAmount());// 借方期末
			sum.setCreditCurrentAmount(daily.getCreditCurrentAmount());// 贷方期初
			result.add(sum);
			
			//计算余额
			BigDecimal debitEndingBalances=new BigDecimal(daily.getDebitEndingBalances());
			BigDecimal creditEndingBalances=new BigDecimal(daily.getCreditEndingBalances());
			//资产类 余额=借方期末-贷方期末 
			if(AccountSubjectVO.assetClass.equals(subject.getSubjectType())){
				assetClass=assetClass.add(debitEndingBalances.subtract(creditEndingBalances));
			}else if(AccountSubjectVO.debtClass.equals(subject.getSubjectType())){
				//负债类 余额=贷方期末-借方期末
				debtClass=debtClass.add(creditEndingBalances.subtract(debitEndingBalances));
			}else{
				//共同类 看账户的接待方向
				if(MerchantAccountDirection.JIE.getValue().equals(subject.getLendingDirection())){
					commonClassD=commonClassD.add(debitEndingBalances.subtract(creditEndingBalances));
				}else{
					commonClassC=commonClassC.add(creditEndingBalances.subtract(debitEndingBalances));					
				}
			}

		}
		model.addAttribute("result", result);
		model.addAttribute("assetClass", assetClass);
		model.addAttribute("debtClass", debtClass);
		model.addAttribute("commonClassD", commonClassD);
		model.addAttribute("commonClassC", commonClassC);
		return "modules/trial/trialBalanceDayForm";
	}



	@RequiresPermissions("trial:trialBalanceDay:edit")
	@RequestMapping(value = "reset")
	public String reSetDate(TrialBalanceDay trialBalanceDay, RedirectAttributes redirectAttributes)throws Exception{

		if(trialBalanceDay.getAccountDate()==null){
			logger.error("账务日汇总重跑操作,未指定重新计算的开始日期!");
			return "redirect:" + Global.getAdminPath() + "/trial/trialBalanceDay/?repage";
		}
		logger.info("账务日汇总重跑操作,重新计算的开始日期={}",trialBalanceDay.getAccountDate());
		
		
		// 获取当前时间
		Date now = new Date();
		Date inEnd = DateUtil.stringToDate(trialBalanceDay.getAccountDate()+ " 23:59:59", "yyyy-MM-dd HH:mm:ss");
		if(inEnd.getTime()>now.getTime()){
			logger.error("账务日汇总重跑操作,指定重新计算的开始日期最后会计日期，开始日期={}，当前日期={}!",trialBalanceDay.getAccountDate(),now);
		}
		
		// 查询下第一个会计日期是多少
		Page<MerchantLog> page = new Page<MerchantLog>();

		page.setOrderBy("createTime ASC");
		page.setPageNo(0);
		page.setPageSize(1);
		MerchantLog merchantLog = new MerchantLog();

		page = merchantLogService.findPage(page, merchantLog);
		if (page != null) {
			merchantLog = page.getList().get(0);
		}
		//数据库查的
		String accountStr = merchantLog.getAccountDate();
		Date accountDate = DateUtil.stringToDate(accountStr, DateUtil.DATE_FORMAT_YYYYMMDD);
		//传进来的参数
		Date inAccountDate = DateUtil.stringToDate(trialBalanceDay.getAccountDate(), DateUtil.DATE_FORMAT_YYYYMMDD);
		//判断 传入的值比最早的财务结算日期大
		if(inAccountDate.getTime()>accountDate.getTime()){
			accountStr=trialBalanceDay.getAccountDate();
			accountDate=inAccountDate;
		}
		String beginDate = accountStr + " 00:00:00";
		String endDate = accountStr + " 23:59:59";

		Date endContart = DateUtil.stringToDate(endDate, "yyyy-MM-dd HH:mm:ss");
		// 把时间改成日历类 好遍历日期
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(accountDate);
		
		// 删除历史数据
		merchantAccountMonthDailyService.deleteByCreateTime(now);
		// 循环遍历时间 判断是不是符合规范
		while (endContart.getTime() < now.getTime()) {
			Thread.sleep(5000);
			// 按照account_date删除日汇总和账户汇总
			trialBalanceDay.setAccountDate(accountStr);
			trialBalanceDayService.delete(trialBalanceDay);
			MerchantAccountDaily merchantAccountDaily = new MerchantAccountDaily();
			merchantAccountDaily.setAccountDate(accountStr);
			merchantAccountDailyService.delete(merchantAccountDaily);
			// 生成汇总
			merchantAccountDailyClient.statisticsOnedayMerchantLogByBetweenCreateTime(beginDate, endDate, accountStr);
			// 加一天
			cal.add(Calendar.DAY_OF_MONTH, 1);
			accountDate = cal.getTime();

			accountStr = DateUtil.dateToString(accountDate, DateUtil.DATE_FORMAT_YYYYMMDD);
			beginDate = accountStr + " 00:00:00";
			endDate = accountStr + " 23:59:59";
			endContart = DateUtil.stringToDate(endDate, "yyyy-MM-dd HH:mm:ss");
		}

		return "redirect:" + Global.getAdminPath() + "/trial/trialBalanceDay/?repage";
	}

}