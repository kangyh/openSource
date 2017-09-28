package com.heepay.prom.modules.prom.entity;

import com.heepay.prom.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

public class PromProfitOrder  extends DataEntity<PromProfitOrder>{
    private Long profitId;

    private String orderId;

    private String warrantyId;

    private String orderBatch;

    private String merchantId;

    private String merchantName;

    private BigDecimal profitMoney;

    private Date settleTime;

    private String settleBatch;

    private Date profitTime;

    private String operator;

    private String remark;
    
    private String[] profitIds;
    private Date beginDate;
    private Date endDate;
    
    public String[] getProfitIds() {
		return profitIds;
	}

	public void setProfitIds(String[] profitIds) {
		this.profitIds = profitIds;
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

	public Long getProfitId() {
        return profitId;
    }

    public void setProfitId(Long profitId) {
        this.profitId = profitId;
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