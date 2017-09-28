/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 充值管理Entity
 * @author ld
 * @version V1.0
 */
public class ChargeRecord extends DataEntity<ChargeRecord> {
	
	private static final long serialVersionUID = 1L;
	private String chargeId;		// 充值流水ID
	private Long accountId;		// 账户ID
	private String accountName;		// 账户名称
	private Long merchantId;		// 商户id
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String chargeAmount;		// 金额
	private String feeAmount; //手续费
	private String feeType; //手续费扣除方式
	private String status;		// 状态
//	private Long bankcardId;		// 银行卡ID
	private String requestIp;		// 商户请求IP
	private String payRequestIp;		// 用户请求IP
	private Date successTime;		// 成功时间
	private String remark;		// 说明
	private String paymentId;		// 支付ID
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String realAmount;
	private String sortOrder;
	private String gatewayId;
	private String payType;
	private String payBankName;
	private String statisticsDate;
	
	private String channelCode;    // 渠道代码
    private String channelName;   // 渠道名称
    private String bankSerialnumber;    // 银行交易流水号

	private List<Long> merchantIds;
	
	public ChargeRecord() {
		super();
	}

	public ChargeRecord(String id){
		super(id);
	}

	
	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getRealAmount() {
		BigDecimal chargeAmount = BigDecimal.ZERO;
		BigDecimal feeAmount = BigDecimal.ZERO;
		if(!StringUtil.isBlank(getChargeAmount())){
			chargeAmount = new BigDecimal(getChargeAmount());
		}
		if(!StringUtil.isBlank(getFeeAmount())){
			feeAmount = new BigDecimal(getFeeAmount());
		}
		
		return String.valueOf(chargeAmount.add(feeAmount));
	}

	
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public void setRealAmount(String realAmount) {
		this.realAmount = realAmount;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	@NotNull(message="充值流水ID不能为空")
	public String getChargeId() {
		return chargeId;
	}
	
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
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
	
	public String getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
//	public Long getBankcardId() {
//		return bankcardId;
//	}
//
//	public void setBankcardId(Long bankcardId) {
//		this.bankcardId = bankcardId;
//	}
	
	@Length(min=0, max=64, message="商户请求IP长度必须介于 0 和 64 之间")
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}
	
	@Length(min=0, max=64, message="用户请求IP长度必须介于 0 和 64 之间")
	public String getPayRequestIp() {
		return payRequestIp;
	}

	public void setPayRequestIp(String payRequestIp) {
		this.payRequestIp = payRequestIp;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
	@Length(min=0, max=1024, message="说明长度必须介于 0 和 1024 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=26, message="支付ID长度必须介于 0 和 26 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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

	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayBankName() {
		return payBankName;
	}

	public void setPayBankName(String payBankName) {
		this.payBankName = payBankName;
	}

	public String getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public String getChannelCode() {
    return channelCode;
  }

  public void setChannelCode(String channelCode) {
    this.channelCode = channelCode;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public String getBankSerialnumber() {
    return bankSerialnumber;
  }

  public void setBankSerialnumber(String bankSerialnumber) {
    this.bankSerialnumber = bankSerialnumber;
  }

	public List<Long> getMerchantIds() {
		return merchantIds;
	}

	public void setMerchantIds(List<Long> merchantIds) {
		this.merchantIds = merchantIds;
	}
}