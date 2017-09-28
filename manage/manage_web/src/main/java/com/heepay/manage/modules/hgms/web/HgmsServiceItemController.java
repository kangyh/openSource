/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.HgmsRuleBuildType;
import com.heepay.manage.common.enums.HgmsStatus;
import com.heepay.manage.common.enums.ListEnums;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsBusiness;
import com.heepay.manage.modules.hgms.entity.HgmsProduct;
import com.heepay.manage.modules.hgms.entity.HgmsServiceItem;
import com.heepay.manage.modules.hgms.service.HgmsBusinessService;
import com.heepay.manage.modules.hgms.service.HgmsServiceItemService;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 描    述：业务管理Controller
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间：
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
@RequestMapping(value = "${adminPath}/hgms/hgmsServiceItem")
public class HgmsServiceItemController extends BaseController {

    @Autowired
    private HgmsServiceItemService hgmsServiceItemService;
    @Autowired
    private HgmsBusinessService hgmsBusinessService;
    @Autowired
    private ProductCService productCServiceImpl;

    @ModelAttribute
    public HgmsServiceItem get(@RequestParam(required = false) String id) {
        HgmsServiceItem entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsServiceItemService.get(id);
        }
        if (entity == null) {
            entity = new HgmsServiceItem();
        }
        return entity;
    }

    /**
     * 服务列表
     *
     * @param businessId
     * @param hgmsServiceItem
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsServiceItem:view")
    @RequestMapping(value = {"list", ""})
    public String list(@RequestParam(required = false) String businessId, HgmsServiceItem hgmsServiceItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        hgmsServiceItem.setBusinessId(businessId);
        Page<HgmsServiceItem> page = new Page<>(request, response);
        //已创建日期到排序
        page.setOrderBy("a.created_time desc");
        if (StringUtils.isNoneBlank(businessId)) {
            page = hgmsServiceItemService.findPage(page, hgmsServiceItem);
            for (HgmsServiceItem hgmsServiceItemTemp : page.getList()) {
                //同步前端商户信息
                if (StringUtils.isNotBlank(hgmsServiceItemTemp.getStatus())) {
                    hgmsServiceItemTemp.setStatus(HgmsStatus.labelOf(hgmsServiceItemTemp.getStatus()));
                }
                if (StringUtils.isNotBlank(hgmsServiceItemTemp.getRuleBuildType())) {
                    hgmsServiceItemTemp.setRuleBuildType(HgmsRuleBuildType.labelOf(hgmsServiceItemTemp.getRuleBuildType()));
                }
            }
        }
        model.addAttribute("page", page);
        model.addAttribute("businessId", businessId);
        return "modules/hgms/hgmsServiceItemList";
    }

    /**
     * 服务型添加
     *
     * @param businessId
     * @param hgmsServiceItem
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsServiceItem:view")
    @RequestMapping(value = "form")
    public String form(@RequestParam(required = false) String businessId, HgmsServiceItem hgmsServiceItem, Model model) {
        //获取业务列表
        if (StringUtils.isNoneBlank(businessId)) {
            HgmsBusiness hgmsBusiness = hgmsBusinessService.get(businessId);
            model.addAttribute("businessName", hgmsBusiness.getBusinessName());
            model.addAttribute("businessId", businessId);
        }
        List<EnumBean> hgmsRuleBuildTypes = ListEnums.hgmsRuleBuildType();
        List<Product> list = productCServiceImpl.findList(new Product());
        List<HgmsProduct> productList = hgmsServiceItemService.transProduct(list);
        model.addAttribute("productList", productList);
        model.addAttribute("hgmsRuleBuildTypes", hgmsRuleBuildTypes);
        model.addAttribute("hgmsServiceItem", hgmsServiceItem);
        return "modules/hgms/hgmsServiceItemForm";
    }

    @RequiresPermissions("hgms:hgmsServiceItem:edit")
    @RequestMapping(value = "save")
    public String save(HgmsServiceItem hgmsServiceItem, Model model, RedirectAttributes redirectAttributes) {
        String serviceName = hgmsServiceItem.getServiceName();
        if (StringUtils.isBlank(serviceName)) {
            addMessage(redirectAttributes, "请填写服务项名称");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsServiceItem/?businessId=" + hgmsServiceItem.getBusinessId();
        }
        HgmsServiceItem hgmsServiceItems = hgmsServiceItemService.selectByName(hgmsServiceItem);
        if (ObjectUtils.isEmpty(hgmsServiceItems)) {
            hgmsServiceItemService.save(hgmsServiceItem);
            addMessage(redirectAttributes, "保存业务管理成功");
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsServiceItem/?businessId=" + hgmsServiceItem.getBusinessId();
        }
        addMessage(redirectAttributes, "保存业务管理失败，该服务项名称已存在");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsServiceItem/?businessId=" + hgmsServiceItem.getBusinessId();
    }

    /**
     * 服务型状态修改
     *
     * @param hgmsServiceItem
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("hgms:hgmsServiceItem:edit")
    @RequestMapping(value = "status")
    public String status(HgmsServiceItem hgmsServiceItem, RedirectAttributes redirectAttributes) {
        boolean serviceItemStatus = hgmsServiceItemService.status(hgmsServiceItem);
        if (serviceItemStatus) {
            addMessage(redirectAttributes, "状态修改成功");
        } else {
            addMessage(redirectAttributes, "状态修改失败");
        }
        logger.info("修改业务状态: " + serviceItemStatus + "->对应的业务ID：" + hgmsServiceItem.getServiceId() + ",对应的业务名称：" + hgmsServiceItem.getServiceName());
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsServiceItem/?businessId=" + hgmsServiceItem.getBusinessId();
    }

}