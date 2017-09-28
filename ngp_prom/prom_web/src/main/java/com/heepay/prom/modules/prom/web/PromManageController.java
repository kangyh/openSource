/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.web;

import com.google.common.collect.Lists;
import com.heepay.billingutils.Base64Utils;
import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.config.Global;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.utils.EnumBean;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromManage;
import com.heepay.prom.modules.prom.enums.PromType;
import com.heepay.prom.modules.prom.enums.Status;
import com.heepay.prom.modules.prom.service.PromManageService;
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
 * 描    述：推广位管理Controller
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
@RequestMapping(value = "${adminPath}/prom/promManage")
public class PromManageController extends BaseController {

	@Autowired
	private PromManageService promManageService;
	
	@ModelAttribute
	public PromManage get(@RequestParam(required=false) String id) {
		PromManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = promManageService.get(id);
		}
		if (entity == null){
			entity = new PromManage();
		}
		return entity;
	}
	
	@RequiresPermissions("prom:promManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(PromManage promManage, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<PromManage> page = promManageService.findPage(new Page<PromManage>(request, response), promManage);
        List<PromManage> promManageList = Lists.newArrayList();
        for (PromManage prom : page.getList()) {
            if(StringUtils.isNotBlank(prom.getProType())){
                prom.setProType(PromType.labelOf(prom.getProType()));
            }
            if(StringUtils.isNotBlank(prom.getStatus())){
                prom.setStatus(Status.labelOf(prom.getStatus()));
            }
            if(StringUtils.isNotBlank(prom.getProUrl())){
                String decode = Base64Utils.decodeData(prom.getProUrl());
                prom.setProUrl(decode);
            }
            promManageList.add(prom);
        }
        page.setList(promManageList);
		model.addAttribute("page", page);
		return "modules/prom/promManageList";
	}

	@RequiresPermissions("prom:promManage:view")
	@RequestMapping(value = "form")
	public String form(PromManage promManage, Model model) {
        if (null != promManage && null != promManage.getProId()) {
            promManage = get(promManage.getProId().toString());
            model.addAttribute("promManage", promManage);
        }

        List<EnumBean> promType = Lists.newArrayList();
        for (PromType type : PromType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(type.getValue());
            ct.setName(type.getContent());
            promType.add(ct);
        }
        model.addAttribute("promType", promType);

        List<EnumBean> status = Lists.newArrayList();
        for (Status stats : Status.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(stats.getValue());
            ct.setName(stats.getContent());
            status.add(ct);
        }
        model.addAttribute("status", status);
		return "modules/prom/promManageForm";
	}

	@RequiresPermissions("prom:promManage:edit")
	@RequestMapping(value = "save")
	public String save(PromManage promManage, Model model, RedirectAttributes redirectAttributes) {
        Integer count = promManageService.findExit(promManage);
        if(count > 0){
            model.addAttribute("message","保存推广位:"+promManage.getPromotionId()+" 重复!");
            return form(promManage,model);
        }
		promManageService.save(promManage);
		if(null != promManage && promManage.getProId() != null){
            addMessage(redirectAttributes, "修改推广位成功");
        }else {
            addMessage(redirectAttributes, "保存推广位成功");
        }
		return "redirect:"+Global.getAdminPath()+"/prom/promManage/?repage";
	}

}