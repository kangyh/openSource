package com.heepay.risk.vo;

import java.util.List;

public class BankFraudBodyQueryVO {
	/**
     * 业务申请编号
     */
    private String applicationID;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 案件类型（0000-电信诈骗）
     */
    private String caseType;

    /**
     * 查询支付机构编号
     */
    private String onlinePayCompanyID;

    /**
     * 查询支付机构名称
     */
    private String onlinePayCompanyName;

    /**************************************************************/
    /**
     * 账户交易明细：明细查询传入参数的类型(01-支付账号)
     * 账户主体详情：明细查询传入参数的类型(01-支付账号;02-POS机编号)
     * 账户动态查询：动态查询传入参数的类型(01-个人支付账号;02-商户支付账号;03-商户号)
     * 账户动态查询解除：无该字段
     * 账户资金流动查询：明细查询传入参数的类型(02-证件号;03-手机号；04-银行卡号;05-登录帐号;06-商户号)
     */
    private String dataType;

    /**
     * 账户明细交易查询：明细查询操作的传入参数（<DataType>=01时，传入支付帐号，要求查询该帐号下所有账户明细)
     *
     * 账户主体详情查询：明细查询操作的传入参数（<DataType>=01时，传入支付帐号，要求查询该帐号绑定属性信息，
     *                                           <DataType>=02时，传入POS机编号，要求查询该绑定该POS的注册信息）
     * 账户动态查询：无该字段
     *
     * 账户动态查询解除：无该字段
     *
     * 账户资金流动查询：明细查询操作的传入参数（<DataType>=02时，传入证件号，要求查询该证件下所有支付账户信息(一对多)；
     *                                           <DataType>=03时，传入手机号，要求查询该手机号绑定的所有支付账户信息(一对多)；
     *                                           <DataType>=04时，传入银行卡号，要求查询该银行卡号绑定的所有支付账户信息(一对多)；
     *                                           <DataType>=05时，传入登录号，要求查询对应的支付帐号信息(一对一)；)
     *                                           <DataType>=06时，传入商户号，要求查询对应商户已注册的POS设备信息(一对多)；)
     */
    private String data;

    /*****************账户资金流动精确查询独有字段*****************/
    /**
     * 开户主体姓名或商户名，<DataType>=02时，必填)
     */
    private String accountOwnerName;

    /**
     * 开户主体证件类型(<DataType>=02时，必填)
     */
    private String accountOwnerIDType;

    /******************账户交易明细查询/账户动态查询独有字段*******************/
    /**
     * 账户交易明细查询：交易流水查询起始时间
     * 账户动态查询：执行起始日期(与申请时间一致)
     */
    private String startTime;

    /**
     * 账户交易明细查询：交易流水查询截止时间
     * 账户动态查询：执行截止日期
     */
    private String expireTime;

    /******************账户动态查询解除独有字段********************/
    /**
     * 原动态查询申请编号
     */
    private String originalApplicationID;

    /*****************账户交易明细查询独有字段*********************/
    /**
     * 查询类型(预留)（01-账户基本信息；02-账户交易明细； 03-账户基本信息+交易明细）
     */
    private String inquiryMode;

    /*****************账户交易明细/账户动态查询/账户资金流动查询独有字段*********************/
    /**
     * 账户交易明细：支付帐号明细查询类别（01-个人；02-商户）
     * 账户动态查询：支付账户类型(1-个人;2-商户)(<DataType>=01时必填，要求支付帐号所有动帐交易及时上报，最迟T+1)
     * 账户资金流动查询：支付帐号明细查询类别（01-个人；02-商户）
     */
    private String subjectType;

    /********************账户动态查询独有字段**********************/
    /**
     * 支付帐号（<DataType>=01时必填，要求支付帐号所有动帐交易及时上报，最迟T+1)
     */
    private String accountNumber;

    /**
     * 支付账户主体的证件类型
     */
    private String idType;

    /**
     * 支付账户主体的证件号码
     */
    private String idNumber;
    /******************************************************************/
    /**
     * 查询事由
     */
    private String reason;

    /**
     * 查询说明
     */
    private String remark;

    /**
     * 申请时间(yyyyMMddHHmmss)
     */
    private String applicationTime;

    /**
     * 申请机构编码
     */
    private String applicationOrgID;

    /**
     * 申请机构名称
     */
    private String applicationOrgName;

    /**
     * 经办人证件类型
     */
    private String operatorIDType;

    /**
     * 经办人证件号
     */
    private String operatorIDNumber;

    /**
     * 经办人姓名
     */
    private String operatorName;

    /**
     * 经办人电话
     */
    private String operatorPhoneNumber;

    /**
     * 协查人证件类型
     */
    private String investigatorIDType;

    /**
     * 协查人证件号
     */
    private String investigatorIDNumber;

    /**
     * 协查人姓名
     */
    private String investigatorName;
    /**
     * 银行编码
     */
    private String bankID;
    /**
     * 交易时间(精确到日)
     */
    private String transactionDate;

    /**
     * 法律文书、协查民警证件扫描件等附件
     */
    private List<BankFraudAttachment> attachments;

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getOnlinePayCompanyID() {
        return onlinePayCompanyID;
    }

    public void setOnlinePayCompanyID(String onlinePayCompanyID) {
        this.onlinePayCompanyID = onlinePayCompanyID;
    }

    public String getOnlinePayCompanyName() {
        return onlinePayCompanyName;
    }

    public void setOnlinePayCompanyName(String onlinePayCompanyName) {
        this.onlinePayCompanyName = onlinePayCompanyName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    public void setAccountOwnerName(String accountOwnerName) {
        this.accountOwnerName = accountOwnerName;
    }

    public String getAccountOwnerIDType() {
        return accountOwnerIDType;
    }

    public void setAccountOwnerIDType(String accountOwnerIDType) {
        this.accountOwnerIDType = accountOwnerIDType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getOriginalApplicationID() {
        return originalApplicationID;
    }

    public void setOriginalApplicationID(String originalApplicationID) {
        this.originalApplicationID = originalApplicationID;
    }

    public String getInquiryMode() {
        return inquiryMode;
    }

    public void setInquiryMode(String inquiryMode) {
        this.inquiryMode = inquiryMode;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getidType() {
        return idType;
    }

    public void setidType(String idType) {
        this.idType = idType;
    }

    public String getidNumber() {
        return idNumber;
    }

    public void setidNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getApplicationOrgID() {
        return applicationOrgID;
    }

    public void setApplicationOrgID(String applicationOrgID) {
        this.applicationOrgID = applicationOrgID;
    }

    public String getApplicationOrgName() {
        return applicationOrgName;
    }

    public void setApplicationOrgName(String applicationOrgName) {
        this.applicationOrgName = applicationOrgName;
    }

    public String getOperatorIDType() {
        return operatorIDType;
    }

    public void setOperatorIDType(String operatorIDType) {
        this.operatorIDType = operatorIDType;
    }

    public String getOperatorIDNumber() {
        return operatorIDNumber;
    }

    public void setOperatorIDNumber(String operatorIDNumber) {
        this.operatorIDNumber = operatorIDNumber;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorPhoneNumber() {
        return operatorPhoneNumber;
    }

    public void setOperatorPhoneNumber(String operatorPhoneNumber) {
        this.operatorPhoneNumber = operatorPhoneNumber;
    }

    public String getInvestigatorIDType() {
        return investigatorIDType;
    }

    public void setInvestigatorIDType(String investigatorIDType) {
        this.investigatorIDType = investigatorIDType;
    }

    public String getInvestigatorIDNumber() {
        return investigatorIDNumber;
    }

    public void setInvestigatorIDNumber(String investigatorIDNumber) {
        this.investigatorIDNumber = investigatorIDNumber;
    }

    public String getInvestigatorName() {
        return investigatorName;
    }

    public void setInvestigatorName(String investigatorName) {
        this.investigatorName = investigatorName;
    }

	public String getBankID() {
		return bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public List<BankFraudAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<BankFraudAttachment> attachments) {
		this.attachments = attachments;
	}
}
