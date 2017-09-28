package com.heepay.manage.modules.settle.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.crypto.Data;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
*
* 描    述：用户清算记录表Entity
*
* 创 建 者： @author wangdong
* 创建时间：
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
public class ClearingMerchantRecord extends DataEntity<ClearingMerchantRecord> {
    
	private static final long serialVersionUID = 1L;

    private Long clearingId;

    private Integer merchantId;

    private String merchantType;

    private String productCode;

    private String currency;

    private String transNo;

    private String transNoOld;

    private BigDecimal requestAmount;

    private Date successTime;

    private Date settleTime;

    private String settleNo;

    private Date settleTimePlan;

    private BigDecimal settleAmountPlan;

    private String settleCyc;

    private String settleBath;

    private Date feeTime;

    private BigDecimal fee;

    private String feeWay;

    private String feeSettleCyc;

    private String checkStatus;

    private String checkFlg;

    private String settleStatus;

    private Date finishTime;

    private String checkBath;

    private Long checkNum;

    private String transType;

    private String isProfit;

    private String merchantOrderNo;

    private Date busiTime;

    private String remark;

    private String isZip;

    private String agentName;

    private Long agentCode;

    private String bankcardType;

    private String productName;

    private String merchantName;

    private String payType;

    private Date createTime;

    private String accountNo;

    private Date payTime;



    /**
     * 下面的字段用于查询  数据库中没有对应的字段
     */
    private Date beginCheckTime;		// 开始 
	private Date endCheckTime;		// 结束 
	private Date beginSettleTime;		// 开始 
	private Date endSettleTime;		// 结束 
	private BigDecimal successAmount; //成功金额
	private BigDecimal settleAmount; //结算到商户金额
	private BigDecimal feeAmount; //手续费总金额
	private String channelCode;//通道类型
	private String channelName;//通道名称
	private String bankCode;//银行卡类型
	private String bankName;//银行名称
	private String groupby;//分组
	private String countNum;//笔数

    private String merchantFlag; //商户标记

    private List<Integer> merchantFlagList; //商户标记

	private BigDecimal sumRequestAmount; //实际支付金额总和

	private BigDecimal sumSettleAmountPlan; //订单应结算金额总和

	private BigDecimal sumFee; //订单手续费总和

    public String getMerchantFlag() {
        return merchantFlag;
    }

    public void setMerchantFlag(String merchantFlag) {
        this.merchantFlag = merchantFlag;
    }

    public List<Integer> getMerchantFlagList() {
        return merchantFlagList;
    }

    public void setMerchantFlagList(List<Integer> merchantFlagList) {
        this.merchantFlagList = merchantFlagList;
    }

    public BigDecimal getSumRequestAmount() {
        return sumRequestAmount;
    }

    public void setSumRequestAmount(BigDecimal sumRequestAmount) {
        this.sumRequestAmount = sumRequestAmount;
    }

    public BigDecimal getSumSettleAmountPlan() {
        return sumSettleAmountPlan;
    }

    public void setSumSettleAmountPlan(BigDecimal sumSettleAmountPlan) {
        this.sumSettleAmountPlan = sumSettleAmountPlan;
    }

    public BigDecimal getSumFee() {
        return sumFee;
    }

    public void setSumFee(BigDecimal sumFee) {
        this.sumFee = sumFee;
    }

    public ClearingMerchantRecord() {
		super();
	}

	public ClearingMerchantRecord(String id){
		super(id);
	}

	@NotNull(message="用户清算记录id不能为空")
    public Long getClearingId() {
        return clearingId;
    }

    public void setClearingId(Long clearingId) {
        this.clearingId = clearingId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType == null ? null : merchantType.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    @Length(min=0, max=3, message="币种长度必须介于 0 和 3 之间")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public String getTransNoOld() {
        return transNoOld;
    }

    public void setTransNoOld(String transNoOld) {
        this.transNoOld = transNoOld == null ? null : transNoOld.trim();
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo == null ? null : settleNo.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSettleTimePlan() {
        return settleTimePlan;
    }

    public void setSettleTimePlan(Date settleTimePlan) {
        this.settleTimePlan = settleTimePlan;
    }

    public BigDecimal getSettleAmountPlan() {
        return settleAmountPlan;
    }

    public void setSettleAmountPlan(BigDecimal settleAmountPlan) {
        this.settleAmountPlan = settleAmountPlan;
    }

    public String getSettleCyc() {
        return settleCyc;
    }

    public void setSettleCyc(String settleCyc) {
        this.settleCyc = settleCyc == null ? null : settleCyc.trim();
    }

    public String getSettleBath() {
        return settleBath;
    }

    public void setSettleBath(String settleBath) {
        this.settleBath = settleBath == null ? null : settleBath.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getFeeTime() {
        return feeTime;
    }

    public void setFeeTime(Date feeTime) {
        this.feeTime = feeTime;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getFeeWay() {
        return feeWay;
    }

    public void setFeeWay(String feeWay) {
        this.feeWay = feeWay == null ? null : feeWay.trim();
    }

    public String getFeeSettleCyc() {
        return feeSettleCyc;
    }

    public void setFeeSettleCyc(String feeSettleCyc) {
        this.feeSettleCyc = feeSettleCyc == null ? null : feeSettleCyc.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getCheckFlg() {
        return checkFlg;
    }

    public void setCheckFlg(String checkFlg) {
        this.checkFlg = checkFlg == null ? null : checkFlg.trim();
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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
	
	public String getCheckBath() {
        return checkBath;
    }
	
	public void setCheckBath(String checkBath) {
        this.checkBath = checkBath == null ? null : checkBath.trim();
    }
	
	public String getTransType() {
    	return transType;
    }
    
    public void setTransType(String transType) {
    	this.transType = transType == null ? null : transType.trim();
    }
    
    public Long getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Long checkNum) {
        this.checkNum = checkNum;
    }

	public String getIsProfit() {
		return isProfit;
	}

	public void setIsProfit(String isProfit) {
		this.isProfit = isProfit;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBankcardType() {
		return bankcardType;
	}

	public void setBankcardType(String bankcardType) {
		this.bankcardType = bankcardType;
	}

	public BigDecimal getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Date getBeginSettleTime() {
		return beginSettleTime;
	}

	public void setBeginSettleTime(Date beginSettleTime) {
		this.beginSettleTime = beginSettleTime;
	}

	public Date getEndSettleTime() {
		return endSettleTime;
	}

	public void setEndSettleTime(Date endSettleTime) {
		this.endSettleTime = endSettleTime;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getGroupby() {
		return groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}