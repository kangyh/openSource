package com.heepay.tpds.entity;

import java.math.BigDecimal;

public class TpdsObjectTransDetail {
    /**
     * 主键
     */
    private Long accId;

    /**
     * 序号
     */
    private String oderNo;

    /**
     * 借方台账账户
     */
    private String debitAccountNo;

    /**
     * 贷方台账账户
     */
    private String cebitAccountNo;

    /**
     * 币种(CNY:人民币)
     */
    private String currency;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 其他金额类型(01：费用
            02：标的收益
            03:提现手续费
            )
     */
    private String otherAmounttype;

    /**
     * 其他金额
     */
    private BigDecimal otherAmount;

    /**
     * 标的编号
     */
    private String objectId;

    /**
     * 备注
     */
    private String note;

    public Long getAccId() {
        return accId;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }

    public String getOderNo() {
        return oderNo;
    }

    public void setOderNo(String oderNo) {
        this.oderNo = oderNo == null ? null : oderNo.trim();
    }

    public String getDebitAccountNo() {
        return debitAccountNo;
    }

    public void setDebitAccountNo(String debitAccountNo) {
        this.debitAccountNo = debitAccountNo == null ? null : debitAccountNo.trim();
    }

    public String getCebitAccountNo() {
        return cebitAccountNo;
    }

    public void setCebitAccountNo(String cebitAccountNo) {
        this.cebitAccountNo = cebitAccountNo == null ? null : cebitAccountNo.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOtherAmounttype() {
        return otherAmounttype;
    }

    public void setOtherAmounttype(String otherAmounttype) {
        this.otherAmounttype = otherAmounttype == null ? null : otherAmounttype.trim();
    }

    public BigDecimal getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        this.otherAmount = otherAmount;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}