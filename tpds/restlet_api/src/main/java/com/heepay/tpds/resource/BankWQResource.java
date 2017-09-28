package com.heepay.tpds.resource;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.rpc.client.BankWQClient;
import com.heepay.tpds.enums.HyCode;
import com.heepay.tpds.enums.InfaceType;
import com.heepay.tpds.interceptor.FieldValidate;
import com.heepay.tpds.vo.ResponseMessage;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.tpds.vo.SignkeyCommon;
import com.heepay.utils.security.KKAES2;
import com.heepay.utils.security.SignRSA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * 
 *
 * 描 述：监管银行的提现和交易状态查询
 *
 * 创 建 者： wangdong 
 * 创建时间：2017年2月15日 下午8:22:08 
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
public class BankWQResource extends BaseServerResource {

	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private BankWQClient bankWQClient;
	
	@Resource(name = "signkeyCommon")
	SignkeyCommon signkeyCommon;

	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;

	@Post
	public Representation bankMethod(Representation entity) throws IOException {
		
		log.info("restlet_api{}", "调用银行提现接口");
		JsonRepresentation jr = new JsonRepresentation(entity.getText());
		JSONObject result = jr.getJsonObject();
		String respJson ="{}";
		JSONObject resultReqHeader = (JSONObject) result.get("reqHeader");
		JSONObject resultInBody =(JSONObject) result.get("inBody");
		String routeName = getRequestAttributes().get("routeName").toString();
		String requestHeader = resultReqHeader.toString();
		log.info("银行提现接口解密restlet_api{}", resultInBody);
		JSONObject error = new JSONObject();
		log.info("显示【银行】调用的路径：method:{},request_entity:{}",routeName,entity);

		//组装验签的value  验签处理
		TreeMap<String,String> map = new TreeMap<>(new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				//指定排序器按照key升序排列
				return o1.compareTo(o2);
			}
		});
		if (StringUtils.equals(resultReqHeader.getString("txnType"), InfaceType.BTX001.getValue())){
			map.put("businessSeqNo", resultInBody.getString("businessSeqNo"));
			map.put("rType", resultInBody.getString("rType"));
			map.put("currency", resultInBody.getString("currency"));
			map.put("amount", resultInBody.getString("amount"));
			try {
				map.put("bankAccountNo", KKAES2.decrypt(resultInBody.getString("bankAccountNo"),signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
				map.put("bankBranch", KKAES2.decrypt(resultInBody.getString("bankBranch"),signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
				map.put("certNo", KKAES2.decrypt(resultInBody.getString("certNo"),signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			} catch (Exception e) {
				log.info("【银行提现】报文验签解密异常--->{}",e);
			}
			map.put("note", resultInBody.getString("note"));
		}else if (StringUtils.equals(resultReqHeader.getString("txnType"), InfaceType.BCX001.getValue())){
			map.put("businessSeqNo", resultInBody.getString("businessSeqNo"));
			map.put("oldbusinessSeqNo", resultInBody.getString("oldbusinessSeqNo"));
		}

		String value = resultReqHeader.getString("signTime")+"|"+ SignRSA.getTreeMap(map);
		//验签
		boolean flag = SignRSA.varifySign(this.getFilePath()+serverBankInfo.getSignPublicKey(),value,resultReqHeader.get("signature").toString());
		value = value.replaceAll(map.get("bankAccountNo"), Aes.encryptStr(map.get("bankAccountNo").toString(), Constants.QuickPay.SYSTEM_KEY));
		value = value.replaceAll(map.get("certNo"), Aes.encryptStr(map.get("certNo").toString(), Constants.QuickPay.SYSTEM_KEY));
		log.info("【银行提现】报文验签参数VALUE--->{}",value);
		log.info("【银行提现】报文验签结果--->{}",flag);
		if(flag){
			// 监管银行提现
			if (routeName.toUpperCase().equals("BANKWITHDRAW")) {
				try {
					result.getJSONObject("inBody").put("bankAccountNo", KKAES2.decrypt(result.getJSONObject("inBody").get("bankAccountNo")+"",signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV())+"");
					result.getJSONObject("inBody").put("bankBranch", KKAES2.decrypt(result.getJSONObject("inBody").get("bankBranch")+"",signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV())+"");
					result.getJSONObject("inBody").put("certNo", KKAES2.decrypt(result.getJSONObject("inBody").get("certNo")+"",signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV())+"");
				} catch (Exception e) {
					log.info("银行提现接口解密异常restlet_api{}", e);
				}
				error= FieldValidate.validateData(this.getFilePath()+"format/bankwithdraw.xml", result);
				String body = result.get("inBody").toString();
				body = body.replaceAll(((JSONObject) result.get("inBody")).getString("bankAccountNo"),Aes.encryptStr(map.get("bankAccountNo").toString(), Constants.QuickPay.SYSTEM_KEY));
				body = body.replaceAll(((JSONObject) result.get("inBody")).getString("certNo"),Aes.encryptStr(map.get("certNo").toString(), Constants.QuickPay.SYSTEM_KEY));
				log.info("监管银行提现报文:{}",body);
				log.info("监管银行提现报文验证结果{}",error);
				if (!(error.has("errorinfo")))
				{
					respJson = bankWQClient.bankWithdraw(requestHeader, result.get("inBody").toString());
				}
			}
			// 监管银行交易状态查询
			if (routeName.toUpperCase().equals("BANKSTATUSQUERY")) {
				log.info("监管银行交易状态查询报文-->",result.toString());
				error= FieldValidate.validateData(this.getFilePath()+"format/bankquery.xml", result);
				log.info("监管银行交易状态查询报文验证结果{}",error);
				if (!(error.has("errorinfo")))
				{
					respJson = bankWQClient.bankStatusQuery(requestHeader, result.get("inBody").toString());
				}
			}
		}else{
			resultReqHeader.put("respCode", HyCode.FAIL88888.getValue());
			resultReqHeader.put("respMsg", HyCode.FAIL88888.getContent());
		}

		ResponseMessage responseMessage = setResponseMessage(resultReqHeader,error,respJson);
		log.info("method:{},response_entity:{}",routeName,(new JSONObject(responseMessage)).toString());
		return new JsonRepresentation(responseMessage);
	}
}