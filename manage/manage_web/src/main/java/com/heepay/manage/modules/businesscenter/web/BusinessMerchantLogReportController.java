/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.businesscenter.web;

import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.BalanceDirection;
import com.heepay.enums.MerchantAccountType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.param.AccountSettleReportResult;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;


/**
 *
 * 描    述：账户明细查询Controller
 *
 * 创 建 者： @author wyh
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
@RequestMapping(value = "${adminPath}/business/merchantLogReport")
public class BusinessMerchantLogReportController extends BaseController {

	@Autowired
	private MerchantLogService merchantLogService;

	@Autowired
	private MerchantUserCService merchantUserCService;

	@ModelAttribute
	public MerchantLog get(@RequestParam(required=false) String id) {
		MerchantLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantLogService.get(id);
		}
		if (entity == null){
			entity = new MerchantLog();
		}
		return entity;
	}
	
	@RequiresPermissions("business:merchantLogReport:view")
	@RequestMapping(value = "getMerchantLogReport")
	public String getMerchantLogReport(MerchantLog merchantLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(null == merchantLog.getMerchantType()){
			merchantLog.setMerchantType(MerchantAccountType.MERCHANT_ACCOUNT.getValue());
		}
		if(null == merchantLog.getBeginCreateTime() && null == merchantLog.getEndCreateTime()){
			merchantLog.setBeginCreateTime(new Date());
			merchantLog.setEndCreateTime(new Date());
		}
		Map<String,List<AccountSettleReportResult>> listMap = query(merchantLog,request,response,model);
		String pageNo = request.getParameter("pageNo");
		if(StringUtil.isBlank(pageNo)){
			pageNo="1";
		}
		String pageSize = request.getParameter("pageSize");
		if(StringUtil.isBlank(pageSize)){
			pageSize= "30";
		}
		List<AccountSettleReportResult> takeOutList = new ArrayList<AccountSettleReportResult>();
		int start = 0;
		int end =0;
		List<AccountSettleReportResult> pageList = listMap.get("list");
		if(1 != Integer.parseInt(pageNo)){
			 start = (Integer.parseInt(pageNo)-1) * Integer.parseInt(pageSize);
		}
		end = start + Integer.parseInt(pageSize);
		if(end > pageList.size()){
			end = pageList.size();
		}
		takeOutList = pageList.subList(start,end);
		Page<AccountSettleReportResult> page = new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize), pageList.size());
		page.setList(takeOutList);
		model.addAttribute("page", page);
		Map<String,BigDecimal> cdMap = new HashMap<String,BigDecimal>();
		List<AccountSettleReportResult> accountSettleReportResultList = listMap.get("total");
		cdMap.put("D",accountSettleReportResultList.get(0).getAllAccountDeebitAmountSum());
		cdMap.put("C",accountSettleReportResultList.get(0).getAllAccountCrebitAmountSum());
		model.addAttribute("cdMap", cdMap);
		model.addAttribute("logserch",merchantLog);
		return "modules/businesscenter/merchantLogReportList";
	}

	private Map<String,List<AccountSettleReportResult>> query(MerchantLog merchantLog, HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String,List<AccountSettleReportResult>> mainMap = new HashMap<String,List<AccountSettleReportResult>>();
		List<Long> merchantIdList = RandomUtil.getMerchantIdList();
		merchantLog.setMerchantIds(merchantIdList);
		List<MerchantLog> merchantLogList = merchantLogService.findList(merchantLog);
		Map<String,AccountSettleReportResult> settleMap = new LinkedHashMap<String,AccountSettleReportResult>();
		for(MerchantLog mlog : merchantLogList){
			if(MerchantAccountType.INTERNAL_ACCOUNT.getValue().equals(mlog.getMerchantType())){
				continue;
			}
			String key = mlog.getMerchantId()+"-"+mlog.getType()+"-"+mlog.getBalanceDirection()+"-"+mlog.getAccountExplain();
			AccountSettleReportResult ar = null;
			if(settleMap.get(key)==null){
				ar = new AccountSettleReportResult();
				ar.setAccountId(String.valueOf(mlog.getAccountId()));
				ar.setAccountName(mlog.getAccountName());
				ar.setMerchantId(String.valueOf(mlog.getMerchantId()));
				ar.setMerchantName(mlog.getMerchantName());
				ar.setTransType(mlog.getType());
				ar.setMerchantType(mlog.getMerchantType());
				ar.setAccountExplain(String.valueOf(mlog.getAccountExplain()));
			}else{
				ar = settleMap.get(key);
			}
			if(BalanceDirection.CREBIT.getValue().equals(mlog.getBalanceDirection())){
				ar.setCrebitAmountSum(ar.getCrebitAmountSum().add(new BigDecimal(mlog.getBalanceAmountChanges())));
			}else{
				ar.setDeebitAmountSum(ar.getDeebitAmountSum().add(new BigDecimal(mlog.getBalanceAmountChanges())));
			}
			settleMap.put(key,ar);
		}
		Map<String,AccountSettleReportResult> tSettleMap = new LinkedHashMap<String,AccountSettleReportResult>();
		Iterator entries = settleMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String key = (String)entry.getKey();
			AccountSettleReportResult vls = (AccountSettleReportResult)entry.getValue();
			String merchantId = key.split("-")[0];
			if(MerchantAccountType.MERCHANT_ACCOUNT.getValue().equals(merchantLog.getMerchantType())){
				if(StringUtil.notBlank(merchantLog.getMerchantSonType())){
					MerchantUser merchantUser = merchantUserCService.get(merchantId);
					if("2-1".equals(merchantLog.getMerchantSonType()) && "outsid".equals(merchantUser.getMerchantFlag())){
						tSettleMap.put(key,vls);
					}
					if("2-2".equals(merchantLog.getMerchantSonType()) && "inside".equals(merchantUser.getMerchantFlag())){
						tSettleMap.put(key,vls);
					}
					if(StringUtil.isBlank(merchantUser.getMerchantFlag())){
						tSettleMap.put(key,vls);
					}
				}else{
					tSettleMap.put(key,vls);
				}
			}else{
				tSettleMap.put(key,vls);
			}
		}
		List<AccountSettleReportResult> pageList = new ArrayList<AccountSettleReportResult>();
		BigDecimal dAmount = BigDecimal.ZERO;
		BigDecimal cAmount = BigDecimal.ZERO;
		for(AccountSettleReportResult accountSettleReportResult : tSettleMap.values()){
			pageList.add(accountSettleReportResult);
			cAmount = cAmount.add(accountSettleReportResult.getCrebitAmountSum());
			dAmount = dAmount.add(accountSettleReportResult.getDeebitAmountSum());
		}
		String sOrder = request.getParameter("sOrder");
		//D升序
		if(StringUtil.isBlank(sOrder) || "0".equals(sOrder)){
			model.addAttribute("sOrder", "0");
			Collections.sort(pageList, new Comparator<AccountSettleReportResult>() {
				@Override
				public int compare(AccountSettleReportResult o1, AccountSettleReportResult o2) {
					return o1.getDeebitAmountSum().compareTo(o2.getDeebitAmountSum());
				}
			});
		}
		//D降序
		if("1".equals(sOrder)){
			model.addAttribute("sOrder", "1");
			Collections.sort(pageList, new Comparator<AccountSettleReportResult>() {
				@Override
				public int compare(AccountSettleReportResult o1, AccountSettleReportResult o2) {
					return o2.getDeebitAmountSum().compareTo(o1.getDeebitAmountSum());
				}
			});
		}
		//C升序
		if("2".equals(sOrder)){
			model.addAttribute("sOrder", "2");
			Collections.sort(pageList, new Comparator<AccountSettleReportResult>() {
				@Override
				public int compare(AccountSettleReportResult o1, AccountSettleReportResult o2) {
					return o1.getCrebitAmountSum().compareTo(o2.getCrebitAmountSum());
				}
			});
		}
		//C降序
		if("3".equals(sOrder)){
			model.addAttribute("sOrder", "3");
			Collections.sort(pageList, new Comparator<AccountSettleReportResult>() {
				@Override
				public int compare(AccountSettleReportResult o1, AccountSettleReportResult o2) {
					return o2.getCrebitAmountSum().compareTo(o1.getCrebitAmountSum());
				}
			});
		}
		mainMap.put("list", pageList);
		AccountSettleReportResult accountSettleReportResult = new AccountSettleReportResult();
		accountSettleReportResult.setAllAccountDeebitAmountSum(dAmount);
		accountSettleReportResult.setAllAccountCrebitAmountSum(cAmount);
		mainMap.put("total", Collections.singletonList(accountSettleReportResult));
		return mainMap;
	}



}
