package com.heepay.tpds.client;

/**
 * *
 * 
* 
* 描    述：billing-common.propertity文件封装为对应实体类
*
* 创 建 者： wangjie
* 创建时间：  2016年11月16日上午9:35:59
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

public class TpdsCommonClient {
	
	private String filePath;
	
	private String warnBlock;
	
	private String contactPerson;
	
	private String bankHostIp;
	
	private String bankServerPath;
	
	private String bankUserName;
	
	private String bankPassword;
	
	private String bankUploadFilePath;
	
	private String p2pHostIp;
	
	private String p2pUserName;
	
	private String p2pPassword;
	
	private String p2pUploadFilePath;
	
	private String busiBranchNo;
	private String BASE64PUBLICKEY;
	private String BASE64PRIVATEKEY;
	
	private String signPublicKey;
	private String signPrivateKey;
	
	
	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getWarnBlock() {
		return warnBlock;
	}


	public void setWarnBlock(String warnBlock) {
		this.warnBlock = warnBlock;
	}


	public String getContactPerson() {
		return contactPerson;
	}


	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}


	public String getBankHostIp() {
		return bankHostIp;
	}


	public void setBankHostIp(String bankHostIp) {
		this.bankHostIp = bankHostIp;
	}


	public String getBankUserName() {
		return bankUserName;
	}


	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}


	public String getBankPassword() {
		return bankPassword;
	}


	public void setBankPassword(String bankPassword) {
		this.bankPassword = bankPassword;
	}


	public String getBankUploadFilePath() {
		return bankUploadFilePath;
	}


	public void setBankUploadFilePath(String bankUploadFilePath) {
		this.bankUploadFilePath = bankUploadFilePath;
	}


	public String getP2pHostIp() {
		return p2pHostIp;
	}


	public void setP2pHostIp(String p2pHostIp) {
		this.p2pHostIp = p2pHostIp;
	}


	public String getP2pUserName() {
		return p2pUserName;
	}


	public void setP2pUserName(String p2pUserName) {
		this.p2pUserName = p2pUserName;
	}


	public String getP2pPassword() {
		return p2pPassword;
	}


	public void setP2pPassword(String p2pPassword) {
		this.p2pPassword = p2pPassword;
	}


	public String getP2pUploadFilePath() {
		return p2pUploadFilePath;
	}


	public void setP2pUploadFilePath(String p2pUploadFilePath) {
		this.p2pUploadFilePath = p2pUploadFilePath;
	}


	public String getBankServerPath() {
		return bankServerPath;
	}


	public void setBankServerPath(String bankServerPath) {
		this.bankServerPath = bankServerPath;
	}


	public String getBusiBranchNo() {
		return busiBranchNo;
	}


	public void setBusiBranchNo(String busiBranchNo) {
		this.busiBranchNo = busiBranchNo;
	}


	public String getBASE64PUBLICKEY() {
		return BASE64PUBLICKEY;
	}


	public void setBASE64PUBLICKEY(String bASE64PUBLICKEY) {
		BASE64PUBLICKEY = bASE64PUBLICKEY;
	}


	public String getBASE64PRIVATEKEY() {
		return BASE64PRIVATEKEY;
	}


	public void setBASE64PRIVATEKEY(String bASE64PRIVATEKEY) {
		BASE64PRIVATEKEY = bASE64PRIVATEKEY;
	}


	public String getSignPrivateKey() {
		return signPrivateKey;
	}


	public void setSignPrivateKey(String signPrivateKey) {
		this.signPrivateKey = signPrivateKey;
	}
	
}
