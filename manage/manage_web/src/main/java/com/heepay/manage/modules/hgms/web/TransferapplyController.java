/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.HgmsTransferType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.Transferapply;
import com.heepay.manage.modules.hgms.rpc.client.InnerTransferClient;
import com.heepay.manage.modules.hgms.service.TransferapplyService;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.rpc.payment.model.InnerTransRecordModel;
import com.heepay.rpc.payment.model.InnerTransResult;
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
import java.util.Date;


/**
 *
 * 描    述：转账审核Controller
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
@RequestMapping(value = "${adminPath}/hgms/transferapply")
public class TransferapplyController extends BaseController {

    @Autowired
    private TransferapplyService transferapplyService;
    @Autowired
    private InnerTransferClient innerTransferClient;

    @ModelAttribute
    public Transferapply get(@RequestParam(required = false) String orderhidden) {
        Transferapply entity = null;
        if (StringUtils.isNotBlank(orderhidden)) {
            entity = transferapplyService.get(orderhidden);
        }
        if (entity == null) {
            entity = new Transferapply();
        }
        return entity;
    }

    @RequiresPermissions("hgms:transferapply:view")
    @RequestMapping(value = {"list", ""})
    public String list(Transferapply transferapply, HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("转账审核列表条件参数:" + transferapply.toString());
        Page<Transferapply> page = transferapplyService.findPage(new Page<Transferapply>(request, response), transferapply);
        logger.info("转账审核列表page结果:" + page.toString());
        for (Transferapply transferapplyl : page.getList()) {
            if (transferapplyl != null) {
                transferapplyl.setApplyStatus(RouteStatus.labelOf(transferapplyl.getApplyStatus()));
                transferapplyl.setTransferType(HgmsTransferType.labelOf(transferapplyl.getTransferType()));
            }

        }
        model.addAttribute("page", page);
        return "modules/hgms/transferapplyList";
    }

    @RequiresPermissions("hgms:transferapply:view")
    @RequestMapping(value = "form")
    public String form(Transferapply transferapply, Model model) {
        model.addAttribute("transferapply", transferapply);
        return "modules/hgms/transferapplyForm";
    }

    @RequiresPermissions("hgms:transferapply:edit")
    @RequestMapping(value = "save")
    public String save(Transferapply transferapply, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        String messageResult = null;
        String reasonhidden = request.getParameter("reasonhidden");
        String orderhidden = request.getParameter("orderhidden");
        String examineFalg = request.getParameter("examineFalg");
        String loginName = UserUtils.getUser().getLoginName();
        Date updatetime = new Date();
        if ("FAILED".equals(examineFalg)) {
            transferapply.setRejectReason(reasonhidden);
            transferapply.setId(orderhidden);
            transferapply.setApplyStatus("AUDREJ");
            messageResult = "审核完成";
        } else {
            //调用转账接口
            InnerTransRecordModel innerTrans = new InnerTransRecordModel();
            innerTrans.setMerchantOrderNo(transferapply.getOrderId());// 转账订单号
            innerTrans.setFromMerchantId(Integer.parseInt(transferapply.getMerchantId()));//出账的商户id
            innerTrans.setFromAccountType("5");//出账的账户类型 现金户，归集户等  2代表是现金账户  5代表是归集账户
            innerTrans.setTransferAmount(transferapply.getAmount().substring(0, transferapply.getAmount().length() - 2));//金额
            innerTrans.setToMerchantId(Integer.parseInt(transferapply.getMerchantId()));// 入账商户id
            innerTrans.setToAccountType("2");//入账的账户类型 现金户，归集户等  2代表是现金账户  5代表是归集账户
            innerTrans.setOpResources("huigms");//平台来源
            innerTrans.setDirection("2");//转账方向 1：现金户到归集户 2：归集户到现金户
            innerTrans.setProductType("CP37");//产品类型 内部转账
            InnerTransResult result = innerTransferClient.innerTransfer(innerTrans);
            if (result != null && result.getCode() == 1000) {
                transferapply.setApplyStatus("SUCCES");
                logger.info("转账成功");
                messageResult = "转账成功";
                transferapply.setExtend1(result.getMsg());
            } else {
                transferapply.setApplyStatus("AUDING");
                logger.info("内部转账操作失败");
                messageResult = result.getMsg();
            }

        }
        transferapply.setAuditor(loginName);
        transferapply.setUpdateTime(updatetime);

        //修改状态(根据接口返回的数据来判断 状态为true or false )
        transferapplyService.save(transferapply);
        addMessage(redirectAttributes, messageResult);
        return "redirect:" + Global.getAdminPath() + "/hgms/transferapply/?repage";
    }

    @RequiresPermissions("hgms:transferapply:edit")
    @RequestMapping(value = "delete")
    public String delete(Transferapply transferapply, RedirectAttributes redirectAttributes) {
        transferapplyService.delete(transferapply);
        addMessage(redirectAttributes, "删除转账审核成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/transferapply/?repage";
    }

}