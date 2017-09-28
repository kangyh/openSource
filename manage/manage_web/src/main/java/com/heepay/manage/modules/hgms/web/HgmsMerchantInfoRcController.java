/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.codec.Aes;
import com.heepay.codec.Sha;
import com.heepay.common.util.Constants;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.SmsUtils;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.*;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.HgmsBankcardType;
import com.heepay.manage.common.enums.ListEnums;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.rpc.MerchantAccountClient;
import com.heepay.manage.modules.cbms.utils.MathUtils;
import com.heepay.manage.modules.cbms.validate.Validator;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantInfo;
import com.heepay.manage.modules.hgms.rpc.client.BoundBankCardClient;
import com.heepay.manage.modules.hgms.rpc.client.MainSubMerchantClient;
import com.heepay.manage.modules.hgms.service.HgmsMerchantInfoService;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.rpc.payment.model.BankcardAuthModel;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 描    述：资金归集商户Controller
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-03-29 11:16:25
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
@RequestMapping(value = "${adminPath}/hgms/hgmsMerchantInfoRc")
public class HgmsMerchantInfoRcController extends BaseController {

    @Autowired
    private HgmsMerchantInfoService hgmsMerchantInfoService;

    @Autowired
    private MerchantUserCService merchantUserCService;

    @Autowired
    private MerchantAccountClient merchantAccountClient;

    @Autowired
    private BoundBankCardClient boundBankCardClient;
    @Autowired
    private MainSubMerchantClient mainSubMerchantClient;


    /**
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
     * 获取商户风控审核列表
     * @param hgmsMerchantInfo
     * @param request
     * @param response
     * @param model
     * @return
     * @discription 获取商户信息列表
     * @author gzx
     * @created 2017年3月24日  10:57:32
     */

    @RequiresPermissions("hgms:hgmsMerchantInfoRc:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsMerchantInfo hgmsMerchantInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        hgmsMerchantInfo.setLegalAuditStatus("SUCCES");
        hgmsMerchantInfo.setCompanyType("ENTERP");
        Page<HgmsMerchantInfo> page = new Page<>(request, response);
        page.setOrderBy("a.create_time desc");
        page = hgmsMerchantInfoService.findPage(page, hgmsMerchantInfo);
        List<HgmsMerchantInfo> list = new ArrayList<>();
        //循环将商户的登录名、商户状态、商户的当前负责人添加到商户中去
        for (HgmsMerchantInfo hgmsMerchantInfonew : page.getList()) {
            if (!"PERSON".equals(hgmsMerchantInfonew.getCompanyType())) {
                hgmsMerchantInfonew.setType(MerchantType.labelOf(hgmsMerchantInfonew.getType()));
                hgmsMerchantInfonew.setRcAuditStatus(RouteStatus.labelOf(hgmsMerchantInfonew.getRcAuditStatus()));
                list.add(hgmsMerchantInfonew);
            }
        }
        page.setList(list);
        logger.info("资金归集商户的列表信息展示状态----->成功----->对应的资金归集商户信息条数：" + page.getCount());
        model.addAttribute("page", page);
        return "modules/hgms/hgmsMerchantInfoRcList";
    }

    /**
     * 跳转风控审核页面
     *
     * @param id
     * @param goal
     * @param model
     * @param redirectAttributes
     * @return
     * @discription 跳转风控审核页面
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("hgms:hgmsMerchantInfoRc:edit")
    @RequestMapping(value = "audit")
    public String audit(@RequestParam(value = "id") Integer id, @RequestParam(value = "goal") Integer goal, Model model, RedirectAttributes redirectAttributes) {
        //根据merchantNo获取HgmsMerchantInfo对象
        HgmsMerchantInfo hgmsMerchantInfo = hgmsMerchantInfoService.get(id.toString());
        //显示封装
        HgmsMerchantInfo hgmsMerchantInfoAll = hgmsMerchantInfoService.integrateUserAndMerchant(hgmsMerchantInfo, goal);
        logger.info("获取综合商户修改页面-----资金归集商户信息：" + hgmsMerchantInfo.toString());

        //跳转修改页面
        //法务审核页面信用等级
        List<EnumBean> riskLevel = ListEnums.riskLevel();
        model.addAttribute("riskLevel", riskLevel);
        model.addAttribute("hgmsMerchantInfo", hgmsMerchantInfoAll);
        addMessage(redirectAttributes, "进入资金归集商户风控审核页面成功");
        return "modules/hgms/hgmsMerchantInfoRcAudit";

    }

    /**
     * 执行风控审核
     *
     * @param hgmsMerchantInfo
     * @param redirectAttributes
     * @return
     * @discription 执行风控审核
     * @author guozx
     * @created 2016年9月2日 下午4:49:26
     */
    @RequiresPermissions("hgms:hgmsMerchantInfoRc:edit")
    @RequestMapping(value = "update")
    public String update(HgmsMerchantInfo hgmsMerchantInfo, RedirectAttributes redirectAttributes) {
        String sendSMSStatus;
        //风控审核通过
        if ("SUCCES".equals(hgmsMerchantInfo.getRcAuditStatus())) {
            //开通资金归集账户
            HgmsMerchantInfo merchantInfo = hgmsMerchantInfoService.get(hgmsMerchantInfo.getMerchantId());
            logger.info("调用merchantAccountClient开通资金归集账户接口--开始");
            Boolean merchanAccountFlag = merchantAccountClient.createMerchanAccount(Long.valueOf(merchantInfo.getMerchantId()), merchantInfo.getEmail(), merchantInfo.getCompanyName(), AllowSystemType.HGMS.getValue());
            logger.info("调用merchantAccountClient开通资金归集账户接口--结束" + "处理结果：" + merchanAccountFlag);
            if (!merchanAccountFlag) {
                logger.error("资金归集商户开户状态：----->失败----->审核的资金归集商户信息：" + hgmsMerchantInfo.toString());
                addMessage(redirectAttributes, "资金归集商户开户失败，商户账号" + hgmsMerchantInfo.getLoginName());
                return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfoRc/?repage";
            }
            //风控审核
            hgmsMerchantInfoService.rcAuditStatus(hgmsMerchantInfo);
            //获取当前商户的联系人手机号
            String contactorPhone = hgmsMerchantInfo.getContactorPhone();
            if (contactorPhone != null && Validator.isMobile(contactorPhone)) {
                if (!hgmsMerchantInfo.getSuperiorId().equals(hgmsMerchantInfo.getMerchantId())) {
                    //绑定银行卡（汇付宝）
                    logger.info("调用boundBankCardClient绑定银行卡(汇付宝)接口--开始");
                    boundBankCardClient.saveBankCardCommon(merchantInfoToBankcard(hgmsMerchantInfo), BankcardAuthType.DAISHO.getValue());
                    logger.info("调用boundBankCardClient绑定银行卡(汇付宝)接口--结束");
                    //绑定上下级关系用于汇付宝虚拟账户转账
                    logger.info("调用mainSubMerchantClient虚拟账户互转绑定上下级关系接口--开始");
                    mainSubMerchantClient.saveMainSubMerchant(hgmsMerchantInfo.getSuperiorId(), hgmsMerchantInfo.getMerchantId());
                    logger.info("调用mainSubMerchantClient虚拟账户互转绑定上下级关系接口--结束");
                }
                //随机生成8位的（英文大小写字母和0-9的）数字
                String code = MathUtils.getRandomString(8);
                //发送短信
                sendSMSStatus = SmsUtils.sendSMS(contactorPhone, code, "40");
                logger.info("商户风控审核完成后发送短信内容:----->成功" + sendSMSStatus);
                logger.info("商户风控审核完成后发送短信状态:----->成功----->联系人手机号：" + contactorPhone);
                //加密登录密码
                String loginPassword = Sha.encode(code);
                //封装和保存merchantUser对象
                MerchantUser merchantUser = new MerchantUser();
                merchantUser.setId(hgmsMerchantInfo.getMerchantId());
                merchantUser.setLoginPassword(loginPassword);
                merchantUserCService.updateMerchantPASS(merchantUser);
                logger.info("商户密码更新----->成功");
            }
            logger.info("资金归集商户审核状态：----->通过----->审核的资金归集商户信息：" + hgmsMerchantInfo.toString());
            addMessage(redirectAttributes, "资金归集商户风控审核成功，商户账号" + hgmsMerchantInfo.getLoginName());
        } else if ("AUDREJ".equals(hgmsMerchantInfo.getRcAuditStatus())) {
            //风控审核
            hgmsMerchantInfoService.rcAuditStatus(hgmsMerchantInfo);
            logger.info("资金归集商户审核状态：----->不通过----->审核的资金归集商户信息：" + hgmsMerchantInfo.toString());
            addMessage(redirectAttributes, "资金归集商户风控审核不通过，商户账号" + hgmsMerchantInfo.getLoginName());
        }
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsMerchantInfoRc/?repage";
    }

    /**
     * 提取商户中的银行卡信息，用于汇付宝银行卡信息绑定
     * @param hgmsMerchantInfo
     * @return
     */
    private BankcardAuthModel merchantInfoToBankcard(HgmsMerchantInfo hgmsMerchantInfo) {
        BankcardAuthModel bankcardAuthModel = new BankcardAuthModel();
        bankcardAuthModel.setBankcardNo(hgmsMerchantInfo.getBankcardNo());
        bankcardAuthModel.setBankName(hgmsMerchantInfo.getBankName());
        bankcardAuthModel.setBankId(hgmsMerchantInfo.getBankNo());
        bankcardAuthModel.setMerchantId(Long.parseLong(hgmsMerchantInfo.getSuperiorId()));
        bankcardAuthModel.setBankcardOwnerName(hgmsMerchantInfo.getBankcardOwnerName());
        bankcardAuthModel.setBankcardOwnerMobile(Aes.encryptStr(hgmsMerchantInfo.getBankcardOwnerMobile(), Constants.QuickPay.SYSTEM_KEY));
        if (hgmsMerchantInfo.getBankcardOwnerIdcard() != null) {
            bankcardAuthModel.setBankcardOwnerIdcard(Aes.encryptStr(hgmsMerchantInfo.getBankcardOwnerIdcard(), Constants.QuickPay.SYSTEM_KEY));
        }
        bankcardAuthModel.setBankcardOwnerType(Byte.parseByte(hgmsMerchantInfoService.get(hgmsMerchantInfo.getMerchantId()).getBankcardOwnerType()));
        bankcardAuthModel.setMerchantUserId(hgmsMerchantInfo.getMerchantId());
        bankcardAuthModel.setStatus(BankcardAuthStatus.SUCCES.getValue());
        bankcardAuthModel.setDefaultTag(BankcardAuthDefaultTag.DEFAULT.getValue());
        bankcardAuthModel.setBankcardType(HgmsBankcardType.DEPOSITCARD.getValue());
        bankcardAuthModel.setType(BankcardAuthType.DAISHO.getValue());
        bankcardAuthModel.setCreateTime(DateUtil.dateToString(new Date()));
        return bankcardAuthModel;
    }

}