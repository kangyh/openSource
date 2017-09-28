/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：汇款充值Entity
 *
 * 创 建 者： @author 王亚洪
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
public class RemitsChargeRecord extends DataEntity<RemitsChargeRecord> {
	
	private static final long serialVersionUID = 1L;
	private String remitsId;		// 汇款充值ID
	private String transNo;		// 汇付宝交易号记录各种业务类型的交易订单号
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private String productCode;		// 产品代码(product表code字段)
	private String payType;		// 支付类型
	private String transType;		// 交易类型
	private String status;		// 支付状态
	private String description;		// 支付说明
	private Date updateTime;		// 更新时间
	private String payAmount;		// 支付金额
	private Date payTime;		// 支付时间
	private String successAmount;		// 成功金额
	private Date successTime;		// 成功时间
	private String merchantOrderNo;		// 商户订单号
	private String bankId;		// 银行ID
	private String bankName;		// 银行名称
	private String bankcardType;		// 银行卡类型
	private String bankcardNo;		// 银行卡卡号
	private String bankcardOwner;		// 银行卡持卡人姓名
	private String channelCode;		// 渠道代码
	private String channelName;		// 渠道名称
	private String currency;		// 币种
	private String feeType;		// 交易手续费扣除方式
	private String feeAmount;		// 手续费
	private String operator;		// 操作人
	private String settleCyc;		// 结算方式  0,1,x
	private Date createTime;		// 创建时间
	private String payOpenbankName;		// 付款方开户行
	private String payBankcardNo;		// 付款方账号
	private String payMerchantName;		// 付款方名称
	private String bankSerialnumber;		// 银行交易流水号
	private String payBankName;
	
	public RemitsChargeRecord() {
		super();
	}

	public RemitsChargeRecord(String id){
		super(id);
	}

	@Length(min=1, max=26, message="汇款充值ID长度必须介于 1 和 26 之间")
	public String getRemitsId() {
		return remitsId;
	}

	public void setRemitsId(String remitsId) {
		this.remitsId = remitsId;
	}
	
	@Length(min=0, max=26, message="汇付宝交易号记录各种业务类型的交易订单号长度必须介于 0 和 26 之间")
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商户登录账号长度必须介于 0 和 64 之间")
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	
	@Length(min=0, max=64, message="商户公司长度必须介于 0 和 64 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	@Length(min=0, max=6, message="产品代码(product表code字段)长度必须介于 0 和 6 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=6, message="支付类型长度必须介于 0 和 6 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=1, max=6, message="交易类型长度必须介于 1 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Length(min=0, max=6, message="支付状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1024, message="支付说明长度必须介于 0 和 1024 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="支付时间不能为空")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=0, max=20, message="银行ID长度必须介于 0 和 20 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=0, max=256, message="银行名称长度必须介于 0 和 256 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=6, message="银行卡类型长度必须介于 0 和 6 之间")
	public String getBankcardType() {
		return bankcardType;
	}

	public void setBankcardType(String bankcardType) {
		this.bankcardType = bankcardType;
	}
	
	@Length(min=0, max=256, message="银行卡卡号长度必须介于 0 和 256 之间")
	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	
	@Length(min=0, max=256, message="银行卡持卡人姓名长度必须介于 0 和 256 之间")
	public String getBankcardOwner() {
		return bankcardOwner;
	}

	public void setBankcardOwner(String bankcardOwner) {
		this.bankcardOwner = bankcardOwner;
	}
	
	@Length(min=0, max=200, message="渠道代码长度必须介于 0 和 200 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=64, message="渠道名称长度必须介于 0 和 64 之间")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@Length(min=0, max=8, message="币种长度必须介于 0 和 8 之间")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Length(min=0, max=6, message="交易手续费扣除方式长度必须介于 0 和 6 之间")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	@Length(min=0, max=6, message="操作人长度必须介于 0 和 6 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=1, message="结算方式  0,1,x长度必须介于 0 和 1 之间")
	public String getSettleCyc() {
		return settleCyc;
	}

	public void setSettleCyc(String settleCyc) {
		this.settleCyc = settleCyc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=255, message="付款方开户行长度必须介于 0 和 255 之间")
	public String getPayOpenbankName() {
		return payOpenbankName;
	}

	public void setPayOpenbankName(String payOpenbankName) {
		this.payOpenbankName = payOpenbankName;
	}
	
	@Length(min=0, max=255, message="付款方账号长度必须介于 0 和 255 之间")
	public String getPayBankcardNo() {
		return payBankcardNo;
	}

	public void setPayBankcardNo(String payBankcardNo) {
		this.payBankcardNo = payBankcardNo;
	}
	
	@Length(min=0, max=64, message="付款方名称长度必须介于 0 和 64 之间")
	public String getPayMerchantName() {
		return payMerchantName;
	}

	public void setPayMerchantName(String payMerchantName) {
		this.payMerchantName = payMerchantName;
	}
	
	@Length(min=0, max=255, message="银行交易流水号长度必须介于 0 和 255 之间")
	public String getBankSerialnumber() {
		return bankSerialnumber;
	}

	public void setBankSerialnumber(String bankSerialnumber) {
		this.bankSerialnumber = bankSerialnumber;
	}

  public String getPayBankName() {
    return payBankName;
  }

  public void setPayBankName(String payBankName) {
    this.payBankName = payBankName;
  }
	
}