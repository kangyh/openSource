package com.heepay.manage.modules.pcac.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PcacMerchantComparedInvestigation extends DataEntity<PcacMerchantComparedInvestigation>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long investigationId;

    private String batchNo;

    private String code;

    private String name;

    private String legalName;

    private Date createTime;
    
    //非映射字段
    private String merchantCode;
    private String merchantName;
    private String merchantLegalName;
    private Date beginCheckTime;
    private Date endCheckTime;

    public Long getInvestigationId() {
        return investigationId;
    }

    public void setInvestigationId(Long investigationId) {
        this.investigationId = investigationId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName == null ? null : legalName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantLegalName() {
		return merchantLegalName;
	}

	public void setMerchantLegalName(String merchantLegalName) {
		this.merchantLegalName = merchantLegalName;
	}

	public Date getBeginCheckTime() {
		return beginCheckTime;
	}

	public void setBeginCheckTime(Date beginCheckTime) {
		this.beginCheckTime = beginCheckTime;
	}

	public Date getEndCheckTime() {
		return endCheckTime;
	}

	public void setEndCheckTime(Date endCheckTime) {
		this.endCheckTime = endCheckTime;
	}
}