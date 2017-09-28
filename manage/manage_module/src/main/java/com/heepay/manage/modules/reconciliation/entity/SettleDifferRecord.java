package com.heepay.manage.modules.reconciliation.entity;

import com.heepay.manage.common.persistence.DataEntity;
import java.util.Date;

public class SettleDifferRecord extends DataEntity<SettleDifferRecord> {
	private static final long serialVersionUID = 1L;

    private Long differId;

    private Integer merchantId;

    private String paymentId;

    private String transNo;

    private String channelCode;

    private String channelName;

    private String successAmount;

    private String requestAmount;

    private String checkBathno;

    private String settleBath;

    private String channelType;

    private String productCode;

    private String productName;

    private String channleNo;

    private String differType;

    private String handleResult;

    private String handleMessage;

    private String updateAuthor;

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

    private String receiptsPath;

    /**
     * 非映射字段（数据库中没有对应的字段，用于查询）
     */
    private Date errorDate;//差错时间
    
    private Date operationDate;//操作时间
    
    private String errorStatus;//差错状态
    
    private String cancellationsMark;//撤单标识
    
    private String fillMark;//补单标识
    
    private Date beginCheckTime;
    
    private Date endCheckTime;
    
    private String transType;//手续费
   
    //补单和撤单
    private String BD;//补单

    private String CD;//撤单

    private String QXCY;//取消差异

    private String QT;//取消差异

    private String feeAmount;//取消差异

    private String costAmount;//成本

    private String settleAmountPlan;//交易金额

    private String transStatus;//支付状态

    private String isBill;//记账标记

    private String isProfit;//分润标识

    private String currency;//币种

    private String settleCyc;//订单结算周期

    private String oldData;

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    public String getSettleCyc() {
        return settleCyc;
    }

    public void setSettleCyc(String settleCyc) {
        this.settleCyc = settleCyc;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceiptsPath() {
        return receiptsPath;
    }

    public void setReceiptsPath(String receiptsPath) {
        this.receiptsPath = receiptsPath;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
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
        this.remark = remark;
    }

    public String getIsZip() {
        return isZip;
    }

    public void setIsZip(String isZip) {
        this.isZip = isZip;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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
        this.feeWay = feeWay;
    }

    public String getBankcardType() {
        return bankcardType;
    }

    public void setBankcardType(String bankcardType) {
        this.bankcardType = bankcardType;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(String bankSeq) {
        this.bankSeq = bankSeq;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getChannelProvider() {
        return channelProvider;
    }

    public void setChannelProvider(String channelProvider) {
        this.channelProvider = channelProvider;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getIsBill() {
		return isBill;
	}

	public void setIsBill(String isBill) {
		this.isBill = isBill;
	}

	public String getIsProfit() {
		return isProfit;
	}

	public void setIsProfit(String isProfit) {
		this.isProfit = isProfit;
	}

	public String getQT() {
		return QT;
	}

	public void setQT(String qT) {
		QT = qT;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(String costAmount) {
		this.costAmount = costAmount;
	}

	public String getSettleAmountPlan() {
		return settleAmountPlan;
	}

	public void setSettleAmountPlan(String settleAmountPlan) {
		this.settleAmountPlan = settleAmountPlan;
	}

	public String getQXCY() {
		return QXCY;
	}

	public void setQXCY(String qXCY) {
		QXCY = qXCY;
	}

	public String getBD() {
		return BD;
	}

	public void setBD(String BD) {
		this.BD = BD;
	}

	public String getCD() {
		return CD;
	}

	public void setCD(String CD) {
		this.CD = CD;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getBeginCheckTime() {
		return beginCheckTime;
	}

	public void setBeginCheckTime(Date beginCheckTime) {
		this.beginCheckTime = beginCheckTime;
	}

	public Date getEndCheckTime() {
		return endCheckTime;
	}

	public void setEndCheckTime(Date endCheckTime) {
		this.endCheckTime = endCheckTime;
	}

	public String getFillMark() {
		return fillMark;
	}

	public void setFillMark(String fillMark) {
		this.fillMark = fillMark;
	}

	public String getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}

	public String getCancellationsMark() {
		return cancellationsMark;
	}

	public void setCancellationsMark(String cancellationsMark) {
		this.cancellationsMark = cancellationsMark;
	}

	public Date getErrorDate() {
		return errorDate;
	}

	public void setErrorDate(Date errorDate) {
		this.errorDate = errorDate;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
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

    public String getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(String successAmount) {
        this.successAmount = successAmount;
    }

    public String getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(String requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getCheckBathno() {
        return checkBathno;
    }

    public void setCheckBathno(String checkBathno) {
        this.checkBathno = checkBathno == null ? null : checkBathno.trim();
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
}