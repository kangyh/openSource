/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：海关信息设置Entity
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
public class CbmsCustomsetting extends DataEntity<CbmsCustomsetting> {
	
	private static final long serialVersionUID = 1L;
	private String customId;		// 支付公司海关配置表ID
	private String customNo;		// 海关编号
	private String recordNumber;		// 支付公司海关备案号
	private String customCode;		// 支付公司海关编号
	private String companyName;		// 支付企业名称
	private String companyDxpid;		// 支付公司
	private String inputUserId;		// 录入人
	private Date createdTime;		// 录入时间
	private String updateUserId;		// 修改人
	private Date updateTime;		// 修改时间
	private String status;		// 状态
	private String customName;		// 海关联系人姓名
	private String customPhone;		// 海关联系人电话
	private String customAddress;		// 海关地址
	private String chinaName;		// 海关名称
	public CbmsCustomsetting() {
		super();
	}

	public CbmsCustomsetting(String id){
		super(id);
	}

	@Length(min=1, max=11, message="支付公司海关配置表ID长度必须介于 1 和 11 之间")
	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	@Length(min=1, max=20, message="海关编号长度必须介于 1 和 20 之间")
	public String getCustomNo() {
		return customNo;
	}

	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}
	
	@Length(min=0, max=100, message="支付公司海关备案号长度必须介于 0 和 100 之间")
	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}
	
	@Length(min=0, max=100, message="支付公司海关编号长度必须介于 0 和 100 之间")
	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}
	
	@Length(min=0, max=200, message="支付企业名称长度必须介于 0 和 200 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=64, message="支付公司长度必须介于 0 和 64 之间")
	public String getCompanyDxpid() {
		return companyDxpid;
	}

	public void setCompanyDxpid(String companyDxpid) {
		this.companyDxpid = companyDxpid;
	}
	
	@Length(min=0, max=11, message="录入人长度必须介于 0 和 11 之间")
	public String getInputUserId() {
		return inputUserId;
	}

	public void setInputUserId(String inputUserId) {
		this.inputUserId = inputUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=4, message="状态长度必须介于 0 和 4 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getChinaName() {
		return chinaName;
	}

	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
	}

	@Override
	public String toString() {
		return "CbmsCustomsetting{" +
				"customId='" + customId + '\'' +
				"| customNo='" + customNo + '\'' +
				"| recordNumber='" + recordNumber + '\'' +
				"| customCode='" + customCode + '\'' +
				"| companyName='" + companyName + '\'' +
				"| companyDxpid='" + companyDxpid + '\'' +
				"| inputUserId='" + inputUserId + '\'' +
				"| createdTime=" + createdTime +
				"| updateUserId='" + updateUserId + '\'' +
				"| updateTime=" + updateTime +
				"| status='" + status + '\'' +
				"| customName='" + customName + '\'' +
				"| customPhone='" + customPhone + '\'' +
				"| customAddress='" + customAddress + '\'' +
				"| chinaName='" + chinaName + '\'' +
				'}';
	}
}