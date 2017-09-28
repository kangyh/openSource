/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.TransWay;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsBatchPayDetailHistory;
import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;
import com.heepay.manage.modules.hgms.service.HgmsBatchPayDetailHistoryService;
import com.heepay.manage.modules.hgms.util.SharmedUtils;
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


/**
 *
 * 描    述：批量代付明细历史表Controller
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
@RequestMapping(value = "${adminPath}/hgms/hgmsBatchPayDetailHistory")
public class HgmsBatchPayDetailHistoryController extends BaseController {

    @Autowired
    private HgmsBatchPayDetailHistoryService hgmsBatchPayDetailHistoryService;

    @ModelAttribute
    public HgmsBatchPayDetailHistory get(@RequestParam(required = false) String id) {
        HgmsBatchPayDetailHistory entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsBatchPayDetailHistoryService.get(id);
        }
        if (entity == null) {
            entity = new HgmsBatchPayDetailHistory();
        }
        return entity;
    }

    @RequiresPermissions("hgms:hgmsBatchPayDetailHistory:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsBatchPayDetailHistory hgmsBatchPayDetailHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
        String batchid = request.getParameter("batchId");
        if (null != batchid) {
            hgmsBatchPayDetailHistory.setTransBatchNo(batchid);
        }
        logger.info("代付历史明细条件参数:" + hgmsBatchPayDetailHistory.toString());
        //查询结果类  changeObject 此方法是将结果类初始化0
        HgmsSummaryResult result = SharmedUtils.changeObject(hgmsBatchPayDetailHistoryService.getHgmsSummaryResult(hgmsBatchPayDetailHistory));
        logger.info("代收历史明细统计结果:" + result.toString());
        //查询明细记录
        Page<HgmsBatchPayDetailHistory> page = hgmsBatchPayDetailHistoryService.findPage(new Page<HgmsBatchPayDetailHistory>(request, response), hgmsBatchPayDetailHistory);
        logger.info("代付历史明细page结果:" + page.toString());
        ArrayList<HgmsBatchPayDetailHistory> newlist = new ArrayList<HgmsBatchPayDetailHistory>();
        for (HgmsBatchPayDetailHistory CollectionHistory : page.getList()) {
            if (null != CollectionHistory) {
                CollectionHistory.setTransStatus("SUCCES".equals(CollectionHistory.getTransStatus()) ? "交易成功" : "交易失败");
                CollectionHistory.setSettlementStatus("SETTED".equals(CollectionHistory.getSettlementStatus()) ? "已结算" : "未结算");
                CollectionHistory.setTransWay(TransWay.labelOf(CollectionHistory.getTransWay()));
            }
            newlist.add(CollectionHistory);
        }

        model.addAttribute("HgmsSummaryResult", result);
        page.setList(newlist);
        model.addAttribute("page", page);
        return "modules/hgms/hgmsBatchPayDetailHistoryList";
    }

    @RequiresPermissions("hgms:hgmsBatchPayDetailHistory:view")
    @RequestMapping(value = "form")
    public String form(HgmsBatchPayDetailHistory hgmsBatchPayDetailHistory, Model model) {
        model.addAttribute("hgmsBatchPayDetailHistory", hgmsBatchPayDetailHistory);
        return "modules/hgms/hgmsBatchPayDetailHistoryForm";
    }

    @RequiresPermissions("hgms:hgmsBatchPayDetailHistory:edit")
    @RequestMapping(value = "save")
    public String save(HgmsBatchPayDetailHistory hgmsBatchPayDetailHistory, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hgmsBatchPayDetailHistory)) {
            return form(hgmsBatchPayDetailHistory, model);
        }
        hgmsBatchPayDetailHistoryService.save(hgmsBatchPayDetailHistory);
        addMessage(redirectAttributes, "保存批量代付明细历史表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchPayDetailHistory/?repage";
    }

    @RequiresPermissions("hgms:hgmsBatchPayDetailHistory:edit")
    @RequestMapping(value = "delete")
    public String delete(HgmsBatchPayDetailHistory hgmsBatchPayDetailHistory, RedirectAttributes redirectAttributes) {
        hgmsBatchPayDetailHistoryService.delete(hgmsBatchPayDetailHistory);
        addMessage(redirectAttributes, "删除批量代付明细历史表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchPayDetailHistory/?repage";
    }

}