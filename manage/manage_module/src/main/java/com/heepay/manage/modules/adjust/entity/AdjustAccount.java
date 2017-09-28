/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.adjust.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：调账Entity
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
public class AdjustAccount extends DataEntity<AdjustAccount> {
	
	private static final long serialVersionUID = 1L;
	private Long adjustId;		// 调账id
	private String serialNo;		// 流水号
	private String serialNum;		// 笔数
	private String transNo;    //交易号
	private String trialBalanceTransId; //试算平衡id
	private String status;		// 状态(待审核:AUDING,审核拒绝:AUDREJ,审核通过:ADOPT)
	private String type;      //是否为交易维度   N：否    Y：是
	private String accountMark; //记账标识
	private String creator;		// 创建人
	private String auditor;		// 审核人
	private String filePath;		// 附件地址
	private String reason;		// 调账原因
	private String auditRemark; //审核备注
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private Date beginCreateTime;    // 开始 创建时间
  private Date endCreateTime;   // 结束 创建时间
  private List<String> images;
  private String sortOrder; //排序
	
	public AdjustAccount() {
		super();
	}

	public AdjustAccount(String id){
		super(id);
	}

	public Long getAdjustId() {
		return adjustId;
	}

	public void setAdjustId(Long adjustId) {
		this.adjustId = adjustId;
	}
	
	@Length(min=1, max=26, message="流水号长度必须介于 1 和 26 之间")
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	@Length(min=1, max=3, message="笔数长度必须介于 1 和 3 之间")
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
	@Length(min=1, max=6, message="状态(待审核:AUDING,审核拒绝:AUDREJ,审核通过:ADOPT)长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
	}

	public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAccountMark() {
    return accountMark;
  }

  public void setAccountMark(String accountMark) {
    this.accountMark = accountMark;
  }

  public String getTransNo() {
    return transNo;
  }

  public void setTransNo(String transNo) {
    this.transNo = transNo;
  }

  public String getTrialBalanceTransId() {
    return trialBalanceTransId;
  }

  public void setTrialBalanceTransId(String trialBalanceTransId) {
    this.trialBalanceTransId = trialBalanceTransId;
  }

  public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=50, message="创建人长度必须介于 0 和 50 之间")
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
	
	@Length(min=0, max=255, message="附件地址长度必须介于 0 和 255 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@NotNull
	@Length(min=0, max=255, message="调账原因长度必须介于 0 和 255 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getAuditRemark() {
    return auditRemark;
  }

  public void setAuditRemark(String auditRemark) {
    this.auditRemark = auditRemark;
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

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }
	
}