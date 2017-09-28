/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.PropertiesLoader;
import com.heepay.enums.CertificateType;
import com.heepay.enums.MerchantStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.ListEnums;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.cbms.entity.CbmsCountrysetting;
import com.heepay.manage.modules.cbms.service.CbmsCountrysettingService;
import com.heepay.manage.modules.cbms.validate.Validator;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantInfo;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantInfoParseResult;
import com.heepay.manage.modules.hgms.service.HgmsMerchantInfoService;
import com.heepay.manage.modules.hgms.service.HgmsMerchantUploadService;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.*;

import static org.apache.logging.log4j.web.WebLoggerContextUtils.getServletContext;


/**
 * 描    述：资金归集商户Controller （添加 、 删除 、 修改 、 上传 ）
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017年3月20日 19:52:56
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
@RequestMapping(value = "${adminPath}/hgms/hgmsMerchantInfo")
public class HgmsMerchantInfoController extends BaseController {

    @Autowired
    private MerchantUserCService merchantUserCService;

    @Autowired
    private HgmsMerchantInfoService hgmsMerchantInfoService;

    @Autowired
    private CbmsCountrysettingService cbmsCountrysettingService;

    @Autowired
    private HgmsMerchantUploadService hgmsMerchantUploadService;

    /**
     * 根据id获取商户信息
     *
     * @param id
     * @return
     * @discription 根据id获取商户信息
     * @author gzx
     * @created 2017年3月24日  10:57:23
     */
    @ModelAttribute
    public HgmsMerchantInfo get(@RequestParam(required = false) String id) {
        HgmsMerchantInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsMerchantInfoService.get(id);
        }
        if (entity == null) {
            entity = new HgmsMerchantInfo();
        }
        return entity;
    }

    /**
     * 跳转商户信息列表页面
     *
     * @param hgmsMerchantInfo
     * @param request
     * @param response
     * @param model
     * @return
     * @discription 跳转商户信息列表页面
     * @author gzx
     * @created 2017年3月24日  10:57:32
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsMerchantInfo hgmsMerchantInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsMerchantInfo> page = new Page<>(request, response);
        //已创建日期到排序
        page.setOrderBy("a.create_time desc");
        page = hgmsMerchantInfoService.findPage(page, hgmsMerchantInfo);
        //循环将复制负责人 商户状态
        for (HgmsMerchantInfo hgmsMerchantInfonew : page.getList()) {
            if (StringUtils.isNotBlank(hgmsMerchantInfonew.getInchargerId())) {
                //赋值商户的负责人
                hgmsMerchantInfonew.setInchargerId(UserUtils.get(hgmsMerchantInfonew.getInchargerId()).getName());
            }
            hgmsMerchantInfonew.setRemark2(MerchantStatus.labelOf(hgmsMerchantInfonew.getRemark2()));
        }
        logger.info("资金归集商户的列表信息展示状态----->成功----->对应的资金归集商户信息条数：" + page.getCount());
        model.addAttribute("page", page);
        return "modules/hgms/hgmsMerchantInfoList";
    }

    /**
     * 跳转综合商户添加页面
     *
     * @param hgmsMerchantInfo
     * @param model
     * @return
     * @discription 跳转综合商户添加页面
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "form")
    public String form(HgmsMerchantInfo hgmsMerchantInfo, Model model) {
        //获取国家的列表（页面的下拉框显示）
        List<CbmsCountrysetting> cbmsCountrysettingList = cbmsCountrysettingService.findAllList();
        //资金归集商户的事业类型显示
        List<EnumBean> industryTypes = ListEnums.industryTypes();
        //资金归集商户的银行卡持卡人类型显示
        List<EnumBean> bankcardOwnerType = ListEnums.bankcardOwnerType();
        //资金归集商户的银行卡类型显示
        List<EnumBean> bankcardType = ListEnums.bankcardType();

        model.addAttribute("hgmsBankcardType", bankcardType);
        model.addAttribute("bankcardOwnerType", bankcardOwnerType);
        model.addAttribute("industryTypes", industryTypes);
        model.addAttribute("cbmsCountrysettingList", cbmsCountrysettingList);
        model.addAttribute("hgmsMerchantInfo", hgmsMerchantInfo);
        return "modules/hgms/hgmsMerchantInfoForm";
    }

    /**
     * 执行保存综合商户命令
     *
     * @param hgmsMerchantInfo
     * @param model
     * @param redirectAttributes
     * @return
     * @discription 保存综合商户
     * @author guozx
     * @created 2016年9月2日 下午4:58:31
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(HgmsMerchantInfo hgmsMerchantInfo, Model model, RedirectAttributes redirectAttributes,
                       @RequestParam("permitsAccountsFile") MultipartFile permitsAccountsFile,
                       @RequestParam("legalCertificateFrontFile") MultipartFile legalCertificateFrontFile,
                       @RequestParam("legalCertificateBackFile") MultipartFile legalCertificateBackFile,
                       @RequestParam("businessLicenseFileFrontFile") MultipartFile businessLicenseFileFrontFile,
                       @RequestParam("taxRegistrationCertificateFile") MultipartFile taxRegistrationCertificateFile,
                       @RequestParam("organizationCodeCertificateFile") MultipartFile organizationCodeCertificateFile) {

        String loginName = hgmsMerchantInfo.getLoginName();
        //验证添加商户时总部商户是否正确
        if (!StringUtils.isEmpty(hgmsMerchantInfo.getSuperiorId())) {
            HgmsMerchantInfo hgmsMerchantInfoTemp = hgmsMerchantInfoService.get(hgmsMerchantInfo.getSuperiorId());
            if (hgmsMerchantInfoTemp == null) {
                addMessage(redirectAttributes, "资金归集商户添加失败,请验证总部商户是否存在！");
                logger.info("资金归集商户保存状态：----->失败----->对应的资金归集商户登录名： " + loginName);
                return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
            }
            if (hgmsMerchantInfoTemp.getMerchantId() != hgmsMerchantInfoTemp.getMerchantId()) {
                addMessage(redirectAttributes, "资金归集商户添加失败，请验证总部商户是否正确！");
                logger.info("资金归集商户保存状态：----->失败----->对应的资金归集商户登录名： " + loginName);
                return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
            }
        }
        //查询商户用户列表中是否存在已经添加的账号（防止用户返回上一步重复提交）
        boolean emailExist = hgmsMerchantInfoService.queryEmailExist(loginName);
        if (emailExist && Validator.isEmail(loginName)) {
            //对比返回的对象和hgmsMerchantInfo是否一致
            if (!beanValidator(model, hgmsMerchantInfo)) {
                return form(hgmsMerchantInfo, model);
            }
            try {
                hgmsMerchantInfo.setPermitsAccounts(hgmsMerchantInfoService.upLoadPic(permitsAccountsFile));
                hgmsMerchantInfo.setLegalCertificateFront(hgmsMerchantInfoService.upLoadPic(legalCertificateFrontFile));
                hgmsMerchantInfo.setLegalCertificateBack(hgmsMerchantInfoService.upLoadPic(legalCertificateBackFile));
                hgmsMerchantInfo.setBusinessLicenseFile(hgmsMerchantInfoService.upLoadPic(businessLicenseFileFrontFile));
                hgmsMerchantInfo.setTaxRegistrationCertificate(hgmsMerchantInfoService.upLoadPic(taxRegistrationCertificateFile));
                hgmsMerchantInfo.setOrganizationCodeCertificate(hgmsMerchantInfoService.upLoadPic(organizationCodeCertificateFile));
                logger.info("资金归集商户相关证件照片保存状态：----->成功----->对应的商户： " + hgmsMerchantInfo.toString());
                Boolean insertAll = hgmsMerchantInfoService.saveInfo(hgmsMerchantInfo);
                if (insertAll) {
                    addMessage(redirectAttributes, "资金归集商户添加成功");
                    logger.info("资金归集商户保存状态：----->成功----->对应的资金归集商户登录名： " + loginName);
                } else {
                    addMessage(redirectAttributes, "资金归集商户添加失败");
                    logger.info("资金归集商户保存状态：----->失败----->对应的资金归集商户登录名： " + loginName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            addMessage(redirectAttributes, "资金归集商户添加失败");
            logger.info("资金归集商户保存状态：----->失败----->对应的资金归集商户登录名： " + loginName);
        }
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
    }


    /**
     * 跳转资金归集综合商户
     *
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     * @discription 跳转资金归集综合商户
     * @author guozx
     * @created 2016年9月2日 下午4:58:31
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "toBatchAdd")
    public String toBatchAdd(@RequestParam(value = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        String message = hgmsMerchantInfoService.CheckHQ(id);
        //再次验证hgmsmerchantInfo的	[商户公司名称 == 总部公司名称] 是否一样
        if ("1".equals(message)) {
            model.addAttribute("id", id);
            return "modules/hgms/hgmsMerchantInfoBatchAdd";
        }
        addMessage(redirectAttributes, message);
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
    }

    /**
     * 解析批量上传资金归集综合商户
     *
     * @param id
     * @param model
     * @param redirectAttributes
     * @param declareExcelFile
     * @return
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "doBathUpload")
    public String doBathUpload(@RequestParam(value = "id") Integer id, Model model,
                               RedirectAttributes redirectAttributes,
                               @RequestParam("declareExcelFile") MultipartFile declareExcelFile) {
        String message = hgmsMerchantInfoService.CheckHQ(id);
        //再次验证hgmsmerchantInfo的	[商户公司名称 == 总部公司名称] 是否一样
        if ("1".equals(message)) {
            try {
                HgmsMerchantInfoParseResult hgmsMerchantInfoParseResult = hgmsMerchantUploadService.readHgmsMerchantInfoXls(declareExcelFile);
                String msg = hgmsMerchantInfoParseResult.getMsg();
                //错误消息不为空
                if (msg != null) {
                    HSSFWorkbook hssfWorkbook = hgmsMerchantInfoParseResult.getHssfWorkbook();
                    //获取当前项目路径
                    String path = getServletContext().getRealPath("/WEB-INF/");
                    File file = new File(path + "汇聚财商户批量错误的文件.xls");
                    // 第2步、通过子类实例化父类对象
                    OutputStream out = null; // 通过对象多态性，进行实例化
                    try {
                        //创建输出流
                        out = new FileOutputStream(file);
                        hssfWorkbook.write(out);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            out.close();
                            addMessage(redirectAttributes, "上传文件有错误信息,上传失败！");
                            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/toBatchAdd?id=" + id;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    List<HgmsMerchantInfo> list = hgmsMerchantInfoParseResult.getList();
                    StringBuilder msgs1 = new StringBuilder();
                    StringBuilder msgs2 = new StringBuilder();
                    //批量保存成功的个数
                    int i = 0;
                    //批量保存失败个数
                    int j = 0;
                    for (HgmsMerchantInfo hgmsMerchantInfo : list) {
                        hgmsMerchantInfo.setSuperiorId(id.toString());
                        Boolean saveStatus = hgmsMerchantInfoService.saveInfo(hgmsMerchantInfo);
                        if (saveStatus) {
                            logger.info("资金归集保存状态：----->成功----->批量添加对应的资金归集商户的登录名为： " + hgmsMerchantInfo.getLoginName());
                            msgs1.append("资金归集商户保存成功登录名：" + hgmsMerchantInfo.getLoginName());
                            i++;
                        } else {
                            logger.info("资金归集保存状态：----->失败----->批量添加对应的资金归集商户的登录名为： " + hgmsMerchantInfo.getLoginName());
                            msgs2.append("资金归集商户保存成功登录名：" + hgmsMerchantInfo.getLoginName());
                            j++;
                        }
                    }
                    addMessage(redirectAttributes, "成功保存条数：" + i + ",不成功保存条数:" + j);
                    return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("id", id);
            return "modules/hgms/hgmsMerchantInfoBatchAdd";
        }
        addMessage(redirectAttributes, message);
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
    }

    /**
     * 导出模板文件
     *
     * @return
     * @discription 导出模板文件
     * @author guozx
     * @created 2016年9月2日 下午4:58:31
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "export")
    public void export(HttpServletResponse response) {
        OutputStream out = null;
        byte[] byteFile = null;
        try {
            String path = getServletContext().getRealPath("/WEB-INF/");
            File file = new File(path + "汇聚财商户批量错误的文件.xls");
            byteFile = hgmsMerchantUploadService.getFileToByte(file);
            response.setCharacterEncoding("UTF-8");
            if (byteFile == null) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("文件不存在");
                return;
            }
            response.setContentType("text/xml;charset=UTF-8");
            String fileName = new String("汇聚财商户批量错误的文件.xls".getBytes("utf-8"), "iso-8859-1");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            out = response.getOutputStream();
            out.write(byteFile);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出批量导入错误文件
     *
     * @return
     * @discription 导出批量导入错误文件
     * @author guozx
     * @created 2016年9月2日 下午4:58:31
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        OutputStream out = null;
        byte[] byteFile = null;
        try {
            String path = getServletContext().getRealPath("/WEB-INF/");
            File file = new File(path + "汇聚财商户批量导入样式.xls");
            byteFile = hgmsMerchantUploadService.getFileToByte(file);
            response.setCharacterEncoding("UTF-8");
            if (byteFile == null) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("文件不存在");
                return;
            }
            response.setContentType("text/xml;charset=UTF-8");
            String fileName = new String("汇聚财商户批量导入样式.xls".getBytes("utf-8"), "iso-8859-1");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            out = response.getOutputStream();
            out.write(byteFile);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转修改页面
     *
     * @param id
     * @param goal               2 、商户列表页面进入修改 4、法务修改页面  5 风控修改页面
     * @param model
     * @param redirectAttributes
     * @return
     * @discription 获取综合商户修改页面
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "edit")
    public String edit(@RequestParam(value = "id") Integer id, @RequestParam(value = "goal") Integer goal, Model model, RedirectAttributes redirectAttributes) {
        //根据merchantNo获取HgmsMerchantInfo对象
        HgmsMerchantInfo hgmsMerchantInfo = hgmsMerchantInfoService.get(id.toString());

        HgmsMerchantInfo hgmsMerchantInfoAll = hgmsMerchantInfoService.integrateUserAndMerchant(hgmsMerchantInfo, goal);
        logger.info("获取综合商户修改页面-----资金归集商户信息：" + hgmsMerchantInfo.toString());

        //获取国家的列表（页面的下拉框显示）
        List<CbmsCountrysetting> cbmsCountrysettingList = cbmsCountrysettingService.findAllList();
        //资金归集商户的事业类型显示
        List<EnumBean> industryTypes = ListEnums.industryTypes();
        //资金归集商户的银行卡持卡人类型显示
        List<EnumBean> bankcardOwnerType = ListEnums.bankcardOwnerType();
        //资金归集商户的银行卡类型显示
        List<EnumBean> bankcardType = ListEnums.bankcardType();

        model.addAttribute("hgmsBankcardType", bankcardType);
        model.addAttribute("bankcardOwnerType", bankcardOwnerType);
        model.addAttribute("industryTypes", industryTypes);
        model.addAttribute("cbmsCountrysettingList", cbmsCountrysettingList);
        model.addAttribute("hgmsMerchantInfo", hgmsMerchantInfoAll);
        //跳转修改页面
        if (2 == goal || 4 == goal || 5 == goal) {
            model.addAttribute("goal", goal);
            addMessage(redirectAttributes, "进入资金归集商户修改页面成功");

            if (!"PERSON".equals(hgmsMerchantInfo.getCompanyType())) {
                return "modules/hgms/hgmsMerchantInfoEdit";
            } else {
                return "modules/hgms/hgmsMerchantPerInfoEdit";
            }

        } else if (1 == goal || 6 == goal || 7 == goal) {
            return "modules/hgms/hgmsMerchantInfoEdit";
        }
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
    }

    /**
     * 跳转查询页面
     *
     * @param id
     * @param goal               1、 商户列表进入查询 6、法务查询页面  7、风控查询页面
     * @param model
     * @param redirectAttributes
     * @return
     * @discription 获取综合商户修改页面
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:view")
    @RequestMapping(value = "detail")
    public String detail(@RequestParam(value = "id") Integer id, @RequestParam(value = "goal") Integer goal, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        //根据merchantNo获取HgmsMerchantInfo对象
        HgmsMerchantInfo hgmsMerchantInfo = null;
        if (id != null) { //个人商户id为空
            hgmsMerchantInfo = hgmsMerchantInfoService.get(id.toString());
        } else {
            String companyId = request.getParameter("companyId");
            hgmsMerchantInfo = hgmsMerchantInfoService.getByCompanyId(companyId);
        }
        HgmsMerchantInfo hgmsMerchantInfoAll = hgmsMerchantInfoService.integrateUserAndMerchant(hgmsMerchantInfo, goal);
        logger.info("获取综合商户修改页面-----资金归集商户信息：" + hgmsMerchantInfo.toString());

        //获取国家的列表（页面的下拉框显示）
        List<CbmsCountrysetting> cbmsCountrysettingList = cbmsCountrysettingService.findAllList();
        //资金归集商户的事业类型显示
        List<EnumBean> industryTypes = ListEnums.industryTypes();
        //资金归集商户的银行卡持卡人类型显示
        List<EnumBean> bankcardOwnerType = ListEnums.bankcardOwnerType();
        //资金归集商户的银行卡类型显示
        List<EnumBean> bankcardType = ListEnums.bankcardType();

        model.addAttribute("hgmsBankcardType", bankcardType);
        model.addAttribute("bankcardOwnerType", bankcardOwnerType);
        model.addAttribute("industryTypes", industryTypes);
        model.addAttribute("cbmsCountrysettingList", cbmsCountrysettingList);
        model.addAttribute("hgmsMerchantInfo", hgmsMerchantInfoAll);
        //跳转修改页面
        if (1 == goal || 6 == goal || 7 == goal) {
            //跳转查看页面
            model.addAttribute("goal", goal);
            addMessage(redirectAttributes, "进入资金归集商户查看页面成功");
            if (!"PERSON".equals(hgmsMerchantInfo.getCompanyType())) {
                return "modules/hgms/hgmsMerchantInfoDetail";
            } else {
                return "modules/hgms/hgmsMerchantPerInfoDetail";
            }
        }
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
    }


    /**
     * 更新汇聚财商户
     *
     * @param hgmsMerchantInfo
     * @param redirectAttributes
     * @param permitsAccountsFile
     * @param legalCertificateFrontFile
     * @param legalCertificateBackFile
     * @param businessLicenseFileFrontFile
     * @param taxRegistrationCertificateFile
     * @param organizationCodeCertificateFile
     * @return
     * @discription 更新汇聚财商户
     * @author guozx
     * @created 2016年9月2日 下午4:58:31
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "update")
    public String update(HgmsMerchantInfo hgmsMerchantInfo, RedirectAttributes redirectAttributes, @RequestParam(value = "goal") Integer goal,
                         @RequestParam("permitsAccountsFile") MultipartFile permitsAccountsFile,
                         @RequestParam("legalCertificateFrontFile") MultipartFile legalCertificateFrontFile,
                         @RequestParam("legalCertificateBackFile") MultipartFile legalCertificateBackFile,
                         @RequestParam("businessLicenseFileFrontFile") MultipartFile businessLicenseFileFrontFile,
                         @RequestParam("taxRegistrationCertificateFile") MultipartFile taxRegistrationCertificateFile,
                         @RequestParam("organizationCodeCertificateFile") MultipartFile organizationCodeCertificateFile) {
        try {
            //修改商户的证件照片（通过三元运算判断是否重新上传照片）
            hgmsMerchantInfo.setPermitsAccounts(permitsAccountsFile.getSize() == 0 ? deleteUrl(hgmsMerchantInfo.getPermitsAccounts()) : hgmsMerchantInfoService.upLoadPic(permitsAccountsFile));
            hgmsMerchantInfo.setLegalCertificateFront(legalCertificateFrontFile.getSize() == 0 ? deleteUrl(hgmsMerchantInfo.getLegalCertificateFront()) : hgmsMerchantInfoService.upLoadPic(legalCertificateFrontFile));
            hgmsMerchantInfo.setLegalCertificateBack(legalCertificateBackFile.getSize() == 0 ? deleteUrl(hgmsMerchantInfo.getLegalCertificateBack()) : hgmsMerchantInfoService.upLoadPic(legalCertificateBackFile));
            hgmsMerchantInfo.setBusinessLicenseFile(businessLicenseFileFrontFile.getSize() == 0 ? deleteUrl(hgmsMerchantInfo.getBusinessLicenseFile()) : hgmsMerchantInfoService.upLoadPic(businessLicenseFileFrontFile));
            if (!CertificateType.MULTIPLE.getValue().equals(hgmsMerchantInfo.getCertificateType())) {
                hgmsMerchantInfo.setTaxRegistrationCertificate(taxRegistrationCertificateFile.getSize() == 0 ? deleteUrl(hgmsMerchantInfo.getTaxRegistrationCertificate()) : hgmsMerchantInfoService.upLoadPic(taxRegistrationCertificateFile));
                hgmsMerchantInfo.setOrganizationCodeCertificate(organizationCodeCertificateFile.getSize() == 0 ? deleteUrl(hgmsMerchantInfo.getOrganizationCodeCertificate()) : hgmsMerchantInfoService.upLoadPic(organizationCodeCertificateFile));
            }
            logger.info("资金归集商户相关证件照片保存状态：----->成功----->对应的商户： " + hgmsMerchantInfo.toString());
            //判断资金归集商户时候更新成功
            boolean updateHgmsMerchantInfoStatus = hgmsMerchantInfoService.updateHgmsMerchantInfo(hgmsMerchantInfo);
            if (hgmsMerchantInfoService.get(hgmsMerchantInfo.getMerchantId()).getLegalAuditor() == null) {
                //如果商户更新成功
                if (updateHgmsMerchantInfoStatus) {
                    addMessage(redirectAttributes, "资金归集商户修改成功");
                    logger.info("资金归集商户修改状态：----->成功----->修改的资金归集商户信息：" + hgmsMerchantInfo.toString());
                } else {
                    addMessage(redirectAttributes, "资金归集商户修改失败");
                    logger.info("资金归集商户修改状态：----->失败----->修改的资金归集商户信息：" + hgmsMerchantInfo.toString());
                }
                //法务审核人不为空
            } else if (hgmsMerchantInfoService.get(hgmsMerchantInfo.getMerchantId()).getRcAuditor() != null) {
                addMessage(redirectAttributes, "资金归集商户审核修改成功");
                logger.info("资金归集商户修改状态：----->成功----->修改的资金归集商户信息：" + hgmsMerchantInfo.toString());
                return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfoRc/?repage";
            } else if (hgmsMerchantInfoService.get(hgmsMerchantInfo.getMerchantId()).getLegalAuditor() != null) {
                addMessage(redirectAttributes, "资金归集商户审核修改成功");
                logger.info("资金归集商户修改状态：----->成功----->修改的资金归集商户信息：" + hgmsMerchantInfo.toString());
                return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfoLegal/?repage";
                //风控审核人不为空
            }
        } catch (Exception e) {
            logger.error("资金归集商户相关证件照片保存状态：----->失败----->对应的商户： " + hgmsMerchantInfo.toString());
            e.printStackTrace();
        }

        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
    }

    /**
     * 修改个人
     *
     * @param hgmsMerchantInfo
     * @param redirectAttributes
     * @param goal
     * @return
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "updatePer")
    public String updatePer(HgmsMerchantInfo hgmsMerchantInfo, RedirectAttributes redirectAttributes, @RequestParam(value = "goal") Integer goal) {
        try {
            logger.info("资金归集商户相关证件照片保存状态：----->成功----->对应的商户： " + hgmsMerchantInfo.toString());
            //判断资金归集商户时候更新成功
            boolean updateHgmsMerchantInfoStatus = hgmsMerchantInfoService.updateHgmsMerchantInfo(hgmsMerchantInfo);
            if (hgmsMerchantInfoService.get(hgmsMerchantInfo.getMerchantId()).getLegalAuditor() == null) {
                //如果商户更新成功
                if (updateHgmsMerchantInfoStatus) {
                    addMessage(redirectAttributes, "资金归集商户修改成功");
                    logger.info("资金归集商户修改状态：----->成功----->修改的资金归集商户信息：" + hgmsMerchantInfo.toString());
                } else {
                    addMessage(redirectAttributes, "资金归集商户修改失败");
                    logger.info("资金归集商户修改状态：----->失败----->修改的资金归集商户信息：" + hgmsMerchantInfo.toString());
                }
                //法务审核人不为空
            } else if (hgmsMerchantInfoService.get(hgmsMerchantInfo.getMerchantId()).getRcAuditor() != null) {
                addMessage(redirectAttributes, "资金归集商户审核修改成功");
                logger.info("资金归集商户修改状态：----->成功----->修改的资金归集商户信息：" + hgmsMerchantInfo.toString());
                return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfoRc/?repage";
            } else if (hgmsMerchantInfoService.get(hgmsMerchantInfo.getMerchantId()).getLegalAuditor() != null) {
                addMessage(redirectAttributes, "资金归集商户审核修改成功");
                logger.info("资金归集商户修改状态：----->成功----->修改的资金归集商户信息：" + hgmsMerchantInfo.toString());
                return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfoLegal/?repage";
                //风控审核人不为空
            }
        } catch (Exception e) {
            logger.error("资金归集商户相关证件照片保存状态：----->失败----->对应的商户： " + hgmsMerchantInfo.toString());
            e.printStackTrace();
        }

        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
    }

    @RequiresPermissions("hgms:hgmsMerchantInfo:edit")
    @RequestMapping(value = "delete")
    public String delete(HgmsMerchantInfo hgmsMerchantInfo, RedirectAttributes redirectAttributes) {
        hgmsMerchantInfoService.delete(hgmsMerchantInfo);
        addMessage(redirectAttributes, "删除资金归集商户成功");
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
    }

    /**
     * 执行修改商户状态
     *
     * @param hgmsMerchantInfo
     * @param redirectAttributes
     * @return
     * @discription 修改商户状态
     * @author gzx
     * @created 2016年9月2日 下午4:23:45
     */
    @RequiresPermissions("merchant:merchant:edit")
    @RequestMapping(value = "status")
    public String status(HgmsMerchantInfo hgmsMerchantInfo, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        MerchantUser merchantUser = new MerchantUser();
        if(hgmsMerchantInfo.getMerchantId()!=null) {
            merchantUser.setId(hgmsMerchantInfo.getMerchantId());
            merchantUser.setStatus(hgmsMerchantInfo.getRemark2());
            merchantUserCService.status(merchantUser);
        }
            hgmsMerchantInfoService.status(hgmsMerchantInfo);

        addMessage(redirectAttributes, "成功");
        logger.info("修改商户状态----->成功----->对应的商户信息：" + hgmsMerchantInfo.toString());
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfo/?repage";
    }

    /**
     * 注册和修改是的ajax用户名验证
     *
     * @param request
     * @param response
     * @throws IOException
     * @discription 注册和修改是的ajax用户名验证
     * @author guozx
     * @created 2017年1月18日11:55:44
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:view")
    @RequestMapping(value = "regist")
    public void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取商户的登录名
        String loginName = request.getParameter("loginName");
        response.setContentType("text/html; charset=utf-8");
        //判断登录名是否填写
        if (loginName == "") {
            //没有填写返回0
            response.getWriter().write("0");
        } else {
            //判断登录名是否是邮箱
            if (Validator.isEmail(loginName)) {
                //查询是否存在已注册用户
                boolean emailExistStatus = hgmsMerchantInfoService.queryEmailExist(loginName);
                //没有注册
                if (emailExistStatus) {
                    //可用
                    response.getWriter().write("1");
                } else {
                    //不可用
                    response.getWriter().write("0");
                }
            } else {
                //邮箱格式不正确
                response.getWriter().write("2");
            }
        }
    }

    /**
     * 注册和修改时ajax总部公司名称
     *
     * @param request
     * @param response
     * @throws IOException
     * @discription 注册和修改时ajax总部公司名称
     * @author guozx
     * @created 2017年1月18日11:55:44
     */
    @RequiresPermissions("hgms:hgmsMerchantInfo:view")
    @RequestMapping(value = "getSuperiorName")
    public void getSuperiorName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取商户的集团商户ID
        String superiorId = request.getParameter("superiorId");
        response.setContentType("text/html; charset=utf-8");
        //判断集团总部ID是否为空
        if (StringUtils.isBlank(superiorId)) {
            //为空返回0
            response.getWriter().write("0");
        }
        //获取集团商户信息
        HgmsMerchantInfo hgmsMerchantInfo = hgmsMerchantInfoService.get(superiorId);
        //获取集团商户信息不存在或者为空
        if (ObjectUtils.isEmpty(hgmsMerchantInfo)) {
            response.getWriter().write("1");
        }
        response.getWriter().write(hgmsMerchantInfo.getCompanyName());

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