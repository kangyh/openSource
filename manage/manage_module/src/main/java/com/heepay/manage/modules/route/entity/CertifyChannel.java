/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import org.hibernate.validator.constraints.Length;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 实名认证通道管理Entity
 * @author lm
 * @version V1.0
 */
public class CertifyChannel extends DataEntity<CertifyChannel> {
	
	private static final long serialVersionUID = 1L;
	private String productCode;		// 产品代码
	private String productName;		// 产品名称
	private String channelCode;		// 通道代码
	private String channelName;		// 通道名称
	private String channelPartnerCode;		// 通道合作方代码
	private String channelPartnerName;		// 通道合作方名称
	private String channelTypeCode;		// 通道类型代码
	private String channelTypeName;		// 通道类型名称
	private String cost;		// 成本
	private String settleType;		// 手续费结算类型
	private String settlePeriod;		// 手续费结算周期
	private String sort;		// 优先级别
	private String updateName;		// 更新人

	public CertifyChannel() {
		super();
	}

	public CertifyChannel(String id){
		super(id);
	}

	@Length(min=0, max=16, message="产品代码长度必须介于 0 和 16 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=100, message="产品名称长度必须介于 0 和 100 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=12, message="通道代码长度必须介于 0 和 12之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=20, message="通道名称长度必须介于 0 和 20 之间")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Length(min=0, max=6, message="通道合作方代码长度必须介于 0 和 6 之间")
	public String getChannelPartnerCode() {
		return channelPartnerCode;
	}

	public void setChannelPartnerCode(String channelPartnerCode) {
		this.channelPartnerCode = channelPartnerCode;
	}
	
	@Length(min=0, max=10, message="通道合作方名称长度必须介于 0 和 10 之间")
	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}
	
	@Length(min=0, max=6, message="通道类型代码长度必须介于 0 和 6 之间")
	public String getChannelTypeCode() {
		return channelTypeCode;
	}

	public void setChannelTypeCode(String channelTypeCode) {
		this.channelTypeCode = channelTypeCode;
	}
	
	@Length(min=0, max=10, message="通道类型名称长度必须介于 0 和 10 之间")
	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
	
	@Length(min=0, max=6, message="手续费结算类型长度必须介于 0 和 6 之间")
	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	
	@Length(min=0, max=6, message="手续费结算周期长度必须介于 0 和 6 之间")
	public String getSettlePeriod() {
		return settlePeriod;
	}

	public void setSettlePeriod(String settlePeriod) {
		this.settlePeriod = settlePeriod;
	}
	
	@Length(min=0, max=6, message="优先级别长度必须介于 0 和 6 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
}