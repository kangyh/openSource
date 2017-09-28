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
import com.heepay.manage.modules.hgms.entity.HgmsBatchCollectionRecordDetail;
import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;
import com.heepay.manage.modules.hgms.service.HgmsBatchCollectionRecordDetailService;
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
 * 描    述：批量收款记录明细表Controller
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
@RequestMapping(value = "${adminPath}/hgms/hgmsBatchCollectionRecordDetail")
public class HgmsBatchCollectionRecordDetailController extends BaseController {

    @Autowired
    private HgmsBatchCollectionRecordDetailService hgmsBatchCollectionRecordDetailService;

    @ModelAttribute
    public HgmsBatchCollectionRecordDetail get(@RequestParam(required = false) String id) {
        HgmsBatchCollectionRecordDetail entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsBatchCollectionRecordDetailService.get(id);
        }
        if (entity == null) {
            entity = new HgmsBatchCollectionRecordDetail();
        }
        return entity;
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecordDetail:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsBatchCollectionRecordDetail hgmsBatchCollectionRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        String batchid = request.getParameter("batchId");
        if (null != batchid) {
            hgmsBatchCollectionRecordDetail.setTransBatchNo(batchid);
        }
        logger.info("代收当天明细条件参数:" + hgmsBatchCollectionRecordDetail.toString());
        //查询结果类  changeObject 此方法是将结果类初始化0
        HgmsSummaryResult result = SharmedUtils.changeObject(hgmsBatchCollectionRecordDetailService.getHgmsSummaryResult(hgmsBatchCollectionRecordDetail));
        logger.info("代收当天明细统计结果:" + result.toString());
        //查询明细记录
        Page<HgmsBatchCollectionRecordDetail> page = hgmsBatchCollectionRecordDetailService.findPage(new Page<HgmsBatchCollectionRecordDetail>(request, response), hgmsBatchCollectionRecordDetail);
        logger.info("代收当天明细page结果:" + page.toString());
        ArrayList<HgmsBatchCollectionRecordDetail> newlist = new ArrayList<HgmsBatchCollectionRecordDetail>();
        for (HgmsBatchCollectionRecordDetail CollectionHistory : page.getList()) {
            if (null != CollectionHistory) {
                switch (CollectionHistory.getTransStatus()) {
                    case "SUCCES":
                        CollectionHistory.setTransStatus("交易成功");
                        break;
                    case "FAILED":
                        CollectionHistory.setTransStatus("交易失败");
                        break;
                    case "PROCES":
                        CollectionHistory.setTransStatus("处理中");
                        break;
                }
                CollectionHistory.setSettlementStatus("SETTED".equals(CollectionHistory.getSettlementStatus()) ? "已结算" : "未结算");
                CollectionHistory.setTransWay(TransWay.labelOf(CollectionHistory.getTransWay()));
            }
            newlist.add(CollectionHistory);
        }

        model.addAttribute("HgmsSummaryResult", result);
        page.setList(newlist);
        model.addAttribute("page", page);
        return "modules/hgms/hgmsBatchCollectionRecordDetailList";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecordDetail:view")
    @RequestMapping(value = "form")
    public String form(HgmsBatchCollectionRecordDetail hgmsBatchCollectionRecordDetail, Model model) {
        model.addAttribute("hgmsBatchCollectionRecordDetail", hgmsBatchCollectionRecordDetail);
        return "modules/hgms/hgmsBatchCollectionRecordDetailForm";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecordDetail:edit")
    @RequestMapping(value = "save")
    public String save(HgmsBatchCollectionRecordDetail hgmsBatchCollectionRecordDetail, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hgmsBatchCollectionRecordDetail)) {
            return form(hgmsBatchCollectionRecordDetail, model);
        }
        hgmsBatchCollectionRecordDetailService.save(hgmsBatchCollectionRecordDetail);
        addMessage(redirectAttributes, "保存批量收款记录明细表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchCollectionRecordDetail/?repage";
    }

    @RequiresPermissions("hgms:hgmsBatchCollectionRecordDetail:edit")
    @RequestMapping(value = "delete")
    public String delete(HgmsBatchCollectionRecordDetail hgmsBatchCollectionRecordDetail, RedirectAttributes redirectAttributes) {
        hgmsBatchCollectionRecordDetailService.delete(hgmsBatchCollectionRecordDetail);
        addMessage(redirectAttributes, "删除批量收款记录明细表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchCollectionRecordDetail/?repage";
    }

}