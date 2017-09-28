/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 描    述：有效合约Entity
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间：2017-06-03 16:48:40
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
public class HgmsValidContract extends DataEntity<HgmsValidContract> {

    private static final long serialVersionUID = 1L;
    private String contractId;          // 合约编码公司的ID系统自动生成
    private String merchantId;          // 商户编号  与user表id字段为一个值
    private String superiorId;          // 上级商户ID 集团总部的ID
    private String superiorName;        // 上级商户公司名称 集团总部的公司名称
    private String companyName;         // 公司名称
    private String contractName;        // 合同名称
    private Long businessId;            // 业务ID
    private String businessName;        // 业务名称
    private Long serviceId;             // 服务项ID
    private String serviceName;         // 服务项名称
    private Date validityBeginTime;     // 有效期开始时间
    private Date validityEndTime;       // 有效期结束时间
    private String contractFileAddress; // 合同文件地址
    private String status;              // 审核状态(枚举类RouteStatus (("SUCCES", "审核通过"),("AUDING", "待审核"),("AUDREJ", "审核不通过")))
    private String rcAuditStatus;       // 风控审核状态(枚举类RouteStatus (("SUCCES", "审核通过"),("AUDING", "待审核"),("AUDREJ", "审核不通过")))
    private Date rcAuditTime;           // 风控审核时间
    private String rcAuditor;           // 风控审核人
    private String legalAuditStatus;    // 法务审核状态(枚举类RouteStatus (("SUCCES", "审核通过"),("AUDING", "待审核"),("AUDREJ", "审核不通过")))
    private Date legalAuditTime;        // 法务审核时间
    private String legalAuditor;        // 法务审核人
    private Date enteringTime;          // 录入时间
    private String enteringId;          // 录入人ID
    private Date opAuditTime;           // 运营修改时间
    private String opAuditor;           // 运营修改人
    private String contractStatus;      // 合约状态(枚举类ContractStatus  (
    private String objection;           // 审核拒绝理由
    private String ruleBuildType;       // 规则建立方式HgmsRuleBuildType("BULDBM", "后台建立"	 "BULDBU", "商户建立" "BULDNL", "不限定")
    private String collRuleId;          // 代收规则ID
    private String collRuleStatus;      // 代收用户管理规则状态 ENABLE("ENABLE", "启用"),   DISABLE("DISABL", "禁用"),
    private String collAmount;          // 代收归集金额
    private String payRuleId;           // 代付规则ID
    private String payRuleStatus;       // 代付用户管理规则状态 ENABLE("ENABLE", "启用"),  DISABLE("DISABL", "禁用"),
    private String payAmount;           // 代付下放金额
    private String extend1;             // extend1
    private String extend2;             // extend2
    private String extend3;             // extend3

    public HgmsValidContract() {
        super();
    }

    public HgmsValidContract(String id) {
        super(id);
    }

    @Length(min = 1, max = 10, message = "合约编码公司的ID系统自动生成长度必须介于 1 和 10 之间")
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Length(min = 0, max = 64, message = "商户编号  与user表id字段为一个值长度必须介于 0 和 64 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Length(min = 0, max = 64, message = "上级商户ID 集团总部的ID长度必须介于 0 和 64 之间")
    public String getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(String superiorId) {
        this.superiorId = superiorId;
    }

    @Length(min = 0, max = 256, message = "上级商户公司名称 集团总部的公司名称长度必须介于 0 和 256 之间")
    public String getSuperiorName() {
        return superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    @Length(min = 0, max = 100, message = "公司名称长度必须介于 0 和 100 之间")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Length(min = 0, max = 256, message = "合同名称长度必须介于 0 和 256 之间")
    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    @Length(min = 0, max = 64, message = "业务名称长度必须介于 0 和 64 之间")
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Length(min = 0, max = 64, message = "服务项名称长度必须介于 0 和 64 之间")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getValidityBeginTime() {
        return validityBeginTime;
    }

    public void setValidityBeginTime(Date validityBeginTime) {
        this.validityBeginTime = validityBeginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getValidityEndTime() {
        return validityEndTime;
    }

    public void setValidityEndTime(Date validityEndTime) {
        this.validityEndTime = validityEndTime;
    }

    @Length(min = 0, max = 256, message = "合同文件地址长度必须介于 0 和 256 之间")
    public String getContractFileAddress() {
        return contractFileAddress;
    }

    public void setContractFileAddress(String contractFileAddress) {
        this.contractFileAddress = contractFileAddress;
    }

    @Length(min = 0, max = 6, message = "状态AUDIT_SUCCESS(长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 6, message = "风控审核状态(枚举类RouteStatus ((长度必须介于 0 和 6 之间")
    public String getRcAuditStatus() {
        return rcAuditStatus;
    }

    public void setRcAuditStatus(String rcAuditStatus) {
        this.rcAuditStatus = rcAuditStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRcAuditTime() {
        return rcAuditTime;
    }

    public void setRcAuditTime(Date rcAuditTime) {
        this.rcAuditTime = rcAuditTime;
    }

    @Length(min = 0, max = 6, message = "风控审核人长度必须介于 0 和 6 之间")
    public String getRcAuditor() {
        return rcAuditor;
    }

    public void setRcAuditor(String rcAuditor) {
        this.rcAuditor = rcAuditor;
    }

    @Length(min = 0, max = 6, message = "法务审核状态(枚举类RouteStatus ((长度必须介于 0 和 6 之间")
    public String getLegalAuditStatus() {
        return legalAuditStatus;
    }

    public void setLegalAuditStatus(String legalAuditStatus) {
        this.legalAuditStatus = legalAuditStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLegalAuditTime() {
        return legalAuditTime;
    }

    public void setLegalAuditTime(Date legalAuditTime) {
        this.legalAuditTime = legalAuditTime;
    }

    @Length(min = 0, max = 6, message = "法务审核人长度必须介于 0 和 6 之间")
    public String getLegalAuditor() {
        return legalAuditor;
    }

    public void setLegalAuditor(String legalAuditor) {
        this.legalAuditor = legalAuditor;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEnteringTime() {
        return enteringTime;
    }

    public void setEnteringTime(Date enteringTime) {
        this.enteringTime = enteringTime;
    }

    @Length(min = 0, max = 11, message = "录入人ID长度必须介于 0 和 11 之间")
    public String getEnteringId() {
        return enteringId;
    }

    public void setEnteringId(String enteringId) {
        this.enteringId = enteringId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOpAuditTime() {
        return opAuditTime;
    }

    public void setOpAuditTime(Date opAuditTime) {
        this.opAuditTime = opAuditTime;
    }

    @Length(min = 0, max = 6, message = "运营修改人长度必须介于 0 和 6 之间")
    public String getOpAuditor() {
        return opAuditor;
    }

    public void setOpAuditor(String opAuditor) {
        this.opAuditor = opAuditor;
    }

    @Length(min = 0, max = 6, message = "合约状态(枚举类ContractStatus  (长度必须介于 0 和 6 之间")
    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    @Length(min = 0, max = 256, message = "审核拒绝理由长度必须介于 0 和 256 之间")
    public String getObjection() {
        return objection;
    }

    public void setObjection(String objection) {
        this.objection = objection;
    }

    @Length(min = 0, max = 6, message = "规则建立方式HgmsRuleBuildType(长度必须介于 0 和 6 之间")
    public String getRuleBuildType() {
        return ruleBuildType;
    }

    public void setRuleBuildType(String ruleBuildType) {
        this.ruleBuildType = ruleBuildType;
    }

    @Length(min = 0, max = 20, message = "代收规则ID长度必须介于 0 和 20 之间")
    public String getCollRuleId() {
        return collRuleId;
    }

    public void setCollRuleId(String collRuleId) {
        this.collRuleId = collRuleId;
    }

    @Length(min = 0, max = 6, message = "代收用户管理规则状态 ENABLE(长度必须介于 0 和 6 之间")
    public String getCollRuleStatus() {
        return collRuleStatus;
    }

    public void setCollRuleStatus(String collRuleStatus) {
        this.collRuleStatus = collRuleStatus;
    }

    public String getCollAmount() {
        return collAmount;
    }

    public void setCollAmount(String collAmount) {
        this.collAmount = collAmount;
    }

    @Length(min = 0, max = 20, message = "代付规则ID长度必须介于 0 和 20 之间")
    public String getPayRuleId() {
        return payRuleId;
    }

    public void setPayRuleId(String payRuleId) {
        this.payRuleId = payRuleId;
    }

    @Length(min = 0, max = 6, message = "代付用户管理规则状态 ENABLE(长度必须介于 0 和 6 之间")
    public String getPayRuleStatus() {
        return payRuleStatus;
    }

    public void setPayRuleStatus(String payRuleStatus) {
        this.payRuleStatus = payRuleStatus;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
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

    @Override
    public String toString() {
        return "HgmsValidContract{" +
                "contractId='" + contractId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", superiorId='" + superiorId + '\'' +
                ", superiorName='" + superiorName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contractName='" + contractName + '\'' +
                ", businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", validityBeginTime=" + validityBeginTime +
                ", validityEndTime=" + validityEndTime +
                ", contractFileAddress='" + contractFileAddress + '\'' +
                ", status='" + status + '\'' +
                ", rcAuditStatus='" + rcAuditStatus + '\'' +
                ", rcAuditTime=" + rcAuditTime +
                ", rcAuditor='" + rcAuditor + '\'' +
                ", legalAuditStatus='" + legalAuditStatus + '\'' +
                ", legalAuditTime=" + legalAuditTime +
                ", legalAuditor='" + legalAuditor + '\'' +
                ", enteringTime=" + enteringTime +
                ", enteringId='" + enteringId + '\'' +
                ", opAuditTime=" + opAuditTime +
                ", opAuditor='" + opAuditor + '\'' +
                ", contractStatus='" + contractStatus + '\'' +
                ", objection='" + objection + '\'' +
                ", ruleBuildType='" + ruleBuildType + '\'' +
                ", collRuleId='" + collRuleId + '\'' +
                ", collRuleStatus='" + collRuleStatus + '\'' +
                ", collAmount='" + collAmount + '\'' +
                ", payRuleId='" + payRuleId + '\'' +
                ", payRuleStatus='" + payRuleStatus + '\'' +
                ", payAmount='" + payAmount + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                '}';
    }
}