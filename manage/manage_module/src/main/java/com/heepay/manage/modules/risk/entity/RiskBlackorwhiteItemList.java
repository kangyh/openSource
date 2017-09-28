package com.heepay.manage.modules.risk.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class RiskBlackorwhiteItemList  extends DataEntity<RiskBlackorwhiteItemList>{
    private Integer blackItemId;

    private Integer blackId;

    private String blackItemValue;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String createAuthor;

    private String updateAuthor;
    
  //非映射字段
    private Date beginCreTime;
    private Date endCreTime;
    private Date beginUpdTime;
    private Date endUpdTime;
//    private String[] blackItemIds;
    private String[] blackItemValues;
    public String[] getBlackItemValues() {
		return blackItemValues;
	}

	public void setBlackItemValues(String[] blackItemValues) {
		this.blackItemValues = blackItemValues;
	}

//	public String[] getBlackItemIds() {
//		return blackItemIds;
//	}
//
//	public void setBlackItemIds(String[] blackItemIds) {
//		this.blackItemIds = blackItemIds;
//	}

	public Date getBeginCreTime() {
		return beginCreTime;
	}

	public void setBeginCreTime(Date beginCreTime) {
		this.beginCreTime = beginCreTime;
	}

	public Date getEndCreTime() {
		return endCreTime;
	}

	public void setEndCreTime(Date endCreTime) {
		this.endCreTime = endCreTime;
	}

	public Date getBeginUpdTime() {
		return beginUpdTime;
	}

	public void setBeginUpdTime(Date beginUpdTime) {
		this.beginUpdTime = beginUpdTime;
	}

	public Date getEndUpdTime() {
		return endUpdTime;
	}

	public void setEndUpdTime(Date endUpdTime) {
		this.endUpdTime = endUpdTime;
	}

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
		this.createAuthor = createAuthor;
	}

	public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }
}