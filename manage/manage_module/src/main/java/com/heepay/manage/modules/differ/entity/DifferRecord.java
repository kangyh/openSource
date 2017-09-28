/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.differ.entity;

import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：差异处理平台Entity
 *
 * 创 建 者： @author ld
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
public class DifferRecord extends DataEntity<DifferRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long differId;		// 差异处理主键
	private String paymentId;		// 支付单号
	private String transNo;		// 交易订单号
	private String merchantId;		// 商户号
	private String merchantLoginName;		// 商户登录名
	private String merchantCompany;		// 商户公司名
	private String payAmount;		// 交易金额
	private String merchantFee;		// 商户手续费
	private String transStatus;		// 订单状态
	private String transType;		// 交易类型
	private String payType;		// 支付类型
	private String productCode;		// 产品编码
	private Date transCreateTime;		// 订单创建时间
	private Date transPayTime;		// 订单提交网关时间
	private Date transSuccessTime;		// 订单成功时间
	private String channelCode;		// 通道编码
	private String bankSerialNo;		// 银行流水号
	private String successAmount;		// 成功金额
	private String channelFee;		// 通道手续费
	private String serviceFee;		// 服务费(银联)
	private Date bankSuccessTime;		// 通道成功时间
	private String differType;		// 差异类型
	private String differStatus;		// 差异处理状态(PENDIN=待处理，FINISH=处理完成)
	private String checkBatch;		// 对账批次
	private String operator;		// 操作人
	private String operateSource;		// 操作来源
	private String operateType;		// 处理方式
	private Date operateTime;		// 处理时间
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private Date beginTransPayTime;		// 开始 订单提交网关时间
	private Date endTransPayTime;		// 结束 订单提交网关时间
	private Date beginOperateTime;		// 开始 处理时间
	private Date endOperateTime;		// 结束 处理时间
	
	public DifferRecord() {
		super();
	}

	public DifferRecord(String id){
		super(id);
	}

	@NotNull(message="差异处理主键不能为空")
	public Long getDifferId() {
		return differId;
	}

	public void setDifferId(Long differId) {
		this.differId = differId;
	}
	
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	@Length(min=0, max=20, message="交易订单号长度必须介于 0 和 20 之间")
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	@Length(min=0, max=10, message="商户号长度必须介于 0 和 10 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=50, message="商户登录名长度必须介于 0 和 50 之间")
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	
	@Length(min=0, max=50, message="商户公司名长度必须介于 0 和 50 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	public String getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(String merchantFee) {
		this.merchantFee = merchantFee;
	}
	
	@Length(min=0, max=6, message="订单状态长度必须介于 0 和 6 之间")
	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Length(min=0, max=6, message="支付类型长度必须介于 0 和 6 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=4, message="产品编码长度必须介于 0 和 4 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTransCreateTime() {
		return transCreateTime;
	}

	public void setTransCreateTime(Date transCreateTime) {
		this.transCreateTime = transCreateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTransPayTime() {
		return transPayTime;
	}

	public void setTransPayTime(Date transPayTime) {
		this.transPayTime = transPayTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTransSuccessTime() {
		return transSuccessTime;
	}

	public void setTransSuccessTime(Date transSuccessTime) {
		this.transSuccessTime = transSuccessTime;
	}
	
	@Length(min=0, max=20, message="通道编码长度必须介于 0 和 20 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=50, message="银行流水号长度必须介于 0 和 50 之间")
	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	public String getChannelFee() {
		return channelFee;
	}

	public void setChannelFee(String channelFee) {
		this.channelFee = channelFee;
	}
	
	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBankSuccessTime() {
		return bankSuccessTime;
	}

	public void setBankSuccessTime(Date bankSuccessTime) {
		this.bankSuccessTime = bankSuccessTime;
	}
	
	@Length(min=0, max=6, message="差异类型长度必须介于 0 和 6 之间")
	public String getDifferType() {
		return differType;
	}

	public void setDifferType(String differType) {
		this.differType = differType;
	}
	
	@Length(min=0, max=6, message="差异处理状态(PENDIN=待处理，FINISH=处理完成)长度必须介于 0 和 6 之间")
	public String getDifferStatus() {
		return differStatus;
	}

	public void setDifferStatus(String differStatus) {
		this.differStatus = differStatus;
	}
	
	@Length(min=0, max=20, message="对账批次长度必须介于 0 和 20 之间")
	public String getCheckBatch() {
		return checkBatch;
	}

	public void setCheckBatch(String checkBatch) {
		this.checkBatch = checkBatch;
	}
	
	@Length(min=0, max=10, message="操作人长度必须介于 0 和 10 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=6, message="操作来源长度必须介于 0 和 6 之间")
	public String getOperateSource() {
		return operateSource;
	}

	public void setOperateSource(String operateSource) {
		this.operateSource = operateSource;
	}
	
	@Length(min=0, max=6, message="处理方式长度必须介于 0 和 6 之间")
	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Date getBeginTransPayTime() {
		return beginTransPayTime;
	}

	public void setBeginTransPayTime(Date beginTransPayTime) {
		this.beginTransPayTime = beginTransPayTime;
	}
	
	public Date getEndTransPayTime() {
		return endTransPayTime;
	}

	public void setEndTransPayTime(Date endTransPayTime) {
		this.endTransPayTime = endTransPayTime;
	}
		
	public Date getBeginOperateTime() {
		return beginOperateTime;
	}

	public void setBeginOperateTime(Date beginOperateTime) {
		this.beginOperateTime = beginOperateTime;
	}
	
	public Date getEndOperateTime() {
		return endOperateTime;
	}

	public void setEndOperateTime(Date endOperateTime) {
		this.endOperateTime = endOperateTime;
	}
		
}