package com.heepay.manage.modules.riskManage.web;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 徐超华 on 2017/8/17.
 */
@Controller
@RequestMapping("${adminPath}/merchant")
public class AjaxCommonController {
    @Autowired
    private MerchantCService merchantCService;

    @RequestMapping(value = "selectMerchantName")
    @ResponseBody
    public String selectMerchantName(HttpServletRequest request, String merchantId) {
        Merchant merchant = merchantCService.get(merchantId);
        String merchantName = null;
        if(merchant == null || merchant.getCompanyName() == null){
            merchantName = JsonMapperUtil.buildNonDefaultMapper().toJson("NOFOUND");
            //merchantName = "NOFOUND";
        }else{
            merchantName = JsonMapperUtil.buildNonDefaultMapper().toJson(merchant.getCompanyName());
            //merchantName = merchant.getCompanyName();
        }
        return merchantName;
    }
}
