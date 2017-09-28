/**
 *
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 转账明细Entity
 * @author zjx
 * @version V1.0
 */
public class BatchPayRecordDetail extends DataEntity<BatchPayRecordDetail> {

	private static final long serialVersionUID = 1L;
	private String batchPayId;		// 付款ID
	private String merchantPayNo;		// 商户付款号
	private String batchId;		// 批次号
	private String merchantBatchNo;		 // 商户批次号
	private Long accountId;		// 账户ID
	private String accountName;		// 账户名称
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private Date createTime;		// 申请时间
	private Date updateTime;		// update_time
	private String payAmount;		// 付款金额
	private String bankcardNo;		// 银行卡号
	private String bankcardOwnerMobile;		// 银行预留手机号
	private String bankcardOwnerName;		// 持卡人姓名
	private String bankcardOwnerIdcard;		// 持卡人身份证号
	private String bankId;		// bank_id
	private String bankName;		// 银行名称
	private String status;		// 状态
	private String failReason;		// 失败原因
	private Date successTime;		// 成功时间
	private String successAmount;		// 成功金额
	private String paymentId;		// 支付订单号
	private String bankOrderId;		// 银行订单号
	private String bankcardOwnerType;		// 银行卡持卡人类型  0=个人1=企业-1=未知
	private String province;		// 省
	private String city;		// 城市
	private String openbankName;		// 开户行名称
	private String payReason;		// 付款理由
	private String auditReason;		// 审核理由
	private String feeAmount;		// 手续费
	private String auditFlag;
	private String associateLineNumber; //联行号
	private String bankcardType;
	private String exceptionStatus;
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String channelCode;		// 渠道代码
	private String channelName;		// 渠道名称
	private Date intoaccountTime; //银行服务类型
	private String operator;//操作人
	private String processDesc;//处理备注
	private String auditPerson;//审核人
	private String statisticsDate;
	private String groupType;


	public BatchPayRecordDetail() {
		super();
	}

	public BatchPayRecordDetail(String id){
		super(id);
	}

	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@NotNull(message="付款ID不能为空")
	public String getBatchPayId() {
		return batchPayId;
	}

	public void setBatchPayId(String batchPayId) {
		this.batchPayId = batchPayId;
	}

	@Length(min=1, max=32, message="批次号长度必须介于 1 和 32 之间")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@NotNull(message="账户ID不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@Length(min=0, max=256, message="账户名称长度必须介于 0 和 256 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@NotNull(message="商户ID不能为空")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	@Length(min=0, max=256, message="银行卡号长度必须介于 0 和 256 之间")
	public String getBankcardNo() {
		if(bankcardNo==null){
			return null;
		}
		return Aes.decryptStr(bankcardNo, Constants.QuickPay.SYSTEM_KEY);
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}

	@Length(min=0, max=256, message="银行预留手机号长度必须介于 0 和 256 之间")
	public String getBankcardOwnerMobile() {
		return bankcardOwnerMobile;
	}

	public void setBankcardOwnerMobile(String bankcardOwnerMobile) {
		this.bankcardOwnerMobile = bankcardOwnerMobile;
	}

	@Length(min=0, max=64, message="持卡人姓名长度必须介于 0 和 64 之间")
	public String getBankcardOwnerName() {
		return bankcardOwnerName;
	}

	public void setBankcardOwnerName(String bankcardOwnerName) {
		this.bankcardOwnerName = bankcardOwnerName;
	}

	@Length(min=0, max=256, message="持卡人身份证号长度必须介于 0 和 256 之间")
	public String getBankcardOwnerIdcard() {
		return bankcardOwnerIdcard;
	}

	public void setBankcardOwnerIdcard(String bankcardOwnerIdcard) {
		this.bankcardOwnerIdcard = bankcardOwnerIdcard;
	}

	@Length(min=0, max=10, message="bank_id长度必须介于 0 和 10 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	@Length(min=0, max=128, message="银行名称长度必须介于 0 和 128 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Length(min=0, max=6, message="状态    0=申请中 1=付款成功 3=付款失败长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Length(min=0, max=26, message="支付订单号长度必须介于 0 和 26 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@Length(min=0, max=32, message="银行订单号长度必须介于 0 和 32 之间")
	public String getBankOrderId() {
		return bankOrderId;
	}

	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}

	@Length(min=0, max=4, message="银行卡持卡人类型  0=个人1=企业-1=未知长度必须介于 0 和 4 之间")
	public String getBankcardOwnerType() {
		return bankcardOwnerType;
	}

	public void setBankcardOwnerType(String bankcardOwnerType) {
		this.bankcardOwnerType = bankcardOwnerType;
	}

	@Length(min=0, max=256, message="省长度必须介于 0 和 256 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Length(min=0, max=256, message="城市长度必须介于 0 和 256 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Length(min=0, max=256, message="开户行名称长度必须介于 0 和 256 之间")
	public String getOpenbankName() {
		return openbankName;
	}

	public void setOpenbankName(String openbankName) {
		this.openbankName = openbankName;
	}

	@Length(min=0, max=256, message="付款理由长度必须介于 0 和 256 之间")
	public String getPayReason() {
		return payReason;
	}

	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}

	@Length(min=0, max=256, message="审核理由长度必须介于 0 和 256 之间")
	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
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

	public String getChannelName() {
		return channelName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Date getIntoaccountTime() {
		return intoaccountTime;
	}

	public void setIntoaccountTime(Date intoaccountTime) {
		this.intoaccountTime = intoaccountTime;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public String getAssociateLineNumber() {
		return associateLineNumber;
	}

	public void setAssociateLineNumber(String associateLineNumber) {
		this.associateLineNumber = associateLineNumber;
	}

	public String getBankcardType() {
		return bankcardType;
	}

	public void setBankcardType(String bankcardType) {
		this.bankcardType = bankcardType;
	}

	public String getMerchantPayNo() {
		return merchantPayNo;
	}

	public void setMerchantPayNo(String merchantPayNo) {
		this.merchantPayNo = merchantPayNo;
	}

	public String getMerchantBatchNo() {
		return merchantBatchNo;
	}

	public void setMerchantBatchNo(String merchantBatchNo) {
		this.merchantBatchNo = merchantBatchNo;
	}

	public String getExceptionStatus() {
		return exceptionStatus;
	}

	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
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