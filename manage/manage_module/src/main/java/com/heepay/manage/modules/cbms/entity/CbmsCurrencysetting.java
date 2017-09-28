/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 *
 * 描    述：币种信息管理Entity
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
public class CbmsCurrencysetting extends DataEntity<CbmsCurrencysetting> {
	
	private static final long serialVersionUID = 1L;
	private String currencyId;		// 币种表主键ID
	private String currencyNo;		// 币种编号
	private String currencyName;		// 币种名称
	private String inputuserId;		// 录入人
	private Date createdTime;		// 录入时间
	private String updateUserId;		// 修改人
	private Date updateTime;		// 修改时间
	private String status;		// 状态
	
	public CbmsCurrencysetting() {
		super();
	}

	public CbmsCurrencysetting(String id){
		super(id);
	}

	@Length(min=1, max=11, message="币种表主键ID长度必须介于 1 和 11 之间")
	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
	@Length(min=1, max=20, message="币种编号长度必须介于 1 和 20 之间")
	public String getCurrencyNo() {
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}
	
	@Length(min=1, max=64, message="币种名称长度必须介于 1 和 64 之间")
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public String getInputuserId() {
		return inputuserId;
	}

	public void setInputuserId(String inputuserId) {
		this.inputuserId = inputuserId;
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

	@Override
	public String toString() {
		return "CbmsCurrencysetting{" +
				"currencyId='" + currencyId + '\'' +
				"| currencyNo='" + currencyNo + '\'' +
				"| currencyName='" + currencyName + '\'' +
				"| inputuserId='" + inputuserId + '\'' +
				"| createdTime=" + createdTime +
				"| updateUserId='" + updateUserId + '\'' +
				"| updateTime=" + updateTime +
				"| status='" + status + '\'' +
				'}';
	}
}