package com.heepay.risk.vo;

import java.util.Date;

public class RiskLoginBlacklistVo {
	

    private Integer blackId;

    //公司名称
    private String companyName;
    //营业执照编号
    private String buziCode;
    //法定代表人
    private String ownerName;
    //法定代表人身份证号
    private String ownerId;

    private String createTime;

    public Integer getBlackId() {
        return blackId;
    }

    public void setBlackId(Integer blackId) {
        this.blackId = blackId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getBuziCode() {
        return buziCode;
    }

    public void setBuziCode(String buziCode) {
        this.buziCode = buziCode == null ? null : buziCode.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

   


}
