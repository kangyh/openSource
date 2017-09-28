/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 提现管理Entity
 * @author hs
 * @version V1.0
 */
public class UserCenterRecord extends DataEntity<UserCenterRecord> {
	
	private static final long serialVersionUID = 1L;
	private String transNo;		// 交易单号
	private Long userId;		// 用户id
	private String transType;		//交易类型
	private Date createTime;		// 创建时间
	private String transStatus;		//交易状态
	private String userLoginName;		//用户登录名
	private Long userName;		//用户名称
	private String transAmount;		// 交易金额
	private Date feeAmount;		//手续费
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间

	private String sortOrder; //升降排序

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public Long getUserName() {
		return userName;
	}

	public void setUserName(Long userName) {
		this.userName = userName;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public Date getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Date feeAmount) {
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

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}


	
	
}