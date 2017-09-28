/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantRateLogCService;
import com.heepay.manage.modules.merchant.vo.MerchantRateLog;
import com.heepay.manage.modules.sys.utils.UserUtils;


/**
 *
 * 描    述：费率操作日志Controller
 *
 * 创 建 者： @author ly
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
@RequestMapping(value = "${adminPath}/merchant/merchantRateLog")
public class MerchantRateLogController extends BaseController {

	@Autowired
	private MerchantRateLogCService merchantRateLogCService;
	
	@ModelAttribute
	public MerchantRateLog get(@RequestParam(required=false) String id) {
		MerchantRateLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantRateLogCService.get(id);
		}
		if (entity == null){
			entity = new MerchantRateLog();
		}
		return entity;
	}
	
	@RequiresPermissions("merchant:merchantRateLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantRateLog merchantRateLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MerchantRateLog> page = merchantRateLogCService.findPage(new Page<MerchantRateLog>(request, response), merchantRateLog); 
		page.setList(EnumView.merchantRateLogList(page.getList()));
		model.addAttribute("page", page);
		return "modules/merchant/merchantRateLogList";
	}

	@RequiresPermissions("merchant:merchantRateLog:view")
	@RequestMapping(value = "detail")
	public String detail(MerchantRateLog merchantRateLog, Model model) {
	    MerchantRateLog merchantRateLogNew = EnumView.changeMerchantRateLog(merchantRateLog);
		model.addAttribute("merchantRateLog", merchantRateLogNew);
		return "modules/merchant/merchantRateLogDetail";
	}

}