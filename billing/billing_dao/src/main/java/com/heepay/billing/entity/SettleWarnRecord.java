package com.heepay.billing.entity;

import java.util.Date;

public class SettleWarnRecord {
    private Long warnId;

    private String warnBlock;

    private String contactPerson;

    private String warnContext;

    private String warnLevel;

    private String handleType;

    private Date operTime;

    private String status;

    public Long getWarnId() {
        return warnId;
    }

    public void setWarnId(Long warnId) {
        this.warnId = warnId;
    }

    public String getWarnBlock() {
        return warnBlock;
    }

    public void setWarnBlock(String warnBlock) {
        this.warnBlock = warnBlock == null ? null : warnBlock.trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getWarnContext() {
        return warnContext;
    }

    public void setWarnContext(String warnContext) {
        this.warnContext = warnContext == null ? null : warnContext.trim();
    }

    public String getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(String warnLevel) {
        this.warnLevel = warnLevel == null ? null : warnLevel.trim();
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType == null ? null : handleType.trim();
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}