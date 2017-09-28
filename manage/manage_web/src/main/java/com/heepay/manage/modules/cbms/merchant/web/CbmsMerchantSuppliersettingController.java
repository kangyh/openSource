/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.merchant.web;

import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.CertificateType;
import com.heepay.enums.MerchantType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.CbmsEnterpriseType;
import com.heepay.manage.common.enums.CbmsSupplierCategory;
import com.heepay.manage.common.enums.CbmsTradeType;
import com.heepay.manage.common.enums.ListEnums;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.cbms.entity.CbmsCountrysetting;
import com.heepay.manage.modules.cbms.entity.CbmsMerchantSuppliersetting;
import com.heepay.manage.modules.cbms.entity.CbmsSuppliersetting;
import com.heepay.manage.modules.cbms.service.CbmsCountrysettingService;
import com.heepay.manage.modules.cbms.service.CbmsMerchantSuppliersettingService;
import com.heepay.manage.modules.cbms.service.CbmsSuppliersettingService;
import com.heepay.manage.modules.cbms.utils.DataprocessUtils;
import com.heepay.manage.modules.cbms.validate.Validator;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * 描    述：总的商户增删改Controller
 * <p>
 * 创 建 者： guozx
 * 创建时间： 2017年1月6日 下午4:48:24
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
@RequestMapping(value = "${adminPath}/cbms/cbmsMerchantSuppliersetting")
public class CbmsMerchantSuppliersettingController extends BaseController {

    @Autowired
    private CbmsMerchantSuppliersettingService cbmsMerchantSuppliersettingService;

    @Autowired
    private CbmsCountrysettingService cbmsCountrysettingService;

    @Autowired
    private MerchantUserCService merchantUserCService;

    @Autowired
    private CbmsSuppliersettingService cbmsSuppliersettingService;

    @Autowired
    private MerchantCService merchantCService;

    /**
     * @param id
     * @return
     * @discription 根据id获取综合商户信息
     * @author guozx
     * @created 2016年9月2日 下午4:48:34
     */
    @ModelAttribute
    public CbmsMerchantSuppliersetting get(@RequestParam(required = false) String id) {
        CbmsMerchantSuppliersetting entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = cbmsMerchantSuppliersettingService.get(id);
        }
        if (entity == null) {
            entity = new CbmsMerchantSuppliersetting();
        }
        return entity;
    }

    /**
     * @param cbmsMerchantSuppliersetting
     * @param model
     * @return
     * @discription 获取综合商户添加页面
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("cbms:cbmsMerchantSuppliersetting:view")
    @RequestMapping(value = "form")
    public String form(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting, Model model) {
        //获取国家的列表（页面的下拉框显示）
        List<CbmsCountrysetting> cbmsCountrysettingList = cbmsCountrysettingService.findAllList();
        model.addAttribute("cbmsCountrysettingList", cbmsCountrysettingList);
        model.addAttribute("cbmsMerchantSuppliersetting", cbmsMerchantSuppliersetting);
        return "modules/cbms/merchant/cbmsMerchantSuppliersettingForm";
    }
    /**
     * @param timeInterval
     * @param merchantid
     * @return
     * @discription 更新API接口查询区间
     * @author 牛俊鹏
     * @created 2017/07/25
     */
    @RequiresPermissions("cbms:cbmsMerchantSuppliersetting:edit")
    @RequestMapping(value = "updateTimeInterval")
    public void updateTimeInterval(String timeInterval,String merchantid,HttpServletRequest request, HttpServletResponse response)throws IOException {
        //更新API接口查询区间
        CbmsSuppliersetting cbmsSuppliersetting=new CbmsSuppliersetting();
        cbmsSuppliersetting.setMerchantNo(merchantid);
        cbmsSuppliersetting.setTimeInterval(timeInterval);
         cbmsSuppliersettingService.updatecbmsSuppliersetting(cbmsSuppliersetting);
         response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("保存成功");
    }

    /**
     * 跳转修改页面
     *
     * @param cbmsMerchantSuppliersetting
     * @param id
     * @param model
     * @param goal                        1、法务修改 2、风控修改
     * @return
     * @discription 获取综合商户修改页面
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("cbms:cbmsMerchantSuppliersetting:view")
    @RequestMapping(value = "edit")
    public String edit(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting, @RequestParam(value = "id") Integer id, Model model, @RequestParam(value = "goal") Integer goal) {
        MerchantUser merchantUser = merchantUserCService.get(id.toString());
        Merchant merchant = merchantCService.get(id.toString());
        logger.info("综合商户修改页面商户信息：" + merchant.toString());
        //页面图片加域
        merchant.setPermitsAccounts(RandomUtil.getFastDfs(merchant.getPermitsAccounts()));
        merchant.setLegalCertificateFront(RandomUtil.getFastDfs(merchant.getLegalCertificateFront()));
        merchant.setLegalCertificateBack(RandomUtil.getFastDfs(merchant.getLegalCertificateBack()));
        merchant.setBusinessLicenseFile(RandomUtil.getFastDfs(merchant.getBusinessLicenseFile()));
        merchant.setTaxRegistrationCertificate(RandomUtil.getFastDfs(merchant.getTaxRegistrationCertificate()));
        merchant.setOrganizationCodeCertificate(RandomUtil.getFastDfs(merchant.getOrganizationCodeCertificate()));
        logger.info("获取综合商户修改页面-----merchant商户信息：" + merchant.toString());
        //根据merchantNo获取CbmsSuppliersetting对象
        CbmsSuppliersetting cbmsSuppliersetting = cbmsSuppliersettingService.get(id.toString());
        //将merchant的对应属性提取封装到cbmsMerchantSuppliersetting对象中
        cbmsMerchantSuppliersetting.setContryCode(cbmsSuppliersetting.getCountryId());
        cbmsMerchantSuppliersetting.setType(merchant.getType());
        cbmsMerchantSuppliersetting.setCertificateType(merchant.getCertificateType());
        cbmsMerchantSuppliersetting.setCbmsEnterpriseTypeName(cbmsSuppliersetting.getCbmsEnterpriseTypeName());
        cbmsMerchantSuppliersetting.setCbmsSupplierCategoryName(cbmsSuppliersetting.getCbmsSupplierCategoryName());
        cbmsMerchantSuppliersetting.setCbmsTradeTypeNameList(Arrays.asList(DataprocessUtils.convertStrToArray(cbmsSuppliersetting.getCbmsTradeTypeName())));
        logger.info("获取综合商户修改页面-----跨境商户信息：" + cbmsSuppliersetting.toString());
        //获取国家的列表（页面的下拉框显示）
        List<CbmsCountrysetting> cbmsCountrysettingList = cbmsCountrysettingService.findAllList();

        logger.info("获取综合商户修改页面-----国家下拉条数：" + cbmsCountrysettingList.size());
        logger.info("获取综合商户修改页面-----综合商户信息：" + cbmsMerchantSuppliersetting.toString());
        model.addAttribute("cbmsCountrysettingList", cbmsCountrysettingList);
        model.addAttribute("merchantUser", merchantUser);
        model.addAttribute("merchant", merchant);
        model.addAttribute("cbmsSuppliersetting", cbmsSuppliersetting);
        model.addAttribute("cbmsMerchantSuppliersetting", cbmsMerchantSuppliersetting);
        model.addAttribute("goal", goal);
        return "modules/cbms/merchant/cbmsMerchantSuppliersettingEdit";
    }

    /**
     * @param id    商户编码
     * @param model
     * @return
     * @discription 获取综合商户详情页面
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("cbms:cbmsMerchantSuppliersetting:view")
    @RequestMapping(value = "detail")
    public String detail(@RequestParam(value = "id") Integer id, Model model) {
        MerchantUser merchantUser = merchantUserCService.get(id.toString());
        Merchant merchant = merchantCService.get(id.toString());
        merchant.setType(MerchantType.labelOf(merchant.getType()));
        merchant.setCertificateType(CertificateType.labelOf(merchant.getCertificateType()));

        //页面图片加域
        merchant.setPermitsAccounts(RandomUtil.getFastDfs(merchant.getPermitsAccounts()));
        merchant.setLegalCertificateFront(RandomUtil.getFastDfs(merchant.getLegalCertificateFront()));
        merchant.setLegalCertificateBack(RandomUtil.getFastDfs(merchant.getLegalCertificateBack()));
        merchant.setBusinessLicenseFile(RandomUtil.getFastDfs(merchant.getBusinessLicenseFile()));
        merchant.setTaxRegistrationCertificate(RandomUtil.getFastDfs(merchant.getTaxRegistrationCertificate()));
        merchant.setOrganizationCodeCertificate(RandomUtil.getFastDfs(merchant.getOrganizationCodeCertificate()));
        logger.info("获取综合商户详情页面-----merchant商户信息：" + merchant.toString());
        //根据商户的id获取中和商户信息
        CbmsSuppliersetting cbmsSuppliersetting = cbmsSuppliersettingService.get(id.toString());
        logger.info("获取综合商户详情页面-----跨境商户信息：" + cbmsSuppliersetting.toString());
        if (cbmsSuppliersetting != null) {
            //将对应的商户企业类别名称转换成中文
            String cbmsEnterpriseTypeName = cbmsSuppliersetting.getCbmsEnterpriseTypeName();
            if (cbmsEnterpriseTypeName != null) {
                cbmsSuppliersetting.setCbmsEnterpriseTypeName(CbmsEnterpriseType.labelOf(cbmsEnterpriseTypeName));
            }
            //将对应的商户企业类型名称转换成中文
            String cbmsSupplierCategoryName = cbmsSuppliersetting.getCbmsSupplierCategoryName();
            if (cbmsSupplierCategoryName != null) {
                cbmsSuppliersetting.setCbmsSupplierCategoryName(CbmsSupplierCategory.labelOf(cbmsSupplierCategoryName));
            }
            //将对应的商户贸易类型名称转换成中文
            String[] tradeValue = DataprocessUtils.convertStrToArray(cbmsSuppliersetting.getCbmsTradeTypeName());
            String[] tradeNameArr = new String[tradeValue.length];
            for (int i = 0; i < tradeValue.length; i++) {
                tradeNameArr[i] = CbmsTradeType.labelOf(tradeValue[i]);
            }
            cbmsSuppliersetting.setCbmsTradeTypeName(DataprocessUtils.convertArrToStr(tradeNameArr));
        }
        List<EnumBean> timeIntervalType = ListEnums.timeIntervalType();
        logger.info("获取综合商户详情页面-----跨境商户信息：" + cbmsSuppliersetting.toString());
        model.addAttribute("merchantUser", merchantUser);
        model.addAttribute("merchant", merchant);
        model.addAttribute("cbmsSuppliersetting", cbmsSuppliersetting);
        model.addAttribute("timeIntervalType", timeIntervalType);
        return "modules/cbms/merchant/cbmsMerchantSuppliersettingDetail";
    }

    /**
     * @param cbmsMerchantSuppliersetting
     * @param model
     * @param redirectAttributes
     * @return
     * @discription 保存综合商户
     * @author guozx
     * @created 2016年9月2日 下午4:58:31
     */
    @RequiresPermissions("cbms:cbmsMerchantSuppliersetting:edit")
    @RequestMapping(value = "save")
    public String save(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting, Model model, RedirectAttributes redirectAttributes,
                       @RequestParam("permitsAccountsFile") MultipartFile permitsAccountsFile,
                       @RequestParam("legalCertificateFrontFile") MultipartFile legalCertificateFrontFile,
                       @RequestParam("legalCertificateBackFile") MultipartFile legalCertificateBackFile,
                       @RequestParam("businessLicenseFileFrontFile") MultipartFile businessLicenseFileFrontFile,
                       @RequestParam("taxRegistrationCertificateFile") MultipartFile taxRegistrationCertificateFile,
                       @RequestParam("organizationCodeCertificateFile") MultipartFile organizationCodeCertificateFile
    ) {
        String loginName = cbmsMerchantSuppliersetting.getLoginName();
        //查询商户用户列表中是否存在已经添加的账号（验证登录名是否是邮箱格式）
        if (!Validator.isEmail(loginName)){
            addMessage(redirectAttributes, "商户添加失败，请输入邮箱格式！");
            logger.info("跨境商户保存状态：----->失败----->对应的商户登录名： " + loginName + "请输入邮箱格式！");
            return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchant/?repage";
        }
        //查询商户用户列表中是否存在已经添加的账号（防止用户返回上一步重复提交）
        if (!cbmsMerchantSuppliersettingService.queryEmailExist(loginName)){
            addMessage(redirectAttributes, "商户添加失败，该商户已经注册汇付宝或跨境支付");
            logger.info("跨境商户保存状态：----->失败----->对应的商户登录名： " + loginName + "该商户已经注册汇付宝或跨境支付");
            return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchant/?repage";
        }
        //保存对应的图片信息
        try {
            cbmsMerchantSuppliersetting.setPermitsAccounts(cbmsMerchantSuppliersettingService.upLoadPic(permitsAccountsFile));
            cbmsMerchantSuppliersetting.setLegalCertificateFront(cbmsMerchantSuppliersettingService.upLoadPic(legalCertificateFrontFile));
            cbmsMerchantSuppliersetting.setLegalCertificateBack(cbmsMerchantSuppliersettingService.upLoadPic(legalCertificateBackFile));
            cbmsMerchantSuppliersetting.setBusinessLicenseFile(cbmsMerchantSuppliersettingService.upLoadPic(businessLicenseFileFrontFile));
            cbmsMerchantSuppliersetting.setTaxRegistrationCertificate(cbmsMerchantSuppliersettingService.upLoadPic(taxRegistrationCertificateFile));
            cbmsMerchantSuppliersetting.setOrganizationCodeCertificate(cbmsMerchantSuppliersettingService.upLoadPic(organizationCodeCertificateFile));
            logger.info("跨境商户相关证件照片保存状态：----->成功----->对应的商户： " + cbmsMerchantSuppliersetting.toString());
        } catch (Exception e) {
            logger.error("跨境商户相关证件照片保存状态：----->失败----->对应的商户： " + cbmsMerchantSuppliersetting.toString(), e);
        }
        Boolean saveCbmsSuppliersettingStatus = cbmsMerchantSuppliersettingService.saveCbmsSuppliersetting(cbmsMerchantSuppliersetting);
        if (saveCbmsSuppliersettingStatus) {
            addMessage(redirectAttributes, "商户添加成功");
            logger.info("跨境商户保存信息状态：----->成功----->对应的商户登录名：" + loginName);
        } else {
            addMessage(redirectAttributes, "商户添加失败");
            logger.info("跨境商户保存信息状态：----->失败----->对应的商户登录名： " + loginName + "商户以存在，无法保存该商户！");
        }
        return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchant/?repage";
    }

    /**
     * @param request
     * @param response
     * @param model
     * @throws IOException
     * @discription 注册和修改是的ajax用户名验证
     * @author guozx
     * @created 2017年1月18日11:55:44
     */
    @RequiresPermissions("cbms:cbmsMerchantSuppliersetting:view")
    @RequestMapping(value = "regist")
    public void regist(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        //获取商户的登录名
        String loginName = request.getParameter("loginName");
        String id = request.getParameter("id");
        response.setContentType("text/html; charset=utf-8");
        //判断登录名是否填写
        if (StringUtil.isBlank(loginName)) {
            //没有填写返回0
            response.getWriter().write("0");
        } else {
            //判断登录名是否是邮箱
            if (Validator.isEmail(loginName)) {
                //查询是否存在已注册用户
                boolean merchantExist = cbmsMerchantSuppliersettingService.queryEmailExist(loginName);
                //没有注册
                if (merchantExist) {
                    //可用
                    response.getWriter().write("1");
                } else {
                    if (id == null) {
                        //不可用
                        response.getWriter().write("0");
                    } else {
                        //商户名和原商户名一致
                        response.getWriter().write("3");
                    }
                }
            } else {
                //邮箱格式不正确
                response.getWriter().write("2");
            }
        }
    }

    /**
     * @param cbmsMerchantSuppliersetting
     * @param redirectAttributes
     * @param goal                            1、法务修改 2、风控修改
     * @param permitsAccountsFile
     * @param legalCertificateFrontFile
     * @param legalCertificateBackFile
     * @param businessLicenseFileFrontFile
     * @param taxRegistrationCertificateFile
     * @param organizationCodeCertificateFile
     * @return
     * @discription 修改综合商户
     * @author guozx
     * @created 2016年9月2日 下午4:58:31
     */
    @RequiresPermissions("cbms:cbmsMerchantSuppliersetting:edit")
    @RequestMapping(value = "update")
    public String update(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting, RedirectAttributes redirectAttributes, @RequestParam(value = "goal") Integer goal,
                         @RequestParam("permitsAccountsFile") MultipartFile permitsAccountsFile,
                         @RequestParam("legalCertificateFrontFile") MultipartFile legalCertificateFrontFile,
                         @RequestParam("legalCertificateBackFile") MultipartFile legalCertificateBackFile,
                         @RequestParam("businessLicenseFileFrontFile") MultipartFile businessLicenseFileFrontFile,
                         @RequestParam("taxRegistrationCertificateFile") MultipartFile taxRegistrationCertificateFile,
                         @RequestParam("organizationCodeCertificateFile") MultipartFile organizationCodeCertificateFile) {
        //更新商户信息
        try {
            //修改商户的证件照片（通过三元运算判断是否重新上传照片）
            cbmsMerchantSuppliersetting.setPermitsAccounts(StringUtil.isBlank(cbmsMerchantSuppliersettingService.upLoadPic(permitsAccountsFile)) ? deleteUrl(cbmsMerchantSuppliersetting.getPermitsAccounts()) : cbmsMerchantSuppliersettingService.upLoadPic(permitsAccountsFile));
            cbmsMerchantSuppliersetting.setLegalCertificateFront(StringUtil.isBlank(cbmsMerchantSuppliersettingService.upLoadPic(legalCertificateFrontFile)) ? deleteUrl(cbmsMerchantSuppliersetting.getLegalCertificateFront()) : cbmsMerchantSuppliersettingService.upLoadPic(legalCertificateFrontFile));
            cbmsMerchantSuppliersetting.setLegalCertificateBack(StringUtil.isBlank(cbmsMerchantSuppliersettingService.upLoadPic(legalCertificateBackFile)) ? deleteUrl(cbmsMerchantSuppliersetting.getLegalCertificateBack()) : cbmsMerchantSuppliersettingService.upLoadPic(legalCertificateBackFile));
            cbmsMerchantSuppliersetting.setBusinessLicenseFile(StringUtil.isBlank(cbmsMerchantSuppliersettingService.upLoadPic(businessLicenseFileFrontFile)) ? deleteUrl(cbmsMerchantSuppliersetting.getBusinessLicenseFile()) : cbmsMerchantSuppliersettingService.upLoadPic(businessLicenseFileFrontFile));
            cbmsMerchantSuppliersetting.setTaxRegistrationCertificate(StringUtil.isBlank(cbmsMerchantSuppliersettingService.upLoadPic(taxRegistrationCertificateFile)) ? deleteUrl(cbmsMerchantSuppliersetting.getTaxRegistrationCertificate()) : cbmsMerchantSuppliersettingService.upLoadPic(taxRegistrationCertificateFile));
            cbmsMerchantSuppliersetting.setOrganizationCodeCertificate(StringUtil.isBlank(cbmsMerchantSuppliersettingService.upLoadPic(organizationCodeCertificateFile)) ? deleteUrl(cbmsMerchantSuppliersetting.getOrganizationCodeCertificate()) : cbmsMerchantSuppliersettingService.upLoadPic(organizationCodeCertificateFile));
            logger.info("跨境商户相关证件照片修改状态：----->成功----->对应的商户登录名： " + cbmsMerchantSuppliersetting.toString());
        } catch (Exception e) {
            logger.error("跨境商户相关证件照片修改状态：----->成功----->对应的商户登录名： " + cbmsMerchantSuppliersetting.toString());
            e.printStackTrace();
        }
        //判断商户时候更新成功
        boolean savecbmsMerchantSuppliersettingStatus = cbmsMerchantSuppliersettingService.updateCbmsSuppliersetting(cbmsMerchantSuppliersetting);
        //如果成功
        if (savecbmsMerchantSuppliersettingStatus) {
            addMessage(redirectAttributes, "商户修改成功");
            logger.info("商户修改状态：----->成功----->修改的商户信息：" + cbmsMerchantSuppliersetting.toString());
        } else {
            addMessage(redirectAttributes, "商户修改失败");
            logger.info("商户修改状态：----->失败----->修改的商户信息：" + cbmsMerchantSuppliersetting.toString());
        }
        if (goal == 1) {
            return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchantLegal/?repage";
        } else if (goal == 2) {
            return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchantRc/?repage";
        }
        return "redirect:" + Global.getAdminPath() + "/cbms/merchant/merchant/?repage";
    }


    /**
     * 修改商户的时候删除图片传回的域名（删除前缀）
     *
     * @param PreSaveUrl
     * @return
     */
    public String deleteUrl(String PreSaveUrl) {
        //获取对应的（对应服务器的fastDFS.properties）
        PropertiesLoader propertiesLoader = new PropertiesLoader("fastDFS.properties");
        //获取#fastDFS图片地址
        String property = propertiesLoader.getProperty("fastdfs.image.host");
        //去除已上传商户的存在域名照片域名
        return PreSaveUrl.replace(property, "");
    }
}