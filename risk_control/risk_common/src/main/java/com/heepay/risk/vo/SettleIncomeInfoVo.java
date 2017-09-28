package com.heepay.risk.vo;

import java.util.Date;

public class SettleIncomeInfoVo {
	 private Long incomeId;

	    private String incomeDirection;

	    private String businessType;

	    private String transType;

	    private String productCode;

	    private String settleStatus;

	    private String createTime;

	    private String creator;

	    private String updateTime;

	    private String modifier;

	    private String remark;

	    private String status;

	    public Long getIncomeId() {
	        return incomeId;
	    }

	    public void setIncomeId(Long incomeId) {
	        this.incomeId = incomeId;
	    }

	    public String getIncomeDirection() {
	        return incomeDirection;
	    }

	    public void setIncomeDirection(String incomeDirection) {
	        this.incomeDirection = incomeDirection == null ? null : incomeDirection.trim();
	    }

	    public String getBusinessType() {
	        return businessType;
	    }

	    public void setBusinessType(String businessType) {
	        this.businessType = businessType == null ? null : businessType.trim();
	    }

	    public String getTransType() {
	        return transType;
	    }

	    public void setTransType(String transType) {
	        this.transType = transType == null ? null : transType.trim();
	    }

	    public String getProductCode() {
	        return productCode;
	    }

	    public void setProductCode(String productCode) {
	        this.productCode = productCode == null ? null : productCode.trim();
	    }

	    public String getSettleStatus() {
	        return settleStatus;
	    }

	    public void setSettleStatus(String settleStatus) {
	        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
	    }

	 

	    public String getCreator() {
	        return creator;
	    }

	    public void setCreator(String creator) {
	        this.creator = creator == null ? null : creator.trim();
	    }

	 

	    public String getModifier() {
	        return modifier;
	    }

	    public void setModifier(String modifier) {
	        this.modifier = modifier == null ? null : modifier.trim();
	    }

	    public String getRemark() {
	        return remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark == null ? null : remark.trim();
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status == null ? null : status.trim();
	    }

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

}
