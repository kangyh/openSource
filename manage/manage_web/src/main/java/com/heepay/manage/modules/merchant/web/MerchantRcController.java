/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.enums.AllowSystemType;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 
 * 描 述：商户风控审核controller
 *
 * 创 建 者： ly
 * 创建时间： 2016年9月2日
 * 下午4:48:24 创建描述：
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
@RequestMapping(value = "${adminPath}/merchant/merchantRc")
public class MerchantRcController extends BaseController {

    @Autowired
    private MerchantCService merchantCService;

    /**
     * @discription 根据id获取商户风控审核信息
     * @author ly
     * @created 2016年9月2日 下午4:48:34
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
     * @discription 获取商户风控审核列表
     * @author ly
     * @created 2016年9月2日 下午4:49:26
     * @param merchant
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantRc:view")
    @RequestMapping(value = { "list", "" })
    public String list(Merchant merchant, HttpServletRequest request, HttpServletResponse response, Model model) {

        //使用cookie保存查询条件
        merchant = (Merchant) SaveConditions.result(merchant, "merchantRcController", request, response);

        merchant.setSource("in('"+AllowSystemType.NGP_WEB.getValue()+"','"+
                AllowSystemType.TPDS.getValue()+"','"+
                AllowSystemType.AGENT_WEB.getValue()+"')");
        merchant.setLegalAuditStatus(RouteStatus.AUDIT_SUCCESS.getValue());
        Page<Merchant> page = new Page<>(request, response);
        page.setOrderBy("a.create_time desc");
        page = merchantCService.findPage(page, merchant);
        page.setList(EnumView.merchant(page.getList()));

        model.addAttribute("page", page);
        model.addAttribute("merchant", merchant);
        return "modules/merchant/merchantRcList";
    }

    /**
     * @discription 获取商户风控审核新增修改页面
     * @author ly
     * @created 2016年9月2日 下午4:49:39
     * @param merchant
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantRc:view")
    @RequestMapping(value = "form")
    public String form(Merchant merchant, Model model) {
        EnumView.changeMerchant(merchant);
        model.addAttribute("merchant", merchant);
        model.addAttribute("merchantProvince", merchant.getProvinceCode());
        model.addAttribute("merchantCity", merchant.getCityCode());
        model.addAttribute("merchantCounty", merchant.getCountyCode());
        return "modules/merchant/merchantRcForm";
    }

    /**
     * @discription 获取商户风控审核页面
     * @author ly
     * @created 2016年9月2日 下午4:50:14
     * @param merchant
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantRc:view")
    @RequestMapping(value = "audit")
    public String audit(Merchant merchant, Model model) {
        EnumView.changeMerchant(merchant);
        model.addAttribute("merchant", merchant);
        return "modules/merchant/merchantRcAuditForm";
    }

    /**
     * @discription 获取商户风控审核详情
     * @author ly
     * @created 2016年9月2日 下午4:58:13
     * @param merchant
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:merchantRc:view")
    @RequestMapping(value = "detail")
    public String detail(Merchant merchant, Model model) {
        EnumView.changeMerchant(merchant);
        if (StringUtils.isNotBlank(merchant.getRcAuditStatus())) {
            merchant.setRcAuditStatus(RouteStatus.labelOf(merchant.getRcAuditStatus()));
        }
        if (StringUtils.isNotBlank(merchant.getRcAuditor())) {
            merchant.setRcAuditor(UserUtils.get(merchant.getRcAuditor()).getName());
        }
        model.addAttribute("merchant", merchant);
        return "modules/merchant/merchantRcDetail";
    }

    /**
     * @discription 保存修改商户风控审核
     * @author ly
     * @created 2016年9月2日 下午4:58:31
     * @param merchant
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:merchantRc:edit")
    @RequestMapping(value = "save")
    public String save(Merchant merchant, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, merchant)) {
            return form(merchant, model);
        }
        merchant.setId(merchant.getUserId().toString());
        merchantCService.save(merchant, false);
        addMessage(redirectAttributes, "保存商户成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantRc?cache=1&repage";
    }

    /**
     * @discription 修改商户风控审核状态
     * @author ly
     * @created 2016年9月2日 下午4:59:34
     * @param merchant
     * @return
     */
    @RequiresPermissions("merchant:merchantRc:edit")
    @RequestMapping(value = "status")
    public String status(Merchant merchant) {
        return merchantCService.rcAuditStatus(merchant);
    }
}