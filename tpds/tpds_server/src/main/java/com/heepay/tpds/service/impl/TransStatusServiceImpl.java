/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月15日下午4:51:33
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
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月15日下午4:51:33
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
@RpcService(name="TransStatusServiceImpl",processor=com.heepay.rpc.tpds.service.TransStatusService.Processor.class)
public class TransStatusServiceImpl implements com.heepay.rpc.tpds.service.TransStatusService.Iface{

	private static final Logger logger = LogManager.getLogger();
	
	@Resource(name = "signkeyCommon")
	SignkeyCommon signkeyCommon;	
	
	JSONObject jSONObject_output = null;
	
	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;
	
	@Resource(name="httpsClient")
	WithoutAuthHttpsClient httpsClient;
	
	@Override
	public String transStatusQuery(String reqHeader, String body) throws TException {
		// TODO Auto-generated method stub
		
		String result = null;
		
		JSONObject jSONObject_Input_reqHeader = new JSONObject(reqHeader);
		JSONObject jSONObject_Input_InBody = new JSONObject(body);
		
		jSONObject_Input_reqHeader.put("txnType", InfaceType.C00001.getValue());  //交易状态信息查询
		
		JSONObject jSONObject_Input_bank = new JSONObject();
		jSONObject_Input_bank.put("reqHeader", jSONObject_Input_reqHeader);
		jSONObject_Input_bank.put("inBody", jSONObject_Input_InBody);
		try {				
						
			result = httpsClient.send(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString(), "UTF-8");
			return dataInfoAESDecrypt(result);
		} catch (Exception e) {
			logger.error("交易状态查询异常:{}", body, e);
		}
		return result;
	}
	
	/**
	 * 
	 * @方法说明：解密密处理
	 * @author xuangang
	 * @param inBody
	 * @时间：2017年2月24日上午11:24:37
	 */
	private String dataInfoAESDecrypt(String result){
		
		if(result == null)
			return result;			
		
			try {
				
				jSONObject_output = new JSONObject(result);
				//解密处理,客户台账账户
				jSONObject_output.getJSONObject("outBody").put("accountNo", KKAES2.decrypt(String.valueOf(jSONObject_output.getJSONObject("outBody").get("accountNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
				//二类户账号
				jSONObject_output.getJSONObject("outBody").put("secBankaccNo", KKAES2.decrypt(String.valueOf(jSONObject_output.getJSONObject("outBody").get("secBankaccNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
				//标的台账账户
				jSONObject_output.getJSONObject("outBody").put("objectaccNo", KKAES2.decrypt(String.valueOf(jSONObject_output.getJSONObject("outBody").get("objectaccNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
				
			} catch (JSONException e) {				
				logger.error("数据信息查询敏感字段解密异常，返回报文:{}", result, e);
			} catch (Exception e) {				
				logger.error("数据信息查询敏感字段解密异常，返回报文:{}", result, e);
			}	
			
			return jSONObject_output.toString();
	}

}
