/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述：业务服务项Entity
 *
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-10 15:57:40
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
public class HgmsServiceItem extends DataEntity<HgmsServiceItem> {
	
	private static final long serialVersionUID = 1L;
	private Long serviceId;		// 服务项ID
	private String serviceName;		// 服务项名称
	private String merchantId;		// 商户编号  与user表id字段为一个值
	private String businessId;		// 业务ID
	private String businessName;		// 业务名称
	private String productCode;		// 产品编码
	private String productName;		// 产品名称
	private Long ruleId;		// 规则ID
	private String ruleBuildType;		// 规则建立方式
	private String inputuserId;		// 录入人ID
    private String inputuserName;		// 录入人名称
    private Date createdTime;		// 录入时间
    private Date beginCreateTime;   // 开始 创建时间
    private Date endCreateTime;   // 结束 创建时间
    private String status;		// 状态
    private String businessStatus;		// 业务状态
	private String extend3;		// extend3
	private String extend4;		// extend4
	private String extend5;		// extend5
	
	public HgmsServiceItem() {
		super();
	}

	public HgmsServiceItem(String id){
		super(id);
	}

	@NotNull(message="服务项ID不能为空")
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	
	@Length(min=0, max=64, message="服务项名称长度必须介于 0 和 64 之间")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Length(min=0, max=64, message="商户编号  与user表id字段为一个值长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="业务ID长度必须介于 0 和 64 之间")
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
	@Length(min=0, max=64, message="业务名称长度必须介于 0 和 64 之间")
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	@Length(min=0, max=64, message="产品编码长度必须介于 0 和 64 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=256, message="产品名称长度必须介于 0 和 256 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	@Length(min=0, max=6, message="规则建立方式长度必须介于 0 和 6 之间")
	public String getRuleBuildType() {
		return ruleBuildType;
	}

	public void setRuleBuildType(String ruleBuildType) {
		this.ruleBuildType = ruleBuildType;
	}
	
	@Length(min=0, max=6, message="录入人长度必须介于 0 和 6 之间")
	public String getInputuserId() {
		return inputuserId;
	}

	public void setInputuserId(String inputuserId) {
		this.inputuserId = inputuserId;
	}

    public String getInputuserName() {
        return inputuserName;
    }

    public void setInputuserName(String inputuserName) {
        this.inputuserName = inputuserName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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

    @Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}
	
	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String extend4) {
		this.extend4 = extend4;
	}
	
	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String extend5) {
		this.extend5 = extend5;
	}

	@Override
	public String toString() {
		return "HgmsServiceItem{" +
				"serviceId=" + serviceId +
				", serviceName='" + serviceName + '\'' +
				", merchantId='" + merchantId + '\'' +
				", businessId='" + businessId + '\'' +
				", businessName='" + businessName + '\'' +
				", productCode='" + productCode + '\'' +
				", productName='" + productName + '\'' +
				", ruleId=" + ruleId +
				", ruleBuildType='" + ruleBuildType + '\'' +
				", inputuserId='" + inputuserId + '\'' +
				", inputuserName='" + inputuserName + '\'' +
				", createdTime=" + createdTime +
				", beginCreateTime=" + beginCreateTime +
				", endCreateTime=" + endCreateTime +
				", status='" + status + '\'' +
				", businessStatus='" + businessStatus + '\'' +
				", extend3='" + extend3 + '\'' +
				", extend4='" + extend4 + '\'' +
				", extend5='" + extend5 + '\'' +
				'}';
	}
}