package com.heepay.boss.entity.vo;

import java.util.Date;

public class BossRuleModel {
	 private Long ruleId;

	    private String startDate;

	    private String endDate;

	    private String creatAuthor;

	    private String createTime;

	    private String updateAuthor;

	    private String updateTime;

	    private String jobStartTime;

	    private String jobEndTime;

	    private String takeTime;

	    private String jobStatus;

	    private String remark;

	    public Long getRuleId() {
	        return ruleId;
	    }

	    public void setRuleId(Long ruleId) {
	        this.ruleId = ruleId;
	    }

	    public String getStartDate() {
	        return startDate;
	    }

	    public void setStartDate(String startDate) {
	        this.startDate = startDate;
	    }

	    public String getEndDate() {
	        return endDate;
	    }

	    public void setEndDate(String endDate) {
	        this.endDate = endDate;
	    }

	    public String getCreatAuthor() {
	        return creatAuthor;
	    }

	    public void setCreatAuthor(String creatAuthor) {
	        this.creatAuthor = creatAuthor == null ? null : creatAuthor.trim();
	    }

	    public String getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(String createTime) {
	        this.createTime = createTime;
	    }

	    public String getUpdateAuthor() {
	        return updateAuthor;
	    }

	    public void setUpdateAuthor(String updateAuthor) {
	        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
	    }

	    public String getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(String updateTime) {
	        this.updateTime = updateTime;
	    }

	    public String getJobStartTime() {
	        return jobStartTime;
	    }

	    public void setJobStartTime(String jobStartTime) {
	        this.jobStartTime = jobStartTime;
	    }

	    public String getJobEndTime() {
	        return jobEndTime;
	    }

	    public void setJobEndTime(String jobEndTime) {
	        this.jobEndTime = jobEndTime;
	    }

	    public String getTakeTime() {
	        return takeTime;
	    }

	    public void setTakeTime(String takeTime) {
	        this.takeTime = takeTime == null ? null : takeTime.trim();
	    }

	    public String getJobStatus() {
	        return jobStatus;
	    }

	    public void setJobStatus(String jobStatus) {
	        this.jobStatus = jobStatus == null ? null : jobStatus.trim();
	    }

	    public String getRemark() {
	        return remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark == null ? null : remark.trim();
	    }

}
