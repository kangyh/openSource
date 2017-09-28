/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.HgmsContractStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsServiceItem;
import com.heepay.manage.modules.hgms.entity.HgmsValidContract;
import com.heepay.manage.modules.hgms.service.HgmsServiceItemService;
import com.heepay.manage.modules.hgms.service.HgmsValidContractService;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isEmpty;


/**
 * 描    述：有效合约Controller
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-06-03 17:08:56
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
@RequestMapping(value = "${adminPath}/hgms/hgmsValidContract")
public class HgmsValidContractController extends BaseController {

    @Autowired
    private HgmsValidContractService hgmsValidContractService;
    @Autowired
    private HgmsServiceItemService hgmsServiceItemService;

    @ModelAttribute
    public HgmsValidContract get(@RequestParam(required = false) String id) {
        HgmsValidContract entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsValidContractService.get(id);
        }
        if (entity == null) {
            entity = new HgmsValidContract();
        }
        return entity;
    }

    /**
     * 有效合约列表
     *
     * @param hgmsValidContract
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsValidContract:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsValidContract hgmsValidContract, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsValidContract> page = new Page<>(request, response);
        //已创建时间反向排序
        page.setOrderBy("a.entering_time desc");
        page = hgmsValidContractService.findPage(page, hgmsValidContract);
        for (HgmsValidContract hgmsValidContractnew : page.getList()) {
            //合同启用停用状态赋值
            if (!isEmpty(hgmsValidContractnew.getContractStatus()))
                hgmsValidContractnew.setContractStatus(HgmsContractStatus.labelOf(hgmsValidContractnew.getContractStatus()));
            //合同审核状态状态赋值
            if (!isEmpty(hgmsValidContractnew.getStatus()))
                hgmsValidContractnew.setStatus(RouteStatus.labelOf(hgmsValidContractnew.getStatus()));
        }
        model.addAttribute("page", page);
        return "modules/hgms/hgmsValidContractList";
    }

    /**
     * 有效合约产看页面
     *
     * @param hgmsValidContract
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsValidContract:view")
    @RequestMapping(value = "form")
    public String form(HgmsValidContract hgmsValidContract, Model model) {
        //合约文件添加域
        if (hgmsValidContract.getContractFileAddress() != null) {
            hgmsValidContract.setContractFileAddress(RandomUtil.getFastDfs(hgmsValidContract.getContractFileAddress()));
        }
        //合同启用停用状态赋值
        if (!isEmpty(hgmsValidContract.getContractStatus()))
            hgmsValidContract.setContractStatus(HgmsContractStatus.labelOf(hgmsValidContract.getContractStatus()));
        //审核状态赋值
        if (!isEmpty(hgmsValidContract.getStatus()))
            hgmsValidContract.setStatus(RouteStatus.labelOf(hgmsValidContract.getStatus()));
        //法务审核状态
        if (!isEmpty(hgmsValidContract.getLegalAuditStatus()))
            hgmsValidContract.setLegalAuditStatus(RouteStatus.labelOf(hgmsValidContract.getLegalAuditStatus()));
        //风控审核状态
        if (!isEmpty(hgmsValidContract.getRcAuditStatus()))
            hgmsValidContract.setRcAuditStatus(RouteStatus.labelOf(hgmsValidContract.getRcAuditStatus()));
        //法务审核人赋值
        User userLegal = UserUtils.get(hgmsValidContract.getLegalAuditor());
        //风控审核人赋值
        User userRc = UserUtils.get(hgmsValidContract.getLegalAuditor());
        //录入人赋值
        User userInput = UserUtils.get(hgmsValidContract.getLegalAuditor());
        if (!ObjectUtils.isEmpty(userLegal) && !ObjectUtils.isEmpty(userRc) && !ObjectUtils.isEmpty(userInput)) {
            hgmsValidContract.setLegalAuditor(userLegal.getName());
            hgmsValidContract.setRcAuditor(userRc.getName());
            hgmsValidContract.setEnteringId(userInput.getName());
        }
        model.addAttribute("hgmsValidContract", hgmsValidContract);
        return "modules/hgms/hgmsValidContractForm";
    }

    /**
     * 有效合约的合约状态切换
     *
     * @param hgmsValidContract
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("hgms:hgmsValidContract:edit")
    @RequestMapping(value = "status")
    public String status(HgmsValidContract hgmsValidContract, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, hgmsValidContract)) {
            return form(hgmsValidContract, model);
        }
        hgmsValidContractService.status(hgmsValidContract);
        addMessage(redirectAttributes, "合约编号：" + hgmsValidContract.getContractId() + "合约状态设置成：" + hgmsValidContract.getContractStatus());
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsValidContract/?repage";
    }

    /**
     * 异步根据businessId获取服务项
     *
     * @param hgmsServiceItem
     * @return
     */
    @RequestMapping(value = "getService")
    @ResponseBody
    public String getService(HgmsServiceItem hgmsServiceItem) {
        List<HgmsServiceItem> list = hgmsServiceItemService.findList(hgmsServiceItem);
        return new JsonMapperUtil().toJson(list);
    }

}