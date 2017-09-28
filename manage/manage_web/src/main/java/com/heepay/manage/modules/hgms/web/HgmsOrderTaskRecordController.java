/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.hgms.rpc.hgmsweb.module.CreateOrderRequest;
import com.heepay.hgms.rpc.hgmsweb.module.CreateOrderResult;
import com.heepay.hgms.rpc.hgmsweb.module.ExecuteOrderRequest;
import com.heepay.hgms.rpc.hgmsweb.module.ExecuteOrderResult;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.HgmsOrderTaskType;
import com.heepay.manage.common.enums.HgmsTaskStatus;
import com.heepay.manage.common.enums.ListEnums;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsOrderTaskRecord;
import com.heepay.manage.modules.hgms.rpc.client.CreateOrderClient;
import com.heepay.manage.modules.hgms.rpc.client.ExecuteOrderClient;
import com.heepay.manage.modules.hgms.service.HgmsOrderTaskRecordService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 描    述：汇聚财定时任务Controller
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-27 16:57:55
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
@RequestMapping(value = "${adminPath}/hgms/hgmsOrderTaskRecord")
public class HgmsOrderTaskRecordController extends BaseController {

    @Autowired
    private HgmsOrderTaskRecordService hgmsOrderTaskRecordService;
    @Autowired
    private CreateOrderClient createOrderClient;
    @Autowired
    private ExecuteOrderClient executeOrderClient;

    @ModelAttribute
    public HgmsOrderTaskRecord get(@RequestParam(required = false) String id) {
        HgmsOrderTaskRecord entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsOrderTaskRecordService.get(id);
        }
        if (entity == null) {
            entity = new HgmsOrderTaskRecord();
        }
        return entity;
    }

    /**
     * 进入规则任务列表页面
     * @param hgmsOrderTaskRecord
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsOrderTaskRecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsOrderTaskRecord hgmsOrderTaskRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsOrderTaskRecord> page = new Page<>(request, response);
        //已创建日期到排序
        page.setOrderBy("a.create_time desc");
        page = hgmsOrderTaskRecordService.findPage(page, hgmsOrderTaskRecord);
        //获取任务类型
        for (HgmsOrderTaskRecord hgmsOrderTaskRecordTemp : page.getList()) {
            hgmsOrderTaskRecordTemp.setTaskType(HgmsOrderTaskType.labelOf(hgmsOrderTaskRecordTemp.getTaskType()));
            hgmsOrderTaskRecordTemp.setStatus(HgmsTaskStatus.labelOf(hgmsOrderTaskRecordTemp.getStatus()));
        }
        //获取任务类型
        List<EnumBean> hgmsOrderTaskType = ListEnums.hgmsOrderTaskType();
        model.addAttribute("page", page);
        model.addAttribute("hgmsOrderTaskType", hgmsOrderTaskType);
        return "modules/hgms/hgmsOrderTaskRecordList";
    }

    /**
     * 手动生成订单
     *
     * @param hgmsOrderTaskRecord
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("hgms:hgmsOrderTaskRecord:edit")
    @RequestMapping(value = "createOrder")
    public String createOrder(HgmsOrderTaskRecord hgmsOrderTaskRecord, RedirectAttributes redirectAttributes) {
        logger.info("执行手动生成订单任务开始");
        HgmsOrderTaskRecord taskRecord = hgmsOrderTaskRecordService.get(hgmsOrderTaskRecord.getId());
        //判断昨天的执行订单是否成功
        boolean status = hgmsOrderTaskRecordService.checkYesterDayExTaskStatus(hgmsOrderTaskRecord.getCheckBeginDate(), hgmsOrderTaskRecord.getCheckEndDate());
        if (!status) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar checkTime = Calendar.getInstance();
            checkTime.setTime(hgmsOrderTaskRecord.getCheckBeginDate());
            checkTime.add(Calendar.DAY_OF_MONTH, -1);
            String date = simpleDateFormat.format(checkTime.getTime());
            addMessage(redirectAttributes, date + "执行订单任务失败或者没有执行，请先执行！");
            logger.info(date + "执行订单任务失败或者没有执行，请先执行！");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsOrderTaskRecord/?repage";
        }
        //判断是否是今天的订单
        if (new Date().compareTo(taskRecord.getCheckBeginDate()) == 1) {
            String message = "执行同步数据任务成功";
            CreateOrderRequest createOrderRequest = new CreateOrderRequest();
            String loginName = UserUtils.getUser().getLoginName();//操作人
            createOrderRequest.setTaskId(hgmsOrderTaskRecord.getId());
            createOrderRequest.setOperator(loginName);
            CreateOrderResult createOrderResult = createOrderClient.doCreateOrder(createOrderRequest);
            if (ObjectUtils.isEmpty(createOrderResult)) {
                message = "汇聚财服务不可用，稍后再试！";
            } else if (createOrderResult.getRetCode() != 1000) {
                message = createOrderResult.getRetMsg();
            }
            addMessage(redirectAttributes, message);
            logger.info("执行手动生成订单任务结束");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsOrderTaskRecord/?repage";
        }
        addMessage(redirectAttributes, "无法执行今天以后的任务！");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsOrderTaskRecord/?repage";
    }

    /**
     * 手动执行订单
     *
     * @param hgmsOrderTaskRecord
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("hgms:hgmsOrderTaskRecord:edit")
    @RequestMapping(value = "executeOrder")
    public String executeOrder(HgmsOrderTaskRecord hgmsOrderTaskRecord, RedirectAttributes redirectAttributes) {

        logger.info("执行手动执行订单任务开始");
        //判断今天的执行订单是否成功
        boolean status = hgmsOrderTaskRecordService.checkYesterDayCrTaskStatus(hgmsOrderTaskRecord.getCheckBeginDate(), hgmsOrderTaskRecord.getCheckEndDate());
        if (!status) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(hgmsOrderTaskRecord.getCheckBeginDate());
            addMessage(redirectAttributes, date + "生成订单任务失败或者没有执行，请先执行！");
            logger.info(date + "生成订单任务失败或者没有执行，请先执行！");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsOrderTaskRecord/?repage";
        }
        String message = "执行同步数据任务成功";
        HgmsOrderTaskRecord taskRecord = hgmsOrderTaskRecordService.get(hgmsOrderTaskRecord.getId());
        if (new Date().compareTo(taskRecord.getCheckBeginDate()) == 1) {
            ExecuteOrderRequest executeOrderRequest = new ExecuteOrderRequest();
            String loginName = UserUtils.getUser().getLoginName();//操作人
            executeOrderRequest.setTaskId(hgmsOrderTaskRecord.getId());
            executeOrderRequest.setOperator(loginName);
            ExecuteOrderResult executeOrderResult = executeOrderClient.doExecuteOrder(executeOrderRequest);
            if (ObjectUtils.isEmpty(executeOrderResult)) {
                message = "汇聚财服务不可用，稍后再试！";
            } else if (executeOrderResult.getRetCode() != 1000) {
                message = executeOrderResult.getRetMsg();
            }
            addMessage(redirectAttributes, message);
            logger.info("执行手动执行订单任务结束");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsOrderTaskRecord/?repage";
        }
        addMessage(redirectAttributes, "无法执行今天以后的任务！");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsOrderTaskRecord/?repage";
    }

}