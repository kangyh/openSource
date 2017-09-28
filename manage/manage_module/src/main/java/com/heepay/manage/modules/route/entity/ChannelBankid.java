/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.entity;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：通道bankid关联Entity
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
public class ChannelBankid extends DataEntity<ChannelBankid> {
	
	private static final long serialVersionUID = 1L;
	private String channelCode;		// 支付通道代码
	private String channelName;		// 支付通道名称
	private String channelKey;		// 通道Key
	private String providerCode;		// 通道商编码
	private String payType;		// 支付类型
	private String sceneKey;		// 场景Key
	private String bankId;		// 银行ID
	private String bankProvider;		// 银行支付提供者
	private String subMerchantNo;      //子商户号
	
	public ChannelBankid() {
		super();
	}

	public ChannelBankid(String id){
		super(id);
	}

	public String getSubMerchantNo() {
		return subMerchantNo;
	}

	public void setSubMerchantNo(String subMerchantNo) {
		this.subMerchantNo = subMerchantNo;
	}

	@Length(min=1, max=21, message="支付通道代码长度必须介于 1 和 21 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=1, max=50, message="支付通道名称长度必须介于 1 和 50 之间")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Length(min=0, max=100, message="通道Key长度必须介于 0 和 100 之间")
	public String getChannelKey() {
		return channelKey;
	}

	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	
	@Length(min=0, max=100, message="通道商编码长度必须介于 0 和 100 之间")
	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	
	@Length(min=0, max=100, message="支付类型长度必须介于 0 和 100 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=100, message="场景Key长度必须介于 0 和 100 之间")
	public String getSceneKey() {
		return sceneKey;
	}

	public void setSceneKey(String sceneKey) {
		this.sceneKey = sceneKey;
	}
	
	@Length(min=0, max=20, message="银行ID长度必须介于 0 和 20 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=0, max=100, message="银行支付提供者长度必须介于 0 和 100 之间")
	public String getBankProvider() {
		return bankProvider;
	}

	public void setBankProvider(String bankProvider) {
		this.bankProvider = bankProvider;
	}
	
}