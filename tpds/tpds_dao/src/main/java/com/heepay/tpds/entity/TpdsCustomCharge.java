package com.heepay.tpds.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TpdsCustomCharge {
    /**
     * 主键
     */
    private Long cusId;

    /**
     * 业务流水号
     */
    private String businessSeqNo;

    /**
     * 订单流水号
     */
    private String businessOrderNo;

    /**
     * 充值/提现类型(R01：客户代扣充值
            R02：客户网银充值
            R03：营销充值
            R04: 代偿充值
            R05：费用充值
            R06：垫资充值
            R07: 线下充值
            R08: 快捷充值申请
            R09: 快捷充值确认
            W01：客户提现
            W02：营销提现
            W03: 代偿提现
            W04：费用提现
            W05：垫资提现
            W06：银行提现（监管银行日终清算交付）
            )
     */
    private String rType;

    /**
     * 委托标识
     */
    private String entrustflag;

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
     * 支付方式(00:银行支付渠道
            01：第三方支付渠道
            )
     */
    private String payType;

    /**
     * 支付公司代码
     */
    private String busiBranchNo;

    /**
     * 银行卡号
     */
    private String bankAccountNo;

    /**
     * 二类户账户
     */
    private String secBankaccNo;

    /**
     * 银行编码
     */
    private String bankId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 卡类型
            SAVING 储蓄卡
            CREDIT 信用卡
     */
    private String cardType;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getCusId() {
        return cusId;
    }

    public void setCusId(Long cusId) {
        this.cusId = cusId;
    }

    public String getBusinessSeqNo() {
        return businessSeqNo;
    }

    public void setBusinessSeqNo(String businessSeqNo) {
        this.businessSeqNo = businessSeqNo == null ? null : businessSeqNo.trim();
    }

    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo == null ? null : businessOrderNo.trim();
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType == null ? null : rType.trim();
    }

    public String getEntrustflag() {
        return entrustflag;
    }

    public void setEntrustflag(String entrustflag) {
        this.entrustflag = entrustflag == null ? null : entrustflag.trim();
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getBusiBranchNo() {
        return busiBranchNo;
    }

    public void setBusiBranchNo(String busiBranchNo) {
        this.busiBranchNo = busiBranchNo == null ? null : busiBranchNo.trim();
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo == null ? null : bankAccountNo.trim();
    }

    public String getSecBankaccNo() {
        return secBankaccNo;
    }

    public void setSecBankaccNo(String secBankaccNo) {
        this.secBankaccNo = secBankaccNo == null ? null : secBankaccNo.trim();
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}