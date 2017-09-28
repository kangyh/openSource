package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描 述：存管提现vo
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月13日 上午10:53:27
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
public class DepositWithdrawVo {
	
	
	/**
	 * 商户ID
	 */
	private String merchantId;
	/**
	 * 提现金额
	 */
	private String amount;
	
	/**
	 * 银行卡号
	 */
	private String bankAccountNo;
	/**
	 * 系统编码
	 */
	private String systemNo;
	
	/**
	 * 业务流水号
	 */
	private String businessSeqNo;

	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
	public String getBusinessSeqNo() {
		return businessSeqNo;
	}
	public void setBusinessSeqNo(String businessSeqNo) {
		this.businessSeqNo = businessSeqNo;
	}
	
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	
	public String getSystemNo() {
		return systemNo;
	}
	public void setSystemNo(String systemNo) {
		this.systemNo = systemNo;
	}

}

 