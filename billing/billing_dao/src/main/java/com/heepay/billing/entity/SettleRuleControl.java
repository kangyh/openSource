package com.heepay.billing.entity;

import java.util.Date;

public class SettleRuleControl {
    /**
     * 规则ID
     */
    private Long ruleControlId;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 业务类型
     */
    private String channelType;

    /**
     * 账单类型（EXCEL：EXCEL  TXT：TXT  OTHER：OTHER）
     */
    private String billType;

    /**
     * 支付单号的位置
     */
    private Byte paymentId;

    /**
     * 上游流水的位置
     */
    private Byte channelNo;

    /**
     * 成本的位置
     */
    private Byte costAmount;

    /**
     * 通道金额的位置
     */
    private Byte successAmount;

    /**
     * 交易状态的位置
     */
    private Byte transStatus;

    /**
     * 分隔符的位置
     */
    private String splitFlg;

    /**
     * 开始行的位置
     */
    private Byte beginRowNum;

    /**
     * 结束标识的位置
     */
    private String endFlg;

    /**
     * 状态：Y:有效，N:无效
     */
    private String status;

    /**
     * 优惠金额的位置
     */
    private Byte promotionAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createAuthor;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateAuthor;

    /**
     * 对账方式：handle（手工对账）auto（自动对账）
     */
    private String settleWay;

    public Long getRuleControlId() {
        return ruleControlId;
    }

    public void setRuleControlId(Long ruleControlId) {
        this.ruleControlId = ruleControlId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public Byte getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Byte paymentId) {
        this.paymentId = paymentId;
    }

    public Byte getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Byte channelNo) {
        this.channelNo = channelNo;
    }

    public Byte getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(Byte costAmount) {
        this.costAmount = costAmount;
    }

    public Byte getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(Byte successAmount) {
        this.successAmount = successAmount;
    }

    public Byte getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Byte transStatus) {
        this.transStatus = transStatus;
    }

    public String getSplitFlg() {
        return splitFlg;
    }

    public void setSplitFlg(String splitFlg) {
        this.splitFlg = splitFlg == null ? null : splitFlg.trim();
    }

    public Byte getBeginRowNum() {
        return beginRowNum;
    }

    public void setBeginRowNum(Byte beginRowNum) {
        this.beginRowNum = beginRowNum;
    }

    public String getEndFlg() {
        return endFlg;
    }

    public void setEndFlg(String endFlg) {
        this.endFlg = endFlg == null ? null : endFlg.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Byte getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(Byte promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(String createAuthor) {
        this.createAuthor = createAuthor == null ? null : createAuthor.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

    public String getSettleWay() {
        return settleWay;
    }

    public void setSettleWay(String settleWay) {
        this.settleWay = settleWay == null ? null : settleWay.trim();
    }
}