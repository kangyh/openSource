package com.heepay.manage.modules.riskManage.util;

import java.util.List;

public class RegisterAndLoginVo {
	
	private String companyName;//公司名称
	private String buziCode;//营业执照编号
	private String ownerName;//法人姓名
	private String ownerId;//法人身份证号
	private String ip;
	private String regLoginType;//1商户注册2商户后台登录3用户注册4用户后台登录
	private String ruleId;
	private List ruleIdList;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRegLoginType() {
		return regLoginType;
	}
	public void setRegLoginType(String regLoginType) {
		this.regLoginType = regLoginType;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public List getRuleIdList() {
		return ruleIdList;
	}

	public void setRuleIdList(List ruleIdList) {
		this.ruleIdList = ruleIdList;
	}
}
