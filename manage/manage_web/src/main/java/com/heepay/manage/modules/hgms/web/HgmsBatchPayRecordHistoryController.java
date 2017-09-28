/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsBatchPayRecordHistory;
import com.heepay.manage.modules.hgms.service.HgmsBatchPayRecordHistoryService;
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


/**
 *
 * 描    述：批量代付汇总历史表Controller
 *
 * 创 建 者： @author 牛俊鹏
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
@RequestMapping(value = "${adminPath}/hgms/hgmsBatchPayRecordHistory")
public class HgmsBatchPayRecordHistoryController extends BaseController {

    @Autowired
    private HgmsBatchPayRecordHistoryService hgmsBatchPayRecordHistoryService;

    @ModelAttribute
    public HgmsBatchPayRecordHistory get(@RequestParam(required = false) String id) {
        HgmsBatchPayRecordHistory entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsBatchPayRecordHistoryService.get(id);
        }
        if (entity == null) {
            entity = new HgmsBatchPayRecordHistory();
        }
        return entity;
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecordHistory:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsBatchPayRecordHistory hgmsBatchPayRecordHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("代付历史汇总条件参数:" + hgmsBatchPayRecordHistory.toString());
        Page<HgmsBatchPayRecordHistory> page = hgmsBatchPayRecordHistoryService.findPage(new Page<HgmsBatchPayRecordHistory>(request, response), hgmsBatchPayRecordHistory);
        logger.info("代付历史汇总page结果:" + page.toString());
        model.addAttribute("page", page);
        return "modules/hgms/hgmsBatchPayRecordHistoryList";
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecordHistory:view")
    @RequestMapping(value = "form")
    public String form(HgmsBatchPayRecordHistory hgmsBatchPayRecordHistory, Model model) {
        model.addAttribute("hgmsBatchPayRecordHistory", hgmsBatchPayRecordHistory);
        return "modules/hgms/hgmsBatchPayRecordHistoryForm";
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecordHistory:edit")
    @RequestMapping(value = "save")
    public String save(HgmsBatchPayRecordHistory hgmsBatchPayRecordHistory, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hgmsBatchPayRecordHistory)) {
            return form(hgmsBatchPayRecordHistory, model);
        }
        hgmsBatchPayRecordHistoryService.save(hgmsBatchPayRecordHistory);
        addMessage(redirectAttributes, "保存批量代付汇总历史表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchPayRecordHistory/?repage";
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecordHistory:edit")
    @RequestMapping(value = "delete")
    public String delete(HgmsBatchPayRecordHistory hgmsBatchPayRecordHistory, RedirectAttributes redirectAttributes) {
        hgmsBatchPayRecordHistoryService.delete(hgmsBatchPayRecordHistory);
        addMessage(redirectAttributes, "删除批量代付汇总历史表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchPayRecordHistory/?repage";
    }

}