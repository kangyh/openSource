package com.heepay.manage.modules.payment.param;

/**
 * 
* 
* 描    述：
*
* 创 建 者： 杨春龙  
* 创建时间： 2017年5月12日 下午4:40:45 
* 创建描述：余额汇总-会计科目
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
public class AccountSubjectSum {
	
	private String subjectType;//类型
	
	private String type;//会计科目类型

	private String lendingDirection;//借贷方向
	
	private String debitBeginBalances;//期初余额(借方)
	
	private String debitEndingBalances;//期末余额(借方)

	private String creditBeginBalances;//期初余额(贷方)

	private String creditEndingBalances;//期末余额(贷方)

	private String debitCurrentAmount; // 发生额(借方)
	
	private String creditCurrentAmount; // 发生额(贷方)
				
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getDebitCurrentAmount() {
		return debitCurrentAmount;
	}
	public void setDebitCurrentAmount(String debitCurrentAmount) {
		this.debitCurrentAmount = debitCurrentAmount;
	}
	public String getCreditCurrentAmount() {
		return creditCurrentAmount;
	}
	public void setCreditCurrentAmount(String creditCurrentAmount) {
		this.creditCurrentAmount = creditCurrentAmount;
	}
	public String getDebitBeginBalances() {
		return debitBeginBalances;
	}

	public String getDebitEndingBalances() {
		return debitEndingBalances;
	}

	public String getCreditBeginBalances() {
		return creditBeginBalances;
	}

	public String getCreditEndingBalances() {
		return creditEndingBalances;
	}

	public void setDebitBeginBalances(String debitBeginBalances) {
		this.debitBeginBalances = debitBeginBalances;
	}

	public void setDebitEndingBalances(String debitEndingBalances) {
		this.debitEndingBalances = debitEndingBalances;
	}

	public void setCreditBeginBalances(String creditBeginBalances) {
		this.creditBeginBalances = creditBeginBalances;
	}

	public void setCreditEndingBalances(String creditEndingBalances) {
		this.creditEndingBalances = creditEndingBalances;
	}
	public String getLendingDirection() {
		return lendingDirection;
	}
	public void setLendingDirection(String lendingDirection) {
		this.lendingDirection = lendingDirection;
	}
	
	

}
