/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.entity;

import com.heepay.prom.common.persistence.DataEntity;

import java.util.Date;

/**
 *
 * 描    述：分润比例模板管理Entity
 *
 * 创 建 者： @author wangdong
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
public class PromProfitTemplet extends DataEntity<PromProfitTemplet> {
	
	private static final long serialVersionUID = 1L;
	private Long promId;		// ID
	private String templetId;		// 模板号
	private String templetName;		// 模板名称
	private String huiyuanScale;		// 汇元比例
	private String gearScale;		// 推广者比例
	private String spreaderScale;		// 推荐人比例
	private String higherLevelScale;		// 上级比例
	private String highestLevelScale;		// 上上级比例
	private Date createTime;		// 创建时间
	private String creator;		// 创建人
	private Date updateTime;		// 修改时间
	private String updatePeople;		// 修改人
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	
	public PromProfitTemplet() {
		super();
	}

	public PromProfitTemplet(String id){
		super(id);
	}

	public Long getPromId() {
		return promId;
	}

	public void setPromId(Long promId) {
		this.promId = promId;
	}
	
	public String getTempletId() {
		return templetId;
	}

	public void setTempletId(String templetId) {
		this.templetId = templetId;
	}
	
	public String getTempletName() {
		return templetName;
	}

	public void setTempletName(String templetName) {
		this.templetName = templetName;
	}
	
	public String getHuiyuanScale() {
		return huiyuanScale;
	}

	public void setHuiyuanScale(String huiyuanScale) {
		this.huiyuanScale = huiyuanScale;
	}
	
	public String getGearScale() {
		return gearScale;
	}

	public void setGearScale(String gearScale) {
		this.gearScale = gearScale;
	}
	
	public String getSpreaderScale() {
		return spreaderScale;
	}

	public void setSpreaderScale(String spreaderScale) {
		this.spreaderScale = spreaderScale;
	}
	
	public String getHigherLevelScale() {
		return higherLevelScale;
	}

	public void setHigherLevelScale(String higherLevelScale) {
		this.higherLevelScale = higherLevelScale;
	}
	
	public String getHighestLevelScale() {
		return highestLevelScale;
	}

	public void setHighestLevelScale(String highestLevelScale) {
		this.highestLevelScale = highestLevelScale;
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
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getUpdatePeople() {
		return updatePeople;
	}

	public void setUpdatePeople(String updatePeople) {
		this.updatePeople = updatePeople;
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