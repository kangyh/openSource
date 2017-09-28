/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.checkSecretKey.web;

import com.heepay.codec.Md5;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.CommonStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.service.MerchantProductRateCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：查看内部商户秘钥
 * <p>
 * 创建者  B.HJ
 * 创建时间 2017-07-03-10:32
 * 创建描述：查看内部商户秘钥
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/checkSecretKey")
public class CheckSecretKeyController extends BaseController {

    @Autowired
    private MerchantCService merchantCService;
    @Autowired
    private MerchantProductRateCService merchantProductRateCService;

    @ModelAttribute
    public Merchant get(@RequestParam(required = false) String id) {
        Merchant entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = merchantCService.get(id);
        }
        if (entity == null) {
            entity = new Merchant();
        }
        return entity;
    }

    @RequiresPermissions("merchant:checkSecretKey:view")
    @RequestMapping(value = {"list", ""})
    public String list(Merchant merchant, HttpServletRequest request, HttpServletResponse response, Model model) {

        List<Dict> apiCustomer = DictUtils.getDictList("APICustomer");
        List<String> ids = new ArrayList<>();
        for (Dict dict : apiCustomer) {
            ids.add(dict.getValue());
        }
        Page<Merchant> page = new Page<>(request, response);
        page = merchantCService.findPage(page, merchant);
        List<Merchant> merchants = merchantCService.queryInternalMerchantsList(ids);
        page.setList(merchants);

        model.addAttribute("page", page);
        model.addAttribute("merchant", merchant);
        return "modules/checkSecretKey/merchantList";
    }

    @RequiresPermissions("merchant:checkSecretKey:view")
    @RequestMapping(value = "detail")
    public String detail(MerchantProductRate merchantProductRate, Model model, HttpServletRequest request, HttpServletResponse response) {
        merchantProductRate.setStatus(CommonStatus.ENABLE.getValue());
        merchantProductRate.setRateAudit("Y");
        Page<MerchantProductRate> page = new Page<>(request, response);
        page.setOrderBy("merchant_id asc");
        page = merchantProductRateCService.findPage(page, merchantProductRate);
        List<MerchantProductRate> merchantProductRates = EnumView.merchantProductRate(page.getList());
        page.setList(checkAndJoinChargeWithdrawRefund(merchantProductRates,merchantProductRate));
        model.addAttribute("page", page);
        return "modules/checkSecretKey/merchantSecretKey";
    }

    /**
     * 检查 充值   提现   退款  是否配置了，配置了读取，加入key信息，没配置给默认，加入list
     *
     * @param merchantProductRates list
     * @return list
     */
    private List<MerchantProductRate> checkAndJoinChargeWithdrawRefund(List<MerchantProductRate> merchantProductRates,MerchantProductRate merchantProductRate) {
        //看是否配置了   充值   提现   退款
        boolean chargeFlag = false;
        boolean withdrawFlag = false;
        boolean refundFlag = false;
        // 配置了产品
        if (!merchantProductRates.isEmpty()) {
            for (MerchantProductRate merchantProduct : merchantProductRates) {
                if ("充值".equals(merchantProduct.getProductName())) {
                    chargeFlag = true;
                    merchantProduct.setAutographKey(Md5.encode(Constants.RedisKey.CHARGE_PRODUCT_KEY + merchantProduct.getMerchantId()));
                } else if ("提现".equals(merchantProduct.getProductName())) {
                    withdrawFlag = true;
                    merchantProduct.setAutographKey(Md5.encode(Constants.RedisKey.WITHDRAW_PRODUCT_KEY + merchantProduct.getMerchantId()));
                } else if ("退款".equals(merchantProduct.getProductName())) {
                    refundFlag = true;
                    merchantProduct.setAutographKey(Md5.encode(Constants.RedisKey.REFUND_PRODUCT_KEY + merchantProduct.getMerchantId()));
                }
            }
            MerchantProductRate product = merchantProductRates.get(0);
            // 没配置充值
            if (!chargeFlag) {
                MerchantProductRate chargeProduct = new MerchantProductRate();
                chargeProduct.setMerchantId(product.getMerchantId());
                chargeProduct.setAutographKey(Md5.encode(Constants.RedisKey.CHARGE_PRODUCT_KEY + product.getMerchantId()));
                chargeProduct.setProductName("充值");
                chargeProduct.setMerchantCompanyName(product.getMerchantCompanyName());
                chargeProduct.setMerchantLoginName(product.getMerchantLoginName());
                chargeProduct.setStatus("启用");
                merchantProductRates.add(chargeProduct);
            }
            // 没配置提现
            if (!withdrawFlag) {
                MerchantProductRate withdrawProduct = new MerchantProductRate();
                withdrawProduct.setMerchantId(product.getMerchantId());
                withdrawProduct.setAutographKey(Md5.encode(Constants.RedisKey.WITHDRAW_PRODUCT_KEY + product.getMerchantId()));
                withdrawProduct.setProductName("提现");
                withdrawProduct.setMerchantCompanyName(product.getMerchantCompanyName());
                withdrawProduct.setMerchantLoginName(product.getMerchantLoginName());
                withdrawProduct.setStatus("启用");
                merchantProductRates.add(withdrawProduct);
            }
            // 没配置退款
            if (!refundFlag) {
                MerchantProductRate refundProduct = new MerchantProductRate();
                refundProduct.setMerchantId(product.getMerchantId());
                refundProduct.setAutographKey(Md5.encode(Constants.RedisKey.REFUND_PRODUCT_KEY + product.getMerchantId()));
                refundProduct.setProductName("退款");
                refundProduct.setMerchantCompanyName(product.getMerchantCompanyName());
                refundProduct.setMerchantLoginName(product.getMerchantLoginName());
                refundProduct.setStatus("启用");
                merchantProductRates.add(refundProduct);
            }
        }else{
            // 没有配置任何产品
            Merchant merchant = merchantCService.get(merchantProductRate.getMerchantId());
            MerchantProductRate chargeProduct = new MerchantProductRate();
            chargeProduct.setMerchantId(merchantProductRate.getMerchantId());
            chargeProduct.setAutographKey(Md5.encode(Constants.RedisKey.CHARGE_PRODUCT_KEY + merchantProductRate.getMerchantId()));
            chargeProduct.setProductName("充值");
            chargeProduct.setMerchantCompanyName(merchant.getCompanyName());
            chargeProduct.setMerchantLoginName(merchant.getEmail());
            chargeProduct.setStatus("启用");
            merchantProductRates.add(chargeProduct);

            MerchantProductRate withdrawProduct = new MerchantProductRate();
            withdrawProduct.setMerchantId(merchantProductRate.getMerchantId());
            withdrawProduct.setAutographKey(Md5.encode(Constants.RedisKey.WITHDRAW_PRODUCT_KEY + merchantProductRate.getMerchantId()));
            withdrawProduct.setProductName("提现");
            withdrawProduct.setMerchantCompanyName(merchant.getCompanyName());
            withdrawProduct.setMerchantLoginName(merchant.getEmail());
            withdrawProduct.setStatus("启用");
            merchantProductRates.add(withdrawProduct);

            MerchantProductRate refundProduct = new MerchantProductRate();
            refundProduct.setMerchantId(merchantProductRate.getMerchantId());
            refundProduct.setAutographKey(Md5.encode(Constants.RedisKey.REFUND_PRODUCT_KEY + merchantProductRate.getMerchantId()));
            refundProduct.setProductName("退款");
            refundProduct.setMerchantCompanyName(merchant.getCompanyName());
            refundProduct.setMerchantLoginName(merchant.getEmail());
            refundProduct.setStatus("启用");
            merchantProductRates.add(refundProduct);
        }
        return merchantProductRates;
    }
}