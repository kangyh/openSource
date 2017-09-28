/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：出入金配置管理Entity
 *
 * 创 建 者： @author wangdong
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
public class SettleIncomeInfo extends DataEntity<SettleIncomeInfo> {
	
	private static final long serialVersionUID = 1L;
	private Long incomeId;		// 主键
	private String incomeDirection;		// in,ount
	private String businessType;		// 业务类型
	private String transType;		// 交易类型
	private String productCode;		// 产品编码
	private String settleStatus;		// 是否结算
	private Date createTime;		// 创建时间
	private String creator;		// 创建者
	private Date updateTime;		// 更新时间
	private String modifier;		// 修改人
	private String remark;		// 备注
	private String status;		// 状态
	
	public SettleIncomeInfo() {
		super();
	}

	public SettleIncomeInfo(String id){
		super(id);
	}

	@NotNull(message="主键不能为空")
	public Long getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Long incomeId) {
		this.incomeId = incomeId;
	}
	
	@Length(min=0, max=12, message="in,ount长度必须介于 0 和 12 之间")
	public String getIncomeDirection() {
		return incomeDirection;
	}

	public void setIncomeDirection(String incomeDirection) {
		this.incomeDirection = incomeDirection;
	}
	
	@Length(min=0, max=6, message="业务类型长度必须介于 0 和 6 之间")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Length(min=0, max=6, message="产品编码长度必须介于 0 和 6 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=6, message="是否结算长度必须介于 0 和 6 之间")
	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=8, message="创建者长度必须介于 0 和 8 之间")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=8, message="修改人长度必须介于 0 和 8 之间")
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	@Length(min=0, max=50, message="备注长度必须介于 0 和 50 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}