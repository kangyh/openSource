/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：费率操作日志Entity
 *
 * 创 建 者： @author ly
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
public class MerchantRateLog extends DataEntity<MerchantRateLog> {
	
	private static final long serialVersionUID = 1L;
	private String rateId;		// 商户产品表ID(merchant_product_rate)
	private String bankRateId;		// 商户费率详情表ID(merchant_rate_bank)
	private String merchantId;		// 商户ID
	private String businessType;		// 业务类型(枚举类RateBusinessType ((
	private String productCode;		// 产品代码(product表code字段)
	private String productName;		// 产品名称
	private Date ruleBeginTime;		// 规则开始时间
	private Date ruleEndTime;		// 规则结束时间
	private String chargeSource;		// 手续费来源(枚举类ChargeSource ((
	private String chargeDeductType;		// 手续费扣除方式(枚举类ChargeDeductType ((
	private String chargeCollectionType;		// 手续费收取方式(枚举类ChargeCollectionType ((
	private String isRefundable;		// 退款时是否退还手续费(枚举类CommonStatus 是(Y),否(N))
	private String status;		// 状态(枚举类CommonStatus  ENABLE(启用) DISABL(禁用))
	private String settleType;		// 结算类型
	private String settlementTo;		// 结算至(枚举类SettlementTo  ACCBAL(账户余额))
	private String notifyUrl;		// 异步通知地址
	private String backUrl;		// 同步返回地址
	private String ipDomain;		// ip/域名
	private String autographKey;		// 签名key
	private String autographType;		// 签名方式
	private String bankNo;		// 银行id
	private String bankName;		// 银行名
	private String bankCardType;		// 银行卡类型(枚举类RateBankcardType ((
	private String chargeType;		// 计费类型(枚举类CostType ((
	private BigDecimal chargeRatio;		// 手续费费用(%)比例
	private BigDecimal chargeFee;		// 手续费费用(元)固定
	private BigDecimal maxFee;		// 手续费比例最大金额
	private BigDecimal minFee;		// 手续费比例最小金额
	private Date operationTime;		// 操作时间
	private String operationUser;		// 操作人
	private String operationType;		// 操作类型
	
	public MerchantRateLog() {
		super();
	}

	public MerchantRateLog(String id){
		super(id);
	}

	@Length(min=0, max=10, message="商户产品表ID(merchant_product_rate)长度必须介于 0 和 10 之间")
	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	
	@Length(min=0, max=10, message="商户费率详情表ID(merchant_rate_bank)长度必须介于 0 和 10 之间")
	public String getBankRateId() {
		return bankRateId;
	}

	public void setBankRateId(String bankRateId) {
		this.bankRateId = bankRateId;
	}
	
	@Length(min=0, max=10, message="商户ID长度必须介于 0 和 10 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=1, max=6, message="业务类型(枚举类RateBusinessType ((长度必须介于 1 和 6 之间")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@Length(min=1, max=6, message="产品代码(product表code字段)长度必须介于 1 和 6 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRuleBeginTime() {
		return ruleBeginTime;
	}

	public void setRuleBeginTime(Date ruleBeginTime) {
		this.ruleBeginTime = ruleBeginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRuleEndTime() {
		return ruleEndTime;
	}

	public void setRuleEndTime(Date ruleEndTime) {
		this.ruleEndTime = ruleEndTime;
	}
	
	@Length(min=0, max=6, message="手续费来源(枚举类ChargeSource ((长度必须介于 0 和 6 之间")
	public String getChargeSource() {
		return chargeSource;
	}

	public void setChargeSource(String chargeSource) {
		this.chargeSource = chargeSource;
	}
	
	@Length(min=0, max=6, message="手续费扣除方式(枚举类ChargeDeductType ((长度必须介于 0 和 6 之间")
	public String getChargeDeductType() {
		return chargeDeductType;
	}

	public void setChargeDeductType(String chargeDeductType) {
		this.chargeDeductType = chargeDeductType;
	}
	
	@Length(min=0, max=6, message="手续费收取方式(枚举类ChargeCollectionType ((长度必须介于 0 和 6 之间")
	public String getChargeCollectionType() {
		return chargeCollectionType;
	}

	public void setChargeCollectionType(String chargeCollectionType) {
		this.chargeCollectionType = chargeCollectionType;
	}
	
	@Length(min=0, max=6, message="退款时是否退还手续费(枚举类CommonStatus 是(Y),否(N))长度必须介于 0 和 6 之间")
	public String getIsRefundable() {
		return isRefundable;
	}

	public void setIsRefundable(String isRefundable) {
		this.isRefundable = isRefundable;
	}
	
	@Length(min=1, max=6, message="状态(枚举类CommonStatus  ENABLE(启用) DISABL(禁用))长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1, message="结算类型长度必须介于 0 和 1 之间")
	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	
	@Length(min=0, max=6, message="结算至(枚举类SettlementTo  ACCBAL(账户余额))长度必须介于 0 和 6 之间")
	public String getSettlementTo() {
		return settlementTo;
	}

	public void setSettlementTo(String settlementTo) {
		this.settlementTo = settlementTo;
	}
	
	@Length(min=0, max=512, message="异步通知地址长度必须介于 0 和 512 之间")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	@Length(min=0, max=512, message="同步返回地址长度必须介于 0 和 512 之间")
	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	@Length(min=0, max=512, message="ip/域名长度必须介于 0 和 512 之间")
	public String getIpDomain() {
		return ipDomain;
	}

	public void setIpDomain(String ipDomain) {
		this.ipDomain = ipDomain;
	}
	
	@Length(min=0, max=512, message="签名key长度必须介于 0 和 512 之间")
	public String getAutographKey() {
		return autographKey;
	}

	public void setAutographKey(String autographKey) {
		this.autographKey = autographKey;
	}
	
	@Length(min=0, max=3, message="签名方式长度必须介于 0 和 3 之间")
	public String getAutographType() {
		return autographType;
	}

	public void setAutographType(String autographType) {
		this.autographType = autographType;
	}
	
	@Length(min=0, max=3, message="银行id长度必须介于 0 和 3 之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Length(min=0, max=100, message="银行名长度必须介于 0 和 100 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=6, message="银行卡类型(枚举类RateBankcardType ((长度必须介于 0 和 6 之间")
	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	
	@Length(min=0, max=6, message="计费类型(枚举类CostType ((长度必须介于 0 和 6 之间")
	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	
	public BigDecimal getChargeRatio() {
        return chargeRatio;
    }

    public void setChargeRatio(BigDecimal chargeRatio) {
        this.chargeRatio = chargeRatio;
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    public BigDecimal getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(BigDecimal maxFee) {
        this.maxFee = maxFee;
    }

    public BigDecimal getMinFee() {
        return minFee;
    }

    public void setMinFee(BigDecimal minFee) {
        this.minFee = minFee;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	@Length(min=0, max=10, message="操作人长度必须介于 0 和 10 之间")
	public String getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}
	
	@Length(min=0, max=10, message="操作类型长度必须介于 0 和 10 之间")
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
}