package com.heepay.tpds.resource;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

import com.heepay.common.util.StringUtil;
import com.heepay.rpc.client.FundTransClient;
import com.heepay.tpds.interceptor.FieldValidate;
import com.heepay.tpds.vo.ResponseMessage;



/***
 * 
* 
* 描    述：资金交易T00002
*
* 创 建 者： xuangang
* 创建时间：  2017年2月14日下午3:46:56
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
public class FundTransResource extends BaseServerResource{
	
	private static final Logger log = LogManager.getLogger();

	@Autowired
	FundTransClient fundTransClient;
	
	@Post
	public Representation fundTransT00002(Representation entity) throws IOException{

		String entityStr = entity.getText();
		log.info("资金交易T00002,请求密文：{}", entityStr);
		String decryptStr = this.Decrypt(entityStr);
		log.info("资金交易T00002,解密后报文：{}", decryptStr);
		String routeName = getRequestAttributes().get("routeName").toString();
		JsonRepresentation jr = new JsonRepresentation(decryptStr);
		JSONObject result = jr.getJsonObject();					
		JSONObject resultReqHeader =(JSONObject) result.get("reqHeader");	 //请求报文头
		JSONObject resultInBody =(JSONObject) result.get("inBody");          //请求报文体
		String header =  resultReqHeader.toString();
		String requestBody = resultInBody.toString();
		String respJson ="{}";                                         //返回报文体
		
		JSONObject error = new JSONObject();	
		try{
			error= FieldValidate.validateData(this.getFilePath()+"format/fundTrans.xml", result);
			if (!(error.has("errorinfo")))
			{
				respJson = fundTransClient.fundTrans(header, requestBody);    //
			}			
		}catch(Exception e){
			log.error("资金交易T00002异常！", e);
		}	
		ResponseMessage responseMessage = setResponseMessage(resultReqHeader,error,respJson);
		
		log.info("method:{},response_entity:{}",routeName,(new JSONObject(responseMessage)).toString());
		return new JsonRepresentation(responseMessage);
	}
}
