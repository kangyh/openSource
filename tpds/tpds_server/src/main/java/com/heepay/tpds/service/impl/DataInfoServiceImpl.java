/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月16日上午10:05:14
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
package com.heepay.tpds.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.heepay.rpc.service.RpcService;
import com.heepay.tpds.enums.InfaceType;
import com.heepay.tpds.util.HttpClientUtils;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.tpds.vo.SignkeyCommon;
import com.heepay.utils.http.WithoutAuthHttpsClient;
import com.heepay.utils.security.KKAES2;

/***
 * 
 * 
 * 描    述：数据信息查询
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月16日上午10:05:14
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
@Component
@RpcService(name="DataInfoServiceImpl",processor=com.heepay.rpc.tpds.service.DataInfoService.Processor.class)
public class DataInfoServiceImpl implements com.heepay.rpc.tpds.service.DataInfoService.Iface{

	private static final Logger logger = LogManager.getLogger();
	
	@Resource(name = "signkeyCommon")
	SignkeyCommon signkeyCommon;	
	
	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;
	
	@Resource(name="httpsClient")
	WithoutAuthHttpsClient httpsClient;
	
	@Override
	public String dataInfoQuery(String reqHeader, String body) throws TException {
		
		JSONObject jSONObject_Input_reqHeader = new JSONObject(reqHeader);
		JSONObject jSONObject_Input_InBody = new JSONObject(body);
		JSONObject jSONObject_Input_bank = new JSONObject();
		
		jSONObject_Input_reqHeader.put("txnType", InfaceType.C00002.getValue());  //数据信息查询
		
		jSONObject_Input_bank.put("reqHeader", jSONObject_Input_reqHeader);
		jSONObject_Input_bank.put("inBody", jSONObject_Input_InBody);
		
		JSONObject jSONObject_output = new JSONObject();
		
		String result = null;
		
		String resultBankJson="";
		try {			
			dataInfoAESEncrypt(jSONObject_Input_InBody);
			
//			result = HttpClientUtils.httpPostWithJSON(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString());
			
			resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString(), "UTF-8");
			jSONObject_output = new JSONObject(resultBankJson);
			if(jSONObject_output.getJSONObject("respHeader").getString("respCode").toString().equals("P2P0000")){
				dataInfodecrypt(jSONObject_output.getJSONObject("outBody"));
			}
			
		} catch (Exception e) {
			logger.error("数据信息查询异常，{}",body, e);
		}
		return jSONObject_output.toString();
	}
	/**
	 * 
	 * @方法说明：加密处理
	 * @author xuangang
	 * @param inBody
	 * @时间：2017年2月24日上午11:24:37
	 */
	private void dataInfoAESEncrypt(JSONObject inBody){
		
		if(inBody == null)
			return;		
		
			try {
				//敏感信息,会员编号
				String customerId = String.valueOf(inBody.get("customerId"));
				if(customerId != null && !"".equals(customerId)){
					inBody.put("customerId", KKAES2.encrypt(customerId, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));	
				}
				String accountNo = String.valueOf(inBody.get("accountNo")); //新增 add by wyl 2017.5.31
				if(accountNo != null && !"".equals(accountNo)){
					inBody.put("accountNo", KKAES2.encrypt(accountNo, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));	
				}
			} catch (JSONException e) {				
				logger.error("数据信息查询敏感字段加密异常，请求报文:{}", inBody, e);
			} catch (Exception e) {				
				logger.error("数据信息查询敏感字段加密异常，请求报文:{}", inBody, e);
			}		
	}
	/**
	 * 
	 * @方法说明：解密处理
	 * @author xuangang
	 * @param inBody
	 * @时间：2017年2月24日上午11:26:49
	 */
	private JSONObject dataInfodecrypt(JSONObject outBody){
		
		if(outBody == null)
			return outBody;		
		
			try {
				//敏感信息,会员编号
				String customerId = String.valueOf(outBody.get("customerId"));
				if(customerId != null && !"".equals(customerId)){
					outBody.put("customerId", KKAES2.decrypt(customerId, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));						
				}
				
				//手机号
				outBody.put("phoneNo", KKAES2.decrypt(String.valueOf(outBody.get("phoneNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
	
				//台账账号
				outBody.put("accountNo", KKAES2.decrypt(String.valueOf(outBody.get("accountNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
				//二类户账号
				outBody.put("secBankaccNo", KKAES2.decrypt(String.valueOf(outBody.get("secBankaccNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
	
				
				JSONArray list  = (JSONArray)outBody.get("cardList");
				
				for(int i=0; i<list.length(); i++){
					
					JSONObject data = (JSONObject)list.get(i);
					
					//敏感信息,绑卡卡号
					String tiedAccno = String.valueOf(data.get("tiedAccno"));
					if(tiedAccno != null && !"".equals(tiedAccno))						
						data.put("tiedAccno", KKAES2.decrypt(tiedAccno, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));	
					
					//敏感信息,绑卡手机号
					String tiedAcctelno = String.valueOf(data.get("tiedAcctelno"));						
					if(tiedAcctelno != null && !"".equals(tiedAcctelno))						
						data.put("tiedAcctelno", KKAES2.decrypt(tiedAcctelno, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));						
				}	
				
			} catch (JSONException e) {				
				logger.error("数据信息查询敏感字段解密异常，请求报文:{}", outBody, e);
			} catch (Exception e) {				
				logger.error("数据信息查询敏感字段解密异常，请求报文:{}", outBody, e);
			}	
			
			return outBody;
	}
}
