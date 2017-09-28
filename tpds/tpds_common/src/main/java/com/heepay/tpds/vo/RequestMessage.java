package com.heepay.tpds.vo;

import java.util.Map;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月10日 下午1:43:37
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
public class RequestMessage {
private CommonRequestHeaderMessage reqHeader;
private Map inBody;
public CommonRequestHeaderMessage getReqHeader() {
	return reqHeader;
}
public void setReqHeader(CommonRequestHeaderMessage reqHeader) {
	this.reqHeader = reqHeader;
}
public Map getInBody() {
	return inBody;
}
public void setInBody(Map inBody) {
	this.inBody = inBody;
}

}
