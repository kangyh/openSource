/**
 *
 */
package com.heepay.manage.modules.merchant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.heepay.enums.AllowSystemType;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;
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
import com.heepay.enums.CertificateType;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.sys.utils.UserUtils;

import java.util.List;

/**
 * 描    述：商户法务审核controller
 * <p>
 * 创 建 者： ly
 * 创建时间： 2016年9月2日 下午4:41:49
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */

@Controller
@RequestMapping(value = "${adminPath}/merchant/merchantLegal")
public class MerchantLegalController extends BaseController {

    @Autowired
    private MerchantCService merchantCService;


    /**
     * @param id
     * @return
     * @discription 根据id获取商户法务审核
     * @author ly
     * @created 2016年9月2日 下午4:42:06
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
     * @param merchant
     * @param request
     * @param response
     * @param model
     * @return
     * @discription 获取商户法务审核列表
     * @author ly
     * @created 2016年9月2日 下午4:42:50
     */
    @RequiresPermissions("merchant:merchantLegal:view")
    @RequestMapping(value = {"list", ""})
    public String list(Merchant merchant, HttpServletRequest request, HttpServletResponse response, Model model) {

        //使用cookie保存查询条件
        merchant = (Merchant) SaveConditions.result(merchant, "merchantLegalController", request, response);

        merchant.setSource("in('"+AllowSystemType.NGP_WEB.getValue()+"','"+
                AllowSystemType.TPDS.getValue()+"','"+
                AllowSystemType.AGENT_WEB.getValue()+"')");
        if (StringUtils.isBlank(merchant.getLegalAuditStatus())) {
            merchant.setLegalAuditStatus("NotNull");
        }
        Page<Merchant> page = new Page<>(request, response);
        page.setOrderBy("a.create_time desc");
        page = merchantCService.findPage(page, merchant);
        page.setList(EnumView.merchant(page.getList()));

        model.addAttribute("page", page);
        model.addAttribute("merchant", merchant);
        return "modules/merchant/merchantLegalList";
    }


    /**
     * @param merchant
     * @param model
     * @return
     * @discription 获取商户法务审核新增修改页面
     * @author ly
     * @created 2016年9月2日 下午4:43:08
     */
    @RequiresPermissions("merchant:merchantLegal:view")
    @RequestMapping(value = "form")
    public String form(Merchant merchant, Model model) {
        EnumView.changeMerchant(merchant);
        model.addAttribute("merchant", merchant);
        if (CertificateType.MULTIPLE.getContent().equals(merchant.getCertificateType())) {
            return "modules/merchant/merchantLegalFormMore";//多证合一
        } else if (CertificateType.INDIVIDUAL.getContent().equals(merchant.getCertificateType())) {
            return "modules/merchant/merchantLegalFormIndividual";//个体
        } else {
            return "modules/merchant/merchantLegalFormCommon";//普通
        }
    }


    /**
     * @param merchant
     * @param model
     * @return
     * @discription 获取商户法务审核详情
     * @author ly
     * @created 2016年9月2日 下午4:43:33
     */
    @RequiresPermissions("merchant:merchantLegal:view")
    @RequestMapping(value = "detail")
    public String detail(Merchant merchant, Model model) {
        EnumView.changeMerchant(merchant);
        if (StringUtils.isNotBlank(merchant.getLegalAuditStatus())) {
            merchant.setLegalAuditStatus(RouteStatus.labelOf(merchant.getLegalAuditStatus()));
        }
        if (StringUtils.isNotBlank(merchant.getLegalAuditor())) {
            merchant.setLegalAuditor(UserUtils.get(merchant.getLegalAuditor()).getName());
        }
        model.addAttribute("merchant", merchant);
        return "modules/merchant/merchantLegalDetail";
    }


    /**
     * @param merchant
     * @param model
     * @param redirectAttributes
     * @return
     * @discription 保存修改商户法务审核
     * @author ly
     * @created 2016年9月2日 下午4:43:52
     */
    @RequiresPermissions("merchant:merchantLegal:edit")
    @RequestMapping(value = "save")
    public String save(Merchant merchant, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, merchant)) {
            return form(merchant, model);
        }
        merchantCService.save(merchant, false);
        addMessage(redirectAttributes, "保存商户成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantLegal?cache=1&repage";
    }


    /**
     * @param merchant
     * @return
     * @discription 修改商户法务审核状态
     * @author ly
     * @created 2016年9月2日 下午4:44:17
     */
    @RequiresPermissions("merchant:merchantLegal:edit")
    @RequestMapping(value = "status")
    public String status(Merchant merchant) {
        merchantCService.legalAuditStatus(merchant);
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantLegal?cache=1&repage";
    }
}