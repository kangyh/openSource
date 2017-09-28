/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.service;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.FastDFSUtils;
import com.heepay.enums.*;
import com.heepay.manage.common.cache.PrimaryKeyCreator;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.enums.HgmsBankcardOwnerType;
import com.heepay.manage.common.enums.HgmsBankcardType;
import com.heepay.manage.common.enums.HgmsCompanyType;
import com.heepay.manage.common.enums.HgmsIndustryTypes;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.hgms.dao.HgmsMerchantInfoDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantInfo;
import com.heepay.manage.modules.merchant.dao.MerchantBankCardDao;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 *
 * 描    述：资金归集商户Service
 *
 * 创 建 者： @author guozx@9186.com
 * 创建时间：
 * 创建描述：
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
@Service
@Transactional(readOnly = true)
public class HgmsMerchantInfoService extends CrudService<HgmsMerchantInfoDao, HgmsMerchantInfo> {

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private HgmsMerchantInfoDao hgmsMerchantInfoDao;

    @Autowired
    private MerchantBankCardDao merchantBankCardCService;

    public HgmsMerchantInfo get(String id) {
        return super.get(id);
    }

    public List<HgmsMerchantInfo> findList(HgmsMerchantInfo hgmsMerchantInfo) {
        return super.findList(hgmsMerchantInfo);
    }

    public Page<HgmsMerchantInfo> findPage(Page<HgmsMerchantInfo> page, HgmsMerchantInfo hgmsMerchantInfo) {
        return super.findPage(page, hgmsMerchantInfo);
    }

    @Transactional(readOnly = false)
    public void save(HgmsMerchantInfo hgmsMerchantInfo) {
        super.save(hgmsMerchantInfo, false);
    }

    @Transactional(readOnly = false)
    public void delete(HgmsMerchantInfo hgmsMerchantInfo) {
        super.delete(hgmsMerchantInfo);
    }

    /**
     * 查询该邮箱是否存在
     * @param email    邮箱
     * @return 返回true时为不存在， 返回false为存在
     */
    public boolean queryEmailExist(String email) {
        MerchantUser merchantUser = merchantUserDao.queryEmailExist(email);
        HgmsMerchantInfo hgmsMerchantInfo = hgmsMerchantInfoDao.queryEmailExist(email);
        if (merchantUser == null && hgmsMerchantInfo == null) {
            return true;
        }
        return false;
    }

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public String upLoadPic(MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            //图片上传返回图片地址（不包含域名）
            return FastDFSUtils.uploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
        }
        return "";
    }

    /**
     * 保存商户信息( 包括 user表 、merchant表 、 hgms_merchant_info表)
     * @param hgmsMerchantInfo
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Boolean saveInfo(HgmsMerchantInfo hgmsMerchantInfo) {
        //将集团商户信息中筛选出merchantUser的数据， 存储到ngp_base数据库中的user表
        MerchantUser merchantUser = cmssInUser(hgmsMerchantInfo);
        int insertUser = merchantUserDao.insert(merchantUser);
        hgmsMerchantInfo.setMerchantId(merchantUser.getId());
        //将集团商户信息中筛选出merchant的数据， 存储到ngp_base数据库中的merchant表
        int insertMerchant = merchantDao.insert(cmssInmerchant(hgmsMerchantInfo));
        HgmsMerchantInfo hgmsMerchantInfoNew = cmssInCss(hgmsMerchantInfo);
        int insertHgmsMerchantInfo = hgmsMerchantInfoDao.insert(hgmsMerchantInfoNew);
        return insertUser == 1 && insertMerchant == 1 && insertHgmsMerchantInfo == 1;
    }

    /**
     * 更新商户信息( 包括 user表 、merchant表 、 hgms_merchant_info表)
     * @param hgmsMerchantInfo
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean updateHgmsMerchantInfo(HgmsMerchantInfo hgmsMerchantInfo) {
        //修改是确认银行卡和身份证是否修改 ， 修改--保存新提交的  未修改--不变
        String merchantNo = hgmsMerchantInfo.getMerchantId();
        hgmsMerchantInfo.setUpdateUserId(UserUtils.getUser().getId());
        hgmsMerchantInfo.setUpdateTime(new Date());

        //将HgmsMerchantInfo类中的元素提取到MerchantUser
        MerchantUser merchantUser = cmssInUser(hgmsMerchantInfo);
        merchantUser.setId(merchantNo);
        Merchant merchant = cmssInmerchant(hgmsMerchantInfo);
        merchant.setUserId(Integer.parseInt(merchantNo));
        //处理HgmsMerchantInfo数据
        HgmsMerchantInfo hgmsMerchantInfoNew = cmssInCss(hgmsMerchantInfo);
        //保存商户的商户编码
        int updateMerchantUer = merchantUserDao.update(merchantUser);
        int updateMerchant = merchantDao.update(merchant);
        int updateHgmsMerchantInfo = hgmsMerchantInfoDao.update(hgmsMerchantInfoNew);
        return updateMerchantUer == 1 && updateMerchant == 1 && updateHgmsMerchantInfo == 1;
    }

    /**
     * 法务审核（更新法务审核状态 - 法务审核时间）
     * @param hgmsMerchantInfo
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void legalAuditStatus(HgmsMerchantInfo hgmsMerchantInfo) {
        Merchant merchant = cmssInmerchant(hgmsMerchantInfo);
        //更新hgms_merchant_info表法务审核通过时
        hgmsMerchantInfo.setLegalAuditor(UserUtils.getUser().getId());
        hgmsMerchantInfo.setLegalAuditTime(new Date());
        hgmsMerchantInfoDao.legalAuditStatus(hgmsMerchantInfo);
        //更新merchant表更新法务审核状态
        merchant.setObjection(hgmsMerchantInfo.getObjection());
        merchant.setUpdateDate(new Date());
        merchant.setUpdateBy(UserUtils.getUser());
        merchant.setLegalAuditStatus(hgmsMerchantInfo.getLegalAuditStatus());
        merchant.setRcAuditStatus(hgmsMerchantInfo.getRcAuditStatus());
        merchantDao.legalAuditStatus(merchant);

    }

    /**
     * 风控审核（更新风控审核状态 - 法务审核时间）
     * @param hgmsMerchantInfo
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void rcAuditStatus(HgmsMerchantInfo hgmsMerchantInfo) {
        if (RouteStatus.AUDIT_SUCCESS.getValue().equals(hgmsMerchantInfo.getRcAuditStatus())) {
            hgmsMerchantInfo.setStatus(RouteStatus.AUDIT_SUCCESS.getValue());
            // 修改商户类型
            MerchantUser user = new MerchantUser();
            user.setId(hgmsMerchantInfo.getMerchantId());
            user.setUserType(UserType.MERCHANT.getValue());
            merchantUserDao.update(user);
            // 提取银行卡信息
            MerchantBankCard merchantBankCard = hgmsInBank(hgmsMerchantInfoDao.get(hgmsMerchantInfo.getMerchantId()));
            // 同步银行卡信息
            merchantBankCardCService.synchronizeBankCard(merchantBankCard);
            // 写缓存(商户信息，默认开通充值CP01提现CP05退款CP04 费率)
            RedisUtil.getRedisUtil().saveDefaultProductVORedis(hgmsMerchantInfo.getMerchantId().toString());// 开通默认产品
            RedisUtil.getRedisUtil().saveMerchantVoRedis(hgmsMerchantInfo.getMerchantId().toString());// 商户信息
        }
        //将hgmsMerchantInfo封装整合到merchant对象中
        Merchant merchant = cmssInmerchant(hgmsMerchantInfo);
        hgmsMerchantInfo.setRcAuditor(UserUtils.getUser().getId());
        hgmsMerchantInfo.setRcAuditTime(new Date());
        //更新hgms_merchant_info表风控审核状态
        hgmsMerchantInfoDao.rcAuditStatus(hgmsMerchantInfo);
        merchant.setObjection(hgmsMerchantInfo.getObjection());
        merchant.setUpdateDate(new Date());
        merchant.setUpdateBy(UserUtils.getUser());
        merchant.setRcAuditStatus(hgmsMerchantInfo.getRcAuditStatus());
        merchant.setRcAuditTime(hgmsMerchantInfo.getRcAuditTime());
        merchant.setStatus(hgmsMerchantInfo.getStatus());
        //更新merchant表风控审核状态
        merchantDao.rcAuditStatus(merchant);
    }

    /**
     * 禁用和启用商户
     * @param hgmsMerchantInfo
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void status(HgmsMerchantInfo hgmsMerchantInfo) {
        hgmsMerchantInfoDao.status(hgmsMerchantInfo);
    }

    /**
     * 将hgmsMerchantInfo类中的元素提取到MerchantUser
     * @param hgmsMerchantInfo
     * @return
     */
    public MerchantUser cmssInUser(HgmsMerchantInfo hgmsMerchantInfo) {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setLoginName(hgmsMerchantInfo.getLoginName());
        merchantUser.setUserType(UserType.MERCHANT.getValue());
        merchantUser.setPhone(hgmsMerchantInfo.getContactorPhone());
        merchantUser.setMobile(hgmsMerchantInfo.getContactorPhone());
        merchantUser.setLinkAddress(hgmsMerchantInfo.getBusinessAddress());
        merchantUser.setSource(AllowSystemType.HGMS.getValue());
        merchantUser.setAllowSystem(AllowSystemType.HGMS.getValue() + "," + AllowSystemType.NGP_WEB.getValue());
        merchantUser.setStatus(MerchantStatus.NORMAL.getValue());
        merchantUser.setCreateTime(new Date());
        return merchantUser;
    }

    /**
     * 将hgmsMerchantInfo类中的元素提取到Merchant
     * @param hgmsMerchantInfo
     * @return
     */
    public Merchant cmssInmerchant(HgmsMerchantInfo hgmsMerchantInfo) {
        Merchant merchant = new Merchant();//获取userid=user表的id
        Merchant merchantTemp = merchantDao.get(hgmsMerchantInfo.getMerchantId());
        merchant.setUserId(Integer.parseInt(hgmsMerchantInfo.getMerchantId()));
        merchant.setEmail(hgmsMerchantInfo.getLoginName());
        merchant.setCompanyName(hgmsMerchantInfo.getCompanyName());
        merchant.setLegalRepresentative(hgmsMerchantInfo.getLegalRepresentative());
        merchant.setCompanyPhone(hgmsMerchantInfo.getCompanyPhone());
        merchant.setLegalMobile(hgmsMerchantInfo.getLegalMobile());
        merchant.setWebsite(hgmsMerchantInfo.getWebsite());
        merchant.setBusinessAddress(hgmsMerchantInfo.getBusinessAddress());
        merchant.setContryCode(hgmsMerchantInfo.getContryCode());
        merchant.setContryName(hgmsMerchantInfo.getContryName());
        merchant.setProvinceCode(hgmsMerchantInfo.getProvinceCode());
        merchant.setProvinceName(hgmsMerchantInfo.getProvinceName());
        merchant.setCityCode(hgmsMerchantInfo.getCityCode());
        merchant.setCityName(hgmsMerchantInfo.getCityName());
        merchant.setCountyCode(hgmsMerchantInfo.getCountyCode());
        merchant.setCountyName(hgmsMerchantInfo.getCountyName());
        merchant.setContactAddress(hgmsMerchantInfo.getContactorPersionAddress());
        merchant.setContactor(hgmsMerchantInfo.getContactor());
        merchant.setType(hgmsMerchantInfo.getType());
        merchant.setInchargerId(UserUtils.getUser().getId());
        merchant.setCreateTime(new Date());
        merchant.setCertificateType(hgmsMerchantInfo.getCertificateType());
        merchant.setBusinessLicenseEndTime(hgmsMerchantInfo.getBusinessLicenseEndTime());
        merchant.setOrganizationCode(hgmsMerchantInfo.getOrganizationCode());
        merchant.setTaxRegistrationCertificateNo(hgmsMerchantInfo.getTaxRegistrationCertificateNo());
        merchant.setBusinessScope(hgmsMerchantInfo.getBusinessScope());
        merchant.setLegalIdcard(hgmsMerchantInfo.getLegalIdcard());
        merchant.setLegalCertificateEndTime(hgmsMerchantInfo.getLegalCertificateEndTime());
        merchant.setContactorIdcardNo(hgmsMerchantInfo.getContactorIdcardNo());
        merchant.setContactorCertificateEndTime(hgmsMerchantInfo.getContactorCertificateEndTime());
        merchant.setContactorPhone(hgmsMerchantInfo.getContactorPhone());
        merchant.setIpcNo(hgmsMerchantInfo.getIpcNo());
        merchant.setPermitsAccounts(hgmsMerchantInfo.getPermitsAccounts());
        merchant.setLegalCertificateFront(hgmsMerchantInfo.getLegalCertificateFront());
        merchant.setLegalCertificateBack(hgmsMerchantInfo.getLegalCertificateBack());
        merchant.setTaxRegistrationCertificate(hgmsMerchantInfo.getTaxRegistrationCertificate());
        merchant.setOrganizationCodeCertificate(hgmsMerchantInfo.getOrganizationCodeCertificate());
        merchant.setBusinessLicenseFile(hgmsMerchantInfo.getBusinessLicenseFile());
        merchant.setBusinessLicenseNo(hgmsMerchantInfo.getBusinessLicenseNo());
        merchant.setUnionPayProvinceCode(hgmsMerchantInfo.getUnionPayProvinceCode());
        merchant.setUnionPayProvinceName(hgmsMerchantInfo.getUnionPayProvinceName());
        merchant.setUnionPayCityCode(hgmsMerchantInfo.getUnionPayCityCode());
        merchant.setUnionPayCityName(hgmsMerchantInfo.getUnionPayCityName());
        //更新法务和风控审核状态
        merchant.setLegalAuditStatus(merchantDao.get(hgmsMerchantInfo.getMerchantId()) == null ? RouteStatus.AUDING.getValue() : merchantTemp.getLegalAuditStatus());
        merchant.setRcAuditStatus(merchantDao.get(hgmsMerchantInfo.getMerchantId()) == null ? "" : merchantTemp.getRcAuditStatus());

        //如果商户的法务审核为审核不通过的时候，修改法务审核状态为待审核
        //如果商户的法务审核为审核通过的时候，而风控审核未通过，修改风控审核状态为待审核
        if (RouteStatus.AUDREJ.getValue().equals(merchant.getLegalAuditStatus())) {
            merchant.setLegalAuditStatus(RouteStatus.AUDING.getValue());
        } else if (RouteStatus.AUDIT_SUCCESS.getValue().equals(merchant.getLegalAuditStatus()) && RouteStatus.AUDREJ.getValue().equals(merchant.getRcAuditStatus())) {
            merchant.setRcAuditStatus(RouteStatus.AUDING.getValue());
        }
        return merchant;
    }

    /**
     * 将hgmsMerchantInfo类中的元素提取到HgmsMerchantInfo
     * @param hgmsMerchantInfo
     * @return
     */
    public HgmsMerchantInfo cmssInCss(HgmsMerchantInfo hgmsMerchantInfo) {
        //如果是首次添加
        HgmsMerchantInfo hgmsMerchantInfoTemp = hgmsMerchantInfoDao.get(hgmsMerchantInfo.getMerchantId());
        //如果是总部时添加总部的公司名称
        if (StringUtils.isEmpty(hgmsMerchantInfo.getSuperiorId()) && StringUtils.isEmpty(hgmsMerchantInfo.getSuperiorName())) {
            hgmsMerchantInfo.setSuperiorId(hgmsMerchantInfo.getMerchantId());
            hgmsMerchantInfo.setSuperiorName(hgmsMerchantInfo.getCompanyName());
        }
        //首次添加
        if (ObjectUtils.isEmpty(hgmsMerchantInfoTemp)) {
            hgmsMerchantInfo.setEmail(hgmsMerchantInfo.getLoginName());
            hgmsMerchantInfo.setCompanyType(HgmsCompanyType.ENTERPRISE.getValue());
            hgmsMerchantInfo.setBusiaddr(hgmsMerchantInfo.getBusinessAddress());
            hgmsMerchantInfo.setInputuserId(UserUtils.getUser().getId());
            hgmsMerchantInfo.setInchargerId(UserUtils.getUser().getId());
            hgmsMerchantInfo.setCreatedTime(new Date());
            hgmsMerchantInfo.setCreateTime(new Date());
            hgmsMerchantInfo.setRemark1("0");
            hgmsMerchantInfo.setRemark2(MerchantStatus.NORMAL.getValue());
            hgmsMerchantInfo.setLegalAuditStatus(RouteStatus.AUDING.getValue());

            //如果上级商户id不为空则查询商户的信息
            if (StringUtils.isNotBlank(hgmsMerchantInfo.getSuperiorId())) {
                HgmsMerchantInfo hgmsMerchantInfoSuperior = hgmsMerchantInfoDao.get(hgmsMerchantInfo.getSuperiorId());
                if (!ObjectUtils.isEmpty(hgmsMerchantInfoSuperior)) {
                    hgmsMerchantInfo.setSuperiorName(hgmsMerchantInfoSuperior.getCompanyName());
                }
            }
            //非首次添加
        } else {
            //如果商户的法务审核为审核不通过的时候，修改法务审核状态为待审核
            if (RouteStatus.AUDREJ.getValue().equals(hgmsMerchantInfo.getLegalAuditStatus()) || "".equals(hgmsMerchantInfo.getLegalAuditStatus())) {
                hgmsMerchantInfo.setLegalAuditStatus(RouteStatus.AUDING.getValue());
                //如果商户的法务审核为审核通过的时候，而风控审核未通过，修改风控审核状态为待审核
            } else if (RouteStatus.AUDIT_SUCCESS.getValue().equals(hgmsMerchantInfo.getLegalAuditStatus()) && RouteStatus.AUDREJ.getValue().equals(hgmsMerchantInfo.getRcAuditStatus())) {
                hgmsMerchantInfo.setRcAuditStatus(RouteStatus.AUDING.getValue());
            }
        }
        return hgmsMerchantInfo;
    }

    /**
     * 将hgmsMerchantInfo类中的元素提取到MerchantBankCard(银行卡信息)
     * @param hgmsMerchantInfo
     * @return
     */
    public MerchantBankCard hgmsInBank(HgmsMerchantInfo hgmsMerchantInfo) {
        MerchantBankCard merchantBankCard = new MerchantBankCard();
        merchantBankCard.setProvinceCode(PrimaryKeyCreator.getAuthId().toString()); //签约号
        merchantBankCard.setBankCardAuthType("REALCA"); //签约类型  "WIDRAW":"提现"
        merchantBankCard.setStatus(BankcardAuthStatus.SUCCES.getValue());   //状态
        merchantBankCard.setMerchantId(hgmsMerchantInfo.getMerchantId());   //商户ID
        merchantBankCard.setCountyCode(hgmsMerchantInfo.getEmail());//商户登录账号
        merchantBankCard.setBankNo(hgmsMerchantInfo.getBankNo()); //银行ID
        merchantBankCard.setBankName(hgmsMerchantInfo.getBankName());// 银行名称
        merchantBankCard.setOpenBankName(hgmsMerchantInfo.getOpenBankName());   //开户银行支行名称
        merchantBankCard.setBankId(Aes.encryptStr(hgmsMerchantInfo.getBankcardNo(), Constants.QuickPay.SYSTEM_KEY));//银行卡号
        merchantBankCard.setCardTypeCode(hgmsMerchantInfo.getBankcardType());      // 银行卡类型(储蓄卡-SAVING  信用卡-CREDIT)
        merchantBankCard.setCountyName(Aes.encryptStr(hgmsMerchantInfo.getBankcardOwnerMobile(), Constants.QuickPay.SYSTEM_KEY));//预留手机号
        merchantBankCard.setRecAccName(hgmsMerchantInfo.getBankcardOwnerName());  //持卡人姓名
        merchantBankCard.setOpenName(Aes.encryptStr(hgmsMerchantInfo.getBankcardOwnerIdcard(), Constants.QuickPay.SYSTEM_KEY));//  持卡人身份证号
        merchantBankCard.setOpenBankCode(hgmsMerchantInfo.getBankcardOwnerType());//    银行卡持卡人类型  0=个人1=企业-1=未知
        merchantBankCard.setAssociateLineNumber(hgmsMerchantInfo.getAssociateLineNumber()); //联行号
        merchantBankCard.setCreateTime(new Date());        // 创建时间
        return merchantBankCard;
    }


    /**
     * 将MerchantUser merchantUser, Merchant merchant中的元素整合到HgmsMerchantInfo hgmsMerchantInfo中
     * @param hgmsMerchantInfo
     * @param goal 2==跳转修改页面    1==跳转查看页面
     * @return
     */
    public HgmsMerchantInfo integrateUserAndMerchant(HgmsMerchantInfo hgmsMerchantInfo, Integer goal) {
        //如果目标页面是查看页面
        if (goal == 1 || goal == 3 || goal == 6 || goal == 7) {
            if (!"PERSON".equals(hgmsMerchantInfo.getCompanyType())) {
                hgmsMerchantInfo.setIndustryTypes(HgmsIndustryTypes.labelOf(hgmsMerchantInfo.getIndustryTypes()));
                hgmsMerchantInfo.setCertificateType(CertificateType.labelOf(hgmsMerchantInfo.getCertificateType()));
            }
            hgmsMerchantInfo.setType(MerchantType.labelOf(hgmsMerchantInfo.getType()));
            hgmsMerchantInfo.setBankcardOwnerType(HgmsBankcardOwnerType.labelOf(hgmsMerchantInfo.getBankcardOwnerType()));
            if (StringUtils.isNotBlank(hgmsMerchantInfo.getBankcardType())) {
                hgmsMerchantInfo.setBankcardType(HgmsBankcardType.labelOf(hgmsMerchantInfo.getBankcardType()));
            }
        }
        //页面图片加域
        hgmsMerchantInfo.setLoginName(hgmsMerchantInfo.getEmail());
        hgmsMerchantInfo.setCreateTime(new Date());
        hgmsMerchantInfo.setCreatedTime(new Date());
        if (StringUtils.isNotBlank(hgmsMerchantInfo.getPermitsAccounts())) {
            hgmsMerchantInfo.setPermitsAccounts(RandomUtil.getFastDfs(hgmsMerchantInfo.getPermitsAccounts()));
        }
        if (StringUtils.isNotBlank(hgmsMerchantInfo.getLegalCertificateFront())) {
            hgmsMerchantInfo.setLegalCertificateFront(RandomUtil.getFastDfs(hgmsMerchantInfo.getLegalCertificateFront()));
        }
        if (StringUtils.isNotBlank(hgmsMerchantInfo.getLegalCertificateBack())) {
            hgmsMerchantInfo.setLegalCertificateBack(RandomUtil.getFastDfs(hgmsMerchantInfo.getLegalCertificateBack()));
        }
        if (StringUtils.isNotBlank(hgmsMerchantInfo.getBusinessLicenseFile())) {
            hgmsMerchantInfo.setBusinessLicenseFile(RandomUtil.getFastDfs(hgmsMerchantInfo.getBusinessLicenseFile()));
        }
        if (StringUtils.isNotBlank(hgmsMerchantInfo.getTaxRegistrationCertificate())) {
            hgmsMerchantInfo.setTaxRegistrationCertificate(RandomUtil.getFastDfs(hgmsMerchantInfo.getTaxRegistrationCertificate()));
        }
        if (StringUtils.isNotBlank(hgmsMerchantInfo.getOrganizationCodeCertificate())) {
            hgmsMerchantInfo.setOrganizationCodeCertificate(RandomUtil.getFastDfs(hgmsMerchantInfo.getOrganizationCodeCertificate()));
        }
        return hgmsMerchantInfo;
    }

    /**
     * 验证是否是总部
     * @param id
     * @return
     */
    public String CheckHQ(Integer id) {
        HgmsMerchantInfo hgmsMerchantInfo = hgmsMerchantInfoDao.get(id.toString());
        //再次验证hgmsmerchantInfo的	[商户公司名称 == 总部公司名称] 是否一样
        if (!ObjectUtils.isEmpty(hgmsMerchantInfo)) {
            if (hgmsMerchantInfo.getSuperiorName() == null || hgmsMerchantInfo.getCompanyName() == null) {
                return "请点击批量添加按钮添加商户，总部不存在！";
            } else {
                if ("ENTERP".equals(hgmsMerchantInfo.getCompanyType())) {
                    return "1";
                } else {
                    return "请点击批量添加按钮添加商户，请别私自修改数据！";
                }
            }
        }
        return "请判断该商户是否是总部商户，请别私自修改数据！";
    }

    /**
     * 根据companyId查询商户
     * @param companyId
     * @return
     */
    public HgmsMerchantInfo getByCompanyId(String companyId) {
        return hgmsMerchantInfoDao.getByCompanyId(companyId);
    }
}