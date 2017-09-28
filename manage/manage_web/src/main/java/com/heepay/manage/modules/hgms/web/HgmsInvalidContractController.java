/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.enums.HgmsContractStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsInvalidContract;
import com.heepay.manage.modules.hgms.service.HgmsInvalidContractService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang.StringUtils.isEmpty;


/**
 * 描    述：无效合约Controller
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-06-03 17:08:50
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
@RequestMapping(value = "${adminPath}/hgms/hgmsInvalidContract")
public class HgmsInvalidContractController extends BaseController {

    @Autowired
    private HgmsInvalidContractService hgmsInvalidContractService;

    @ModelAttribute
    public HgmsInvalidContract get(@RequestParam(required = false) String id) {
        HgmsInvalidContract entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsInvalidContractService.get(id);
        }
        if (entity == null) {
            entity = new HgmsInvalidContract();
        }
        return entity;
    }

    /**
     * 无效合约列表
     *
     * @param hgmsInvalidContract
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsInvalidContract:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsInvalidContract hgmsInvalidContract, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsInvalidContract> page = new Page<>(request, response);
        //已创建时间反向排序
        page.setOrderBy("a.entering_time desc");
        page = hgmsInvalidContractService.findPage(page, hgmsInvalidContract);
        for (HgmsInvalidContract hgmsInvalidContracttnew : page.getList()) {
            //合同启用停用状态赋值
            if (!isEmpty(hgmsInvalidContracttnew.getContractStatus()))
                hgmsInvalidContracttnew.setContractStatus(HgmsContractStatus.labelOf(hgmsInvalidContracttnew.getContractStatus()));
            //合同审核状态状态赋值
            if (!isEmpty(hgmsInvalidContracttnew.getStatus()))
                hgmsInvalidContracttnew.setStatus(RouteStatus.labelOf(hgmsInvalidContracttnew.getStatus()));
        }
        model.addAttribute("page", page);
        model.addAttribute("invalidContract", hgmsInvalidContract.getMerchantId());
        return "modules/hgms/hgmsInvalidContractList";
    }

    /**
     * 无效合约详情
     *
     * @param model
     * @param id
     * @return
     */
    @RequiresPermissions("hgms:hgmsInvalidContract:view")
    @RequestMapping(value = "form")
    public String form(Model model, @RequestParam(required = true) String id) {
        HgmsInvalidContract hgmsInvalidContract = hgmsInvalidContractService.get(id);
        //合约文件添加域
        if (hgmsInvalidContract.getContractFileAddress() != null) {
            hgmsInvalidContract.setContractFileAddress(RandomUtil.getFastDfs(hgmsInvalidContract.getContractFileAddress()));
        }
        //合同启用停用状态赋值
        if (!isEmpty(hgmsInvalidContract.getContractStatus()))
            hgmsInvalidContract.setContractStatus(HgmsContractStatus.labelOf(hgmsInvalidContract.getContractStatus()));
        //审核状态赋值
        if (!isEmpty(hgmsInvalidContract.getStatus()))
            hgmsInvalidContract.setStatus(RouteStatus.labelOf(hgmsInvalidContract.getStatus()));
        //法务审核状态
        if (!isEmpty(hgmsInvalidContract.getLegalAuditStatus()))
            hgmsInvalidContract.setLegalAuditStatus(RouteStatus.labelOf(hgmsInvalidContract.getLegalAuditStatus()));
        //风控审核状态
        if (!isEmpty(hgmsInvalidContract.getRcAuditStatus()))
            hgmsInvalidContract.setRcAuditStatus(RouteStatus.labelOf(hgmsInvalidContract.getRcAuditStatus()));
        //法务审核人赋值
        User userLegal = UserUtils.get(hgmsInvalidContract.getLegalAuditor());
        //风控审核人赋值
        User userRc = UserUtils.get(hgmsInvalidContract.getLegalAuditor());
        //录入人赋值
        User userInput = UserUtils.get(hgmsInvalidContract.getLegalAuditor());
        if (!ObjectUtils.isEmpty(userLegal) && !ObjectUtils.isEmpty(userRc) && !ObjectUtils.isEmpty(userInput)) {
            hgmsInvalidContract.setLegalAuditor(userLegal.getName());
            hgmsInvalidContract.setRcAuditor(userRc.getName());
            hgmsInvalidContract.setEnteringId(userInput.getName());
        }
        model.addAttribute("hgmsInvalidContract", hgmsInvalidContract);
        model.addAttribute("invalidContract", hgmsInvalidContract.getMerchantId());
        return "modules/hgms/hgmsInvalidContractForm";
    }


}