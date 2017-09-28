/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：内部账户映射Entity
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
public class InternalAccountMpr extends DataEntity<InternalAccountMpr> {
	
	private static final long serialVersionUID = 1L;
	private String internalAccountSerialNumber;		// 内部账号序列号
	private String internalAccountName;		// 内部账户名称
	private String internalAccountTypeId;		// 内部账户类型   待结算(收入,付款)、手续费(收入,付款)
	private String internalAccountDetailsTypeId;		// 内部账户详细类型id (建设银行 773)
	private String internalAccountBalanceDirection;		// 余额方向 (0:借方 1:贷方)
	private Date createTime;		// 内部账户创建时间
	private Date updateTime;		// 内部账户更新时间
	private String description;		// 描述
	private String remark;		// 备注
	
	public InternalAccountMpr() {
		super();
	}

	public InternalAccountMpr(String id){
		super(id);
	}

	@Length(min=1, max=20, message="内部账号序列号长度必须介于 1 和 20 之间")
	public String getInternalAccountSerialNumber() {
		return internalAccountSerialNumber;
	}

	public void setInternalAccountSerialNumber(String internalAccountSerialNumber) {
		this.internalAccountSerialNumber = internalAccountSerialNumber;
	}
	
	@Length(min=0, max=256, message="内部账户名称长度必须介于 0 和 256 之间")
	public String getInternalAccountName() {
		return internalAccountName;
	}

	public void setInternalAccountName(String internalAccountName) {
		this.internalAccountName = internalAccountName;
	}
	
	@Length(min=0, max=10, message="内部账户类型   待结算(收入,付款)、手续费(收入,付款)长度必须介于 0 和 10 之间")
	public String getInternalAccountTypeId() {
		return internalAccountTypeId;
	}

	public void setInternalAccountTypeId(String internalAccountTypeId) {
		this.internalAccountTypeId = internalAccountTypeId;
	}
	
	@Length(min=0, max=10, message="内部账户详细类型id (建设银行 773)长度必须介于 0 和 10 之间")
	public String getInternalAccountDetailsTypeId() {
		return internalAccountDetailsTypeId;
	}

	public void setInternalAccountDetailsTypeId(String internalAccountDetailsTypeId) {
		this.internalAccountDetailsTypeId = internalAccountDetailsTypeId;
	}
	
	@Length(min=0, max=6, message="余额方向 (0:借方 1:贷方)长度必须介于 0 和 6 之间")
	public String getInternalAccountBalanceDirection() {
		return internalAccountBalanceDirection;
	}

	public void setInternalAccountBalanceDirection(String internalAccountBalanceDirection) {
		this.internalAccountBalanceDirection = internalAccountBalanceDirection;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="内部账户创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="内部账户更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}