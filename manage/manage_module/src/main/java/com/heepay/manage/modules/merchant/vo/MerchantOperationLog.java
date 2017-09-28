/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * merchantEntity
 * @author ly
 * @version V1.0
 */
public class MerchantOperationLog extends DataEntity<MerchantOperationLog> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商户ID
	private String employeeId;		// 员工ID
	private String operateFunction;		// 操作功能
	private String operateBehavior;		// 操作行为
	private Date operateDate;		// 操作时间
	private String currentIp;		// 当前操作IP
	private String operateOldData;		// 操作原对象数据记录
	private String operateNewData;		// 操作更新后对象数据记录
	private String headerUserAgent;		// 浏览器的版本号、类型
	private String requestUrl;		// 请求字符串的客户端地址
	
	public MerchantOperationLog() {
		super();
	}

	public MerchantOperationLog(String id){
		super(id);
	}

	@Length(min=0, max=11, message="商户ID长度必须介于 0 和 11 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=11, message="员工ID长度必须介于 0 和 11 之间")
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	@Length(min=0, max=20, message="操作功能长度必须介于 0 和 20 之间")
	public String getOperateFunction() {
		return operateFunction;
	}

	public void setOperateFunction(String operateFunction) {
		this.operateFunction = operateFunction;
	}
	
	@Length(min=0, max=50, message="操作行为长度必须介于 0 和 50 之间")
	public String getOperateBehavior() {
		return operateBehavior;
	}

	public void setOperateBehavior(String operateBehavior) {
		this.operateBehavior = operateBehavior;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	
	@Length(min=0, max=15, message="当前操作IP长度必须介于 0 和 15 之间")
	public String getCurrentIp() {
		return currentIp;
	}

	public void setCurrentIp(String currentIp) {
		this.currentIp = currentIp;
	}
	
	public String getOperateOldData() {
		return operateOldData;
	}

	public void setOperateOldData(String operateOldData) {
		this.operateOldData = operateOldData;
	}
	
	public String getOperateNewData() {
		return operateNewData;
	}

	public void setOperateNewData(String operateNewData) {
		this.operateNewData = operateNewData;
	}
	
	@Length(min=0, max=50, message="浏览器的版本号、类型长度必须介于 0 和 50 之间")
	public String getHeaderUserAgent() {
		return headerUserAgent;
	}

	public void setHeaderUserAgent(String headerUserAgent) {
		this.headerUserAgent = headerUserAgent;
	}
	
	@Length(min=0, max=255, message="请求字符串的客户端地址长度必须介于 0 和 255 之间")
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
}