/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：风控规则列表Entity
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
public class RiskRulelist extends DataEntity<RiskRulelist> {
	
	private static final long serialVersionUID = 1L;
	private String rulelistId;		// 主键
	private String ruleType;		// 规则类型
	private String monitorObject;		// 监控对像
	private String buziType;		// 业务类型
	private String ruleId;		// 规则ID
	private String ruleDescription;		// 规则描述
	private String riskControlAction;		// 风控措施
	private String riskControlFact;		// 风控因素
	private String otherItem;		// 关联风控项
	private Date createTime;		// 创建时间
	private String status;         //状态
	private String operator;       //操作人
	private Date updateTime;		//更新时间
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	
	public RiskRulelist() {
		super();
	}

	public RiskRulelist(String id){
		super(id);
	}

	public String getRulelistId() {
		return rulelistId;
	}

	public void setRulelistId(String rulelistId) {
		this.rulelistId = rulelistId;
	}
	
	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	public String getMonitorObject() {
		return monitorObject;
	}

	public void setMonitorObject(String monitorObject) {
		this.monitorObject = monitorObject;
	}
	
	public String getBuziType() {
		return buziType;
	}

	public void setBuziType(String buziType) {
		this.buziType = buziType;
	}
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	
	public String getRiskControlAction() {
		return riskControlAction;
	}

	public void setRiskControlAction(String riskControlAction) {
		this.riskControlAction = riskControlAction;
	}
	
	public String getRiskControlFact() {
		return riskControlFact;
	}

	public void setRiskControlFact(String riskControlFact) {
		this.riskControlFact = riskControlFact;
	}
	
	public String getOtherItem() {
		return otherItem;
	}

	public void setOtherItem(String otherItem) {
		this.otherItem = otherItem;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}