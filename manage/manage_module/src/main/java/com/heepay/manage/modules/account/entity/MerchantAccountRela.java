/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.entity;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：账务关联账户Entity
 *
 * 创 建 者： @author zjx
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
public class MerchantAccountRela extends DataEntity<MerchantAccountRela> {
	
	private static final long serialVersionUID = 1L;
	private String relaId;		// 记账关联ID
	private String transType;		// 交易类型
	private String transSubType;		// 交易子类型(记账类型)，枚举类AccountMark
	private String accountType;		// 记账账户类型，枚举类InternalAccountType
	private String balanceDirection;		// 余额发生方向
	private String balanceChangeType;		// 余额变动类型:TOTAL=总金额,FEE=手续费,TRANS=交易金额
	private String sequence;		// 记账顺序
	private String isEnable;		//启用状态

	public MerchantAccountRela() {
		super();
	}

	public MerchantAccountRela(String id){
		super(id);
	}

	@Length(min=1, max=10, message="记账关联ID长度必须介于 1 和 10 之间")
	public String getRelaId() {
		return relaId;
	}

	public void setRelaId(String relaId) {
		this.relaId = relaId;
	}
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Length(min=0, max=6, message="交易子类型(记账类型)，枚举类AccountMark长度必须介于 0 和 6 之间")
	public String getTransSubType() {
		return transSubType;
	}

	public void setTransSubType(String transSubType) {
		this.transSubType = transSubType;
	}
	
	@Length(min=0, max=3, message="记账账户类型，枚举类InternalAccountType长度必须介于 0 和 3 之间")
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	@Length(min=0, max=1, message="余额发生方向长度必须介于 0 和 1 之间")
	public String getBalanceDirection() {
		return balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}
	
	@Length(min=0, max=6, message="余额变动类型:TOTAL=总金额,FEE=手续费,TRANS=交易金额长度必须介于 0 和 6 之间")
	public String getBalanceChangeType() {
		return balanceChangeType;
	}

	public void setBalanceChangeType(String balanceChangeType) {
		this.balanceChangeType = balanceChangeType;
	}
	
	@Length(min=1, max=3, message="记账顺序长度必须介于 1 和 3 之间")
	public String getSequence() {
		return sequence;
	}


	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
}