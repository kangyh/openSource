/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * merchantEntity
 * @author ly
 * @version V1.0
 */
public class MerchantBankCard extends DataEntity<MerchantBankCard> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商户ID
	private String bankName;		// 银行名称
	private String bankNo;		// 银行id
	private String openBankName;		// 开户银行名称
	private String openName;		// 开户名称
	private String status;		// 状态
	private String associateLineNumber; //联行号
	private String bankId; //银行卡号
	private Date createTime;		// 创建时间
	private String recAccName;  //卡持有人
	private String accountType;
	private String cardTypeCode;
	private String bankCardAuthType;
	
	private String provinceCode;
	private String provinceName;
	private String cityCode;
	private String cityName;
	private String countyCode;
	private String countyName;
	private String openBankCode;
	
	public MerchantBankCard() {
		super();
	}

	public MerchantBankCard(String id){
		super(id);
	}

	@Length(min=0, max=8, message="商户ID长度必须介于 0 和 8 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=50, message="银行名称长度必须介于 0 和 50 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=50, message="银行账户号长度必须介于 0 和 50 之间")
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
	
	@Length(min=0, max=50, message="开户名称长度必须介于 0 和 50 之间")
	public String getOpenName() {
		return openName;
	}

	public void setOpenName(String openName) {
		this.openName = openName;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

  public String getAssociateLineNumber() {
    return associateLineNumber;
  }

  public void setAssociateLineNumber(String associateLineNumber) {
    this.associateLineNumber = associateLineNumber;
  }

  public String getBankId() {
    return bankId;
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }

	public String getRecAccName() {
		return recAccName;
	}
	
	public void setRecAccName(String recAccName) {
		this.recAccName = recAccName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public String getBankCardAuthType() {
		return bankCardAuthType;
	}

	public void setBankCardAuthType(String bankCardAuthType) {
		this.bankCardAuthType = bankCardAuthType;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getOpenBankCode() {
		return openBankCode;
	}

	public void setOpenBankCode(String openBankCode) {
		this.openBankCode = openBankCode;
	}

}