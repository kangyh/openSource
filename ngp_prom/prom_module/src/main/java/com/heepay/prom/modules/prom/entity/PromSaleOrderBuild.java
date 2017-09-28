package com.heepay.prom.modules.prom.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 李震
 *
 */
public class PromSaleOrderBuild {
	

    private String promId;
	private String orderId;

    private String warrantyId;

    private String orderBatch;

    private String spreadId;

    private String productId;

    private String productName;

    private String merchantId;

    private String merchantName;

    private Date settleTime;

    private String settleBatch;

    private Date profitTime;

    private String operator;

    private String remark;
    
    private String isProfit;

    private BigDecimal huiyuanProfit;//汇元分润
    private BigDecimal gearProfit;//推广者分润
    private BigDecimal refereeProfit;//推荐人分润
    private BigDecimal higherMasterProfit;//师父分润
    private BigDecimal highestMasterProfit;//师爷分润
    
    private BigDecimal huiyuanScale;//汇元分润比例
    private BigDecimal gearScale;//推广者分润比例
    private BigDecimal refereeScale;//推荐人分润比例
    private BigDecimal higherMasterScale;//师父分润比例
    private BigDecimal highestMasterScale;//师爷分润比例
    
    private String higherMerId;//上级商户编号
    private String highestMerId;//上上级商户编号
    private String higherMerName;//上级商户名称
    private String highestMerName;//上上级商户名称
    
	public String getHigherMerName() {
		return higherMerName;
	}
	public void setHigherMerName(String higherMerName) {
		this.higherMerName = higherMerName;
	}
	public String getHighestMerName() {
		return highestMerName;
	}
	public void setHighestMerName(String highestMerName) {
		this.highestMerName = highestMerName;
	}
	public String getHigherMerId() {
		return higherMerId;
	}
	public void setHigherMerId(String higherMerId) {
		this.higherMerId = higherMerId;
	}
	public String getHighestMerId() {
		return highestMerId;
	}
	public void setHighestMerId(String highestMerId) {
		this.highestMerId = highestMerId;
	}
	public String getPromId() {
		return promId;
	}
	public void setPromId(String promId) {
		this.promId = promId;
	}
	public String getIsProfit() {
		return isProfit;
	}
	public void setIsProfit(String isProfit) {
		this.isProfit = isProfit;
	}
	public String getOrderBatch() {
		return orderBatch;
	}
	public void setOrderBatch(String orderBatch) {
		this.orderBatch = orderBatch;
	}
	public String getSpreadId() {
		return spreadId;
	}
	public void setSpreadId(String spreadId) {
		this.spreadId = spreadId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Date getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}
	public String getSettleBatch() {
		return settleBatch;
	}
	public void setSettleBatch(String settleBatch) {
		this.settleBatch = settleBatch;
	}
	public Date getProfitTime() {
		return profitTime;
	}
	public void setProfitTime(Date profitTime) {
		this.profitTime = profitTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getHuiyuanProfit() {
		return huiyuanProfit;
	}
	public void setHuiyuanProfit(BigDecimal huiyuanProfit) {
		this.huiyuanProfit = huiyuanProfit;
	}
	public BigDecimal getGearProfit() {
		return gearProfit;
	}
	public void setGearProfit(BigDecimal gearProfit) {
		this.gearProfit = gearProfit;
	}
	public BigDecimal getRefereeProfit() {
		return refereeProfit;
	}
	public void setRefereeProfit(BigDecimal refereeProfit) {
		this.refereeProfit = refereeProfit;
	}
	public BigDecimal getHigherMasterProfit() {
		return higherMasterProfit;
	}
	public void setHigherMasterProfit(BigDecimal higherMasterProfit) {
		this.higherMasterProfit = higherMasterProfit;
	}
	public BigDecimal getHighestMasterProfit() {
		return highestMasterProfit;
	}
	public void setHighestMasterProfit(BigDecimal highestMasterProfit) {
		this.highestMasterProfit = highestMasterProfit;
	}
	public BigDecimal getHuiyuanScale() {
		return huiyuanScale;
	}
	public void setHuiyuanScale(BigDecimal huiyuanScale) {
		this.huiyuanScale = huiyuanScale;
	}
	public BigDecimal getGearScale() {
		return gearScale;
	}
	public void setGearScale(BigDecimal gearScale) {
		this.gearScale = gearScale;
	}
	public BigDecimal getRefereeScale() {
		return refereeScale;
	}
	public void setRefereeScale(BigDecimal refereeScale) {
		this.refereeScale = refereeScale;
	}
	public BigDecimal getHigherMasterScale() {
		return higherMasterScale;
	}
	public void setHigherMasterScale(BigDecimal higherMasterScale) {
		this.higherMasterScale = higherMasterScale;
	}
	public BigDecimal getHighestMasterScale() {
		return highestMasterScale;
	}
	public void setHighestMasterScale(BigDecimal highestMasterScale) {
		this.highestMasterScale = highestMasterScale;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getWarrantyId() {
		return warrantyId;
	}
	public void setWarrantyId(String warrantyId) {
		this.warrantyId = warrantyId;
	}
    
    
}
