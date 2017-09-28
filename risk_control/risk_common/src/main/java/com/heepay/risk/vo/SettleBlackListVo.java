package com.heepay.risk.vo;
/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月3日 上午10:03:15
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
public class SettleBlackListVo {
	 /**
     * 商户名称
     */
	private String regName;
	/**
     * 商户简称
     */
	private String cusName;
	/**
     * 法人证件类型
     */
	private String docType;
	/**
     * 法人证件号码
     */
	private String docCode;
	/**
     * 法定代表人姓名：黑名单
     */
	private String docName;
	/**
     * 法定代表人姓名:风险提示信息
     */
	private String legDocName;
	/**
     * 法定代表人证件类型
     */
	private String legDocType;
	/**
     * 法定代表人（负责人）证件号码
     */
	private String legDocCode;
	/**
     * 风险信息等级
     */
	private String level;
	/**
     * 风险类型
     */
	private String riskType;
	/**
     * 有效期
     */
	private String validDate;
	/**
     * 有效性
     */
	private String validStatus;

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getLegDocType() {
		return legDocType;
	}

	public void setLegDocType(String legDocType) {
		this.legDocType = legDocType;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getRiskType() {
		return riskType;
	}

	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLegDocCode() {
		return legDocCode;
	}

	public void setLegDocCode(String legDocCode) {
		this.legDocCode = legDocCode;
	}

	public String getLegDocName() {
		return legDocName;
	}

	public void setLegDocName(String legDocName) {
		this.legDocName = legDocName;
	}

	

}

 