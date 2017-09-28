/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.ProductType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.risk.entity.RiskChartRecord;
import com.heepay.manage.modules.risk.entity.RiskMerchantOrderConversionRatio;
import com.heepay.manage.modules.risk.service.RiskMerchantOrderConversionRatioService;
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
@RequestMapping(value = "${adminPath}/risk/riskMerchantOrderConversionRatio")
public class RiskMerchantOrderConversionRatioController extends BaseController {

    @Autowired
    private RiskMerchantOrderConversionRatioService riskMerchantOrderConversionRatioService;

    @ModelAttribute
    public RiskMerchantOrderConversionRatio get(@RequestParam(required = false) String id) {
        RiskMerchantOrderConversionRatio entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = riskMerchantOrderConversionRatioService.get(id);
        }
        if (entity == null) {
            entity = new RiskMerchantOrderConversionRatio();
        }
        return entity;
    }

    @RequiresPermissions("risk:riskMerchantOrderConversionRatio:view")
    @RequestMapping(value = {"list", ""})
    public String list(RiskMerchantOrderConversionRatio riskMerchantOrderConversionRatio, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<RiskMerchantOrderConversionRatio> page = riskMerchantOrderConversionRatioService.findPage(new Page<RiskMerchantOrderConversionRatio>(request, response), riskMerchantOrderConversionRatio);
        model.addAttribute("page", page);
        //获取产品名称
        List<ProductType> productTypeList = EnumUtils.getEnumList(ProductType.class);
        model.addAttribute("productTypeList", productTypeList);

        return "modules/riskManage/riskMerchantStatistic";
    }

    @RequiresPermissions("risk:riskMerchantOrderConversionRatio:view")
    @ResponseBody
    @RequestMapping(value = "getStatisticsInfo")
    public List<RiskChartRecord> getStatisticsInfo(RiskMerchantOrderConversionRatio statisticsRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {

            String begin = request.getParameter("beginStatisticsTime");
            String end = request.getParameter("endStatisticsTime");
            String merchantId = request.getParameter("merchantId");
            String productCode = request.getParameter("productCode");
            String hostName = request.getParameter("hostName");

            if (StringUtils.isNotBlank(begin)) {
                statisticsRecord.setBeginStatisticTime(begin);
            }
            if (StringUtils.isNotBlank(end)) {
                statisticsRecord.setEndStatisticTime(end);
            }
            if (StringUtils.isNotBlank(merchantId)) {
                statisticsRecord.setMerchantId(merchantId);
            }
            if (StringUtils.isNotBlank(productCode)) {
                statisticsRecord.setProductCode(productCode);
            }
            if (StringUtils.isNotBlank(hostName)) {
                statisticsRecord.setHost(hostName);
            }

            String checkDate = request.getParameter("checkDate");
            statisticsRecord.setPage(null);
            List<RiskMerchantOrderConversionRatio> statisticsRecords = new ArrayList<RiskMerchantOrderConversionRatio>();

            if ("week".equals(checkDate)) {
                statisticsRecords = riskMerchantOrderConversionRatioService.findListByDay(statisticsRecord);
            } else if ("day".equals(checkDate)) {
                statisticsRecords = riskMerchantOrderConversionRatioService.findInTimeList(statisticsRecord);
            } else if ("theMonth".equals(checkDate)) {
                statisticsRecords = riskMerchantOrderConversionRatioService.findListByMonth(statisticsRecord);
            }

            List<RiskChartRecord> riskRecordList = new ArrayList<RiskChartRecord>();
            for (RiskMerchantOrderConversionRatio risk : statisticsRecords) {
                RiskChartRecord riskRecord = new RiskChartRecord();
                riskRecord.setDay(risk.getMerchantId());
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