package com.heepay.manage.modules.settle.web;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecordHis;
import com.heepay.manage.modules.settle.service.ClearingChannelRecordHisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * *
 * 描 述： 通道清算历史记录Controller
 *
 * 创 建 者： wangjie
 * 创建时间： 2017年3月10日下午3:54:20
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Controller
@RequestMapping(value = "${adminPath}/settle/clearingChannelRecordHis")
public class ClearingChannelRecordHisController extends BaseController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ClearingChannelRecordHisService clearingChannelRecordHisService;

    // 显示列表
    @RequiresPermissions("settle:clearingChannelRecordHis:view")
    @RequestMapping(value = {"list", ""})
    public String list(ClearingChannelRecordHis clearingChannelRecordHis, HttpServletRequest request,
                       HttpServletResponse response, Model model) throws Exception {

        try {
            model = clearingChannelRecordHisService.findClearingChannelRecordHisPage(
                    new Page<ClearingChannelRecordHis>(request, response), clearingChannelRecordHis, model);
            return "modules/settle/clearingChannelRecordHisList";
        } catch (Exception e) {
            logger.error("ClearingChannelRecordHisController list has a error:{查询通道清算历史记录List出错 FIND_ERROR }", e);
            throw new Exception(e);
        }
    }

    //导出
    @RequiresPermissions("settle:clearingChannelRecordHis:view")
    @RequestMapping(value = "export")
    public void export(RedirectAttributes redirectAttributes, ClearingChannelRecordHis clearingChannelRecordHis, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            clearingChannelRecordHisService.exportClearingChannelRecordHisExcel(clearingChannelRecordHis, request, response);
        } catch (Exception e) {
            logger.error("ClearingChannelRecordHisController export has a error:{通道清算历史记录信息导出出错 FIND_ERROR }", e);
            throw new Exception(e);
        }

    }

}