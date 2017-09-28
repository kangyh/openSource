package com.heepay.manage.modules.reconciliation.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class SettleDifferRecordHis extends DataEntity<SettleDifferRecordHis> {
    /**
	 * 2017年3月22日
	 */
	private static final long serialVersionUID = -66259519130096120L;

	private Long hisId;

    private Long differId;

    private Integer merchantId;

    private String paymentId;

    private String transNo;

    private String channelCode;

    private String channelName;

    private BigDecimal successAmount;

    private BigDecimal requestAmount;

    private String checkBathNo;

    private String settleBath;

    private String channelType;

    private String productCode;

    private String productName;

    private String channleNo;

    private String differType;

    private String handleResult;

    private String handleMessage;

    private Date errorDate;

    private String updateAuthor;

    private Date operationDate;

    private BigDecimal feeAmount;

    private BigDecimal costAmount;

    private BigDecimal settleAmountPlan;

    private String transType;

    private String transStatus;

    private String isBill;

    private String isProfit;

    private String merchantOrderNo;

    private Date successTime;

    private Date payTime;

    private Date busiTime;

    private String remark;

    private String isZip;

    private String agentName;

    private Long agentCode;

    private String feeWay;

    private String bankcardType;

    private String merchantName;

    private String bankSeq;

    private String bankName;

    private String bankCode;

    private String channelProvider;

    private String payType;

    private String accountNo;

    private Date dateZip;
    
  //补单和撤单
    private String BD;//补单
    private String CD;//撤单
    private String QXCY;//取消差异
    private String QT;//取消差异
    private Date beginErrorTime;
    private Date endErrorTime;

    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

    public Long getDifferId() {
        return differId;
    }

    public void setDifferId(Long differId) {
        this.differId = differId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getCheckBathNo() {
        return checkBathNo;
    }

    public void setCheckBathNo(String checkBathNo) {
        this.checkBathNo = checkBathNo == null ? null : checkBathNo.trim();
    }

    public String getSettleBath() {
        return settleBath;
    }

    public void setSettleBath(String settleBath) {
        this.settleBath = settleBath == null ? null : settleBath.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getChannleNo() {
        return channleNo;
    }

    public void setChannleNo(String channleNo) {
        this.channleNo = channleNo == null ? null : channleNo.trim();
    }

    public String getDifferType() {
        return differType;
    }

    public void setDifferType(String differType) {
        this.differType = differType == null ? null : differType.trim();
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult == null ? null : handleResult.trim();
    }

    public String getHandleMessage() {
        return handleMessage;
    }

    public void setHandleMessage(String handleMessage) {
        this.handleMessage = handleMessage == null ? null : handleMessage.trim();
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public BigDecimal getSettleAmountPlan() {
        return settleAmountPlan;
    }

    public void setSettleAmountPlan(BigDecimal settleAmountPlan) {
        this.settleAmountPlan = settleAmountPlan;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public String getIsBill() {
        return isBill;
    }

    public void setIsBill(String isBill) {
        this.isBill = isBill == null ? null : isBill.trim();
    }

    public String getIsProfit() {
        return isProfit;
    }

    public void setIsProfit(String isProfit) {
        this.isProfit = isProfit == null ? null : isProfit.trim();
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getBusiTime() {
        return busiTime;
    }

    public void setBusiTime(Date busiTime) {
        this.busiTime = busiTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsZip() {
        return isZip;
    }

    public void setIsZip(String isZip) {
        this.isZip = isZip == null ? null : isZip.trim();
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public Long getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(Long agentCode) {
        this.agentCode = agentCode;
    }

    public String getFeeWay() {
        return feeWay;
    }

    public void setFeeWay(String feeWay) {
        this.feeWay = feeWay == null ? null : feeWay.trim();
    }

    public String getBankcardType() {
        return bankcardType;
    }

    public void setBankcardType(String bankcardType) {
        this.bankcardType = bankcardType == null ? null : bankcardType.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(String bankSeq) {
        this.bankSeq = bankSeq == null ? null : bankSeq.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getChannelProvider() {
        return channelProvider;
    }

    public void setChannelProvider(String channelProvider) {
        this.channelProvider = channelProvider == null ? null : channelProvider.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public Date getDateZip() {
        return dateZip;
    }

    public void setDateZip(Date dateZip) {
        this.dateZip = dateZip;
    }

	public String getBD() {
		return BD;
	}

	public void setBD(String bD) {
		BD = bD;
	}

	public String getCD() {
		return CD;
	}

	public void setCD(String cD) {
		CD = cD;
	}

	public String getQXCY() {
		return QXCY;
	}

	public void setQXCY(String qXCY) {
		QXCY = qXCY;
	}

	public String getQT() {
		return QT;
	}

	public void setQT(String qT) {
		QT = qT;
	}

	public Date getBeginErrorTime() {
		return beginErrorTime;
	}

	public void setBeginErrorTime(Date beginErrorTime) {
		this.beginErrorTime = beginErrorTime;
	}

	public Date getEndErrorTime() {
		return endErrorTime;
	}

	public void setEndErrorTime(Date endErrorTime) {
		this.endErrorTime = endErrorTime;
	}
}