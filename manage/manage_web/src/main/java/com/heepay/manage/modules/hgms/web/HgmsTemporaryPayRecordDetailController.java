/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.enums.TransWay;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsTemporaryPayRecordDetail;
import com.heepay.manage.modules.hgms.service.HgmsTemporaryPayRecordDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * 描    述：临时代付明细Controller
 *
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 10:11:59
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
@RequestMapping(value = "${adminPath}/hgms/hgmsTemporaryPayRecordDetail")
public class HgmsTemporaryPayRecordDetailController extends BaseController {

    @Autowired
    private HgmsTemporaryPayRecordDetailService hgmsTemporaryPayRecordDetailService;

    @ModelAttribute
    public HgmsTemporaryPayRecordDetail get(@RequestParam(required = false) String id) {
        HgmsTemporaryPayRecordDetail entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsTemporaryPayRecordDetailService.get(id);
        }
        if (entity == null) {
            entity = new HgmsTemporaryPayRecordDetail();
        }
        return entity;
    }

    /**
     * 定时任务代付明细临时查询
     * @param hgmsTemporaryPayRecordDetail
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsTemporaryPayRecordDetail:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsTemporaryPayRecordDetail hgmsTemporaryPayRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsTemporaryPayRecordDetail> page = new Page<>(request, response);
        //已创建日期到排序
        page.setOrderBy("a.create_time desc");
        page = hgmsTemporaryPayRecordDetailService.findPage(page, hgmsTemporaryPayRecordDetail);
        for (HgmsTemporaryPayRecordDetail detail : page.getList()) {
            detail.setTransWay(TransWay.labelOf(detail.getTransWay()));
        }
        model.addAttribute("page", page);
        return "modules/hgms/hgmsTemporaryPayRecordDetailList";
    }

}