/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.config.Global;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.utils.EnumBean;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromBinding;
import com.heepay.prom.modules.prom.entity.PromMerchantManage;
import com.heepay.prom.modules.prom.enums.Status;
import com.heepay.prom.modules.prom.service.PromBindingService;
import com.heepay.prom.modules.prom.service.PromMerchantManageService;
import com.heepay.prom.modules.prom.utils.BindingId;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 描 述：promController
 *
 * 创 建 者： @author wj 创建时间： 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/prom/promBinding")
public class PromBindingController extends BaseController {

	@Autowired
	private PromBindingService promBindingService;
	@Autowired
	private PromMerchantManageService promMerchantManageService;

	@ModelAttribute
	public PromBinding get(@RequestParam(required = false) String id) {
		PromBinding entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = promBindingService.get(id);
		}
		if (entity == null) {
			entity = new PromBinding();
		}
		return entity;
	}

	@RequiresPermissions("prom:promBinding:view")
	@RequestMapping(value = { "list", "" })
	public String list(PromBinding promBinding, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PromBinding> page = promBindingService.findPage(new Page<PromBinding>(request, response), promBinding);
		for (PromBinding promBinding1 : page.getList()) {
			// 状态 ENABLE(启用) DISABL(禁用)
			if (StringUtils.isNotBlank(promBinding1.getStatus())) {
				promBinding1.setStatus(Status.labelOf(promBinding1.getStatus()));
			}
		}
		List<EnumBean> status = Lists.newArrayList();
		for (Status stats : Status.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(stats.getValue());
			ct.setName(stats.getContent());
			status.add(ct);
		}
		model.addAttribute("status", status);

		model.addAttribute("promBinding", promBinding);
		model.addAttribute("page", page);
		return "modules/prom/promBindingList";
	}

	@RequiresPermissions("prom:promBinding:view")
	@RequestMapping(value = "form")
	public String form(PromBinding promBinding, Model model) {
		if (null != promBinding && null != promBinding.getPromId()) {
			promBinding = get(promBinding.getPromId().toString());
			model.addAttribute("promBinding", promBinding);
		}
		
		List<PromMerchantManage> merchantIdList = promMerchantManageService.findAllList();
		List<EnumBean> status = Lists.newArrayList();
		for (Status stats : Status.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(stats.getValue());
			ct.setName(stats.getContent());
			status.add(ct);
		}
		model.addAttribute("status", status);
		model.addAttribute("merchantIdList", merchantIdList);
		return "modules/prom/promBindingForm";
	}

	@RequiresPermissions("prom:promBinding:edit")
	@RequestMapping(value = "save")
	public String save(PromBinding promBinding, Model model, RedirectAttributes redirectAttributes) {
		if (promBinding.getPromId() == null) {
			promBinding.setBindingId(BindingId.getBindingId());
			promBindingService.save(promBinding);
		} else {
			promBindingService.update(promBinding);
		}
		addMessage(redirectAttributes, "保存推广位绑定成功");
		return "redirect:" + Global.getAdminPath() + "/prom/promBinding/?repage";
	}

	@RequiresPermissions("prom:promBinding:edit")
	@RequestMapping(value = "delete")
	public String delete(PromBinding promBinding, RedirectAttributes redirectAttributes) {
		promBindingService.delete(promBinding);
		addMessage(redirectAttributes, "删除推广位绑定成功");
		return "redirect:" + Global.getAdminPath() + "/prom/promBinding/?repage";
	}

	@RequiresPermissions("prom:promBinding:edit")
	@RequestMapping(value = "queryMerchantName")
	@ResponseBody
	public String queryMerchantName(HttpServletRequest request, HttpServletResponse response,
                                    RedirectAttributes redirectAttributes) {
		String merchantId = request.getParameter("merchantId");
		String merchantName = promMerchantManageService.selectByMerchantId(merchantId);
		return merchantName;
	}

}