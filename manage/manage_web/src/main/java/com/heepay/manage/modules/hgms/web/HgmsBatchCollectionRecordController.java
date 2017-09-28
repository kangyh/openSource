/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsBatchCollectionRecord;
import com.heepay.manage.modules.hgms.service.HgmsBatchCollectionRecordService;
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
 * 描    述：批量收款记录汇总表Controller
 * <p>
 * 创 建 者： @author 牛俊鹏
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
@RequestMapping(value = "${adminPath}/hgms/hgmsBatchCollectionRecord")
public class HgmsBatchCollectionRecordController extends BaseController {

    @Autowired
    private HgmsBatchCollectionRecordService hgmsBatchCollectionRecordService;

    @ModelAttribute
    public HgmsBatchCollectionRecord get(@RequestParam(required = false) String id) {
        HgmsBatchCollectionRecord entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsBatchCollectionRecordService.get(id);
        }
        if (entity == null) {
            entity = new HgmsBatchCollectionRecord();
        }
        return entity;
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsBatchCollectionRecord hgmsBatchCollectionRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("代收当天汇总条件参数:" + hgmsBatchCollectionRecord.toString());
        Page<HgmsBatchCollectionRecord> page = hgmsBatchCollectionRecordService.findPage(new Page<HgmsBatchCollectionRecord>(request, response), hgmsBatchCollectionRecord);
        logger.info("代收当天汇总page结果:" + page.toString());
        model.addAttribute("page", page);
        return "modules/hgms/hgmsBatchCollectionRecordList";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecord:view")
    @RequestMapping(value = "form")
    public String form(HgmsBatchCollectionRecord hgmsBatchCollectionRecord, Model model) {
        model.addAttribute("hgmsBatchCollectionRecord", hgmsBatchCollectionRecord);
        return "modules/hgms/hgmsBatchCollectionRecordForm";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecord:edit")
    @RequestMapping(value = "save")
    public String save(HgmsBatchCollectionRecord hgmsBatchCollectionRecord, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hgmsBatchCollectionRecord)) {
            return form(hgmsBatchCollectionRecord, model);
        }
        hgmsBatchCollectionRecordService.save(hgmsBatchCollectionRecord);
        addMessage(redirectAttributes, "保存批量收款记录汇总表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchCollectionRecord/?repage";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecord:edit")
    @RequestMapping(value = "delete")
    public String delete(HgmsBatchCollectionRecord hgmsBatchCollectionRecord, RedirectAttributes redirectAttributes) {
        hgmsBatchCollectionRecordService.delete(hgmsBatchCollectionRecord);
        addMessage(redirectAttributes, "删除批量收款记录汇总表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchCollectionRecord/?repage";
    }

}