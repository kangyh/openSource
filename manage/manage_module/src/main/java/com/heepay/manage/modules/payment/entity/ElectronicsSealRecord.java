/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述：电子签章订单Entity
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
public class ElectronicsSealRecord extends DataEntity<ElectronicsSealRecord> {
	
	private static final long serialVersionUID = 1L;
	private String electronicsSealId;		// 签章序列号
	private Long merchantId;		// 商户id
	private String merchantCompany;		// 商户公司
	private String feeAmount;		// 手续费金额
	private String sysResource;		// 平台来源
	private String sealProduct;		// 签约产品
	private String channelCode;		// 通道编码
	private Integer sealType;		// 签约类型
	private String status;		// 状态
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String remark;		// 备注

	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String statisticsDate;
	private String groupType;

	public ElectronicsSealRecord() {
		super();
	}

	public ElectronicsSealRecord(String id){
		super(id);
	}

	@Length(min=0, max=20, message="签章序列号长度必须介于 0 和 20 之间")
	public String getElectronicsSealId() {
		return electronicsSealId;
	}

	public void setElectronicsSealId(String electronicsSealId) {
		this.electronicsSealId = electronicsSealId;
	}
	
	@NotNull(message="商户id不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=100, message="商户公司长度必须介于 0 和 100 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
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
	
	@Length(min=1, max=64, message="通道编码长度必须介于 1 和 64 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@NotNull(message="签约类型不能为空")
	public Integer getSealType() {
		return sealType;
	}

	public void setSealType(Integer sealType) {
		this.sealType = sealType;
	}
	
	@Length(min=1, max=6, message="状态长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
}