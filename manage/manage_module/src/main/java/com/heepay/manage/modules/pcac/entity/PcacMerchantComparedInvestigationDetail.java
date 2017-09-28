package com.heepay.manage.modules.pcac.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PcacMerchantComparedInvestigationDetail extends DataEntity<PcacMerchantComparedInvestigationDetail>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long detailId;

    private String batchNo;

    private String code;

    private String name;

    private String legalName;

    private Date createTime;

    private Long investigationId;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
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

    public Long getInvestigationId() {
        return investigationId;
    }

    public void setInvestigationId(Long investigationId) {
        this.investigationId = investigationId;
    }
}