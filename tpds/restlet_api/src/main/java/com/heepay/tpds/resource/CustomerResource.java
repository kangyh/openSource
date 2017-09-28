package com.heepay.tpds.resource;

import com.heepay.codec.Aes;
import com.heepay.codec.MD5Util;
import com.heepay.common.util.Constants;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.ProductType;
import com.heepay.rpc.client.CustomerClient;
import com.heepay.rpc.client.DataInfoSyncServiceClient;
import com.heepay.rpc.client.QuerySignKeyClient;
import com.heepay.tpds.enums.PayType;
import com.heepay.tpds.enums.RType;
import com.heepay.tpds.interceptor.FieldValidate;
import com.heepay.tpds.vo.NotifyUrlVo;
import com.heepay.tpds.vo.PaymentCommon;
import com.heepay.tpds.vo.ResponseMessage;
import com.heepay.utils.security.EncryptUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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
 *
 */

public class CustomerResource extends BaseServerResource{
	
	private static final Logger log = LogManager.getLogger();
	@Autowired
	private DataInfoSyncServiceClient dataInfoSyncServiceClient;
	@Autowired
	private CustomerClient customerClient;
	@Autowired
	private PaymentCommon paymentCommon;
	
	@Autowired
	private NotifyUrlVo notifyUrlVo;
	@Autowired
	private QuerySignKeyClient querySignKeyClient;
	
	@Post
	public Representation customerInfoSync(Representation entity) throws IOException
	{
		log.info("restlet_api:{}", "调用客户接口");
		JSONObject resultReqHeader = null,error = null;
		String respJson = null,routeName=null;
		try{
			String body= entity.getText().replace("body=","");
			String decryptStr = this.Decrypt(body);
			log.info("restlet_api:{}解密之后的报文", EncryptUtil.fieldEncrypt(decryptStr));
			if (decryptStr==null)
			{
				//没有进行加解密处理
				log.info("加密错误："+this.getExceptionDecrypt().toString());
				return  new JsonRepresentation(this.getExceptionDecrypt());
			}
			log.info("restlet_api:{}解密之后的报文", EncryptUtil.fieldEncrypt(decryptStr));
			
			JsonRepresentation jr = new JsonRepresentation(decryptStr);
			JSONObject result = jr.getJsonObject();
			resultReqHeader = (JSONObject) result.get("reqHeader");
			JSONObject resultInBody =(JSONObject) result.get("inBody");
			routeName = getRequestAttributes().get("routeName").toString();
			log.info("显示【客户】调用的路径：method:{}",routeName);

			String requestHeader = resultReqHeader.toString();
			String requestBody = resultInBody.toString();
			error = new JSONObject();
			JSONObject logStr = jr.getJsonObject();
			//记日志
			if (routeName.toUpperCase().equals("CUSTOMERINFOSYNC")) //客户信息同步
			{
				
				error= FieldValidate.validateData(this.getFilePath()+"format/customersync.xml", result);
				if (!(error.has("errorinfo")))
				{
					respJson = dataInfoSyncServiceClient.setCustomerInfoSync(requestHeader, requestBody);
				}
			}else if(routeName.toUpperCase().equals("BIDINFOSYNC")) //标的信息同步
			{
				
				error= FieldValidate.validateData(this.getFilePath()+"format/bidsync.xml", result);
				if (!(error.has("errorinfo"))){
					respJson = dataInfoSyncServiceClient.setBidInfoSync(requestHeader, requestBody);
				}
			}else if(routeName.toUpperCase().equals("CHARGE")) //客户充值
			{
				error= FieldValidate.validateData(this.getFilePath()+"format/customercharge.xml", result);
				log.info("客户充值报文验证结果{}",error);
				if (!(error.has("errorinfo")))
				{
					respJson = customerClient.customerCharge(requestHeader, requestBody);
					log.info("客户充值报文返回结果{}",respJson);
					Map<String, Object> requestBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestBody, Map.class);
					if(null != requestBodyMap.get("payType") && StringUtils.isNotBlank(requestBodyMap.get("payType")+"") 
							&& StringUtils.equals(requestBodyMap.get("payType")+"",PayType.P0.getValue())){
						log.info("客户银行支付渠道充值返回结果{}",respJson);
					}else if(null != requestBodyMap.get("payType") && StringUtils.isNotBlank(requestBodyMap.get("payType")+"") 
							&& StringUtils.equals(requestBodyMap.get("payType")+"",PayType.P1.getValue())){
						log.info("第三方支付渠道充值返回结果{}",respJson);
						if(null != requestBodyMap.get("rType")){
							//B2C网银
							if(StringUtils.equals(requestBodyMap.get("rType")+"",RType.R02.getValue())){
								log.info("客户充值B2C网银跳转银行的支付页面",requestBodyMap.get("rType"));
								return getB2cBody(requestHeader,respJson,requestBody);  //modifyed2017-07-21   kangyh
								/** kangyh  modifyed20170721  
								//Reference targetRef = reRedirect(requestHeader,respJson,requestBody);
								//getResponse().redirectTemporary(targetRef); 
								**/
							}
						}
					}
				}
				
			}else if(routeName.toUpperCase().equals("WITHDRAW")){ //客户提现
				error= FieldValidate.validateData(this.getFilePath()+"format/customercharge.xml", result);
				log.info("客户提现报文验证结果{}",error);
				if (!(error.has("errorinfo"))){
					respJson = customerClient.customerWithdraw(requestHeader, requestBody);
				}
			}else
			{
				respJson = "{}";
			}
		}catch(Exception e)
		{
			log.error(e);
		}
		ResponseMessage responseMessage = setResponseMessage(resultReqHeader,error,respJson);
		log.info("method:{},response_entity:{}",routeName,(new JSONObject(responseMessage)).toString());
		
		return new JsonRepresentation(responseMessage);
	}
	
	/**
	 * 组装发往上游通道的报文格式
	 */
	private Representation getB2cBody(String requestHeader,String respJson,String requestBody){
		
		Representation representation = null;
		try {
			log.info("网银充值处理开始");
			Map<String, String> requestHeaderMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestHeader, Map.class);
			Map<String, String> outBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(respJson, Map.class);
			Map<String, Object> requestBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestBody, Map.class);
			List accountList = (List) requestBodyMap.get("accountList");
			Map cha = (Map) accountList.get(0);
			if(StringUtils.equals(requestBodyMap.get("rType").toString(),RType.R02.getValue())){
				//B2C网银
				String b2cPayUurl =paymentCommon.getPaymentB2CPayUrl();
				String chargeUrl =paymentCommon.getPaymentB2CChargeUrl();
				log.info("调用交易的B2C网银路径：{}", b2cPayUurl);
				log.info("调用交易的B2C网银跳转路径：{}", chargeUrl);
				ClientResource client = new ClientResource(chargeUrl);
				//创建表单  
		        Form form = new Form(); 
		       
				form.add("merchantId", outBodyMap.get("merchantId"));
				form.add("merchantOrderNo", requestBodyMap.get("businessSeqNo") != null ? requestBodyMap.get("businessSeqNo").toString() : null);
				form.add("merchantUserId", outBodyMap.get("merchantUserId"));
				form.add("productCode", "HY_EBANKDPT");
				form.add("payAmount", cha.get("amount") != null ? cha.get("amount").toString() : null);
				form.add("version", requestHeaderMap.get("version") != null ? requestHeaderMap.get("version").toString() : null);
				form.add("notifyUrl", notifyUrlVo.getNotifyUrl());
				
				Map<String,String> map=new TreeMap<String,String>();
				map.put("merchantId", outBodyMap.get("merchantId"));
				map.put("merchantOrderNo", requestBodyMap.get("businessSeqNo") != null ? requestBodyMap.get("businessSeqNo").toString() : null);
				map.put("merchantUserId", outBodyMap.get("merchantUserId"));
				//map.put("notifyUrl", "http://p2.heepay.com/asynNotice/customerCharge");
				map.put("notifyUrl", notifyUrlVo.getNotifyUrl());
				map.put("productCode", "HY_EBANKDPT");
				map.put("payAmount", cha.get("amount") != null ? cha.get("amount").toString() : null);
				map.put("version", requestHeaderMap.get("version") != null ? requestHeaderMap.get("version").toString() : null);
				
				String signKey = querySignKeyClient.querySignKey(outBodyMap.get("merchantId"), ProductType.DEPOSIT_GATEWAYPAY.getValue());
				if(signKey == null) {
					log.info("没有对应的签约key:{},{}", outBodyMap.get("merchantId"), ProductType.DEPOSIT_GATEWAYPAY.getValue());
				}
				//String key = signkeyCommon.getJiuctB2CSignkey();
				String signString = this.getSignMethod(map, signKey);
				log.info("调用交易的B2C网银的,MerchantId{},签名串{}", outBodyMap.get("merchantId"),signString);
				
				form.add("signString", signString);
				form.add("unionPayUrl", b2cPayUurl);
				form.add("requestTime", DateUtils.getDateStr(new Date(),"yyyyMMddHHmmss"));
				form.add("callBackUrl", notifyUrlVo.getCallbackUrl()+"?ctl=uc_money&act=incharge_log");
				form.add("description", outBodyMap.get("merchantId"));
				form.add("clientIp", "");
				form.add("reqHyTime", DateUtils.getDateStr(new Date(), "yyyyMMddHHmmss"));
				form.add("onlineType", "simple");
				form.add("bankId", requestBodyMap.get("bankId") != null ? requestBodyMap.get("bankId").toString() : null);
				form.add("bankName", requestBodyMap.get("bankName") != null ? requestBodyMap.get("bankName").toString() : null);
				form.add("bankCardType", requestBodyMap.get("cardType") != null ? requestBodyMap.get("cardType").toString() : null);
				log.info("调用交易的B2C网银的方法报文体{}", form.toString());
				
				//以post方式提交表单  
		        representation = client.post(form);
		        //log.info("B2C网银返回报文体{}", representation.getText());
		        //getResponse().setEntity(representation);
		        //return representation;
			}
		} catch (Exception e) {
			log.info("网银B2C充值报错", e);
		}
		return representation;
	}
	

	private String getSignMethod(Map<String, String> map, String keyvalue) {
		StringBuilder signkey= new StringBuilder();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			if(StringUtils.isBlank(signkey)) {
				signkey.append(key).append("=").append(map.get(key));
			}else {
				signkey.append("&").append(key).append("=").append(map.get(key));
			}
		}
		signkey.append("&").append("key").append("=").append(keyvalue);
		log.info("签名串为{}", signkey.toString());
		String sign = MD5Util.md5(signkey.toString());
		return sign;
	}

}
