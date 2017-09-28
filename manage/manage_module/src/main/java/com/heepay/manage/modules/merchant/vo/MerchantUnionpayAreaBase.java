/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 银联地区编码基础数据Entity
 * @author ly
 * @version V1.0
 */
public class MerchantUnionpayAreaBase extends DataEntity<MerchantUnionpayAreaBase> {
	
	private static final long serialVersionUID = 1L;
	private String provinceId;		// 省份id
	private String provinceName;		// 省份名称
	private String cityId;		// 城市id
	private String cityName;		// 市名称
	private String unionpayAreaCode;		// 联地区码银
	
	public MerchantUnionpayAreaBase() {
		super();
	}

	public MerchantUnionpayAreaBase(String id){
		super(id);
	}

	@Length(min=0, max=8, message="省份id长度必须介于 0 和 8 之间")
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	@Length(min=0, max=50, message="省份名称长度必须介于 0 和 50 之间")
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	@Length(min=0, max=8, message="城市id长度必须介于 0 和 8 之间")
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@Length(min=0, max=50, message="市名称长度必须介于 0 和 50 之间")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Length(min=0, max=8, message="联地区码银长度必须介于 0 和 8 之间")
	public String getUnionpayAreaCode() {
		return unionpayAreaCode;
	}

	public void setUnionpayAreaCode(String unionpayAreaCode) {
		this.unionpayAreaCode = unionpayAreaCode;
	}
	
}