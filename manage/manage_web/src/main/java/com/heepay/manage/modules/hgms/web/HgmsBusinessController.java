/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.HgmsStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsBusiness;
import com.heepay.manage.modules.hgms.service.HgmsBusinessService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 描    述：业务管理Controller
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间：
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Controller
@RequestMapping(value = "${adminPath}/hgms/hgmsBusiness")
public class HgmsBusinessController extends BaseController {

    @Autowired
    private HgmsBusinessService hgmsBusinessService;

    @ModelAttribute
    public HgmsBusiness get(@RequestParam(required = false) String id) {
        HgmsBusiness entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsBusinessService.get(id);
        }
        if (entity == null) {
            entity = new HgmsBusiness();
        }
        return entity;
    }

    /**
     * 业务列表
     *
     * @param hgmsBusiness
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsBusiness:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsBusiness hgmsBusiness, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsBusiness> page = new Page<>(request, response);
        //已创建日期到排序
        page.setOrderBy("a.created_time desc");
        page = hgmsBusinessService.findPage(page, hgmsBusiness);
        for (HgmsBusiness hgmsBusinessTemp : page.getList()) {
            //同步前端商户信息
            if (StringUtils.isNotBlank(hgmsBusinessTemp.getStatus())) {
                hgmsBusinessTemp.setStatus(HgmsStatus.labelOf(hgmsBusinessTemp.getStatus()));
            }
        }
        model.addAttribute("page", page);
        return "modules/hgms/hgmsBusinessList";
    }

    /**
     * 进入业务添加和修改页面
     *
     * @param hgmsBusiness
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsBusiness:view")
    @RequestMapping(value = "form")
    public String form(HgmsBusiness hgmsBusiness, Model model) {
        model.addAttribute("hgmsBusiness", hgmsBusiness);
        return "modules/hgms/hgmsBusinessForm";
    }

    /**
     * 业务类型保存
     *
     * @param hgmsBusiness
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("hgms:hgmsBusiness:edit")
    @RequestMapping(value = "save")
    public String save(HgmsBusiness hgmsBusiness, Model model, RedirectAttributes redirectAttributes) {
        if (ObjectUtils.isEmpty(hgmsBusiness)) {
            addMessage(redirectAttributes, "请填写业务类型名称");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsServiceItem/?repage";
        }
        HgmsBusiness hgmsBusinessTemp = hgmsBusinessService.selectByName(hgmsBusiness.getBusinessName());
        if (ObjectUtils.isEmpty(hgmsBusinessTemp)) {
            hgmsBusinessService.save(hgmsBusiness);
            addMessage(redirectAttributes, "保存业务类型成功");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBusiness/?repage";
        }
        addMessage(redirectAttributes, "保存业务类型失败，该业务类型名称已存在");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBusiness/?repage";
    }

    /**
     * 修改业务状态
     *
     * @param hgmsBusiness
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("hgms:hgmsBusiness:edit")
    @RequestMapping(value = "status")
    public String status(HgmsBusiness hgmsBusiness, RedirectAttributes redirectAttributes) {
        boolean businessStatus = hgmsBusinessService.status(hgmsBusiness);
        addMessage(redirectAttributes, "成功");
        logger.info("修改业务状态: " + businessStatus + "->对应的业务ID：" + hgmsBusiness.getBusinessId() + ",对应的业务名称：" + hgmsBusiness.getBusinessName());
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBusiness/?repage";
    }

}