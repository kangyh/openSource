package com.heepay.risk.entity;

import java.util.Date;

public class RiskBlockInfo {
    private Integer blockId;

    private String blockType;

    private String ruleId;

    private String fileds;

    private String monitorObject;

    private String buziType;

    private Date createtime;


    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType == null ? null : blockType.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getFileds() {
        return fileds;
    }

    public void setFileds(String fileds) {
        this.fileds = fileds == null ? null : fileds.trim();
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }
}