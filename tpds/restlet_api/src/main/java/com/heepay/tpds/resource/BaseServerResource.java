package com.heepay.tpds.resource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.tpds.vo.CommonResponseHeaderMessage;
import com.heepay.tpds.vo.RequestMessage;
import com.heepay.tpds.vo.ResponseMessage;
import com.heepay.tpds.vo.SignkeyCommon;
/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月10日 上午10:55:29
 * 创建描述：
 * 
 * 修 改 者：  
 * 修改时间：
 * 修改描述： 
 * 
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 * @param <T>
 *
 */
import com.heepay.utils.security.KKAES2;

public class BaseServerResource extends ServerResource{
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private SignkeyCommon signkeyCommon;
	
	private String filePath = this.getClass().getResource("/").getPath();
	
	public String getFilePath() {
		return filePath;
	}

	private Representation commonEntity ;
	
	private RequestMessage  requestMessage ; 
	
	private ResponseMessage responseMessage;

	public Representation getCommonEntity() {
		return commonEntity;
	}

	public void setCommonEntity(Representation commonEntity) {
		this.commonEntity = commonEntity;
	}

	public RequestMessage getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(RequestMessage requestMessage) {
		this.requestMessage = requestMessage;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public String Encrypt(String encryptStr)
	{
		try {
			log.info("加密串{},加密密钥{},加密向量{}",encryptStr,signkeyCommon.getBank_Key(),signkeyCommon.getBank_IV());
			return KKAES2.decrypt(encryptStr, signkeyCommon.getBank_Key(),signkeyCommon.getBank_IV());
		} catch (Exception e) {
			log.info("加密失败",e);
		}
		return null;
	}
	public String Decrypt(String decryptStr)
	{
		try {
			log.info("解密串{},解密密钥{},加密向量{}",decryptStr,signkeyCommon.getBank_Key(),signkeyCommon.getBank_IV());
			return KKAES2.decrypt(decryptStr, signkeyCommon.getBank_Key(),signkeyCommon.getBank_IV());
		} catch (Exception e) {
			log.info("解密失败",e);
			
		}
		return null;
	}
	
	protected ResponseMessage getExceptionDecrypt()
	{
		ResponseMessage responseMessage = new ResponseMessage();
		CommonResponseHeaderMessage respHeader = new CommonResponseHeaderMessage();
		responseMessage.setRespHeader(respHeader);
		respHeader.setRespCode("888");
		respHeader.setRespMsg("报文加密验证失败");
		responseMessage.setOutBody(new JSONObject());
		return responseMessage;
	}
	
	/**
	 * 
	 * @param reqEntity 请求参数，从前端接收的实体对像
	 * @param error 公共校验时，返回的校验
	 * @param resEntity,调用 rpc 时返回的 实体对像
	 * @return
	 */
	protected ResponseMessage setResponseMessage(JSONObject reqHeaderEntityResult,JSONObject error,String respStr)
	{
		ResponseMessage responseMessage = new ResponseMessage();
		CommonResponseHeaderMessage respHeader = new CommonResponseHeaderMessage();
		responseMessage.setRespHeader(respHeader);
		respHeader.setMerchantCode(reqHeaderEntityResult.has("merchantCode")?reqHeaderEntityResult.getString("merchantCode"):"");
		respHeader.setTxnType(reqHeaderEntityResult.has("txnType")?reqHeaderEntityResult.getString("txnType"):"");
		respHeader.setClientSn(reqHeaderEntityResult.has("clientSn")?reqHeaderEntityResult.getString("clientSn"):"");
		respHeader.setClientDate(reqHeaderEntityResult.has("clientDate")?reqHeaderEntityResult.get("clientDate").toString():"");
		respHeader.setClientTime(reqHeaderEntityResult.has("clientTime")?reqHeaderEntityResult.get("clientTime").toString():"");
		respHeader.setSignTime(reqHeaderEntityResult.has("signTime")?reqHeaderEntityResult.get("signTime").toString():"");
		respHeader.setSignature(reqHeaderEntityResult.has("signature")?reqHeaderEntityResult.getString("signature"):"");
		respHeader.setServiceDate("");
		respHeader.setServiceTime("");
		respHeader.setServiceSn("");
		if ( error !=null && error.has("errorinfo")){ //如果有错误，就不用进行其它的赋值操作
			respHeader.setRespCode(error.getString("errorcode"));
			respHeader.setRespMsg(error.getString("errorinfo"));
		}else{
			if (new JSONObject(respStr).has("respHeader"))
			{
				JSONObject respEntity = new JSONObject(respStr).getJSONObject("respHeader");
				if (respEntity.has("serviceSn")){
					respHeader.setServiceDate(String.valueOf(respEntity.get("serviceSn")));
				}
				if (respEntity.has("serviceDate")){
					respHeader.setServiceDate(String.valueOf(respEntity.get("serviceDate")));
				}
				if (respEntity.has("serviceTime")){
					respHeader.setServiceTime(String.valueOf(respEntity.get("serviceTime")));
				}
				if (respEntity.has("respCode")){
					respHeader.setRespCode(String.valueOf(respEntity.get("respCode")));
				}
				if (respEntity.has("respMsg")){
					respHeader.setRespMsg(String.valueOf(respEntity.get("respMsg")));
				}
			}
			if (new JSONObject(respStr).has("outBody"))
			{
				responseMessage.setOutBody(new JSONObject(respStr).getJSONObject("outBody"));
			}
		}
		return responseMessage;
	}
    
	@Override
	 public void init(Context context, Request request, Response response){
		 super.init(context, request, response);
		 log.info("--------------------开始打印浏览器请求参数---------------------");
		 log.info("context---->"+context.getCurrent().toString());
		 log.info("request---->"+request.getHeaders().toString());
		 String referer = request.getHeaders().getValues("Referer");
		 
		 if(referer==null){
			 log.info("request.referer---->"+"is null");
		 }else{
			 if(request.getHeaders().removeFirst("Referer")){
				 log.info("request.referer---->"+"is delete");
				 log.info("request---->"+request.getHeaders().toString());
			 }else{
				 log.info("request.referer---->"+referer);
			 }
		 }
		 log.info("request---->"+request.getHostRef().toString());
		 log.info("request---->"+request.getMethod().toString());
		 log.info("request---->"+request.getCurrent().toString());	 
		 log.info("--------------------结束打印浏览器请求参数---------------------");
	}

}
