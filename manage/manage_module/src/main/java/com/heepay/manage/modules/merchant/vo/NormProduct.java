/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

import java.math.BigDecimal;

/**
 *
 * 描    述：标准产品Entity
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
public class NormProduct extends DataEntity<NormProduct> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 标准产品编码
	private String productCode;		// 产品编码
	private String productName;   //产品名称
	private String businessType; // 业务类型
	private String trxType; // 交易类型
	private String chargeSource; // 手续费来源
	private String chargeDeductType; // 手续费扣除方式
	private String chargeCollectionType; // 手续费收取方式
	private BigDecimal openUpFee;		// 开通费
	private BigDecimal technologyFee;		// 技术对接费
	private BigDecimal cashDeposit;		// 保证金
	private String bankCardType; //银行卡类型
	private String chargeType;		// 计费类型
	private BigDecimal chargeRatio;		// 手续费费用(%)比例
	private BigDecimal chargeFee; // 手续费费用(元)
	private BigDecimal maxFee;		// 手续费比例最大金额
	private BigDecimal minFee;		// 手续费比例最小金额
	private String status;  //状态
	private String isRefundable;  //是否退手续费
	private String settleType;  //结算类型
	private String settlementTo; //结算至
	
	public NormProduct() {
		super();
	}

	public NormProduct(String id){
		super(id);
	}

	@Length(min=0, max=10, message="标准产品编码长度必须介于 0 和 10 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=10, message="产品编码长度必须介于 0 和 10 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=6, message="计费类型长度必须介于 0 和 6 之间")
	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public BigDecimal getOpenUpFee() {
		return openUpFee;
	}

	public void setOpenUpFee(BigDecimal openUpFee) {
		this.openUpFee = openUpFee;
	}

	public BigDecimal getTechnologyFee() {
		return technologyFee;
	}

	public void setTechnologyFee(BigDecimal technologyFee) {
		this.technologyFee = technologyFee;
	}

	public BigDecimal getCashDeposit() {
		return cashDeposit;
	}

	public void setCashDeposit(BigDecimal cashDeposit) {
		this.cashDeposit = cashDeposit;
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

	@Length(min=0, max=6, message="银行卡类型长度必须介于 0 和 6 之间")
	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	@Length(min = 0, max = 6, message = "手续费来源长度必须介于 0 和 6 之间")
	public String getChargeSource() {
		return chargeSource;
	}

	public void setChargeSource(String chargeSource) {
		this.chargeSource = chargeSource;
	}

	@Length(min = 0, max = 6, message = "手续费扣除方式长度必须介于 0 和 6 之间")
	public String getChargeDeductType() {
		return chargeDeductType;
	}

	public void setChargeDeductType(String chargeDeductType) {
		this.chargeDeductType = chargeDeductType;
	}

	@Length(min = 0, max = 6, message = "手续费收取方式长度必须介于 0 和 6 之间")
	public String getChargeCollectionType() {
		return chargeCollectionType;
	}

	public void setChargeCollectionType(String chargeCollectionType) {
		this.chargeCollectionType = chargeCollectionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsRefundable() {
		return isRefundable;
	}

	public void setIsRefundable(String isRefundable) {
		this.isRefundable = isRefundable;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public String getSettlementTo() {
		return settlementTo;
	}

	public void setSettlementTo(String settlementTo) {
		this.settlementTo = settlementTo;
	}
}