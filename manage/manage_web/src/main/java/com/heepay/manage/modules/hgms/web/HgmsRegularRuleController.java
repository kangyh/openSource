/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.*;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsRegularRule;
import com.heepay.manage.modules.hgms.service.HgmsRegularRuleService;
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
 * 描    述：规则表Controller
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-08-10 21:49:26
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
@RequestMapping(value = "${adminPath}/hgms/hgmsRegularRule")
public class HgmsRegularRuleController extends BaseController {

    @Autowired
    private HgmsRegularRuleService hgmsRegularRuleService;

    @ModelAttribute
    public HgmsRegularRule get(@RequestParam(required = false) String id) {
        HgmsRegularRule entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsRegularRuleService.get(id);
        }
        if (entity == null) {
            entity = new HgmsRegularRule();
        }
        return entity;
    }

    /**
     * 规则列表
     *
     * @param hgmsRegularRule
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsRegularRule:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsRegularRule hgmsRegularRule, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsRegularRule> page = new Page<>(request, response);
        page.setOrderBy("a.create_time desc");
        page = hgmsRegularRuleService.findPage(page, hgmsRegularRule);
        //规则列表种的规则类型、规则模式、规则形式、交易类型、管理状态，赋值成中文
        for (HgmsRegularRule rule : page.getList()) {
            rule.setRuleType(HgmsBusinessType.labelOf(rule.getRuleType()));
            rule.setRuleForms(HgmsRuleForms.labelOf(rule.getRuleForms()));
            rule.setRuleMode(HgmsRuleMode.labelOf(rule.getRuleMode()));
            rule.setTransWay(TransWay.labelOf(rule.getTransWay()));
            rule.setAdminStatus(HgmsStatus.labelOf(rule.getAdminStatus()));
            rule.setUserStatus(HgmsStatus.labelOf(rule.getUserStatus()));
        }
        //规则列表的查询下拉框规则类型、规则模式、规则形式、交易类型，添加对应的值
        List<EnumBean> hgmsBusinessType = ListEnums.hgmsBusinessType();
        List<EnumBean> hgmsRuleForms = ListEnums.hgmsRuleForms();
        List<EnumBean> hgmsRuleMode = ListEnums.hgmsRuleMode();
        List<EnumBean> hgmsTransWay = ListEnums.hgmsTransWay();
        model.addAttribute("hgmsBusinessType", hgmsBusinessType);
        model.addAttribute("hgmsRuleForms", hgmsRuleForms);
        model.addAttribute("hgmsRuleMode", hgmsRuleMode);
        model.addAttribute("hgmsTransWay", hgmsTransWay);
        model.addAttribute("page", page);
        return "modules/hgms/hgmsRegularRuleList";
    }

    /**
     * 规则详情
     *
     * @param request
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsRegularRule:view")
    @RequestMapping(value = "detail")
    public String detail(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        HgmsRegularRule rule = hgmsRegularRuleService.get(id);
        rule.setRuleType(HgmsBusinessType.labelOf(rule.getRuleType()));
        rule.setRuleForms(HgmsRuleForms.labelOf(rule.getRuleForms()));
        rule.setRuleMode(HgmsRuleMode.labelOf(rule.getRuleMode()));
        rule.setTransWay(TransWay.labelOf(rule.getTransWay()));
        rule.setAdminStatus(HgmsStatus.labelOf(rule.getAdminStatus()));
        rule.setUserStatus(HgmsStatus.labelOf(rule.getUserStatus()));
        model.addAttribute("hgmsRegularRule", rule);
        return "modules/hgms/hgmsRegularRuleDetail";
    }

    /**
     * 修改规则状态
     *
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequiresPermissions("hgms:hgmsRegularRule:edit")
    @RequestMapping(value = "status")
    public String status(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HgmsRegularRule hgmsRegularRule = new HgmsRegularRule();
        String id = request.getParameter("id");
        String adminStatus = request.getParameter("adminStatus");
        hgmsRegularRule.setRuleId(Long.parseLong(id));
        hgmsRegularRule.setAdminStatus(adminStatus);
        boolean updateStatus = hgmsRegularRuleService.status(hgmsRegularRule) == 1;
        if (!updateStatus) {
            addMessage(redirectAttributes, "更新状态规则失败");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsRegularRule/?repage";
        }
        addMessage(redirectAttributes, "更新规则状态成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsRegularRule/?repage";
    }

}