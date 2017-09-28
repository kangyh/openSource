/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 商户打款认证Entity
 * @author ly
 * @version V1.0
 */
public class MerchantBankCardAuthentication extends DataEntity<MerchantBankCardAuthentication> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商户ID
	private String merchantCompanyName;		// 商户公司名称
	private String legalRepresentative;		// 法人代表
	private String bankNo;		// 银行卡号
	private String openBankName;		// 开户银行名称
	private BigDecimal payAmount;		// 打款金额
	private String payStatus;		// 打款状态
	private BigDecimal authenticationAmount;		// 认证金额
	private String authenticationStatus;		// 认证状态
	private String level;		// 商户等级
	private String reason;		// 修改理由
	private Date beginCreateTime;		// 开始 打款时间
	private Date endCreateTime;		// 结束 打款时间
	private String cause; //失败原因
	
	public MerchantBankCardAuthentication() {
		super();
	}

	public MerchantBankCardAuthentication(String id){
		super(id);
	}

	@Length(min=0, max=8, message="商户ID长度必须介于 0 和 8 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=100, message="商户公司名称长度必须介于 0 和 100 之间")
	public String getMerchantCompanyName() {
		return merchantCompanyName;
	}

	public void setMerchantCompanyName(String merchantCompanyName) {
		this.merchantCompanyName = merchantCompanyName;
	}
	
	@Length(min=0, max=255, message="法人代表长度必须介于 0 和 255 之间")
	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	
	@Length(min=0, max=50, message="银行卡号长度必须介于 0 和 50 之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Length(min=0, max=50, message="开户银行名称长度必须介于 0 和 50 之间")
	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	
	@Length(min=0, max=6, message="打款状态长度必须介于 0 和 6 之间")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public BigDecimal getAuthenticationAmount() {
		return authenticationAmount;
	}

	public void setAuthenticationAmount(BigDecimal authenticationAmount) {
		this.authenticationAmount = authenticationAmount;
	}
	
	@Length(min=0, max=6, message="认证状态长度必须介于 0 和 6 之间")
	public String getAuthenticationStatus() {
		return authenticationStatus;
	}

	public void setAuthenticationStatus(String authenticationStatus) {
		this.authenticationStatus = authenticationStatus;
	}
	
	@Length(min=0, max=4, message="商户等级长度必须介于 0 和 4 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=200, message="修改理由长度必须介于 0 和 200 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }
	
}