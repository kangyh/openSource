/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.contract.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述：已签约合同Entity
 *
 * 创 建 者： @author zjx
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
public class SealContractRecord extends DataEntity<SealContractRecord> {
	
	private static final long serialVersionUID = 1L;
	private String sealContractId;		// 签约合同编码
	private String sealContractName;		// 签约合同名称
	private String electronicsSealId;		// 签章序列号
	private Long sealAsideId;		// 签约甲方id
	private String sealAsideName;		// 签约甲方
	private String sealBsideName;		// 签约乙方
	private String sysResource;		// 平台来源
	private String sealProduct;		// 签约产品
	private String contractEffectiveTime;		// 合同有效期
	private String contractFileName;		// 合同文件名
	private String contractUrl;		// 合同URL
	private Date sealTime;		// 签约时间
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String remark;		// 备注

	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间

	public SealContractRecord() {
		super();
	}

	public SealContractRecord(String id){
		super(id);
	}

	@Length(min=0, max=20, message="签约合同编码长度必须介于 0 和 20 之间")
	public String getSealContractId() {
		return sealContractId;
	}

	public void setSealContractId(String sealContractId) {
		this.sealContractId = sealContractId;
	}
	
	@Length(min=0, max=64, message="签约合同名称长度必须介于 0 和 64 之间")
	public String getSealContractName() {
		return sealContractName;
	}

	public void setSealContractName(String sealContractName) {
		this.sealContractName = sealContractName;
	}
	
	@Length(min=0, max=20, message="签章序列号长度必须介于 0 和 20 之间")
	public String getElectronicsSealId() {
		return electronicsSealId;
	}

	public void setElectronicsSealId(String electronicsSealId) {
		this.electronicsSealId = electronicsSealId;
	}
	
	@NotNull(message="签约甲方id不能为空")
	public Long getSealAsideId() {
		return sealAsideId;
	}

	public void setSealAsideId(Long sealAsideId) {
		this.sealAsideId = sealAsideId;
	}
	
	@Length(min=0, max=100, message="签约甲方长度必须介于 0 和 100 之间")
	public String getSealAsideName() {
		return sealAsideName;
	}

	public void setSealAsideName(String sealAsideName) {
		this.sealAsideName = sealAsideName;
	}
	
	@Length(min=0, max=100, message="签约乙方长度必须介于 0 和 100 之间")
	public String getSealBsideName() {
		return sealBsideName;
	}

	public void setSealBsideName(String sealBsideName) {
		this.sealBsideName = sealBsideName;
	}
	
	@Length(min=0, max=32, message="平台来源长度必须介于 0 和 32 之间")
	public String getSysResource() {
		return sysResource;
	}

	public void setSysResource(String sysResource) {
		this.sysResource = sysResource;
	}
	
	@Length(min=0, max=32, message="签约产品长度必须介于 0 和 32 之间")
	public String getSealProduct() {
		return sealProduct;
	}

	public void setSealProduct(String sealProduct) {
		this.sealProduct = sealProduct;
	}
	
	@Length(min=0, max=32, message="合同有效期长度必须介于 0 和 32 之间")
	public String getContractEffectiveTime() {
		return contractEffectiveTime;
	}

	public void setContractEffectiveTime(String contractEffectiveTime) {
		this.contractEffectiveTime = contractEffectiveTime;
	}
	
	@Length(min=0, max=100, message="合同文件名长度必须介于 0 和 100 之间")
	public String getContractFileName() {
		return contractFileName;
	}

	public void setContractFileName(String contractFileName) {
		this.contractFileName = contractFileName;
	}
	
	@Length(min=0, max=100, message="合同URL长度必须介于 0 和 100 之间")
	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="签约时间不能为空")
	public Date getSealTime() {
		return sealTime;
	}

	public void setSealTime(Date sealTime) {
		this.sealTime = sealTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}