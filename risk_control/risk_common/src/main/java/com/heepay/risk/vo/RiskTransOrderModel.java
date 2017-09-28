package com.heepay.risk.vo;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-08-11 11:22
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
public class RiskTransOrderModel {
    private String merchantOrderNo;
    private String transNo;
    private String productType;
    private String productName;
    private String merchantId;
    private String merchantCompany;
    private String transType;
    private String transAmount;
    private String bankCardType;
    private String bankCardNo;
    private String bankCardOwnerName;
    private String bankCardOwnerIdCard;
    private String bankCardOwnerMobile;
    private String channelCode;
    private String channelName;
    private String channelTransType;
    private String feeType;
    private String bankCardOwnerType;
    private String createTime;
    private String transferBatchAmount;
    private String feeAmount;
    private String reqIp;

    public String getMerchantOrderNo() {
        if(StringUtil.isBlank(merchantOrderNo))
            return "null";
        else
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getTransNo() {
        if(StringUtil.isBlank(transNo))
            return "null";
        else
            return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getProductType() {
        if(StringUtil.isBlank(productType))
            return "null";
        else
            return productType;

    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        if(StringUtil.isBlank(productName))
            return "null";
        else
            return productName;

    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMerchantId() {
        if(StringUtil.isBlank(merchantId))
            return "null";
        else
            return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantCompany() {
        if(StringUtil.isBlank(merchantCompany))
            return "null";
        else
            return merchantCompany;
    }

    public void setMerchantCompany(String merchantCompany) {
        this.merchantCompany = merchantCompany;
    }

    public String getTransType() {
        if(StringUtil.isBlank(transType))
            return "null";
        else
            return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransAmount() {
        if(StringUtil.isBlank(transAmount))
            return "0";
        else
            return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getBankCardType() {
        if(StringUtil.isBlank(bankCardType))
            return "null";
        else
            return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getBankCardNo() {
        if(StringUtil.isBlank(bankCardNo))
            return "null";
        else
            return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCardOwnerName() {
        if(StringUtil.isBlank(bankCardOwnerName))
            return "null";
        else
            return bankCardOwnerName;
    }

    public void setBankCardOwnerName(String bankCardOwnerName) {
        this.bankCardOwnerName = bankCardOwnerName;
    }

    public String getBankCardOwnerIdCard() {
        if(StringUtil.isBlank(bankCardOwnerIdCard))
            return "null";
        else
            return bankCardOwnerIdCard;
    }

    public void setBankCardOwnerIdCard(String bankCardOwnerIdCard) {
        this.bankCardOwnerIdCard = bankCardOwnerIdCard;
    }

    public String getBankCardOwnerMobile() {
        if(StringUtil.isBlank(bankCardOwnerMobile))
            return "null";
        else
            return bankCardOwnerMobile;
    }

    public void setBankCardOwnerMobile(String bankCardOwnerMobile) {
        this.bankCardOwnerMobile = bankCardOwnerMobile;
    }

    public String getChannelCode() {
        if(StringUtil.isBlank(channelCode))
            return "null";
        else
            return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        if(StringUtil.isBlank(channelName))
            return "null";
        else
            return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelTransType() {
        if(StringUtil.isBlank(channelTransType))
            return "null";
        else
            return channelTransType;
    }

    public void setChannelTransType(String channelTransType) {
        this.channelTransType = channelTransType;
    }

    public String getFeeType() {
        if(StringUtil.isBlank(feeType))
            return "null";
        else
            return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getBankCardOwnerType() {
        if(StringUtil.isBlank(bankCardOwnerType))
            return "null";
        else
            return bankCardOwnerType;
    }

    public void setBankCardOwnerType(String bankCardOwnerType) {
        this.bankCardOwnerType = bankCardOwnerType;
    }

    public String getCreateTime() {
        if(StringUtil.isBlank(createTime))
            return "null";
        else
            return String.valueOf(DateUtil.stringToDate(createTime,"yyyyMMddHHmmss").getTime());
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTransferBatchAmount() {
        if(StringUtil.isBlank(transferBatchAmount))
            return "0";
        else
            return transferBatchAmount;
    }

    public void setTransferBatchAmount(String transferBatchAmount) {
        this.transferBatchAmount = transferBatchAmount;
    }

    public String getFeeAmount() {
        if(StringUtil.isBlank(feeAmount))
            return "0";
        else
            return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getReqIp() {
        if(StringUtil.isBlank(reqIp))
            return "null";
        else
            return reqIp;
    }

    public void setReqIp(String reqIp) {
        this.reqIp = reqIp;
    }


}
