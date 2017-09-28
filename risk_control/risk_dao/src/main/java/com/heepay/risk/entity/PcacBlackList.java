package com.heepay.risk.entity;

import java.util.Date;

public class PcacBlackList {
    private Long blackId;

    private String bachNo;

    private String regName;

    private String cusName;

    private String docType;

    private String docCode;

    private String docName;

    private String legDocType;

    private String legDocCode;

    private String level;

    private String riskType;

    private Date validDate;

    private String validStatus;

    private Date createtime;

    public Long getBlackId() {
        return blackId;
    }

    public void setBlackId(Long blackId) {
        this.blackId = blackId;
    }

    public String getBachNo() {
        return bachNo;
    }

    public void setBachNo(String bachNo) {
        this.bachNo = bachNo == null ? null : bachNo.trim();
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName == null ? null : regName.trim();
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName == null ? null : cusName.trim();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode == null ? null : docCode.trim();
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName == null ? null : docName.trim();
    }

    public String getLegDocType() {
        return legDocType;
    }

    public void setLegDocType(String legDocType) {
        this.legDocType = legDocType == null ? null : legDocType.trim();
    }

    public String getLegDocCode() {
        return legDocCode;
    }

    public void setLegDocCode(String legDocCode) {
        this.legDocCode = legDocCode == null ? null : legDocCode.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType == null ? null : riskType.trim();
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus == null ? null : validStatus.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}