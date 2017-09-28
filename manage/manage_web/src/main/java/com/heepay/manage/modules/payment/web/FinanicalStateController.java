package com.heepay.manage.modules.payment.web;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.TransType;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.payment.entity.MerchantLogSum;
import com.heepay.manage.modules.payment.param.AccountSubjectSum;
import com.heepay.manage.modules.payment.param.SubjectFinanAccSumDetailParam;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import com.heepay.manage.modules.trial.entity.MerchantAccountDaily;
import com.heepay.manage.modules.trial.entity.MerchantAccountMonthDaily;
import com.heepay.manage.modules.trial.entity.TrialBalanceDay;
import com.heepay.manage.modules.trial.service.MerchantAccountDailyService;
import com.heepay.manage.modules.trial.service.MerchantAccountMonthDailyService;
import com.heepay.manage.modules.trial.service.TrialBalanceDayService;
import com.heepay.payment.enums.AccountSubjects;
import com.heepay.payment.vo.AccountSubjectVO;
import com.heepay.vo.MerchantVO;

/**
 * 
 * 
 * 描 述：
 *
 * 创 建 者： 杨春龙 创建时间： 2017年5月12日 上午10:51:22 创建描述：财务报表
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/finanical/statement")
public class FinanicalStateController {
	private static final Logger log = LogManager.getLogger();
	@Autowired
	private MerchantAccountDailyService merchantAccountDailyService;
	@Autowired
	private MerchantLogService merchantLogService;
	@Autowired
	private MerchantAccountMonthDailyService merchantAccountMonthDailyService;
	@Autowired
	private TrialBalanceDayService trialBalanceDayService;
	
	@RequiresPermissions("finanical:statement:view")
	@RequestMapping(value = "sumList")
	public String finanicalStateList(MerchantAccountDaily merchantAccountDaily, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		merchantAccountDaily.setSubjectType("200");
		// 查询商户余额
		Page<MerchantAccountDaily> page = merchantAccountDailyService
				.findPage(new Page<MerchantAccountDaily>(request, response), merchantAccountDaily);

		// 根据查询的出的商户 找到商户的待结算信息
		List<MerchantAccountDaily> list = page.getList();
		if (list == null || list.size() == 0) {
			// 返回数据
			model.addAttribute("page", page);
			model.addAttribute("beginAccountDate",
					DateUtil.stringToDate(merchantAccountDaily.getBeginAccountDate(), "yyyy-MM-dd"));
			model.addAttribute("endAccountDate",
					DateUtil.stringToDate(merchantAccountDaily.getEndAccountDate(), "yyyy-MM-dd"));
			model.addAttribute("merchantAccountDaily", merchantAccountDaily);

			return "modules/finanical/finanical_merchantAccountDailyList";

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectType", "210");
		map.put("list", list);
		map.put("beginAccountDate", merchantAccountDaily.getBeginAccountDate());
		map.put("endAccountDate", merchantAccountDaily.getEndAccountDate());
		// 商户对应的待结算数据
		List<MerchantAccountDaily> daijiesuan_list = merchantAccountDailyService.findByMerchantIds(map);
		Map<String, MerchantAccountDaily> daijiesuan_map = new HashMap<String, MerchantAccountDaily>();
		for (MerchantAccountDaily daily : daijiesuan_list) {
			daijiesuan_map.put(daily.getMerchantId() + "_" + daily.getAccountDate(), daily);
		}
		// 手动待结算数据
		map.put("subjectType", "220");
		List<MerchantAccountDaily> hm_daijiesuan_list = merchantAccountDailyService.findByMerchantIds(map);
		Map<String, MerchantAccountDaily> hm_daijiesuan_map = new HashMap<String, MerchantAccountDaily>();
		for (MerchantAccountDaily daily : hm_daijiesuan_list) {
			hm_daijiesuan_map.put(daily.getMerchantId() + "_" + daily.getAccountDate(), daily);
		}

		// 组装数据
		for (MerchantAccountDaily daily : list) {
			MerchantAccountDaily daijiesuan = daijiesuan_map.get(daily.getMerchantId() + "_" + daily.getAccountDate());
			String json = RedisUtil.getRedisUtil().merchantRedis(String.valueOf(daily.getMerchantId()), "", "", "",
					"","","merchant");
			JsonMapperUtil mapper = new JsonMapperUtil();
			MerchantVO vo = mapper.fromJson(json, MerchantVO.class);
			daily.setMerchantCompany(vo == null ? "-" : vo.getMerchantCompany());
			if (daijiesuan != null) {
				daily.setDaijiesuanAccountId(String.valueOf(daijiesuan.getAccountId()));
				daily.setDaijiesuanBeginningBalances(daijiesuan.getCreditBeginningBalances());
				daily.setDaijiesuanEndingBalances(daijiesuan.getCreditEndingBalances());
				daily.setDaijiesuanDebitCurrentAmount(daijiesuan.getDebitCurrentAmount());
				daily.setDaijiesuanCreditCurrentAmount(daijiesuan.getCreditCurrentAmount());
			}

			MerchantAccountDaily hm_daijiesuan = hm_daijiesuan_map
					.get(daily.getMerchantId() + "_" + daily.getAccountDate());
			if (hm_daijiesuan != null) {
				daily.setDaijiesuanAccountId(String.valueOf(hm_daijiesuan.getAccountId()));
				daily.setDaijiesuanBeginningBalances(hm_daijiesuan.getCreditBeginningBalances());
				daily.setDaijiesuanEndingBalances(hm_daijiesuan.getCreditEndingBalances());
				daily.setDaijiesuanDebitCurrentAmount(hm_daijiesuan.getDebitCurrentAmount());
				daily.setDaijiesuanCreditCurrentAmount(hm_daijiesuan.getCreditCurrentAmount());
			}
		}

		// 返回数据
		model.addAttribute("page", page);
		model.addAttribute("beginAccountDate",
				DateUtil.stringToDate(merchantAccountDaily.getBeginAccountDate(), "yyyy-MM-dd"));
		model.addAttribute("endAccountDate",
				DateUtil.stringToDate(merchantAccountDaily.getEndAccountDate(), "yyyy-MM-dd"));
		model.addAttribute("merchantAccountDaily", merchantAccountDaily);

		return "modules/finanical/finanical_merchantAccountDailyList";
	}

	@RequiresPermissions("finanical:statement:view")
	@RequestMapping(value = "subjectList")
	public String subjectFinanicalStateList(MerchantAccountDaily merchantAccountDaily, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		// 获取所有商户本期的 期初余额和期末余额
		Map<String,Object> queryMap=new HashMap<String,Object>();
		if(StringUtil.isBlank(merchantAccountDaily.getAccountDate())){
			queryMap.put("accountDate", DateUtil.getYesterDay("yyyy-MM-dd"));
		}else{
			queryMap.put("accountDate", merchantAccountDaily.getAccountDate());
		}
		model.addAttribute("accountDate", DateUtil.stringToDate(queryMap.get("accountDate").toString(),"yyyy-MM-dd"));
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
			debitCurrent=debitCurrent.add(new BigDecimal(mad.getDebitCurrentAmount()));
			creditCurrent=creditCurrent.add(new BigDecimal(mad.getCreditCurrentAmount()));
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
		Set<String> set = map.keySet();
		for (String key : set) {
			// 获取汇总数据
			MerchantAccountDaily daily = map.get(key);

			AccountSubjectSum sum = new AccountSubjectSum();
			// 计算借贷方向
			AccountSubjectVO subject = AccountSubjects.getContentByValue(key);
			if (subject == null) {
				log.error("会计科目维度余额汇总，遗漏数据：{}", key);
				continue;
			}

			sum.setType(key);
			sum.setSubjectType(subject.getSubjectType());
			log.info("{}#{}#{}#{}#{}", key, daily.getDebitBeginningBalances(), daily.getDebitEndingBalances(),
					daily.getCreditBeginningBalances(), daily.getCreditEndingBalances());

			sum.setDebitBeginBalances(daily.getDebitBeginningBalances());
			sum.setDebitEndingBalances(daily.getDebitEndingBalances());
			sum.setCreditBeginBalances(daily.getCreditBeginningBalances());
			sum.setCreditEndingBalances(daily.getCreditEndingBalances());
			
			sum.setDebitCurrentAmount(daily.getDebitCurrentAmount());// 借方期末
			sum.setCreditCurrentAmount(daily.getCreditCurrentAmount());// 贷方期初
			result.add(sum);
		}
		model.addAttribute("result", result);
		return "modules/finanical/subject_finanical_merchantAccountDailyList";
	}

	// 商户余额月度出入金汇总
	@RequiresPermissions("finanical:statement:view")
	@RequestMapping(value = "merchantAccountMonth")
	public String merchantAccountSum(MerchantAccountMonthDaily merchantAccountMonthDaily, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Page<MerchantAccountMonthDaily> page = merchantAccountMonthDailyService
				.findPage(new Page<MerchantAccountMonthDaily>(request, response), merchantAccountMonthDaily);

		// 然后查询下返回
		model.addAttribute("page", page);

		if (!StringUtil.isBlank(merchantAccountMonthDaily.getAccountMonth())) {
			String accountMonth = merchantAccountMonthDaily.getAccountMonth() + "-01";

			model.addAttribute("accountMonth", DateUtil.stringToDate(accountMonth, "yyyy-MM-dd"));
		}

		for (MerchantAccountMonthDaily monthDaily : page.getList()) {
			String json = RedisUtil.getRedisUtil().merchantRedis(String.valueOf(monthDaily.getMerchantId()), "", "", "",
					"","","merchant");
			if (StringUtil.notBlank(json)) {
				JsonMapperUtil mapper = new JsonMapperUtil();
				MerchantVO vo = mapper.fromJson(json, MerchantVO.class);
				monthDaily.setMerchantCompany(vo.getMerchantCompany());
			}
		}

		model.addAttribute("merchantAccountMonthDaily", merchantAccountMonthDaily);
		return "modules/finanical/merchantAccountMonthDailyList";
	}

	// 商户余额月度出入金汇总初始化
	@RequiresPermissions("finanical:statement:view")
	@RequestMapping(value = "merchantAccountMonthInitialize")
	@ResponseBody
	public String merchantAccountInitialize(MerchantAccountMonthDaily merchantAccountMonthDaily,
			HttpServletRequest request, HttpServletResponse response, Model model) {

		// 重新生成查询下数据
		List<MerchantAccountDaily> dailyList = merchantAccountDailyService.findListBySubject("200");

		Map<String, MerchantAccountMonthDaily> map = new HashMap<String, MerchantAccountMonthDaily>();
		Date createTime = new Date();
		for (MerchantAccountDaily daily : dailyList) {
			// 更新的会计日期
			String accountDate = daily.getAccountDate();
			Date date = DateUtil.stringToDate(accountDate, "yyyy-MM-dd");

			String monthDate = accountDate.substring(0, 7);

			String key = daily.getAccountId() + "_" + monthDate;

			if (map.containsKey(key)) {
				MerchantAccountMonthDaily month = map.get(key);
				// 判断下当前数据和map里的数据 accountDate的顺序 谁在前 谁在后
				if (month.getAccountDate().getTime() < date.getTime()) {
					month.setEndingBalances(daily.getCreditEndingBalances());
					month.setAccountDate(date);
				} else {
					month.setBeginningBalances(daily.getCreditBeginningBalances());
				}
				BigDecimal inMoney = new BigDecimal(month.getInMoney());
				BigDecimal outMoney = new BigDecimal(month.getOutMoney());

				inMoney = inMoney.add(new BigDecimal(daily.getCreditCurrentAmount()));
				outMoney = outMoney.add(new BigDecimal(daily.getDebitCurrentAmount()));
				month.setInMoney(inMoney.toString());
				month.setOutMoney(outMoney.toString());
			} else {
				MerchantAccountMonthDaily month = new MerchantAccountMonthDaily();
				month.setAccountId(daily.getAccountId());
				month.setMerchantId(daily.getMerchantId());
				month.setAccountDate(date);
				month.setAccountMonth(monthDate);
				month.setBeginningBalances(daily.getCreditBeginningBalances());
				month.setEndingBalances(daily.getCreditEndingBalances());
				month.setInMoney(daily.getCreditCurrentAmount());
				month.setOutMoney(daily.getDebitCurrentAmount());
				month.setCreateTime(createTime);
				month.setUpdateTime(createTime);
				map.put(key, month);
			}
		}
		Set<String> set = map.keySet();
		List<MerchantAccountMonthDaily> batchInsertList = new ArrayList<MerchantAccountMonthDaily>();
		for (String key : set) {
			batchInsertList.add(map.get(key));
		}
		// 批量插入
		merchantAccountMonthDailyService.batchInsert(batchInsertList);
		// 删除历史数据
		merchantAccountMonthDailyService.deleteByCreateTime(createTime);
		return "ok";
	}
	
	// 商户余额月度出入金汇总
	@RequiresPermissions("finanical:statement:view")
	@RequestMapping(value = "merchantAccountAmount")	
	public  String  merchantAmount(MerchantAccountDaily merchantAccountDaily, HttpServletRequest request,
			HttpServletResponse response, Model model){
		Page<MerchantAccountDaily> page=new Page<MerchantAccountDaily>(request, response);
		page.setOrderBy("account_date desc");
		page= merchantAccountDailyService.findSumListBySubject(page, merchantAccountDaily);

		model.addAttribute("beginAccountDate", DateUtil.stringToDate(merchantAccountDaily.getBeginAccountDate(),"yyyy-MM-dd"));
		model.addAttribute("endAccountDate", DateUtil.stringToDate(merchantAccountDaily.getEndAccountDate(),"yyyy-MM-dd"));
		model.addAttribute("page", page);
		return "modules/finanical/subject_finanical_account_sum";
	}
	@RequiresPermissions("trial:trialBalanceDay:view")
	@RequestMapping(value = "merchantAccountAmountDetail")
	public String merchantAmountDetail(SubjectFinanAccSumDetailParam detailParam,HttpServletRequest request, Model model) {		
		boolean flag=true;
		if(detailParam.getBeginAccountDate()==null){
			detailParam.setBeginAccountDate(detailParam.getAccountDate());
			detailParam.setEndAccountDate(detailParam.getAccountDate());
			flag=false;
		}
		//判断下结束时间和当前时间的关系
		Calendar   cal   =   Calendar.getInstance();
	    cal.add(Calendar.DATE,   -1);
		//昨天
	    long yesterDay=cal.getTime().getTime();
		long endacc=DateUtil.stringToDate(detailParam.getEndAccountDate(),"yyyy-MM-dd").getTime();
		long beginacc=DateUtil.stringToDate(detailParam.getBeginAccountDate(),"yyyy-MM-dd").getTime();
		if(endacc>yesterDay){
	    	detailParam.setEndAccountDate(DateUtil.getYesterDay("yyyy-MM-dd"));
	    	endacc=DateUtil.stringToDate(detailParam.getEndAccountDate(),"yyyy-MM-dd").getTime();
	    }
	    //如果开始时间大于结束时间
		if(beginacc>endacc){
			detailParam.setBeginAccountDate(detailParam.getEndAccountDate());
		}
	    
	    
	    Map<String,Object> paramsMap=new HashMap<String,Object>();
		paramsMap.put("beginCreateTime", detailParam.getBeginAccountDate()+" 00:00:00");
		paramsMap.put("endCreateTime", detailParam.getEndAccountDate()+" 23:59:59");
		
		List<MerchantLogSum> list=merchantLogService.getSumList(paramsMap);
		
		BigDecimal c=BigDecimal.ZERO;//收入 rightAmount
		BigDecimal d=BigDecimal.ZERO;//支付 leftAmount
		for(MerchantLogSum sum:list){
			String content=TransType.getContentByValue(sum.getType());
			sum.setContent(content);
			c = c.add(sum.getRightAmount());
			d = d.add(sum.getLeftAmount());
			
		}
		
		detailParam.setC(c.toString());
		detailParam.setD(d.toString());
		if(flag){
			//获取下   期初余额
			MerchantAccountDaily merchantAccountDaily=new MerchantAccountDaily();
			merchantAccountDaily.setBeginAccountDate(detailParam.getBeginAccountDate());
			merchantAccountDaily.setEndAccountDate(detailParam.getBeginAccountDate());
			Page<MerchantAccountDaily> page=new Page<MerchantAccountDaily>();
			page= merchantAccountDailyService.findSumListBySubject(page, merchantAccountDaily);
			if(page.getList()==null ||page.getList().size()==0){
				detailParam.setBegin("0");
			}else{
				MerchantAccountDaily beginDaily=page.getList().get(0);	
				BigDecimal creditBalances=new BigDecimal(beginDaily.getCreditBeginningBalances());
				BigDecimal debitBalances=new BigDecimal(beginDaily.getDebitBeginningBalances());
				detailParam.setBegin(creditBalances.subtract(debitBalances).toString());
			}
			//获取下   期末余额
			merchantAccountDaily.setBeginAccountDate(detailParam.getEndAccountDate());
			merchantAccountDaily.setEndAccountDate(detailParam.getEndAccountDate());
			page= merchantAccountDailyService.findSumListBySubject(page, merchantAccountDaily);
			if(page.getList()==null ||page.getList().size()==0){
				detailParam.setEnd("0");
			}else{
				MerchantAccountDaily beginDaily=page.getList().get(0);	
				BigDecimal creditBalances=new BigDecimal(beginDaily.getCreditEndingBalances());
				BigDecimal debitBalances=new BigDecimal(beginDaily.getDebitEndingBalances());
				detailParam.setEnd(creditBalances.subtract(debitBalances).toString());
			}
		}
		
		model.addAttribute("detailParam", detailParam);
		model.addAttribute("list", list);
		model.addAttribute("beginAccountDate", DateUtil.stringToDate(detailParam.getBeginAccountDate(),DateUtil.DATE_FORMAT_YYYYMMDD));
		model.addAttribute("endAccountDate",  DateUtil.stringToDate(detailParam.getEndAccountDate(),DateUtil.DATE_FORMAT_YYYYMMDD));
		return "modules/finanical/subject_finanical_account_sum_detail";	
	}
	
	public static void main(String[] args) {
		BigDecimal a=new BigDecimal("20516143.22");
		BigDecimal b=new BigDecimal("13.35");
		BigDecimal c=new BigDecimal("4.80");
		BigDecimal d=new BigDecimal("220517429.10");
		
		
		System.out.println(a.add(c).subtract(b));
		
	}
}
