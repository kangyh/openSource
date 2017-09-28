/**
 *
 */
package com.heepay.manage.modules.cbms.accountManagement.web;

import com.heepay.codec.Sha;
import com.heepay.common.util.SmsUtils;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AllowSystemType;
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
import com.heepay.manage.modules.cbms.utils.MathUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描 述：商户风控审核controller
 * <p>
 * 创 建 者： guozx
 * 创建时间： 2017年1月10日 下午4:48:24
 * 创建描述： 处理跨境风控业务逻辑
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
@RequestMapping(value = "${adminPath}/cbms/merchant/merchantRc")
public class CbmsMerchantRcController extends BaseController {

    @Autowired
    private MerchantCService merchantCService;

    @Autowired
    private MerchantUserCService merchantUserCService;

    @Autowired
    private CbmsSuppliersettingService cbmsSuppliersettingService;

    /**
     * @param id
     * @return
     * @discription 根据id获取商户风控审核信息
     * @author guozx
     * @created 2016年9月2日 下午4:48:34
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
     * @discription 商户风控审核列表
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("merchant:merchantRc:view")
    @RequestMapping(value = {"list", ""})
    public String list(Merchant merchant, HttpServletRequest request, HttpServletResponse response, Model model) {
        //获取跨境商户，并且法务状态为("审核通过")的商户
        merchant.setSource("in('" + AllowSystemType.CROSS_BORDER.getValue() + "')");
        merchant.setLegalAuditStatus(RouteStatus.AUDIT_SUCCESS.getValue());
        logger.info("初始化商户风控审核列表，查询条件merchant：" + merchant.toString());
        Page<Merchant> page = new Page<>(request, response);
        //根据创建时间反排序
        page.setOrderBy("a.create_time desc");
        page = merchantCService.findPage(page, merchant);
        page.setList(EnumView.merchant(page.getList()));
        List<Merchant> list = page.getList();
        //循环给每一个商户的商户企业类别名称显示中文名称
        for (Merchant cbmsmerchant : list) {
            CbmsSuppliersetting cbmsSuppliersetting = cbmsSuppliersettingService.getMerchantByNo(cbmsmerchant.getUserId());
            String cbmsEnterpriseTypeName = cbmsSuppliersetting.getCbmsEnterpriseTypeName();
            String content = CbmsEnterpriseType.labelOf(cbmsEnterpriseTypeName);
            cbmsmerchant.setRemark(content);
        }
        page.setList(list);
        logger.info("商户风控审核列表总条数：" + page.getCount());
        model.addAttribute("page", page);
        return "modules/cbms/accountManagement/merchantRcList";
    }

    /**
     * @param merchant
     * @param model
     * @return
     * @discription 获取商户风控审核页面
     * @author guozx
     * @created 2016年9月2日 下午4:50:14
     */
    @RequiresPermissions("merchant:merchantRc:view")
    @RequestMapping(value = "audit")
    public String audit(Merchant merchant, Model model) {
        //风控审核时转换Merchant显示商户类型和图片的加域
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
        String[] tradeNameArr = new String[tradeValue.length];
        for (int i = 0; i < tradeValue.length; i++) {
            tradeNameArr[i] = CbmsTradeType.labelOf(tradeValue[i]);
        }
        //将商户商户贸易类型重新封装测字符串封装到CbmsSuppliersetting类中
        cbmsSuppliersetting.setCbmsTradeTypeName(DataprocessUtils.convertArrToStr(tradeNameArr));
        logger.info("获取商户风控审核页面跨境商户信息：" + cbmsSuppliersetting.toString());
        logger.info("获取商户风控审核页面merchant商户信息：" + merchant.toString());
        model.addAttribute("cbmsSuppliersetting", cbmsSuppliersetting);
        model.addAttribute("merchant", merchant);
        return "modules/cbms/accountManagement/merchantRcAuditForm";
    }

    /**
     * @param merchant
     * @param model
     * @return
     * @discription 获取商户风控审核详情
     * @author guozx
     * @created 2016年9月2日 下午4:58:13
     */
    @RequiresPermissions("merchant:merchantRc:view")
    @RequestMapping(value = "detail")
    public String detail(Merchant merchant, Model model) {
        //风控审核时转换Merchant显示商户类型和图片的加域
        EnumView.changeMerchant(merchant);
        //分别获取商户的风控和法务状态,并且转换成对应的中文状态
        if (StringUtils.isNotBlank(merchant.getRcAuditStatus())) {
            merchant.setRcAuditStatus(RouteStatus.labelOf(merchant.getRcAuditStatus()));
        }
        if (StringUtils.isNotBlank(merchant.getRcAuditor())) {
            merchant.setRcAuditor(UserUtils.get(merchant.getRcAuditor()).getName());
        }
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
        String[] tradeNameArr = new String[tradeValue.length];
        for (int i = 0; i < tradeValue.length; i++) {
            tradeNameArr[i] = CbmsTradeType.labelOf(tradeValue[i]);
        }
        //将商户商户贸易类型重新封装测字符串封装到CbmsSuppliersetting类中
        cbmsSuppliersetting.setCbmsTradeTypeName(DataprocessUtils.convertArrToStr(tradeNameArr));
        logger.info("获取商户风控审核详情页面跨境商户信息：" + cbmsSuppliersetting.toString());
        logger.info("获取商户风控审核详情页面merchant商户信息：" + merchant.toString());
        model.addAttribute("cbmsSuppliersetting", cbmsSuppliersetting);
        model.addAttribute("merchant", merchant);
        return "modules/cbms/accountManagement/merchantRcDetail";
    }

    /**
     * @param merchant
     * @return
     * @discription 修改商户风控审核状态（添加初始产品）
     * @author guozx
     * @created 2016年9月2日 下午4:59:34
     */
    @RequiresPermissions("merchant:merchantRc:edit")
    @RequestMapping(value = "status")
    public String status(Merchant merchant, RedirectAttributes redirectAttributes) {
        //短信发送信息
        String sendSMSStatus = null;
        if ("SUCCES".equals(merchant.getRcAuditStatus())) {
            //获取当前商户的联系人手机号
            String contactorPhone = merchant.getContactorPhone();
            if (contactorPhone != null) {
                //随机生成8位的（英文大小写字母和0-9的）数字
                String code = MathUtils.getRandomString(8);
                //发送短信
                sendSMSStatus = SmsUtils.sendSMS(contactorPhone, code, "40");
                logger.info("商户风控审核完成后发送短信状态:----->成功" + sendSMSStatus);
                logger.info("商户风控审核完成后发送短信状态:----->成功----->联系人手机号：" + contactorPhone);
                //加密登录密码
                String loginPassword = Sha.encode(code);
                //封装和保存merchantUser对象
                MerchantUser merchantUser = new MerchantUser();
                merchantUser.setId(merchant.getUserId().toString());
                merchantUser.setLoginPassword(loginPassword);
                merchantUserCService.updateMerchantPASS(merchantUser);
                logger.info("商户密码更新----->成功");
            }
        }
        logger.info("风控审核跳转----->待审核商户:" + merchant.toString());
        String nextStep = merchantCService.rcAuditStatus(merchant);
        logger.info("风控审核跳转----->审核完成商户:" + merchant.toString());
        //如果时普通商户跳转的时普通商户
        if (AllowSystemType.NGP_WEB.getValue().equals(merchant.getAllowSystem())) {
            logger.info("风控审核跳转原来商户----->审核完成商户:" + merchant.toString());
            return nextStep;
        }
        //重定向商户风控列表
        logger.info("风控审核页面消息下发" + merchant.toString());
        addMessage(redirectAttributes, "1".equals(sendSMSStatus) ? "短信下发成功" : "该商户风控审核不通过：商户名：" + merchant.getEmail());
        logger.info("风控审核页面消息下发成功" + merchant.toString());
        logger.info("跳转的页面链接：" + "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchantRc/?repage");
        return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchantRc/?repage";
    }
}