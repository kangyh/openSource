/**
 *
 */
package com.heepay.manage.modules.cbms.accountManagement.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.AllowSystemType;
import com.heepay.enums.CertificateType;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.CbmsEnterpriseType;
import com.heepay.manage.common.enums.CbmsSupplierCategory;
import com.heepay.manage.common.enums.CbmsTradeType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.cbms.entity.CbmsSuppliersetting;
import com.heepay.manage.modules.cbms.service.CbmsCountrysettingService;
import com.heepay.manage.modules.cbms.service.CbmsSuppliersettingService;
import com.heepay.manage.modules.cbms.utils.DataprocessUtils;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描    述：商户法务审核controller
 * <p>
 * 创 建 者： guozx
 * 创建时间： 2017年1月10日09:49:46
 * 创建描述： 处理跨境法务审核业务逻辑
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
@RequestMapping(value = "${adminPath}/cbms/merchant/merchantLegal")
public class CbmsMerchantLegalController extends BaseController {

    @Autowired
    private MerchantCService merchantCService;

    @Autowired
    private MerchantUserCService merchantUserCService;

    @Autowired
    private CbmsSuppliersettingService cbmsSuppliersettingService;

    /**
     * @param id
     * @return Merchant
     * @discription 根据id获取商户法务审核
     * @author guozx
     * @created 2017年1月10日 下午4:42:06
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
     * @discription 商户法务审核列表
     * @author guozx
     * @created 2017年1月10日 下午4:42:50
     */
    @RequiresPermissions("merchant:merchantLegal:view")
    @RequestMapping(value = {"list", ""})
    public String list(Merchant merchant, HttpServletRequest request, HttpServletResponse response, Model model) {
        //法务审核只能获取跨境的商户
        merchant.setSource("in('" + AllowSystemType.CROSS_BORDER.getValue() + "')");
        //判断商户的法务审核状态是否为空
        if (StringUtils.isBlank(merchant.getLegalAuditStatus())) {
            merchant.setLegalAuditStatus("NotNull");
        }
        Page<Merchant> page = new Page<>(request, response);
        //已创建时间反向排序
        page.setOrderBy("a.create_time desc");
        logger.info("初始化商户法务审核列表，查询条件merchant：" + merchant.toString());
        page = merchantCService.findPage(page, merchant);
        page.setList(EnumView.merchant(page.getList()));
        List<Merchant> list = page.getList();
        //循环向merchant对象中添加商户企业类别名称，页面显示
        for (Merchant cbmsmerchant : list) {
            CbmsSuppliersetting cbmsSuppliersetting = cbmsSuppliersettingService.getMerchantByNo(cbmsmerchant.getUserId());
            if (cbmsSuppliersetting != null) {
                String cbmsEnterpriseTypeName = cbmsSuppliersetting.getCbmsEnterpriseTypeName();
                String content = CbmsEnterpriseType.labelOf(cbmsEnterpriseTypeName);
                cbmsmerchant.setRemark(content);
            }
        }

        logger.info("商户法务审核列表商户总条数：" + page.getCount());
        page.setList(list);
        model.addAttribute("page", page);
        return "modules/cbms/accountManagement/merchantLegalList";
    }


    /**
     * @param merchant
     * @param model
     * @return
     * @discription 商户法务审核新增修改页面
     * @author guozx
     * @created 2017年1月10日 下午4:43:08
     */
    @RequiresPermissions("merchant:merchantLegal:view")
    @RequestMapping(value = "form")
    public String form(Merchant merchant, Model model) {
        //法务审核时转换Merchant显示商户类型和图片的加域
        EnumView.changeMerchant(merchant);
        //根据merchantUser的id获取merchantUser对象
        MerchantUser merchantUser = merchantUserCService.get(merchant.getUserId().toString());
        merchant.setEmail(merchantUser.getLoginName());
        //根据CbmsSuppliersetting的merchantNo获取跨境商户对象
        CbmsSuppliersetting cbmsSuppliersetting = cbmsSuppliersettingService.get(merchant.getUserId().toString());
        // 封装到setCbmsEnterpriseTypeNamecbmsSupplierCategoryName    cbmsTradeTypeName
        cbmsSuppliersetting.setCbmsEnterpriseTypeName(CbmsEnterpriseType.labelOf(cbmsSuppliersetting.getCbmsEnterpriseTypeName()));
        cbmsSuppliersetting.setCbmsSupplierCategoryName(CbmsSupplierCategory.labelOf(cbmsSuppliersetting.getCbmsSupplierCategoryName()));
        //处理分装商户贸易类型(1,2,3 to [1,2,3])
        String[] tradeValue = DataprocessUtils.convertStrToArray(cbmsSuppliersetting.getCbmsTradeTypeName());
        //根据对应的商户贸易类型获取实际名字
        String[] tradeNameArr = new String[tradeValue.length];
        for (int i = 0; i < tradeValue.length; i++) {
            tradeNameArr[i] = CbmsTradeType.labelOf(tradeValue[i]);
        }
        //将商户商户贸易类型重新封装测字符串封装到CbmsSuppliersetting类中
        cbmsSuppliersetting.setCbmsTradeTypeName(DataprocessUtils.convertArrToStr(tradeNameArr));
        logger.info("获取跨境商户的信息----->" + cbmsSuppliersetting.toString());
        model.addAttribute("cbmsSuppliersetting", cbmsSuppliersetting);
        model.addAttribute("merchant", merchant);
        //判断商户证件类型（"普通证件","多证合一", "个体商户"）分别跳转
        if (CertificateType.MULTIPLE.getContent().equals(merchant.getCertificateType())) {
            //多证合一
            logger.info("商户类型是：----->" + CertificateType.MULTIPLE.getContent());
            return "modules/cbms/accountManagement/merchantLegalFormMore";
        } else if (CertificateType.INDIVIDUAL.getContent().equals(merchant.getCertificateType())) {
            logger.info("商户类型是：----->" + CertificateType.INDIVIDUAL.getContent());
            return "modules/cbms/accountManagement/merchantLegalFormIndividual";//个体
        } else {
            logger.info("商户类型是：----->" + CertificateType.ORDINARY.getContent());
            return "modules/cbms/accountManagement/merchantLegalFormCommon";//普通
        }
    }


    /**
     * @param merchant
     * @param model
     * @return
     * @discription 获取商户法务审核详情
     * @author guozx
     * @created 2017年1月10日 下午4:43:33
     */
    @RequiresPermissions("merchant:merchantLegal:view")
    @RequestMapping(value = "detail")
    public String detail(Merchant merchant, Model model) {
        //法务审核时转换Merchant显示商户类型和图片的加域
        EnumView.changeMerchant(merchant);
        //分别获取商户的风控和法务状态,并且转换成对应的中文状态
        if (StringUtils.isNotBlank(merchant.getLegalAuditStatus())) {
            merchant.setLegalAuditStatus(RouteStatus.labelOf(merchant.getLegalAuditStatus()));
        }
        if (StringUtils.isNotBlank(merchant.getLegalAuditor())) {
            merchant.setLegalAuditor(UserUtils.get(merchant.getLegalAuditor()).getName());
        }
        //根据merchantUser的id获取merchantUser对象
        MerchantUser merchantUser = merchantUserCService.get(merchant.getUserId().toString());
        logger.info("获取商户法务审核详情：----->成功");
        //根据CbmsSuppliersetting的merchantNo获取跨境商户对象
        CbmsSuppliersetting cbmsSuppliersetting = cbmsSuppliersettingService.get(merchant.getUserId().toString());
        // 封装到setCbmsEnterpriseTypeNamecbmsSupplierCategoryName    cbmsTradeTypeName
        cbmsSuppliersetting.setCbmsEnterpriseTypeName(CbmsEnterpriseType.labelOf(cbmsSuppliersetting.getCbmsEnterpriseTypeName()));
        cbmsSuppliersetting.setCbmsSupplierCategoryName(CbmsSupplierCategory.labelOf(cbmsSuppliersetting.getCbmsSupplierCategoryName()));
        //处理分装商户贸易类型(1,2,3 to [1,2,3])
        String[] tradeValue = DataprocessUtils.convertStrToArray(cbmsSuppliersetting.getCbmsTradeTypeName());
        String[] tradeNameArr = new String[tradeValue.length];
        for (int i = 0; i < tradeValue.length; i++) {
            tradeNameArr[i] = CbmsTradeType.labelOf(tradeValue[i]);
        }
        //将商户商户贸易类型重新封装测字符串封装到CbmsSuppliersetting类中
        cbmsSuppliersetting.setCbmsTradeTypeName(DataprocessUtils.convertArrToStr(tradeNameArr));
        logger.info("获取跨境商户用户对象：----->" + cbmsSuppliersetting.toString());
        merchant.setEmail(merchantUser.getLoginName());
        model.addAttribute("merchant", merchant);
        model.addAttribute("cbmsSuppliersetting", cbmsSuppliersetting);
        return "modules/cbms/accountManagement/merchantLegalDetail";
    }

    /**
     * @param merchant
     * @return
     * @discription 修改商户法务审核状态
     * @author guozx
     * @created 2017年1月10日 下午4:44:17
     */
    @RequiresPermissions("merchant:merchantLegal:edit")
    @RequestMapping(value = "status")
    public String status(Merchant merchant) {
        //修改法务审核状态
        merchantCService.legalAuditStatus(merchant);
        logger.info("商户法务审核状态：----->" + RouteStatus.labelOf(merchant.getLegalAuditStatus()));
        return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchantLegal/?repage";
    }

}