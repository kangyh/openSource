/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：商户员工操作日志Entity
 *
 * 创 建 者： @author ly
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
public class MerchantEmployeeOpLog extends DataEntity<MerchantEmployeeOpLog> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商户ID
	private String employeeId;		// 商户员工ID
	private String opFunction;		// 操作的功能
	private String opAction;		// 操作动作
	private String oldData;		// 旧数据
	private String newData;		// 新数据
	private String ip;		// ip地址
	private String remark;		// 备注
	
	public MerchantEmployeeOpLog() {
		super();
	}

	public MerchantEmployeeOpLog(String id){
		super(id);
	}

	@Length(min=1, max=10, message="商户ID长度必须介于 1 和 10 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=1, max=10, message="商户员工ID长度必须介于 1 和 10 之间")
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	@Length(min=1, max=3, message="操作的功能长度必须介于 1 和 3 之间")
	public String getOpFunction() {
		return opFunction;
	}

	public void setOpFunction(String opFunction) {
		this.opFunction = opFunction;
	}
	
	@Length(min=1, max=3, message="操作动作长度必须介于 1 和 3 之间")
	public String getOpAction() {
		return opAction;
	}

	public void setOpAction(String opAction) {
		this.opAction = opAction;
	}
	
	@Length(min=1, max=256, message="旧数据长度必须介于 1 和 256 之间")
	public String getOldData() {
		return oldData;
	}

	public void setOldData(String oldData) {
		this.oldData = oldData;
	}
	
	@Length(min=1, max=256, message="新数据长度必须介于 1 和 256 之间")
	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}
	
	@Length(min=0, max=100, message="ip地址长度必须介于 0 和 100 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}