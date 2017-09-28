package com.heepay.manage.modules.pbc.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.heepay.manage.common.persistence.DataEntity;

@SuppressWarnings("rawtypes")
public class PbcPaymentAccount extends DataEntity<PbcPaymentAccount>{
    private static final long serialVersionUID = 1L;

    private long paymentAccountId;

    private String featureCode;

    private String accountNumber;

    private String subjectType;

    private String accountOwnerName;

    private String accountOwnerIdType;

    private String accountOwnerId;

    private String credentialValidity;

    private String telNumber;

    private String loginId;

    private String accountName;

    private String wechatId;

    private String qq;

    private String regularIpAddress;

    private String regularDeviceNumber;

    private String onlineShopName;

    private String merchantNumber;

    private String posIdentity;

    private String posAddress;

    private String posCommonlyUsedAddress;

    private String bankId;

    private String bankName;

    private String bankAccount;

    private String cardType;

    private String cardValidation;

    private String cardExpiryDate;

    private String cardInfo;

    private String freezeSerial;

    private String paymentAccountNumber;

    private String measureType;

    private Date measureStartTime;

    private Date measureEndTime;

    private String measureDeptName;

    private String currency;

    private BigDecimal measureBalance;

    private String remark;

    private String applicationCode;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private String yes; //用于页面比较
    
    private String no; //用于页面比较
    
    private Date endOperEndTime;

    private List bankCard;//映射子数据
    
    private List transactions;
    
    //用于另一个实体映射  PbcFeedBack
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

    
    

	public List getTransactions() {
		return transactions;
	}

	public void setTransactions(List transactions) {
		this.transactions = transactions;
	}

	public long getPaymentAccountId() {
		return paymentAccountId;
	}

	public void setPaymentAccountId(long paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}

	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getAccountOwnerName() {
		return accountOwnerName;
	}

	public void setAccountOwnerName(String accountOwnerName) {
		this.accountOwnerName = accountOwnerName;
	}

	public String getAccountOwnerIdType() {
		return accountOwnerIdType;
	}

	public void setAccountOwnerIdType(String accountOwnerIdType) {
		this.accountOwnerIdType = accountOwnerIdType;
	}

	public String getAccountOwnerId() {
		return accountOwnerId;
	}

	public void setAccountOwnerId(String accountOwnerId) {
		this.accountOwnerId = accountOwnerId;
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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRegularIpAddress() {
		return regularIpAddress;
	}

	public void setRegularIpAddress(String regularIpAddress) {
		this.regularIpAddress = regularIpAddress;
	}

	public String getRegularDeviceNumber() {
		return regularDeviceNumber;
	}

	public void setRegularDeviceNumber(String regularDeviceNumber) {
		this.regularDeviceNumber = regularDeviceNumber;
	}

	public String getOnlineShopName() {
		return onlineShopName;
	}

	public void setOnlineShopName(String onlineShopName) {
		this.onlineShopName = onlineShopName;
	}

	public String getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public String getPosIdentity() {
		return posIdentity;
	}

	public void setPosIdentity(String posIdentity) {
		this.posIdentity = posIdentity;
	}

	public String getPosAddress() {
		return posAddress;
	}

	public void setPosAddress(String posAddress) {
		this.posAddress = posAddress;
	}

	public String getPosCommonlyUsedAddress() {
		return posCommonlyUsedAddress;
	}

	public void setPosCommonlyUsedAddress(String posCommonlyUsedAddress) {
		this.posCommonlyUsedAddress = posCommonlyUsedAddress;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardValidation() {
		return cardValidation;
	}

	public void setCardValidation(String cardValidation) {
		this.cardValidation = cardValidation;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public String getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	public String getFreezeSerial() {
		return freezeSerial;
	}

	public void setFreezeSerial(String freezeSerial) {
		this.freezeSerial = freezeSerial;
	}

	public String getPaymentAccountNumber() {
		return paymentAccountNumber;
	}

	public void setPaymentAccountNumber(String paymentAccountNumber) {
		this.paymentAccountNumber = paymentAccountNumber;
	}

	public String getMeasureType() {
		return measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	public Date getMeasureStartTime() {
		return measureStartTime;
	}

	public void setMeasureStartTime(Date measureStartTime) {
		this.measureStartTime = measureStartTime;
	}

	public Date getMeasureEndTime() {
		return measureEndTime;
	}

	public void setMeasureEndTime(Date measureEndTime) {
		this.measureEndTime = measureEndTime;
	}

	public String getMeasureDeptName() {
		return measureDeptName;
	}

	public void setMeasureDeptName(String measureDeptName) {
		this.measureDeptName = measureDeptName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getMeasureBalance() {
		return measureBalance;
	}

	public void setMeasureBalance(BigDecimal measureBalance) {
		this.measureBalance = measureBalance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public String getYes() {
		return yes;
	}

	public void setYes(String yes) {
		this.yes = yes;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public List getBankCard() {
		return bankCard;
	}

	public void setBankCard(List bankCard) {
		this.bankCard = bankCard;
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

}