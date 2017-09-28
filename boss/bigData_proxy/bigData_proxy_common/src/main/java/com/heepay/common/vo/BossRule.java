package com.heepay.common.vo;

import java.util.Date;

public class BossRule {
	
	private Long ruleId;		// 主键
	private Date startDate;		// 创建日期
	private Date endDate;		// 结束日期
	private String creatAuthor;		// 创建者
	private Date createTime;		// 创建时间
	private String updateAuthor;		// 更新者
	private Date updateTime;		// 更新时间
	private Date jobStartTime;		// 任务开始时间
	private Date jobEndTime;		// 任务结束时间
	private String takeTime;		// 耗时
	private String jobStatus;		// 任务状态
	private String remark;		// 备注
	
	
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCreatAuthor() {
		return creatAuthor;
	}
	public void setCreatAuthor(String creatAuthor) {
		this.creatAuthor = creatAuthor;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateAuthor() {
		return updateAuthor;
	}
	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getJobStartTime() {
		return jobStartTime;
	}
	public void setJobStartTime(Date jobStartTime) {
		this.jobStartTime = jobStartTime;
	}
	public Date getJobEndTime() {
		return jobEndTime;
	}
	public void setJobEndTime(Date jobEndTime) {
		this.jobEndTime = jobEndTime;
	}
	public String getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
