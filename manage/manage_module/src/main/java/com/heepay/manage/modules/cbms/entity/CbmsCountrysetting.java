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
 * 描    述：国家代码和名称Entity
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月10日 03:00:25
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
public class CbmsCountrysetting extends DataEntity<CbmsCountrysetting> {
	
	private static final long serialVersionUID = 1L;
	private String countryId;		// country_id
	private String currencyId;		// 币种ID
	private String countryNo;		// 国家编号
	private String isoE;		// 国家简称
	private String countryName;		// 国家名称
	private Long inputuserId;		// 录入人
	private Date createdTime;		// 录入时间
	private Long updateUserId;		// 修改人
	private Date updateTime;		// 修改时间
	private String status;		// 状态（1有效0无效）
	
	public CbmsCountrysetting() {
		super();
	}

	public CbmsCountrysetting(String id){
		super(id);
	}

	@Length(min=1, max=11, message="country_id长度必须介于 1 和 11 之间")
	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	@Length(min=0, max=11, message="币种ID长度必须介于 0 和 11 之间")
	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
	@Length(min=1, max=20, message="国家编号长度必须介于 1 和 20 之间")
	public String getCountryNo() {
		return countryNo;
	}

	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}
	
	@Length(min=0, max=4, message="国家简称长度必须介于 0 和 4 之间")
	public String getIsoE() {
		return isoE;
	}

	public void setIsoE(String isoE) {
		this.isoE = isoE;
	}
	
	@Length(min=1, max=64, message="国家名称长度必须介于 1 和 64 之间")
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public Long getInputuserId() {
		return inputuserId;
	}

	public void setInputuserId(Long inputuserId) {
		this.inputuserId = inputuserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=4, message="状态（1有效0无效）长度必须介于 0 和 4 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CbmsCountrysetting{" +
				"countryId='" + countryId + '\'' +
				"| currencyId='" + currencyId + '\'' +
				"| countryNo='" + countryNo + '\'' +
				"| isoE='" + isoE + '\'' +
				"| countryName='" + countryName + '\'' +
				"| inputuserId=" + inputuserId +
				"| createdTime=" + createdTime +
				"| updateUserId=" + updateUserId +
				"| updateTime=" + updateTime +
				"| status='" + status + '\'' +
				'}';
	}
}