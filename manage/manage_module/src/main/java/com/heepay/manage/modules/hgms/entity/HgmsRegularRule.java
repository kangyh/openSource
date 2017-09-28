/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 描    述：规则表Entity
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-08-10 21:47:51
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
public class HgmsRegularRule extends DataEntity<HgmsRegularRule> {

    private static final long serialVersionUID = 1L;
    private Long ruleId;        // 规则ID
    private String merchantId;        // 规则创建的商户ID
    private String companyName;        // 规则创建的商户名称
    private String ruleName;        // 任务名称
    private String ruleType;        // BusinessType规则类型 CASHSP(
    private String ruleMode;        // 规则模式
    private String ruleForms;        // 规则形式
    private String ruleModeMonth;        // 规则的月
    private String ruleModeDay;        // 规则的日
    private String ruleModeWeek;        // 规则的周
    private String userStatus;        // 用户管理规则状态            ENABLE(
    private String adminStatus;        // 后台管理员管理状态            ENABLE(
    private String payAmount;        // 归集金额
    private Date createTime;        // 创建时间
    private Long updateUserId;        // 修改人
    private Date updateTime;        // 更新时间
    private String transWay;        // 交易类型PAYHFB：向汇付宝账户付款 PAYBNK：向银行卡付款 CLTHFB：向汇付宝账户收款 CLTBNK：向银行卡收款
    private String status;        // 状态
    private String operator;        // 操作人
    private String errorRecord;        // 错误记录
    private String ruleDetail;        // 规则模式详情
    private String extend1;        // extend1
    private String extend2;        // extend2
    private String extend3;        // extend3
    private String extend4;        // extend4

    public HgmsRegularRule() {
        super();
    }

    public HgmsRegularRule(String id) {
        super(id);
    }

    @NotNull(message = "规则ID不能为空")
    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    @Length(min = 0, max = 64, message = "规则创建的商户ID长度必须介于 0 和 64 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Length(min = 0, max = 50, message = "规则创建的商户名称长度必须介于 0 和 50 之间")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Length(min = 0, max = 256, message = "任务名称长度必须介于 0 和 256 之间")
    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    @Length(min = 0, max = 64, message = "BusinessType规则类型 CASHSP(长度必须介于 0 和 64 之间")
    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    @Length(min = 0, max = 64, message = "规则模式长度必须介于 0 和 64 之间")
    public String getRuleMode() {
        return ruleMode;
    }

    public void setRuleMode(String ruleMode) {
        this.ruleMode = ruleMode;
    }

    @Length(min = 0, max = 6, message = "规则形式长度必须介于 0 和 6 之间")
    public String getRuleForms() {
        return ruleForms;
    }

    public void setRuleForms(String ruleForms) {
        this.ruleForms = ruleForms;
    }

    @Length(min = 0, max = 4, message = "规则的月长度必须介于 0 和 4 之间")
    public String getRuleModeMonth() {
        return ruleModeMonth;
    }

    public void setRuleModeMonth(String ruleModeMonth) {
        this.ruleModeMonth = ruleModeMonth;
    }

    @Length(min = 0, max = 4, message = "规则的日长度必须介于 0 和 4 之间")
    public String getRuleModeDay() {
        return ruleModeDay;
    }

    public void setRuleModeDay(String ruleModeDay) {
        this.ruleModeDay = ruleModeDay;
    }

    @Length(min = 0, max = 64, message = "规则的周长度必须介于 0 和 64 之间")
    public String getRuleModeWeek() {
        return ruleModeWeek;
    }

    public void setRuleModeWeek(String ruleModeWeek) {
        this.ruleModeWeek = ruleModeWeek;
    }

    @Length(min = 0, max = 6, message = "用户管理规则状态            ENABLE(长度必须介于 0 和 6 之间")
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Length(min = 0, max = 6, message = "后台管理员管理状态            ENABLE(长度必须介于 0 和 6 之间")
    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Length(min = 0, max = 6, message = "交易类型PAYHFB：向汇付宝账户付款 PAYBNK：向银行卡付款 CLTHFB：向汇付宝账户收款 CLTBNK：向银行卡收款长度必须介于 0 和 6 之间")
    public String getTransWay() {
        return transWay;
    }

    public void setTransWay(String transWay) {
        this.transWay = transWay;
    }

    @Length(min = 0, max = 6, message = "状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 64, message = "操作人长度必须介于 0 和 64 之间")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Length(min = 0, max = 1024, message = "错误记录长度必须介于 0 和 1024 之间")
    public String getErrorRecord() {
        return errorRecord;
    }

    public void setErrorRecord(String errorRecord) {
        this.errorRecord = errorRecord;
    }

    @Length(min = 0, max = 1024, message = "规则模式详情长度必须介于 0 和 1024 之间")
    public String getRuleDetail() {
        return ruleDetail;
    }

    public void setRuleDetail(String ruleDetail) {
        this.ruleDetail = ruleDetail;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    public String getExtend4() {
        return extend4;
    }

    public void setExtend4(String extend4) {
        this.extend4 = extend4;
    }

    @Override
    public String toString() {
        return "HgmsRegularRule{" +
                "ruleId=" + ruleId +
                ", merchantId='" + merchantId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", ruleType='" + ruleType + '\'' +
                ", ruleMode='" + ruleMode + '\'' +
                ", ruleForms='" + ruleForms + '\'' +
                ", ruleModeMonth='" + ruleModeMonth + '\'' +
                ", ruleModeDay='" + ruleModeDay + '\'' +
                ", ruleModeWeek='" + ruleModeWeek + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", adminStatus='" + adminStatus + '\'' +
                ", payAmount='" + payAmount + '\'' +
                ", createTime=" + createTime +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                ", transWay='" + transWay + '\'' +
                ", status='" + status + '\'' +
                ", operator='" + operator + '\'' +
                ", errorRecord='" + errorRecord + '\'' +
                ", ruleDetail='" + ruleDetail + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                ", extend4='" + extend4 + '\'' +
                '}';
    }
}