/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.service.impl;

import com.heepay.common.util.DateUtil;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.rpc.service.ExternalMerchantService;
import com.heepay.manage.rpc.service.ExternalMerchantThrift;
import com.heepay.manage.rpc.service.ExternalMerchantUserThrift;
import com.heepay.rpc.service.RpcService;
import com.heepay.vo.MerchantVO;
import com.thoughtworks.xstream.mapper.Mapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * 描述：外部商户导入接口
 * <p>
 * 创建者  B.HJ
 * 创建时间 2017-01-19-15:19
 * 创建描述：外部商户导入接口
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Component
@RpcService(name = "externalMerchantServiceImpl", processor = ExternalMerchantService.Processor.class)
public class ExternalMerchantServiceImpl implements ExternalMerchantService.Iface {

    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private MerchantUserDao merchantUserDao;
    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String saveExternalMerchant(ExternalMerchantUserThrift externalMerchantUserThrift, ExternalMerchantThrift externalMerchantThrift) throws TException {
        //先生成一个用户记录
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setLoginName(externalMerchantUserThrift.getLoginName());//登录名   邮箱
        merchantUser.setUserType(externalMerchantUserThrift.getUserType());//用户类型
        merchantUser.setLoginPassword(externalMerchantUserThrift.getLoginPassword());//登录密码
        merchantUser.setPayPassword(externalMerchantUserThrift.getPayPassword());//支付密码
        merchantUser.setSecretQuestion(externalMerchantUserThrift.getSecretQuestion());//安全问题
        merchantUser.setAnswerSecretQuestion(externalMerchantUserThrift.getAnswerSecretQuestion());//安全问题答案
        merchantUser.setMobile(externalMerchantUserThrift.getMobile());//手机号
        merchantUser.setSource(externalMerchantUserThrift.getSource());//来源
        merchantUser.setAllowSystem(externalMerchantUserThrift.getAllowSystem());//允许登录的系统
        merchantUser.setRemarks(externalMerchantUserThrift.getRemarks());//备注
        merchantUser.setStatus(externalMerchantUserThrift.getStatus());
        try {
            merchantUser.setCreateTime(DateUtils.parseDate(externalMerchantThrift.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            throw new TException(e);
        }

        //判断该用户是否存在，如果存在则直接返回失败
        MerchantUser merchantUser1 = merchantUserDao.queryEmailExist(merchantUser.getLoginName());

        if(merchantUser1 != null) {
            //if(merchantId is exist), exist:update;  otherwise  insert;
            String merchantId = externalMerchantThrift.getUserId();
            if (merchantId != null && (!"".equals(merchantId))){
                MerchantVO merchantVO = merchantDao.getMerchantVo(merchantId);
                if (merchantVO != null) {   //update
                merchantDao.update(getWrapMerchant(merchantId,externalMerchantThrift));
                return merchantId;
                }
            }
            else{
                //新增商户，邮箱存在时
                return "exist";
            }

            //return "exist";
        }

       else{
            merchantUserDao.insert(merchantUser);
        }
        //生成商户记录   String userId = merchantUser.getId();
        merchantDao.insert(getWrapMerchant(merchantUser.getId(),externalMerchantThrift));
        return merchantUser.getId();
    }
    private   Merchant  getWrapMerchant(String userId,ExternalMerchantThrift externalMerchantThrift){
        Merchant merchant = new Merchant();
        merchant.setUserId(Integer.valueOf(userId));//用户id
        merchant.setEmail(externalMerchantThrift.getEmail());//邮箱email
        merchant.setCompanyName(externalMerchantThrift.getCompanyName());//公司名称
        merchant.setLegalRepresentative(externalMerchantThrift.getLegalRepresentative());//法人代表
        merchant.setCompanyPhone(externalMerchantThrift.getCompanyPhone());//公司联系电话
        merchant.setLegalMobile(externalMerchantThrift.getLegalMobile());//法人手机号码
        merchant.setWebsite(externalMerchantThrift.getWebsite());//公司网址
        merchant.setBusinessAddress(externalMerchantThrift.getBusinessAddress());//公司注册地址
        merchant.setProvinceCode(externalMerchantThrift.getProvinceCode());//省、自治区、州代码
        merchant.setProvinceName(externalMerchantThrift.getProvinceName());//省、自治区、州名称
        merchant.setCityCode(externalMerchantThrift.getCityCode());//城市代码
        merchant.setCityName(externalMerchantThrift.getCityName());//城市名称
        merchant.setCountyCode(externalMerchantThrift.getCountyCode());//县代码
        merchant.setCountyName(externalMerchantThrift.getCountyName());//县名称
        merchant.setContactAddress(externalMerchantThrift.getContactAddress());//联系地址
        merchant.setContactor(externalMerchantThrift.getContactor());//联系人
        merchant.setType(externalMerchantThrift.getType());//商户类型
        merchant.setStatus(externalMerchantThrift.getStatus());//商户状态
        merchant.setRemarks(externalMerchantThrift.getRemark());//备注
        merchant.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));//创建时间
        merchant.setCertificateType(externalMerchantThrift.getCertificateType());//证件类型
        merchant.setBusinessLicenseEndTime(DateUtil.stringToDate(externalMerchantThrift.getBusinessLicenseEndTime(), DateUtil.DATE_FORMAT_YYYYMMDD));//营业执照结束时间
        merchant.setOrganizationCode(externalMerchantThrift.getOrganizationCode());//组织机构代码
        merchant.setTaxRegistrationCertificateNo(externalMerchantThrift.getTaxRegistrationCertificateNo());//税务登记证号码
        merchant.setBusinessScope(externalMerchantThrift.getBusinessScope());//经营范围
        merchant.setLegalIdcard(externalMerchantThrift.getLegalIdcard());//法人代表身份证号
        merchant.setLegalCertificateEndTime(DateUtil.stringToDate(externalMerchantThrift.getLegalCertificateEndTime(), DateUtil.DATE_FORMAT_YYYYMMDD));//法人代表证件有效期结束
        merchant.setContactorIdcardNo(externalMerchantThrift.getContactorIdcardNo());//联系人身份证号
        merchant.setContactorCertificateEndTime(DateUtil.stringToDate(externalMerchantThrift.getContactorCertificateEndTime(), DateUtil.DATE_FORMAT_YYYYMMDD));//联系人证件有效期结束
        merchant.setContactorPhone(externalMerchantThrift.getContactorPhone());//联系人手机号
        merchant.setIpcNo(externalMerchantThrift.getIpcNo());//IPC备案号
        merchant.setPermitsAccounts(externalMerchantThrift.getPermitsAccounts());//开户许可证
        merchant.setLegalCertificateFront(externalMerchantThrift.getLegalCertificateFront());//法人代表证件照(正)
        merchant.setLegalCertificateBack(externalMerchantThrift.getLegalCertificateBack());//法人代表证件照(反)
        merchant.setTaxRegistrationCertificate(externalMerchantThrift.getTaxRegistrationCertificate());//税务登记证
        merchant.setOrganizationCodeCertificate(externalMerchantThrift.getOrganizationCodeCertificate());//组织机构代码证
        merchant.setBusinessLicenseFile(externalMerchantThrift.getBusinessLicenseFile());//营业执照文件本地存储路径及文件名
        merchant.setBusinessLicenseNo(externalMerchantThrift.getBusinessLicenseNo());//营业执照号码
        merchant.setSuperiorId(externalMerchantThrift.getSuperior_id());//上级商户Id
        merchant.setLegalAuditStatus(externalMerchantThrift.getLegalAuditStatus());
        merchant.setRcAuditStatus(externalMerchantThrift.getRcAuditStatus());
        return merchant;
    }
}
