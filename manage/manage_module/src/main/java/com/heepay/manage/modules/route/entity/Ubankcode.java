/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 支行代码Entity
 * @author 牛文
 * @version V1.0
 */
public class Ubankcode extends DataEntity<Ubankcode> {
	
	private static final long serialVersionUID = 1L;
	private String bankNo;		// 银行代码
	private String areaCode;		// 地区代码
	private String openBankName;		// 支行名称
	private String openBankCode;		// 支行代码
	
	public Ubankcode() {
		super();
	}

	public Ubankcode(String id){
		super(id);
	}

	@Length(min=0, max=3, message="银行代码长度必须介于 0 和 3 之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Length(min=0, max=4, message="地区代码长度必须介于 0 和 4 之间")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	@Length(min=0, max=50, message="支行名称长度必须介于 0 和 50 之间")
	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	
	@Length(min=0, max=5, message="支行代码长度必须介于 0 和 5 之间")
	public String getOpenBankCode() {
		return openBankCode;
	}

	public void setOpenBankCode(String openBankCode) {
		this.openBankCode = openBankCode;
	}
	
}