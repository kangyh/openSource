/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsBatchCollectionRecordHistory;
import com.heepay.manage.modules.hgms.service.HgmsBatchCollectionRecordHistoryService;
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
 * 描    述：批量收款汇总历史查询Controller
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
@RequestMapping(value = "${adminPath}/hgms/hgmsBatchCollectionRecordHistory")
public class HgmsBatchCollectionRecordHistoryController extends BaseController {

    @Autowired
    private HgmsBatchCollectionRecordHistoryService hgmsBatchCollectionRecordHistoryService;

    @ModelAttribute
    public HgmsBatchCollectionRecordHistory get(@RequestParam(required = false) String id) {
        HgmsBatchCollectionRecordHistory entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsBatchCollectionRecordHistoryService.get(id);
        }
        if (entity == null) {
            entity = new HgmsBatchCollectionRecordHistory();
        }
        return entity;
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecordHistory:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsBatchCollectionRecordHistory hgmsBatchCollectionRecordHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("代收历史汇总条件参数:" + hgmsBatchCollectionRecordHistory.toString());
        Page<HgmsBatchCollectionRecordHistory> page = hgmsBatchCollectionRecordHistoryService.findPage(new Page<HgmsBatchCollectionRecordHistory>(request, response), hgmsBatchCollectionRecordHistory);
        logger.info("代收历史汇总page结果:" + page.toString());
        model.addAttribute("page", page);
        return "modules/hgms/hgmsBatchCollectionRecordHistoryList";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecordHistory:view")
    @RequestMapping(value = "form")
    public String form(HgmsBatchCollectionRecordHistory hgmsBatchCollectionRecordHistory, Model model) {
        model.addAttribute("hgmsBatchCollectionRecordHistory", hgmsBatchCollectionRecordHistory);
        return "modules/hgms/hgmsBatchCollectionRecordHistoryForm";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecordHistory:edit")
    @RequestMapping(value = "save")
    public String save(HgmsBatchCollectionRecordHistory hgmsBatchCollectionRecordHistory, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hgmsBatchCollectionRecordHistory)) {
            return form(hgmsBatchCollectionRecordHistory, model);
        }
        hgmsBatchCollectionRecordHistoryService.save(hgmsBatchCollectionRecordHistory);
        addMessage(redirectAttributes, "保存批量收款汇总历史表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchCollectionRecordHistory/?repage";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecordHistory:edit")
    @RequestMapping(value = "delete")
    public String delete(HgmsBatchCollectionRecordHistory hgmsBatchCollectionRecordHistory, RedirectAttributes redirectAttributes) {
        hgmsBatchCollectionRecordHistoryService.delete(hgmsBatchCollectionRecordHistory);
        addMessage(redirectAttributes, "删除批量收款汇总历史表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchCollectionRecordHistory/?repage";
    }

}