/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * 描    述：账户明细查询Controller
 *
 * 创 建 者： @author yq
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
@RequestMapping(value = "${adminPath}/payment/merchantLogSettle")
public class MerchantLogSettleController extends BaseController {

	@Autowired
	private MerchantLogService merchantLogService;
	
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
	
	@RequiresPermissions("payment:merchantLogSettle:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantLog merchantLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(merchantLog.getSortOrder() == null){
			//默认按照创建时间降序
			merchantLog.setSortOrder(SortOrderType.DESC.getValue());
		}
		merchantLog.setSourceSearch(1);
		Page<MerchantLog> page = merchantLogService.findPage(new Page<MerchantLog>(request, response), merchantLog); 
		model.addAttribute("page", page);
		return "modules/payment/merchantLogSettleList";
	}

	@RequiresPermissions("payment:merchantLog:view")
	@RequestMapping(value = "form")
	public String form(MerchantLog merchantLog, Model model) {
		model.addAttribute("merchantLog", merchantLog);
		return "modules/payment/merchantLogSettleForm";
	}

}