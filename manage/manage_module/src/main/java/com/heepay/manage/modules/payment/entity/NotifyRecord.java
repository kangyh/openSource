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
 * 描    述：异步通知Entity
 *
 * 创 建 者： @author zc
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
public class NotifyRecord extends DataEntity<NotifyRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long notifyId;		// 通知ID
	private String transNo;		// 交易号  汇付宝交易号
	private Long merchantId;		// 商户id
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private String merchantOrderNo;		// 商户订单号
	private String notifyUrl;		// 通知地址
	private String status;		// 通知状态
	private String notifyRequestParams;		// 请求参数
	private String notifyResponse;		// 返回结果
	private int notifyNumber;		// 通知次数        5分钟一次，通知5次
	private String notifyType;		// 通知类型       1=手动通知2=自动通知
	private Date notifyTime;		// 通知时间
	private String successAmount;		// 实际支付成功金额
	private String payResult;		// 支付结果
	private String paymentId;		// 支付ID
	private Date updateTime;		// 最后修改时间
	private String payAmount;		// 应该支付总额
	private Date createTime;		// 创建时间
	private String productCode;		// product_code
	private String bankSerialNo;		// 银行流水号   银行或者渠道流水号
	
	public NotifyRecord() {
		super();
	}

	public NotifyRecord(String id){
		super(id);
	}

	@NotNull(message="通知ID不能为空")
	public Long getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Long notifyId) {
		this.notifyId = notifyId;
	}
	
	@Length(min=0, max=32, message="交易号  汇付宝交易号长度必须介于 0 和 32 之间")
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商户登录账号长度必须介于 0 和 64 之间")
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	
	@Length(min=0, max=64, message="商户公司长度必须介于 0 和 64 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=0, max=512, message="通知地址长度必须介于 0 和 512 之间")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	@Length(min=0, max=6, message="通知状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1024, message="请求参数长度必须介于 0 和 1024 之间")
	public String getNotifyRequestParams() {
		return notifyRequestParams;
	}

	public void setNotifyRequestParams(String notifyRequestParams) {
		this.notifyRequestParams = notifyRequestParams;
	}
	
	@Length(min=0, max=5000, message="返回结果长度必须介于 0 和 5000 之间")
	public String getNotifyResponse() {
		return notifyResponse;
	}

	public void setNotifyResponse(String notifyResponse) {
		this.notifyResponse = notifyResponse;
	}
	
	@Length(min=0, max=2, message="通知次数        5分钟一次，通知5次长度必须介于 0 和 2 之间")
	public int getNotifyNumber() {
		return notifyNumber;
	}

	public void setNotifyNumber(int notifyNumber) {
		this.notifyNumber = notifyNumber;
	}
	
	@Length(min=0, max=2, message="通知类型       1=手动通知2=自动通知长度必须介于 0 和 2 之间")
	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	@Length(min=0, max=11, message="支付结果长度必须介于 0 和 11 之间")
	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}
	
	@Length(min=0, max=26, message="支付ID长度必须介于 0 和 26 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="最后修改时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=4, message="product_code长度必须介于 0 和 4 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=64, message="银行流水号   银行或者渠道流水号长度必须介于 0 和 64 之间")
	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}
	
}