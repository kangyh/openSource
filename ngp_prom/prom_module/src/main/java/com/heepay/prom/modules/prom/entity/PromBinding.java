/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.entity;

import com.heepay.prom.common.persistence.DataEntity;

import java.util.Date;

/**
 *
 * 描    述：promEntity
 *
 * 创 建 者： @author wj
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
public class PromBinding extends DataEntity<PromBinding> {
	
	private static final long serialVersionUID = 1L;
	private Long promId;		// ID
	private String bindingId;		// 绑定编码
	private String merchantId;		// 商户编码
	private String merchantName;		// 商户名称
	private String promotionId;		// 推广位ID
	private Date effectiveTime;		// 生效时间
	private Date loseTime;		// 失效时间
	private String status;		// 状态
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	private Date beginOperEndTime1;
	private Date endOperEndTime1;
	
	public PromBinding() {
		super();
	}

	public PromBinding(String id){
		super(id);
	}

	public Long getPromId() {
		return promId;
	}

	public void setPromId(Long promId) {
		this.promId = promId;
	}
	
	public String getBindingId() {
		return bindingId;
	}

	public void setBindingId(String bindingId) {
		this.bindingId = bindingId;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	
	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	
	public Date getLoseTime() {
		return loseTime;
	}

	public void setLoseTime(Date loseTime) {
		this.loseTime = loseTime;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public Date getBeginOperEndTime1() {
		return beginOperEndTime1;
	}

	public void setBeginOperEndTime1(Date beginOperEndTime1) {
		this.beginOperEndTime1 = beginOperEndTime1;
	}

	public Date getEndOperEndTime1() {
		return endOperEndTime1;
	}

	public void setEndOperEndTime1(Date endOperEndTime1) {
		this.endOperEndTime1 = endOperEndTime1;
	}
	
}