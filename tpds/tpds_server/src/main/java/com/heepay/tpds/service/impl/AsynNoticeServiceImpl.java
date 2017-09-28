package com.heepay.tpds.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.heepay.tpds.enums.HyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.rpc.service.RpcService;
import com.heepay.rpc.tpds.service.AsynNoticeService;
import com.heepay.tpds.client.TpdsCommonClient;
import com.heepay.tpds.dao.TpdsCustomerAccountMapper;
import com.heepay.tpds.entity.TpdsCustomerAccount;
import com.heepay.tpds.enums.Code;
import com.heepay.tpds.util.HttpClientUtils;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.tpds.vo.SignkeyCommon;
import com.heepay.utils.http.WithoutAuthHttpsClient;
import com.heepay.utils.security.KKAES2;
/**
 * 
 *
 * 描    述：异步通知
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月8日 下午6:20:34
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
@RpcService(name="AsynNoticeServiceImpl",processor=AsynNoticeService.Processor.class)
public class AsynNoticeServiceImpl implements com.heepay.rpc.tpds.service.AsynNoticeService.Iface {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Resource(name = "tpdsCommonClient")
	TpdsCommonClient tpdsCommonClient;
	@Resource(name = "httpsClient")
	WithoutAuthHttpsClient httpsClient;
	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;
	@Resource(name = "signkeyCommon")
	SignkeyCommon signkeyCommon;
	@Autowired
	private TpdsCustomerAccountMapper tpdsCustomerAccountMapper;

	/**
	 * 
	 * @方法说明：客户充值异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	@Override
	public String customerChargeAsynNotice(String requestHeader, String requestBody) throws TException {
		// TODO Auto-generated method stub
		try {
			Map<String, String> requestHeaderMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestHeader, Map.class);
			Map<String, String> requestBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestBody, Map.class);
			Map<String, String> bodyMap = new HashMap<String, String>();
			Map<String, String> headerMap = new HashMap<String, String>();
			bodyMap.put("businessSeqNo", "");//业务流水号
			bodyMap.put("oldbusinessSeqNo", "");//原业务流水号
			bodyMap.put("oldTxnType", requestHeaderMap.get("txnType"));//原交易代码
			bodyMap.put("dealStatus", "");//原交易业务处理状态
			if(StringUtils.isNotBlank(requestBodyMap.get("result")) && StringUtils.equals("1000", requestBodyMap.get("result"))){
				headerMap.put("respCode", Code.C0.getValue());//原交易响应码
				headerMap.put("respMsg", Code.C0.getContent());//原交易响应信息
			}else if(StringUtils.isNotBlank(requestBodyMap.get("result")) && StringUtils.equals("1002", requestBodyMap.get("result"))){
				headerMap.put("respCode", HyCode.FAIL00000.getValue());//原交易响应码
				headerMap.put("respMsg", HyCode.FAIL00000.getContent());//原交易响应信息
			}
			bodyMap.put("accNo", "");//虚拟账号
			bodyMap.put("secBankaccNo", "");//二类户账号
			bodyMap.put("note", "");//备注
			bodyMap.put("busiBranchNo", tpdsCommonClient.getBusiBranchNo());//支付公司代码

			JSONObject jSONObject_Input_bank = new JSONObject();
			jSONObject_Input_bank.put("reqHeader", new JSONObject(headerMap));
			jSONObject_Input_bank.put("inBody", new JSONObject(bodyMap));
			logger.info("客户充值异步通知玖财通的报文体-->{}", jSONObject_Input_bank.toString());
			String resultBankJson = httpsClient.send(serverBankInfo.getJctHostIp()+"?ctl=dep&act=charge_back", jSONObject_Input_bank.toString(), "UTF-8");
			logger.info("客户充值异步通知玖财通返回的报文体-->{}", resultBankJson);
			jSONObject_Input_bank.remove("reqHeader");
			jSONObject_Input_bank.remove("inBody");
			jSONObject_Input_bank.put("respHeader", new JSONObject(headerMap));
			jSONObject_Input_bank.put("outBody", new JSONObject(bodyMap));
			logger.info("客户充值异步通知返回宜宾的报文体-->{}", jSONObject_Input_bank.toString());
			resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString(), "UTF-8");
			logger.info("客户充值异步通知返回宜宾的返回报文体-->{}", jSONObject_Input_bank.toString());
			if(StringUtils.isNotBlank(requestBodyMap.get("result")) && StringUtils.equals("1000", requestBodyMap.get("result"))){
				return "ok";
			}
		} catch (Exception e) {
			logger.error("客户充值异步通知异常-->{}", e);
		}
		return "";
	}

	/**
	 * 
	 * @方法说明：客户提现异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	@Override
	public String customerWithdrawAsynNotice(String requestHeader, String requestBody) throws TException {
		JSONObject jSONObject_Input_bank = new JSONObject();
		try {
			jSONObject_Input_bank.put("reqHeader", new JSONObject(requestHeader));
			jSONObject_Input_bank.put("inBody", new JSONObject(requestBody));
			logger.info("客户充值提现异步通知的报文体-->{}", jSONObject_Input_bank.toString());
			String resultBankJson = httpsClient.send(serverBankInfo.getJctHostIp()+"?ctl=dep&act=charge_back", jSONObject_Input_bank.toString(), "UTF-8");
		} catch (Exception e) {
			logger.error("客户充值提现异步通知异常-->{}", e);
		}
		jSONObject_Input_bank.remove("reqHeader");
		jSONObject_Input_bank.remove("inBody");
		jSONObject_Input_bank.put("respHeader", new JSONObject(requestHeader));
		jSONObject_Input_bank.put("outBody", new JSONObject(requestBody));
		return jSONObject_Input_bank.toString();
	}

	/**
	 * 
	 * @方法说明：文件异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	@Override
	public String fileAsynNotice(String requestHeader, String requestBody) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @方法说明：银行提现异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	@Override
	public String bankWithdrawAsynNotice(String requestHeader, String requestBody) throws TException {
		JSONObject jSONObject_Input_bank = new JSONObject();
		try {
			jSONObject_Input_bank.put("reqHeader", new JSONObject(requestHeader));
			jSONObject_Input_bank.put("inBody", new JSONObject(requestBody));
			logger.info("客户提现异步通知的报文体-->{}", jSONObject_Input_bank.toString());
			String resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString(), "UTF-8");
		} catch (Exception e) {
			logger.error("客户提现异步通知异常-->{}", e);
		}
		jSONObject_Input_bank.remove("reqHeader");
		jSONObject_Input_bank.remove("inBody");
		jSONObject_Input_bank.put("respHeader", new JSONObject(requestHeader));
		jSONObject_Input_bank.put("outBody", new JSONObject(requestBody));
		return jSONObject_Input_bank.toString();
	}

	/**
	 * 
	 * @方法说明：日切异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	@Override
	public String cutDayAsynNotice(String requestHeader, String requestBody) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @方法说明：设置密码异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	@Override
	public String setPasswordAsynNotice(String requestHeader, String requestBody) throws TException {
		Map<String, Object> requestBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestBody, Map.class);
		TpdsCustomerAccount tpdsCustomerAccount = new TpdsCustomerAccount();
		JSONObject jSONObject_Output_bank = new JSONObject();
		jSONObject_Output_bank.put("respHeader", new JSONObject(requestHeader));
		jSONObject_Output_bank.put("outBody", new JSONObject(requestBody));
		try {
			logger.info("设置密码异步通知报文头-->{}", requestHeader);
			logger.info("设置密码异步通知报文体-->{}", requestBodyMap);
			tpdsCustomerAccount.setBankAccNo(KKAES2.decrypt(requestBodyMap.get("accNo")+"",signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			tpdsCustomerAccount.setSecBankAccNo(KKAES2.decrypt(requestBodyMap.get("secBankaccNo")+"",signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			logger.info("设置密码更新二类户-->台账账号：BankAccNo{}，二类户：SecBankAccNo{}", tpdsCustomerAccount.getBankAccNo(),tpdsCustomerAccount.getSecBankAccNo());
			int count = tpdsCustomerAccountMapper.updateEntityByAcc(tpdsCustomerAccount);
			if (count>0){
				logger.info("设置密码更新二类户-->台账账号：TpdsCustomAccpunt 成功！条数："+count);
			}
			JSONObject jSONObject_Input_bank = new JSONObject();
			jSONObject_Input_bank.put("reqHeader", new JSONObject(requestHeader));
			jSONObject_Input_bank.put("inBody", new JSONObject(requestBody));
			String retStr = HttpClientUtils.httpPostWithJSON(serverBankInfo.getJctHostIp()+"?ctl=dep&act=pwd_back", jSONObject_Input_bank.toString());
			logger.info("设置密码异步通知玖财通返回报文-->{}", retStr);
		} catch (Exception e) {
			logger.error("设置密码通知异常-->{}", e);
		}
		logger.info("设置密码通知返回报文体-->{}", jSONObject_Output_bank.toString());
		return jSONObject_Output_bank.toString();
	}

	/**
	 * 
	 * @方法说明：绑卡  修改绑卡异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	@Override
	public String editPasswordAsynNotice(String requestHeader, String requestBody) throws TException {
		JSONObject jSONObject_Input_bank = new JSONObject();
		jSONObject_Input_bank.put("respHeader", new JSONObject(requestHeader));
		jSONObject_Input_bank.put("inBody", new JSONObject(requestBody));
		try {
			String retStr = HttpClientUtils.httpPostWithJSON(serverBankInfo.getJctHostIp()+"?ctl=dep&act=bank_back", jSONObject_Input_bank.toString());
			logger.info("绑卡  修改绑卡异步通知玖财通返回报文-->{}", retStr);
		} catch (Exception e) {
			logger.info("绑卡  修改绑卡异步通知异常-->{}", e);
		}
		jSONObject_Input_bank.remove("inBody");
		jSONObject_Input_bank.put("outBody", new JSONObject(requestBody));
		logger.info("绑卡  修改绑卡异步通知返回宜宾的报文-->{}", jSONObject_Input_bank.toString());
		return jSONObject_Input_bank.toString();
	}

	/**
	 * 
	 * @方法说明：重置密码异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	@Override
	public String resetPasswordAsynNotice(String requestHeader, String requestBody) throws TException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
