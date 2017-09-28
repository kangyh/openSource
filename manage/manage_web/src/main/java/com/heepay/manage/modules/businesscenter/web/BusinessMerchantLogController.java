/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.businesscenter.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
@RequestMapping(value = "${adminPath}/business/merchantLog")
public class BusinessMerchantLogController extends BaseController {

	@Autowired
	private MerchantLogService merchantLogService;

	@Autowired
	private MerchantAccountService merchantAccountService;


	private static Logger logger = LogManager.getLogger();

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
	
	@RequiresPermissions("business:merchantLog:view")
	@RequestMapping(value = "getMerchantLog")
	public String getMerchantLog(MerchantLog merchantLog, HttpServletRequest request, HttpServletResponse response, Model model) {

		if (merchantLog.getSortOrder() == null) {
			//默认按照创建时间降序
			merchantLog.setSortOrder(SortOrderType.DESC.getValue());
		}
		merchantLog.setSourceSearch(0);

		String accountCodesHidden = merchantLog.getAccountCodesHidden();
		List<String> accCodesList = Lists.newArrayList();
		if (StringUtil.notBlank(accountCodesHidden)) {
			String[] accountCodes = accountCodesHidden.split(",");
			accCodesList = Arrays.asList(accountCodes);
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("accCodes", accCodesList);
			List<Long> merchantIds = merchantAccountService.getSelectMerchantIds(paramsMap);
			merchantLog.setMerchantIds(merchantIds);
		}
		if (null == merchantLog.getBeginCreateTime() && null == merchantLog.getEndCreateTime()) {
			merchantLog.setBeginCreateTime(new Date());
			merchantLog.setEndCreateTime(new Date());
		}

		List<Long> merchantIdList = RandomUtil.getMerchantIdList();
		merchantLog.setMerchantIds(merchantIdList);

		Page<MerchantLog> page = merchantLogService.findPage(new Page<MerchantLog>(request, response), merchantLog);
		model.addAttribute("page", page);
		return "modules/businesscenter/merchantLogList";
	}

	@RequiresPermissions("payment:merchantLog:view")
	@RequestMapping(value = "form")
	public String form(MerchantLog merchantLog, Model model) {
		model.addAttribute("merchantLog", merchantLog);
		return "modules/businesscenter/merchantLogForm";
	}


}
