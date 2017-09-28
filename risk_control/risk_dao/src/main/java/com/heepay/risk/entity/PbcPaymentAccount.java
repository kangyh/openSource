package com.heepay.risk.entity;

import java.math.BigDecimal;
import java.util.Date;


public class PbcPaymentAccount {
    private static final long serialVersionUID = 1L;

	private Long paymentAccountId;

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

    private String regularIp;

    private String regularDeviceNumber;

    private String onlineShopName;

    private String merchantNumber;

    private String posId;

    private String posAddress;

    private String posCommonlyUsedAddress;

    private String bankId;

    private String bankName;

    private String bankNumber;

    private String cardType;

    private String cardValidationStatus;

    private String cardExpiryDate;

    private String cardInfo;

    private String freezeSerial;

    private String accountNumber;

    private String measureType;

    private Date measureStartTime;

    private Date measureEndTime;

    private String measureDeptName;

    private String currency;

    private BigDecimal measureBalance;

    private String remark;

    private Long feedBackId;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

    //用于另一个实体映射  PbcFeedBack

    private String mode;

    private String toId;

    private String transTypeCode;

    private String transSerialNumber;

    private String applicationId;

    private String result;

    private String operatorName;

    private String operatorPhoneNumber;

    private String feedBackOrgName;

    private String feedBackRemark;

    private String requestEventType;

    private String applicationOrgCode;

    private Date applicationTime;

    private String applicationOrgName;

    private String emergencyLevel;

    private String feedbackLimit;

    private String status;

    private String returnCode;

    private String returnResults;

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
    
    
    
    public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public String getTransTypeCode() {
		return transTypeCode;
	}

	public void setTransTypeCode(String transTypeCode) {
		this.transTypeCode = transTypeCode;
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

	public String getApplicationOrgCode() {
		return applicationOrgCode;
	}

	public void setApplicationOrgCode(String applicationOrgCode) {
		this.applicationOrgCode = applicationOrgCode;
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
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

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnResults() {
		return returnResults;
	}

	public void setReturnResults(String returnResults) {
		this.returnResults = returnResults;
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

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}
	
    public Long getPaymentAccountId() {
        return paymentAccountId;
    }

    public void setPaymentAccountId(Long paymentAccountId) {
        this.paymentAccountId = paymentAccountId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType == null ? null : subjectType.trim();
    }

    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    public void setAccountOwnerName(String accountOwnerName) {
        this.accountOwnerName = accountOwnerName == null ? null : accountOwnerName.trim();
    }

    public String getAccountOwnerIdType() {
        return accountOwnerIdType;
    }

    public void setAccountOwnerIdType(String accountOwnerIdType) {
        this.accountOwnerIdType = accountOwnerIdType == null ? null : accountOwnerIdType.trim();
    }

    public String getAccountOwnerId() {
        return accountOwnerId;
    }

    public void setAccountOwnerId(String accountOwnerId) {
        this.accountOwnerId = accountOwnerId == null ? null : accountOwnerId.trim();
    }

    public String getCredentialValidity() {
        return credentialValidity;
    }

    public void setCredentialValidity(String credentialValidity) {
        this.credentialValidity = credentialValidity == null ? null : credentialValidity.trim();
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber == null ? null : telNumber.trim();
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId == null ? null : loginId.trim();
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getRegularIp() {
        return regularIp;
    }

    public void setRegularIp(String regularIp) {
        this.regularIp = regularIp == null ? null : regularIp.trim();
    }

    public String getRegularDeviceNumber() {
        return regularDeviceNumber;
    }

    public void setRegularDeviceNumber(String regularDeviceNumber) {
        this.regularDeviceNumber = regularDeviceNumber == null ? null : regularDeviceNumber.trim();
    }

    public String getOnlineShopName() {
        return onlineShopName;
    }

    public void setOnlineShopName(String onlineShopName) {
        this.onlineShopName = onlineShopName == null ? null : onlineShopName.trim();
    }

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber == null ? null : merchantNumber.trim();
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId == null ? null : posId.trim();
    }

    public String getPosAddress() {
        return posAddress;
    }

    public void setPosAddress(String posAddress) {
        this.posAddress = posAddress == null ? null : posAddress.trim();
    }

    public String getPosCommonlyUsedAddress() {
        return posCommonlyUsedAddress;
    }

    public void setPosCommonlyUsedAddress(String posCommonlyUsedAddress) {
        this.posCommonlyUsedAddress = posCommonlyUsedAddress == null ? null : posCommonlyUsedAddress.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber == null ? null : bankNumber.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCardValidationStatus() {
        return cardValidationStatus;
    }

    public void setCardValidationStatus(String cardValidationStatus) {
        this.cardValidationStatus = cardValidationStatus == null ? null : cardValidationStatus.trim();
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate == null ? null : cardExpiryDate.trim();
    }

    public String getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo == null ? null : cardInfo.trim();
    }

    public String getFreezeSerial() {
        return freezeSerial;
    }

    public void setFreezeSerial(String freezeSerial) {
        this.freezeSerial = freezeSerial == null ? null : freezeSerial.trim();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType == null ? null : measureType.trim();
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
        this.measureDeptName = measureDeptName == null ? null : measureDeptName.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(Long feedBackId) {
        this.feedBackId = feedBackId;
    }
}