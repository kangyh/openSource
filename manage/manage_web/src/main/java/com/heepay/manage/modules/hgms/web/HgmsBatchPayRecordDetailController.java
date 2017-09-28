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
import com.heepay.manage.modules.hgms.entity.HgmsBatchPayRecordDetail;
import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;
import com.heepay.manage.modules.hgms.service.HgmsBatchPayRecordDetailService;
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
 * 描    述：批量代付明细表Controller
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
@RequestMapping(value = "${adminPath}/hgms/hgmsBatchPayRecordDetail")
public class HgmsBatchPayRecordDetailController extends BaseController {

    @Autowired
    private HgmsBatchPayRecordDetailService hgmsBatchPayRecordDetailService;

    @ModelAttribute
    public HgmsBatchPayRecordDetail get(@RequestParam(required = false) String id) {
        HgmsBatchPayRecordDetail entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsBatchPayRecordDetailService.get(id);
        }
        if (entity == null) {
            entity = new HgmsBatchPayRecordDetail();
        }
        return entity;
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecordDetail:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsBatchPayRecordDetail hgmsBatchPayRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        String batchid = request.getParameter("batchId");
        if (null != batchid) {
            hgmsBatchPayRecordDetail.setTransBatchNo(batchid);
        }
        logger.info("代付当天明细条件参数:" + hgmsBatchPayRecordDetail.toString());
        //查询结果类  changeObject 此方法是将结果类初始化0
        HgmsSummaryResult result = SharmedUtils.changeObject(hgmsBatchPayRecordDetailService.getHgmsSummaryResult(hgmsBatchPayRecordDetail));
        logger.info("代付当天明细统计结果:" + result.toString());
        //查询明细记录
        Page<HgmsBatchPayRecordDetail> page = hgmsBatchPayRecordDetailService.findPage(new Page<HgmsBatchPayRecordDetail>(request, response), hgmsBatchPayRecordDetail);
        logger.info("代付当天明细page结果:" + page.toString());
        ArrayList<HgmsBatchPayRecordDetail> newlist = new ArrayList<HgmsBatchPayRecordDetail>();
        for (HgmsBatchPayRecordDetail CollectionHistory : page.getList()) {
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
        return "modules/hgms/hgmsBatchPayRecordDetailList";
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecordDetail:view")
    @RequestMapping(value = "form")
    public String form(HgmsBatchPayRecordDetail hgmsBatchPayRecordDetail, Model model) {
        model.addAttribute("hgmsBatchPayRecordDetail", hgmsBatchPayRecordDetail);
        return "modules/hgms/hgmsBatchPayRecordDetailForm";
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecordDetail:edit")
    @RequestMapping(value = "save")
    public String save(HgmsBatchPayRecordDetail hgmsBatchPayRecordDetail, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hgmsBatchPayRecordDetail)) {
            return form(hgmsBatchPayRecordDetail, model);
        }
        hgmsBatchPayRecordDetailService.save(hgmsBatchPayRecordDetail);
        addMessage(redirectAttributes, "保存批量代付明细表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchPayRecordDetail/?repage";
    }

    @RequiresPermissions("hgms:hgmsBatchPayRecordDetail:edit")
    @RequestMapping(value = "delete")
    public String delete(HgmsBatchPayRecordDetail hgmsBatchPayRecordDetail, RedirectAttributes redirectAttributes) {
        hgmsBatchPayRecordDetailService.delete(hgmsBatchPayRecordDetail);
        addMessage(redirectAttributes, "删除批量代付明细表成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsBatchPayRecordDetail/?repage";
    }

}