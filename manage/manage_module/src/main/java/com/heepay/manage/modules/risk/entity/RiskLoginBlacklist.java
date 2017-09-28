/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：risk_login_blacklistEntity
 *
 * 创 建 者： @author wj
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
public class RiskLoginBlacklist extends DataEntity<RiskLoginBlacklist> {
	
	private static final long serialVersionUID = 1L;
	private String blackId;		// 自增编号
	private String companyName;		// 公司名称
	private String buziCode;		// 营业执照编号
	private String ownerName;		// 法人姓名
	private String ownerId;		// 法人身份证号
	private Date createTime;		// 创建时间
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	
	public RiskLoginBlacklist() {
		super();
	}

	public RiskLoginBlacklist(String id){
		super(id);
	}

	public String getBlackId() {
		return blackId;
	}

	public void setBlackId(String blackId) {
		this.blackId = blackId;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getBuziCode() {
		return buziCode;
	}

	public void setBuziCode(String buziCode) {
		this.buziCode = buziCode;
	}
	
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}
	
}