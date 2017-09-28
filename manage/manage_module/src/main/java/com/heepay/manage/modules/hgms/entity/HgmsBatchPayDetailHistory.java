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
 * 描    述：批量代付明细历史表Entity
 * <p>
 * 创 建 者： @author 牛俊鹏
 * 创建时间：
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
public class HgmsBatchPayDetailHistory extends DataEntity<HgmsBatchPayDetailHistory> {

    private static final long serialVersionUID = 1L;
    private String transId;        // 交易ID
    private String hfbTransId;        // 汇付宝交易ID
    private String sourceMerchantId;        // 请求来源商户ID
    private String destMerchantId;        // dest_merchant_id
    private String sourceMerchantName;        // 请求来源商户名称
    private String destMerchantName;        // 目标商户名称
    private String payAmount;        // 付款金额
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间
    private Date successTime;        // 成功时间
    private String successAmount;        // 成功金额
    private String transBatchNo;        // 交易批次号
    private String transWay;        // 交易方式
    private String bankNo;        // 银行卡代码
    private String bankcardNo;        // 银行卡号
    private String bankcardOwnerName;        // 银行卡持卡人姓名
    private String bankcardOwnerType;        // 银行卡持卡人类型  0=个人1=企业
    private String province;        // 省
    private String city;        // 市
    private String openbankName;        // 开户行名称
    private String purpose;        // 用途/付款理由
    private String businessType;        // 业务类型
    private String settlementStatus;        // 结算状态
    private String transStatus;        // 交易状态
    private String feeType;        // 手续费扣款方式
    private String feeAmount;        // 手续费扣款金额
    private String feeRatio;        // 手续费扣款费率
    private String requestSource;        // 请求来源
    private String auditFlag;        // 是否需要审核  是否需要审核 0需要1不需要
    private String auditReason;        // 审核理由
    private String errorCode;        // 错误码
    private String errorMsg;        // 错误信息描述
    private String extend1;        // 扩展字段1
    private String extend2;        // extend2
    private String extend3;        // extend3
    private String extend4;        // extend4
    private String extend5;        // extend5
    private Date beginCreateTime;        // 开始 创建时间
    private Date endCreateTime;        // 结束 创建时间

    public HgmsBatchPayDetailHistory() {
        super();
    }

    public HgmsBatchPayDetailHistory(String id) {
        super(id);
    }

    @Length(min = 0, max = 20, message = "交易ID长度必须介于 0 和 20 之间")
    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    @Length(min = 0, max = 32, message = "汇付宝交易ID长度必须介于 0 和 32 之间")
    public String getHfbTransId() {
        return hfbTransId;
    }

    public void setHfbTransId(String hfbTransId) {
        this.hfbTransId = hfbTransId;
    }

    @Length(min = 0, max = 64, message = "请求来源商户ID长度必须介于 0 和 64 之间")
    public String getSourceMerchantId() {
        return sourceMerchantId;
    }

    public void setSourceMerchantId(String sourceMerchantId) {
        this.sourceMerchantId = sourceMerchantId;
    }

    @Length(min = 0, max = 64, message = "dest_merchant_id长度必须介于 0 和 64 之间")
    public String getDestMerchantId() {
        return destMerchantId;
    }

    public void setDestMerchantId(String destMerchantId) {
        this.destMerchantId = destMerchantId;
    }

    @Length(min = 0, max = 64, message = "请求来源商户名称长度必须介于 0 和 64 之间")
    public String getSourceMerchantName() {
        return sourceMerchantName;
    }

    public void setSourceMerchantName(String sourceMerchantName) {
        this.sourceMerchantName = sourceMerchantName;
    }

    @Length(min = 0, max = 64, message = "目标商户名称长度必须介于 0 和 64 之间")
    public String getDestMerchantName() {
        return destMerchantName;
    }

    public void setDestMerchantName(String destMerchantName) {
        this.destMerchantName = destMerchantName;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "创建时间不能为空")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "更新时间不能为空")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public String getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(String successAmount) {
        this.successAmount = successAmount;
    }

    @Length(min = 0, max = 64, message = "交易批次号长度必须介于 0 和 64 之间")
    public String getTransBatchNo() {
        return transBatchNo;
    }

    public void setTransBatchNo(String transBatchNo) {
        this.transBatchNo = transBatchNo;
    }

    @Length(min = 0, max = 6, message = "交易方式长度必须介于 0 和 6 之间")
    public String getTransWay() {
        return transWay;
    }

    public void setTransWay(String transWay) {
        this.transWay = transWay;
    }

    @Length(min = 0, max = 12, message = "银行卡代码长度必须介于 0 和 12 之间")
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    @Length(min = 0, max = 64, message = "银行卡号长度必须介于 0 和 64 之间")
    public String getBankcardNo() {
        return bankcardNo;
    }

    public void setBankcardNo(String bankcardNo) {
        this.bankcardNo = bankcardNo;
    }

    @Length(min = 0, max = 64, message = "银行卡持卡人姓名长度必须介于 0 和 64 之间")
    public String getBankcardOwnerName() {
        return bankcardOwnerName;
    }

    public void setBankcardOwnerName(String bankcardOwnerName) {
        this.bankcardOwnerName = bankcardOwnerName;
    }

    @Length(min = 0, max = 1, message = "银行卡持卡人类型  0=个人1=企业长度必须介于 0 和 1 之间")
    public String getBankcardOwnerType() {
        return bankcardOwnerType;
    }

    public void setBankcardOwnerType(String bankcardOwnerType) {
        this.bankcardOwnerType = bankcardOwnerType;
    }

    @Length(min = 0, max = 256, message = "省长度必须介于 0 和 256 之间")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Length(min = 0, max = 256, message = "市长度必须介于 0 和 256 之间")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Length(min = 0, max = 256, message = "开户行名称长度必须介于 0 和 256 之间")
    public String getOpenbankName() {
        return openbankName;
    }

    public void setOpenbankName(String openbankName) {
        this.openbankName = openbankName;
    }

    @Length(min = 0, max = 256, message = "用途/付款理由长度必须介于 0 和 256 之间")
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Length(min = 0, max = 6, message = "业务类型长度必须介于 0 和 6 之间")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Length(min = 0, max = 6, message = "结算状态长度必须介于 0 和 6 之间")
    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    @Length(min = 0, max = 6, message = "交易状态长度必须介于 0 和 6 之间")
    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    @Length(min = 0, max = 6, message = "手续费扣款方式长度必须介于 0 和 6 之间")
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeRatio() {
        return feeRatio;
    }

    public void setFeeRatio(String feeRatio) {
        this.feeRatio = feeRatio;
    }

    @Length(min = 0, max = 3, message = "请求来源长度必须介于 0 和 3 之间")
    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    @Length(min = 1, max = 1, message = "是否需要审核  是否需要审核 0需要1不需要长度必须介于 1 和 1 之间")
    public String getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(String auditFlag) {
        this.auditFlag = auditFlag;
    }

    @Length(min = 0, max = 256, message = "审核理由长度必须介于 0 和 256 之间")
    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    @Length(min = 0, max = 10, message = "错误码长度必须介于 0 和 10 之间")
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Length(min = 0, max = 256, message = "错误信息描述长度必须介于 0 和 256 之间")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public String getExtend5() {
        return extend5;
    }

    public void setExtend5(String extend5) {
        this.extend5 = extend5;
    }

    public Date getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Date beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    @Override
    public String toString() {
        return "HgmsBatchPayDetailHistory{" +
                "transId='" + transId + '\'' +
                ", hfbTransId='" + hfbTransId + '\'' +
                ", sourceMerchantId='" + sourceMerchantId + '\'' +
                ", destMerchantId='" + destMerchantId + '\'' +
                ", sourceMerchantName='" + sourceMerchantName + '\'' +
                ", destMerchantName='" + destMerchantName + '\'' +
                ", payAmount='" + payAmount + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", successTime=" + successTime +
                ", successAmount='" + successAmount + '\'' +
                ", transBatchNo='" + transBatchNo + '\'' +
                ", transWay='" + transWay + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", bankcardNo='" + bankcardNo + '\'' +
                ", bankcardOwnerName='" + bankcardOwnerName + '\'' +
                ", bankcardOwnerType='" + bankcardOwnerType + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", openbankName='" + openbankName + '\'' +
                ", purpose='" + purpose + '\'' +
                ", businessType='" + businessType + '\'' +
                ", settlementStatus='" + settlementStatus + '\'' +
                ", transStatus='" + transStatus + '\'' +
                ", feeType='" + feeType + '\'' +
                ", feeAmount='" + feeAmount + '\'' +
                ", feeRatio='" + feeRatio + '\'' +
                ", requestSource='" + requestSource + '\'' +
                ", auditFlag='" + auditFlag + '\'' +
                ", auditReason='" + auditReason + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                ", extend4='" + extend4 + '\'' +
                ", extend5='" + extend5 + '\'' +
                ", beginCreateTime=" + beginCreateTime +
                ", endCreateTime=" + endCreateTime +
                '}';
    }
}