package com.heepay.risk.vo;

import java.math.BigDecimal;
import java.util.Date;

public class RiskIncomeVo {
	 private Long quotaId;

	    private Integer merchantId;

	    private BigDecimal dayIncomeQuotaAmount;

	    private String incomeDirection;

	    private String createAuthor;

	    private String createTime;

	    private String updateAuthor;

	    private String updateTime;

	    private String status;

	    public Long getQuotaId() {
	        return quotaId;
	    }

	    public void setQuotaId(Long quotaId) {
	        this.quotaId = quotaId;
	    }

	    public Integer getMerchantId() {
	        return merchantId;
	    }

	    public void setMerchantId(Integer merchantId) {
	        this.merchantId = merchantId;
	    }

	    public BigDecimal getDayIncomeQuotaAmount() {
	        return dayIncomeQuotaAmount;
	    }

	    public void setDayIncomeQuotaAmount(BigDecimal dayIncomeQuotaAmount) {
	        this.dayIncomeQuotaAmount = dayIncomeQuotaAmount;
	    }

	    public String getIncomeDirection() {
	        return incomeDirection;
	    }

	    public void setIncomeDirection(String incomeDirection) {
	        this.incomeDirection = incomeDirection == null ? null : incomeDirection.trim();
	    }

	    public String getCreateAuthor() {
	        return createAuthor;
	    }

	    public void setCreateAuthor(String createAuthor) {
	        this.createAuthor = createAuthor == null ? null : createAuthor.trim();
	    }

	   

	    public String getUpdateAuthor() {
	        return updateAuthor;
	    }

	    public void setUpdateAuthor(String updateAuthor) {
	        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
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
