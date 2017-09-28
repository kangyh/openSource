package com.heepay.manage.modules.merchant.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.CommonStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.utils.RateAudit;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantProductRateCService;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * 描 述：商户产品费率Controller
 *
 * 创 建 者： @author ly
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
@RequestMapping(value = "${adminPath}/merchant/merchantProductRate")
public class MerchantProductRateController extends BaseController {

    @Autowired
    private MerchantProductRateCService merchantProductRateCService;

    @ModelAttribute
    public MerchantProductRate get(@RequestParam(required = false) String id) {
        MerchantProductRate entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = merchantProductRateCService.get(id);
        }
        if (entity == null) {
            entity = new MerchantProductRate();
        }
        return entity;
    }

    @RequiresPermissions("merchant:merchantRateNew:view")
    @RequestMapping(value = { "list", "" })
    public String list(MerchantProductRate merchantProductRate,
                       HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        if(!StringUtils.isNotBlank(merchantProductRate.getStatus())){//如果状态不存在
            merchantProductRate.setStatus(CommonStatus.ENABLE.getValue());
        }
        if(!StringUtils.isNotBlank(merchantProductRate.getRateAudit())){//如果审核状态不存在
            merchantProductRate.setRateAuditsValue(RateAudit.RATE_AUDIT_S.getValue());
        }
        //使用redis保存查询条件
        merchantProductRate = (MerchantProductRate) SaveConditions.result(merchantProductRate, "merchantProductRates", request, response);

        Page<MerchantProductRate> page = new Page<MerchantProductRate>(request, response);
        page.setOrderBy("merchant_id asc");
        page = merchantProductRateCService.findPage(page, merchantProductRate);
        page.setList(EnumView.merchantProductRate(page.getList()));

        List<EnumBean> rateAudit = Lists.newArrayList();
        Stream<RateAudit> stream = Stream.of(RateAudit.values());
        stream.forEach(p ->{
            if(!p.getValue().equals(RateAudit.RATE_AUDIT_S.getValue())){
                EnumBean ct = new EnumBean();
                ct.setValue(p.getValue());
                ct.setName(p.getContent());
                rateAudit.add(ct);
            }
        });
        model.addAttribute("rateAudit", rateAudit);
        model.addAttribute("page", page);
        model.addAttribute("merchantProductRate", merchantProductRate);
        return "modules/merchant/merchantRateNewList";
    }

    @RequiresPermissions("merchant:merchantRateNew:view")
    @RequestMapping(value = "form")
    public String form(MerchantProductRate merchantProductRate, Model model) {
        EnumView.changeMerchantProductRate(merchantProductRate);
        String productX = RandomUtil.getSettleX();
        model.addAttribute("productX",productX);
        model.addAttribute("merchantProductRate", merchantProductRate);
        return "modules/merchant/merchantRateNewForm";
    }

    @RequiresPermissions("merchant:merchantRateNew:view")
    @RequestMapping(value = "detail")
    public String detail(MerchantProductRate merchantProductRate, Model model) {
        EnumView.changeMerchantProductRateDetail(merchantProductRate);
        model.addAttribute("merchantProductRate", merchantProductRate);
        return "modules/merchant/merchantRateNewDetail";
    }

    @RequiresPermissions("merchant:merchantRateNew:edit")
    @RequestMapping(value = "save")
    public String save(MerchantProductRate merchantProductRate, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, merchantProductRate)) {
            return form(merchantProductRate, model);
        }
        merchantProductRateCService.save(merchantProductRate, false);
        // 修改商家缓存(产品费率)
        addMessage(redirectAttributes, "保存商户产品费率成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantProductRate?cache=1&repage";
    }

    @RequiresPermissions("merchant:merchantRateNew:edit")
    @RequestMapping(value = "delete")
    public String delete(MerchantProductRate merchantProductRate, RedirectAttributes redirectAttributes) {
        merchantProductRateCService.delete(merchantProductRate);
        addMessage(redirectAttributes, "删除商户产品费率成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantProductRate?cache=1&repage";
    }

    @RequiresPermissions("merchant:merchantRateNew:edit")
    @RequestMapping(value = "status")
    public String status(MerchantProductRate merchantProductRate, RedirectAttributes redirectAttributes) {
        merchantProductRateCService.status(merchantProductRate);
        addMessage(redirectAttributes, "成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantProductRate?cache=1&repage";
    }

}