/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsBatchPayRecord;
import com.heepay.manage.modules.hgms.service.HgmsBatchPayRecordService;
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
 * 描    述：批量代付汇总表Controller
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
@RequestMapping(value = "${adminPath}/hgms/hgmsBatchPayRecord")
public class HgmsBatchPayRecordController extends BaseController {

    @Autowired
    private HgmsBatchPayRecordService hgmsBatchPayRecordService;

    @ModelAttribute
    public HgmsBatchPayRecord get(@RequestParam(required = false) String id) {
        HgmsBatchPayRecord entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsBatchPayRecordService.get(id);
        }
        if (entity == null) {
            entity = new HgmsBatchPayRecord();
        }
        return entity;
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsBatchPayRecord hgmsBatchPayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("代付当天汇总条件参数:" + hgmsBatchPayRecord.toString());
        Page<HgmsBatchPayRecord> page = hgmsBatchPayRecordService.findPage(new Page<HgmsBatchPayRecord>(request, response), hgmsBatchPayRecord);
        logger.info("代付当天汇总page结果:" + page.toString());
        model.addAttribute("page", page);
        return "modules/hgms/hgmsBatchPayRecordList";
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecord:view")
    @RequestMapping(value = "form")
    public String form(HgmsBatchPayRecord hgmsBatchPayRecord, Model model) {
        model.addAttribute("hgmsBatchPayRecord", hgmsBatchPayRecord);
        return "modules/hgms/hgmsBatchPayRecordForm";
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecord:edit")
    @RequestMapping(value = "save")
    public String save(HgmsBatchPayRecord hgmsBatchPayRecord, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hgmsBatchPayRecord)) {
            return form(hgmsBatchPayRecord, model);
        }
        hgmsBatchPayRecordService.save(hgmsBatchPayRecord);
        addMessage(redirectAttributes, "保存批量代付汇总表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchPayRecord/?repage";
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecord:edit")
    @RequestMapping(value = "delete")
    public String delete(HgmsBatchPayRecord hgmsBatchPayRecord, RedirectAttributes redirectAttributes) {
        hgmsBatchPayRecordService.delete(hgmsBatchPayRecord);
        addMessage(redirectAttributes, "删除批量代付汇总表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchPayRecord/?repage";
    }

}