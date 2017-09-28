package com.heepay.manage.modules.reconciliation.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class SettleRegexControl extends DataEntity<SettleRegexControl> {
	
    /**
	 * @方法说明：
	 * @时间： 2017年1月17日下午4:51:33
	 * @创建人：wangl
	 */
	private static final long serialVersionUID = 1L;

	private Long regexControlId;

    private Long ruleId;

    private Long ruleKey;

    private String regexName;

    private String regexShow;

    private Date createTime;

    private String createAuthor;

    private Date updateTime;

    private String updateAuthor;
    
    private Date beginCreateTime;
    
    private Date endCreateTime;

    private String ruleType;

    //映射字段
    private Long idValue;
    
    public Long getIdValue() {
		return idValue;
	}

	public void setIdValue(Long idValue) {
		this.idValue = idValue;
	}

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

   
    public Long getRuleKey() {
		return ruleKey;
	}

	public void setRuleKey(Long ruleKey) {
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

	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
}