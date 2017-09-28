package com.heepay.tpds.resource;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.heepay.tpds.enums.HyCode;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.tpds.vo.SignkeyCommon;
import com.heepay.utils.security.KKAES2;
import com.heepay.utils.security.SignRSA;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.client.AsynNoticeClient;
import com.heepay.tpds.enums.InfaceType;
import com.heepay.tpds.interceptor.FieldValidate;
import com.heepay.tpds.vo.ResponseMessage;

import javax.annotation.Resource;

/**
 * 
 *
 * 描    述：异步通知接口
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月18日 上午10:50:25
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
public class AsynNoticeResource extends BaseServerResource {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private AsynNoticeClient asynNoticeClient;

	@Resource(name = "signkeyCommon")
	SignkeyCommon signkeyCommon;

	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;



	@Post
	public Representation asynNotice(Representation entity) throws IOException {

		log.info("restlet_api{}", "调用异步通知接口");
		JsonRepresentation jr = new JsonRepresentation(entity.getText());
		JSONObject result = jr.getJsonObject();
		String respJson ="{}";
		JSONObject resultReqHeader = (JSONObject) result.get("reqHeader");
		JSONObject resultInBody =(JSONObject) result.get("inBody");
		String routeName = getRequestAttributes().get("routeName").toString();
		String requestHeader = resultReqHeader.toString();
		String requestBody = resultInBody.toString();
		JSONObject error = new JSONObject();
		log.info("显示【异步通知】调用的路径：method:{},request_entity:{}",routeName,entity);

		log.info("报文转码：{}", new String(result.toString().getBytes("UTF-8")));
		log.info("报文{}",result.toString());

		//组装验签的value  验签处理
		TreeMap<String,String> map = new TreeMap<>(new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				//指定排序器按照key升序排列
				return o1.compareTo(o2);
			}
		});
		map.put("businessSeqNo", resultInBody.getString("businessSeqNo"));
		map.put("oldbusinessSeqNo", resultInBody.getString("oldbusinessSeqNo"));
		map.put("oldTxnType", resultInBody.getString("oldTxnType"));
		map.put("dealStatus", resultInBody.getString("dealStatus"));
		map.put("respCode", resultInBody.getString("respCode"));
		map.put("respMsg", resultInBody.getString("respMsg"));
		try {
			map.put("accNo", KKAES2.decrypt(resultInBody.getString("accNo"),signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			map.put("secBankaccNo", KKAES2.decrypt(resultInBody.getString("secBankaccNo"),signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
		} catch (Exception e) {
			log.info("【异步通知】报文解密异常---->{}",e);
		}
		map.put("note", resultInBody.getString("note"));
		String value = resultReqHeader.getString("signTime")+"|"+ SignRSA.getTreeMap(map);
		log.info("【异步通知】报文验签参数VALUE--->{}",value);
		log.info("【异步通知】报文验签的证书路径--->{}",this.getFilePath()+serverBankInfo.getSignPublicKey());

		//验签
		boolean flag = SignRSA.varifySign(this.getFilePath()+serverBankInfo.getSignPublicKey(),value,resultReqHeader.get("signature").toString());
		log.info("【异步通知】报文验签结果--->{}",flag);
		if(flag){
			//根据报文头中的txnType判断是什么接口
			Map<String, Object> requestBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestBody, Map.class);
			if(null != requestBodyMap && requestBodyMap.size() > 0 && null != requestBodyMap.get("oldTxnType")
					&& StringUtils.isNotBlank(requestBodyMap.get("oldTxnType")+"")){
				String txnType = requestBodyMap.get("oldTxnType")+"";
				if(StringUtils.equals(txnType, InfaceType.U00000.getValue())){
					//设置密码
					log.info("设置密码异步通知");
					error= FieldValidate.validateData(this.getFilePath()+"format/asyn.xml", result);
					log.info("设置密码异步通知报文验证结果{}",error);
					if (!(error.has("errorinfo")))
					{
						respJson = asynNoticeClient.setPasswordAsynNotice(requestHeader, requestBody);
						log.info("设置密码返回结果{}",respJson);
					}
				}else if(StringUtils.equals(txnType, InfaceType.U00001.getValue())){
					//绑卡  修改绑卡
					log.info("绑卡  修改绑卡异步通知");
					error= FieldValidate.validateData(this.getFilePath()+"format/asyn.xml", result);
					log.info("绑卡  修改绑卡异步通知报文验证结果{}",error);
					if (!(error.has("errorinfo")))
					{
						respJson = asynNoticeClient.editPasswordAsynNotice(requestHeader, requestBody);
						log.info("绑卡  修改绑卡异步返回结果{}",respJson);
					}
				}else if(StringUtils.equals(txnType, InfaceType.T00001.getValue())){
					//充值  提现
					error= FieldValidate.validateData(this.getFilePath()+"format/asyn.xml", result);
					log.info("客户提现异步通知报文验证结果{}",error);
					if (!(error.has("errorinfo")))
					{
						respJson = asynNoticeClient.customerWithdrawAsynNotice(requestHeader, requestBody);
					}
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
