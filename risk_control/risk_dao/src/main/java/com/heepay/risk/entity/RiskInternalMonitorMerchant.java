/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.risk.entity;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * 描    述：内部监控商户配制表Entity
 *
 * 创 建 者： @author lizhen
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
public class RiskInternalMonitorMerchant    implements Serializable  {
	
	private String internalMerchantId;		// 主建
	private String merchantId;		// 商户编码
	private String merchantName;		// 商户名称
	private String productCode;		// 产品编码
	private String productName;		// 产品名称
	private String beginTime;		// 开始时间
	private String endTime;		// 结束时间
	private Integer frequency;		// 频率（分钟）
	private Integer threshold;		// 阈值（%）
	private String operator;		// 操作人
	private Date createTime;		// 创建时间
	private Integer changeRate;//变化率

	public RiskInternalMonitorMerchant() {
		super();
	}

	
	public String getInternalMerchantId() {
		return internalMerchantId;
	}

	public void setInternalMerchantId(String internalMerchantId) {
		this.internalMerchantId = internalMerchantId;
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
	

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(Integer changeRate) {
		this.changeRate = changeRate;
	}
}