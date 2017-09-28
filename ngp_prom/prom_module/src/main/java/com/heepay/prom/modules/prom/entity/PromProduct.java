/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.entity;

import com.heepay.prom.common.persistence.DataEntity;

import java.util.Date;

/**
 *
 * 描    述：产品管理Entity
 *
 * 创 建 者： @author wj
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
public class PromProduct extends DataEntity<PromProduct> {
	
	private static final long serialVersionUID = 1L;
	private Long promId;		// ID
	private String productId;		// 产品编码
	private String productName;		// 产品名称
	private String subjectType;		// 档位编码
	private String gearName;		// 档位名称
	private String spreadMoney;		// 推广金额
	private String spreadScale;		// 推广比例
	private String importBath;		// 导入批次号
	private Date createTime;		// 导入时间
	private String creator;		// 导入人
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	
	public PromProduct() {
		super();
	}

	public PromProduct(String id){
		super(id);
	}

	public Long getPromId() {
		return promId;
	}

	public void setPromId(Long promId) {
		this.promId = promId;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	
	public String getGearName() {
		return gearName;
	}

	public void setGearName(String gearName) {
		this.gearName = gearName;
	}
	
	public String getSpreadMoney() {
		return spreadMoney;
	}

	public void setSpreadMoney(String spreadMoney) {
		this.spreadMoney = spreadMoney;
	}
	
	public String getSpreadScale() {
		return spreadScale;
	}

	public void setSpreadScale(String spreadScale) {
		this.spreadScale = spreadScale;
	}
	
	public String getImportBath() {
		return importBath;
	}

	public void setImportBath(String importBath) {
		this.importBath = importBath;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}
	
}