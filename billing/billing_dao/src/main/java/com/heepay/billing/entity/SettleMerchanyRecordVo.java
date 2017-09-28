package com.heepay.billing.entity;

import java.math.BigDecimal;

/**
 * 
 * 
 * 描 述：自定义通道侧接收查询数据对象
 *
 * 创 建 者：chenyanming 
 * 创建时间： 2016年9月10日下午3:48:06 
 * 创建描述：
 * 
 * 修 改 者： 
 * 修改时间： 
 * 修改描述：
 * 
 * 审 核 者：
 *  
 * 审核时间： 
 * 审核描述：
 *
 */
public class SettleMerchanyRecordVo {

	private String merchantId;
	private String productCode;
	private String merchantType;
	private int payNum;
	private BigDecimal requestAmountAmount;

	/*
	 * private Date checkTime; private Date settleTime; private String transNo;
	 * // 交易订单号 private String settleCyc; // 交易结算周期 private Date costTime; //
	 * 成本结算日期 private String costSettleCyc; // 成本结算周期
	 */

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public int getPayNum() {
		return payNum;
	}

	public void setPayNum(int payNum) {
		this.payNum = payNum;
	}

	public BigDecimal getRequestAmountAmount() {
		return requestAmountAmount;
	}

	public void setRequestAmountAmount(BigDecimal requestAmountAmount) {
		this.requestAmountAmount = requestAmountAmount;
	}

}
