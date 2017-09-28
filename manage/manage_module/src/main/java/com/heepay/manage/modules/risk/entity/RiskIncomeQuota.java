/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述：商户出入金限额Entity
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
public class RiskIncomeQuota extends DataEntity<RiskIncomeQuota> {
	
	private static final long serialVersionUID = 1L;
	private Long quotaId;		// 主键
	private String merchantId;		// 商户编码
	private String dayIncomeQuotaAmount;		// 日出入金限额
	private String incomeDirection;		// 出入金类型
	private String createAuthor;		// 创建者
	private Date createTime;		// 创建时间
	private String updateAuthor;		// 更新者
	private Date updateTime;		// 更新时间
	private String status;		// 状态【Y:启用  N:禁用】
	
	public RiskIncomeQuota() {
		super();
	}

	public RiskIncomeQuota(String id){
		super(id);
	}

	@NotNull(message="主键不能为空")
	public Long getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(Long quotaId) {
		this.quotaId = quotaId;
	}
	
	@Length(min=0, max=10, message="商户编码长度必须介于 0 和 10 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getDayIncomeQuotaAmount() {
		return dayIncomeQuotaAmount;
	}

	public void setDayIncomeQuotaAmount(String dayIncomeQuotaAmount) {
		this.dayIncomeQuotaAmount = dayIncomeQuotaAmount;
	}
	
	@Length(min=0, max=6, message="出入金类型长度必须介于 0 和 6 之间")
	public String getIncomeDirection() {
		return incomeDirection;
	}

	public void setIncomeDirection(String incomeDirection) {
		this.incomeDirection = incomeDirection;
	}
	
	@Length(min=0, max=20, message="创建者长度必须介于 0 和 20 之间")
	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=20, message="更新者长度必须介于 0 和 20 之间")
	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=6, message="状态【Y:启用  N:禁用】长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}