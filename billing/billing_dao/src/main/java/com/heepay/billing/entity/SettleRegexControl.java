package com.heepay.billing.entity;

import java.util.Date;

public class SettleRegexControl {
    /**
     * 正则id
     */
    private Long regexControlId;

    /**
     * 规则主键
     */
    private Long ruleId;

    /**
     * 规则key
     */
    private Byte ruleKey;

    /**
     * 规则名称
     */
    private String regexName;

    /**
     * 正则表达式
     */
    private String regexShow;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createAuthor;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateAuthor;

    /**
     * 规则类型 COMM：一代规则，SPECIAL：二代规则
     */
    private String ruleType;

    public Long getRegexControlId() {
        return regexControlId;
    }

    public void setRegexControlId(Long regexControlId) {
        this.regexControlId = regexControlId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Byte getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(Byte ruleKey) {
        this.ruleKey = ruleKey;
    }

    public String getRegexName() {
        return regexName;
    }

    public void setRegexName(String regexName) {
        this.regexName = regexName == null ? null : regexName.trim();
    }

    public String getRegexShow() {
        return regexShow;
    }

    public void setRegexShow(String regexShow) {
        this.regexShow = regexShow == null ? null : regexShow.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(String createAuthor) {
        this.createAuthor = createAuthor == null ? null : createAuthor.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType == null ? null : ruleType.trim();
    }
}