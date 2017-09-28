/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.hgms.rpc.hgmsweb.module.CutDataRequest;
import com.heepay.hgms.rpc.hgmsweb.module.CutDataResult;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.ListEnums;
import com.heepay.manage.common.enums.PayTransStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsSynDataTaskRecord;
import com.heepay.manage.modules.hgms.rpc.client.CutDataByDayClient;
import com.heepay.manage.modules.hgms.service.HgmsSynDataTaskRecordService;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 描    述： 日切任务controller层（每天早上10点同步昨天所有订单）
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间：
 * 创建描述：
 * <p>
 * 修 改 者：guozx@9186.com
 * 修改时间：2017-08-28 10:34:28
 * 修改描述：添加对应的
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Controller
@RequestMapping(value = "${adminPath}/hgms/hgmsSynDataTaskRecord")
public class HgmsSynDataTaskRecordController extends BaseController {

    @Autowired
    private HgmsSynDataTaskRecordService hgmsSynDataTaskRecordService;
    @Autowired
    private CutDataByDayClient cutDataByDayClient;

    @ModelAttribute
    public HgmsSynDataTaskRecord get(@RequestParam(required = false) String id) {
        HgmsSynDataTaskRecord entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsSynDataTaskRecordService.get(id);
        }
        if (entity == null) {
            entity = new HgmsSynDataTaskRecord();
        }
        return entity;
    }

    /**
     * 日切任务列表
     *
     * @param hgmsSynDataTaskRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsSynDataTaskRecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsSynDataTaskRecord hgmsSynDataTaskRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsSynDataTaskRecord> page = hgmsSynDataTaskRecordService.findPage(new Page<>(request, response), hgmsSynDataTaskRecord);
        for (HgmsSynDataTaskRecord hgmsSynDataTaskRecordTemp : page.getList()) {
            hgmsSynDataTaskRecordTemp.setTransType("PAY".equals(hgmsSynDataTaskRecordTemp.getTransType()) ? "代付" : "代收");
            hgmsSynDataTaskRecordTemp.setStatus(PayTransStatus.labelOf(hgmsSynDataTaskRecordTemp.getStatus()));
        }
        List<EnumBean> payTransStatus = ListEnums.payTransStatus();
        model.addAttribute("payTransStatus", payTransStatus);
        model.addAttribute("page", page);
        return "modules/hgms/hgmsSynDataTaskRecordList";
    }

    /**
     * 执行日切任务
     *
     * @param hgmsSynDataTaskRecord
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("hgms:hgmsSynDataTaskRecord:edit")
    @RequestMapping(value = "cutData")
    public String cutData(HgmsSynDataTaskRecord hgmsSynDataTaskRecord, RedirectAttributes redirectAttributes) {
        String message;
        boolean status = hgmsSynDataTaskRecordService.checkYesterDayTaskStatus(hgmsSynDataTaskRecord.getCheckBeginDate(), hgmsSynDataTaskRecord.getCheckEndDate());
        if (!status) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(hgmsSynDataTaskRecord.getCheckBeginDate());
            addMessage(redirectAttributes, date + "任务失败或者没有执行，请先执行！");
            logger.info(date + "日切任务失败或者没有执行，请先执行！");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsSynDataTaskRecord/?repage";
        }
        //判断是否是今天的订单
        if (new Date().compareTo(hgmsSynDataTaskRecord.getCheckBeginDate()) == 1) {
            message = "执行日切任务任务成功";
            CutDataRequest cutDataRequest = new CutDataRequest();
            String loginName = UserUtils.getUser().getLoginName();//操作人
            cutDataRequest.setTaskId(hgmsSynDataTaskRecord.getId());
            cutDataRequest.setOperator(loginName);
            CutDataResult cutDataResult = cutDataByDayClient.doCutData(cutDataRequest);
            if (ObjectUtils.isEmpty(cutDataResult)) {
                message = "汇聚财服务不可用，稍后再试！";
            }else if (cutDataResult.getRetCode() != 1000) {
                message = cutDataResult.getRetMsg();
            }
            addMessage(redirectAttributes, message);
            logger.info("执行手动日切任务结束");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsSynDataTaskRecord/?repage";
        }
        message = "请于明天执行该任务";
        addMessage(redirectAttributes, message);
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsSynDataTaskRecord/?repage";
    }

}