package com.heepay.tpds.vo;

/**
 * 
 * 
 * 描 述：风控系统
 *
 * 创 建 者：王英雷 E-mail:wangyl@9186.com 创建时间： 2017年2月22日 下午4:59:24 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
public class ServerBankInfo {
	private String bankServerAddr;

	private String bankHostPasswordUrl;
	
	private String bankHostPasswordModifyUrl;
	private String bankHostPasswordResettingUrl;
	private String bankHostPasswordSettingUrl; 
	private String bankHostPasswordVerifyUrl;
	
	private String backURL;
	
	private String publicKeyPath;
	private String privateKeyPath;

	private String jctHostIp;

	private String signPublicKey;

	private String signPrivateKey;

	public String getSignPublicKey() {
		return signPublicKey;
	}

	public void setSignPublicKey(String signPublicKey) {
		this.signPublicKey = signPublicKey;
	}

	public String getSignPrivateKey() {
		return signPrivateKey;
	}

	public void setSignPrivateKey(String signPrivateKey) {
		this.signPrivateKey = signPrivateKey;
	}

	public String getBankServerAddr() {
		return bankServerAddr;
	}

	public void setBankServerAddr(String bankServerAddr) {
		this.bankServerAddr = bankServerAddr;
	}

	public String getBankHostPasswordUrl() {
		return bankHostPasswordUrl;
	}

	public void setBankHostPasswordUrl(String bankHostPasswordUrl) {
		this.bankHostPasswordUrl = bankHostPasswordUrl;
	}

	public String getBankHostPasswordModifyUrl() {
		return bankHostPasswordModifyUrl;
	}

	public void setBankHostPasswordModifyUrl(String bankHostPasswordModifyUrl) {
		this.bankHostPasswordModifyUrl = bankHostPasswordModifyUrl;
	}

	public String getBankHostPasswordResettingUrl() {
		return bankHostPasswordResettingUrl;
	}

	public void setBankHostPasswordResettingUrl(String bankHostPasswordResettingUrl) {
		this.bankHostPasswordResettingUrl = bankHostPasswordResettingUrl;
	}

	public String getBankHostPasswordSettingUrl() {
		return bankHostPasswordSettingUrl;
	}

	public void setBankHostPasswordSettingUrl(String bankHostPasswordSettingUrl) {
		this.bankHostPasswordSettingUrl = bankHostPasswordSettingUrl;
	}

	public String getBankHostPasswordVerifyUrl() {
		return bankHostPasswordVerifyUrl;
	}

	public void setBankHostPasswordVerifyUrl(String bankHostPasswordVerifyUrl) {
		this.bankHostPasswordVerifyUrl = bankHostPasswordVerifyUrl;
	}

	public String getPublicKeyPath() {
		return publicKeyPath;
	}

	public void setPublicKeyPath(String publicKeyPath) {
		this.publicKeyPath = publicKeyPath;
	}

	public String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}

	public String getBackURL() {
		return backURL;
	}

	public void setBackURL(String backURL) {
		this.backURL = backURL;
	}

	public String getJctHostIp() {
		return jctHostIp;
	}

	public void setJctHostIp(String jctHostIp) {
		this.jctHostIp = jctHostIp;
	}
}
