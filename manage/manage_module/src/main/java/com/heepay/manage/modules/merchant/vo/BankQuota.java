/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.vo;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：银行限额Entity
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
public class BankQuota extends DataEntity<BankQuota> {
	
	private static final long serialVersionUID = 1L;
	private String bankId;		// 银行ID
	private String bankName;		// 银行名称
	private String bankCardType;		// 银行卡类型
	private String tradeType;		// 交易类型
	private BigDecimal perlimitAmount;		// 单笔限额
	private BigDecimal daylimitAmount;		// 单日限额
	private BigDecimal monlimitAmount;		// 单月限额
	
	public BankQuota() {
		super();
	}

	public BankQuota(String id){
		super(id);
	}

	@Length(min=0, max=3, message="银行ID长度必须介于 0 和 3 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=0, max=50, message="银行名称长度必须介于 0 和 50 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=6, message="银行卡类型长度必须介于 0 和 6 之间")
	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

    public BigDecimal getPerlimitAmount() {
        return perlimitAmount;
    }

    public void setPerlimitAmount(BigDecimal perlimitAmount) {
        this.perlimitAmount = perlimitAmount;
    }

    public BigDecimal getDaylimitAmount() {
        return daylimitAmount;
    }

    public void setDaylimitAmount(BigDecimal daylimitAmount) {
        this.daylimitAmount = daylimitAmount;
    }

    public BigDecimal getMonlimitAmount() {
        return monlimitAmount;
    }

    public void setMonlimitAmount(BigDecimal monlimitAmount) {
        this.monlimitAmount = monlimitAmount;
    }
	
}