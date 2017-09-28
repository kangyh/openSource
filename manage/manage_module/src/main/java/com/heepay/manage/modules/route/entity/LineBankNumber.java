/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 联行号管理Entity
 * @author 牛文
 * @version V1.0
 */
public class LineBankNumber extends DataEntity<LineBankNumber> {
	
	private static final long serialVersionUID = 1L;
	private String bankName;		// 银行名称
	private String bankNo;		// 银行代码
	private String provinceName;		// 省份名称
	private String provinceCode;		// 省份代码
	private String cityName;		// 城市名称
	private String cityCode;		// 城市代码
	private String openBankName;		// 开户银行名称
	private String openBankCode;		// 开户银行代码
	private String associateLineNumber;		// 联行号
	private String status;		// 状态
	private String source; //来源
	private String updateName; //修改人

	public LineBankNumber() {
		super();
	}

	public LineBankNumber(String id){
		super(id);
	}

	@Length(min=0, max=50, message="银行名称长度必须介于 0 和 50 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=3, message="银行代码长度必须介于 0 和 3 之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Length(min=0, max=50, message="省份名称长度必须介于 0 和 50 之间")
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	@Length(min=0, max=6, message="省份代码长度必须介于 0 和6 之间")
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	@Length(min=0, max=50, message="城市名称长度必须介于 0 和 50 之间")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Length(min=0, max=4, message="城市代码长度必须介于 0 和 4 之间")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	@Length(min=0, max=80, message="开户银行名称长度必须介于 0 和 80 之间")
	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	
	@Length(min=0, max=5, message="开户银行代码长度必须介于 0 和 5 之间")
	public String getOpenBankCode() {
		return openBankCode;
	}

	public void setOpenBankCode(String openBankCode) {
		this.openBankCode = openBankCode;
	}
	
	@Length(min=0, max=12, message="联行号长度必须介于 0 和 12 之间")
	public String getAssociateLineNumber() {
		return associateLineNumber;
	}

	public void setAssociateLineNumber(String associateLineNumber) {
		this.associateLineNumber = associateLineNumber;
	}

	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
}