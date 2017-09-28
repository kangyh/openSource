package com.heepay.manage.modules.pbc.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcAccountTransDetail extends DataEntity<PbcAccountTransDetail>{
    private static final long serialVersionUID = 1L;

    private long transDetailId;

    private String subjectType;

    private String onlineShopName;
    
    private String accountNumber;

    private String accountType;

    private String accountStatus;

    private String currency;

    private BigDecimal accountBalance;

    private String accountInfo;

    private Date accountOpenTime;

    private String accountOpenIpAddress;

    private String accountOpenMachineNumber;

    private Date lastTransactionTime;

    private String orderId;

    private String transactionType;

    private String paymentType;

    private String borrowingSigns;

    private Date transactionTime;

    private BigDecimal transactionAmount;

    private String transactionSerial;

    private BigDecimal transAccountBalance;

    private String transCurrency;

    private String transInBankId;

    private String transInBankName;

    private String transInCardNumber;

    private String posIdentity;

    private String merchantNumber;

    private String transInAccountNumber;

    private String transactionDeviceType;

    private String transactionIp;

    private String transactionMac;

    private BigDecimal transactionLongitude;

    private BigDecimal transactionLatitude;

    private String transactionDeviceCode;

    private String bankTransactionSerial;

    private String remark;

    private String applicationCode;

    private String featureCodes;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;
    
    private String yes;
    
    private String no;

    /**
     * 反馈表的映射字段用于添加时页面显示
     */
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

    private String abnormalDescribe;

    private String reportCodes;
    
    private List<PbcAccountTransDetail>  transaction;

    
    
    
  	public String getOnlineShopName() {
		return onlineShopName;
	}

	public void setOnlineShopName(String onlineShopName) {
		this.onlineShopName = onlineShopName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public List<PbcAccountTransDetail> getTransaction() {
  		return transaction;
  	}

  	public void setTransaction(List<PbcAccountTransDetail> transaction) {
  		this.transaction = transaction;
  	}
    

	public long getTransDetailId() {
		return transDetailId;
	}

	public void setTransDetailId(long transDetailId) {
		this.transDetailId = transDetailId;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(String accountInfo) {
		this.accountInfo = accountInfo;
	}

	public Date getAccountOpenTime() {
		return accountOpenTime;
	}

	public void setAccountOpenTime(Date accountOpenTime) {
		this.accountOpenTime = accountOpenTime;
	}

	public String getAccountOpenIpAddress() {
		return accountOpenIpAddress;
	}

	public void setAccountOpenIpAddress(String accountOpenIpAddress) {
		this.accountOpenIpAddress = accountOpenIpAddress;
	}

	public String getAccountOpenMachineNumber() {
		return accountOpenMachineNumber;
	}

	public void setAccountOpenMachineNumber(String accountOpenMachineNumber) {
		this.accountOpenMachineNumber = accountOpenMachineNumber;
	}

	public Date getLastTransactionTime() {
		return lastTransactionTime;
	}

	public void setLastTransactionTime(Date lastTransactionTime) {
		this.lastTransactionTime = lastTransactionTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getBorrowingSigns() {
		return borrowingSigns;
	}

	public void setBorrowingSigns(String borrowingSigns) {
		this.borrowingSigns = borrowingSigns;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionSerial() {
		return transactionSerial;
	}

	public void setTransactionSerial(String transactionSerial) {
		this.transactionSerial = transactionSerial;
	}

	public BigDecimal getTransAccountBalance() {
		return transAccountBalance;
	}

	public void setTransAccountBalance(BigDecimal transAccountBalance) {
		this.transAccountBalance = transAccountBalance;
	}

	public String getTransCurrency() {
		return transCurrency;
	}

	public void setTransCurrency(String transCurrency) {
		this.transCurrency = transCurrency;
	}

	public String getTransInBankId() {
		return transInBankId;
	}

	public void setTransInBankId(String transInBankId) {
		this.transInBankId = transInBankId;
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

	public String getTransInAccountNumber() {
		return transInAccountNumber;
	}

	public void setTransInAccountNumber(String transInAccountNumber) {
		this.transInAccountNumber = transInAccountNumber;
	}

	public String getTransactionDeviceType() {
		return transactionDeviceType;
	}

	public void setTransactionDeviceType(String transactionDeviceType) {
		this.transactionDeviceType = transactionDeviceType;
	}

	public String getTransactionIp() {
		return transactionIp;
	}

	public void setTransactionIp(String transactionIp) {
		this.transactionIp = transactionIp;
	}

	public String getTransactionMac() {
		return transactionMac;
	}

	public void setTransactionMac(String transactionMac) {
		this.transactionMac = transactionMac;
	}

	public BigDecimal getTransactionLongitude() {
		return transactionLongitude;
	}

	public void setTransactionLongitude(BigDecimal transactionLongitude) {
		this.transactionLongitude = transactionLongitude;
	}

	public BigDecimal getTransactionLatitude() {
		return transactionLatitude;
	}

	public void setTransactionLatitude(BigDecimal transactionLatitude) {
		this.transactionLatitude = transactionLatitude;
	}

	public String getTransactionDeviceCode() {
		return transactionDeviceCode;
	}

	public void setTransactionDeviceCode(String transactionDeviceCode) {
		this.transactionDeviceCode = transactionDeviceCode;
	}

	public String getBankTransactionSerial() {
		return bankTransactionSerial;
	}

	public void setBankTransactionSerial(String bankTransactionSerial) {
		this.bankTransactionSerial = bankTransactionSerial;
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

	public String getFeatureCodes() {
		return featureCodes;
	}

	public void setFeatureCodes(String featureCodes) {
		this.featureCodes = featureCodes;
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