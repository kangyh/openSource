package com.heepay.tpds.vo;

import org.springframework.stereotype.Service;

/**
 * 
 * 
 * 描    述：异步通知地址Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年4月17日上午11:31:39 
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
@Service
public class NotifyUrlVo {
	
	private String notifyUrl;
	private String callbackUrl;
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

}
