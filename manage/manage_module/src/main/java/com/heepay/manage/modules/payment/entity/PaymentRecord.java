/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：交易管理Entity
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
public class PaymentRecord extends DataEntity<PaymentRecord> {
	
	private static final long serialVersionUID = 1L;
	private String paymentId;		// 支付ID
	private String transNo;		// 汇付宝交易号记录各种业务类型的交易订单号
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private String productCode;		// 产品编码
	private String type;
	private String transType;		// 交易类型
	private String payType;		// 支付类型
	private String status;		// 支付状态
	private String description;		// 支付说明
	private Date updateTime;		// 更新时间
	private String payAmount;		// 支付金额
	private Date payTime;		// 支付时间
	private String successAmount;		// 成功金额
	private Date successTime;		// 成功时间
	private String merchantOrderNo;		// 商户订单号
	private String bankId;		// 银行ID
	private String bankName;		// 银行名称
	private String bankReturnUrl;		// 银行返回地址
	private String bankcardType;		// 银行卡类型
	private String bankcardNo;		// 银行卡卡号
	private String bankcardOwner;		// 银行卡持卡人姓名
	private String bankcardOwnerIdcard;		// 银行卡持卡人身份证号
	private String bankcardOwnerMobile;		// 银行卡持卡人手机号
	private String bankcardOwnerType;		// 银行卡持卡人类型          0=个人1=企业
	private String bankSerialNo;		// 银行流水号   银行或者渠道流水号
	private String authorizationCode;		// 授权码
	private String channelCode;		// 渠道代码
	private String channelName;		// 渠道名称
	private String channelPartner;	//通道合作方
	private String currency;		// 币种
	private String channelRefNo;//通道参考号(通道标识交易唯一性的标识)

	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间

	private String sortOrder; //升降排序
	private String paymentTypeFilter;
	private String feeAmount;//手续费
	private String feeType;//手续费扣除方式
	private String settleCyc;//结算方式
	private String notifyUrl;//通知地址
	
	

	private String operator;    // 操作人
	private String couponAmount;    // 优惠总额
	
	private String checkStatus;   // 校对状态，CHECKD--已核对，DIFFER--校对不一致
	private Date createTime;    // 创建时间
	private String statisticsDate;
	private String groupType;
	private String walletMerchantId;    // 钱包商户ID
	private String merchantUserId;    // 钱包用户ID


	private Date bankSerialTime;
	private String clearDate;
	private String ext1;
	private String ext2;
	private String ext3;
	private String requestIp;
	private String requestUserIp;
	private String version;

	public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getCouponAmount() {
    return couponAmount;
  }

  public void setCouponAmount(String couponAmount) {
    this.couponAmount = couponAmount;
  }

  public String getCheckStatus() {
    return checkStatus;
  }

  public void setCheckStatus(String checkStatus) {
    this.checkStatus = checkStatus;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

 
	  
	  
	public PaymentRecord() {
		super();
	}

	public PaymentRecord(String id){
		super(id);
	}

	@Length(min=1, max=26, message="支付ID长度必须介于 1 和 26 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	@Length(min=0, max=26, message="汇付宝交易号记录各种业务类型的交易订单号长度必须介于 0 和 26 之间")
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
	
	@Length(min=0, max=4, message="产品编码长度必须介于 0 和 4 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	

	
	@Length(min=0, max=6, message="支付状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1024, message="支付说明长度必须介于 0 和 1024 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="支付时间不能为空")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=0, max=11, message="银行ID长度必须介于 0 和 11 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=0, max=256, message="银行名称长度必须介于 0 和 256 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=256, message="银行返回地址长度必须介于 0 和 256 之间")
	public String getBankReturnUrl() {
		return bankReturnUrl;
	}

	public void setBankReturnUrl(String bankReturnUrl) {
		this.bankReturnUrl = bankReturnUrl;
	}
	
	@Length(min=0, max=6, message="银行卡类型长度必须介于 0 和 6 之间")
	public String getBankcardType() {
		return bankcardType;
	}

	public void setBankcardType(String bankcardType) {
		this.bankcardType = bankcardType;
	}
	
	@Length(min=0, max=256, message="银行卡卡号长度必须介于 0 和 256 之间")
	public String getBankcardNo() {
		try {
			if (StringUtil.notBlank(bankcardNo)) {
				return Aes.decryptStr(bankcardNo, Constants.QuickPay.SYSTEM_KEY);
			} else {
				return bankcardNo;
			}
		} catch (Exception e) {
			return bankcardNo;
		}
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	
	@Length(min=0, max=256, message="银行卡持卡人姓名长度必须介于 0 和 256 之间")
	public String getBankcardOwner() {
		return bankcardOwner;
	}

	public void setBankcardOwner(String bankcardOwner) {
		this.bankcardOwner = bankcardOwner;
	}
	
	@Length(min=0, max=256, message="银行卡持卡人身份证号长度必须介于 0 和 256 之间")
	public String getBankcardOwnerIdcard() {
		try {
			if (StringUtil.notBlank(bankcardOwnerIdcard)) {
				return Aes.decryptStr(bankcardOwnerIdcard, Constants.QuickPay.SYSTEM_KEY);
			} else {
				return bankcardOwnerIdcard;
			}
		} catch (Exception e) {
			return bankcardOwnerIdcard;
		}
	}

	public void setBankcardOwnerIdcard(String bankcardOwnerIdcard) {
		this.bankcardOwnerIdcard = bankcardOwnerIdcard;
	}
	
	@Length(min=0, max=256, message="银行卡持卡人手机号长度必须介于 0 和 256 之间")
	public String getBankcardOwnerMobile() {
		try {
			if (StringUtil.notBlank(bankcardOwnerMobile)) {
				return Aes.decryptStr(bankcardOwnerMobile, Constants.QuickPay.SYSTEM_KEY);
			} else {
				return bankcardOwnerMobile;
			}
		} catch (Exception e) {
			return bankcardOwnerMobile;
		}
	}

	public void setBankcardOwnerMobile(String bankcardOwnerMobile) {
		this.bankcardOwnerMobile = bankcardOwnerMobile;
	}
	
	@Length(min=0, max=4, message="银行卡持卡人类型          0=个人1=企业长度必须介于 0 和 4 之间")
	public String getBankcardOwnerType() {
		return bankcardOwnerType;
	}

	public void setBankcardOwnerType(String bankcardOwnerType) {
		this.bankcardOwnerType = bankcardOwnerType;
	}
	
	@Length(min=0, max=64, message="银行流水号   银行或者渠道流水号长度必须介于 0 和 64 之间")
	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}
	
	@Length(min=0, max=256, message="授权码长度必须介于 0 和 256 之间")
	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	
	@Length(min=0, max=11, message="渠道代码长度必须介于 0 和 11 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=64, message="渠道名称长度必须介于 0 和 64 之间")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelPartner() {
		return channelPartner;
	}

	public void setChannelPartner(String channelPartner) {
		this.channelPartner = channelPartner;
	}

	@Length(min=0, max=8, message="币种长度必须介于 0 和 8 之间")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public String getPaymentTypeFilter() {
		return paymentTypeFilter;
	}

	public void setPaymentTypeFilter(String paymentTypeFilter) {
		this.paymentTypeFilter = paymentTypeFilter;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChannelRefNo() {
		return channelRefNo;
	}

	public void setChannelRefNo(String channelRefNo) {
		this.channelRefNo = channelRefNo;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getSettleCyc() {
		return settleCyc;
	}

	public void setSettleCyc(String settleCyc) {
		this.settleCyc = settleCyc;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getWalletMerchantId() {
		return walletMerchantId;
	}

	public void setWalletMerchantId(String walletMerchantId) {
		this.walletMerchantId = walletMerchantId;
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

	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public Date getBankSerialTime() {
		return bankSerialTime;
	}

	public void setBankSerialTime(Date bankSerialTime) {
		this.bankSerialTime = bankSerialTime;
	}

	public String getClearDate() {
		return clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getRequestUserIp() {
		return requestUserIp;
	}

	public void setRequestUserIp(String requestUserIp) {
		this.requestUserIp = requestUserIp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}