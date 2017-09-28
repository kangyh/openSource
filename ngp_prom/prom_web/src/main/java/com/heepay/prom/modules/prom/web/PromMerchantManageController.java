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
import com.heepay.prom.modules.prom.entity.PromMerchantManage;
import com.heepay.prom.modules.prom.enums.*;
import com.heepay.prom.modules.prom.service.PromMerchantManageService;
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
import java.util.List;


/**
 *
 * 描    述：商户管理Controller
 *
 * 创 建 者： @author wangdong
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
@RequestMapping(value = "${adminPath}/prom/promMerchantManage")
public class PromMerchantManageController extends BaseController {

	@Autowired
	private PromMerchantManageService promMerchantManageService;

	@ModelAttribute
	public PromMerchantManage get(@RequestParam(required=false) String id) {
		PromMerchantManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = promMerchantManageService.get(id);
		}
		if (entity == null){
			entity = new PromMerchantManage();
		}
		return entity;
	}
	
	@RequiresPermissions("prom:promMerchantManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(PromMerchantManage promMerchantManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PromMerchantManage> page = promMerchantManageService.findPage(new Page<PromMerchantManage>(request, response), promMerchantManage);
        List<PromMerchantManage> promMerchantManageList = Lists.newArrayList();
        for (PromMerchantManage prom : page.getList()) {
            if(StringUtils.isNotBlank(prom.getMerchantType())){
                prom.setMerchantType(MerchantType.labelOf(prom.getMerchantType()));
            }
            if(StringUtils.isNotBlank(prom.getStatus())){
                prom.setStatus(Status.labelOf(prom.getStatus()));
            }
            if(StringUtils.isNotBlank(prom.getMerSource())){
                prom.setMerSource(SourceType.labelOf(prom.getMerSource()));
            }
            promMerchantManageList.add(prom);
        }
        page.setList(promMerchantManageList);
        model.addAttribute("page", page);
		return "modules/prom/promMerchantManageList";
	}

	@RequiresPermissions("prom:promMerchantManage:view")
	@RequestMapping(value = "form")
	public String form(PromMerchantManage promMerchantManage, Model model) {
        if (null != promMerchantManage && null != promMerchantManage.getMerchantId()) {
            promMerchantManage = get(promMerchantManage.getMerchantId().toString());
            model.addAttribute("promMerchantManage", promMerchantManage);
        }
        List<EnumBean> merchantTypeList = Lists.newArrayList();
        for (MerchantType type : MerchantType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(type.getValue());
            ct.setName(type.getContent());
            merchantTypeList.add(ct);
        }
        model.addAttribute("merchantTypeList", merchantTypeList);
        List<EnumBean> sourceTypeList = Lists.newArrayList();
        for (SourceType type : SourceType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(type.getValue());
            ct.setName(type.getContent());
            sourceTypeList.add(ct);
        }
        model.addAttribute("sourceTypeList", sourceTypeList);
        List<EnumBean> payTypeList = Lists.newArrayList();
        for (PayType type : PayType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(type.getValue());
            ct.setName(type.getContent());
            payTypeList.add(ct);
        }
        model.addAttribute("payTypeList", payTypeList);
        List<EnumBean> status = Lists.newArrayList();
        for (Status stats : Status.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(stats.getValue());
            ct.setName(stats.getContent());
            status.add(ct);
        }
        model.addAttribute("status", status);
		return "modules/prom/promMerchantManageForm";
	}

	@RequiresPermissions("prom:promMerchantManage:edit")
	@RequestMapping(value = "save")
	public String save(PromMerchantManage promMerchantManage, Model model, RedirectAttributes redirectAttributes) {
		promMerchantManageService.save(promMerchantManage);
		addMessage(redirectAttributes, "修改商户成功");
		return "redirect:"+Global.getAdminPath()+"/prom/promMerchantManage/?repage";
	}
}