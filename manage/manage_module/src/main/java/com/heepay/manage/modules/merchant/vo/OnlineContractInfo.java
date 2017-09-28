/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 *
 * 描    述：线上签约Entity
 *
 * 创 建 者： @author ly
 * 创建时间：
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
public class OnlineContractInfo extends DataEntity<OnlineContractInfo> {

    private static final long serialVersionUID = 1L;
    private String userId;        // 用户ID
    private String normProductCode;        // 标准产品编码
    private String url;        // 网址
    private String notifyUrl;        // 异步通知地址
    private String backUrl;        // 同步返回地址
    private String ipDomain;        // ip/域名
    private String file1;        // 应用1
    private String file2;        // 应用2
    private String file3;        // 应用3
    private String file4;        // 应用4
    private String file5;        // 应用5
    private String objection;        // 拒绝理由
    private String status;        // 状态
    private Date createTime;        // create_time
    private Date passTime;        // 审核通过时间
    private Date successTime;        // 验证成功时间
    private Date contractTime;        // 签约时间
    private Date rejectTime;        // 审核拒绝时间
    private String otherInformation;        // 其他资料
    private String times;
    private String merchantCompanyName; //商户公司名
    private String businessType; // 业务类型
    private String productName; //产品名称
    private String rcAuditStatus;   // 风控审核状态
    private Date rcAuditTime;   // 风控审核时间
    private String rcAuditor;   // 风控审核人
    private String legalAuditStatus;    // 法务审核状态
    private Date legalAuditTime;    // 法务审核时间
    private String legalAuditor;    // 风控审核人
    private String originalFilePath; //生成的合同存储地址
    private String middleFilePath;  //盖了商户章的文件存储地址
    private String finalFilePath;   //有效的合同文件地址

	private Date beginCreateTime;
	private Date endCreateTime;

    private String batchNo;     //申请批次号

    private Integer count; //产品数量

    public OnlineContractInfo() {
        super();
    }

    public OnlineContractInfo(String id) {
        super(id);
    }

    public String getUserId() {
        return userId;
    }

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=6, message="标准产品编码长度必须介于 0 和 6 之间")
	public String getNormProductCode() {
		return normProductCode;
	}

    public void setNormProductCode(String normProductCode) {
        this.normProductCode = normProductCode;
    }

    @Length(min = 0, max = 512, message = "网址长度必须介于 0 和 512 之间")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Length(min = 0, max = 512, message = "异步通知地址长度必须介于 0 和 512 之间")
    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Length(min = 0, max = 512, message = "同步返回地址长度必须介于 0 和 512 之间")
    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    @Length(min = 0, max = 512, message = "ip/域名长度必须介于 0 和 512 之间")
    public String getIpDomain() {
        return ipDomain;
    }

    public void setIpDomain(String ipDomain) {
        this.ipDomain = ipDomain;
    }

    @Length(min = 0, max = 512, message = "应用1长度必须介于 0 和 512 之间")
    public String getFile1() {
        return file1;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    @Length(min = 0, max = 512, message = "应用2长度必须介于 0 和 512 之间")
    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    @Length(min = 0, max = 512, message = "应用3长度必须介于 0 和 512 之间")
    public String getFile3() {
        return file3;
    }

    public void setFile3(String file3) {
        this.file3 = file3;
    }

    @Length(min = 0, max = 512, message = "应用4长度必须介于 0 和 512 之间")
    public String getFile4() {
        return file4;
    }

    public void setFile4(String file4) {
        this.file4 = file4;
    }

    @Length(min = 0, max = 512, message = "应用5长度必须介于 0 和 512 之间")
    public String getFile5() {
        return file5;
    }

    public void setFile5(String file5) {
        this.file5 = file5;
    }

    @Length(min = 0, max = 512, message = "拒绝理由长度必须介于 0 和 512 之间")
    public String getObjection() {
        return objection;
    }

    public void setObjection(String objection) {
        this.objection = objection;
    }

    @Length(min = 0, max = 6, message = "状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getContractTime() {
        return contractTime;
    }

    public void setContractTime(Date contractTime) {
        this.contractTime = contractTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    @Length(min = 0, max = 512, message = "其他资料长度必须介于 0 和 512 之间")
    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getMerchantCompanyName() {
        return merchantCompanyName;
    }

    public void setMerchantCompanyName(String merchantCompanyName) {
        this.merchantCompanyName = merchantCompanyName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRcAuditStatus() {
        return rcAuditStatus;
    }

    public void setRcAuditStatus(String rcAuditStatus) {
        this.rcAuditStatus = rcAuditStatus;
    }

    public Date getRcAuditTime() {
        return rcAuditTime;
    }

    public void setRcAuditTime(Date rcAuditTime) {
        this.rcAuditTime = rcAuditTime;
    }

    public String getRcAuditor() {
        return rcAuditor;
    }

    public void setRcAuditor(String rcAuditor) {
        this.rcAuditor = rcAuditor;
    }

    public String getLegalAuditStatus() {
        return legalAuditStatus;
    }

    public void setLegalAuditStatus(String legalAuditStatus) {
        this.legalAuditStatus = legalAuditStatus;
    }

    public Date getLegalAuditTime() {
        return legalAuditTime;
    }

    public void setLegalAuditTime(Date legalAuditTime) {
        this.legalAuditTime = legalAuditTime;
    }

    public String getLegalAuditor() {
        return legalAuditor;
    }

    public void setLegalAuditor(String legalAuditor) {
        this.legalAuditor = legalAuditor;
    }

    public String getOriginalFilePath() {
        return originalFilePath;
    }

    public void setOriginalFilePath(String originalFilePath) {
        this.originalFilePath = originalFilePath;
    }

    public String getMiddleFilePath() {
        return middleFilePath;
    }

    public void setMiddleFilePath(String middleFilePath) {
        this.middleFilePath = middleFilePath;
    }

    public String getFinalFilePath() {
        return finalFilePath;
    }

    public void setFinalFilePath(String finalFilePath) {
        this.finalFilePath = finalFilePath;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}