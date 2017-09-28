package com.heepay.rpc.client;

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

public class BillingCommonClient {
	
	private String filePath;
	
	private String warnBlock;
	
	private String contactPerson;
	
	
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
	
	

	
}
