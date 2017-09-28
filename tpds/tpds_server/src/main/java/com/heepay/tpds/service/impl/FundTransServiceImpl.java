/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月16日上午9:53:32
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

import com.heepay.common.util.JsonMapperUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.StringUtil;
import com.heepay.rpc.service.RpcService;
import com.heepay.tpds.service.IFundTransHandle;
import com.heepay.tpds.util.HttpClientUtils;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.tpds.vo.SignkeyCommon;
import com.heepay.utils.http.WithoutAuthHttpsClient;
import com.heepay.utils.security.KKAES2;

import java.util.Map;

/***
 * 
 * 
 * 描    述：资金交易server端接口
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月16日上午9:53:32
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
@RpcService(name="FundTransServiceImpl",processor=com.heepay.rpc.tpds.service.FundTransService.Processor.class)
public class FundTransServiceImpl implements com.heepay.rpc.tpds.service.FundTransService.Iface{

	private static final Logger logger = LogManager.getLogger();
	@Autowired
	IFundTransHandle fundTransHandleImpl;
	
	@Resource(name = "signkeyCommon")
	SignkeyCommon signkeyCommon;
	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;
	@Resource(name="httpsClient")
	WithoutAuthHttpsClient httpsClient;
	
	@Override
	public String fundTrans(String reqHeader, String body) throws TException {
		
		logger.info("资金交易server端处理开始，{}", body);
		// TODO Auto-generated method stub
		JSONObject jSONObject_Input_InBody = new JSONObject(body);
		JSONObject jSONObject_Input_bank = new JSONObject();
		JSONObject jSONObject_output =null;
		String resultBankJson="";
		Map<String, Object> reqHeaderMap = JsonMapperUtil.nonEmptyMapper().fromJson(reqHeader, Map.class);
		reqHeaderMap.put("txnType","T00002");
		JSONObject jSONObject_Input_reqHeader = new JSONObject(reqHeaderMap);
		jSONObject_Input_bank.put("reqHeader", jSONObject_Input_reqHeader);
		jSONObject_Input_bank.put("inBody", jSONObject_Input_InBody);
		
		if(StringUtil.notBlank(body)){			
			fundTransHandleImpl.fundTransSave(body);
			
			try {
				logger.info("资金交易同步接口发送银行报文---->{}",jSONObject_Input_bank.toString());
				fundTransAESEncrypt(jSONObject_Input_InBody);
				resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString(), "UTF-8");
				logger.info("资金交易同步接口银行返回报文---->{}",resultBankJson);
				jSONObject_output = new JSONObject(resultBankJson);   //出错校验
				if (jSONObject_output.getJSONObject("respHeader").getString("respCode").toString().equals("P2P0000")){
				
				}
			} catch (Exception e) {
				logger.error("资金交易异常，请求报文:{}", body, e);
			}
		}
		return resultBankJson;
	}
	
	public void fundTransAESEncrypt(JSONObject inBody){
		
		if(inBody == null)
			return;		
		
			try {
				//敏感信息,标的编号
				String objectId = String.valueOf(inBody.get("objectId"));
				if(objectId != null && !"".equals(objectId)){
					inBody.put("objectId", KKAES2.encrypt(objectId, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));	
				}				
				
				if ((inBody.get("accountList") != null)&&(String.valueOf(inBody.get("accountList")).length()>0)){
					
					JSONArray list  = (JSONArray)inBody.get("accountList");
					System.out.println(list.length());
					for(int i=0; i<list.length(); i++){
						
						JSONObject data = (JSONObject)list.get(i);
						
						//敏感信息,借方台账账户
						String debitAccountNo = String.valueOf(data.get("debitAccountNo"));
						if(debitAccountNo != null && !"".equals(debitAccountNo))						
							data.put("debitAccountNo", KKAES2.encrypt(debitAccountNo, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));	
						
						//敏感信息,贷方台账账户
						String cebitAccountNo = String.valueOf(data.get("cebitAccountNo"));						
						if(cebitAccountNo != null && !"".equals(cebitAccountNo))						
							data.put("cebitAccountNo", KKAES2.encrypt(cebitAccountNo, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));						
					}					
				}
				
			} catch (JSONException e) {				
				logger.error("资金交易敏感字段加密异常，请求报文:{}", inBody, e);
			} catch (Exception e) {				
				logger.error("资金交易敏感字段加密异常，请求报文:{}", inBody, e);
			}		
	}
}
