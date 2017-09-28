package com.heepay.risk.service;


import java.math.BigDecimal;

import com.heepay.risk.vo.ProductQuotaResultVO;
import com.heepay.rpc.risk.model.TransOrderRiskModel;

public abstract class  IProductQuota {
      public  abstract ProductQuotaResultVO ComputeQuota(TransOrderRiskModel vo);
      public BigDecimal getQuotaValue() {
		return quotaValue;
	}
	public void setQuotaValue(BigDecimal quotaValue) {
		this.quotaValue = quotaValue;
	}
	private  BigDecimal quotaValue;    
}
