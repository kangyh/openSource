/**
 * 
 */
package com.heepay.manage.modules.risk.entity;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class RiskBlackorwhiteItemVo implements Serializable{
	
	private Integer blackItemId;

    private Integer blackId;

    private String blackItemValue;

    private String status;

    private String createTime;

    private String updateTime;

    private String createAuthor;

    private String updateAuthor;


	public String getBlackItemValue() {
		return blackItemValue;
	}

	public void setBlackItemValue(String blackItemValue) {
		this.blackItemValue = blackItemValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		this.createAuthor = createAuthor;
	}

	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}

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

}
