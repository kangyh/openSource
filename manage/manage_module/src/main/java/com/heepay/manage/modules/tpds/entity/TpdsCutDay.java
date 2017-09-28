package com.heepay.manage.modules.tpds.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class TpdsCutDay extends DataEntity<TpdsCutDay>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer tpdsCutDayId;

    private String busiNo;

    private String cutType;

    private String cutTime;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

    private String status;

    //非映射字段
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

    private Date time;
    
    
 
	public String getCutTime() {
		return cutTime;
	}

	public void setCutTime(String cutTime) {
		this.cutTime = cutTime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getBusiNo() {
        return busiNo;
    }

    public void setBusiNo(String busiNo) {
        this.busiNo = busiNo == null ? null : busiNo.trim();
    }

    public String getCutType() {
        return cutType;
    }

    public void setCutType(String cutType) {
        this.cutType = cutType == null ? null : cutType.trim();
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public Integer getTpdsCutDayId() {
		return tpdsCutDayId;
	}

	public void setTpdsCutDayId(Integer tpdsCutDayId) {
		this.tpdsCutDayId = tpdsCutDayId;
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
}