package com.heepay.manage.modules.settle.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * *
 * 
* 
* 描    述：通道结算历史
*
* 创 建 者： wangjie
* 创建时间：  2017年3月10日下午1:51:45
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
public class SettleChannelRecordHis extends DataEntity<SettleChannelRecordHis>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long hisId;

    private Long settleId;

    private String channelCode;

    private String channelName;

    private String channelType;

    private String currency;

    private Integer payNum;

    private BigDecimal totalAmount;

    private Date checkTime;

    private Date settleTime;

    private String settleCyc;

    private String settleBath;

    private Date costTime;

    private BigDecimal costAmount;

    private String costSettleCyc;

    private String checkStatus;

    private String settleStatus;

    private Date dateZip;
    
    private Date beginSettleTime;
    private Date endSettleTime;

    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

    public Long getSettleId() {
        return settleId;
    }

    public void setSettleId(Long settleId) {
        this.settleId = settleId;
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

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
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

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
    }

    public Date getDateZip() {
        return dateZip;
    }

    public void setDateZip(Date dateZip) {
        this.dateZip = dateZip;
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

	
}