/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：交易数据统计Entity
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
public class StatisticsTransRecord extends DataEntity<StatisticsTransRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long merchantId;		// 商户编码
	private String merchantCompany;		// 商户公司名称
	private String transType;		// 交易类型
	private String productCode;		// 产品名称
	private String payType;		// 支付类型
	private String totalCount;		// 总笔数
	private String totalAmount;		// 总金额（元）
	private String sucessCount;		// 成功总笔数
	private String sucessAmount;		// 成功总金额（元）
	private String failCount;		// 失败总笔数
	private String failAmount;		// 失败总金额（元）
	private Date statisticsTime;		// 支付时间（统计时间）
	private String statisticHour;		// 按小时统计
	private Date createTime;		// 统计发生时间
	private String currency;		// 货币类型
	private String remark;		// 备注
	private Date beginStatisticsTime;		// 开始 支付时间（统计时间）
	private Date endStatisticsTime;		// 结束 支付时间（统计时间）
	private String isNow;
	private String feeAmount; //手续费
	private String allowSystem;//商户来源
	
	
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getAllowSystem() {
		return allowSystem;
	}

	public void setAllowSystem(String allowSystem) {
		this.allowSystem = allowSystem;
	}

	public String getIsNow() {
		return isNow;
	}

	public void setIsNow(String isNow) {
		this.isNow = isNow;
	}

	public StatisticsTransRecord() {
		super();
	}

	public StatisticsTransRecord(String id){
		super(id);
	}

	@NotNull(message="商户编码不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=100, message="商户公司名称长度必须介于 0 和 100 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Length(min=0, max=6, message="产品名称长度必须介于 0 和 6 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}
	
	@Length(min=0, max=2, message="按小时统计长度必须介于 0 和 2 之间")
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
		
}