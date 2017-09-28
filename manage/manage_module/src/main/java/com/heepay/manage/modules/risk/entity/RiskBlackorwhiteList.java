package com.heepay.manage.modules.risk.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class RiskBlackorwhiteList extends DataEntity<RiskBlackorwhiteList>{
    private Integer blackId;

    private String name;

    private String type;

    private String status;

    private String productCode;

    private String category;

    private String desc;

    private Date createTime;

    private Date updateTime;

    private String createAuthor;

    private String updateAuthor;

    
    //非映射字段
    private Date beginCreTime;
    private Date endCreTime;
    private Date beginUpdTime;
    private Date endUpdTime;
    private String[] blackIds;
    
    public String[] getBlackIds() {
		return blackIds;
	}

	public void setBlackIds(String[] blackIds) {
		this.blackIds = blackIds;
	}

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

	public Integer getBlackId() {
        return blackId;
    }

    public void setBlackId(Integer blackId) {
        this.blackId = blackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
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