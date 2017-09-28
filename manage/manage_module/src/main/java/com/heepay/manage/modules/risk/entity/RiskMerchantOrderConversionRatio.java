/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：风控商户统计Entity
 *
 * 创 建 者： @author xch
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
public class RiskMerchantOrderConversionRatio extends DataEntity<RiskMerchantOrderConversionRatio> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商户编号
	private String merchantSucessOrder;		// 商户成功订单
	private String merchantTotalOrder;		// 商户总订单
	private Date beginTime;		// 开始时间
	private Date endTime;		// 结束时间
	private Date createTime;		// 创建时间
	private String sucessRatio;		// 成功率
	private String productCode;		// product_code
	private String productName;		// product_name
	private String host;		// host
	private String beginStatisticTime;
	private String endStatisticTime;
	
	public RiskMerchantOrderConversionRatio() {
		super();
	}

	public RiskMerchantOrderConversionRatio(String id){
		super(id);
	}

	@Length(min=0, max=30, message="商户编号长度必须介于 0 和 30 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=11, message="商户成功订单长度必须介于 0 和 11 之间")
	public String getMerchantSucessOrder() {
		return merchantSucessOrder;
	}

	public void setMerchantSucessOrder(String merchantSucessOrder) {
		this.merchantSucessOrder = merchantSucessOrder;
	}
	
	@Length(min=0, max=11, message="商户总订单长度必须介于 0 和 11 之间")
	public String getMerchantTotalOrder() {
		return merchantTotalOrder;
	}

	public void setMerchantTotalOrder(String merchantTotalOrder) {
		this.merchantTotalOrder = merchantTotalOrder;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=11, message="成功率长度必须介于 0 和 11 之间")
	public String getSucessRatio() {
		return sucessRatio;
	}

	public void setSucessRatio(String sucessRatio) {
		this.sucessRatio = sucessRatio;
	}
	
	@Length(min=0, max=20, message="product_code长度必须介于 0 和 20 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=200, message="product_name长度必须介于 0 和 200 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=200, message="host长度必须介于 0 和 200 之间")
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}


	public String getBeginStatisticTime() {
		return beginStatisticTime;
	}

	public void setBeginStatisticTime(String beginStatisticTime) {
		this.beginStatisticTime = beginStatisticTime;
	}

	public String getEndStatisticTime() {
		return endStatisticTime;
	}

	public void setEndStatisticTime(String endStatisticTime) {
		this.endStatisticTime = endStatisticTime;
	}
}