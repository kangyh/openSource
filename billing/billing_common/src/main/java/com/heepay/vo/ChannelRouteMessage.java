package com.heepay.vo;

import java.util.Date;


/***
 * 
* 
* 描    述：通道端路由信息
*
* 创 建 者： xuangang
* 创建时间：  2016年9月14日上午11:47:24
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
public class ChannelRouteMessage {
	
	private String channelCode; // 支付通道代码
	private String channelName; // 支付通道名称
	private String bankName; // 银行名称
	private String bankNo; // 银行代码
	private String channelPartnerName; // 通道合作方
	private String channelPartnerCode; // 通道合作方代码
	private String channelTypeName; // 支付通道类型
	private String channelTypeCode; // 支付通道类型代码
	private String cardTypeName; // 银行卡类型
	private String cardTypeCode; // 银行卡类型代码
	private String accountType; // 账户类型代码
	private String businessType; // 业务类型代码
	private Date effectStartDate; // 有效开始时间
	private Date effectEndDate; // 有效结束时间
	private String costType; // 成本类型
	private String costRate; // 成本按比例
	private String costCount; // 成本按笔数
	private String status; // 状态
	private Date contractDate; // 合约时间
	private String settleType; // 成本扣除方式
	private String settlePeriod; // 成本结算周期
	private String sort; // 优先级别
	private String perlimitAmount; // 单笔限额
	private String daylimitAmount; // 单日限额
	private String monlimitAmount; // 单月限额
	private String merchantId; // 商户ID
	private String productDetailId;
	private String productCode;
	
	private String orderSettlePeriod;//通道结算周期
	private String chargeDeductType; // 手续费扣除方式
	
	public String getChargeDeductType() {
		return chargeDeductType;
	}
	public void setChargeDeductType(String chargeDeductType) {
		this.chargeDeductType = chargeDeductType;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getChannelPartnerName() {
		return channelPartnerName;
	}
	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}
	public String getChannelPartnerCode() {
		return channelPartnerCode;
	}
	public void setChannelPartnerCode(String channelPartnerCode) {
		this.channelPartnerCode = channelPartnerCode;
	}
	public String getChannelTypeName() {
		return channelTypeName;
	}
	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	public String getChannelTypeCode() {
		return channelTypeCode;
	}
	public void setChannelTypeCode(String channelTypeCode) {
		this.channelTypeCode = channelTypeCode;
	}
	public String getCardTypeName() {
		return cardTypeName;
	}
	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
	public String getCardTypeCode() {
		return cardTypeCode;
	}
	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Date getEffectStartDate() {
		return effectStartDate;
	}
	public void setEffectStartDate(Date effectStartDate) {
		this.effectStartDate = effectStartDate;
	}
	public Date getEffectEndDate() {
		return effectEndDate;
	}
	public void setEffectEndDate(Date effectEndDate) {
		this.effectEndDate = effectEndDate;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public String getCostRate() {
		return costRate;
	}
	public void setCostRate(String costRate) {
		this.costRate = costRate;
	}
	public String getCostCount() {
		return costCount;
	}
	public void setCostCount(String costCount) {
		this.costCount = costCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	public String getSettlePeriod() {
		return settlePeriod;
	}
	public void setSettlePeriod(String settlePeriod) {
		this.settlePeriod = settlePeriod;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getPerlimitAmount() {
		return perlimitAmount;
	}
	public void setPerlimitAmount(String perlimitAmount) {
		this.perlimitAmount = perlimitAmount;
	}
	public String getDaylimitAmount() {
		return daylimitAmount;
	}
	public void setDaylimitAmount(String daylimitAmount) {
		this.daylimitAmount = daylimitAmount;
	}
	public String getMonlimitAmount() {
		return monlimitAmount;
	}
	public void setMonlimitAmount(String monlimitAmount) {
		this.monlimitAmount = monlimitAmount;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getProductDetailId() {
		return productDetailId;
	}
	public void setProductDetailId(String productDetailId) {
		this.productDetailId = productDetailId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getOrderSettlePeriod() {
		return orderSettlePeriod;
	}
	public void setOrderSettlePeriod(String orderSettlePeriod) {
		this.orderSettlePeriod = orderSettlePeriod;
	}
	

}
