package com.heepay.risk.entity;

import java.util.Date;

public class RiskRulelist {
    private Integer rulelistId;

    private String ruleType;

    private String monitorObject;

    private String buziType;

    private String ruleId;

    private String ruleDescription;

    private String riskControlAction;

    private String riskControlFact;

    private String otherItem;

    private Date createTime;
    
    private String status;
    
    private String operator;
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRulelistId() {
        return rulelistId;
    }

    public void setRulelistId(Integer rulelistId) {
        this.rulelistId = rulelistId;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType == null ? null : ruleType.trim();
    }

    public String getMonitorObject() {
        return monitorObject;
    }

    public void setMonitorObject(String monitorObject) {
        this.monitorObject = monitorObject == null ? null : monitorObject.trim();
    }

    public String getBuziType() {
        return buziType;
    }

    public void setBuziType(String buziType) {
        this.buziType = buziType == null ? null : buziType.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription == null ? null : ruleDescription.trim();
    }

    public String getRiskControlAction() {
        return riskControlAction;
    }

    public void setRiskControlAction(String riskControlAction) {
        this.riskControlAction = riskControlAction == null ? null : riskControlAction.trim();
    }

    public String getRiskControlFact() {
        return riskControlFact;
    }

    public void setRiskControlFact(String riskControlFact) {
        this.riskControlFact = riskControlFact == null ? null : riskControlFact.trim();
    }

    public String getOtherItem() {
        return otherItem;
    }

    public void setOtherItem(String otherItem) {
        this.otherItem = otherItem == null ? null : otherItem.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}