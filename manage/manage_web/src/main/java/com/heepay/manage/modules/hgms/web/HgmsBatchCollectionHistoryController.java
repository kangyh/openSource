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
import com.heepay.manage.modules.hgms.entity.HgmsBatchCollectionHistory;
import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;
import com.heepay.manage.modules.hgms.service.HgmsBatchCollectionHistoryService;
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
 * 描    述：历史代收明细查询Controller
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
@RequestMapping(value = "${adminPath}/hgms/hgmsBatchCollectionHistory")
public class HgmsBatchCollectionHistoryController extends BaseController {

    @Autowired
    private HgmsBatchCollectionHistoryService hgmsBatchCollectionHistoryService;

    @ModelAttribute
    public HgmsBatchCollectionHistory get(@RequestParam(required = false) String id) {
        HgmsBatchCollectionHistory entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsBatchCollectionHistoryService.get(id);
        }
        if (entity == null) {
            entity = new HgmsBatchCollectionHistory();
        }
        return entity;
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionHistory:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsBatchCollectionHistory hgmsBatchCollectionHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
        String batchid = request.getParameter("batchId");
        if (null != batchid) {
            hgmsBatchCollectionHistory.setTransBatchNo(batchid);
        }
        logger.info("代收历史明细条件参数:" + hgmsBatchCollectionHistory.toString());
        //查询结果类  changeObject 此方法是将结果类初始化0
        HgmsSummaryResult result = SharmedUtils.changeObject(hgmsBatchCollectionHistoryService.getHgmsSummaryResult(hgmsBatchCollectionHistory));
        logger.info("代收历史明细统计结果:" + result.toString());
        //查询明细记录
        Page<HgmsBatchCollectionHistory> page = hgmsBatchCollectionHistoryService.findPage(new Page<HgmsBatchCollectionHistory>(request, response), hgmsBatchCollectionHistory);
        logger.info("代收历史明细page结果:" + page.toString());
        ArrayList<HgmsBatchCollectionHistory> newlist = new ArrayList<HgmsBatchCollectionHistory>();
        for (HgmsBatchCollectionHistory collectionHistory : page.getList()) {
            if (null != collectionHistory) {
                logger.info("打印代收历史明细对象" + collectionHistory.toString());
                collectionHistory.setTransStatus("SUCCES".equals(collectionHistory.getTransStatus()) ? "交易成功" : "交易失败");
                collectionHistory.setSettlementStatus("SETTED".equals(collectionHistory.getSettlementStatus()) ? "已结算" : "未结算");
                collectionHistory.setTransWay(TransWay.labelOf(collectionHistory.getTransWay()));
            }
            newlist.add(collectionHistory);
        }


        model.addAttribute("HgmsSummaryResult", result);
        page.setList(newlist);
        model.addAttribute("page", page);
        return "modules/hgms/hgmsBatchCollectionHistoryList";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionHistory:view")
    @RequestMapping(value = "form")
    public String form(HgmsBatchCollectionHistory hgmsBatchCollectionHistory, Model model) {
        model.addAttribute("hgmsBatchCollectionHistory", hgmsBatchCollectionHistory);
        return "modules/hgms/hgmsBatchCollectionHistoryForm";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionHistory:edit")
    @RequestMapping(value = "save")
    public String save(HgmsBatchCollectionHistory hgmsBatchCollectionHistory, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hgmsBatchCollectionHistory)) {
            return form(hgmsBatchCollectionHistory, model);
        }
        hgmsBatchCollectionHistoryService.save(hgmsBatchCollectionHistory);
        addMessage(redirectAttributes, "保存历史代收明细查询成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchCollectionHistory/?repage";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionHistory:edit")
    @RequestMapping(value = "delete")
    public String delete(HgmsBatchCollectionHistory hgmsBatchCollectionHistory, RedirectAttributes redirectAttributes) {
        hgmsBatchCollectionHistoryService.delete(hgmsBatchCollectionHistory);
        addMessage(redirectAttributes, "删除历史代收明细查询成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchCollectionHistory/?repage";
    }

}