package com.heepay.manage.modules.settle.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
*
* 描    述：通道结算表Entity
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
public class SettleChannelRecord extends DataEntity<SettleChannelRecord> {
    
	private static final long serialVersionUID = 1L;

	private Long settleId; //清算ID

    private String channelCode; //通道编码

    private String channelName; //通道名称

    private String channelType; //通道类型

    private String currency; //币种(156:人民币)

    private Integer payNum; //交易总笔数

    private BigDecimal totalAmount; //交易总金额

    private Date checkTime; //会计时间

    private Date settleTime; //清算日期

    private String settleCyc; //交易结算周期

    private String settleBath; //结算单据

    private Date costTime; //成本结算日期

    private BigDecimal costAmount; //总成本

    private String costSettleCyc; //成本结算周期

    private String checkStatus; //对账状态（N：未对账 Y：已对账）

    private String settleStatus; //结算状态（N：未结算，D：结算中 Y：已结算）
    
    /**
     * 下面的字段用于查询  数据库中没有对应的字段
     */
    private Date beginSettleTime;		// 开始

	private Date endSettleTime;		// 结束 

    private String flag; //标记操作状态

    private BigDecimal sumPayNum;//交易总笔数

    private BigDecimal sumTotalAmount;//交易总金额

    private BigDecimal sumCostAmount;//交易总成本

    public BigDecimal getSumPayNum() {
        return sumPayNum;
    }

    public void setSumPayNum(BigDecimal sumPayNum) {
        this.sumPayNum = sumPayNum;
    }

    public BigDecimal getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(BigDecimal sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }

    public BigDecimal getSumCostAmount() {
        return sumCostAmount;
    }

    public void setSumCostAmount(BigDecimal sumCostAmount) {
        this.sumCostAmount = sumCostAmount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public SettleChannelRecord() {
		super();
	}

	public SettleChannelRecord(String id){
		super(id);
	}

	@NotNull(message="通道结算id不能为空")
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