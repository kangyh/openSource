/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：试算平衡-交易维度Entity
 *
 * 创 建 者： @author 杨春龙
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
public class TrialBalanceTrans extends DataEntity<TrialBalanceTrans> {
	
	private static final long serialVersionUID = 1L;
	private String transNo;		// 交易号
	private String debitCurrentAmount;		// 发生额(借方)
	private String creditCurrentAmount;		// 发生额(贷方)
	private String accountDate;		// 会计日期
	private String type;		// 交易类型
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String status;          //处理状态  待处理: PREHAN 已处理 FINISH  add：处理中 INHAND
	//非数据库字段
	private String diffAmount;  //差额
	private String logIds;
	
	
	public TrialBalanceTrans() {
		super();
	}

	public TrialBalanceTrans(String id){
		super(id);
	}

	@Length(min=1, max=26, message="交易号长度必须介于 1 和 26 之间")
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	public String getDebitCurrentAmount() {
		return debitCurrentAmount;
	}

	public void setDebitCurrentAmount(String debitCurrentAmount) {
		this.debitCurrentAmount = debitCurrentAmount;
	}
	
	public String getCreditCurrentAmount() {
		return creditCurrentAmount;
	}

	public void setCreditCurrentAmount(String creditCurrentAmount) {
		this.creditCurrentAmount = creditCurrentAmount;
	}
	
	@Length(min=0, max=10, message="会计日期长度必须介于 0 和 10 之间")
	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

  public String getDiffAmount() {
    return diffAmount;
  }

  public void setDiffAmount(String diffAmount) {
    this.diffAmount = diffAmount;
  }

  public String getLogIds() {
    return logIds;
  }

  public void setLogIds(String logIds) {
    this.logIds = logIds;
  }
	
	
	
}