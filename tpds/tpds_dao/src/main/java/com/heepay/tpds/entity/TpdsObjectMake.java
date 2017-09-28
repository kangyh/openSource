package com.heepay.tpds.entity;

import java.math.BigDecimal;

public class TpdsObjectMake {
    /**
     * 主键
     */
    private Long objId;

    /**
     * 业务操作类型(
            M01：修改银行密码
            M02：重置银行密码
            U01：客户注册
            U02：客户信息修改
            B01：客户绑卡
            B02：客户解绑
            B03：客户绑卡修改
            P01：标的发布
            P03：标的撤标
            P04：标的修改
            T01：投标
            T02: 取消投标
            T03：放款
            T04：还款
            T05：出款
            T07：受让
            T10：营销)
     */
    private String busiTradeType;

    /**
     * 业务流水号
     */
    private String businessSeqNo;

    /**
     * 标的编号
     */
    private String objectId;

    /**
     * 标的名称
     */
    private String objectName;

    /**
     * 标的状态(01:发布
            02:投资中
            03:放款
            05:撤标)
     */
    private String status;

    /**
     * 标的金额
     */
    private BigDecimal totalAmount;

    /**
     * 年化利率
     */
    private String interestRate;

    /**
     * 还款方式(01：一次性还本付息
            02：先息后本
            03：等额本息
            04：等额本金
            05：等本等息
            06：到期结清
            07：一次付息
            )
     */
    private String returnType;

    /**
     * 会员编号
     */
    private String customerId;

    /**
     * 标的台账账户
     */
    private String objectaccNo;

    /**
     * 客户台账账户
     */
    private String accNo;

    /**
     * 备注
     */
    private String note;

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public String getBusiTradeType() {
        return busiTradeType;
    }

    public void setBusiTradeType(String busiTradeType) {
        this.busiTradeType = busiTradeType == null ? null : busiTradeType.trim();
    }

    public String getBusinessSeqNo() {
        return businessSeqNo;
    }

    public void setBusinessSeqNo(String businessSeqNo) {
        this.businessSeqNo = businessSeqNo == null ? null : businessSeqNo.trim();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate == null ? null : interestRate.trim();
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getObjectaccNo() {
        return objectaccNo;
    }

    public void setObjectaccNo(String objectaccNo) {
        this.objectaccNo = objectaccNo == null ? null : objectaccNo.trim();
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo == null ? null : accNo.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}