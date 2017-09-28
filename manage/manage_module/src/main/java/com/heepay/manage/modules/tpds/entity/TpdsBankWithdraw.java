/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.tpds.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述：存管银行提现记录Entity
 *
 * 创 建 者： @author wangdong
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
public class TpdsBankWithdraw extends DataEntity<TpdsBankWithdraw> {
	
	private static final long serialVersionUID = 1L;
	private Long cusId;		// 主键
	private String businessSeqNo;		// 业务流水号
	private String businessOrderNo;		// 订单流水号
	private String raType;		// 充值/提现类型
	private String entrustflag;		// 委托标识
	private String oderNo;		// 序号
	private String debitAccountNo;		// 借方台账账户
	private String cebitAccountNo;		// 贷方台账账户
	private String currency;		// 币种(CNY:人民币)
	private String amount;		// 交易金额
	private String otherAmounttype;		// 其他金额类型(01：费用            02：标的收益            03:提现手续费            )
	private String otherAmount;		// 其他金额
	private String payType;		// 支付方式(00:银行支付渠道            01：第三方支付渠道            )
	private String busiBranchNo;		// 支付公司代码
	private String bankAccountNo;		// 银行卡号
	private String secBankaccNo;		// 二类户账户
	private String note;		// 备注
	private Date createTime;		// 创建时间
	
	public TpdsBankWithdraw() {
		super();
	}

	public TpdsBankWithdraw(String id){
		super(id);
	}

	@NotNull(message="主键不能为空")
	public Long getCusId() {
		return cusId;
	}

	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	
	@Length(min=0, max=64, message="业务流水号长度必须介于 0 和 64 之间")
	public String getBusinessSeqNo() {
		return businessSeqNo;
	}

	public void setBusinessSeqNo(String businessSeqNo) {
		this.businessSeqNo = businessSeqNo;
	}
	
	@Length(min=0, max=64, message="订单流水号长度必须介于 0 和 64 之间")
	public String getBusinessOrderNo() {
		return businessOrderNo;
	}

	public void setBusinessOrderNo(String businessOrderNo) {
		this.businessOrderNo = businessOrderNo;
	}
	
	@Length(min=0, max=3, message="充值/提现类型长度必须介于 0 和 3 之间")
	public String getRaType() {
		return raType;
	}

	public void setRaType(String raType) {
		this.raType = raType;
	}
	
	@Length(min=0, max=2, message="委托标识长度必须介于 0 和 2 之间")
	public String getEntrustflag() {
		return entrustflag;
	}

	public void setEntrustflag(String entrustflag) {
		this.entrustflag = entrustflag;
	}
	
	@Length(min=0, max=2, message="序号长度必须介于 0 和 2 之间")
	public String getOderNo() {
		return oderNo;
	}

	public void setOderNo(String oderNo) {
		this.oderNo = oderNo;
	}
	
	@Length(min=0, max=128, message="借方台账账户长度必须介于 0 和 128 之间")
	public String getDebitAccountNo() {
		return debitAccountNo;
	}

	public void setDebitAccountNo(String debitAccountNo) {
		this.debitAccountNo = debitAccountNo;
	}
	
	@Length(min=0, max=128, message="贷方台账账户长度必须介于 0 和 128 之间")
	public String getCebitAccountNo() {
		return cebitAccountNo;
	}

	public void setCebitAccountNo(String cebitAccountNo) {
		this.cebitAccountNo = cebitAccountNo;
	}
	
	@Length(min=0, max=3, message="币种(CNY:人民币)长度必须介于 0 和 3 之间")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=3, message="其他金额类型(01：费用            02：标的收益            03:提现手续费            )长度必须介于 0 和 3 之间")
	public String getOtherAmounttype() {
		return otherAmounttype;
	}

	public void setOtherAmounttype(String otherAmounttype) {
		this.otherAmounttype = otherAmounttype;
	}
	
	public String getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(String otherAmount) {
		this.otherAmount = otherAmount;
	}
	
	@Length(min=0, max=2, message="支付方式(00:银行支付渠道            01：第三方支付渠道            )长度必须介于 0 和 2 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=30, message="支付公司代码长度必须介于 0 和 30 之间")
	public String getBusiBranchNo() {
		return busiBranchNo;
	}

	public void setBusiBranchNo(String busiBranchNo) {
		this.busiBranchNo = busiBranchNo;
	}
	
	@Length(min=0, max=100, message="银行卡号长度必须介于 0 和 100 之间")
	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	
	@Length(min=0, max=128, message="二类户账户长度必须介于 0 和 128 之间")
	public String getSecBankaccNo() {
		return secBankaccNo;
	}

	public void setSecBankaccNo(String secBankaccNo) {
		this.secBankaccNo = secBankaccNo;
	}
	
	@Length(min=0, max=100, message="备注长度必须介于 0 和 100 之间")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}