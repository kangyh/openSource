/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.adjust.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：调账明细Entity
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
public class AdjustAccountDetail extends DataEntity<AdjustAccountDetail> implements Comparable<AdjustAccountDetail> {
	
	private static final long serialVersionUID = 1L;
	private Long adjustDetailId;		// 调账明细Id
	private Long adjustId;		// 调账Id
	private String seNo;		// 套号
	private Long accountid;		// 账户Id
	private String balanceDirection;		// 余额方向  C：借 D：贷
	private String amount;		// 金额
	private String logIds;    //merchantLog主键
	private String remark;		// 备注
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String accountType;
	private String accountName;
	private String balanceAmount;
	private String direction;
	//说明-手工记账/正常记账
	private String explain;
	
	public AdjustAccountDetail() {
		super();
	}

	public AdjustAccountDetail(String id){
		super(id);
	}

	public Long getAdjustDetailId() {
		return adjustDetailId;
	}

	public void setAdjustDetailId(Long adjustDetailId) {
		this.adjustDetailId = adjustDetailId;
	}
	
	@NotNull(message="调账Id不能为空")
	public Long getAdjustId() {
		return adjustId;
	}

	public void setAdjustId(Long adjustId) {
		this.adjustId = adjustId;
	}
	
	@Length(min=1, max=3, message="套号长度必须介于 1 和 3 之间")
	public String getSeNo() {
		return seNo;
	}

	public void setSeNo(String seNo) {
		this.seNo = seNo;
	}
	
	@NotNull(message="账户Id不能为空")
	public Long getAccountid() {
		return accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}
	
	@Length(min=1, max=1, message="余额方向  C：借 D：贷长度必须介于 1 和 1 之间")
	public String getBalanceDirection() {
		return balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getLogIds() {
    return logIds;
  }

  public void setLogIds(String logIds) {
    this.logIds = logIds;
  }

  @Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getBalanceAmount() {
    return balanceAmount;
  }

  public void setBalanceAmount(String balanceAmount) {
    this.balanceAmount = balanceAmount;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public String getExplain() {
    return explain;
  }

  public void setExplain(String explain) {
    this.explain = explain;
  }

  /**
   * 
  * @description 按套号排序
  * @author 王亚洪       
  * @created 2017年1月17日 上午10:49:12      
  * @param o
  * @return     
  * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(AdjustAccountDetail o) {
    return Integer.parseInt(this.getSeNo()) - Integer.parseInt(o.getSeNo());
  }
	
  
  
  
}