/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.enums.ListEnums;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsTemporaryPayRecord;
import com.heepay.manage.modules.hgms.service.HgmsTemporaryPayRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 描    述：临时代付汇总Controller
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 10:09:23
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
@RequestMapping(value = "${adminPath}/hgms/hgmsTemporaryPayRecord")
public class HgmsTemporaryPayRecordController extends BaseController {

    @Autowired
    private HgmsTemporaryPayRecordService hgmsTemporaryPayRecordService;

    @ModelAttribute
    public HgmsTemporaryPayRecord get(@RequestParam(required = false) String id) {
        HgmsTemporaryPayRecord entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsTemporaryPayRecordService.get(id);
        }
        if (entity == null) {
            entity = new HgmsTemporaryPayRecord();
        }
        return entity;
    }

    /**
     * 定时任务代付汇总临时查询
     *
     * @param hgmsTemporaryPayRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsTemporaryPayRecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsTemporaryPayRecord hgmsTemporaryPayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsTemporaryPayRecord> page = new Page<>(request, response);
        //已创建日期到排序
        page.setOrderBy("a.create_time desc");
        page = hgmsTemporaryPayRecordService.findPage(page, hgmsTemporaryPayRecord);
        //订单来源
        List<EnumBean> hgmsOrderSource = ListEnums.hgmsOrderSource();
        model.addAttribute("hgmsOrderSource", hgmsOrderSource);
        model.addAttribute("page", page);
        return "modules/hgms/hgmsTemporaryPayRecordList";
    }

}