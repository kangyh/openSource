/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.allocate.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：备付金调拨Entity
 *
 * 创 建 者： @author 王亚洪
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
public class AllocateRecord extends DataEntity<AllocateRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long allocateId;		// 调拨id
	private String transNo;    //流水号
	private Long reserveOutAccountId;		// 出账的备付金账户
	private Long reserveInAccountId;		// 入账的备付金账户
	private Long merchantId;		// 商户id
	private String amount;		// 调拨金额
	private String reason;		// 原因
	private String status;		// 审核状态(待审核:AUDING,审核拒绝:AUDREJ,审核通过:ADOPT)
	private String creator;		// 操作人
	private String auditor;		// 审核人
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String remark;		// 备注
	
	private String reserveOutAccountName; //出账的备付金账户名称
	private String reserveInAccountName; //入账的备付金账户名称
	private String merchantName; //商户名
	private String sortOrder; //排序
	
	public AllocateRecord() {
		super();
	}

	public AllocateRecord(String id){
		super(id);
	}

	public Long getAllocateId() {
		return allocateId;
	}

	public void setAllocateId(Long allocateId) {
		this.allocateId = allocateId;
	}
	
	public String getTransNo() {
    return transNo;
  }

  public void setTransNo(String transNo) {
    this.transNo = transNo;
  }

  @NotNull(message="出账的备付金账户不能为空")
	public Long getReserveOutAccountId() {
		return reserveOutAccountId;
	}

	public void setReserveOutAccountId(Long reserveOutAccountId) {
		this.reserveOutAccountId = reserveOutAccountId;
	}
	
	@NotNull(message="入账的备付金账户不能为空")
	public Long getReserveInAccountId() {
		return reserveInAccountId;
	}

	public void setReserveInAccountId(Long reserveInAccountId) {
		this.reserveInAccountId = reserveInAccountId;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=1, max=255, message="原因长度必须介于 1 和 255 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=6, message="审核状态(待审核:AUDING,审核拒绝:AUDREJ,审核通过:ADOPT)长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=50, message="操作人长度必须介于 0 和 50 之间")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Length(min=0, max=50, message="审核人长度必须介于 0 和 50 之间")
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

  public String getReserveOutAccountName() {
    return reserveOutAccountName;
  }

  public void setReserveOutAccountName(String reserveOutAccountName) {
    this.reserveOutAccountName = reserveOutAccountName;
  }

  public String getReserveInAccountName() {
    return reserveInAccountName;
  }

  public void setReserveInAccountName(String reserveInAccountName) {
    this.reserveInAccountName = reserveInAccountName;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }
	
	
}