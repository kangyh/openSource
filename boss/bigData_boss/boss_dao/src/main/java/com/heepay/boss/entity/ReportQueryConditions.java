package com.heepay.boss.entity;

import java.util.Date;
/**
 *
 *
 * 描 述：BOSS报表条件设置
 *
 * 创 建 者：wangdong
 * 创建时间：2017/6/1 14:13
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
public class ReportQueryConditions {
    /**
     * 主键
     */
    private Long reportId;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 支付类型名称
     */
    private String payTypeName;

    /**
     * 银行支付提供者
     */
    private String bankProvider;

    /**
     * 银行支付提供者名称
     */
    private String bankProviderName;

    /**
     * 银行ID
     */
    private String bankId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行名称(复)
     */
    private String bankNameRe;

    /**
     * java支付类型
     */
    private String payTypeJava;

    /**
     * java支付类型名称
     */
    private String payTypeNameJava;

    /**
     * java银行ID
     */
    private String bankIdJava;

    /**
     * java银行名称
     */
    private String bankNameJava;

    /**
     * java通道合作方
     */
    private String channelPartnerJava;

    /**
     * java通道合作方名称
     */
    private String channelPartnerNameJava;

    /**
     * 公司ID|结算主体
     */
    private String companyId;

    /**
     * 公司名称|结算主体
     */
    private String companyName;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateAuthor;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态
     */
    private String status;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName == null ? null : payTypeName.trim();
    }

    public String getBankProvider() {
        return bankProvider;
    }

    public void setBankProvider(String bankProvider) {
        this.bankProvider = bankProvider == null ? null : bankProvider.trim();
    }

    public String getBankProviderName() {
        return bankProviderName;
    }

    public void setBankProviderName(String bankProviderName) {
        this.bankProviderName = bankProviderName == null ? null : bankProviderName.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankNameRe() {
        return bankNameRe;
    }

    public void setBankNameRe(String bankNameRe) {
        this.bankNameRe = bankNameRe == null ? null : bankNameRe.trim();
    }

    public String getPayTypeJava() {
        return payTypeJava;
    }

    public void setPayTypeJava(String payTypeJava) {
        this.payTypeJava = payTypeJava == null ? null : payTypeJava.trim();
    }

    public String getPayTypeNameJava() {
        return payTypeNameJava;
    }

    public void setPayTypeNameJava(String payTypeNameJava) {
        this.payTypeNameJava = payTypeNameJava == null ? null : payTypeNameJava.trim();
    }

    public String getBankIdJava() {
        return bankIdJava;
    }

    public void setBankIdJava(String bankIdJava) {
        this.bankIdJava = bankIdJava == null ? null : bankIdJava.trim();
    }

    public String getBankNameJava() {
        return bankNameJava;
    }

    public void setBankNameJava(String bankNameJava) {
        this.bankNameJava = bankNameJava == null ? null : bankNameJava.trim();
    }

    public String getChannelPartnerJava() {
        return channelPartnerJava;
    }

    public void setChannelPartnerJava(String channelPartnerJava) {
        this.channelPartnerJava = channelPartnerJava == null ? null : channelPartnerJava.trim();
    }

    public String getChannelPartnerNameJava() {
        return channelPartnerNameJava;
    }

    public void setChannelPartnerNameJava(String channelPartnerNameJava) {
        this.channelPartnerNameJava = channelPartnerNameJava == null ? null : channelPartnerNameJava.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}