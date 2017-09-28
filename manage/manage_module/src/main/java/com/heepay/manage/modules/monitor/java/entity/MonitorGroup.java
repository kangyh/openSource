package com.heepay.manage.modules.monitor.java.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

/***
 * 
* 
* 描    述：
*
* 创 建 者： wangl
* 创建时间：  2017年1月20日上午10:07:00
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
 */
public class MonitorGroup extends DataEntity<MonitorGroup>{
    /**
	 * @方法说明：
	 * @时间： 2017年1月20日上午10:07:03
	 * @创建人：wangl
	 */
	private static final long serialVersionUID = 1L;

	private Integer groupId;

    private String groupName;

    private String mark;

    private Date createTime;
    
    private Date updateTime;
    
    private String createAuthor;
    
    private String updateAuthor;
    //非映射字段
    private Date beginOperEndTime;
    
    private Date endOperEndTime;
    
    
    
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
		this.updateAuthor = updateAuthor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}