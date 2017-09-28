package com.heepay.tpds.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.tpds.enums.*;
import com.heepay.tpds.vo.*;
import com.heepay.utils.security.EncryptUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.ProductType;
import com.heepay.rpc.service.RpcService;
import com.heepay.rpc.tpds.service.CustomerCwService;
import com.heepay.tpds.client.ChargeClient;
import com.heepay.tpds.dao.TpdsCustomChargeMapper;
import com.heepay.tpds.dao.TpdsCustomerAccountMapper;
import com.heepay.tpds.dao.TpdsMerchantAccountMapper;
import com.heepay.tpds.dao.TpdsMerchantH5Mapper;
import com.heepay.tpds.dao.TpdsMerchantMsgMapper;
import com.heepay.tpds.entity.TpdsCustomCharge;
import com.heepay.tpds.entity.TpdsCustomerAccount;
import com.heepay.tpds.entity.TpdsMerchantAccount;
import com.heepay.tpds.entity.TpdsMerchantMsg;
import com.heepay.tpds.entity.TpdsProductKey;
import com.heepay.utils.http.WithoutAuthHttpsClient;
import com.heepay.utils.security.KKAES2;

/**
 * 
 *
 * 描 述：客户资金充值和提现【实现】
 *
 * 创 建 者： wangdong 创建时间：2017年2月8日 下午6:20:34 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
@RpcService(name = "CustomerCwServiceImpl", processor = CustomerCwService.Processor.class)
public class CustomerCwServiceImpl implements com.heepay.rpc.tpds.service.CustomerCwService.Iface {

	@Autowired
	private TpdsMerchantMsgMapper tpdsMerchantMsgDaoImpl;

	@Autowired
	private TpdsCustomChargeMapper tpdsCustomChargeDaoImpl;

	@Autowired
	private TpdsCustomerAccountMapper tpdsCustomerAccountMapper;

	@Autowired
	private TpdsMerchantAccountMapper tpdsMerchantAccountDaoImpl;

	@Autowired
	private ChargeClient chargeClient;

	@Autowired
	private TpdsMerchantH5Mapper tpdsMerchantH5DaoImpl;

	@Autowired
	ConfigServiceImpl configServiceImpl;

	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;

	@Resource(name = "httpsClient")
	WithoutAuthHttpsClient httpsClient;

	@Autowired
	SignkeyCommon signkeyCommon;

	@Autowired
	PaymentCommon paymentCommon;
	
	@Autowired
	NotifyUrlVo notifyUrlVo;

	private static final Logger logger = LogManager.getLogger();

	/*
	 * 客户资金充值 (non-Javadoc)
	 * 
	 * @see com.heepay.tpds.service.ICustomMoneyAndPrepaidphoneWithDrawService#
	 * customWithDraw(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String customerCharge(String requestHeader, String requestBody) throws TException {
		// TODO Auto-generated method stub
		try {

			Map<String, Object> requestHeaderMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestHeader, Map.class);
			Map<String, Object> requestBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestBody, Map.class);
			logger.info("客户资金充值的报文头-->{}", requestHeader);
			logger.info("客户资金充值的报文体-->{}", EncryptUtil.fieldEncrypt(requestBody));
			//业务流水号
			String businessSeqNo = requestBodyMap.get("businessSeqNo").toString();
			//获取accountlist中的值
			List accountList = (List) requestBodyMap.get("accountList");
			Map cha = (Map) accountList.get(0);

			// 查询校验交易密码是否成功
			String result = checkPassword(requestHeaderMap, businessSeqNo);
			if (result != null) {
				logger.info("客户资金充值的校验密码失败-->{}", result);
				return result;
			}

			// 获取商户编码
			TpdsMerchantAccount entity = new TpdsMerchantAccount();
			if (requestHeaderMap.get("merchantCode") != null && StringUtils.isNotBlank(requestHeaderMap.get("merchantCode").toString())){
				entity.setSystemNo(requestHeaderMap.get("merchantCode").toString());
				entity = tpdsMerchantAccountDaoImpl.selectBySysNoAndBankNo(entity);
			}
			if(entity == null){
				logger.info("获取商户编码商户信息失败！");
				JSONObject obj = new JSONObject();
				requestHeaderMap.put("respCode", HyCode.FAIL50000.getValue());
				requestHeaderMap.put("respMsg", HyCode.FAIL50000.getContent());
				obj.put("respHeader", new JSONObject(requestHeaderMap));
				return obj.toString();
			}

			// 1.记录该客户的台账
			TpdsMerchantMsg record = saveTpdsMerchantMsg(requestHeaderMap, requestBodyMap, entity);
			
			// 2.保存客户充值信息表
			saveTpdsCustomCharge(requestBodyMap, cha);

			logger.info("客户资金充值【支付类型】-->{}", requestBodyMap.get("payType").toString());

			//银行返回结果
			String resultBankJson;
			// 判断充值的支付方式
			if (null != requestBodyMap.get("payType") && StringUtils.isNotBlank(requestBodyMap.get("payType").toString())
					&& StringUtils.equals(requestBodyMap.get("payType").toString(), PayType.P0.getValue())) {
				// 银行渠道充值
				resultBankJson = bankChargeMethod(requestHeaderMap, requestBodyMap, cha, record);
				logger.info("客户资金充值【银行渠道充值返回结果】-->{}", resultBankJson);
				return resultBankJson;
			} else if (null != requestBodyMap.get("payType") && StringUtils.isNotBlank(requestBodyMap.get("payType").toString())
					&& StringUtils.equals(requestBodyMap.get("payType").toString(), PayType.P1.getValue())) {
				// 第三方渠道充值
				resultBankJson = threePayMethod(requestHeaderMap, requestBodyMap, cha, entity, record);
				logger.info("客户资金充值【第三方充值返回结果】-->{}", resultBankJson);
				return resultBankJson;
			}
		} catch (Exception e) {
			logger.error("客户资金充值异常-->{}", e);
		}
		return "";
	}

	/**
	 * @方法说明：三方充值方法
	 * @时间： 2017/7/7 14:45
	 * @创建人：wangdong
	 */
	private String threePayMethod(Map<String, Object> requestHeaderMap, Map<String, Object> requestBodyMap, Map cha, TpdsMerchantAccount entity, TpdsMerchantMsg record) throws Exception {
		String resultBankJson;// 查询会员编码
		try{
			TpdsCustomerAccount tpdsCustomerAccount = new TpdsCustomerAccount();
			if (null != entity.getMerchantNo()) {
				tpdsCustomerAccount.setMerchantNo(entity.getMerchantNo());
			}
			String cebitAccountNo = cha.get("cebitAccountNo").toString();
			logger.info("客户资金充值【贷方台账账户】-->{}", cebitAccountNo);
			if (StringUtils.isNotBlank(cebitAccountNo)) {
				tpdsCustomerAccount.setBankAccNo(cebitAccountNo);
			}
			if(tpdsCustomerAccount != null){
				logger.info("查询会员编码参数-->{},MerchantNo:{},BankAccNo:{}", tpdsCustomerAccount,tpdsCustomerAccount.getMerchantNo(),tpdsCustomerAccount.getBankAccNo());
			}
			//查询会员编码
			tpdsCustomerAccount = tpdsCustomerAccountMapper.selectCustomerNoByMerchantNo(tpdsCustomerAccount);
			if(tpdsCustomerAccount != null){
				logger.info("查询会员编码返回值-->{},CustomerNo:{}", tpdsCustomerAccount,tpdsCustomerAccount.getCustomerNo());
			}else{
				JSONObject obj = new JSONObject();
				requestHeaderMap.put("respCode", HyCode.FAIL60000.getValue());
				requestHeaderMap.put("respMsg", HyCode.FAIL60000.getContent());
				obj.put("respHeader", new JSONObject(requestHeaderMap));
				logger.info("该商户设置密码成功未发异步通知-->{}", obj.toString());
				return obj.toString();
			}
			B2CPayMsgVo b2CPayMsg = new B2CPayMsgVo();
			QuickPayConfirmMsgVo quickPayConfirmMsg = new QuickPayConfirmMsgVo();

			Map<String, String> headerMap = new HashMap<>();
			Map<String, String> bodyMap = new HashMap<>();
			// 2.调用内部的充值接口
			if (null != requestBodyMap.get("rType")) {
				if (StringUtils.equals(requestBodyMap.get("rType").toString(), RType.R02.getValue())) {
					logger.info("调用内部的充值接口【B2C网银】-->{}", requestBodyMap.get("rType").toString());
					// B2C网银充值
					return b2cChargeMethod(requestHeaderMap, requestBodyMap, cha, entity, record, tpdsCustomerAccount, bodyMap);
				} else if (StringUtils.equals(requestBodyMap.get("rType") + "", RType.R08.getValue())) {
					logger.info("调用内部的充值接口【认证支付请求】-->{}", requestBodyMap.get("rType") + "");
					//认证支付申请
					return quickPay(entity, requestBodyMap, tpdsCustomerAccount, requestHeaderMap, cha);
				} else if (StringUtils.equals(requestBodyMap.get("rType").toString(), RType.R09.getValue())) {
					logger.info("调用内部的充值接口【认证支付确认】-->{}", requestBodyMap.get("rType").toString());
					//认证代码确认
					return quickPayConfirm(entity,requestBodyMap,tpdsCustomerAccount,requestHeaderMap, cha, record);
				}
			}
		}catch (Exception e){
			logger.error("客户资金充值【第三方充值方法】报文体异常-->{}", e);
		}
		return "";
	}

	/**
	 * @方法说明：认证支付确认
	 * @时间： 2017/7/25 13:37
	 * @创建人：wangdong
	 * @param entity
	 * @param requestBodyMap
	 * @param tpdsCustomerAccount
	 * @param requestHeaderMap
	 * @param cha
	 * @param record
	 */
	private String quickPayConfirm(TpdsMerchantAccount entity, Map<String, Object> requestBodyMap, TpdsCustomerAccount tpdsCustomerAccount, Map<String, Object> requestHeaderMap, Map cha, TpdsMerchantMsg record) throws TException {
		try{
			//用于返回客户的报文体
			Map<String,Object> jctOutBodyMap = requestBodyMap;
			Map<String,Object> jctrespHeadMap = requestHeaderMap;

			String productKey = null;
			if (StringUtils.isNoneBlank(entity.getMerchantNo())) {
				productKey = configServiceImpl.getProductKey(entity.getMerchantNo(), ProductType.DEPOSIT_QUICKPAY.getValue());
			}
			if (StringUtils.isBlank(productKey)) {
				logger.info("查询不到商户和汇付宝的签约key,{}", entity.getMerchantNo());
				return null;
			}
			TpdsProductKey tpdsProductKey = JsonMapperUtil.nonEmptyMapper().fromJson(productKey, TpdsProductKey.class);

			QuickPayConfirmVo vo = new QuickPayConfirmVo();
			vo.setMerchantId(entity.getMerchantNo());// 商户的ID
			if(requestBodyMap.get("businessSeqNo") != null && requestBodyMap.get("businessSeqNo") != ""){
				vo.setMerchantOrderNo(requestBodyMap.get("businessSeqNo").toString());// 商户交易号
			}
			if (null != tpdsCustomerAccount && null != tpdsCustomerAccount.getCustomerNo()) {
				vo.setMerchantUserId(tpdsCustomerAccount.getCustomerNo());// 用户号
			}

			if (cha.get("amount") != null && cha.get("amount") != ""){
				vo.setPayAmount(cha.get("amount").toString());// 交易金额
			}
			vo.setRequestTime(DateUtils.getDateStr(new Date(), "yyyyMMddHHmmss"));// 请求时间
			vo.setVersion(requestHeaderMap.get("version").toString());// 版本号
			if(requestBodyMap.get("identifycode") != null && requestBodyMap.get("identifycode") != ""){
				vo.setVerifyCode(requestBodyMap.get("identifycode").toString());// 验证码
				logger.info("调用内部的充值接口【认证支付确认】验证码-->{}", requestBodyMap.get("identifycode").toString());
			}
			if(requestBodyMap.get("note") != null && requestBodyMap.get("note") != ""){
				String token = Aes.decryptStr(requestBodyMap.get("note").toString(),Constants.QuickPay.SYSTEM_KEY);
				logger.info("调用内部的充值接口【认证支付确认】token-->{}", token);
				vo.setToken(token.split(",")[0]);// 支付令牌
				vo.setHyPaymentId(token.split(",")[1]);// 支付单号
				vo.setAuthorizationCode(token.toString().split(",")[2]);// 授权码
			}else{
				logger.info("调用内部的充值接口【认证支付确认】支付令牌异常-->{}", requestBodyMap.get("note"));
				JSONObject object_input_bank = new JSONObject();
				requestHeaderMap.put("respCode",HyCode.FAIL40000.getValue());
				requestHeaderMap.put("respMsg",HyCode.FAIL40000.getContent());
				object_input_bank.put("respHeader", new JSONObject(requestHeaderMap));
				return object_input_bank.toString();
			}
			vo.setRequestUserIp("");// 用户请求IP
			logger.info("调用内部的充值接口【认证支付确认】开始-->{}", vo);
			QuickPayConfirmMsgVo quickPayConfirmMsg = chargeClient.QuickPayConfirm(paymentCommon.getPaymentQuickpayConfirmUrl(), vo, tpdsProductKey.getSignKey());
			logger.info("调用内部的充值接口【认证支付确认】结束，返回参数-->{}", quickPayConfirmMsg);
			JSONObject object_input_bank = new JSONObject();
			if(null != quickPayConfirmMsg){
				// 3.更新台帐记录的状态
				if (StringUtils.isNotBlank(quickPayConfirmMsg.getRetCode())){
					if(StringUtils.equals(quickPayConfirmMsg.getRetCode(), "1000")) {
						requestBodyMap.put("rType", RType.R01.getValue());
						// 5.把信息发给存管银行  移除银行报文中不需要的字段
						requestBodyMap.remove("bankId");
						requestBodyMap.remove("bankName");
						requestBodyMap.remove("cardType");
						requestBodyMap.remove("ownerName");
						requestBodyMap.remove("ownerCertNo");
						requestBodyMap.remove("ownerMobile");
						requestBodyMap.remove("identifycode");
						requestBodyMap.put("note","");

						//加密字段
						if (requestBodyMap.get("bankAccountNo") != null && requestBodyMap.get("bankAccountNo") != "") {
							requestBodyMap.put("bankAccountNo",
									KKAES2.encrypt(requestBodyMap.get("bankAccountNo").toString(), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
						}
						if (requestBodyMap.get("secBankaccNo") != null && requestBodyMap.get("secBankaccNo") != "") {
							requestBodyMap.put("secBankaccNo",
									KKAES2.encrypt(requestBodyMap.get("secBankaccNo").toString(), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
						}
						if (cha.get("debitAccountNo") != null && cha.get("debitAccountNo") != "") {
							cha.put("debitAccountNo", KKAES2.encrypt(cha.get("debitAccountNo").toString(),
									signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
						}
						if (cha.get("cebitAccountNo") != null && cha.get("cebitAccountNo") != "") {
							cha.put("cebitAccountNo", KKAES2.encrypt(cha.get("cebitAccountNo").toString(),
									signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
						}

						//转化交易代码
						requestHeaderMap.put("txnType", "T00003");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						requestBodyMap.put("settleDate", sdf.format(DateUtils.getNextDate()).toString());

						object_input_bank.put("reqHeader", new JSONObject(requestHeaderMap));
						object_input_bank.put("inBody", new JSONObject(requestBodyMap));
						logger.info("客户资金第三方认证支付通道报文体-->{}", EncryptUtil.fieldEncrypt(object_input_bank.toString()));
						String resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), object_input_bank.toString(), "UTF-8");
						logger.info("客户第三方认证支付银行返回报文-->{}", resultBankJson);
						//更新客户公共信息表
						updateTpdsMerchantMsg(record, resultBankJson,false);

						object_input_bank.remove("reqHeader");
						object_input_bank.remove("inBody");
						jctrespHeadMap.put("respCode", Code.C0.getValue());
						jctrespHeadMap.put("respMsg",Code.C0.getContent());
						jctOutBodyMap.put("rType", RType.R09.getValue());
						object_input_bank.put("respHeader", new JSONObject(jctrespHeadMap));
						object_input_bank.put("outBody", new JSONObject(jctOutBodyMap));
						String resultCustomJson = httpsClient.send(serverBankInfo.getJctHostIp()+"?ctl=uc_chongceshi&act=incha", object_input_bank.toString(), "UTF-8");
						logger.info("认证支付确认，跳转【玖财通】充值页面 返回玖财通参数报文：{}",resultCustomJson);
						return object_input_bank.toString();
					}else if(StringUtils.equals(quickPayConfirmMsg.getRetCode(),"1003")
							|| StringUtils.equals(quickPayConfirmMsg.getRetCode(),"3004")
							|| StringUtils.equals(quickPayConfirmMsg.getRetCode(),"3013")
							|| StringUtils.equals(quickPayConfirmMsg.getRetCode(),"3021")
							|| StringUtils.equals(quickPayConfirmMsg.getRetCode(),"4001")
							|| StringUtils.equals(quickPayConfirmMsg.getRetCode(),"4300")){
						jctrespHeadMap.put("respCode", HyCode.FAIL43000.getValue());
						jctrespHeadMap.put("respMsg", HyCode.FAIL43000.getContent());
						jctOutBodyMap.put("rType", RType.R09.getValue());
						object_input_bank.put("respHeader", new JSONObject(jctrespHeadMap));
						object_input_bank.put("outBody", new JSONObject(jctOutBodyMap));
						String resultCustomJson = httpsClient.send(serverBankInfo.getJctHostIp()+"?ctl=uc_chongceshi&act=incha", object_input_bank.toString(), "UTF-8");
						logger.info("认证支付确认，跳转【玖财通】充值页面 返回玖财通参数报文：{}",resultCustomJson);
						return object_input_bank.toString();
					}else if(StringUtils.equals(quickPayConfirmMsg.getRetCode(),"4105")
							|| StringUtils.equals(quickPayConfirmMsg.getRetCode(),"4106")
							|| StringUtils.equals(quickPayConfirmMsg.getRetCode(),"4107")){
						jctrespHeadMap.put("respCode", HyCode.FAIL44000.getValue());
						jctrespHeadMap.put("respMsg", HyCode.FAIL44000.getContent());
						jctOutBodyMap.put("rType", RType.R09.getValue());
						object_input_bank.put("respHeader", new JSONObject(jctrespHeadMap));
						object_input_bank.put("outBody", new JSONObject(jctOutBodyMap));
						String resultCustomJson = httpsClient.send(serverBankInfo.getJctHostIp()+"?ctl=uc_chongceshi&act=incha", object_input_bank.toString(), "UTF-8");
						logger.info("认证支付确认，跳转【玖财通】充值页面 返回玖财通参数报文：{}",resultCustomJson);
						return object_input_bank.toString();
					}else if(StringUtils.equals(quickPayConfirmMsg.getRetCode(),"3005")){
						jctrespHeadMap.put("respCode", HyCode.FAIL45000.getValue());
						jctrespHeadMap.put("respMsg", HyCode.FAIL45000.getContent());
						jctOutBodyMap.put("rType", RType.R09.getValue());
						object_input_bank.put("respHeader", new JSONObject(jctrespHeadMap));
						object_input_bank.put("outBody", new JSONObject(jctOutBodyMap));
						String resultCustomJson = httpsClient.send(serverBankInfo.getJctHostIp()+"?ctl=uc_chongceshi&act=incha", object_input_bank.toString(), "UTF-8");
						logger.info("认证支付确认，跳转【玖财通】充值页面 返回玖财通参数报文：{}",resultCustomJson);
						return object_input_bank.toString();
					}else{
						jctrespHeadMap.put("respCode", quickPayConfirmMsg.getRetCode());
						jctrespHeadMap.put("respMsg", quickPayConfirmMsg.getRetMsg());
						jctOutBodyMap.put("rType", RType.R09.getValue());
						object_input_bank.put("respHeader", new JSONObject(jctrespHeadMap));
						object_input_bank.put("outBody", new JSONObject(jctOutBodyMap));
						String resultCustomJson = httpsClient.send(serverBankInfo.getJctHostIp()+"?ctl=uc_chongceshi&act=incha", object_input_bank.toString(), "UTF-8");
						logger.info("认证支付确认，跳转【玖财通】充值页面 返回玖财通参数报文：{}",resultCustomJson);
						return object_input_bank.toString();
					}
				}
			}
			return "";
		}catch (Exception e){
			logger.info("调用内部的充值接口【认证支付确认】异常：{}", e);
		}
		return "";
	}

	/**
	 * @方法说明：认证支付请求
	 * @时间： 2017/7/25 11:02
	 * @创建人：wangdong
	 * @param entity
	 * @param requestBodyMap
	 * @param tpdsCustomerAccount
	 * @param requestHeaderMap
	 * @param cha
	 */
	private String quickPay(TpdsMerchantAccount entity, Map<String, Object> requestBodyMap, TpdsCustomerAccount tpdsCustomerAccount, Map<String, Object> requestHeaderMap, Map cha) throws TException {
		try{
			// 认证支付请求
			Map<String, String> bodyMap = new HashMap<>();
			String productKey = null;
			if (StringUtils.isNoneBlank(entity.getMerchantNo())) {
				productKey = configServiceImpl.getProductKey(entity.getMerchantNo(), ProductType.DEPOSIT_QUICKPAY.getValue());
			}
			if (StringUtils.isBlank(productKey)) {
				logger.info("查询不到商户和汇付宝的签约key,{}", entity.getMerchantNo());
				return null;
			}
			TpdsProductKey tpdsProductKey = JsonMapperUtil.nonEmptyMapper().fromJson(productKey, TpdsProductKey.class);

			QuickPayVo vo = new QuickPayVo();
			if (null != entity.getMerchantNo()) {
				vo.setMerchantId(entity.getMerchantNo());// 商户的ID
			}
			if(requestBodyMap.get("businessSeqNo") != null && requestBodyMap.get("businessSeqNo") != ""){
				vo.setMerchantOrderNo(requestBodyMap.get("businessSeqNo").toString());// 商户交易号
			}
			if (null != tpdsCustomerAccount && null != tpdsCustomerAccount.getCustomerNo()) {
				vo.setMerchantUserId(tpdsCustomerAccount.getCustomerNo());// 用户号
			}
			if(cha.get("amount") != null && cha.get("amount") != ""){
				vo.setPayAmount(cha.get("amount").toString());// 交易金额
			}
			vo.setRequestTime(DateUtils.getDateStr(new Date(), "yyyyMMddHHmmss"));// 请求时间
			if(requestHeaderMap.get("version") != null && requestHeaderMap.get("version") != ""){
				vo.setVersion(requestHeaderMap.get("version").toString());// 版本号
			}
			vo.setProductCode(ProductType.DEPOSIT_QUICKPAY.getValue());// 产品编码
			if(requestBodyMap.get("ownerName") != null && requestBodyMap.get("ownerName") != ""){
				vo.setOwnerName(requestBodyMap.get("ownerName").toString());// 持卡人姓名
			}
			if (requestBodyMap.get("ownerCertNo") != null && requestBodyMap.get("ownerCertNo") != ""){
				vo.setOwnerCertNo(requestBodyMap.get("ownerCertNo").toString());// 持卡人身份证号
			}
			if (requestBodyMap.get("ownerMobile") != null && requestBodyMap.get("ownerMobile") != ""){
				vo.setOwnerMobile(requestBodyMap.get("ownerMobile").toString());// 持卡人手机号
			}
			if (requestBodyMap.get("bankAccountNo") != null && requestBodyMap.get("bankAccountNo") != ""){
				vo.setBankCardNo(requestBodyMap.get("bankAccountNo").toString());// 银行卡号
			}
			vo.setNotifyUrl(notifyUrlVo.getNotifyUrl());// 通知URL
			vo.setHyAuthCode("");// 授权码 否
			vo.setValidate("");// 卡有效期 否
			vo.setCvv2("");// 卡验证码 否
			vo.setDescription("");// 商品信息 否
			vo.setMerchantUserName("");// 用户名 否
			vo.setRequestUserIp("");// 用户请求IP 否

			logger.info("调用内部的充值接口【认证支付请求】开始-->{}", vo);
			QuickPayMsgVo quickPay =  chargeClient.QuickPay(paymentCommon.getPaymentQuickpayUrl(), vo, tpdsProductKey.getSignKey());
			if(null != quickPay){
				logger.info("调用内部的充值接口【认证支付请求】交易系统返回参数-->{}", "RetCode:"+quickPay.getRetCode()+","+"RetMsg:"+quickPay.getRetMsg()+","+"PaymentId:"+quickPay.getPaymentId()+","+"Token:"+quickPay.getToken()+","+"AuthorizationCode:"+quickPay.getAuthorizationCode());
				JSONObject return_obj = new JSONObject();
				if(null != quickPay && StringUtils.isNotBlank(quickPay.getRetCode()) && StringUtils.equals(quickPay.getRetCode(),"1000")){
					String token = Aes.encryptStr(quickPay.getToken()+","+quickPay.getPaymentId()+","+quickPay.getAuthorizationCode(),Constants.QuickPay.SYSTEM_KEY);
					bodyMap.put("token", token);
				}else{
					bodyMap.put("token", "");
				}
				logger.info("认证支付请求返回参数-->{}", bodyMap);
				if(StringUtils.isNotBlank(quickPay.getRetCode()) && StringUtils.isNotBlank(quickPay.getRetMsg())){
					if(StringUtils.equals(quickPay.getRetCode(),"1000")){
						requestHeaderMap.put("respCode", Code.C0.getValue());
						requestHeaderMap.put("respMsg",Code.C0.getContent());
					}else if(StringUtils.equals(quickPay.getRetCode(),"1002") && quickPay.getRetMsg().indexOf("信息认证未通过") > -1){
						requestHeaderMap.put("respCode", quickPay.getRetCode());
						requestHeaderMap.put("respMsg",quickPay.getRetMsg());
					}else if(StringUtils.equals(quickPay.getRetCode(),"1003")
							|| StringUtils.equals(quickPay.getRetCode(),"3004")
							|| StringUtils.equals(quickPay.getRetCode(),"3013")
							|| StringUtils.equals(quickPay.getRetCode(),"3021")
							|| StringUtils.equals(quickPay.getRetCode(),"4001")
							|| StringUtils.equals(quickPay.getRetCode(),"4300")){
						requestHeaderMap.put("respCode", HyCode.FAIL43000.getValue());
						requestHeaderMap.put("respMsg", HyCode.FAIL43000.getContent());
					}else if(StringUtils.equals(quickPay.getRetCode(),"4105")
							|| StringUtils.equals(quickPay.getRetCode(),"4106")
							|| StringUtils.equals(quickPay.getRetCode(),"4107")){
						requestHeaderMap.put("respCode", HyCode.FAIL44000.getValue());
						requestHeaderMap.put("respMsg", HyCode.FAIL44000.getContent());
					}else{
						requestHeaderMap.put("respCode", quickPay.getRetCode());
						requestHeaderMap.put("respMsg",quickPay.getRetMsg());
					}
				}
				return_obj.put("respHeader", new JSONObject(requestHeaderMap));
				return_obj.put("outBody", new JSONObject(bodyMap));
				logger.info("认证支付请求充值同步返回的参数：{}",return_obj.toString());
				return return_obj.toString();
			}
			return "";
		}catch (Exception e){
			logger.info("调用内部的充值接口【认证支付请求】异常：{}",e);
		}
		return "";
	}

	/**
	 * 校验密码
	 * @param requestHeaderMap
	 * @param businessSeqNo
	 * @return
	 */
	private String checkPassword(Map<String, Object> requestHeaderMap, String businessSeqNo) {
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("businessSeqNo", businessSeqNo); // 业务流水号
		paraMap.put("flag", "1"); // 结果码
		Integer num = tpdsMerchantH5DaoImpl.queryMerchantH5BybusinessSeqNo(paraMap);

		// 若交易密码交易成功，则资金数据入库
		if (num == 0) {
            logger.info("查询校验交易密码失败！-->{}", num);
            JSONObject obj = new JSONObject();
            requestHeaderMap.put("respCode", HyCode.FAIL70000.getValue());
            requestHeaderMap.put("respMsg", HyCode.FAIL70000.getContent());
            obj.put("respHeader", new JSONObject(requestHeaderMap));
            logger.info("查询校验交易密码失败报文体-->{}", obj.toString());
            return obj.toString();
        }
		return null;
	}

	/**
	 * 调用内部B2C网银充值接口
	 * @param requestHeaderMap
	 * @param requestBodyMap
	 * @param cha
	 * @param entity
	 * @param record
	 * @param tpdsCustomerAccount
	 * @param bodyMap
	 * @return
	 * @throws Exception
	 */
	private String b2cChargeMethod(Map<String, Object> requestHeaderMap, Map<String, Object> requestBodyMap, Map cha,
			TpdsMerchantAccount entity, TpdsMerchantMsg record, TpdsCustomerAccount tpdsCustomerAccount,
			Map<String, String> bodyMap) throws Exception{
		String resultBankJson;
		if (null != entity && null != entity.getMerchantNo()) {
			bodyMap.put("merchantId", entity.getMerchantNo());// 商户号
		}
		if (null != tpdsCustomerAccount && null != tpdsCustomerAccount.getCustomerNo()) {
			bodyMap.put("merchantUserId", tpdsCustomerAccount.getCustomerNo());// 用户号
		}else{
			//为空时传  merchantId
			bodyMap.put("merchantUserId", entity.getMerchantNo());
		}

		//移除存管银行接口中不用的字段
		requestBodyMap.remove("bankId");
		requestBodyMap.remove("bankName");
		requestBodyMap.remove("cardType");
		requestBodyMap.remove("ownerName");
		requestBodyMap.remove("ownerCertNo");
		requestBodyMap.remove("ownerMobile");
		requestBodyMap.remove("identifycode");

		//加密字段
		requestBodyMap.put("rType", RType.R02.getValue());
		if (requestBodyMap.get("bankAccountNo") != null && requestBodyMap.get("bankAccountNo") != "") {
			requestBodyMap.put("bankAccountNo",
					KKAES2.encrypt(requestBodyMap.get("bankAccountNo").toString(), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
		}
		if (requestBodyMap.get("secBankaccNo") != null && requestBodyMap.get("secBankaccNo") != "") {
			requestBodyMap.put("secBankaccNo",
					KKAES2.encrypt(requestBodyMap.get("secBankaccNo").toString(), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
		}
		if (cha.get("debitAccountNo") != null && cha.get("debitAccountNo") != "") {
			cha.put("debitAccountNo",
					KKAES2.encrypt(cha.get("debitAccountNo").toString(),signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
		}
		if (cha.get("cebitAccountNo") != null && cha.get("cebitAccountNo") != "") {
			cha.put("cebitAccountNo",
					KKAES2.encrypt(cha.get("cebitAccountNo").toString(),signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
		}
		//转化发送存管银行的接口交易代码
		requestHeaderMap.put("txnType", "T00003");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		requestBodyMap.put("settleDate", sdf.format(DateUtils.getNextDate()).toString());

		JSONObject object_input_bank = new JSONObject();
		object_input_bank.put("reqHeader", new JSONObject(requestHeaderMap));
		object_input_bank.put("inBody", new JSONObject(requestBodyMap));
		logger.info("客户资金第三方网银充值通道报文体-->{}", EncryptUtil.fieldEncrypt(object_input_bank.toString()));
		resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), object_input_bank.toString(), "UTF-8");
		logger.info("客户第三方网银充值银行返回报文-->{}", resultBankJson);

		updateTpdsMerchantMsg(record, resultBankJson, false);
		//B2C网银充值跳转
		return JsonMapperUtil.nonDefaultMapper().toJson(bodyMap);
	}


	/**
	 * 银行渠道充值方法
	 * @param requestHeaderMap
	 * @param requestBodyMap
	 * @param cha
	 * @param record
	 * @return
	 * @throws Exception
	 */
	private String bankChargeMethod(Map<String, Object> requestHeaderMap, Map<String, Object> requestBodyMap, Map cha,
			TpdsMerchantMsg record) throws Exception{
		String resultBankJson;
		//转化交易代码  客户到存管银行
		requestHeaderMap.put("txnType", "T00001");

		requestBodyMap.remove("bankId");
		requestBodyMap.remove("bankName");
		requestBodyMap.remove("cardType");
		requestBodyMap.remove("ownerName");
		requestBodyMap.remove("ownerCertNo");
		requestBodyMap.remove("ownerMobile");
		requestBodyMap.remove("identifycode");

		//加密字段
		if (cha.get("cebitAccountNo") != null && cha.get("cebitAccountNo") != "") {
			cha.put("cebitAccountNo", KKAES2.encrypt(cha.get("cebitAccountNo").toString(),
					signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
		}

		if (requestBodyMap.get("bankAccountNo") != null && requestBodyMap.get("bankAccountNo") != "") {
			requestBodyMap.put("bankAccountNo", KKAES2.encrypt(requestBodyMap.get("bankAccountNo").toString(),
					signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
		}
		if (requestBodyMap.get("secBankaccNo") != null && requestBodyMap.get("secBankaccNo") != "") {
			requestBodyMap.put("secBankaccNo", KKAES2.encrypt(requestBodyMap.get("secBankaccNo").toString(),
					signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
		}

		JSONObject object_input_bank = new JSONObject();
		object_input_bank.put("reqHeader", new JSONObject(requestHeaderMap));
		object_input_bank.put("inBody", new JSONObject(requestBodyMap));
		logger.info("客户资金银行充值通道报文体-->{}", EncryptUtil.fieldEncrypt(object_input_bank.toString()));
		//发送给存管银行
		resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), object_input_bank.toString(), "UTF-8");
		//更新【商户公共信息表】
		updateTpdsMerchantMsg(record, resultBankJson, true);
		logger.info("客户资金银行充值通道【宜宾】返回报文-->{}", resultBankJson);

		return resultBankJson;
	}

	/**
	 * 更新客户公共信息
	 * @param record
	 * @param resultBankJson
	 * @param flg
	 * @throws ParseException
	 */
	private void updateTpdsMerchantMsg(TpdsMerchantMsg record, String resultBankJson, boolean flg) throws ParseException {
		//更新客户公共信息
		TpdsMerchantMsg tpdsMerchantMsg =  new TpdsMerchantMsg();
		tpdsMerchantMsg.setId(record.getId());
		JSONObject updateRe = new JSONObject(resultBankJson);
		JSONObject updateMsg = new JSONObject(updateRe.get("respHeader").toString());
		tpdsMerchantMsg.setServiceSn(updateMsg.get("serviceSn").toString());
		tpdsMerchantMsg.setServiceDate(DateUtils.getDate(updateMsg.get("serviceDate").toString()));
		tpdsMerchantMsg.setServiceTime(updateMsg.get("serviceTime").toString());
		//第三方充值不更新状态码
		if(flg) {
			tpdsMerchantMsg.setRespCode(updateMsg.get("respCode").toString());
			tpdsMerchantMsg.setRespMsg(updateMsg.get("respMsg").toString());
		}
		tpdsMerchantMsgDaoImpl.updateRespCodeBySystemNo(tpdsMerchantMsg);
		logger.info("更新客户公共信息结束");
	}

	/**
	 * 保存客户台账充值记录
	 * @param requestBodyMap
	 * @param cha
	 */
	private void saveTpdsCustomCharge(Map<String, Object> requestBodyMap, Map cha) {
		TpdsCustomCharge charge = new TpdsCustomCharge();

		if(requestBodyMap.get("businessSeqNo") != null && requestBodyMap.get("businessSeqNo") != ""){
			charge.setBusinessSeqNo(requestBodyMap.get("businessSeqNo").toString());
		}
		if(requestBodyMap.get("businessOrderNo") != null && requestBodyMap.get("businessOrderNo") != "") {
			charge.setBusinessOrderNo(requestBodyMap.get("businessOrderNo").toString());
		}
		if(requestBodyMap.get("rType") != null && requestBodyMap.get("rType") != "") {
			charge.setrType(requestBodyMap.get("rType").toString());
		}
		if(requestBodyMap.get("entrustflag") != null && requestBodyMap.get("entrustflag") != "") {
			charge.setEntrustflag(requestBodyMap.get("entrustflag").toString());
		}
		if(cha.get("oderNo") != null && cha.get("oderNo") != "") {
			charge.setOderNo(cha.get("oderNo").toString());
		}
		if(cha.get("debitAccountNo") != null && cha.get("debitAccountNo") != "") {
			charge.setDebitAccountNo(cha.get("debitAccountNo").toString());
		}
		if(cha.get("cebitAccountNo") != null && cha.get("cebitAccountNo") != "") {
			charge.setCebitAccountNo(cha.get("cebitAccountNo").toString());
		}
		if(cha.get("currency") != null && cha.get("currency") != "") {
			charge.setCurrency(cha.get("currency").toString());
		}
		if (cha.get("amount") != null && cha.get("amount") != "") {
			charge.setAmount(new BigDecimal(cha.get("amount").toString()));
		}
		if (cha.get("otherAmounttype") != null && cha.get("otherAmounttype") != "") {
			charge.setOtherAmounttype(cha.get("otherAmounttype").toString());
		}
		if (cha.get("otherAmount") != null && cha.get("otherAmount") != "") {
			charge.setOtherAmount(new BigDecimal(cha.get("otherAmount").toString()));
		}
		if(requestBodyMap.get("payType") != null && requestBodyMap.get("payType") != "") {
			charge.setPayType(requestBodyMap.get("payType").toString());
		}
		if(requestBodyMap.get("busiBranchNo") != null && requestBodyMap.get("busiBranchNo") != "") {
			charge.setBusiBranchNo(requestBodyMap.get("busiBranchNo").toString());
		}
		if(requestBodyMap.get("bankAccountNo") != null && requestBodyMap.get("bankAccountNo") != "") {
			charge.setBankAccountNo(Aes.encryptStr(requestBodyMap.get("bankAccountNo").toString(), Constants.QuickPay.SYSTEM_KEY));
		}
		if(requestBodyMap.get("secBankaccNo") != null && requestBodyMap.get("secBankaccNo") != "") {
			charge.setSecBankaccNo(requestBodyMap.get("secBankaccNo").toString());
		}
		if(requestBodyMap.get("bankId") != null && requestBodyMap.get("bankId") != "") {
			charge.setBankId(requestBodyMap.get("bankId").toString());
		}
		if(requestBodyMap.get("bankName") != null && requestBodyMap.get("bankName") != "") {
			charge.setBankName(requestBodyMap.get("bankName").toString());
		}
		if(requestBodyMap.get("cardType") != null && requestBodyMap.get("cardType") != "") {
			charge.setCardType(requestBodyMap.get("cardType").toString());
		}
		if(requestBodyMap.get("note") != null && requestBodyMap.get("note") != "") {
			charge.setNote(requestBodyMap.get("note").toString());
		}
		tpdsCustomChargeDaoImpl.insert(charge);
		logger.info("客户资金充值【保存客户充值信息表】结束");
	}

	/**
	 * 保存客户公共信息
	 * @param requestHeaderMap
	 * @param requestBodyMap
	 * @param entity
	 * @return
	 * @throws ParseException
	 */
	private TpdsMerchantMsg saveTpdsMerchantMsg(Map<String, Object> requestHeaderMap, Map<String, Object> requestBodyMap,
			TpdsMerchantAccount entity) throws ParseException {
		// 1).保存客户公共信息表
		TpdsMerchantMsg record = new TpdsMerchantMsg();
		if(requestHeaderMap.get("version") != null && requestHeaderMap.get("version") != "") {
			record.setVersionId(requestHeaderMap.get("version").toString());
		}
		if (null != entity) {
			record.setMerchantNo(entity.getMerchantNo());
		}
		if(requestHeaderMap.get("merchantCode") != null && requestHeaderMap.get("merchantCode") != "") {
			record.setSystemNo(requestHeaderMap.get("merchantCode").toString());
		}
		if(requestHeaderMap.get("txnType") != null && requestHeaderMap.get("txnType") != ""){
			record.setTxType(requestHeaderMap.get("txnType").toString());
			record.setTxName(InfaceType.labelOf(requestHeaderMap.get("txnType").toString()));
		}
		if(requestHeaderMap.get("clientSn") != null && requestHeaderMap.get("clientSn") != "") {
			record.setClientSn(requestHeaderMap.get("clientSn").toString());
		}
		if(requestHeaderMap.get("clientDate") != null && requestHeaderMap.get("clientDate") != "") {
			record.setClientDate(DateUtils.getDate(requestHeaderMap.get("clientDate").toString()));
		}
		if(requestHeaderMap.get("clientTime") != null && requestHeaderMap.get("clientTime") != "") {
			record.setClientTime(DateUtils.getStrDate(requestHeaderMap.get("clientTime").toString(), "HHmmss"));
		}
		record.setSettleDate(DateUtils.getNextDate());//当前时间的下一天
		if(requestHeaderMap.get("signTime") != null && requestHeaderMap.get("signTime") != "") {
			record.setSignTime(requestHeaderMap.get("signTime").toString());
		}
		if(requestBodyMap.get("businessSeqNo") != null && requestBodyMap.get("businessSeqNo") != "") {
			record.setBusinessSeqNo(requestBodyMap.get("businessSeqNo").toString());
		}
		tpdsMerchantMsgDaoImpl.insert(record);
		logger.info("客户资金充值【保存客户公共信息表】结束");
		return record;
	}

	/*
	 * 客户资金提现 (non-Javadoc)
	 * 
	 * @see com.heepay.tpds.service.ICustomMoneyAndPrepaidphoneWithDrawService#
	 * customWithDraw(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String customerWithdraw(String requestHeader, String requestBody) throws TException {
		try {

			Map<String, Object> requestHeaderMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestHeader, Map.class);
			Map<String, Object> requestBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestBody, Map.class);
			logger.info("客户资金提现报文头-->{}", requestHeader);
			if(requestBodyMap.get("ownerName") != null && StringUtils.isNotBlank(requestBodyMap.get("ownerName").toString())) {
				requestBody.replaceAll("ownerName", Aes.encryptStr(requestBodyMap.get("ownerName").toString(), Constants.QuickPay.SYSTEM_KEY));
			}
			if(requestBodyMap.get("ownerCertNo") != null && StringUtils.isNotBlank(requestBodyMap.get("ownerCertNo").toString())) {
				requestBody.replaceAll("ownerCertNo", Aes.encryptStr(requestBodyMap.get("ownerCertNo").toString(), Constants.QuickPay.SYSTEM_KEY));
			}
			if(requestBodyMap.get("ownerMobile") != null && StringUtils.isNotBlank(requestBodyMap.get("ownerMobile").toString())) {
				requestBody.replaceAll("ownerMobile", Aes.encryptStr(requestBodyMap.get("ownerMobile").toString(), Constants.QuickPay.SYSTEM_KEY));
			}
			if(requestBodyMap.get("bankAccountNo") != null && StringUtils.isNotBlank(requestBodyMap.get("bankAccountNo").toString())) {
				requestBody.replaceAll("bankAccountNo", Aes.encryptStr(requestBodyMap.get("bankAccountNo").toString(), Constants.QuickPay.SYSTEM_KEY));
			}
			logger.info("客户资金提现报文体-->{}", requestBody);

			String businessSeqNo = requestBodyMap.get("businessSeqNo").toString();
			List accountList = (List) requestBodyMap.get("accountList");
			Map cha = (Map) accountList.get(0);
			Map<String, String> paraMap = new HashMap<>();

			paraMap.put("businessSeqNo", businessSeqNo); // 业务流水号
			paraMap.put("flag", "1"); // 结果码

			// 查询校验交易密码是否成功
			String result = checkPassword(requestHeaderMap, businessSeqNo);
			if (result != null) return result;

			// 1.根据restlet接口传来的提现信息保存
			saveTpdsCustomCharge(requestBodyMap, cha);

			// 2.转送银行 requestHeader : 银行的地址 requestBody : 发送给银行的报文  移除银行报文中不需要的字段
			requestBodyMap.remove("bankId");
			requestBodyMap.remove("bankName");
			requestBodyMap.remove("cardType");
			requestBodyMap.remove("ownerName");
			requestBodyMap.remove("ownerCertNo");
			requestBodyMap.remove("ownerMobile");

			//加密字段
			if(cha.get("debitAccountNo") != null && cha.get("debitAccountNo") != ""){
				cha.put("debitAccountNo",
					KKAES2.encrypt(cha.get("debitAccountNo") + "", signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if (requestBodyMap.get("bankAccountNo") != null && requestBodyMap.get("bankAccountNo") != ""){
				requestBodyMap.put("bankAccountNo",
					KKAES2.encrypt(requestBodyMap.get("bankAccountNo") + "", signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if (requestBodyMap.get("secBankaccNo") != null && requestBodyMap.get("secBankaccNo") != ""){
				requestBodyMap.put("secBankaccNo",
					KKAES2.encrypt(requestBodyMap.get("secBankaccNo") + "", signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}

			//从客户到存管银行的交易代码的转化
			requestHeaderMap.put("txnType", "T00001");

			JSONObject jSONObject_Input_bank = new JSONObject();
			jSONObject_Input_bank.put("reqHeader", new JSONObject(requestHeaderMap));
			jSONObject_Input_bank.put("inBody", new JSONObject(requestBodyMap));

			logger.info("客户资金提现发送存管银行的报文-->{}", EncryptUtil.fieldEncrypt(jSONObject_Input_bank.toString()));
			String resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString(), "UTF-8");
			logger.info("客户资金提现银行返回报文-->{}", resultBankJson);
			return resultBankJson;
		} catch (Exception e) {
			logger.error("客户资金提现异常-->{}", e);
		}
		return "";
	}
}
