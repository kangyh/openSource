/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.BankcardType;
import com.heepay.enums.CommonStatus;
import com.heepay.enums.CostType;
import com.heepay.enums.RateBankcardType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantProductRateCService;
import com.heepay.manage.modules.merchant.service.MerchantRateBankCService;
import com.heepay.manage.modules.merchant.service.MerchantRateNewCService;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.merchant.vo.MerchantRateBank;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;
import com.heepay.manage.modules.merchant.vo.MerchantRateNewVo;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.service.BankInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描 述：商家费率Controller
 *
 * 创 建 者： ly 创建时间： 2016年9月2日 下午4:44:35 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/merchant/merchantRateNew")
public class MerchantRateNewController extends BaseController {

    @Autowired
    private MerchantRateNewCService merchantRateNewCService;
    
    @Autowired
    private MerchantProductRateCService merchantProductRateCService;
    
    @Autowired
    private MerchantRateBankCService merchantRateBankCService;

    @Autowired
    private BankInfoService bankInfoService;

    /**
     * @discription 根据id获取商家费率
     * @author ly
     * @created 2016年9月2日 下午4:44:45
     * @param id
     * @return
     */
    @ModelAttribute
    public MerchantRateNew get(@RequestParam(required = false) String id) {
        MerchantRateNew entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = merchantRateNewCService.get(id);
        }
        if (entity == null) {
            entity = new MerchantRateNew();
        }
        return entity;
    }

    /**
     * @discription 获取商家费率列表
     * @author ly
     * @created 2016年9月2日 下午4:45:47
     * @param merchantRateNew
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantRateNew:view")
    @RequestMapping(value = { "list", "" })
    public String list(MerchantRateNew merchantRateNew, HttpServletRequest request, HttpServletResponse response,
            Model model) {
        MerchantRateNew merchantRateNewSearch = new MerchantRateNew();
        merchantRateNewSearch.setBankNo(merchantRateNew.getBankNo());
        merchantRateNewSearch.setBankCardType(merchantRateNew.getBankCardType());
        Page<MerchantRateNew> page = merchantRateNewCService.findPage(new Page<MerchantRateNew>(request, response),
                merchantRateNew);
        page.setList(EnumView.merchantRateNew(page.getList()));
        model.addAttribute("rateId", merchantRateNew.getRateId());
        model.addAttribute("page", page);
        model.addAttribute("merchantRateNewSearch", merchantRateNewSearch);
        return "modules/merchant/merchantRateBankList";
    }

    /**
     * @discription 获取商家费率新增修改页面
     * @author ly
     * @created 2016年9月2日 下午4:46:06
     * @param merchantRateNewVo
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantRateNew:view")
    @RequestMapping(value = "form")
    public String form(MerchantRateNewVo merchantRateNewVo, Model model) {
        String productX = RandomUtil.getSettleX();
        model.addAttribute("productX",productX);
        model.addAttribute("merchantRateNewVo", merchantRateNewVo);
        return "modules/merchant/merchantRateNewAdd";
    }

    /**
     * @discription 获取商家费率详情修改页面
     * @author ly
     * @created 2016年9月2日 下午4:46:06
     * @param merchantRateNew
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantRateNew:view")
    @RequestMapping(value = "edit")
    public String edit(MerchantRateNew merchantRateNew, Model model) {
        merchantRateNew = merchantRateNewCService.edit(merchantRateNew);
        EnumView.changeMerchantRateNew(merchantRateNew);
        model.addAttribute("merchantRateNew", merchantRateNew);
        return "modules/merchant/merchantRateBankForm";
    }

    /**
     * @discription 获取商家费率详情
     * @author ly
     * @created 2016年9月2日 下午4:46:37
     * @param merchantRateNew
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantRateNew:view")
    @RequestMapping(value = "detail")
    public String detail(MerchantRateNew merchantRateNew, HttpServletRequest request, HttpServletResponse response,
            Model model) {
        MerchantProductRate merchantProductRate = merchantProductRateCService.get(merchantRateNew.getId());
        MerchantRateBank merchantRateBank = new MerchantRateBank();
        merchantRateBank.setRateId(merchantProductRate.getId());
        List<MerchantRateBank> merchantRateBanks = merchantRateBankCService.findList(merchantRateBank);
        model.addAttribute("merchantRateBanks", merchantRateBanks);
        model.addAttribute("merchantProductRate", merchantProductRate);
        return "modules/merchant/merchantProductRateDetail";
    }

    /**
     * @discription 保存修改商家费率
     * @author ly
     * @created 2016年9月2日 下午4:46:49
     * @param merchantRateNewVo\
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:merchantRateNew:edit")
    @RequestMapping(value = "save")
    public String save(MerchantRateNewVo merchantRateNewVo, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, merchantRateNewVo)) {
            return form(merchantRateNewVo, model);
        }
        if (StringUtils.isNotBlank(merchantRateNewVo.getProductName())) {
            merchantRateNewVo.setProductName(HtmlUtils.htmlUnescape(merchantRateNewVo.getProductName()));
        }
        List<MerchantRateNew> rateExsits = merchantRateNewCService.existNoBank(merchantRateNewVo);
        if (null != rateExsits && !rateExsits.isEmpty()) {
            String msg = "该商户的" + merchantRateNewVo.getProductName() + "的";
            for(MerchantRateNew merchantRateNew : rateExsits){
                if(StringUtils.isNotBlank(merchantRateNew.getBankCardType())){
                    msg = msg + RateBankcardType.labelOf(merchantRateNew.getBankCardType());
                }
            }
            msg = msg + "费率已存在";
            model.addAttribute("msg",msg);
            return form(merchantRateNewVo, model);
        }
        String msg = merchantRateNewCService.saveVo(merchantRateNewVo, false);
        if(!Boolean.TRUE.toString().equals(msg)){
            model.addAttribute("msg",msg);
            return form(merchantRateNewVo, model);
        }
        addMessage(redirectAttributes, "保存商家费率成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchant?cache=1&repage";
    }
    
    /**
     * @discription 保存修改商家费率
     * @author ly
     * @created 2016年9月2日 下午4:46:49
     * @param merchantRateNew
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:merchantRateNew:edit")
    @RequestMapping(value = "editSave")
    public String editSave(MerchantRateNew merchantRateNew, Model model, RedirectAttributes redirectAttributes,
        HttpServletRequest request, HttpServletResponse response) {
        if (!beanValidator(model, merchantRateNew)) {
            return edit(merchantRateNew, model);
        }
        if (StringUtils.isNotBlank(merchantRateNew.getProductName())) {
            merchantRateNew.setProductName(HtmlUtils.htmlUnescape(merchantRateNew.getProductName()));
        }
        String msg = merchantRateNewCService.saveRate(merchantRateNew, false);
        if(!Boolean.TRUE.toString().equals(msg)){
            model.addAttribute("msg",msg);
            return edit(merchantRateNew, model);
        }
        addMessage(redirectAttributes, "保存商家费率成功");
        MerchantRateNew merchantRateNewReturn = new MerchantRateNew();
        merchantRateNewReturn.setRateId(merchantRateNew.getRateId());
        return list(merchantRateNewReturn,request,response,model);
    }

    /**
     * @discription 修改商家费率状态
     * @author ly
     * @created 2016年9月2日 下午4:47:04
     * @param merchantRateNew
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:merchantRateNew:edit")
    @RequestMapping(value = "status")
    public String status(MerchantRateNew merchantRateNew, RedirectAttributes redirectAttributes) {
        merchantRateNewCService.status(merchantRateNew);
        addMessage(redirectAttributes, "删除商家费率成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantRateNew/?repage";
    }

    @RequestMapping(value = "cost")
    @ResponseBody
    public String cost(String productCode,String merchantId,String savingType,
                       String savingFee,String creditType,String creditFee) {
        MerchantRateNew merchantRateNew = new MerchantRateNew();
        merchantRateNew.setProductCode(productCode);
        merchantRateNew.setMerchantId(merchantId);
        merchantRateNew.setBankCardType(BankcardType.SAVINGCARD.getValue());
        merchantRateNew.setChargeType(savingType);
        merchantRateNew.setChargeFee(StringUtils.isNotBlank(savingFee) ? new BigDecimal(savingFee):null);
        merchantRateNew.setChargeRatio(StringUtils.isNotBlank(savingFee) ? new BigDecimal(savingFee):null);
        Map<String,String> map = merchantRateNewCService.isOverCost(merchantRateNew);
        if(StringUtils.isNotBlank(creditType)){
            if(Boolean.FALSE.toString().equals(map.get("flag"))){
                merchantRateNew.setProductCode(productCode);
                merchantRateNew.setMerchantId(merchantId);
                merchantRateNew.setBankCardType(BankcardType.CREDITCARD.getValue());
                merchantRateNew.setChargeType(creditType);
                merchantRateNew.setChargeFee(StringUtils.isNotBlank(creditFee) ? new BigDecimal(creditFee):null);
                merchantRateNew.setChargeRatio(StringUtils.isNotBlank(creditFee) ? new BigDecimal(creditFee):null);
                map = merchantRateNewCService.isOverCost(merchantRateNew);
                if(Boolean.TRUE.toString().equals(map.get("flag"))){
                    map.put("flag","trueCredit");
                }
            }
        }
        return JsonMapperUtil.nonDefaultMapper().toJson(map);
    }

    /**
     * 费率批量修改
     */
    @RequiresPermissions("merchant:merchantRateNew:edit")
    @RequestMapping(value = "editRateSome")
    @ResponseBody
    public String editRateSome(String checkedstr,String somerateType,MerchantRateNew merchantRateNew){
        merchantRateBankCService.batchSaveRate(checkedstr,somerateType,merchantRateNew);
        return JsonMapperUtil.nonDefaultMapper().toJson(merchantRateNew.getRateId());
    }

}