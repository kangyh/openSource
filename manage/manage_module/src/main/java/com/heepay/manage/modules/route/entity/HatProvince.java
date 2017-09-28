/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 省Entity
 * @author 牛文
 * @version V1.0
 */
public class HatProvince extends DataEntity<HatProvince> {
	
	private static final long serialVersionUID = 1L;
	private String provinceId;		// 省code
	private String province;		// 省名称
	private String wm;		// 省缩写
	
	public HatProvince() {
		super();
	}

	public HatProvince(String id){
		super(id);
	}

	@Length(min=0, max=18, message="省code长度必须介于 0 和 18 之间")
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	@Length(min=0, max=120, message="省名称长度必须介于 0 和 120 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=30, message="省缩写长度必须介于 0 和 30 之间")
	public String getWm() {
		return wm;
	}

	public void setWm(String wm) {
		this.wm = wm;
	}
	
}