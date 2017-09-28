package com.heepay.manage.modules.monitor.java.entity;
/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月20日 下午2:28:17
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
public class MonitorMessage<T> {
	private Boolean hasError;
	private String returnCode;
	private String message;
	private T data;
	public Boolean getHasError() {
		return hasError;
	}
	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
