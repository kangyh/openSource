package com.heepay.manage.modules.risk.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;
/**
 * 
 *
 * 描    述：风险订单实体类
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月22日 下午2:05:43
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class RiskOrder extends DataEntity<RiskOrder>{
    /**
	 * 2016年11月22日
	 */
	private static final long serialVersionUID = -4726489196177215503L;

	private Long riskId;

    private String riskNo;

    private String merchantOrderNo;

    private String transNo;
    
    private String paymentId;

    private Integer merchantId;

    private String merchantName;

    private BigDecimal transAmount;

    private String productCode;

    private String transType;

    private Date createTime;

    private Date updateTime;

    private String updateAuthor;

    private String orderDealwith;

    private String orderStatus;

    private String productName;

    private Long quotaId;

    private String quotaItem;
    
    private String quotaType;
    
    private String ruleDetailId;

    public Long getRiskId() {
        return riskId;
    }

    public void setRiskId(Long riskId) {
        this.riskId = riskId;
    }

    public String getRiskNo() {
        return riskNo;
    }

    public void setRiskNo(String riskNo) {
        this.riskNo = riskNo == null ? null : riskNo.trim();
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }
    
    public String getPaymentId() {
    	return paymentId;
    }
    
    public void setPaymentId(String paymentId) {
    	this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getOrderDealwith() {
        return orderDealwith;
    }

    public void setOrderDealwith(String orderDealwith) {
        this.orderDealwith = orderDealwith == null ? null : orderDealwith.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Long getQuotaId() {
        return quotaId;
    }

    public void setQuotaId(Long quotaId) {
        this.quotaId = quotaId;
    }

    public String getQuotaItem() {
        return quotaItem;
    }

    public void setQuotaItem(String quotaItem) {
        this.quotaItem = quotaItem == null ? null : quotaItem.trim();
    }
    
    public String getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(String quotaType) {
        this.quotaType = quotaType == null ? null : quotaType.trim();
    }
    
    public String getRuleDetailId() {
    	return ruleDetailId;
    }
    
    public void setRuleDetailId(String ruleDetailId) {
    	this.ruleDetailId = ruleDetailId == null ? null : ruleDetailId.trim();
    }
}