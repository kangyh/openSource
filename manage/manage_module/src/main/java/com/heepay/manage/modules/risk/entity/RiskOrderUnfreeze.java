package com.heepay.manage.modules.risk.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class RiskOrderUnfreeze extends DataEntity<RiskOrderUnfreeze> {
	/**
	 * 描 述：
	 *
	 * 创 建 者：wangl 创建时间： Nov 22, 20162:39:49 PM
	 */
	private static final long serialVersionUID = 1L;

	private String unfreezeNo;

	private String freezeNo;

	private String status;

	private String updateAuthor;

	private String unfreezeRemark;

	// 关联查询时用于映射值
	private Integer freezeId;

	private String freezeType;

	private String defreezeNo;

	private String orderNo;

	private String transNo;
	private String transType;

	private BigDecimal transAmount;

	private String freezeRemark;

	private String failReason;

	private Date createTime;

	private String createAuthor;

	private Date updateTime;

	private String merchantCode;
	private String merchantName;
	private String transStatus;
	private String checkMessage;// 解冻理由

	// 创建自定义属性
	private Date beginOperEndTime;
	private Date endOperEndTime;

	public Integer getFreezeId() {
		return freezeId;
	}

	public void setFreezeId(Integer freezeId) {
		this.freezeId = freezeId;
	}

	public String getFreezeType() {
		return freezeType;
	}

	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}

	public String getDefreezeNo() {
		return defreezeNo;
	}

	public void setDefreezeNo(String defreezeNo) {
		this.defreezeNo = defreezeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getFreezeRemark() {
		return freezeRemark;
	}

	public void setFreezeRemark(String freezeRemark) {
		this.freezeRemark = freezeRemark;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getCheckMessage() {
		return checkMessage;
	}

	public void setCheckMessage(String checkMessage) {
		this.checkMessage = checkMessage;
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

	public String getUnfreezeNo() {
		return unfreezeNo;
	}

	public void setUnfreezeNo(String unfreezeNo) {
		this.unfreezeNo = unfreezeNo == null ? null : unfreezeNo.trim();
	}

	public String getFreezeNo() {
		return freezeNo;
	}

	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo == null ? null : freezeNo.trim();
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
	}

	public String getUnfreezeRemark() {
		return unfreezeRemark;
	}

	public void setUnfreezeRemark(String unfreezeRemark) {
		this.unfreezeRemark = unfreezeRemark == null ? null : unfreezeRemark.trim();
	}

	@Override
	public String toString() {
		return "RiskOrderUnfreeze [unfreezeNo=" + unfreezeNo + ", freezeNo=" + freezeNo + ", status=" + status
				+ ", updateAuthor=" + updateAuthor + ", unfreezeRemark=" + unfreezeRemark + ", freezeId=" + freezeId
				+ ", freezeType=" + freezeType + ", defreezeNo=" + defreezeNo + ", orderNo=" + orderNo + ", transNo="
				+ transNo + ", transType=" + transType + ", transAmount=" + transAmount + ", freezeRemark="
				+ freezeRemark + ", failReason=" + failReason + ", createTime=" + createTime + ", createAuthor="
				+ createAuthor + ", updateTime=" + updateTime + ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", transStatus=" + transStatus + ", checkMessage=" + checkMessage
				+ ", beginOperEndTime=" + beginOperEndTime + ", endOperEndTime=" + endOperEndTime + "]";
	}
}