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
 * 描    述：临时代付汇总Entity
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 09:55:39
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
public class HgmsTemporaryPayRecord extends DataEntity<HgmsTemporaryPayRecord> {

    private static final long serialVersionUID = 1L;
    private String batchId;        // 批次号
    private String merchantId;        // 商户ID
    private String merchantLoginName;        // 商户登录名
    private String merchantCompany;        // 商户公司
    private String totalAmount;        // 交易总额
    private String totalNum;        // 交易总笔数
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间
    private String status;        // 状态
    private Date auditTime;        // 审核时间
    private String auditor;        // 审核人
    private String successTotalNum;        // 成功交易笔数
    private String successTotalAmount;        // 成功交易总额
    private String failedTotalNum;        // 失败交易总笔数
    private String failedTotalAmount;        // 失败交易总额
    private String feeType;        // 手续费方式
    private String feeRatio;        // 手续费费率
    private String feeTotalAmount;        // 手续费总金额
    private String requestSource;        // 请求来源
    private Date finishTime;        // 完成时间
    private String batchFileName;        // 批量文件名称
    private String batchFilePath;        // 批量文件路径
    private String heepayBatchNo;        // 汇付宝批次号
    private String transWay;        // 交易方式
    private String recordSource;        // 订单来源-
    private String extend3;        // 导入文件批次号
    private String extend4;        // extend4
    private String extend5;        // extend5

    public HgmsTemporaryPayRecord() {
        super();
    }

    public HgmsTemporaryPayRecord(String id) {
        super(id);
    }

    @Length(min = 1, max = 32, message = "批次号长度必须介于 1 和 32 之间")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Length(min = 0, max = 64, message = "商户ID长度必须介于 0 和 64 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Length(min = 0, max = 64, message = "商户登录名长度必须介于 0 和 64 之间")
    public String getMerchantLoginName() {
        return merchantLoginName;
    }

    public void setMerchantLoginName(String merchantLoginName) {
        this.merchantLoginName = merchantLoginName;
    }

    @Length(min = 0, max = 64, message = "商户公司长度必须介于 0 和 64 之间")
    public String getMerchantCompany() {
        return merchantCompany;
    }

    public void setMerchantCompany(String merchantCompany) {
        this.merchantCompany = merchantCompany;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Length(min = 0, max = 11, message = "交易总笔数长度必须介于 0 和 11 之间")
    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Length(min = 0, max = 6, message = "状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    @Length(min = 0, max = 10, message = "审核人长度必须介于 0 和 10 之间")
    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    @Length(min = 0, max = 11, message = "成功交易笔数长度必须介于 0 和 11 之间")
    public String getSuccessTotalNum() {
        return successTotalNum;
    }

    public void setSuccessTotalNum(String successTotalNum) {
        this.successTotalNum = successTotalNum;
    }

    public String getSuccessTotalAmount() {
        return successTotalAmount;
    }

    public void setSuccessTotalAmount(String successTotalAmount) {
        this.successTotalAmount = successTotalAmount;
    }

    @Length(min = 0, max = 11, message = "失败交易总笔数长度必须介于 0 和 11 之间")
    public String getFailedTotalNum() {
        return failedTotalNum;
    }

    public void setFailedTotalNum(String failedTotalNum) {
        this.failedTotalNum = failedTotalNum;
    }

    public String getFailedTotalAmount() {
        return failedTotalAmount;
    }

    public void setFailedTotalAmount(String failedTotalAmount) {
        this.failedTotalAmount = failedTotalAmount;
    }

    @Length(min = 0, max = 6, message = "手续费方式长度必须介于 0 和 6 之间")
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeRatio() {
        return feeRatio;
    }

    public void setFeeRatio(String feeRatio) {
        this.feeRatio = feeRatio;
    }

    public String getFeeTotalAmount() {
        return feeTotalAmount;
    }

    public void setFeeTotalAmount(String feeTotalAmount) {
        this.feeTotalAmount = feeTotalAmount;
    }

    @Length(min = 0, max = 3, message = "请求来源长度必须介于 0 和 3 之间")
    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Length(min = 0, max = 256, message = "批量文件名称长度必须介于 0 和 256 之间")
    public String getBatchFileName() {
        return batchFileName;
    }

    public void setBatchFileName(String batchFileName) {
        this.batchFileName = batchFileName;
    }

    @Length(min = 0, max = 256, message = "批量文件路径长度必须介于 0 和 256 之间")
    public String getBatchFilePath() {
        return batchFilePath;
    }

    public void setBatchFilePath(String batchFilePath) {
        this.batchFilePath = batchFilePath;
    }

    @Length(min = 0, max = 32, message = "汇付宝批次号长度必须介于 0 和 32 之间")
    public String getHeepayBatchNo() {
        return heepayBatchNo;
    }

    public void setHeepayBatchNo(String heepayBatchNo) {
        this.heepayBatchNo = heepayBatchNo;
    }

    @Length(min = 0, max = 6, message = "交易方式长度必须介于 0 和 6 之间")
    public String getTransWay() {
        return transWay;
    }

    public void setTransWay(String transWay) {
        this.transWay = transWay;
    }

    @Length(min = 0, max = 6, message = "订单来源-长度必须介于 0 和 6 之间")
    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(String recordSource) {
        this.recordSource = recordSource;
    }

    @Length(min = 0, max = 32, message = "导入文件批次号长度必须介于 0 和 32 之间")
    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    @Length(min = 0, max = 32, message = "extend4长度必须介于 0 和 32 之间")
    public String getExtend4() {
        return extend4;
    }

    public void setExtend4(String extend4) {
        this.extend4 = extend4;
    }

    @Length(min = 0, max = 32, message = "extend5长度必须介于 0 和 32 之间")
    public String getExtend5() {
        return extend5;
    }

    public void setExtend5(String extend5) {
        this.extend5 = extend5;
    }

    @Override
    public String toString() {
        return "HgmsTemporaryPayRecord{" +
                "batchId='" + batchId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", merchantLoginName='" + merchantLoginName + '\'' +
                ", merchantCompany='" + merchantCompany + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", totalNum='" + totalNum + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status='" + status + '\'' +
                ", auditTime=" + auditTime +
                ", auditor='" + auditor + '\'' +
                ", successTotalNum='" + successTotalNum + '\'' +
                ", successTotalAmount='" + successTotalAmount + '\'' +
                ", failedTotalNum='" + failedTotalNum + '\'' +
                ", failedTotalAmount='" + failedTotalAmount + '\'' +
                ", feeType='" + feeType + '\'' +
                ", feeRatio='" + feeRatio + '\'' +
                ", feeTotalAmount='" + feeTotalAmount + '\'' +
                ", requestSource='" + requestSource + '\'' +
                ", finishTime=" + finishTime +
                ", batchFileName='" + batchFileName + '\'' +
                ", batchFilePath='" + batchFilePath + '\'' +
                ", heepayBatchNo='" + heepayBatchNo + '\'' +
                ", transWay='" + transWay + '\'' +
                ", recordSource='" + recordSource + '\'' +
                ", extend3='" + extend3 + '\'' +
                ", extend4='" + extend4 + '\'' +
                ", extend5='" + extend5 + '\'' +
                '}';
    }
}