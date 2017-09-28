package com.heepay.risk.vo;


import java.io.Serializable;
import java.util.Date;


/**
 *
 * 描    述：内部监控通道配置Entity
 *
 * 创 建 者： @author lizhen
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
public class RiskMonitorChannelVo   implements Serializable {
	
	
	private String internalChannelId;		// 主键

	private String payTypeCode;		// 通道支付类型
	private String payTypeName;		// 通道支付类型名称

	private String channelPartnerCode;		// 通道支付类型
	private String beginTime;		// 开始时间
	private String endTime;		// 结束时间
	private Integer frequency;		// 频率（分钟）
	private Integer threshold;		// 阈值（%）

	private String operator;		// 操作人
	private Date createTime;		// 创建时间
	private String channelPartnerName;
	private String channelTypeName;
	private Integer changeRate;
	public RiskMonitorChannelVo() {
	}
	public String getInternalChannelId() {
		return internalChannelId;
	}

	public void setInternalChannelId(String internalChannelId) {
		this.internalChannelId = internalChannelId;
	}

	public String getPayTypeCode() {
		return payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}
	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getChannelPartnerCode() {
		return channelPartnerCode;
	}
	public void setChannelPartnerCode(String channelPartnerCode) {
		this.channelPartnerCode = channelPartnerCode;
	}
	public String getChannelPartnerName() {
		return channelPartnerName;
	}
	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}

	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}

	public Integer getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(Integer changeRate) {
		this.changeRate = changeRate;
	}
}