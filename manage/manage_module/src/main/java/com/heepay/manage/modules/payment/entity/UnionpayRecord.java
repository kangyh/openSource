/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import com.heepay.manage.modules.sys.entity.User;

/**
 *
 * 描    述：银联扫码支付Entity
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
public class UnionpayRecord extends DataEntity<UnionpayRecord> {
	
	private static final long serialVersionUID = 1L;
	private String unionpayId;		// 交易号
	private String prepayId;		// 预下单号
	private String paymentId;		// 支付单号
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private String merchantUserId;		// 商户用户ID
	private String merchantUserName;		// 商户用户账号
	private String merchantOrderNo;		// 商户订单号
	private String termId;		// 终端号
	private String qrNo;		// C2B码
	private String qrCode;		// 二维码
	private String voucherNum;		// 付款凭证号
	private String payerInfo;		// 付款方信息
	private String payerComments;		// 付款方附言
	private String payeeInfo;		// 收款方信息
	private String payeeComments;		// 收款方附言
	private String settleKey;		// 清算主键
	private String settleDate;		// 清算日期
	private String encryptCertid;		// 加密证书ID
	private String comInfo;		// 银行对账流水信息
	private String invoiceInfo;		// 发票信息
	private String version;		// 版本号
	private String requestDate;		// 商家请求时间       yyyyMMddHHmmss
	private String requestIp;		// 商户请求IP
	private String requestUserIp;		// 商户用户请求IP
	private String notifyUrl;		// 商户通知地址
	private String callbackUrl;		// 商户回调地址
	private String requestType;		// 来源方式
	private Date createTime;		// 交易时间
	private String requestAmount;		// 交易金额
	private Date updateTime;		// 更新时间
	private Date successTime;		// 成功时间
	private String successAmount;		// 成功交易金额
	private String productCode;		// 产品代码(product表code字段)
	private String type;		// 银联扫码交易类型
	private String status;		// 银联扫码交易状态
	private String failReason;		// 失败原因
	private String refundTimes;		// 发起退款次数
	private String canRefundAmount;		// 可退余额
	private String note;		// 交易说明
	private String feeType;		// 交易手续费扣除方式
	private String feeRatio;		// 交易手续费率
	private String feeAmount;		// 手续费金额
	private String riskcontrolType;		// 风控结果类型
	private String riskcontrolReason;		// 风控原因
	private String authorizationCode;		// 授权码
	private String operator;		// 操作人
	private String processDesc;		// 处理备注
	private User user;		// 用户id
	private Date beginCreateTime;		// 开始 交易时间
	private Date endCreateTime;		// 结束 交易时间
	
	private String statisticsDate;
	private String groupType;
	
	
	public UnionpayRecord() {
		super();
	}

	public UnionpayRecord(String id){
		super(id);
	}

	@Length(min=0, max=20, message="交易号长度必须介于 0 和 20 之间")
	public String getUnionpayId() {
		return unionpayId;
	}

	public void setUnionpayId(String unionpayId) {
		this.unionpayId = unionpayId;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	@Length(min=0, max=20, message="支付单号长度必须介于 0 和 20 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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
	
	@Length(min=0, max=128, message="商户用户ID长度必须介于 0 和 128 之间")
	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	
	@Length(min=0, max=128, message="商户用户账号长度必须介于 0 和 128 之间")
	public String getMerchantUserName() {
		return merchantUserName;
	}

	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=0, max=32, message="终端号长度必须介于 0 和 32 之间")
	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}
	
	@Length(min=0, max=32, message="C2B码长度必须介于 0 和 32 之间")
	public String getQrNo() {
		return qrNo;
	}

	public void setQrNo(String qrNo) {
		this.qrNo = qrNo;
	}
	
	@Length(min=0, max=512, message="二维码长度必须介于 0 和 512 之间")
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	@Length(min=0, max=64, message="付款凭证号长度必须介于 0 和 64 之间")
	public String getVoucherNum() {
		return voucherNum;
	}

	public void setVoucherNum(String voucherNum) {
		this.voucherNum = voucherNum;
	}
	
	@Length(min=0, max=1024, message="付款方信息长度必须介于 0 和 1024 之间")
	public String getPayerInfo() {
		return payerInfo;
	}

	public void setPayerInfo(String payerInfo) {
		this.payerInfo = payerInfo;
	}
	
	@Length(min=0, max=1024, message="付款方附言长度必须介于 0 和 1024 之间")
	public String getPayerComments() {
		return payerComments;
	}

	public void setPayerComments(String payerComments) {
		this.payerComments = payerComments;
	}
	
	@Length(min=0, max=1024, message="收款方信息长度必须介于 0 和 1024 之间")
	public String getPayeeInfo() {
		return payeeInfo;
	}

	public void setPayeeInfo(String payeeInfo) {
		this.payeeInfo = payeeInfo;
	}
	
	@Length(min=0, max=1024, message="收款方附言长度必须介于 0 和 1024 之间")
	public String getPayeeComments() {
		return payeeComments;
	}

	public void setPayeeComments(String payeeComments) {
		this.payeeComments = payeeComments;
	}
	
	@Length(min=0, max=256, message="清算主键长度必须介于 0 和 256 之间")
	public String getSettleKey() {
		return settleKey;
	}

	public void setSettleKey(String settleKey) {
		this.settleKey = settleKey;
	}
	
	@Length(min=0, max=20, message="清算日期长度必须介于 0 和 20 之间")
	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	
	@Length(min=0, max=32, message="加密证书ID长度必须介于 0 和 32 之间")
	public String getEncryptCertid() {
		return encryptCertid;
	}

	public void setEncryptCertid(String encryptCertid) {
		this.encryptCertid = encryptCertid;
	}
	
	@Length(min=0, max=512, message="银行对账流水信息长度必须介于 0 和 512 之间")
	public String getComInfo() {
		return comInfo;
	}

	public void setComInfo(String comInfo) {
		this.comInfo = comInfo;
	}
	
	@Length(min=0, max=512, message="发票信息长度必须介于 0 和 512 之间")
	public String getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
	
	@Length(min=0, max=3, message="版本号长度必须介于 0 和 3 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=14, message="商家请求时间       yyyyMMddHHmmss长度必须介于 0 和 14 之间")
	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	
	@Length(min=0, max=15, message="商户请求IP长度必须介于 0 和 15 之间")
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	
	@Length(min=0, max=15, message="商户用户请求IP长度必须介于 0 和 15 之间")
	public String getRequestUserIp() {
		return requestUserIp;
	}

	public void setRequestUserIp(String requestUserIp) {
		this.requestUserIp = requestUserIp;
	}
	
	@Length(min=0, max=512, message="商户通知地址长度必须介于 0 和 512 之间")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	@Length(min=0, max=512, message="商户回调地址长度必须介于 0 和 512 之间")
	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	
	@Length(min=0, max=3, message="来源方式长度必须介于 0 和 3 之间")
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	@Length(min=0, max=6, message="产品代码(product表code字段)长度必须介于 0 和 6 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=6, message="银联扫码交易类型长度必须介于 0 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=6, message="银联扫码交易状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="失败原因长度必须介于 0 和 64 之间")
	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
	@Length(min=1, max=1, message="发起退款次数长度必须介于 1 和 1 之间")
	public String getRefundTimes() {
		return refundTimes;
	}

	public void setRefundTimes(String refundTimes) {
		this.refundTimes = refundTimes;
	}
	
	public String getCanRefundAmount() {
		return canRefundAmount;
	}

	public void setCanRefundAmount(String canRefundAmount) {
		this.canRefundAmount = canRefundAmount;
	}
	
	@Length(min=0, max=256, message="交易说明长度必须介于 0 和 256 之间")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Length(min=0, max=6, message="交易手续费扣除方式长度必须介于 0 和 6 之间")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public String getFeeRatio() {
		return feeRatio;
	}

	public void setFeeRatio(String feeRatio) {
		this.feeRatio = feeRatio;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	@Length(min=0, max=6, message="风控结果类型长度必须介于 0 和 6 之间")
	public String getRiskcontrolType() {
		return riskcontrolType;
	}

	public void setRiskcontrolType(String riskcontrolType) {
		this.riskcontrolType = riskcontrolType;
	}
	
	@Length(min=0, max=128, message="风控原因长度必须介于 0 和 128 之间")
	public String getRiskcontrolReason() {
		return riskcontrolReason;
	}

	public void setRiskcontrolReason(String riskcontrolReason) {
		this.riskcontrolReason = riskcontrolReason;
	}
	
	@Length(min=0, max=256, message="授权码长度必须介于 0 和 256 之间")
	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	
	@Length(min=0, max=6, message="操作人长度必须介于 0 和 6 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=256, message="处理备注长度必须介于 0 和 256 之间")
	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

  public String getStatisticsDate() {
    return statisticsDate;
  }

  public void setStatisticsDate(String statisticsDate) {
    this.statisticsDate = statisticsDate;
  }

  public String getGroupType() {
    return groupType;
  }

  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }
		
}