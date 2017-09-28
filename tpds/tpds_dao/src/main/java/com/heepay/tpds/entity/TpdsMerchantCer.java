package com.heepay.tpds.entity;

import java.util.Date;

public class TpdsMerchantCer {
    private Integer tpdsMerchantCerId;

    /**
     * 商户编号MERCHANT_NO
     */
    private String merchantNo;

    /**
     * 公钥
     */
    private String pubKey;

    /**
     * 私钥
     */
    private String priKey;

    /**
     * 证书存储路径
     */
    private String cerPath;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 备注
     */
    private String note;


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
}