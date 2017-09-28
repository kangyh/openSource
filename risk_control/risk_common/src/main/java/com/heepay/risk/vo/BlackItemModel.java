package com.heepay.risk.vo;

import java.util.Date;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年4月21日 下午2:16:05
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
public class BlackItemModel {
	 private Integer blackItemId;

	    private Integer blackId;

	    private String blackItemValue;

	    private String status;

	    private String createTime;

	    private String updateTime;

	    private String createAuthor;

	    private String updateAuthor;

	    public Integer getBlackItemId() {
	        return blackItemId;
	    }

	    public void setBlackItemId(Integer blackItemId) {
	        this.blackItemId = blackItemId;
	    }

	    public Integer getBlackId() {
	        return blackId;
	    }

	    public void setBlackId(Integer blackId) {
	        this.blackId = blackId;
	    }

	    public String getBlackItemValue() {
	        return blackItemValue;
	    }

	    public void setBlackItemValue(String blackItemValue) {
	        this.blackItemValue = blackItemValue == null ? null : blackItemValue.trim();
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status == null ? null : status.trim();
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

 