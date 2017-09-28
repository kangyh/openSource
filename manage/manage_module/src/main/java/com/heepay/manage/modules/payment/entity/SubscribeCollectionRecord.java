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
 * 描    述：订阅支付申请Entity
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
public class SubscribeCollectionRecord extends DataEntity<SubscribeCollectionRecord> {
	
	private static final long serialVersionUID = 1L;
	private String subscribeId;		// 订阅id
	private String merchantOrderNo;		// 商户订单号
	private Long merchantId;		// 商户ID
	private String notifyUrl;		// 异步通知地址
	private String totalAmount;		// 总金额
	private Date createTime;		// 创建时间
	private Date updateTime;		// 修改时间
	private String successTotalAmount;		// 成功金额
	private String successTotalNum;		// 成功笔数
	private String transferFrom;		// 代扣请求来源（API\WEB\JOB）
	private String withholdRate;		// 扣款频率
	private String withholdAmount;		// 单次扣款金额
	private String withholdTotalAmount;		// 已扣款金额
	private Date withholdBeginTime;		// 开始扣款时间
	private Date withholdLastTime;		// withhold_last_time
	private Date withholdPreTime;		// 前次扣款时间
	private Date withholdLatterTime;		// 下次扣款时间
	private String subscribeStatus;		// 申请状态
	private String authCode;		// auth_code
	
	public SubscribeCollectionRecord() {
		super();
	}

	public SubscribeCollectionRecord(String id){
		super(id);
	}

	@Length(min=1, max=32, message="订阅id长度必须介于 1 和 32 之间")
	public String getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=128, message="异步通知地址长度必须介于 0 和 128 之间")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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
	
	public String getSuccessTotalAmount() {
		return successTotalAmount;
	}

	public void setSuccessTotalAmount(String successTotalAmount) {
		this.successTotalAmount = successTotalAmount;
	}
	
	@Length(min=0, max=11, message="成功笔数长度必须介于 0 和 11 之间")
	public String getSuccessTotalNum() {
		return successTotalNum;
	}

	public void setSuccessTotalNum(String successTotalNum) {
		this.successTotalNum = successTotalNum;
	}
	
	@Length(min=0, max=3, message="代扣请求来源长度必须介于 0 和 3 之间")
	public String getTransferFrom() {
		return transferFrom;
	}

	public void setTransferFrom(String transferFrom) {
		this.transferFrom = transferFrom;
	}
	
	@Length(min=1, max=1, message="扣款频率长度必须介于 1 和 1 之间")
	public String getWithholdRate() {
		return withholdRate;
	}

	public void setWithholdRate(String withholdRate) {
		this.withholdRate = withholdRate;
	}
	
	public String getWithholdAmount() {
		return withholdAmount;
	}

	public void setWithholdAmount(String withholdAmount) {
		this.withholdAmount = withholdAmount;
	}
	
	public String getWithholdTotalAmount() {
		return withholdTotalAmount;
	}

	public void setWithholdTotalAmount(String withholdTotalAmount) {
		this.withholdTotalAmount = withholdTotalAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始扣款时间不能为空")
	public Date getWithholdBeginTime() {
		return withholdBeginTime;
	}

	public void setWithholdBeginTime(Date withholdBeginTime) {
		this.withholdBeginTime = withholdBeginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWithholdLastTime() {
		return withholdLastTime;
	}

	public void setWithholdLastTime(Date withholdLastTime) {
		this.withholdLastTime = withholdLastTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="前次扣款时间不能为空")
	public Date getWithholdPreTime() {
		return withholdPreTime;
	}

	public void setWithholdPreTime(Date withholdPreTime) {
		this.withholdPreTime = withholdPreTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="下次扣款时间不能为空")
	public Date getWithholdLatterTime() {
		return withholdLatterTime;
	}

	public void setWithholdLatterTime(Date withholdLatterTime) {
		this.withholdLatterTime = withholdLatterTime;
	}
	
	@Length(min=1, max=6, message="申请状态长度必须介于 1 和 6 之间")
	public String getSubscribeStatus() {
		return subscribeStatus;
	}

	public void setSubscribeStatus(String subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}
	
	@Length(min=0, max=256, message="auth_code长度必须介于 0 和 256 之间")
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
}