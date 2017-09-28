/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.ProductType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.risk.entity.RiskChannelOrderConversionRatio;
import com.heepay.manage.modules.risk.entity.RiskChartRecord;
import com.heepay.manage.modules.risk.entity.RiskChannelOrderConversionRatio;
import com.heepay.manage.modules.risk.service.RiskChannelOrderConversionRatioService;
import com.heepay.manage.modules.risk.service.RiskChannelOrderConversionRatioService;
import org.apache.commons.lang3.EnumUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * 描    述：风控商户统计Controller
 *
 * 创 建 者： @author xch
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
@RequestMapping(value = "${adminPath}/risk/riskChannelRatioReporter")
public class RiskChannelRatioReporterController extends BaseController {

    @Autowired
    private RiskChannelOrderConversionRatioService riskChannelOrderConversionRatioService;

    @ModelAttribute
    public RiskChannelOrderConversionRatio get(@RequestParam(required = false) String id) {
        RiskChannelOrderConversionRatio entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = riskChannelOrderConversionRatioService.get(id);
        }
        if (entity == null) {
            entity = new RiskChannelOrderConversionRatio();
        }
        return entity;
    }

    @RequiresPermissions("risk:riskChannelRatioReporter:view")
    @RequestMapping(value = {"list", ""})
    public String list(RiskChannelOrderConversionRatio riskChannelOrderConversionRatio, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<RiskChannelOrderConversionRatio> page = riskChannelOrderConversionRatioService.findPage(new Page<RiskChannelOrderConversionRatio>(request, response), riskChannelOrderConversionRatio);
        model.addAttribute("page", page);
        //获取产品名称
        List<ProductType> productTypeList = EnumUtils.getEnumList(ProductType.class);
        model.addAttribute("productTypeList", productTypeList);
        //查询通道合作方的内容
        List<EnumBean> dataEntity = DictList.channelPartner();
        model.addAttribute("dataEntity", dataEntity);
        return "modules/riskManage/riskChannelStatistic";
    }

    @RequiresPermissions("risk:riskChannelRatioReporter:view")
    @ResponseBody
    @RequestMapping(value = "getStatisticsInfo")
    public List<RiskChartRecord> getStatisticsInfo(RiskChannelOrderConversionRatio statisticsRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {

            String begin = request.getParameter("beginStatisticsTime");
            String end = request.getParameter("endStatisticsTime");
            String channelPartnerCode = request.getParameter("channelPartnerCode");
            String channelTypeCode = request.getParameter("channelTypeCode");
            String hostName = request.getParameter("hostName");

            if (StringUtils.isNotBlank(begin)) {
                statisticsRecord.setBeginStatisticTime(begin);
            }
            if (StringUtils.isNotBlank(end)) {
                statisticsRecord.setEndStatisticTime(end);
            }
            if (StringUtils.isNotBlank(channelPartnerCode)) {
                statisticsRecord.setChannelPartnerCode(channelPartnerCode);
            }
            if (StringUtils.isNotBlank(channelTypeCode)) {
                statisticsRecord.setChannelTypeCode(channelTypeCode);
            }
            if (StringUtils.isNotBlank(hostName)) {
                statisticsRecord.setHost(hostName);
            }

            String checkDate = request.getParameter("checkDate");
            statisticsRecord.setPage(null);
            List<RiskChannelOrderConversionRatio> statisticsRecords = new ArrayList<RiskChannelOrderConversionRatio>();

            if ("week".equals(checkDate)) {
                statisticsRecords = riskChannelOrderConversionRatioService.findListByDay(statisticsRecord);
            } else if ("day".equals(checkDate)) {
                statisticsRecords = riskChannelOrderConversionRatioService.findInTimeList(statisticsRecord);
            } else if ("theMonth".equals(checkDate)) {
                statisticsRecords = riskChannelOrderConversionRatioService.findListByMonth(statisticsRecord);
            }

            List<RiskChartRecord> riskRecordList = new ArrayList<RiskChartRecord>();
            for (RiskChannelOrderConversionRatio risk : statisticsRecords) {
                RiskChartRecord riskRecord = new RiskChartRecord();
                riskRecord.setDay(risk.getChannelPartnerCode());
                riskRecord.setSucessRatio(risk.getSucessRatio());
                riskRecordList.add(riskRecord);
            }

            return riskRecordList;

        } catch (Exception e) {
            logger.error("获取数据出错，错误原因{}", e.toString());

            return null;
        }

    }

}