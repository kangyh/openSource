/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：通道数据统计Entity
 *
 * 创 建 者： @author id
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
public class StatisticsChannelRecord extends DataEntity<StatisticsChannelRecord> {
	
	private static final long serialVersionUID = 1L;
	private String channelCode;		// channel_code通道代码
	private String channelPartners;		// 通道合作方
	private String bankName;		// 银行名称
	private String channelPartnersName;		// 通道合作方名称
	private String channelType;		// 支付通道类型
	private String bankCode;		// 银行名称
	private String totalCount;		// 总笔数
	private String totalAmount;		// 总金额(元)
	private String sucessCount;		// 成功总笔数
	private String channelTypeName;		// 通道类型名称
	private String sucessAmount;		// 成功总金额(元)
	private String failCount;		// 失败总笔数
	private String failAmount;		// 失败总金额(元)
	private Date statisticTime;		// 统计时间
	private Date createTime;		// 创建时间
	private String currency;		// 货币类型
	private String remark;		// 备注
	private String statisticHour;		// 按小时统计
	private Date beginStatisticTime;		// 开始 统计时间
	private Date endStatisticTime;		// 结束 统计时间
	
	private String bankType;//银行卡类型
	private String feeAmount;//手续费
	
	
	//按天展示
//	private String date;
//	private String dayTotalCount;
//	private String daySuccessCount;
//	private String dayFailCount;
//	private String dayTotalAmount;
//	private String daySuccessAmount;
//	private String dayFailAmount;
	
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	//按周展示
	private String week;
//	private String weekTotalCount;
//	private String weekSuccessCount;
//	private String weekFailCount;
	private String weekTotalAmount;
	private String weekSuccessAmount;
	private String weekFailAmount;
	
	
	
	

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWeekTotalAmount() {
		return weekTotalAmount;
	}

	public void setWeekTotalAmount(String weekTotalAmount) {
		this.weekTotalAmount = weekTotalAmount;
	}

	public String getWeekSuccessAmount() {
		return weekSuccessAmount;
	}

	public void setWeekSuccessAmount(String weekSuccessAmount) {
		this.weekSuccessAmount = weekSuccessAmount;
	}

	public String getWeekFailAmount() {
		return weekFailAmount;
	}

	public void setWeekFailAmount(String weekFailAmount) {
		this.weekFailAmount = weekFailAmount;
	}

	public StatisticsChannelRecord() {
		super();
	}

	public StatisticsChannelRecord(String id){
		super(id);
	}

	@Length(min=0, max=200, message="channel_code通道代码长度必须介于 0 和 200 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=64, message="通道合作方长度必须介于 0 和 64 之间")
	public String getChannelPartners() {
		return channelPartners;
	}

	public void setChannelPartners(String channelPartners) {
		this.channelPartners = channelPartners;
	}
	
	@Length(min=0, max=64, message="银行名称长度必须介于 0 和 64 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=64, message="通道合作方名称长度必须介于 0 和 64 之间")
	public String getChannelPartnersName() {
		return channelPartnersName;
	}

	public void setChannelPartnersName(String channelPartnersName) {
		this.channelPartnersName = channelPartnersName;
	}
	
	@Length(min=0, max=6, message="支付通道类型长度必须介于 0 和 6 之间")
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	@Length(min=0, max=10, message="银行名称长度必须介于 0 和 10 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=0, max=11, message="总笔数长度必须介于 0 和 11 之间")
	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Length(min=0, max=11, message="成功总笔数长度必须介于 0 和 11 之间")
	public String getSucessCount() {
		return sucessCount;
	}

	public void setSucessCount(String sucessCount) {
		this.sucessCount = sucessCount;
	}
	
	@Length(min=0, max=64, message="通道类型名称长度必须介于 0 和 64 之间")
	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	
	public String getSucessAmount() {
		return sucessAmount;
	}

	public void setSucessAmount(String sucessAmount) {
		this.sucessAmount = sucessAmount;
	}
	
	@Length(min=0, max=11, message="失败总笔数长度必须介于 0 和 11 之间")
	public String getFailCount() {
		return failCount;
	}

	public void setFailCount(String failCount) {
		this.failCount = failCount;
	}
	
	public String getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(String failAmount) {
		this.failAmount = failAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStatisticTime() {
		return statisticTime;
	}

	public void setStatisticTime(Date statisticTime) {
		this.statisticTime = statisticTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=6, message="货币类型长度必须介于 0 和 6 之间")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=2, message="按小时统计长度必须介于 0 和 2 之间")
	public String getStatisticHour() {
		return statisticHour;
	}

	public void setStatisticHour(String statisticHour) {
		this.statisticHour = statisticHour;
	}
	
	public Date getBeginStatisticTime() {
		return beginStatisticTime;
	}

	public void setBeginStatisticTime(Date beginStatisticTime) {
		this.beginStatisticTime = beginStatisticTime;
	}
	
	public Date getEndStatisticTime() {
		return endStatisticTime;
	}

	public void setEndStatisticTime(Date endStatisticTime) {
		this.endStatisticTime = endStatisticTime;
	}
		
}