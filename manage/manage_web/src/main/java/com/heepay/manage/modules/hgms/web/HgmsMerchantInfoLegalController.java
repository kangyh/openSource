/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.MerchantType;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.ListEnums;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantInfo;
import com.heepay.manage.modules.hgms.service.HgmsMerchantInfoService;
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
import java.util.ArrayList;
import java.util.List;


/**
 * 描    述：资金归集商户Controller
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
@RequestMapping(value = "${adminPath}/hgms/hgmsMerchantInfoLegal")
public class HgmsMerchantInfoLegalController extends BaseController {

    @Autowired
    private HgmsMerchantInfoService hgmsMerchantInfoService;

    /**
     * @param id
     * @return
     * @discription 根据id获取商户信息
     * @author gzx
     * @created 2017年3月24日  10:57:23
     */
    @ModelAttribute
    public HgmsMerchantInfo get(@RequestParam(required = false) String id) {
        HgmsMerchantInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsMerchantInfoService.get(id);
        }
        if (entity == null) {
            entity = new HgmsMerchantInfo();
        }
        return entity;
    }

    /**
     * 获取商户信息列表
     *
     * @param hgmsMerchantInfo
     * @param request
     * @param response
     * @param model
     * @return
     * @discription 获取商户信息列表
     * @author gzx
     * @created 2017年3月24日  10:57:32
     */

    @RequiresPermissions("hgms:hgmsMerchantInfoLegal:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsMerchantInfo hgmsMerchantInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        hgmsMerchantInfo.setCompanyType("ENTERP");
        Page<HgmsMerchantInfo> page = new Page<>(request, response);
        page.setOrderBy("a.create_time desc");
        page = hgmsMerchantInfoService.findPage(page, hgmsMerchantInfo);
        List<HgmsMerchantInfo> list = new ArrayList<>();
        //记录前端同步的用户个数
        //循环将商户的登录名、商户状态、商户的当前负责人添加到商户中去
        for (HgmsMerchantInfo hgmsMerchantInfonew : page.getList()) {
            if (StringUtils.isNotEmpty(hgmsMerchantInfonew.getType()))
                hgmsMerchantInfonew.setType(MerchantType.labelOf(hgmsMerchantInfonew.getType()));
            hgmsMerchantInfonew.setLegalAuditStatus(RouteStatus.labelOf(hgmsMerchantInfonew.getLegalAuditStatus()));
            list.add(hgmsMerchantInfonew);
        }
        page.setList(list);
        logger.info("资金归集商户的列表信息展示状态----->成功----->对应的资金归集商户信息条数：" + page.getCount());
        model.addAttribute("page", page);
        return "modules/hgms/hgmsMerchantInfoLegalList";
    }


    /**
     * 跳转修改页面
     *
     * @param id
     * @param goal
     * @param model
     * @param redirectAttributes
     * @return
     * @discription 获取综合商户修改页面
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("hgms:hgmsMerchantInfoLegal:edit")
    @RequestMapping(value = "audit")
    public String audit(@RequestParam(value = "id") Integer id, @RequestParam(value = "goal") Integer goal, Model model, RedirectAttributes redirectAttributes) {
        //根据merchantNo获取HgmsMerchantInfo对象
        HgmsMerchantInfo hgmsMerchantInfo = hgmsMerchantInfoService.get(id.toString());
        //显示封装
        HgmsMerchantInfo hgmsMerchantInfoAll = hgmsMerchantInfoService.integrateUserAndMerchant(hgmsMerchantInfo, goal);
        logger.info("获取综合商户修改页面-----资金归集商户信息：" + hgmsMerchantInfo.toString());

        //跳转修改页面
        //法务审核页面信用等级
        List<EnumBean> creditLevel = ListEnums.creditLevel();
        model.addAttribute("creditLevel", creditLevel);
        model.addAttribute("hgmsMerchantInfo", hgmsMerchantInfoAll);
        addMessage(redirectAttributes, "进入资金归集商户审核页面成功");
        return "modules/hgms/hgmsMerchantInfoLegalAudit";
    }

    /**
     * 跳转修改页面
     *
     * @param hgmsMerchantInfo
     * @param redirectAttributes
     * @return
     * @discription 获取综合商户修改页面
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("hgms:hgmsMerchantInfoLegal:edit")
    @RequestMapping(value = "update")
    public String update(HgmsMerchantInfo hgmsMerchantInfo, RedirectAttributes redirectAttributes) {
        hgmsMerchantInfoService.legalAuditStatus(hgmsMerchantInfo);
        if ("SUCCES".equals(hgmsMerchantInfo.getLegalAuditStatus()) && "AUDING".equals(hgmsMerchantInfo.getRcAuditStatus())) {
            logger.info("资金归集商户审核状态：----->成功----->审核的资金归集商户信息：" + hgmsMerchantInfo.toString());
            addMessage(redirectAttributes, "资金归集商户审核成功，商户账号" + hgmsMerchantInfo.getLoginName());
        } else if ("AUDREJ".equals(hgmsMerchantInfo.getLegalAuditStatus())) {
            logger.info("资金归集商户审核状态：----->不通过----->审核的资金归集商户信息：" + hgmsMerchantInfo.toString());
            addMessage(redirectAttributes, "资金归集商户审核不通过，商户账号" + hgmsMerchantInfo.getLoginName());
        }
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfoLegal/?repage";
    }
}