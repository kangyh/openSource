/**
 *  
 */
package com.heepay.manage.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 市级Entity
 * @author 牛文
 * @version V1.0
 */
public class HatCities extends DataEntity<HatCities> {
	
	private static final long serialVersionUID = 1L;
	private String cityid;		// 市code
	private String city;		// 市名称
	private String father;		// 父id
	private Integer hotFlag;		// 热度标示
	
	public HatCities() {
		super();
	}

	public HatCities(String id){
		super(id);
	}

	@Length(min=0, max=18, message="市code长度必须介于 0 和 18 之间")
	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	
	@Length(min=0, max=150, message="市名称长度必须介于 0 和 150 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=18, message="父id长度必须介于 0 和 18 之间")
	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}
	
	public Integer getHotFlag() {
		return hotFlag;
	}

	public void setHotFlag(Integer hotFlag) {
		this.hotFlag = hotFlag;
	}
	
}