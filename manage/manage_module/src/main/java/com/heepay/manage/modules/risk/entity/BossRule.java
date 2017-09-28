/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：报表规则配置Entity
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
public class BossRule extends DataEntity<BossRule> {
	
	private static final long serialVersionUID = 1L;
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
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	
	private Date beginOperEndTime1;
	private Date endOperEndTime1;
	
	public BossRule() {
		super();
	}

	public BossRule(String id){
		super(id);
	}

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

	public Date getBeginOperEndTime1() {
		return beginOperEndTime1;
	}

	public void setBeginOperEndTime1(Date beginOperEndTime1) {
		this.beginOperEndTime1 = beginOperEndTime1;
	}

	public Date getEndOperEndTime1() {
		return endOperEndTime1;
	}

	public void setEndOperEndTime1(Date endOperEndTime1) {
		this.endOperEndTime1 = endOperEndTime1;
	}
	
}