package com.heepay.manage.modules.pcac.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PcacPersonReport extends DataEntity<PcacPersonReport> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long personReportId;

    private String batchNo;

    private String cusType;

    private String cusNature;

    private String regName;

    private String cusName;

    private String cusNameEn;

    private String docType;

    private String docCode;

    private String cusCode;

    private String induType;

    private String bankNo;

    private String openBank;

    private String regAddrProv;

    private String regAddrDetail;

    private String addrProv;

    private String addrDetail;

    private String url;

    private String serverIp;

    private String icp;

    private String contName;

    private String contPhone;

    private String cusEmail;

    private String occurarea;

    private String networkType;

    private String status;

    private String startTime;

    private String endTime;

    private String riskStatus;

    private String openType;

    private String chageType;

    private String accountType;

    private String expandType;

    private String outServiceName;

    private String outServiceCardType;

    private String outServiceCardCode;

    private String outServicelegCardType;

    private String outServicelegCardCode;

    private String orgId;

    private Date repDate;

    private String repType;

    private String repPerson;

    private String uploader;

    private String reviewer;

    private Date uploadTime;

    private Date reviewTime;
    
    private String opStatus;
    
    private String opStatusNote;
    
    private Date fillerTime ;
    
    private String filler;
    /**
     * 非映射字段（数据库中没有对应的字段，用于查询）
     */
	private Date  beginRepDate;//上报开始日期
	private Date  endRepDate;//上报结束日期
	private Date  beginReviewTime;//审核开始日期
	private Date  endReviewTime;//审核结束日期
	private Date  beginUploadTime;//上传开始日期upload_time
	private Date  endUploadTime;//上传结束日期
	private Date beginFillerTime;//填写Excel开始时间
	private Date endFillerTime;//填写Excel结束时间
    private String repNum;//统计字段
    private String[] reportIds;

    public String[] getReportIds() {
		return reportIds;
	}

	public void setReportIds(String[] reportIds) {
		this.reportIds = reportIds;
	}

	public String getOpStatusNote() {
		return opStatusNote;
	}

	public void setOpStatusNote(String opStatusNote) {
		this.opStatusNote = opStatusNote;
	}

	public Date getFillerTime() {
		return fillerTime;
	}

	public void setFillerTime(Date fillerTime) {
		this.fillerTime = fillerTime;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getRepNum() {
		return repNum;
	}

	public void setRepNum(String repNum) {
		this.repNum = repNum;
	}

	public String getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(String opStatus) {
		this.opStatus = opStatus;
	}

	public Date getBeginRepDate() {
		return beginRepDate;
	}

	public void setBeginRepDate(Date beginRepDate) {
		this.beginRepDate = beginRepDate;
	}

	public Date getEndRepDate() {
		return endRepDate;
	}

	public void setEndRepDate(Date endRepDate) {
		this.endRepDate = endRepDate;
	}

	public Date getBeginReviewTime() {
		return beginReviewTime;
	}

	public void setBeginReviewTime(Date beginReviewTime) {
		this.beginReviewTime = beginReviewTime;
	}

	public Date getEndReviewTime() {
		return endReviewTime;
	}

	public void setEndReviewTime(Date endReviewTime) {
		this.endReviewTime = endReviewTime;
	}

	

	public Date getBeginUploadTime() {
		return beginUploadTime;
	}

	public void setBeginUploadTime(Date beginUploadTime) {
		this.beginUploadTime = beginUploadTime;
	}

	public Date getEndUploadTime() {
		return endUploadTime;
	}

	public void setEndUploadTime(Date endUploadTime) {
		this.endUploadTime = endUploadTime;
	}

	public Date getBeginFillerTime() {
		return beginFillerTime;
	}

	public void setBeginFillerTime(Date beginFillerTime) {
		this.beginFillerTime = beginFillerTime;
	}

	public Date getEndFillerTime() {
		return endFillerTime;
	}

	public void setEndFillerTime(Date endFillerTime) {
		this.endFillerTime = endFillerTime;
	}

	public Long getPersonReportId() {
        return personReportId;
    }

    public void setPersonReportId(Long personReportId) {
        this.personReportId = personReportId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getCusType() {
        return cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType == null ? null : cusType.trim();
    }

    public String getCusNature() {
        return cusNature;
    }

    public void setCusNature(String cusNature) {
        this.cusNature = cusNature == null ? null : cusNature.trim();
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName == null ? null : regName.trim();
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName == null ? null : cusName.trim();
    }

    public String getCusNameEn() {
        return cusNameEn;
    }

    public void setCusNameEn(String cusNameEn) {
        this.cusNameEn = cusNameEn == null ? null : cusNameEn.trim();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode == null ? null : docCode.trim();
    }

    public String getCusCode() {
        return cusCode;
    }

    public void setCusCode(String cusCode) {
        this.cusCode = cusCode == null ? null : cusCode.trim();
    }

    public String getInduType() {
        return induType;
    }

    public void setInduType(String induType) {
        this.induType = induType == null ? null : induType.trim();
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo == null ? null : bankNo.trim();
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank == null ? null : openBank.trim();
    }

    public String getRegAddrProv() {
        return regAddrProv;
    }

    public void setRegAddrProv(String regAddrProv) {
        this.regAddrProv = regAddrProv == null ? null : regAddrProv.trim();
    }

    public String getRegAddrDetail() {
        return regAddrDetail;
    }

    public void setRegAddrDetail(String regAddrDetail) {
        this.regAddrDetail = regAddrDetail == null ? null : regAddrDetail.trim();
    }

    public String getAddrProv() {
        return addrProv;
    }

    public void setAddrProv(String addrProv) {
        this.addrProv = addrProv == null ? null : addrProv.trim();
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail == null ? null : addrDetail.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public String getIcp() {
        return icp;
    }

    public void setIcp(String icp) {
        this.icp = icp == null ? null : icp.trim();
    }

    public String getContName() {
        return contName;
    }

    public void setContName(String contName) {
        this.contName = contName == null ? null : contName.trim();
    }

    public String getContPhone() {
        return contPhone;
    }

    public void setContPhone(String contPhone) {
        this.contPhone = contPhone == null ? null : contPhone.trim();
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail == null ? null : cusEmail.trim();
    }

    public String getOccurarea() {
        return occurarea;
    }

    public void setOccurarea(String occurarea) {
        this.occurarea = occurarea == null ? null : occurarea.trim();
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType == null ? null : networkType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    

    public String getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}

	public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType == null ? null : openType.trim();
    }
	public String getChageType() {
		return chageType;
	}

	public void setChageType(String chageType) {
		this.chageType = chageType;
	}

	public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getExpandType() {
        return expandType;
    }

    public void setExpandType(String expandType) {
        this.expandType = expandType == null ? null : expandType.trim();
    }

    public String getOutServiceName() {
        return outServiceName;
    }

    public void setOutServiceName(String outServiceName) {
        this.outServiceName = outServiceName == null ? null : outServiceName.trim();
    }

    public String getOutServiceCardType() {
        return outServiceCardType;
    }

    public void setOutServiceCardType(String outServiceCardType) {
        this.outServiceCardType = outServiceCardType == null ? null : outServiceCardType.trim();
    }

    public String getOutServiceCardCode() {
        return outServiceCardCode;
    }

    public void setOutServiceCardCode(String outServiceCardCode) {
        this.outServiceCardCode = outServiceCardCode == null ? null : outServiceCardCode.trim();
    }

    public String getOutServicelegCardType() {
        return outServicelegCardType;
    }

    public void setOutServicelegCardType(String outServicelegCardType) {
        this.outServicelegCardType = outServicelegCardType == null ? null : outServicelegCardType.trim();
    }

    public String getOutServicelegCardCode() {
        return outServicelegCardCode;
    }

    public void setOutServicelegCardCode(String outServicelegCardCode) {
        this.outServicelegCardCode = outServicelegCardCode == null ? null : outServicelegCardCode.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public Date getRepDate() {
        return repDate;
    }

    public void setRepDate(Date repDate) {
        this.repDate = repDate;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType == null ? null : repType.trim();
    }

    public String getRepPerson() {
        return repPerson;
    }

    public void setRepPerson(String repPerson) {
        this.repPerson = repPerson == null ? null : repPerson.trim();
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader == null ? null : uploader.trim();
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer == null ? null : reviewer.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }
}