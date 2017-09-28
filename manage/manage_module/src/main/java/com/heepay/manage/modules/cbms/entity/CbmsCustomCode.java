/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：海关代码信息Entity
 *
 * 创 建 者： @author 牛俊鹏
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
public class CbmsCustomCode extends DataEntity<CbmsCustomCode> {
	
	private static final long serialVersionUID = 1L;
	private String customId;		// Customs
	private String superId;		// 上级ID
	private String levelType;		// 级别            0：一级            1：二级            2：三级
	private String customCode;		// 关区代码
	private String chinaName;		// 中文名称
	private String customAlias;		// 关区别名
	private String customName;		// 海关联系人姓名
	private String customPhone;		// 海关联系人电话
	private String customAddress;		// 海关地址
	private String status;		// 状态            0.正常            1. 停用
	private Date createdTime;		// 创建时间
	private Date updateTime;		// 修改时间

	public CbmsCustomCode() {
		super();
	}

	public CbmsCustomCode(String id){
		super(id);
	}

	@Length(min=1, max=10, message="Customs长度必须介于 1 和 10 之间")
	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	@Length(min=0, max=11, message="上级ID长度必须介于 0 和 11 之间")
	public String getSuperId() {
		return superId;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}

	@Length(min=1, max=4, message="级别            0：一级            1：二级            2：三级长度必须介于 1 和 4 之间")
	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	@Length(min=1, max=10, message="关区代码长度必须介于 1 和 10 之间")
	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}

	@Length(min=1, max=100, message="中文名称长度必须介于 1 和 100 之间")
	public String getChinaName() {
		return chinaName;
	}

	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
	}

	@Length(min=0, max=100, message="关区别名长度必须介于 0 和 100 之间")
	public String getCustomAlias() {
		return customAlias;
	}

	public void setCustomAlias(String customAlias) {
		this.customAlias = customAlias;
	}

	@Length(min=0, max=200, message="海关联系人姓名长度必须介于 0 和 200 之间")
	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	@Length(min=0, max=15, message="海关联系人电话长度必须介于 0 和 15 之间")
	public String getCustomPhone() {
		return customPhone;
	}

	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}

	@Length(min=0, max=200, message="海关地址长度必须介于 0 和 200 之间")
	public String getCustomAddress() {
		return customAddress;
	}

	public void setCustomAddress(String customAddress) {
		this.customAddress = customAddress;
	}

	@ Length(min=0, max=10, message="停用长度必须介于 0 和 4 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "CbmsCustomCode{" +
				"customId='" + customId + '\'' +
				"| superId='" + superId + '\'' +
				"| levelType='" + levelType + '\'' +
				"| customCode='" + customCode + '\'' +
				"| chinaName='" + chinaName + '\'' +
				"| customAlias='" + customAlias + '\'' +
				"| customName='" + customName + '\'' +
				"| customPhone='" + customPhone + '\'' +
				"| customAddress='" + customAddress + '\'' +
				"| status='" + status + '\'' +
				"| createdTime=" + createdTime +
				"| updateTime=" + updateTime +
				'}';
	}
}