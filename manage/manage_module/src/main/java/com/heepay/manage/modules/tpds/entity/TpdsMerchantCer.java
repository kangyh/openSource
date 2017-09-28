package com.heepay.manage.modules.tpds.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class TpdsMerchantCer extends DataEntity<TpdsMerchantCer>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer tpdsMerchantCerId;

    private String merchantNo;

    private String pubKey;

    private String priKey;

    private String cerPath;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

    private String note;

    //非映射字段
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey == null ? null : pubKey.trim();
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey == null ? null : priKey.trim();
    }

    public String getCerPath() {
        return cerPath;
    }

    public void setCerPath(String cerPath) {
        this.cerPath = cerPath == null ? null : cerPath.trim();
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

	public Integer getTpdsMerchantCerId() {
		return tpdsMerchantCerId;
	}

	public void setTpdsMerchantCerId(Integer tpdsMerchantCerId) {
		this.tpdsMerchantCerId = tpdsMerchantCerId;
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