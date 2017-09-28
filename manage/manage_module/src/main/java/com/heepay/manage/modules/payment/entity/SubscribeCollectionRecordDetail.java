/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述：订阅支付明细Entity
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
public class SubscribeCollectionRecordDetail extends DataEntity<SubscribeCollectionRecordDetail> {
	
	private static final long serialVersionUID = 1L;
	private String collectDetailId;		// 订阅明细id
	private String subscribeId;		// 订阅id
	private Long merchantId;		// 商户ID
	private String merchantUserId;		// 商户用户ID
	private String collectAmount;		// 代收金额
	private Date createTime;		// 创建时间
	private Date updateTime;		// 修改时间
	private String bankcardNo;		// 卡号
	private String bankcardOwnerMobile;		// 手机号
	private String bankcardOwnerName;		// 姓名
	private String bankcardOwnerIdcard;		// bankcard_owner_idcard
	private String bankName;		// bank_name
	private String bankId;		// bank_id
	private String authCode;		// auth_code
	private String merchantOrderNo;		// merchant_order_no
	private String status;		// status
	private String productCode;		// product_code
	private String transType;		// trans_type
	private Date successTime;		// 创建时间
	private String successAmount;		// 代收成功金额
	private String feeAmount;		// 手续费
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String transferFrom;		//

	public SubscribeCollectionRecordDetail() {
		super();
	}

	public SubscribeCollectionRecordDetail(String id){
		super(id);
	}

	@Length(min=1, max=32, message="订阅明细id长度必须介于 1 和 32 之间")
	public String getCollectDetailId() {
		return collectDetailId;
	}

	public void setCollectDetailId(String collectDetailId) {
		this.collectDetailId = collectDetailId;
	}
	
	@Length(min=1, max=32, message="订阅id长度必须介于 1 和 32 之间")
	public String getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商户用户ID长度必须介于 0 和 64 之间")
	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	
	public String getCollectAmount() {
		return collectAmount;
	}

	public void setCollectAmount(String collectAmount) {
		this.collectAmount = collectAmount;
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
	@NotNull(message="修改时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=256, message="卡号长度必须介于 0 和 256 之间")
	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	
	@Length(min=0, max=256, message="手机号长度必须介于 0 和 256 之间")
	public String getBankcardOwnerMobile() {
		return bankcardOwnerMobile;
	}

	public void setBankcardOwnerMobile(String bankcardOwnerMobile) {
		this.bankcardOwnerMobile = bankcardOwnerMobile;
	}
	
	@Length(min=0, max=256, message="姓名长度必须介于 0 和 256 之间")
	public String getBankcardOwnerName() {
		return bankcardOwnerName;
	}

	public void setBankcardOwnerName(String bankcardOwnerName) {
		this.bankcardOwnerName = bankcardOwnerName;
	}
	
	@Length(min=0, max=256, message="bankcard_owner_idcard长度必须介于 0 和 256 之间")
	public String getBankcardOwnerIdcard() {
		return bankcardOwnerIdcard;
	}

	public void setBankcardOwnerIdcard(String bankcardOwnerIdcard) {
		this.bankcardOwnerIdcard = bankcardOwnerIdcard;
	}
	
	@Length(min=0, max=128, message="bank_name长度必须介于 0 和 128 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=20, message="bank_id长度必须介于 0 和 20 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=0, max=64, message="auth_code长度必须介于 0 和 64 之间")
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	@Length(min=0, max=64, message="merchant_order_no长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=0, max=6, message="status长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=6, message="product_code长度必须介于 0 和 6 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=6, message="trans_type长度必须介于 0 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
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

	public String getTransferFrom() {
		return transferFrom;
	}

	public void setTransferFrom(String transferFrom) {
		this.transferFrom = transferFrom;
	}
}