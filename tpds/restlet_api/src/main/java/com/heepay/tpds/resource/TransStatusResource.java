package com.heepay.tpds.resource;

import java.io.IOException;

import javax.annotation.Resource;

import com.heepay.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

import com.heepay.rpc.client.TransStatusQueryClient;
import com.heepay.tpds.interceptor.FieldValidate;
import com.heepay.tpds.vo.ResponseMessage;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.utils.http.WithoutAuthHttpsClient;

/***
 * 
* 
* 描    述：交易状态查询
*
* 创 建 者： xuangang
* 创建时间：  2017年2月14日下午3:47:07
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
public class TransStatusResource extends BaseServerResource{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	TransStatusQueryClient transStatusQueryClient;

	@Post
	public Representation transStatusC00001(Representation entity) throws IOException
	{
		logger.info("交易状态查询");
		String decryptStr = this.Decrypt(entity.getText());
		logger.info("交易状态查询,解密报文：{}", decryptStr);
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
			error= FieldValidate.validateData(this.getFilePath()+"format/transStatus.xml", result);
			if (!(error.has("errorinfo")))
			{
				respJson = transStatusQueryClient.transStatusQuery(header, requestBody);	
			}	
		}catch(Exception e){
			logger.error("数据信息查询异常！报文体:{}", requestBody, e);
		}
		
		ResponseMessage responseMessage = setResponseMessage(resultReqHeader,error,respJson);
		
		logger.info("method:{},response_entity:{}",routeName,(new JSONObject(responseMessage)).toString());
		return new JsonRepresentation(responseMessage);
	}	

}
