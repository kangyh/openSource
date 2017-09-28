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
 * 描    述：转账审核Entity
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
public class Transferapply extends DataEntity<Transferapply> {

    private static final long serialVersionUID = 1L;
    private String merchantId;        // 商户id
    private String orderId;        // 订单id
    private String amount;        // 转账金额
    private String transferType;        // 转账类型
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间
    private String applyStatus;        // 申请状态
    private String auditor;        // 审核人
    private String rejectReason;        // 审核拒绝理由
    private String extend1;        // extend1
    private String extend2;        // extend2
    private String extend3;        // extend3
    private Date beginCreateTime;        // 开始 创建时间
    private Date endCreateTime;        // 结束 创建时间

    public Transferapply() {
        super();
    }

    public Transferapply(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "商户id长度必须介于 0 和 64 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Length(min = 0, max = 64, message = "订单id长度必须介于 0 和 64 之间")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Length(min = 0, max = 64, message = "转账金额长度必须介于 0 和 64 之间")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Length(min = 0, max = 6, message = "转账类型长度必须介于 0 和 6 之间")
    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
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

    @Length(min = 0, max = 6, message = "申请状态长度必须介于 0 和 6 之间")
    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    @Length(min = 0, max = 64, message = "审核人长度必须介于 0 和 64 之间")
    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    @Length(min = 0, max = 1024, message = "审核拒绝理由长度必须介于 0 和 1024 之间")
    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Length(min = 0, max = 64, message = "extend1长度必须介于 0 和 64 之间")
    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    @Length(min = 0, max = 64, message = "extend2长度必须介于 0 和 64 之间")
    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    @Length(min = 0, max = 64, message = "extend3长度必须介于 0 和 64 之间")
    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
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
        return "Transferapply{" +
                "merchantId='" + merchantId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", amount='" + amount + '\'' +
                ", transferType='" + transferType + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", applyStatus='" + applyStatus + '\'' +
                ", auditor='" + auditor + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                ", beginCreateTime=" + beginCreateTime +
                ", endCreateTime=" + endCreateTime +
                '}';
    }
}