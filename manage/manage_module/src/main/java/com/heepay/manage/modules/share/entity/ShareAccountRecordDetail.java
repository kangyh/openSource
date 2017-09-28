/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.share.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：分润明细Entity
 *
 * 创 建 者： @author zc
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
public class ShareAccountRecordDetail extends DataEntity<ShareAccountRecordDetail> {
	
	private static final long serialVersionUID = 1L;
	private Long shareDetailId;		// 自增主键
	private String shareId;		// 分润表id
	private Long masterMerchantId;		// 分润商户id
	private Long slaverMerchantId;		// 收润商户id
	private String shareAmount;		// 分润金额
	private String status;		// 状态
	private Date createTime;		// 创建日期
	private Date updateTime;		// 修改日期
	private String operator;		// 操作人
	private String slaverMerchantLoginName;		// slaver_merchant_login_name
	private String slaverMerchantCompany;		// slaver_merchant_company
	private String masterMerchantLoginName;		// master_merchant_login_name
	private String masterMerchantCompany;		// master_merchant_company
	private String transNo;		// 要分润的订单号,gateway_record表id
	private Date transCreateTime;		// 原订单创建时间
	
	
  private Date beginCreateTime;   // 开始 创建时间
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

  private Date endCreateTime;   // 结束 创建时间
  
  
	public ShareAccountRecordDetail() {
		super();
	}

	public ShareAccountRecordDetail(String id){
		super(id);
	}

	public Long getShareDetailId() {
		return shareDetailId;
	}

	public void setShareDetailId(Long shareDetailId) {
		this.shareDetailId = shareDetailId;
	}
	
	@Length(min=0, max=18, message="分润表id长度必须介于 0 和 18 之间")
	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	
	public Long getMasterMerchantId() {
		return masterMerchantId;
	}

	public void setMasterMerchantId(Long masterMerchantId) {
		this.masterMerchantId = masterMerchantId;
	}
	
	public Long getSlaverMerchantId() {
		return slaverMerchantId;
	}

	public void setSlaverMerchantId(Long slaverMerchantId) {
		this.slaverMerchantId = slaverMerchantId;
	}
	
	public String getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(String shareAmount) {
		this.shareAmount = shareAmount;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	@Length(min=0, max=6, message="操作人长度必须介于 0 和 6 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=64, message="slaver_merchant_login_name长度必须介于 0 和 64 之间")
	public String getSlaverMerchantLoginName() {
		return slaverMerchantLoginName;
	}

	public void setSlaverMerchantLoginName(String slaverMerchantLoginName) {
		this.slaverMerchantLoginName = slaverMerchantLoginName;
	}
	
	@Length(min=0, max=64, message="slaver_merchant_company长度必须介于 0 和 64 之间")
	public String getSlaverMerchantCompany() {
		return slaverMerchantCompany;
	}

	public void setSlaverMerchantCompany(String slaverMerchantCompany) {
		this.slaverMerchantCompany = slaverMerchantCompany;
	}
	
	@Length(min=0, max=64, message="master_merchant_login_name长度必须介于 0 和 64 之间")
	public String getMasterMerchantLoginName() {
		return masterMerchantLoginName;
	}

	public void setMasterMerchantLoginName(String masterMerchantLoginName) {
		this.masterMerchantLoginName = masterMerchantLoginName;
	}
	
	@Length(min=0, max=64, message="master_merchant_company长度必须介于 0 和 64 之间")
	public String getMasterMerchantCompany() {
		return masterMerchantCompany;
	}

	public void setMasterMerchantCompany(String masterMerchantCompany) {
		this.masterMerchantCompany = masterMerchantCompany;
	}
	
	@Length(min=0, max=50, message="要分润的订单号,gateway_record表id长度必须介于 0 和 50 之间")
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTransCreateTime() {
		return transCreateTime;
	}

	public void setTransCreateTime(Date transCreateTime) {
		this.transCreateTime = transCreateTime;
	}
	
}