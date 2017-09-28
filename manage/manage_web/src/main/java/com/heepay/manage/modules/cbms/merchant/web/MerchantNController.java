/**
 *
 */
package com.heepay.manage.modules.cbms.merchant.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.AllowSystemType;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.cbms.entity.CbmsSuppliersetting;
import com.heepay.manage.modules.cbms.service.CbmsSuppliersettingService;
import com.heepay.manage.modules.merchant.service.*;
import com.heepay.manage.modules.merchant.vo.*;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描 述：商户信息controller
 * <p>
 * 创 建 者：guozx
 * 创建时间：2016年12月30日 下午4:48:24
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cbms/merchant/merchant")
public class MerchantNController extends BaseController {

    @Autowired
    private MerchantCService merchantCService;

    @Autowired
    private MerchantUserCService merchantUserCService;
    @Autowired
    private CbmsSuppliersettingService cbmsSuppliersettingService;
    /**
     * @param id
     * @return
     * @discription 根据id获取商户信息
     * @author gzx
     * @created 2016年9月2日 下午4:20:03
     */
    @ModelAttribute
    public CbmsSuppliersetting get(@RequestParam(required = false) String id) {
        CbmsSuppliersetting entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cbmsSuppliersettingService.get(id);
        }
        if (entity == null) {
            entity = new CbmsSuppliersetting();
        }
        return entity;
    }

    /**
     * @param cbmsSuppliersetting
     * @param request
     * @param response
     * @param model
     * @return
     * @discription 获取商户信息列表
     * @author gzx
     * @created 2016年9月2日 下午4:22:11
     */
    @RequiresPermissions("merchant:merchant:view")
    @RequestMapping(value = {"list", ""})
    public String list(CbmsSuppliersetting cbmsSuppliersetting, HttpServletRequest request, HttpServletResponse response, Model model) {
        //只显示商户审核通过的
        Page<CbmsSuppliersetting> page = new Page<>(request, response);
        //以创建日期反向排序
        page.setOrderBy("m.create_time desc");
        List<CbmsSuppliersetting> list=cbmsSuppliersettingService.findList(new CbmsSuppliersetting());
        Page<CbmsSuppliersetting> pages= cbmsSuppliersettingService.findCbmsSuppliersettingList(page,cbmsSuppliersetting);
        //循环将商户的登录名、商户状态、商户的当前负责人添加到商户中去
        for (CbmsSuppliersetting merchantnew : pages.getList()) {
            if (null != merchantnew) {
                for(CbmsSuppliersetting cbmssupplicetting :list){
                    if(merchantnew.getMerchantNo().equals(cbmssupplicetting.getMerchantNo())){
                        merchantnew.setAutomaticAudit(cbmssupplicetting.getAutomaticAudit());
                        continue;
                    }
                }
                //赋值商户的负责人
                if (StringUtils.isNotBlank(merchantnew.getInchargerId())) {
                    merchantnew.setInchargerId(UserUtils.get(merchantnew.getInchargerId()).getName());
                }
                //赋值商户的状态
                if (StringUtils.isNotBlank(merchantnew.getStatus())) {
                    merchantnew.setStatus(MerchantStatus.labelOf(merchantnew.getStatus()));
                }
            }
        }
        logger.info("商户的列表信息展示状态----->成功----->对应的商户信息条数：" + page.getCount());
        model.addAttribute("page", pages);
        return "modules/cbms/merchant/cbmsMerchantSuppliersettingList";
    }

    /**
     * @param merchant
     * @param redirectAttributes
     * @return
     * @discription 修改商户状态
     * @author gzx
     * @created 2016年9月2日 下午4:23:45
     */
    @RequiresPermissions("merchant:merchant:edit")
    @RequestMapping(value = "status")
    public String status(CbmsSuppliersetting merchant, RedirectAttributes redirectAttributes) {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setId(merchant.getMerchantNo());
        merchantUser.setStatus(merchant.getStatus());
        merchantUserCService.status(merchantUser);
        addMessage(redirectAttributes, "修改商户状态成功");
        logger.info("修改商户状态----->成功----->对应的商户信息：" + merchant.toString());
        return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchant/?repage";
    }
    /**
     * @param merchant
     * @param redirectAttributes
     * @return
     * @discription 修改商户自动审核通过开关
     * @author gzx
     * @created 2016年9月2日 下午4:23:45
     */
    @RequiresPermissions("merchant:merchant:edit")
    @RequestMapping(value = "updateAutomaticAudit")
    public String updateAutomaticAudit(CbmsSuppliersetting merchant, RedirectAttributes redirectAttributes) {
        CbmsSuppliersetting merchantUser = new CbmsSuppliersetting();
        merchantUser.setMerchantNo(merchant.getMerchantNo());
        merchantUser.setAutomaticAudit(merchant.getAutomaticAudit());
        cbmsSuppliersettingService.updatecbmsSuppliersetting(merchantUser);
        addMessage(redirectAttributes, "修改商户自动审核开关状态成功");
        logger.info("修改商户自动审核开关----->成功----->对应的商户信息：" + merchant.toString());
        return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchant/?repage";
    }
    @RequiresPermissions("merchant:merchant:edit")
    @RequestMapping(value = "getMerchantPosCode")
    @ResponseBody
    public String getMerchantPosCode(String userId, String mcc) {
        return merchantCService.getMerchantPosCode(userId, mcc);
    }

}