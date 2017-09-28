/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 交易管理Entity
 * @author ld
 * @version V1.0
 */
public class GatewayRecord extends DataEntity<GatewayRecord> {
	
	private static final long serialVersionUID = 1L;
	private String gatewayId;		// 交易号
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private String merchantUserId;		// 商户用户ID
	private String merchantUserName;		// 商户用户账号
	private String merchantOrderNo;		// 商户订单号
	private String version;		// 版本号
	private String requestDate;		// 商家请求时间       yyyyMMddHHmmss
	private String requestIp;		// 商户请求IP
	private String requestUserIp;		// 商户用户请求IP
	private String notifyUrl;		// 商户通知地址
	private String callbackUrl;		// 商户回调地址
	private String requestType;		// 来源方式
	private Date createTime;		// 交易时间
	private String requestAmount;		// 交易金额
	private Date successTime;		// 成功时间
	private String successAmount;		// 成功交易金额
	private String type;		// 交易类型
	private String status;		// 交易状态
	private String note;		// 交易说明
	private String feeType;		// 交易手续费方式
	private Date updateTime;//更新时间
	private Date processDesc;//处理备注
	

  private String feeRatio;		// 交易手续费率
	private String feeAmount;		// 手续费金额
	private String riskcontrolType;		// 风控结果
	private String riskcontrolReason;		// 风控原因
	private String authorizationCode;		// 授权码
	private Date beginCreateTime;		// 开始 交易时间
	private Date endCreateTime;		// 结束 交易时间
	private String beginRequestAmount;		// 开始 交易金额
	private String endRequestAmount;		// 结束 交易金额
	private Date beginSuccessTime;		// 开始 成功时间
	private Date endSuccessTime;		// 结束 成功时间
	private String sortOrder;
	private String statisticsDate;

	private List<Long> merchantIds;
	
	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getFeeType() {
    return feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }
	public GatewayRecord() {
		super();
	}

	public GatewayRecord(String id){
		super(id);
	}

	@Length(min=1, max=26, message="交易号长度必须介于 1 和 26 之间")
	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
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
	
	@Length(min=0, max=6, message="版本号长度必须介于 0 和 6 之间")
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
	
	@Length(min=0, max=6, message="来源方式长度必须介于 0 和 6 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
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
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=6, message="交易状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=256, message="交易说明长度必须介于 0 和 256 之间")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	
	@Length(min=0, max=6, message="风控结果长度必须介于 0 和 6 之间")
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
		
	public String getBeginRequestAmount() {
		return beginRequestAmount;
	}

	public void setBeginRequestAmount(String beginRequestAmount) {
		this.beginRequestAmount = beginRequestAmount;
	}
	
	public String getEndRequestAmount() {
		return endRequestAmount;
	}

	public void setEndRequestAmount(String endRequestAmount) {
		this.endRequestAmount = endRequestAmount;
	}
		
	public Date getBeginSuccessTime() {
		return beginSuccessTime;
	}

	public void setBeginSuccessTime(Date beginSuccessTime) {
		this.beginSuccessTime = beginSuccessTime;
	}
	
	public Date getEndSuccessTime() {
		return endSuccessTime;
	}

	public void setEndSuccessTime(Date endSuccessTime) {
		this.endSuccessTime = endSuccessTime;
	}

	public Date getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(Date processDesc) {
		this.processDesc = processDesc;
	}

	public String getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}


	public List<Long> getMerchantIds() {
		return merchantIds;
	}

	public void setMerchantIds(List<Long> merchantIds) {
		this.merchantIds = merchantIds;
	}
}