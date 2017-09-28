package com.heepay.manage.modules.payment.entity;

import com.heepay.manage.common.persistence.DataEntity;

import java.util.Date;

/**
 *
 * 描    述：钱包绑卡
 *
 * 创 建 者： @author liudh
 * 创建时间： 2017/6/8 15:00
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
public class BindCardAuth extends DataEntity<BindCardAuth>{

    private static final long serialVersionUID = 1L;
    private Long authId;		// 签约号
    private Date authTime;		// 签约时间
    private String type;		// 签约类型
    private String status;		// 签约状态
    private String transNo;		// 汇付宝交易号
    private Long merchantId;		// 商户ID
    private String merchantLoginName;		// 商户登录账号
    private String merchantCompany;		// 商户公司
    private String bankId;		// 银行ID
    private String bankName;		// 银行名称
    private String openBankName;		// 开户银行支行名称
    private String bankInfo;		// 银行信息
    private String bankcardNo;		// 银行卡号
    private String bankcardType;		// 银行卡类型
    private String bankcardExpiredDate;		// 银行卡有效期  yyyyMM
    private String bankcardCvv2;		// 银行卡cvv2
    private String bankcardOwnerMobile;		// 银行卡预留手机号
    private String bankcardOwnerName;		// 持卡人姓名
    private String bankcardOwnerIdcard;		// 持卡人身份证号
    private String bankcardOwnerType;		// 银行卡持卡人类型  0=个人1=企业-1=未知
    private String defaultTag;		// 默认银行卡标记  0=否 1=是
    private String channelCode;		// 渠道代码
    private String channelAuth;		// 渠道授权信息
    private String merchantUserId;		// 商户下用户ID
    private String merchantOrderNo;		// 商户订单号
    private String authorizationCode;		// 授权码
    private String ext1;		// 扩展信息
    private String ext2;		// 扩展信息
    private String shortCardNo;		// 短卡号(网关)
    private String shortPhnoeNo;		// 短手机号(网关)
    private Date createTime;		// 创建时间
    private Date updateTime;		// 更新时间
    private String associateLineNumber;		// 联行号

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public Date getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantLoginName() {
        return merchantLoginName;
    }

    public void setMerchantLoginName(String merchantLoginName) {
        this.merchantLoginName = merchantLoginName;
    }

    public String getMerchantCompany() {
        return merchantCompany;
    }

    public void setMerchantCompany(String merchantCompany) {
        this.merchantCompany = merchantCompany;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOpenBankName() {
        return openBankName;
    }

    public void setOpenBankName(String openBankName) {
        this.openBankName = openBankName;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getBankcardNo() {
        return bankcardNo;
    }

    public void setBankcardNo(String bankcardNo) {
        this.bankcardNo = bankcardNo;
    }

    public String getBankcardType() {
        return bankcardType;
    }

    public void setBankcardType(String bankcardType) {
        this.bankcardType = bankcardType;
    }

    public String getBankcardExpiredDate() {
        return bankcardExpiredDate;
    }

    public void setBankcardExpiredDate(String bankcardExpiredDate) {
        this.bankcardExpiredDate = bankcardExpiredDate;
    }

    public String getBankcardCvv2() {
        return bankcardCvv2;
    }

    public void setBankcardCvv2(String bankcardCvv2) {
        this.bankcardCvv2 = bankcardCvv2;
    }

    public String getBankcardOwnerMobile() {
        return bankcardOwnerMobile;
    }

    public void setBankcardOwnerMobile(String bankcardOwnerMobile) {
        this.bankcardOwnerMobile = bankcardOwnerMobile;
    }

    public String getBankcardOwnerName() {
        return bankcardOwnerName;
    }

    public void setBankcardOwnerName(String bankcardOwnerName) {
        this.bankcardOwnerName = bankcardOwnerName;
    }

    public String getBankcardOwnerIdcard() {
        return bankcardOwnerIdcard;
    }

    public void setBankcardOwnerIdcard(String bankcardOwnerIdcard) {
        this.bankcardOwnerIdcard = bankcardOwnerIdcard;
    }

    public String getBankcardOwnerType() {
        return bankcardOwnerType;
    }

    public void setBankcardOwnerType(String bankcardOwnerType) {
        this.bankcardOwnerType = bankcardOwnerType;
    }

    public String getDefaultTag() {
        return defaultTag;
    }

    public void setDefaultTag(String defaultTag) {
        this.defaultTag = defaultTag;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelAuth() {
        return channelAuth;
    }

    public void setChannelAuth(String channelAuth) {
        this.channelAuth = channelAuth;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getShortCardNo() {
        return shortCardNo;
    }

    public void setShortCardNo(String shortCardNo) {
        this.shortCardNo = shortCardNo;
    }

    public String getShortPhnoeNo() {
        return shortPhnoeNo;
    }

    public void setShortPhnoeNo(String shortPhnoeNo) {
        this.shortPhnoeNo = shortPhnoeNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAssociateLineNumber() {
        return associateLineNumber;
    }

    public void setAssociateLineNumber(String associateLineNumber) {
        this.associateLineNumber = associateLineNumber;
    }
}
