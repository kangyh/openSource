/**
 *  
 */
package com.heepay.manage.modules.businesscenter.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.sys.entity.Office;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.BusinessInfoUtils;
import com.heepay.manage.modules.util.FastDFSUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.RouteStatus;
import com.heepay.enums.SettlementTo;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.AmountChangeUtil;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantAutographInfoCService;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.service.MerchantIndustryBaseCService;
import com.heepay.manage.modules.merchant.service.MerchantRateNewCService;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.service.SettleCycleManageCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantAutographInfo;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.merchant.vo.SettleCycleManage;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 *
 * 描 述：商户信息controller
 *
 * 创 建 者：ly 创建时间：2016-08-23 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者：ljh 审核时间：2016-09-01 审核描述：90行查询后做非空处理，优化代码逻辑；缺少每段代码和方法注释；注释头部认真描述该类作用
 * 删除无用注释代码
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/business/merchant")
public class BusinessMerchantController extends BaseController {

    @Autowired
    private MerchantCService merchantCService;

    @Autowired
    private MerchantUserCService merchantUserCService;

    @Autowired
    private SettleCycleManageCService settleCycleManageService;

    @Autowired
    private MerchantAutographInfoCService merchantAutographInfoService;

    @Autowired
    private MerchantRateNewCService merchantRateNewCService;

    @Autowired
    private MerchantIndustryBaseCService merchantIndustryBaseCService;

    /**
     * @discription 根据id获取商户信息
     * @author ly
     * @created 2016年9月2日 下午4:20:03
     * @param id
     * @return
     */
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

    /**
     * @discription 获取商户信息列表
     * @author ly
     * @created 2016年9月2日 下午4:22:11
     * @param merchant
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("business:merchant:view")
    @RequestMapping(value = "getMerchantList")
    public String getMerchantList(Merchant merchant, HttpServletRequest request, HttpServletResponse response, Model model) {

        //使用cookie保存查询条件
        merchant = (Merchant) SaveConditions.result(merchant, "merchantsController", request, response);

        String inchargerId = RandomUtil.getInchargeId();
        if(StringUtils.isNotBlank(inchargerId)){
            merchant.setInchargerId(inchargerId);
        }
        merchant.setStatus(RouteStatus.AUDIT_SUCCESS.getValue());
        Page<Merchant> page = new Page<>(request, response);
        page.setOrderBy("a.create_time desc");
        String source = merchant.getSource();
        if(StringUtils.isNotBlank(source)){
            merchant.setSource("= '"+source+"'");
        }

//        List<Long> merchantIdList = RandomUtil.getMerchantIdList();
//        merchant.setMerchantIds(merchantIdList);
        page = merchantCService.findPage(page, merchant);
        List<Merchant> list = Lists.newArrayList();
        for (Merchant merchantFor : page.getList()) {
            MerchantUser merchantUser = merchantUserCService.get(merchantFor.getUserId().toString());
            if (null != merchantUser) {
                merchantFor.setLoginName(merchantUser.getLoginName());
                if (StringUtils.isNotBlank(merchantUser.getStatus())) {
                    merchantFor.setUserStatus(MerchantStatus.labelOf(merchantUser.getStatus()));
                }
            }
            list.add(merchantFor);
        }
        page.setList(list);
        merchant.setSource(source);
        model.addAttribute("page", page);
        model.addAttribute("merchant", merchant);
        return "modules/businesscenter/merchantList";
    }

    /**
     * @discription 获取商户详情页面
     * @author ly
     * @created 2016年9月2日 下午4:22:51
     * @param merchant
     * @param model
     * @return
     */
    @RequiresPermissions("business:merchant:view")
    @RequestMapping(value = "detail")
    public String detail(Merchant merchant, Model model) {
        if (null != merchant.getRetainedAmount()) {
            merchant.setRetainedAmount(AmountChangeUtil.change(merchant.getRetainedAmount()));
        }
        // 结算周期
        SettleCycleManage settleCycleManage = new SettleCycleManage();
        settleCycleManage.setMerchantId(merchant.getUserId().toString());
        if (settleCycleManageService.findList(settleCycleManage).size() > 0) {
            settleCycleManage = settleCycleManageService.findList(settleCycleManage).get(0);
            if (StringUtils.isNotBlank(settleCycleManage.getSettlementTo())) {
                settleCycleManage.setSettlementTo(SettlementTo.labelOf(settleCycleManage.getSettlementTo()));
            }
            if (null != settleCycleManage.getMinSettlementAmount()) {
                settleCycleManage
                        .setMinSettlementAmount(AmountChangeUtil.change(settleCycleManage.getMinSettlementAmount()));
            }
        }
        // 将维系人员id转换为名字
        if (StringUtils.isNotBlank(merchant.getInchargerId())) {
            merchant.setInchargerId(UserUtils.get(merchant.getInchargerId()).getName());
        }
        // 技术签约
        MerchantAutographInfo merchantAutographInfo = new MerchantAutographInfo();
        merchantAutographInfo.setMerchantId(merchant.getUserId().toString());
        if (merchantAutographInfoService.findList(merchantAutographInfo).size() > 0) {
            merchantAutographInfo = merchantAutographInfoService.findList(merchantAutographInfo).get(0);
        }
        // 会员平台分类回显
        MerchantIndustryBase merchantIndustryBase = merchantIndustryBaseCService.getMcc(merchant.getIndustryCategory());
        // 费率
        MerchantRateNew merchantRateNewFind = new MerchantRateNew();
        merchantRateNewFind.setMerchantId(merchant.getUserId().toString());
        Page<MerchantRateNew> page = merchantRateNewCService.findPageMerchant(new Page<MerchantRateNew>(),
                merchantRateNewFind);
        if (page.getList().size() > 0) {
            page.setList(EnumView.merchantRateNew(page.getList()));
        }
        MerchantUser merchantUser = merchantUserCService.get(merchant.getUserId().toString());
        if (null != merchantUser) {
            merchant.setLoginName(merchantUser.getLoginName());
        }
        model.addAttribute("page", page);
        model.addAttribute("merchant", merchant);
        model.addAttribute("settleCycleManage", settleCycleManage);
        model.addAttribute("merchantAutographInfo", merchantAutographInfo);
        model.addAttribute("merchantIndustryBase", merchantIndustryBase);
        return "modules/businesscenter/merchantDetail";
    }



    @RequiresPermissions("business:merchant:view")
    @RequestMapping(value = "getMerchantPosCode")
    @ResponseBody
    public String getMerchantPosCode(String userId, String mcc) {
        return merchantCService.getMerchantPosCode(userId, mcc);
    }



}