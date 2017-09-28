/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * mcc基础数据Entity
 * @author ly
 * @version V1.0
 */
public class MerchantIndustryBase extends DataEntity<MerchantIndustryBase> {
	
	private static final long serialVersionUID = 1L;
	private String industryId;		// 行业id
	private String industryName;		// 行业名称
	private String industryChildId;		// 行业子类id
	private String industryChildName;		// 行业子类名称
	private String parentId;		// 父类id
	private String childParentId;		// 子类父id
	private String mcc;		// mcc码
	private String industryDescribe;		// 行业描述
	
	public MerchantIndustryBase() {
		super();
	}

	public MerchantIndustryBase(String id){
		super(id);
	}

	@Length(min=0, max=8, message="行业id长度必须介于 0 和 8 之间")
	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	
	@Length(min=0, max=50, message="行业名称长度必须介于 0 和 50 之间")
	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	@Length(min=0, max=8, message="行业子类id长度必须介于 0 和 8 之间")
	public String getIndustryChildId() {
		return industryChildId;
	}

	public void setIndustryChildId(String industryChildId) {
		this.industryChildId = industryChildId;
	}
	
	@Length(min=0, max=50, message="行业子类名称长度必须介于 0 和 50 之间")
	public String getIndustryChildName() {
		return industryChildName;
	}

	public void setIndustryChildName(String industryChildName) {
		this.industryChildName = industryChildName;
	}
	
	@Length(min=0, max=8, message="父类id长度必须介于 0 和 8 之间")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Length(min=0, max=8, message="子类父id长度必须介于 0 和 8 之间")
	public String getChildParentId() {
		return childParentId;
	}

	public void setChildParentId(String childParentId) {
		this.childParentId = childParentId;
	}
	
	@Length(min=0, max=50, message="mcc码长度必须介于 0 和 50 之间")
	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
	@Length(min=0, max=50, message="行业描述长度必须介于 0 和 50 之间")
	public String getIndustryDescribe() {
		return industryDescribe;
	}

	public void setIndustryDescribe(String industryDescribe) {
		this.industryDescribe = industryDescribe;
	}
	
}