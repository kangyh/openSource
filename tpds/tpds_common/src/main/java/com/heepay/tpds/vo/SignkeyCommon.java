package com.heepay.tpds.vo;

import org.springframework.stereotype.Component;

/**
 * 
 * 
 * 描    述：商户与汇付宝签名key公共类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月18日上午11:25:01 
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
@Component
public class SignkeyCommon {
	
	private String jiuctSignkey;
	
	private String jiuctB2CSignkey;
	
	private String jiuctTransQuery;
	
	private String bank_IV;

	private String bank_Key;
	
	private String bank_Signature;
	
	public String getJiuctB2CSignkey() {
		return jiuctB2CSignkey;
	}

	public void setJiuctB2CSignkey(String jiuctB2CSignkey) {
		this.jiuctB2CSignkey = jiuctB2CSignkey;
	}

	public String getJiuctTransQuery() {
		return jiuctTransQuery;
	}

	public void setJiuctTransQuery(String jiuctTransQuery) {
		this.jiuctTransQuery = jiuctTransQuery;
	}

	public String getJiuctSignkey() {
		return jiuctSignkey;
	}

	public void setJiuctSignkey(String jiuctSignkey) {
		this.jiuctSignkey = jiuctSignkey;
	}

	public String getBank_IV() {
		return bank_IV;
	}

	public void setBank_IV(String bank_IV) {
		this.bank_IV = bank_IV;
	}

	public String getBank_Key() {
		return bank_Key;
	}

	public void setBank_Key(String bank_Key) {
		this.bank_Key = bank_Key;
	}

	public String getBank_Signature() {
		return bank_Signature;
	}

	public void setBank_Signature(String bank_signature) {
		this.bank_Signature = bank_signature;
	}
}
