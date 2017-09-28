package com.heepay.manage.modules.reconciliation.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleRegexControl;
import com.heepay.manage.modules.reconciliation.service.SettleRegexControlService;
import com.heepay.manage.modules.reconciliation.web.util.RuleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 *
 * 描    述：规则验证
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年1月19日 下午1:09:26
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
@RequestMapping(value = "${adminPath}/reconciliation/settleRegexControl")
public class SettleRegexControlControllor extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private  SettleRegexControlService  settleRegexControlService;
	
	/**
	 * @方法说明：查询规则验证List
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:settleRegexControl:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleRegexControl settleRegexControl,
					   HttpServletRequest request,
					   HttpServletResponse response,
					   Model model) throws Exception{
		try {

			Page<SettleRegexControl> page = settleRegexControlService.findPage(new Page<SettleRegexControl>(request, response),settleRegexControl);
			for (SettleRegexControl clearingCR : page.getList()) {
				//对账方式
				if(StringUtils.isNotBlank(clearingCR.getRuleType())){
					clearingCR.setRuleType(RuleType.labelOf(clearingCR.getRuleType()));
				}
			}
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("SettleRegexControlControllor list has a error:{查询规则验证List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}

		model.addAttribute("settleRegexControl",settleRegexControl);
		return "modules/reconciliation/settleRegexControlList";
	}
}
