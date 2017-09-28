/**
 * 
 */
package com.heepay.manage.modules.risk.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class RiskBlackorwhiteVo  implements Serializable{
	
	private Integer blackId;
	private String name;

    private String type;

    private String status;

    private String productCode;

    private String category;

    private String desc;

    private String createTime;

    private String updateTime;

    private String createAuthor;

    private String updateAuthor;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}

	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}
	public Integer getBlackId() {
		return blackId;
	}
	public void setBlackId(Integer blackId) {
		this.blackId = blackId;
	}
}
