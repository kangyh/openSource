package com.heepay.manage.modules.settle.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
*
* 描    述：用户结算表Entity
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
public class SettleMerchantRecord extends DataEntity<SettleMerchantRecord> {
    
	private static final long serialVersionUID = 1L;

	private Long settleId; //清算ID

    private Integer merchantId; //用户编码

    private String merchantType; //用户类型

    private String transType; //业务类型（产品编码）

    private String currency; //币种(156:人民币)

    private Integer payNum; //交易总笔数

    private BigDecimal totalAmount; //交易总金额

    private Date checkTime; //会计时间

    private Date settleTime; //清算日期

    private String settleCyc; //订单结算周期

    private String settleBath; //结算单据

    private BigDecimal settleAmount; //应结算总金额

    private Date feeTime; //手续费结算日期

    private BigDecimal totalFee; //总手续费

    private String feeWay; //手续费扣除方式

    private String feeSettleCyc; //手续费结算周期

    private String checkStatus; //对账状态（N：未对账 Y：已对账）

    private String settleStatus; //结算状态（N：未结算，D：结算中 Y：已结算）
    
    private String merchantName;

    private String productName;

    private String flag; //标记操作状态

    private List<Long> merchantIds;



    private String merchantFlag; //商户标记

    private List<Integer> merchantFlagList; //商户标记

    private BigDecimal sumTotalAmount; //交易总金额总和

    private BigDecimal sumPayNum; //交易总笔数

    private BigDecimal sumSettleAmount; //订单应结算总金额

    private BigDecimal sumTotalFee; //交易总手续费

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

    public BigDecimal getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(BigDecimal sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }

    public BigDecimal getSumPayNum() {
        return sumPayNum;
    }

    public void setSumPayNum(BigDecimal sumPayNum) {
        this.sumPayNum = sumPayNum;
    }

    public BigDecimal getSumSettleAmount() {
        return sumSettleAmount;
    }

    public void setSumSettleAmount(BigDecimal sumSettleAmount) {
        this.sumSettleAmount = sumSettleAmount;
    }

    public BigDecimal getSumTotalFee() {
        return sumTotalFee;
    }

    public void setSumTotalFee(BigDecimal sumTotalFee) {
        this.sumTotalFee = sumTotalFee;
    }

    /**
     * 下面的字段用于查询  数据库中没有对应的字段
     */
    private Date beginCheckTime;		// 开始

	private Date endCheckTime;		// 结束

	private Date beginSettleTime;		// 开始

	private Date endSettleTime;		// 结束 

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public SettleMerchantRecord() {
		super();
	}

	public SettleMerchantRecord(String id){
		super(id);
	}

	@NotNull(message="用户结算id不能为空")
    public Long getSettleId() {
        return settleId;
    }

    public void setSettleId(Long settleId) {
        this.settleId = settleId;
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

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    @Length(min=0, max=3, message="币种长度必须介于 0 和 3 之间")
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

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getFeeTime() {
        return feeTime;
    }

    public void setFeeTime(Date feeTime) {
        this.feeTime = feeTime;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
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

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

    public List<Long> getMerchantIds() {
        return merchantIds;
    }

    public void setMerchantIds(List<Long> merchantIds) {
        this.merchantIds = merchantIds;
    }
}