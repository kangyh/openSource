
package com.heepay.engine;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.risk.ProductQuotaType;
import com.heepay.enums.risk.QuotaType;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月17日 下午2:05:18
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
public abstract class AbstractRiskFact {
	private InterfaceStatus message ; 
	private long quotaId; //规则ID编号
	private ProductQuotaType productQuotaType; 
	private QuotaType quotaType;
	private String ruleCode;
	public void setMessage(InterfaceStatus message)
	{
		this.message = message;
	}
	public InterfaceStatus getMessage()
	{
		return message;
	}
	public long getQuotaId() {
		return quotaId;
	}
	public void setQuotaId(long quotaId) {
		this.quotaId = quotaId;
	}
	public ProductQuotaType getProductQuotaType() {
		return productQuotaType;
	}
	public void setProductQuotaType(ProductQuotaType productQuotaType) {
		this.productQuotaType = productQuotaType;
	}
	public QuotaType getQuotaType() {
		return quotaType;
	}
	public void setQuotaType(QuotaType quotaType) {
		this.quotaType = quotaType;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
}
