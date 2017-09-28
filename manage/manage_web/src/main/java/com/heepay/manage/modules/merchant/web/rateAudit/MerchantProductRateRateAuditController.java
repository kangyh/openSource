package com.heepay.manage.modules.merchant.web.rateAudit;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.CommonStatus;
import com.heepay.enums.RateBusinessType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.utils.RateAudit;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantRateNewCService;
import com.heepay.manage.modules.merchant.service.check.MerchantProductRateCheckService;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Stream;

/***
 *
 *
 * 描    述：费率审核管理
 *
 * 创 建 者： wangl
 * 创建时间： 2017-06-06 02:03 PM
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
@RequestMapping(value = "${adminPath}/merchant/merchantProductRate/rateAudit")
public class MerchantProductRateRateAuditController extends BaseController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private MerchantRateNewCService merchantRateNewCService;

    @Autowired
    private MerchantProductRateCheckService merchantProductRateCheckService;


    /**
     * @方法说明：页面显示
     * @时间： 2017-06-06 01:39 PM
     * @创建人：wangl
     */
    @RequiresPermissions("merchant:merchantProductRate:view")
    @RequestMapping(value = {"list", ""})
    public String list(MerchantProductRate merchantProductRate,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       Model model) {

        if(StringUtils.isBlank(merchantProductRate.getRateAudit())){//如果审核状态不存在
            merchantProductRate.setRateAudit(RateAudit.RATE_AUDIT_S.getValue());
        }
        //merchantProductRate.setRateAudit(RateAudit.RATE_AUDIT_S.getValue());
        //使用redis保存查询条件
        merchantProductRate = (MerchantProductRate) SaveConditions.result(merchantProductRate, "merchantProductsRate", request, response);

        logger.info("费率审核管理--->{页面显示}--->{}", merchantProductRate.toString());
        Page<MerchantProductRate> page = getListByPage(new Page<>(request, response), merchantProductRate);
        List<MerchantProductRate> list = page.getList();

        list.forEach(k -> {
            if (StringUtils.isNoneBlank(k.getRateAudit())) {
                k.setRateAudit(RateAudit.labelOf(k.getRateAudit()));
            }
            if (StringUtils.isNotBlank(k.getStatus())) {
                k.setStatus(CommonStatus.labelOf(k.getStatus()));
            }
            if (StringUtils.isNotBlank(k.getBusinessType())) {
                k.setBusinessType(RateBusinessType.labelOf(k.getBusinessType()));
            }
        });

        List<EnumBean> rateAudit = Lists.newArrayList();
        Stream.of(RateAudit.values()).forEach(p ->{
            if(!p.getValue().equals(RateAudit.RATE_AUDIT_Y.getValue())){
                EnumBean ct = new EnumBean();
                ct.setValue(p.getValue());
                ct.setName(p.getContent());
                rateAudit.add(ct);
            }
        });
        model.addAttribute("rateAudit", rateAudit);

        model.addAttribute("page", page);
        model.addAttribute("merchantProductRate", merchantProductRate);
        return "modules/merchant/rateAudit/merchantProductRate";
    }


    /**
     * @方法说明：费率审核跳转
     * @时间： 2017-06-06 01:38 PM
     * @创建人：wangl
     */
    @RequiresPermissions("merchant:merchantProductRate:edit")
    @RequestMapping(value = "/jump/{id}")
    public String detail(MerchantProductRate merchantProductRate,
                         Page<MerchantRateNew> pageList,
                         @RequestParam(value = "rateAuditValue",required = false) String rateAuditValue,
                         @PathVariable(value = "id") String id,
                         Model model) {

        logger.info("费率审核管理--->{费率审核跳转}--->{条件}" + id);

        merchantProductRate = merchantProductRateCheckService.get(id);
        EnumView.changeMerchantProductRateDetail(merchantProductRate);
        List<EnumBean> rateAudit = Lists.newArrayList();
        Stream<RateAudit> stream = Stream.of(RateAudit.values());
        stream.forEach(p -> {
            if (!p.getValue().equals(RateAudit.RATE_AUDIT_S.getValue())) {
                EnumBean ct = new EnumBean();
                ct.setValue(p.getValue());
                ct.setName(p.getContent());
                rateAudit.add(ct);
            }
        });
        /*for (RateAudit checkFlg : RateAudit.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            rateAudit.add(ct);
        }*/
        model.addAttribute("rateAudit", rateAudit);

        MerchantRateNew merchantRateNew = new MerchantRateNew();
        merchantRateNew.setRateId(merchantProductRate.getId());

        pageList.setPageSize(5);
        merchantRateNew.setFlag("Y");
        pageList = merchantRateNewCService.findPage(pageList, merchantRateNew);
        pageList.setList(EnumView.merchantRateNew(pageList.getList()));

        model.addAttribute("merchantProductRate", merchantProductRate);
        model.addAttribute("page", pageList);
        model.addAttribute("id", id);

        if(StringUtils.isNotBlank(rateAuditValue)){//查看操作
            model.addAttribute("rateAuditValue", "S");
        }
        return "modules/merchant/rateAudit/merchantRateNewDetail";
    }


    /**
     * @方法说明：费率审核
     * @时间： 2017-06-06 01:38 PM
     * @创建人：wangl
     */
    @RequiresPermissions("merchant:merchantProductRate:edit")
    @RequestMapping(value = "/save/{id}")
    public String save(MerchantProductRate merchantProductRate,
                       @PathVariable(value = "id") String id,
                       RedirectAttributes redirectAttributes) {

        try {
            logger.error("费率审核管理--->{费率审核}--->{审核状态}" + merchantProductRate.getRateAudit());
            merchantProductRateCheckService.beachAndInsert(merchantProductRate);
            addMessage(redirectAttributes, "审核成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "审核出错");
            logger.error("费率审核管理--->{费率审核}--->{异常}" + e);
        }
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantProductRate/rateAudit?cache=1";
    }

    /**
     * @方法说明：分页查询的方法
     * @时间： 2017-06-06 01:37 PM
     * @创建人：wangl
     */
    public Page<MerchantProductRate> getListByPage(Page<MerchantProductRate> page, MerchantProductRate entity) {
        entity.setPage(page);
        page.setList(merchantProductRateCheckService.getListByPage(entity));
        return page;
    }
}