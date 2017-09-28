/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：退款Entity
 *
 * 创 建 者： @author zjx
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
public class RefundRecord extends DataEntity<RefundRecord> {
	
	private static final long serialVersionUID = 1L;
	private String refundId;		// 退款ID
	private String transNo;		// 交易号
	private String merchantOrderNo;		// 商家订单号
	private String merchantUserName;
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private Long accountId;		// 账户ID
	private String accountName;		// 账户名称
	private String refundDetail;		// 退款说明
	private String refundAmount;		// 退款金额
	private Date refundTime;		// 退款时间
	private Date updateTime;		// 更新时间
	private Date transCreateTime;
	private String refundSuccessAmount;		// 成功交易金额
	private Date refundSuccessTime;		// 成功时间
	private String type;		// 退款类型 NORMAL=交易退款 CHARGE=充值退款
	private String status;		// 状态 0=申请中 1=审核通过退款中 2=审核通过退款成功 3=审核通过退款失败 4=审核驳回退款取消
	private String refundFrom;		// 退款来源 0=接口 1=商家申请 2=管理平台申请
	private String notifyUrl;		// 异步通知地址
	private Long applyId;		// 申请人ID
	private String applyName;		// 申请人姓名
	private Long processId;		// 处理人ID
	private String processName;		// 最后处理人
	private Date processTime;		// 处理时间
	private String paymentId;		// 支付ID
	private String originPaymentId;
	private String feeType;		// 手续费方式
	private String sortOrder;

	private String operator;
	private String isFeeBack;
	private String userId;

    private String feeRatio;		// 手续费费率
	private String feeAmount;		// 手续费金额
	private String processDesc;       //处理备注
	
	private Date beginCreateTime;    // 开始 创建时间
	private Date endCreateTime;   // 结束 创建时间

	private String statisticsDate;
	
	private String groupType;
	
	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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


	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}

	public void setTransCreateTime(Date transCreateTime) {
		this.transCreateTime = transCreateTime;
	}

	public void setOriginPaymentId(String originPaymentId) {
		this.originPaymentId = originPaymentId;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setIsFeeBack(String isFeeBack) {
		this.isFeeBack = isFeeBack;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerchantUserName() {
		return merchantUserName;
	}

	public Date getTransCreateTime() {
		return transCreateTime;
	}

	public String getOriginPaymentId() {
		return originPaymentId;
	}

	public String getOperator() {
		return operator;
	}

	public String getIsFeeBack() {
		return isFeeBack;
	}

	public String getUserId() {
		return userId;
	}

	@Length(min=0, max=10, message="手续费方式长度必须介于 0 和 10 之间")
	public String getFeeType() {
    return feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }
  
  
	public RefundRecord() {
		super();
	}

	public RefundRecord(String id){
		super(id);
	}

	@NotNull(message="退款ID不能为空")
	public String getRefundId() {
		return refundId;
	}
	
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	
	
	@Length(min=0, max=26, message="交易号长度必须介于 0 和 26 之间")
	public String getTransNo() {
		return transNo;
	}


	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	@Length(min=0, max=64, message="商家订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
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
	
	@Length(min=0, max=256, message="退款说明长度必须介于 0 和 256 之间")
	public String getRefundDetail() {
		return refundDetail;
	}

	public void setRefundDetail(String refundDetail) {
		this.refundDetail = refundDetail;
	}
	
	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getRefundSuccessAmount() {
		return refundSuccessAmount;
	}

	public void setRefundSuccessAmount(String refundSuccessAmount) {
		this.refundSuccessAmount = refundSuccessAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRefundSuccessTime() {
		return refundSuccessTime;
	}

	public void setRefundSuccessTime(Date refundSuccessTime) {
		this.refundSuccessTime = refundSuccessTime;
	}
	
	@Length(min=0, max=6, message="退款类型 NORMAL=交易退款 CHARGE=充值退款长度必须介于 0 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=6, message="状态 0=申请中 1=审核通过退款中 2=审核通过退款成功 3=审核通过退款失败 4=审核驳回退款取消长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=6, message="退款来源 0=接口 1=商家申请 2=管理平台申请长度必须介于 0 和 6 之间")
	public String getRefundFrom() {
		return refundFrom;
	}

	public void setRefundFrom(String refundFrom) {
		this.refundFrom = refundFrom;
	}
	
	@Length(min=0, max=512, message="异步通知地址长度必须介于 0 和 512 之间")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	@Length(min=0, max=11, message="申请人ID长度必须介于 0 和 11 之间")
	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
	@Length(min=0, max=128, message="申请人姓名长度必须介于 0 和 128 之间")
	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	
	@Length(min=0, max=11, message="处理人ID长度必须介于 0 和 11 之间")
	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	@Length(min=0, max=128, message="最后处理人长度必须介于 0 和 128 之间")
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
	
	@Length(min=0, max=26, message="支付ID长度必须介于 0 和 26 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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