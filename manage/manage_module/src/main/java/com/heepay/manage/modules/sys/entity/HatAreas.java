/**
 *  
 */
package com.heepay.manage.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 县Entity
 * @author 牛文
 * @version V1.0
 */
public class HatAreas extends DataEntity<HatAreas> {
	
	private static final long serialVersionUID = 1L;
	private String areaid;		// 县code
	private String area;		// 县名称
	private String father;		// 父id
	
	public HatAreas() {
		super();
	}

	public HatAreas(String id){
		super(id);
	}

	@Length(min=0, max=150, message="县code长度必须介于 0 和 150 之间")
	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	
	@Length(min=0, max=180, message="县名称长度必须介于 0 和 180 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=18, message="父id长度必须介于 0 和 18 之间")
	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}
	
}