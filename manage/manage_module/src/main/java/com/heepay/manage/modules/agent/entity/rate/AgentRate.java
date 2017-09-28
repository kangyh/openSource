/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.entity.rate;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：设置代理商费率Entity
 *
 * 创 建 者： @author xch
 * 创建时间：
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
public class AgentRate extends DataEntity<AgentRate> {
	
	private static final long serialVersionUID = 1L;
	private String agentId;		// 代理商ID
	private String productCode;		// 产品编码
	private String productName;		// 产品名称
	private String profitPercent;		// 分润百分比
	private String transAmountBegin;		// 交易总额区间开始值
	private String transAmountEnd;		// 交易总额区间结束值
	private String defaultFeeType;		// 默认计费类型
	private String defaultFeePercent;		// 默认基本费率，百分比
	private String defaultFeeMin;		// 默认费用最小值，元
	private String defaultFeeMax;		// 默认费用最大值，元
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	
	public AgentRate() {
		super();
	}

	public AgentRate(String id){
		super(id);
	}

	@Length(min=1, max=11, message="代理商ID长度必须介于 1 和 11 之间")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	@Length(min=1, max=6, message="产品编码长度必须介于 1 和 6 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=1, max=50, message="产品名称长度必须介于 1 和 50 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=1, max=11, message="分润百分比长度必须介于 1 和 11 之间")
	public String getProfitPercent() {
		return profitPercent;
	}

	public void setProfitPercent(String profitPercent) {
		this.profitPercent = profitPercent;
	}
	
	@Length(min=1, max=11, message="交易总额区间开始值长度必须介于 1 和 11 之间")
	public String getTransAmountBegin() {
		return transAmountBegin;
	}

	public void setTransAmountBegin(String transAmountBegin) {
		this.transAmountBegin = transAmountBegin;
	}
	
	@Length(min=1, max=11, message="交易总额区间结束值长度必须介于 1 和 11 之间")
	public String getTransAmountEnd() {
		return transAmountEnd;
	}

	public void setTransAmountEnd(String transAmountEnd) {
		this.transAmountEnd = transAmountEnd;
	}
	
	@Length(min=1, max=6, message="默认计费类型长度必须介于 1 和 6 之间")
	public String getDefaultFeeType() {
		return defaultFeeType;
	}

	public void setDefaultFeeType(String defaultFeeType) {
		this.defaultFeeType = defaultFeeType;
	}
	
	public String getDefaultFeePercent() {
		return defaultFeePercent;
	}

	public void setDefaultFeePercent(String defaultFeePercent) {
		this.defaultFeePercent = defaultFeePercent;
	}
	
	@Length(min=1, max=11, message="默认费用最小值，元长度必须介于 1 和 11 之间")
	public String getDefaultFeeMin() {
		return defaultFeeMin;
	}

	public void setDefaultFeeMin(String defaultFeeMin) {
		this.defaultFeeMin = defaultFeeMin;
	}
	
	@Length(min=1, max=11, message="默认费用最大值，元长度必须介于 1 和 11 之间")
	public String getDefaultFeeMax() {
		return defaultFeeMax;
	}

	public void setDefaultFeeMax(String defaultFeeMax) {
		this.defaultFeeMax = defaultFeeMax;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}