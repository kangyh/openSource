package com.heepay.tpds.vo;

import java.util.Map;

import org.json.JSONObject;
/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月10日 下午1:45:13
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
	public class ResponseMessage{
	private CommonResponseHeaderMessage respHeader;
	private JSONObject outBody;
	public CommonResponseHeaderMessage getRespHeader() {
		
		return respHeader;
	}
	public void setRespHeader(CommonResponseHeaderMessage respHeader) {
		this.respHeader = respHeader;
	}
	public JSONObject getOutBody() {
		//return "{\"status\":\"ok\"}";
		return outBody;
	}
	public void setOutBody(JSONObject outBody) {
		this.outBody = outBody;
	}

	/*private CommonResponseHeaderMessage setRespHeaderValue()
	{
		CommonResponseHeaderMessage respHeader = new CommonResponseHeaderMessage();
		respHeader.setVersion("1.0");		
		respHeader.setMerchantCode("34556");
		respHeader.setTxnType("");	
		respHeader.setClientSn("");		
		respHeader.setClientDate("");	
		respHeader.setClientTime("");	
		respHeader.setServiceSn	("");	
		respHeader.setServiceDate("");	
		respHeader.setServiceTime("");	
		respHeader.setRespCode("");
		respHeader.setRespMsg("");
		respHeader.setSignTime("");
		respHeader.setSignature("");
		return respHeader;
	}*/
	public String getJSONWithHeader(String body)
	{
		return "";
	}
}
