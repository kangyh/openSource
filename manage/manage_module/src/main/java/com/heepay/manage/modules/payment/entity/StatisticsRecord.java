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
 * 描    述：财务统计报表Entity
 *
 * 创 建 者： @author tyq
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
public class StatisticsRecord extends DataEntity<StatisticsRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long merchantId;		// 商户编码
	private String merchantLoginName;
	private String merchantCompany;		// 商户公司
	private String allowSystem;		// 商户来源
	private String transType;		// 交易类型
	private String productCode;		// 产品类型
	private String payType;		// 支付类型
	private String channelCode;		// 渠道编码
	private String channelPartners;		// 通道合作方
	private String notDirecoChannelPartners;//非直连通道合作方
	private String channelPartnersName;		// 通道合作方名称
	private String channelType;		// 支付通道类型
	private String channelTypeName;		// 支付通道类型名称
	private String bankCode;		// 银行编码
	private String bankName;		// 银行名称
	private String bankType;		// 银行卡类型
	private String totalAmount;		// 成功总金额
	private String sucessCount;		// 成功笔数
	private String sucessAmount;		// 成功金额
	private String feeCount;		// 手续费笔数
	private String feeAmount;		// 手续费金额
	private String settleAmount;  //
	private Date statisticsTime;		// 统计时间
	private String statisticHour;		// 统计小时
	private Date createTime;		// 创建时间
	private String currency;		// 币种
	private String remark;		// 备注
	private Date beginStatisticsTime;		// 开始 统计时间
	private Date endStatisticsTime;		// 结束 统计时间
	private String operator;
	private String sortType;
	private String sortDirection;
	private String statisticsDate;
	private String orderBy;
	private String source;
	
	private String day;
	private String month;
	
	private String searchField;
	private String groupBy;
	
	
	
	private String time ;
	private String countType;		//临时添加字段：统计类型
	
	
	public String getOperator() {
		return operator;
	}



	public void setOperator(String operator) {
		this.operator = operator;
	}



	public String getNotDirecoChannelPartners() {
		return notDirecoChannelPartners;
	}



	public void setNotDirecoChannelPartners(String notDirecoChannelPartners) {
		this.notDirecoChannelPartners = notDirecoChannelPartners;
	}



	public String getSearchField() {
		return searchField;
	}



	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}



	public String getGroupBy() {
		return groupBy;
	}



	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getDay() {
		return day;
	}



	public void setDay(String day) {
		this.day = day;
	}



	public String getMonth() {
		return month;
	}



	public void setMonth(String month) {
		this.month = month;
	}



	public StatisticsRecord() {
		super();
	}

	
	
	public String getOrderBy() {
		return orderBy;
	}



	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}



	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public String getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}



	public String getSortType() {
		return sortType;
	}


	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortDirection() {
		return sortDirection;
	}


	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}


	public String getStatisticsDate() {
		return statisticsDate;
	}


	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}


	public StatisticsRecord(String id){
		super(id);
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=200, message="商户公司长度必须介于 0 和 200 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	@Length(min=0, max=6, message="商户来源长度必须介于 0 和 6 之间")
	public String getAllowSystem() {
		return allowSystem;
	}

	public void setAllowSystem(String allowSystem) {
		this.allowSystem = allowSystem;
	}
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Length(min=0, max=6, message="产品类型长度必须介于 0 和 6 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=6, message="支付类型长度必须介于 0 和 6 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=50, message="渠道编码长度必须介于 0 和 50 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=50, message="通道合作方长度必须介于 0 和 50 之间")
	public String getChannelPartners() {
		return channelPartners;
	}

	public void setChannelPartners(String channelPartners) {
		this.channelPartners = channelPartners;
	}
	
	@Length(min=0, max=100, message="通道合作方名称长度必须介于 0 和 100 之间")
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
	
	@Length(min=0, max=100, message="支付通道类型名称长度必须介于 0 和 100 之间")
	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	
	@Length(min=0, max=10, message="银行编码长度必须介于 0 和 10 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=0, max=50, message="银行名称长度必须介于 0 和 50 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=6, message="银行卡类型长度必须介于 0 和 6 之间")
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Length(min=0, max=10, message="成功笔数长度必须介于 0 和 10 之间")
	public String getSucessCount() {
		return sucessCount;
	}

	public void setSucessCount(String sucessCount) {
		this.sucessCount = sucessCount;
	}
	
	public String getSucessAmount() {
		return sucessAmount;
	}

	public void setSucessAmount(String sucessAmount) {
		this.sucessAmount = sucessAmount;
	}
	
	@Length(min=0, max=10, message="手续费笔数长度必须介于 0 和 10 之间")
	public String getFeeCount() {
		return feeCount;
	}

	public void setFeeCount(String feeCount) {
		this.feeCount = feeCount;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}
	
	@Length(min=0, max=2, message="统计小时长度必须介于 0 和 2 之间")
	public String getStatisticHour() {
		return statisticHour;
	}

	public void setStatisticHour(String statisticHour) {
		this.statisticHour = statisticHour;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=8, message="币种长度必须介于 0 和 8 之间")
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
	
	public Date getBeginStatisticsTime() {
		return beginStatisticsTime;
	}

	public void setBeginStatisticsTime(Date beginStatisticsTime) {
		this.beginStatisticsTime = beginStatisticsTime;
	}
	
	public Date getEndStatisticsTime() {
		return endStatisticsTime;
	}

	public void setEndStatisticsTime(Date endStatisticsTime) {
		this.endStatisticsTime = endStatisticsTime;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}
}