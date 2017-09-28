package com.heepay.risk.entity;

import java.util.Date;

public class BlackorwhiteItemList {
    private Integer blackItemId;

    private Integer blackId;

    private String blackItemValue;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String createAuthor;

    private String updateAuthor;

    public Integer getBlackItemId() {
        return blackItemId;
    }

    public void setBlackItemId(Integer blackItemId) {
        this.blackItemId = blackItemId;
    }

    public Integer getBlackId() {
        return blackId;
    }

    public void setBlackId(Integer blackId) {
        this.blackId = blackId;
    }

    public String getBlackItemValue() {
        return blackItemValue;
    }

    public void setBlackItemValue(String blackItemValue) {
        this.blackItemValue = blackItemValue == null ? null : blackItemValue.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(String createAuthor) {
        this.createAuthor = createAuthor == null ? null : createAuthor.trim();
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }
}