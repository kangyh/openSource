/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.entity;

import com.heepay.prom.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 描    述：订单管理Entity
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
public class PromOrder extends DataEntity<PromOrder> {
	
	private static final long serialVersionUID = 1L;
	private Long promId;		// ID
	private String orderId;		// 订单号
	private String warrantyId;		// 保单号
	private String spreadId;		// 推广位ID
	private String spreadName;		// 推广位名称
	private String productName;		// 产品名称
	private String spreadWay;		// 推广方式
	private String commissionStatus;		// 佣金状态
	private BigDecimal orderMoney;		// 订单总额（元）
	private Date createTime;		// 下单时间
	private String cocerPeople;		// 投保人
	private String coverAddress;		// 投保地址
	private BigDecimal spreadMoney;		// 推广费（元）
	private Date effectTime;		// 生效时间
	private String companyName;		// 公司名称
	private String orderBath;		// 订单批次号
	private Date dealTime;		// 处理时间
	private String dealPeople;		// 处理人
	private String isProfit;		// 是否分润
	private Date profitTime;		// 分润时间
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	
	public PromOrder() {
		super();
	}

	public PromOrder(String id){
		super(id);
	}

	public Long getPromId() {
		return promId;
	}

	public void setPromId(Long promId) {
		this.promId = promId;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getWarrantyId() {
		return warrantyId;
	}

	public void setWarrantyId(String warrantyId) {
		this.warrantyId = warrantyId;
	}
	
	public String getSpreadId() {
		return spreadId;
	}

	public void setSpreadId(String spreadId) {
		this.spreadId = spreadId;
	}
	
	public String getSpreadName() {
		return spreadName;
	}

	public void setSpreadName(String spreadName) {
		this.spreadName = spreadName;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getSpreadWay() {
		return spreadWay;
	}

	public void setSpreadWay(String spreadWay) {
		this.spreadWay = spreadWay;
	}
	
	public String getCommissionStatus() {
		return commissionStatus;
	}

	public void setCommissionStatus(String commissionStatus) {
		this.commissionStatus = commissionStatus;
	}
	
	
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCocerPeople() {
		return cocerPeople;
	}

	public void setCocerPeople(String cocerPeople) {
		this.cocerPeople = cocerPeople;
	}
	
	public String getCoverAddress() {
		return coverAddress;
	}

	public void setCoverAddress(String coverAddress) {
		this.coverAddress = coverAddress;
	}
	
	
	
	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getOrderBath() {
		return orderBath;
	}

	public void setOrderBath(String orderBath) {
		this.orderBath = orderBath;
	}
	
	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	
	public String getDealPeople() {
		return dealPeople;
	}

	public void setDealPeople(String dealPeople) {
		this.dealPeople = dealPeople;
	}
	
	public String getIsProfit() {
		return isProfit;
	}

	public void setIsProfit(String isProfit) {
		this.isProfit = isProfit;
	}
	
	public Date getProfitTime() {
		return profitTime;
	}

	public void setProfitTime(Date profitTime) {
		this.profitTime = profitTime;
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

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getSpreadMoney() {
		return spreadMoney;
	}

	public void setSpreadMoney(BigDecimal spreadMoney) {
		this.spreadMoney = spreadMoney;
	}
	
}