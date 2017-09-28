/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.modules.merchant.service.OnlineContractInfoCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.OnlineContractInfo;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;

import java.util.List;


/**
 *
 * 描    述：线上签约Controller
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
@RequestMapping(value = "${adminPath}/merchant/onlineContractInfoLegal")
public class OnlineContractInfoLegalController extends BaseController {

	@Autowired
	private OnlineContractInfoCService onlineContractInfoService;
	
	@ModelAttribute
	public OnlineContractInfo get(@RequestParam(required=false) String id) {
		OnlineContractInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = onlineContractInfoService.get(id);
		}
		if (entity == null){
			entity = new OnlineContractInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("merchant:onlineContractInfoLegal:view")
	@RequestMapping(value = {"list", ""})
	public String list(OnlineContractInfo onlineContractInfo, HttpServletRequest request, HttpServletResponse response, Model model) {

		//使用cookie保存查询条件
		onlineContractInfo = (OnlineContractInfo) SaveConditions.result(onlineContractInfo, "onlineContractIn", request, response);

		if (StringUtils.isBlank(onlineContractInfo.getLegalAuditStatus())) {
			onlineContractInfo.setLegalAuditStatus("NotNull");
		}
		Page<OnlineContractInfo> page = onlineContractInfoService.findPage(new Page<OnlineContractInfo>(request, response), onlineContractInfo);
		page.setList(EnumView.onlineContractInfo(page.getList()));

		model.addAttribute("page", page);
		model.addAttribute("onlineContractInfo", onlineContractInfo);
		return "modules/merchant/onlineContractInfoLegalList";
	}

	@RequiresPermissions("merchant:onlineContractInfoLegal:view")
	@RequestMapping(value = "detail")
	public String detail(OnlineContractInfo onlineContractInfo, Model model) {
		List<OnlineContractInfo> list = onlineContractInfoService.findProductList(onlineContractInfo);
		model.addAttribute("list",list);
		onlineContractInfo = onlineContractInfoService.queryProductsBybatchNoAndUserId(
				onlineContractInfo.getBatchNo(),onlineContractInfo.getUserId());
		model.addAttribute("onlineContractInfo", onlineContractInfo);
		return "modules/merchant/onlineContractInfoLegalDetail";
	}

	@RequiresPermissions("merchant:onlineContractInfoLegal:edit")
	@RequestMapping(value = "save")
	public String save(OnlineContractInfo onlineContractInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, onlineContractInfo)){
			return audit(onlineContractInfo, model);
		}
		onlineContractInfoService.legalAudit(onlineContractInfo);
		addMessage(redirectAttributes, "保存线上签约成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/onlineContractInfoLegal?cache=1&repage";
	}


	@RequiresPermissions("merchant:onlineContractInfoLegal:view")
	@RequestMapping(value = "audit")
	public String audit(OnlineContractInfo onlineContractInfo, Model model) {
		List<OnlineContractInfo> list = onlineContractInfoService.findProductList(onlineContractInfo);
		model.addAttribute("list",list);
		onlineContractInfo = onlineContractInfoService.queryProductsBybatchNoAndUserId(
				onlineContractInfo.getBatchNo(),onlineContractInfo.getUserId());
		model.addAttribute("onlineContractInfo", onlineContractInfo);
		return "modules/merchant/onlineContractInfoLegalAuditForm";
	}
}