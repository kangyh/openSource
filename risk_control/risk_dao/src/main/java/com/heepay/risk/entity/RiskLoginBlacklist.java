package com.heepay.risk.entity;

import java.util.Date;

public class RiskLoginBlacklist {
    private Integer blackId;

    private String companyName;

    private String buziCode;

    private String ownerName;

    private String ownerId;

    private Date createTime;

    public Integer getBlackId() {
        return blackId;
    }

    public void setBlackId(Integer blackId) {
        this.blackId = blackId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getBuziCode() {
        return buziCode;
    }

    public void setBuziCode(String buziCode) {
        this.buziCode = buziCode == null ? null : buziCode.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}