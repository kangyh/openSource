/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：内部监控通道配置Entity
 *
 * 创 建 者： @author wj
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
public class RiskInternalMonitorChannel extends DataEntity<RiskInternalMonitorChannel> {
	
	private static final long serialVersionUID = 1L;
	private String internalChannelId;		// 主键
	private String channelTypeName;		// 通道名称
	private String channelPartnerCode;		// 通道支付类型
	private String channelTypeCode;		//通道类型
	private String channelPartnerName;		// 通道支付类型名称
	private String beginTime;		// 开始时间
	private String endTime;		// 结束时间
	private String frequency;		// 频率（分钟）
	private String threshold;		// 阈值（%）
	private String operator;		// 操作人
	private Date createTime;		// 创建时间
	private String changeRate;	//变化率（%）
	private Date updateTime;		//更新时间

	private String bankNo;
	private String channelName1;
	private String bankName;
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	
	public RiskInternalMonitorChannel() {
		super();
	}

	public RiskInternalMonitorChannel(String id){
		super(id);
	}

	public String getInternalChannelId() {
		return internalChannelId;
	}

	public void setInternalChannelId(String internalChannelId) {
		this.internalChannelId = internalChannelId;
	}
	
	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
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

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getChannelName1() {
		return channelName1;
	}

	public void setChannelName1(String channelName1) {
		this.channelName1 = channelName1;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getChannelTypeCode() {
		return channelTypeCode;
	}

	public void setChannelTypeCode(String channelTypeCode) {
		this.channelTypeCode = channelTypeCode;
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

	public String getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}