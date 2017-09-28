package com.heepay.manage.modules.pbc.entity;

import java.util.Date;
import java.util.List;

import com.heepay.manage.common.persistence.DataEntity;

@SuppressWarnings("rawtypes")
public class PbcFeedBack extends DataEntity<PbcFeedBack>{
    private static final long serialVersionUID = 1L;

    private long feedBackId;

    private String feedType;

    private String mode;

    private String toId;

    private String txCode;

    private String transSerialNumber;
    
    private String applicationId;

    private String result;

    private String operatorName;

    private String operatorPhoneNumber;

    private String feedBackOrgName;

    private String feedBackRemark;

    private String remark;

    private String requestEventType;

    private Date applicationTime;

    private String applicationOrgCode;

    private String applicationOrgName;

    private String emergencyLevel;

    private String feedbackLimit;

    private String status;

    private String code;

    private String description;

    private Date riskTime;

    private String riskOperator;

    private String riskRemark;

    private Integer num;

    private String riskStatus;

    private String failRemark;

    private String paramType;

    private String params;

    private Date reportTime;

    private Date dealTime;

    private String featureCodes;

    private String abnormalDescribe;

    private String reportCodes;
    
    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

    private String yes;
    
    private String changeType;

    private String open;
    
    private String subjectType;
    
    private String accountNumber;
    
    private String queryFlg;
    
	private List accountsList;

	private PbcAccountDetail detail;
	
	private String xml;//用于页面查询出符合主动上报的数据
	
	private String queryXml;
	
	private String queryYes;
	
	
	//  
	private String onlinePayCompanyType;
	private String transactionType;
	private String paymentType;
	private String currency;
	private String transferAmount;
	private String transInBankID;
	private String transInBankName;
	private String transInCardNumber;
	private String transInAccountNumber;
	private String posIdentity;
	private String merchantNumber;
	private String transOutBankID;
	private String transOutBankName;
	private String transOutCardNumber;
	private String transOutAccountNumber;
	private String transactionDeviceType;
	private String transactionIP;
	private String transactionMAC;
	private String transactionDeviceCode;
	private String transactionLongitude;
	private String transactionLatitude;
	private String bankTransactionSerial;
	private String feedbackOrgName;
	private String feedbackRemark;
    

	//主体详情
	private String accountOwnerName;
	private String accountOwnerIDType;
	private String accountOwnerID;
	private String credentialValidity;
	private String telNumber;
	private String onlineShopName;
	private List<PbcBankInfo> pbcBankInfo;
	private String dataType;
	private String 	data;
	private List<PbcAccountDetail> list;
	
	
	//解除反馈
    private String caseNumber;
    private String applicationCode;
    private String OnlinePayCompanyID;
    private String OnlinePayCompanyName;
    
    
    
	
    public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public List getAccountsList() {
		return accountsList;
	}

	public void setAccountsList(List accountsList) {
		this.accountsList = accountsList;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public long getFeedBackId() {
		return feedBackId;
	}

	public void setFeedBackId(long feedBackId) {
		this.feedBackId = feedBackId;
	}

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getTransSerialNumber() {
		return transSerialNumber;
	}

	public void setTransSerialNumber(String transSerialNumber) {
		this.transSerialNumber = transSerialNumber;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public String getFeedBackOrgName() {
		return feedBackOrgName;
	}

	public void setFeedBackOrgName(String feedBackOrgName) {
		this.feedBackOrgName = feedBackOrgName;
	}

	public String getFeedBackRemark() {
		return feedBackRemark;
	}

	public void setFeedBackRemark(String feedBackRemark) {
		this.feedBackRemark = feedBackRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRequestEventType() {
		return requestEventType;
	}

	public void setRequestEventType(String requestEventType) {
		this.requestEventType = requestEventType;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getApplicationOrgCode() {
		return applicationOrgCode;
	}

	public void setApplicationOrgCode(String applicationOrgCode) {
		this.applicationOrgCode = applicationOrgCode;
	}

	public String getApplicationOrgName() {
		return applicationOrgName;
	}

	public void setApplicationOrgName(String applicationOrgName) {
		this.applicationOrgName = applicationOrgName;
	}

	public String getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(String emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}

	public String getFeedbackLimit() {
		return feedbackLimit;
	}

	public void setFeedbackLimit(String feedbackLimit) {
		this.feedbackLimit = feedbackLimit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRiskTime() {
		return riskTime;
	}

	public void setRiskTime(Date riskTime) {
		this.riskTime = riskTime;
	}

	public String getRiskOperator() {
		return riskOperator;
	}

	public void setRiskOperator(String riskOperator) {
		this.riskOperator = riskOperator;
	}

	public String getRiskRemark() {
		return riskRemark;
	}

	public void setRiskRemark(String riskRemark) {
		this.riskRemark = riskRemark;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}

	public String getFailRemark() {
		return failRemark;
	}

	public void setFailRemark(String failRemark) {
		this.failRemark = failRemark;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getFeatureCodes() {
		return featureCodes;
	}

	public void setFeatureCodes(String featureCodes) {
		this.featureCodes = featureCodes;
	}

	public String getAbnormalDescribe() {
		return abnormalDescribe;
	}

	public void setAbnormalDescribe(String abnormalDescribe) {
		this.abnormalDescribe = abnormalDescribe;
	}

	public String getReportCodes() {
		return reportCodes;
	}

	public void setReportCodes(String reportCodes) {
		this.reportCodes = reportCodes;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public String getYes() {
		return yes;
	}

	public void setYes(String yes) {
		this.yes = yes;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
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

	public PbcAccountDetail getDetail() {
		return detail;
	}

	public void setDetail(PbcAccountDetail detail) {
		this.detail = detail;
	}

	public String getOnlinePayCompanyType() {
		return onlinePayCompanyType;
	}

	public void setOnlinePayCompanyType(String onlinePayCompanyType) {
		this.onlinePayCompanyType = onlinePayCompanyType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getTransInBankID() {
		return transInBankID;
	}

	public void setTransInBankID(String transInBankID) {
		this.transInBankID = transInBankID;
	}

	public String getTransInBankName() {
		return transInBankName;
	}

	public void setTransInBankName(String transInBankName) {
		this.transInBankName = transInBankName;
	}

	public String getTransInCardNumber() {
		return transInCardNumber;
	}

	public void setTransInCardNumber(String transInCardNumber) {
		this.transInCardNumber = transInCardNumber;
	}

	public String getTransInAccountNumber() {
		return transInAccountNumber;
	}

	public void setTransInAccountNumber(String transInAccountNumber) {
		this.transInAccountNumber = transInAccountNumber;
	}

	public String getPosIdentity() {
		return posIdentity;
	}

	public void setPosIdentity(String posIdentity) {
		this.posIdentity = posIdentity;
	}

	public String getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public String getTransOutBankID() {
		return transOutBankID;
	}

	public void setTransOutBankID(String transOutBankID) {
		this.transOutBankID = transOutBankID;
	}

	public String getTransOutBankName() {
		return transOutBankName;
	}

	public void setTransOutBankName(String transOutBankName) {
		this.transOutBankName = transOutBankName;
	}

	public String getTransOutCardNumber() {
		return transOutCardNumber;
	}

	public void setTransOutCardNumber(String transOutCardNumber) {
		this.transOutCardNumber = transOutCardNumber;
	}

	public String getTransOutAccountNumber() {
		return transOutAccountNumber;
	}

	public void setTransOutAccountNumber(String transOutAccountNumber) {
		this.transOutAccountNumber = transOutAccountNumber;
	}

	public String getTransactionDeviceType() {
		return transactionDeviceType;
	}

	public void setTransactionDeviceType(String transactionDeviceType) {
		this.transactionDeviceType = transactionDeviceType;
	}

	public String getTransactionIP() {
		return transactionIP;
	}

	public void setTransactionIP(String transactionIP) {
		this.transactionIP = transactionIP;
	}

	public String getTransactionMAC() {
		return transactionMAC;
	}

	public void setTransactionMAC(String transactionMAC) {
		this.transactionMAC = transactionMAC;
	}

	public String getTransactionDeviceCode() {
		return transactionDeviceCode;
	}

	public void setTransactionDeviceCode(String transactionDeviceCode) {
		this.transactionDeviceCode = transactionDeviceCode;
	}

	public String getTransactionLongitude() {
		return transactionLongitude;
	}

	public void setTransactionLongitude(String transactionLongitude) {
		this.transactionLongitude = transactionLongitude;
	}

	public String getTransactionLatitude() {
		return transactionLatitude;
	}

	public void setTransactionLatitude(String transactionLatitude) {
		this.transactionLatitude = transactionLatitude;
	}

	public String getBankTransactionSerial() {
		return bankTransactionSerial;
	}

	public void setBankTransactionSerial(String bankTransactionSerial) {
		this.bankTransactionSerial = bankTransactionSerial;
	}

	public String getFeedbackOrgName() {
		return feedbackOrgName;
	}

	public void setFeedbackOrgName(String feedbackOrgName) {
		this.feedbackOrgName = feedbackOrgName;
	}

	public String getFeedbackRemark() {
		return feedbackRemark;
	}

	public void setFeedbackRemark(String feedbackRemark) {
		this.feedbackRemark = feedbackRemark;
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

	public String getAccountOwnerID() {
		return accountOwnerID;
	}

	public void setAccountOwnerID(String accountOwnerID) {
		this.accountOwnerID = accountOwnerID;
	}

	public String getCredentialValidity() {
		return credentialValidity;
	}

	public void setCredentialValidity(String credentialValidity) {
		this.credentialValidity = credentialValidity;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getOnlineShopName() {
		return onlineShopName;
	}

	public void setOnlineShopName(String onlineShopName) {
		this.onlineShopName = onlineShopName;
	}

	public List<PbcBankInfo> getPbcBankInfo() {
		return pbcBankInfo;
	}

	public void setPbcBankInfo(List<PbcBankInfo> pbcBankInfo) {
		this.pbcBankInfo = pbcBankInfo;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public String getOnlinePayCompanyID() {
		return OnlinePayCompanyID;
	}

	public void setOnlinePayCompanyID(String onlinePayCompanyID) {
		OnlinePayCompanyID = onlinePayCompanyID;
	}

	public String getOnlinePayCompanyName() {
		return OnlinePayCompanyName;
	}

	public void setOnlinePayCompanyName(String onlinePayCompanyName) {
		OnlinePayCompanyName = onlinePayCompanyName;
	}

	public String getQueryFlg() {
		return queryFlg;
	}

	public void setQueryFlg(String queryFlg) {
		this.queryFlg = queryFlg;
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

	public List<PbcAccountDetail> getList() {
		return list;
	}

	public void setList(List<PbcAccountDetail> list) {
		this.list = list;
	}

	public String getQueryXml() {
		return queryXml;
	}

	public void setQueryXml(String queryXml) {
		this.queryXml = queryXml;
	}

	public String getQueryYes() {
		return queryYes;
	}

	public void setQueryYes(String queryYes) {
		this.queryYes = queryYes;
	}



	
}