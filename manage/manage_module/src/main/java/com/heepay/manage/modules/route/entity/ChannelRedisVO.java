/**
 * 
 */
package com.heepay.manage.modules.route.entity;

/**
 * <p>Title:ChannelRedisVO.java</p> 
 * <p>Description:放入缓存通道VO</p>
 * <p>Company:hy</p>
 * @author M.Z
 * @date 2016年8月17日 上午10:56:27
 */
public class ChannelRedisVO{

	private String channelCode;		// 支付通道代码
	private String costType;		// 成本类型
	private String costRate;		// 成本按比例
	private String costCount;    // 成本按笔数
	private String sort;		// 优先级别
	private String merchantId;		// 商家ID
	private String bankNo;   // 银行代码
	private String channelTypeCode;    // 支付通道类型代码
	private String cardTypeCode;   // 银行卡类型代码
	private String channelPartnerCode;   // 通道合作方代码
	private String accountType;   // 账户类型代码
	private String businessType;   // 业务类型代码
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getChannelTypeCode() {
		return channelTypeCode;
	}
	public void setChannelTypeCode(String channelTypeCode) {
		this.channelTypeCode = channelTypeCode;
	}
	public String getCardTypeCode() {
		return cardTypeCode;
	}
	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}
	public String getChannelPartnerCode() {
		return channelPartnerCode;
	}
	public void setChannelPartnerCode(String channelPartnerCode) {
		this.channelPartnerCode = channelPartnerCode;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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

	
}
