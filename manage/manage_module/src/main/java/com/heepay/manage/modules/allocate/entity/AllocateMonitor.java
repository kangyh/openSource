/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.allocate.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：调拨监听Entity
 *
 * 创 建 者： @author 王亚洪
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
public class AllocateMonitor extends DataEntity<AllocateMonitor> {
	
	private static final long serialVersionUID = 1L;
	private Long monitorId;		// 主键
	private String thresholdValue;		// 阀值
	private String monitorAccount; //监控的备付金账户
	private String email;		// 邮箱
	private String phoneNum;		// 手机
	private String emailStatus;		// 是否发送邮件(Y:是 N:否)
	private String smsStatus;		// 是否发送短信(Y:是 N:否)
	private String creator;		// 操作人
	private String modifier;		// 修改人
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	
	public AllocateMonitor() {
		super();
	}

	public AllocateMonitor(String id){
		super(id);
	}

	public Long getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(Long monitorId) {
		this.monitorId = monitorId;
	}
	
	public String getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(String thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	
	public String getMonitorAccount() {
    return monitorAccount;
  }

  public void setMonitorAccount(String monitorAccount) {
    this.monitorAccount = monitorAccount;
  }

  @Length(min=1, max=2000, message="邮箱长度必须介于 1 和 2000 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	@Length(min=1, max=1, message="是否发送邮件(Y:是 N:否)长度必须介于 1 和 1 之间")
	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}
	
	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}
	
	@Length(min=1, max=50, message="操作人长度必须介于 1 和 50 之间")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Length(min=0, max=50, message="修改人长度必须介于 0 和 50 之间")
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}