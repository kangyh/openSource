package com.heepay.tpds.client;

import java.util.*;
import java.util.Map.Entry;

import com.heepay.codec.*;
import com.heepay.common.util.*;
import com.heepay.utils.http.HttpsUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.tpds.vo.B2CPayMsgVo;
import com.heepay.tpds.vo.B2CPayVo;
import com.heepay.tpds.vo.ChargeMsgVo;
import com.heepay.tpds.vo.PaymentCommon;
import com.heepay.tpds.vo.QuickPayConfirmMsgVo;
import com.heepay.tpds.vo.QuickPayConfirmVo;
import com.heepay.tpds.vo.QuickPayMsgVo;
import com.heepay.tpds.vo.QuickPayVo;
import com.heepay.tpds.vo.SignQueryMsgVo;
import com.heepay.tpds.vo.SignQueryVo;
import com.heepay.tpds.vo.SignkeyCommon;
import com.heepay.tpds.vo.TransQueryMsgVo;
import com.heepay.tpds.vo.TransQueryVo;

/**
 * 
 * 
 * 描    述：内部接口   充值客户端
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月9日上午10:46:47 
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
public class ChargeClient {
	
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private PaymentCommon paymentCommon;
	@Autowired
	private SignkeyCommon signkeyCommon;
	
	/**
	 * 
	 * @方法说明：客户B2C支付
	 * @author chenyanming
	 * @param url
	 * @param vo
	 * @return
	 * @时间：2017年2月13日下午2:34:11
	 */
	public B2CPayMsgVo b2cPay(String url, B2CPayVo vo) {
		try {
			Map<String,String> map=new TreeMap<String,String>();
			map.put("merchantId", vo.getMerchantId());
			map.put("merchantOrderNo", vo.getMerchantOrderNo());
			map.put("merchantUserId", vo.getMerchantUserId());
			map.put("productCode", vo.getProductCode());
			map.put("payAmount", vo.getPayAmount());
			map.put("version", vo.getVersion());
			map.put("notifyUrl", vo.getNotifyUrl());
			
			String signMethod = this.getSignMethod(map, "");
			map.put("signString", signMethod);
			
			map.put("requestTime", vo.getRequestTime());
			map.put("callBackUrl", vo.getCallBackUrl());
			map.put("description", vo.getDescription());
			map.put("clientIp", vo.getClientIp());
			map.put("reqHyTime", vo.getReqHyTime());
			map.put("onlineType", vo.getOnlineType());
			map.put("bankId", vo.getBankId());
			map.put("bankName", vo.getBankName());
			map.put("bankCardType", vo.getBankCardType());
			String msg = HttpClientUtils.requestByPostMethodReturnBody(paymentCommon.getPaymentB2CPayUrl(), map);
			logger.info("客户B2C支付从交易系统返回的数据为:{}", msg);
			if(StringUtil.isBlank(msg)){
	    		  return null;
			}else{
	    		  return JsonMapperUtil.nonDefaultMapper().fromJson(msg, B2CPayMsgVo.class);
	    	  }
		} catch (Exception e) {
			logger.error("客户B2C支付失败", e);
			return null;
		}
	} 
	
	/**
	 * 
	 * @方法说明：获取银行列表
	 * @author chenyanming
	 * @return
	 * @时间：2017年2月13日下午3:03:52
	 */
	public String GetBankList(String url, String merchantId) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("merchantId", merchantId);
		return HttpClientUtils.requestByPostMethodReturnBody(paymentCommon.getPaymentBankListUrl(), map);
	}
	
	/**
	 * 
	 * @方法说明：认证支付请求
	 * @author chenyanming
	 * @param url
	 * @param vo
	 * @return
	 * @时间：2017年2月13日下午3:48:16
	 */
	public QuickPayMsgVo QuickPay(String url, QuickPayVo vo, String signkey) {
		try {
			Map<String,String> map=new TreeMap<String,String>();
			map.put("merchantId", vo.getMerchantId());
			map.put("merchantOrderNo", vo.getMerchantOrderNo());
			map.put("merchantUserId", vo.getMerchantUserId());
			map.put("payAmount", vo.getPayAmount());
			map.put("requestTime", vo.getRequestTime());
			map.put("version", vo.getVersion());
			map.put("authorizationCode", vo.getHyAuthCode());
			String bankCardInfoVo = vo.getOwnerName() + "^" + vo.getBankCardNo() + "^" + vo.getOwnerCertNo() +
					"^" + vo.getOwnerMobile();
			logger.info("认证支付请求 银行信息----->{}", Aes.encryptStr(bankCardInfoVo, Constants.QuickPay.SYSTEM_KEY));
			if(StringUtils.isNotBlank(vo.getCvv2())) {
				bankCardInfoVo = bankCardInfoVo + "^" + vo.getCvv2() + "^" + vo.getValidate();
			}
			String string = StringUtils.substring(signkey, 0, 24);
			String encodeECB = Desede.encodeECB(bankCardInfoVo, string);
			map.put("bankCardInfo", encodeECB);
			map.put("requestUserIp", vo.getRequestUserIp());
			map.put("notifyUrl", vo.getNotifyUrl());
			List<NameValuePair> nvPair = generateSign(map,signkey);
			String signString = this.getSignMethod(map, signkey);
			logger.info("认证支付请求签名为{},{}", vo.getMerchantId(), signString);
			nvPair.add(new BasicNameValuePair("merchantUserName", vo.getMerchantUserName()));
			nvPair.add(new BasicNameValuePair("feeType", "EXDEDU"));
			nvPair.add(new BasicNameValuePair("sign", signString));
			String result = HttpsUtil.sendHttpsRequestWithParam(url, nvPair);
			logger.info("认证支付请求从交易系统返回的数据为:{}", result);
			if(StringUtil.isBlank(result)){
	    		return null;
			}else{
				return JsonMapperUtil.nonDefaultMapper().fromJson(result, QuickPayMsgVo.class);
			}
		} catch (Exception e) {
			logger.error("认证支付请求失败", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @方法说明：获取签名串
	 * @author chenyanming
	 * @param map
	 * @return
	 * @时间：2017年2月18日下午12:26:24
	 */
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
		logger.info("签名串为:{}", signkey);
		String sign = MD5Util.md5(signkey.toString());
		return sign;
	}

	/**
	 * 
	 * @方法说明：认证支付确认
	 * @author chenyanming
	 * @param url
	 * @param vo
	 * @return
	 * @时间：2017年2月13日下午3:51:56
	 */
	public QuickPayConfirmMsgVo QuickPayConfirm(String url, QuickPayConfirmVo vo, String signkey) {
		try {
			Map<String,String> map=new TreeMap<String,String>();
			map.put("merchantId", vo.getMerchantId());
			map.put("merchantOrderNo", vo.getMerchantOrderNo());
			map.put("merchantUserId", vo.getMerchantUserId());
			map.put("hyPaymentId", vo.getHyPaymentId());
			map.put("payAmount", vo.getPayAmount());
			map.put("requestTime", vo.getRequestTime());
			map.put("version", vo.getVersion());
			map.put("verifyCode", vo.getVerifyCode());
			map.put("token", vo.getToken());
			map.put("authorizationCode", vo.getAuthorizationCode());
			map.put("requestUserIp",vo.getRequestUserIp());
			List<NameValuePair> nvPair = generateSign(map,signkey);
			String signString = this.getSignMethod(map, signkey);
			logger.info("商户的认证支付确认签名为{},{}", vo.getMerchantId(), signString);
			nvPair.add(new BasicNameValuePair("sign", signString));
			String result = HttpsUtil.sendHttpsRequestWithParam(url, nvPair);
			logger.info("认证支付确认从交易系统返回的数据为:{}", result);
			if(StringUtil.isBlank(result)){
	    		return null;
			}else{
				return JsonMapperUtil.nonDefaultMapper().fromJson(result, QuickPayConfirmMsgVo.class);
			}
		} catch (Exception e) {
			logger.error("认证支付确认失败", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @方法说明：签约查询
	 * @author chenyanming
	 * @param url
	 * @param vo
	 * @return
	 * @时间：2017年2月13日下午4:53:17
	 */
	public ChargeMsgVo QuerySignData(String url, SignQueryVo vo) {
		try {
			Map<String,String> map=new TreeMap<String,String>();
			map.put("merchantId", vo.getMerchantId());
			map.put("merchantUserId", vo.getMerchantUserId());
			map.put("version", vo.getVersion());
			map.put("productCode", vo.getProductCode());
			map.put("requestTime", vo.getRequestTime());
			map.put("bankCardType", vo.getBankCardType());
			String signMethod = this.getSignMethod(map, "");
			map.put("signString", signMethod);
			String msg = HttpClientUtils.requestByPostMethodReturnBody(paymentCommon.getPaymentSignqueryUrl(), map);
			logger.info("签约查询从交易系统返回的数据为:{}", msg);
			if(StringUtil.isBlank(msg)){
	    		return null;
			}else{
				return JsonMapperUtil.nonDefaultMapper().fromJson(msg, ChargeMsgVo.class);
			}
		} catch (Exception e) {
			logger.error("签约查询异常", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @方法说明：订单查询
	 * @author chenyanming
	 * @param url
	 * @param vo
	 * @return
	 * @时间：2017年2月13日下午5:30:12
	 */
	public TransQueryMsgVo TransQuery(String url, TransQueryVo vo) {
		try {
			Map<String,String> map=new TreeMap<String,String>();
			map.put("merchantId", vo.getMerchantId());
			map.put("merchantOrderNo", vo.getMerchantOrderNo());
			map.put("version", vo.getVersion());
			map.put("requestTime", vo.getRequestTime());
			
			//String gg = "817a801aaab0fa96529a4c348ffc684c";
			String signMethod = this.getSignMethod(map, signkeyCommon.getJiuctTransQuery());
			//String signMethod = this.getSignMethod(map, gg);
			
			map.put("signString", signMethod);
			String msg = HttpClientUtils.requestByPostMethodReturnBody(paymentCommon.getPaymentTransqueryUrl(), map);
			//String msg = HttpClientUtils.requestByPostMethodReturnBody("https://open.heepay.com/HY_QUICKPAYAPI/transQuery.do", map);
			logger.info("订单查询从交易系统返回的数据为:{}", msg);
			if(StringUtil.isBlank(msg)){
	    		return null;
			}else{
				return JsonMapperUtil.nonDefaultMapper().fromJson(msg, TransQueryMsgVo.class);
			}
		} catch (Exception e) {
			logger.error("订单查询异常", e);
			return null;
		}
	}

	/**
	 * @方法说明：构造签名
	 * @时间： 2017/7/25 15:27
	 * @创建人：wangdong
	 */
	public List<NameValuePair> generateSign(Map<String, String> params, String key) throws Exception {
		Set<Entry<String,String>> entrys = params.entrySet();
		List<String> signs = new ArrayList<String>();
		List<NameValuePair> nvPairs = new ArrayList<NameValuePair>();
		for(Entry<String,String> entry:entrys){
			signs.add(entry.getKey()+"="+(entry.getValue()==null?"":entry.getValue()));
			nvPairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		Collections.sort(signs);
		StringBuffer sb = new StringBuffer();
		for(String sign:signs){
			sb.append(sign);
			sb.append("&");
		}
		sb.append("key="+key);
		System.out.println("签名串加密前为："+sb.toString());
		String paramSign = Md5.encode(sb.toString().getBytes("UTF-8"));
		System.out.println("签名串加密后为："+paramSign);
		nvPairs.add(new BasicNameValuePair("signString", paramSign));
		return nvPairs;
	}

}









