package com.heepay.manage.modules.settle.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
*
* 描    述：通道清算记录表Entity
*
* 创 建 者： @author wangl
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
public class ClearingChannelRecord extends DataEntity<ClearingChannelRecord> {
    
	private static final long serialVersionUID = 1L;

    private Long clearingId;

    private String channelCode;

    private String channelName;

    private String channelType;

    private Date payTime;

    private String currency;

    private String paymentId;

    private String paymentIdOld;

    private String transNo;

    private String transNoOld;

    private BigDecimal successAmount;

    private Date channelTime;

    private Date checkTime;

    private Date settleTime;

    private String settleNo;

    private Date settleTimePlan;

    private String settleCyc;

    private String settleBath;

    private Date costTime;

    private BigDecimal costAmount;

    private String costWay;

    private String costSettleCyc;

    private String checkStatus;

    private String checkFlg;

    private String settleStatus;

    private Date finishTime;

    private String checkBath;

    private Long checkNum;

    private String transType;

    private String remark;

    private String isZip;

    private String bankSeq;

    private String bankName;

    private String bankCode;

    private String channelProvider;

    private BigDecimal sumSuccessAmount; //实际支付金额

    private BigDecimal sumCostAmount; //交易成本

    public BigDecimal getSumSuccessAmount() {
        return sumSuccessAmount;
    }

    public void setSumSuccessAmount(BigDecimal sumSuccessAmount) {
        this.sumSuccessAmount = sumSuccessAmount;
    }

    public BigDecimal getSumCostAmount() {
        return sumCostAmount;
    }

    public void setSumCostAmount(BigDecimal sumCostAmount) {
        this.sumCostAmount = sumCostAmount;
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

    /**
     * 下面的字段用于查询  数据库中没有对应的字段
     */
    private Date beginChannelTime;		// 开始 
	private Date endChannelTime;		// 结束 
    
    
    public ClearingChannelRecord() {
		super();
	}

	public ClearingChannelRecord(String id){
		super(id);
	}

	@NotNull(message="通道清算记录id不能为空")
    public Long getClearingId() {
        return clearingId;
    }

    public void setClearingId(Long clearingId) {
        this.clearingId = clearingId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }
    @Length(min=1, max=50, message="通道名称长度必须介于 1 和 50 之间")
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Length(min=0, max=3, message="币种长度必须介于 0 和 3 之间")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getPaymentIdOld() {
        return paymentIdOld;
    }

    public void setPaymentIdOld(String paymentIdOld) {
        this.paymentIdOld = paymentIdOld == null ? null : paymentIdOld.trim();
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

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getChannelTime() {
        return channelTime;
    }

    public void setChannelTime(Date channelTime) {
        this.channelTime = channelTime;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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
    public Date getCostTime() {
        return costTime;
    }

    public void setCostTime(Date costTime) {
        this.costTime = costTime;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public String getCostWay() {
        return costWay;
    }

    public void setCostWay(String costWay) {
        this.costWay = costWay == null ? null : costWay.trim();
    }

    public String getCostSettleCyc() {
        return costSettleCyc;
    }

    public void setCostSettleCyc(String costSettleCyc) {
        this.costSettleCyc = costSettleCyc == null ? null : costSettleCyc.trim();
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

	public Date getBeginChannelTime() {
		return beginChannelTime;
	}

	public void setBeginChannelTime(Date beginChannelTime) {
		this.beginChannelTime = beginChannelTime;
	}

	public Date getEndChannelTime() {
		return endChannelTime;
	}

	public void setEndChannelTime(Date endChannelTime) {
		this.endChannelTime = endChannelTime;
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
}