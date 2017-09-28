/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：协议代扣审批Entity
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
public class BankcardAuth extends DataEntity<BankcardAuth> {
	
	private static final long serialVersionUID = 1L;
	private Long authId;		// 签约号
	private Date authTime;		// 签约时间
	private String type;		// 签约类型
	private String status;		// 签约状态
	private String transNo;		// 汇付宝交易号
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private String bankId;		// 银行ID
	private String bankName;		// 银行名称
	private String openBankName;		// 开户银行支行名称
	private String bankInfo;		// 银行信息
	private String bankcardNo;		// 银行卡号
	private String bankcardType;		// 银行卡类型
	private String bankcardExpiredDate;		// 银行卡有效期  yyyyMM
	private String bankcardCvv2;		// 银行卡cvv2
	private String bankcardOwnerMobile;		// 银行卡预留手机号
	private String bankcardOwnerName;		// 持卡人姓名
	private String bankcardOwnerIdcard;		// 持卡人身份证号
	private String bankcardOwnerType;		// 银行卡持卡人类型  0=个人1=企业-1=未知
	private String defaultTag;		// 默认银行卡标记  0=否 1=是
	private String channelCode;		// 渠道代码
	private String channelAuth;		// 渠道授权信息
	private String merchantUserId;		// 商户下用户ID
	private String merchantOrderNo;		// 商户订单号
	private String authorizationCode;		// 授权码
	private String ext1;		// 审批意见
	private String ext2;		// 协议状态
	private String shortCardNo;		// 短卡号(网关)
	private String shortPhnoeNo;		// 短手机号(网关)
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String associateLineNumber;		// 联行号
	private String protocolPath;		// 签约协议存放地址
	private String protocolExpireDate;		// 签约协议有效期截止日期 yyyyMMdd
	private String protocolMaxAmount;		// 委托代收协议最大累计金额
	private String protocolSumAmount;		// 委托代收协议累计使用金额
	private Date beginAuthTime;		// 开始 签约时间
	private Date endAuthTime;		// 结束 签约时间
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间

	private List<String> typeList;

	public BankcardAuth() {
		super();
	}

	public BankcardAuth(String id){
		super(id);
	}

	@NotNull(message="签约号不能为空")
	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}
	
	@Length(min=0, max=6, message="签约类型长度必须介于 0 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=6, message="签约状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=26, message="汇付宝交易号长度必须介于 0 和 26 之间")
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
	
	@Length(min=0, max=20, message="银行ID长度必须介于 0 和 20 之间")
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
	
	@Length(min=0, max=128, message="开户银行支行名称长度必须介于 0 和 128 之间")
	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	
	@Length(min=0, max=256, message="银行信息长度必须介于 0 和 256 之间")
	public String getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}
	
	@Length(min=0, max=256, message="银行卡号长度必须介于 0 和 256 之间")
	public String getBankcardNo() {
		if(StringUtil.isBlank(bankcardNo)){
			return "";
		} else {
			try {
				return Aes.decryptStr(bankcardNo, Constants.QuickPay.SYSTEM_KEY);
			} catch (Exception e) {
				return "";
			}
		}
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	
	@Length(min=0, max=6, message="银行卡类型长度必须介于 0 和 6 之间")
	public String getBankcardType() {
		return bankcardType;
	}

	public void setBankcardType(String bankcardType) {
		this.bankcardType = bankcardType;
	}
	
	@Length(min=0, max=16, message="银行卡有效期  yyyyMM长度必须介于 0 和 16 之间")
	public String getBankcardExpiredDate() {
		return bankcardExpiredDate;
	}

	public void setBankcardExpiredDate(String bankcardExpiredDate) {
		this.bankcardExpiredDate = bankcardExpiredDate;
	}
	
	@Length(min=0, max=8, message="银行卡cvv2长度必须介于 0 和 8 之间")
	public String getBankcardCvv2() {
		return bankcardCvv2;
	}

	public void setBankcardCvv2(String bankcardCvv2) {
		this.bankcardCvv2 = bankcardCvv2;
	}
	
	@Length(min=0, max=256, message="银行卡预留手机号长度必须介于 0 和 256 之间")
	public String getBankcardOwnerMobile() {
		if(StringUtil.isBlank(bankcardOwnerMobile)){
			return "";
		} else {
			try {
				return Aes.decryptStr(bankcardOwnerMobile, Constants.QuickPay.SYSTEM_KEY);
			} catch (Exception e) {
				return "";
			}
		}
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
		if(StringUtil.isBlank(bankcardOwnerIdcard)){
			return "";
		} else {
			try {
				return Aes.decryptStr(bankcardOwnerIdcard, Constants.QuickPay.SYSTEM_KEY);
			} catch (Exception e) {
				return "";
			}
		}
	}

	public void setBankcardOwnerIdcard(String bankcardOwnerIdcard) {
		this.bankcardOwnerIdcard = bankcardOwnerIdcard;
	}
	
	@Length(min=0, max=4, message="银行卡持卡人类型  0=个人1=企业-1=未知长度必须介于 0 和 4 之间")
	public String getBankcardOwnerType() {
		return bankcardOwnerType;
	}

	public void setBankcardOwnerType(String bankcardOwnerType) {
		this.bankcardOwnerType = bankcardOwnerType;
	}
	
	@Length(min=0, max=1, message="默认银行卡标记  0=否 1=是长度必须介于 0 和 1 之间")
	public String getDefaultTag() {
		return defaultTag;
	}

	public void setDefaultTag(String defaultTag) {
		this.defaultTag = defaultTag;
	}
	
	@Length(min=0, max=200, message="渠道代码长度必须介于 0 和 200 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Length(min=0, max=1024, message="渠道授权信息长度必须介于 0 和 1024 之间")
	public String getChannelAuth() {
		return channelAuth;
	}

	public void setChannelAuth(String channelAuth) {
		this.channelAuth = channelAuth;
	}
	
	@Length(min=0, max=64, message="商户下用户ID长度必须介于 0 和 64 之间")
	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=0, max=256, message="授权码长度必须介于 0 和 256 之间")
	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	
	@Length(min=0, max=128, message="审批意见长度必须介于 0 和 128 之间")
	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	@Length(min=0, max=128, message="协议状态长度必须介于 0 和 128 之间")
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
	@Length(min=0, max=32, message="短卡号(网关)长度必须介于 0 和 32 之间")
	public String getShortCardNo() {
		return shortCardNo;
	}

	public void setShortCardNo(String shortCardNo) {
		this.shortCardNo = shortCardNo;
	}
	
	@Length(min=0, max=32, message="短手机号(网关)长度必须介于 0 和 32 之间")
	public String getShortPhnoeNo() {
		return shortPhnoeNo;
	}

	public void setShortPhnoeNo(String shortPhnoeNo) {
		this.shortPhnoeNo = shortPhnoeNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=12, message="联行号长度必须介于 0 和 12 之间")
	public String getAssociateLineNumber() {
		return associateLineNumber;
	}

	public void setAssociateLineNumber(String associateLineNumber) {
		this.associateLineNumber = associateLineNumber;
	}
	
	@Length(min=0, max=128, message="签约协议存放地址长度必须介于 0 和 128 之间")
	public String getProtocolPath() {
		return protocolPath;
	}

	public void setProtocolPath(String protocolPath) {
		this.protocolPath = protocolPath;
	}
	
	@Length(min=0, max=8, message="签约协议有效期截止日期 yyyyMMdd长度必须介于 0 和 8 之间")
	public String getProtocolExpireDate() {
		return protocolExpireDate;
	}

	public void setProtocolExpireDate(String protocolExpireDate) {
		this.protocolExpireDate = protocolExpireDate;
	}
	
	public String getProtocolMaxAmount() {
		return protocolMaxAmount;
	}

	public void setProtocolMaxAmount(String protocolMaxAmount) {
		this.protocolMaxAmount = protocolMaxAmount;
	}
	
	public String getProtocolSumAmount() {
		return protocolSumAmount;
	}

	public void setProtocolSumAmount(String protocolSumAmount) {
		this.protocolSumAmount = protocolSumAmount;
	}
	
	public Date getBeginAuthTime() {
		return beginAuthTime;
	}

	public void setBeginAuthTime(Date beginAuthTime) {
		this.beginAuthTime = beginAuthTime;
	}
	
	public Date getEndAuthTime() {
		return endAuthTime;
	}

	public void setEndAuthTime(Date endAuthTime) {
		this.endAuthTime = endAuthTime;
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

	public List<String> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}
}