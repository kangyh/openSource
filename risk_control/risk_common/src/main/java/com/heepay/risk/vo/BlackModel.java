package com.heepay.risk.vo;

import java.util.Date;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年4月21日 下午2:10:30
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
public class BlackModel {
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

	    public Integer getBlackId() {
	        return blackId;
	    }

	    public void setBlackId(Integer blackId) {
	        this.blackId = blackId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type == null ? null : type.trim();
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status == null ? null : status.trim();
	    }

	    public String getProductCode() {
	        return productCode;
	    }

	    public void setProductCode(String productCode) {
	        this.productCode = productCode == null ? null : productCode.trim();
	    }

	    public String getCategory() {
	        return category;
	    }

	    public void setCategory(String category) {
	        this.category = category == null ? null : category.trim();
	    }

	    public String getDesc() {
	        return desc;
	    }

	    public void setDesc(String desc) {
	        this.desc = desc == null ? null : desc.trim();
	    }

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

	    public String getCreateAuthor() {
	        return createAuthor;
	    }

	    public void setCreateAuthor(String createAuthor) {
	        this.createAuthor = createAuthor == null ? null : createAuthor.trim();
	    }

	    public String getUpdateAuthor() {
	        return updateAuthor;
	    }

	    public void setUpdateAuthor(String updateAuthor) {
	        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
	    }

}

 