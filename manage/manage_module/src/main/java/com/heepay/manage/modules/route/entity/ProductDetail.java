/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import org.hibernate.validator.constraints.Length;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 产品明细Entity
 * @author lm
 * @version V1.0
 */
public class ProductDetail extends DataEntity<ProductDetail> {
	
	private static final long serialVersionUID = 1L;
	private String productName;		// 产品名称
	private String productCode;		// 产品代码
	private String channelName;		// 支付通道名称
	private String channelCode;		// 支付通道代码
	private String bankName;		// 银行名称
	private String bankNo;		// 银行代码
	private String channelPartnerName;		// 通道合作方
	private String channelPartnerCode;		// 通道合作方代码
	private String channelTypeName;		// 支付通道类型
	private String channelTypeCode;		// 支付通道类型代码
	private String cardTypeName;		// 银行卡类型
	private String cardTypeCode;		// 银行卡类型代码
	private String status;		// 状态
	private String createName;		// 创建人
	public ProductDetail() {
		super();
	}

	public ProductDetail(String id){
		super(id);
	}

	@Length(min=0, max=100, message="产品名称长度必须介于 0 和 100 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=16, message="产品代码长度必须介于 0 和 16 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=50, message="支付通道名称长度必须介于 0 和 50 之间")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Length(min=0, max=34, message="支付通道代码长度必须介于 0 和 34 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=50, message="银行名称长度必须介于 0 和 50 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=3, message="银行代码长度必须介于 0 和 3 之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Length(min=0, max=20, message="通道合作方长度必须介于 0 和 20 之间")
	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}
	
	@Length(min=0, max=6, message="通道合作方代码长度必须介于 0 和 6 之间")
	public String getChannelPartnerCode() {
		return channelPartnerCode;
	}

	public void setChannelPartnerCode(String channelPartnerCode) {
		this.channelPartnerCode = channelPartnerCode;
	}
	
	@Length(min=0, max=20, message="支付通道类型长度必须介于 0 和 20 之间")
	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	
	@Length(min=0, max=6, message="支付通道类型代码长度必须介于 0 和 6 之间")
	public String getChannelTypeCode() {
		return channelTypeCode;
	}

	public void setChannelTypeCode(String channelTypeCode) {
		this.channelTypeCode = channelTypeCode;
	}
	
	@Length(min=0, max=6, message="银行卡类型长度必须介于 0 和 6 之间")
	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
	
	@Length(min=0, max=6, message="银行卡类型代码长度必须介于 0 和 6 之间")
	public String getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}