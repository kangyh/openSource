package com.heepay.prom.modules.prom.entity;

import com.heepay.prom.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

public class PromSaleOrder extends DataEntity<PromSaleOrder>{
    private Long saleId;

    private String orderId;

    private String warrantyId;

    private String orderBatch;

    private String spreadId;

    private String productId;

    private String productName;

    private String merchantId;

    private String merchantName;

    private BigDecimal profitMoney;

    private Date settleTime;

    private String settleBatch;

    private Date profitTime;

    private String operator;

    private String remark;
    
    /**额外字段**/
    private String[] saleIds;//主键数组
    private Date beginDate;//分润时间范围
    private Date endDate;//分润时间范围
    public String[] getSaleIds() {
		return saleIds;
	}

	public void setSaleIds(String[] saleIds) {
		this.saleIds = saleIds;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
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

	public String getOrderBatch() {
        return orderBatch;
    }

    public void setOrderBatch(String orderBatch) {
        this.orderBatch = orderBatch == null ? null : orderBatch.trim();
    }

    public String getSpreadId() {
        return spreadId;
    }

    public void setSpreadId(String spreadId) {
        this.spreadId = spreadId == null ? null : spreadId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public BigDecimal getProfitMoney() {
		return profitMoney;
	}

	public void setProfitMoney(BigDecimal profitMoney) {
		this.profitMoney = profitMoney;
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
        this.settleBatch = settleBatch == null ? null : settleBatch.trim();
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
        this.operator = operator == null ? null : operator.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}