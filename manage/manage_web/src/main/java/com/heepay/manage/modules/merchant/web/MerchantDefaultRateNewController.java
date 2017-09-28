/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.RateBankcardType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantRateNewCService;
import com.heepay.manage.modules.merchant.vo.MerchantRateDefault;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;

/**
 *
 * 描 述：默认费率controller
 *
 * 创 建 者：ly 创建时间：2016-08-23 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者：ljh 审核时间：2016-09-01 审核描述：92/100/108行相同代码可合并
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/merchantDefaultRateNew")
public class MerchantDefaultRateNewController extends BaseController {

    @Autowired
    private MerchantRateNewCService merchantRateNewCService;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * @discription 根据id获取默认费率
     * @author ly
     * @created 2016年9月2日 下午4:24:46
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
     * @discription 获取默认费率列表
     * @author ly
     * @created 2016年9月2日 下午4:25:12
     * @param merchantRateNew
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantDefaultRateNew:view")
    @RequestMapping(value = { "list", "" })
    public String list(MerchantRateNew merchantRateNew, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        //使用cookie保存查询条件
        merchantRateNew = (MerchantRateNew) SaveConditions.result(merchantRateNew, "merchantRateNewsController", request, response);

        merchantRateNew.setMerchantId(com.heepay.manage.common.utils.Constants.MERCHANT_DEFAULT_ID);
        Page<MerchantRateNew> page = new Page<>(request, response);
        page.setOrderBy("c.create_time desc");
        page = merchantRateNewCService.findPageDefaul(page, merchantRateNew);
        page.setList(EnumView.merchantRateNew(page.getList()));

        model.addAttribute("page", page);
        model.addAttribute("merchantRateNew", merchantRateNew);
        return "modules/merchant/merchantDefaultRateNewList";
    }

    /**
     * @discription 获取默认费率新增修改列表
     * @author ly
     * @created 2016年9月2日 下午4:25:38
     * @param merchantRateNew
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantDefaultRateNew:view")
    @RequestMapping(value = "form")
    public String form(MerchantRateNew merchantRateNew, Model model) {
        if (StringUtils.isBlank(merchantRateNew.getId())) {
            model.addAttribute("merchantRateNew", merchantRateNew);
            return "modules/merchant/merchantDefaultRateNewAdd";
        } else {
            EnumView.changeMerchantRateNewDefault(merchantRateNew);
            model.addAttribute("merchantRateNew", merchantRateNew);
            return "modules/merchant/merchantDefaultRateNewEdit";
        }
    }

    /**
     * @discription 获取默认费率详情
     * @author ly
     * @created 2016年9月2日 下午4:46:37
     * @param merchantRateNew
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantDefaultRateNew:view")
    @RequestMapping(value = "detail")
    public String detail(MerchantRateNew merchantRateNew, HttpServletRequest request, HttpServletResponse response,
            Model model) {
        MerchantRateNew merchantRateFind = new MerchantRateNew();
        merchantRateFind.setMerchantId(merchantRateNew.getMerchantId());
        merchantRateFind.setProductCode(merchantRateNew.getProductCode());
        merchantRateFind.setChargeType(merchantRateNew.getChargeType());
        Page<MerchantRateNew> page = merchantRateNewCService.findPage(new Page<MerchantRateNew>(request, response),
                merchantRateFind);
        if (page.getList().size() > 0) {
            page.setList(EnumView.merchantRateNew(page.getList()));
        }
        model.addAttribute("page", page);
        return "modules/merchant/merchantDefaultRateNewDetail";
    }

    /**
     * @discription 保存修改默认费率
     * @author ly
     * @created 2016年9月2日 下午4:25:54
     * @param merchantRateNew
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:merchantDefaultRateNew:edit")
    @RequestMapping(value = "save")
    public String save(MerchantRateNew merchantRateNew, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, merchantRateNew)) {
            return form(merchantRateNew, model);
        }
        if (StringUtils.isNotBlank(merchantRateNew.getProductName())) {
            merchantRateNew.setProductName(HtmlUtils.htmlUnescape(merchantRateNew.getProductName()));
        }
        MerchantRateNew rateExsit = merchantRateNewCService.existDefault(merchantRateNew);
        if (null != rateExsit) {
            if (StringUtils.isNotBlank(merchantRateNew.getBankCardType())) {
                model.addAttribute("msg", "默认的" + merchantRateNew.getProductName()
                        + RateBankcardType.labelOf(merchantRateNew.getBankCardType()) + "的费率已存在");
                return form(merchantRateNew, model);
            }
            model.addAttribute("msg", "默认的" + merchantRateNew.getProductName() + "的费率已存在");
            return form(merchantRateNew, model);
        }
        String msg = merchantRateNewCService.saveRate(merchantRateNew, false);
        if(!Boolean.TRUE.toString().equals(msg)){
            model.addAttribute("msg",msg);
            return form(merchantRateNew, model);
        }
        addMessage(redirectAttributes, "保存默认费率成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantDefaultRateNew?cache=1&repage";
    }

    /**
     * @discription 修改默认费率状态
     * @author ly
     * @created 2016年9月2日 下午4:26:21
     * @param merchantRateNew
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:merchantDefaultRateNew:edit")
    @RequestMapping(value = "status")
    public String status(MerchantRateNew merchantRateNew, RedirectAttributes redirectAttributes) {
        merchantRateNewCService.status(merchantRateNew);
        addMessage(redirectAttributes, "删除商家费率成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantDefaultRateNew?cache=1&repage";
    }

    /**
     * @discription 获取默认费率
     * @author ly
     * @created 2016年9月13日 下午7:52:12
     * @param merchantRateNew
     * @return
     */
    @RequestMapping(value = "getDefault")
    @ResponseBody
    public String getDefault(MerchantRateNew merchantRateNew) {
        merchantRateNew.setMerchantId(com.heepay.manage.common.utils.Constants.MERCHANT_DEFAULT_ID);
        MerchantRateDefault merchantRateReturn = merchantRateNewCService.getDefault(merchantRateNew);
        String json = "";
        try {
            if (null != merchantRateReturn) {
                json = mapper.writeValueAsString(merchantRateReturn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return json;
    }
}