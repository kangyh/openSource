/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import org.hibernate.validator.constraints.Length;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 银行卡bin管理Entity
 * @author lgk
 * @version V1.0
 */
public class BankCardBin extends DataEntity<BankCardBin> {
	
	private static final long serialVersionUID = 1L;
	private String bankcardCode;		// 发卡行代码
	private String bankcardName;		// 发卡行名称
	private String bankcardNote;		// 发卡行标识
	private String bankcardLength;		// 卡号长度
	private String bankcardNoteLength;		// 发卡行标识长度
	private String cardCode;		// 卡代码
	private String cardName;		// 卡名称
	private String cardType;		// 卡类型
	private String cardBinNo;		// 卡bin银行卡号
	private String status;		// 状态
	private String updateName;		// 修改人

	public BankCardBin() {
		super();
	}

	public BankCardBin(String id){
		super(id);
	}

	@Length(min=0, max=12, message="发卡行代码长度必须介于 0 和 12 之间")
	public String getBankcardCode() {
		return bankcardCode;
	}

	public void setBankcardCode(String bankcardCode) {
		this.bankcardCode = bankcardCode;
	}
	
	@Length(min=0, max=30, message="发卡行名称长度必须介于 0 和 30 之间")
	public String getBankcardName() {
		return bankcardName;
	}

	public void setBankcardName(String bankcardName) {
		this.bankcardName = bankcardName;
	}
	
	@Length(min=0, max=9, message="发卡行标识长度必须介于 0 和 9 之间")
	public String getBankcardNote() {
		return bankcardNote;
	}

	public void setBankcardNote(String bankcardNote) {
		this.bankcardNote = bankcardNote;
	}
	
	@Length(min=0, max=2, message="卡号长度长度必须介于 0 和 2 之间")
	public String getBankcardLength() {
		return bankcardLength;
	}

	public void setBankcardLength(String bankcardLength) {
		this.bankcardLength = bankcardLength;
	}
	
	@Length(min=0, max=1, message="标识长度必须介于 0 和 1 之间")
	public String getBankcardNoteLength() {
		return bankcardNoteLength;
	}

	public void setBankcardNoteLength(String bankcardNoteLength) {
		this.bankcardNoteLength = bankcardNoteLength;
	}
	
	@Length(min=0, max=8, message="卡代码长度必须介于 0 和 8 之间")
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	
	@Length(min=0, max=40, message="卡名称长度必须介于 0 和 40 之间")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	@Length(min=0, max=6, message="卡类型长度必须介于 0 和 6 之间")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Length(min=0, max=50, message="卡bin银行卡号长度必须介于 0 和 50 之间")
	public String getCardBinNo() {
		return cardBinNo;
	}

	public void setCardBinNo(String cardBinNo) {
		this.cardBinNo = cardBinNo;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
}