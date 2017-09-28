/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 提现管理Entity
 * @author hs
 * @version V1.0
 */
public class WithdrawRecord extends DataEntity<WithdrawRecord> {
	
	private static final long serialVersionUID = 1L;
	private String withdrawId;		// 提现ID
	private Long merchantId;		// 商户id
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private Long accountId;		// 账户ID
	private String accountName;		// 账户名称
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String withdrawAmount;		// 金额
	private String status;		// 状态 0=申请中 1=提现中 2=成功 3=失败 4=审核驳回
	private Long authId;		// 签约银行卡ID
	private String requestIp;		// 请求IP
	private Date successTime;		// 成功时间
	private String processId;		// 处理人ID
	private String processName;		// 处理人姓名
	private Date processTime;		// 处理时间
	private String processDesc;		// process_desc
	private String paymentId;		// 支付ID
//	private String withdrawFeeAmount;		// 手续费
	private String bankcardOwnerName;		// 提现银行卡持卡人姓名
	private String bankcardOwnerMobile;		// bankcard_owner_mobile
	private String bankcardOwnerIdcard;		// 持卡人身份证号
	private String bankcardOwnerType;		// 持卡人类型 0=个人 1=企业 -1 未知
	private String bankcardNo;		// 提现银行卡号
	private String type;		// 0=储蓄卡 1=信用卡
	private String feeType;		// 手续费方式
	private String remark;

	private String feeRatio;		// 手续费费率
	private String feeAmount;		// 手续费金额

	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间

	private String sortOrder; //升降排序

	private Date intoaccountTime; //提现模式
	private String openBankName; //开户支行
	
	private Date auditTime; //审核时间
	private String auditPerson; //审核人
	private String auditDesc;//审核备注
	
	private String gatewayMsg;

	private String statisticsDate;

	public String getGatewayMsg() {
		return gatewayMsg;
	}

	public void setGatewayMsg(String gatewayMsg) {
		this.gatewayMsg = gatewayMsg;
	}

	@Length(min=0, max=10, message="手续费方式长度必须介于 0 和 10 之间")
	public String getFeeType() {
    return feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }
	
	
	public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

	public WithdrawRecord() {
		super();
	}

	public WithdrawRecord(String id){
		super(id);
	}

	@NotNull(message="提现ID不能为空")
	public String getWithdrawId() {
		return withdrawId;
	}
	
	public void setWithdrawId(String withdrawId) {
		this.withdrawId = withdrawId;
	}
	
	@NotNull(message="商户id不能为空")
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
	
	@Length(min=0, max=64, message="账户名称长度必须介于 0 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	
	public String getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(String withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	
	@Length(min=0, max=6, message="状态 0=申请中 1=提现中 2=成功 3=失败 4=审核驳回长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}
	
	@Length(min=0, max=32, message="请求IP长度必须介于 0 和 32 之间")
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
	@Length(min=0, max=11, message="处理人ID长度必须介于 0 和 11 之间")
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	@Length(min=0, max=128, message="处理人姓名长度必须介于 0 和 128 之间")
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
	
	@Length(min=0, max=256, message="process_desc长度必须介于 0 和 256 之间")
	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	
	@Length(min=0, max=26, message="支付ID长度必须介于 0 和 26 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
//	public String getWithdrawFeeAmount() {
//		return withdrawFeeAmount;
//	}
//
//	public void setWithdrawFeeAmount(String withdrawFeeAmount) {
//		this.withdrawFeeAmount = withdrawFeeAmount;
//	}
	
	@Length(min=0, max=64, message="提现银行卡持卡人姓名长度必须介于 0 和 64 之间")
	public String getBankcardOwnerName() {
		return bankcardOwnerName;
	}

	public void setBankcardOwnerName(String bankcardOwnerName) {
		this.bankcardOwnerName = bankcardOwnerName;
	}
	
	@Length(min=0, max=64, message="bankcard_owner_mobile长度必须介于 0 和 64 之间")
	public String getBankcardOwnerMobile() {
		return bankcardOwnerMobile;
	}

	public void setBankcardOwnerMobile(String bankcardOwnerMobile) {
		this.bankcardOwnerMobile = bankcardOwnerMobile;
	}
	
	@Length(min=0, max=32, message="持卡人身份证号长度必须介于 0 和 32 之间")
	public String getBankcardOwnerIdcard() {
		return bankcardOwnerIdcard;
	}

	public void setBankcardOwnerIdcard(String bankcardOwnerIdcard) {
		this.bankcardOwnerIdcard = bankcardOwnerIdcard;
	}
	
	@Length(min=0, max=4, message="持卡人类型 0=个人 1=企业 -1 未知长度必须介于 0 和 4 之间")
	public String getBankcardOwnerType() {
		return bankcardOwnerType;
	}

	public void setBankcardOwnerType(String bankcardOwnerType) {
		this.bankcardOwnerType = bankcardOwnerType;
	}
	
	@Length(min=0, max=64, message="提现银行卡号长度必须介于 0 和 64 之间")
	public String getBankcardNo() {	
		
		if(bankcardNo==null){
			return null;
		}
		String card = Aes.decryptStr(bankcardNo,Constants.QuickPay.SYSTEM_KEY);
		return StringUtil.getEncryptedCardNo(card);
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	
	@Length(min=0, max=6, message="0=储蓄卡 1=信用卡长度必须介于 0 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}


	public Date getIntoaccountTime() {
		return intoaccountTime;
	}

	public void setIntoaccountTime(Date intoaccountTime) {
		this.intoaccountTime = intoaccountTime;
	}

	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}
}