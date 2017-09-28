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
 * 描    述：银联扫码预下单Entity
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
public class PreUnionpayRecord extends DataEntity<PreUnionpayRecord> {
	
	private static final long serialVersionUID = 1L;
	private String prepayId;		// 预下单单号
	private Long merchantId;		// 商户号
	private String merchantLoginName;		// 商户登录名
	private String merchantCompany;		// 商户公司名
	private String merchantMcc;		// 商户MCC码
	private String merchantOrderNo;		// 商户订单号(商品号)
	private String productCode;		// 产品编码
	private String transType;		// 交易类型
	private String qrCode;		// 二维码
	private String limitCount;		// 二维码支付次数限制(0为无限制)
	private String qrValidTime;		// 二维码有效期(单位为秒)
	private Date qrExpireTime;		// 二维码截止时间
	private String requestAmount;		// 二维码单次支付金额(收款方指定，可以为空)
	private String successTimes;		// 成功支付总次数
	private String successAmount;		// 成功支付总金额
	private String payeeInfo;		// 收款方信息
	private String payeeComments;		// 收款方附言
	private String requestDate;		// 商户请求时间yyyyMMddHHmmss
	private String requestIp;		// 商户请求ip
	private String notifyUrl;		// 商户通知地址
	private String callbackUrl;		// 商户回调地址
	private String requestType;		// 来源方式 API/WEB
	private String version;		// 版本号
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String ext1;		// 扩展字段1
	private String ext2;		// 扩展字段2
	private String ext3;		// 扩展字段3
	
	public PreUnionpayRecord() {
		super();
	}

	public PreUnionpayRecord(String id){
		super(id);
	}

	@Length(min=1, max=20, message="预下单单号长度必须介于 1 和 20 之间")
	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	
	@NotNull(message="商户号不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商户登录名长度必须介于 0 和 64 之间")
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	
	@Length(min=0, max=64, message="商户公司名长度必须介于 0 和 64 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	@Length(min=0, max=4, message="商户MCC码长度必须介于 0 和 4 之间")
	public String getMerchantMcc() {
		return merchantMcc;
	}

	public void setMerchantMcc(String merchantMcc) {
		this.merchantMcc = merchantMcc;
	}
	
	@Length(min=1, max=64, message="商户订单号(商品号)长度必须介于 1 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=1, max=4, message="产品编码长度必须介于 1 和 4 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=1, max=6, message="交易类型长度必须介于 1 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Length(min=1, max=512, message="二维码长度必须介于 1 和 512 之间")
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	@Length(min=1, max=10, message="二维码支付次数限制(0为无限制)长度必须介于 1 和 10 之间")
	public String getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount;
	}
	
	@Length(min=1, max=10, message="二维码有效期(单位为秒)长度必须介于 1 和 10 之间")
	public String getQrValidTime() {
		return qrValidTime;
	}

	public void setQrValidTime(String qrValidTime) {
		this.qrValidTime = qrValidTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="二维码截止时间不能为空")
	public Date getQrExpireTime() {
		return qrExpireTime;
	}

	public void setQrExpireTime(Date qrExpireTime) {
		this.qrExpireTime = qrExpireTime;
	}
	
	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}
	
	@Length(min=1, max=10, message="成功支付总次数长度必须介于 1 和 10 之间")
	public String getSuccessTimes() {
		return successTimes;
	}

	public void setSuccessTimes(String successTimes) {
		this.successTimes = successTimes;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	@Length(min=1, max=256, message="收款方信息长度必须介于 1 和 256 之间")
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
	
	@Length(min=1, max=14, message="商户请求时间yyyyMMddHHmmss长度必须介于 1 和 14 之间")
	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	
	@Length(min=0, max=50, message="商户请求ip长度必须介于 0 和 50 之间")
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
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
	
	@Length(min=0, max=3, message="来源方式 API/WEB长度必须介于 0 和 3 之间")
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	@Length(min=0, max=3, message="版本号长度必须介于 0 和 3 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
	
	@Length(min=0, max=64, message="扩展字段1长度必须介于 0 和 64 之间")
	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	@Length(min=0, max=128, message="扩展字段2长度必须介于 0 和 128 之间")
	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
	@Length(min=0, max=256, message="扩展字段3长度必须介于 0 和 256 之间")
	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	
}