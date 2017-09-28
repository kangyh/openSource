/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：聚合通道Entity
 *
 * 创 建 者： @author 马振
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
public class IntegrationChannel extends DataEntity<IntegrationChannel> {
	
	private static final long serialVersionUID = 1L;
	private String channelKey;		// 通道Key
	private String channelName;		// 通道名称
	private String providerCode;		// 通道商编码
	private String payType;		// 支付类型
	private String sceneKey;		// 场景Key
	private String bankId;		// 银行ID
	private String bankProvider;		// 银行支付提供者
	private String bankCardType;		// 卡类型
	private String costIsByBill;		// 是否按笔成本
	private String costRate;		// 成本费率
	private String minCostFee;		// 最小成本金额
	private String maxCostFee;		// 最大成本金额
	private String isByBill;		// 是否按笔收费
	private String oneBillFee;		// 按笔费率金额
	private String minBillFee;		// 按笔最小费率金额
	private String maxBillFee;		// 按笔最大费率金额
	private String feeRate;		// 按金额结算费率
	private String minRate;		// 按金额最小结算费率
	private String maxRate;		// 按金额最大结算费率
	private String note;		// 备注
	private String status;		// 状态
	private String mappingId;   //关联表ID号
	private String channelCode;
	private String subMerchantNo; //子商户号

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public IntegrationChannel() {
		super();
	}

	public IntegrationChannel(String id){
		super(id);
	}

	public String getSubMerchantNo() {
		return subMerchantNo;
	}

	public void setSubMerchantNo(String subMerchantNo) {
		this.subMerchantNo = subMerchantNo;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	@Length(min=0, max=100, message="通道Key长度必须介于 0 和 100 之间")
	public String getChannelKey() {
		return channelKey;
	}

	@JsonProperty("ChannelKey")
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	
	@Length(min=0, max=100, message="通道名称长度必须介于 0 和 100 之间")
	public String getChannelName() {
		return channelName;
	}

	@JsonProperty("ChannelName")
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Length(min=0, max=100, message="通道商编码长度必须介于 0 和 100 之间")
	public String getProviderCode() {
		return providerCode;
	}

	@JsonProperty("ProviderCode")
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	
	@Length(min=0, max=100, message="支付类型长度必须介于 0 和 100 之间")
	public String getPayType() {
		return payType;
	}

	@JsonProperty("PayType")
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=100, message="场景Key长度必须介于 0 和 100 之间")
	public String getSceneKey() {
		return sceneKey;
	}

	@JsonProperty("SceneKey")
	public void setSceneKey(String sceneKey) {
		this.sceneKey = sceneKey;
	}
	
	@Length(min=0, max=20, message="银行ID长度必须介于 0 和 20 之间")
	public String getBankId() {
		return bankId;
	}

	@JsonProperty("BankID")
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=0, max=100, message="银行支付提供者长度必须介于 0 和 100 之间")
	public String getBankProvider() {
		return bankProvider;
	}

	@JsonProperty("BankProvider")
	public void setBankProvider(String bankProvider) {
		this.bankProvider = bankProvider;
	}
	
	@Length(min=0, max=100, message="卡类型长度必须介于 0 和 100 之间")
	public String getBankCardType() {
		return bankCardType;
	}

	@JsonProperty("BankCardType")
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	
	@Length(min=0, max=100, message="是否按笔成本长度必须介于 0 和 100 之间")
	public String getCostIsByBill() {
		return costIsByBill;
	}

	@JsonProperty("CostIsByBill")
	public void setCostIsByBill(String costIsByBill) {
		this.costIsByBill = costIsByBill;
	}
	
	@Length(min=0, max=100, message="成本费率长度必须介于 0 和 100 之间")
	public String getCostRate() {
		return costRate;
	}

	@JsonProperty("CostRate")
	public void setCostRate(String costRate) {
		this.costRate = costRate;
	}
	
	public String getMinCostFee() {
		return minCostFee;
	}

	@JsonProperty("MinCostFee")
	public void setMinCostFee(String minCostFee) {
		this.minCostFee = minCostFee;
	}
	
	public String getMaxCostFee() {
		return maxCostFee;
	}

	@JsonProperty("MaxCostFee")
	public void setMaxCostFee(String maxCostFee) {
		this.maxCostFee = maxCostFee;
	}
	
	@Length(min=0, max=100, message="是否按笔收费长度必须介于 0 和 100 之间")
	public String getIsByBill() {
		return isByBill;
	}

	@JsonProperty("IsByBill")
	public void setIsByBill(String isByBill) {
		this.isByBill = isByBill;
	}
	
	public String getOneBillFee() {
		return oneBillFee;
	}

	@JsonProperty("OneBillFee")
	public void setOneBillFee(String oneBillFee) {
		this.oneBillFee = oneBillFee;
	}
	
	public String getMinBillFee() {
		return minBillFee;
	}

	@JsonProperty("MinBillFee")
	public void setMinBillFee(String minBillFee) {
		this.minBillFee = minBillFee;
	}
	
	public String getMaxBillFee() {
		return maxBillFee;
	}

	@JsonProperty("MaxBillFee")
	public void setMaxBillFee(String maxBillFee) {
		this.maxBillFee = maxBillFee;
	}
	
	public String getFeeRate() {
		return feeRate;
	}

	@JsonProperty("FeeRate")
	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}
	
	public String getMinRate() {
		return minRate;
	}

	@JsonProperty("MinRate")
	public void setMinRate(String minRate) {
		this.minRate = minRate;
	}
	
	public String getMaxRate() {
		return maxRate;
	}

	@JsonProperty("MaxRate")
	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}
	
	public String getNote() {
		return note;
	}

	@JsonProperty("Note")
	public void setNote(String note) {
		this.note = note;
	}
	
	@Length(min=0, max=20, message="状态长度必须介于 0 和 20 之间")
	public String getStatus() {
		return status;
	}

	@JsonProperty("Status")
	public void setStatus(String status) {
		this.status = status;
	}
	
}