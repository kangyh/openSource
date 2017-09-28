package com.heepay.manage.modules.risk.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;
/***
 * 
* 
* 描    述：商户冻结/解冻表
*
* 创 建 者：wangl
* 创建时间：  Nov 18, 20167:02:45 PM
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
public class RiskMerchantFreeze extends DataEntity<RiskMerchantFreeze>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer freezeId;

    private String freezeNo;

    private String freezeType;

    private String defreezeNo;

    private String merchantCode;

    private String merchantName;

    private BigDecimal transAmount;

    private String freezeRemark;

    private String failReason;

    private String status;

    private Date createTime;

    private String createAuthor;

    private Date updateTime;

    private String updateAuthor;
    private String remark1;//备注1
    private String remark2;
    private String remark3;
    private String remark4;
    private String thawMessages;//冻结备注
    private String freezeMessage;//解冻审核
    private String thawLog;//解冻备注

    
    //创建自定义属性
    private Date beginOperEndTime;
    private Date endOperEndTime;
    private String statusTwo;
    
    private String statusthree;
    private String yes;
    
    
    
    
	public String getStatusthree() {
		return statusthree;
	}
	public void setStatusthree(String statusthree) {
		this.statusthree = statusthree;
	}
	public String getYes() {
		return yes;
	}
	public void setYes(String yes) {
		this.yes = yes;
	}
	public String getStatusTwo() {
		return statusTwo;
	}
	public void setStatusTwo(String statusTwo) {
		this.statusTwo = statusTwo;
	}
	public String getThawLog() {
		return thawLog;
	}
	public void setThawLog(String thawLog) {
		this.thawLog = thawLog;
	}
	public String getThawMessages() {
		return thawMessages;
	}
	public void setThawMessages(String thawMessages) {
		this.thawMessages = thawMessages;
	}
	public String getFreezeMessage() {
		return freezeMessage;
	}
	public void setFreezeMessage(String freezeMessage) {
		this.freezeMessage = freezeMessage;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public Integer getFreezeId() {
		return freezeId;
	}
	public void setFreezeId(Integer freezeId) {
		this.freezeId = freezeId;
	}
	public String getFreezeNo() {
		return freezeNo;
	}
	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getUpdateAuthor() {
		return updateAuthor;
	}
	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
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
	@Override
	public String toString() {
		return "RiskMerchantFreeze [freezeId=" + freezeId + ", freezeNo=" + freezeNo + ", freezeType=" + freezeType
				+ ", defreezeNo=" + defreezeNo + ", merchantCode=" + merchantCode + ", merchantName=" + merchantName
				+ ", transAmount=" + transAmount + ", freezeRemark=" + freezeRemark + ", failReason=" + failReason
				+ ", status=" + status + ", createTime=" + createTime + ", createAuthor=" + createAuthor
				+ ", updateTime=" + updateTime + ", updateAuthor=" + updateAuthor + ", beginOperEndTime="
				+ beginOperEndTime + ", endOperEndTime=" + endOperEndTime + "]";
	}
}