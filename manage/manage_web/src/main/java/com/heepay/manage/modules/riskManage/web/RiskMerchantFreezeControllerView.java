package com.heepay.manage.modules.riskManage.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.risk.entity.RiskMerchantFreeze;
import com.heepay.manage.modules.risk.service.RiskMerchantFreezeService;


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
@RequestMapping(value = "${adminPath}/riskManage/RiskMerchantFreezeQuery/view")
public class RiskMerchantFreezeControllerView extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private  RiskMerchantFreezeService  riskMerchantFreezeService;
	
	
	@Autowired
	private Explort explort;
	
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
			model = riskMerchantFreezeService.findRiskMerchantProductQuotaPage(new Page<RiskMerchantFreeze>(request, response), riskMerchantFreeze,model);
			
			return "modules/riskManage/riskMerchantFreezeListView";
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController list has a error:", e);
		}
		return "error/500";
	}
	
	// 记录信息导出
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes, RiskMerchantFreeze riskMerchantFreeze,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		explort.export(redirectAttributes, riskMerchantFreeze, request, response);
	}
}
