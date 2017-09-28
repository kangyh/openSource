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
 * 描    述：量化派白条支付记录Entity
 *
 * 创 建 者： @author TangWei
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
public class QuantGroupPayRecord extends DataEntity<QuantGroupPayRecord> {
	
	private static final long serialVersionUID = 1L;
	private String quantId;		// 分期支付ID
	private Long merchantId;		// 商户ID
	private String phoneno;		// 用户手机号
	private String extData;		// 商品描述信息  量化派返回的时候回带着
	private String userIp;		// 商户用户IP
	private String orderName;		// 订单展示信息
	private String orderMerchant;		// 订单所属分店
	private String orderAmount;		// 订单金额
	private String term;		// 付款分期 0:不分期 1:3期 2:6期   3:9期   4:12期
	private String payMode;		// 支付方式 0，即时支付  1，冻结支付
	private Date createTime;		// 创建时间
	private Date updateTime;		// 修改时间
	private String status;		// 状态
	private String beginOrderAmount;		// 开始 订单金额
	private String endOrderAmount;		// 结束 订单金额
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String paymentId;			//商户订单号
	private String bankSerialNo;		//银行流水号   银行或者渠道流水号
	private String repaymentPlanData;		//分期费率信息
	private String groupType;			//时间类型
	
	public QuantGroupPayRecord() {
		super();
	}

	public QuantGroupPayRecord(String id){
		super(id);
	}

	@Length(min=1, max=32, message="分期支付ID长度必须介于 1 和 32 之间")
	public String getQuantId() {
		return quantId;
	}

	public void setQuantId(String quantId) {
		this.quantId = quantId;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=1, max=20, message="用户手机号长度必须介于 1 和 20 之间")
	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	
	@Length(min=0, max=256, message="商品描述信息  量化派返回的时候回带着长度必须介于 0 和 256 之间")
	public String getExtData() {
		return extData;
	}

	public void setExtData(String extData) {
		this.extData = extData;
	}
	
	@Length(min=0, max=32, message="商户用户IP长度必须介于 0 和 32 之间")
	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	@Length(min=0, max=128, message="订单展示信息长度必须介于 0 和 128 之间")
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
	@Length(min=0, max=128, message="订单所属分店长度必须介于 0 和 128 之间")
	public String getOrderMerchant() {
		return orderMerchant;
	}

	public void setOrderMerchant(String orderMerchant) {
		this.orderMerchant = orderMerchant;
	}
	
	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	@Length(min=0, max=2, message="付款分期 0:不分期 1:3期 2:6期   3:9期   4:12期长度必须介于 0 和 2 之间")
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
	
	@Length(min=0, max=2, message="支付方式 0，即时支付  1，冻结支付长度必须介于 0 和 2 之间")
	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
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
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBeginOrderAmount() {
		return beginOrderAmount;
	}

	public void setBeginOrderAmount(String beginOrderAmount) {
		this.beginOrderAmount = beginOrderAmount;
	}
	
	public String getEndOrderAmount() {
		return endOrderAmount;
	}

	public void setEndOrderAmount(String endOrderAmount) {
		this.endOrderAmount = endOrderAmount;
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

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}

	public String getRepaymentPlanData() {
		return repaymentPlanData;
	}

	public void setRepaymentPlanData(String repaymentPlanData) {
		this.repaymentPlanData = repaymentPlanData;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
}