/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.subject.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：科目Entity
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
public class Subject extends DataEntity<Subject> {
	
	private static final long serialVersionUID = 1L;
	private Long subjectId;		// 科目Id
	private String subjectCode;		// 科目号
	private String subjectName;		// 科目名称
	private Long parentSubjectId;		// 父节点
	private String subjectLevel;		// 科目级别
	private String isLast;		// 是否为最底层(Y-是  N-否)
	private String type;		// 类别(资产类-ASSET  负债类-DEBT  共同类-COMMON)
	private String balanceDirection;		// 余额方向(C-借 D-贷)
	private String status;		// 状态(正常-NORMAL  关闭-CLOSED)
	private String creator;		// 创建人
	private String modifier;		// 修改人
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String remark;		// 备注
	private String levelName;
	private String directionName;
	private String statusName;
	private String typeName;
	private String isLastName;
	private String parentSubjectName;
	
	public Subject() {
		super();
	}

	public Subject(String id){
		super(id);
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	
	@Length(min=1, max=8, message="科目号长度必须介于 1 和 8 之间")
	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	@Length(min=1, max=100, message="科目名称长度必须介于 1 和 100 之间")
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public Long getParentSubjectId() {
		return parentSubjectId;
	}

	public void setParentSubjectId(Long parentSubjectId) {
		this.parentSubjectId = parentSubjectId;
	}
	
	@Length(min=1, max=1, message="科目级别长度必须介于 1 和 1 之间")
	public String getSubjectLevel() {
		return subjectLevel;
	}

	public void setSubjectLevel(String subjectLevel) {
		this.subjectLevel = subjectLevel;
	}
	
	@Length(min=1, max=1, message="是否为最底层(Y-是  N-否)长度必须介于 1 和 1 之间")
	public String getIsLast() {
		return isLast;
	}

	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}
	
	@Length(min=1, max=6, message="类别(资产类-ASSET  负债类-DEBT  共同类-COMMON)长度必须介于 1 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=1, message="余额方向(C-借 D-贷)长度必须介于 1 和 1 之间")
	public String getBalanceDirection() {
		return balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}
	
	@Length(min=1, max=6, message="状态(正常-NORMAL  关闭-CLOSED)长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
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
	
	@Length(min=0, max=50, message="修改人长度必须介于 0 和 50 之间")
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
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
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public String getDirectionName() {
    return directionName;
  }

  public void setDirectionName(String directionName) {
    this.directionName = directionName;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getIsLastName() {
    return isLastName;
  }

  public void setIsLastName(String isLastName) {
    this.isLastName = isLastName;
  }

  public String getParentSubjectName() {
    return parentSubjectName;
  }

  public void setParentSubjectName(String parentSubjectName) {
    this.parentSubjectName = parentSubjectName;
  }
	
	
	
}