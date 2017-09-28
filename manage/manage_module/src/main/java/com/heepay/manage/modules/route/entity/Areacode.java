/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 市级Entity
 * @author 牛文
 * @version V1.0
 */
public class Areacode extends DataEntity<Areacode> {
	
	private static final long serialVersionUID = 1L;
	private String areaCode;		// area_code
	private String areaName;		// area_name
	private String provinceCode;		// province_code
	private String provinceName;		// province_name
	
	public Areacode() {
		super();
	}

	public Areacode(String id){
		super(id);
	}

	@Length(min=0, max=255, message="area_code长度必须介于 0 和 255 之间")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	@Length(min=0, max=255, message="area_name长度必须介于 0 和 255 之间")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@Length(min=0, max=255, message="province_code长度必须介于 0 和 255 之间")
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	@Length(min=0, max=255, message="province_name长度必须介于 0 和 255 之间")
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
}