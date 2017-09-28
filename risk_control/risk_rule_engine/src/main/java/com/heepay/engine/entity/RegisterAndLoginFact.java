package com.heepay.engine.entity;

import com.heepay.engine.AbstractRiskFact;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年7月26日 下午4:21:51
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
public class RegisterAndLoginFact extends AbstractRiskFact{
	
	private String companyName;//公司名称
	private String buziCode;//营业执照编号
	private String ownerName;//法人姓名
	private String ownerId;//法人身份证号
	private String ip;
	private String regLoginType;//1商户注册2商户后台登录3用户注册4用户后台登录
	private String ruleId;
	private boolean isForeignIp;//是否为境外ip
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRegLoginType() {
		return regLoginType;
	}
	public void setRegLoginType(String regLoginType) {
		this.regLoginType = regLoginType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBuziCode() {
		return buziCode;
	}
	public void setBuziCode(String buziCode) {
		this.buziCode = buziCode;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isForeignIp() {
		return isForeignIp;
	}

	public void setForeignIp(boolean foreignIp) {
		isForeignIp = foreignIp;
	}
}
