package com.heepay.manage.modules.riskManage.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.ProductType;
import com.heepay.manage.common.persistence.Page;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 徐超华 on 2017/8/18.
 */
@Controller
@RequestMapping("${adminPath}/risk/riskMerchantRatio")
public class RiskMerchantRatioController {
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
        Page page1 = new Page<RiskMerchantOrderConversionRatio>(request, response);
        page1.setOrderBy("create_time DESC ");
        Page<RiskMerchantOrderConversionRatio> page = riskMerchantOrderConversionRatioService.findPage(page1, riskMerchantOrderConversionRatio);
        model.addAttribute("page", page);
        //获取产品名称
        List<ProductType> productTypeList = EnumUtils.getEnumList(ProductType.class);
        model.addAttribute("productTypeList", productTypeList);

        return "modules/riskManage/riskMerchantOrderConversionRatioList";
    }
}
