/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：阻断风险操作表Entity
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
public class RiskBlockInfo extends DataEntity<RiskBlockInfo> {
	
	private static final long serialVersionUID = 1L;
	private Integer blockId;
	private String blockType;		// 阻断类型
	private String ruleId;		// 规则ID
	private String fileds;		// 属性
	private String monitorObject;		// 监控对象
	private String buziType;		// 业务类型
	private Date createtime;		// 创建时间
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	
	public RiskBlockInfo() {
		super();
	}

	public RiskBlockInfo(String id){
		super(id);
	}

	public String getBlockType() {
		return blockType;
	}

	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getFileds() {
		return fileds;
	}

	public void setFileds(String fileds) {
		this.fileds = fileds;
	}
	
	public String getMonitorObject() {
		return monitorObject;
	}

	public void setMonitorObject(String monitorObject) {
		this.monitorObject = monitorObject;
	}
	
	public String getBuziType() {
		return buziType;
	}

	public void setBuziType(String buziType) {
		this.buziType = buziType;
	}
	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}
	
}