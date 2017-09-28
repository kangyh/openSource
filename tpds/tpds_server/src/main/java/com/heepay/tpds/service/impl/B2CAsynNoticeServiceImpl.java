package com.heepay.tpds.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.heepay.date.DateUtils;
import com.heepay.tpds.client.TpdsCommonClient;
import com.heepay.tpds.dao.TpdsCustomerAccountMapper;
import com.heepay.tpds.dao.TpdsMerchantMsgMapper;
import com.heepay.tpds.entity.TpdsCustomerAccount;
import com.heepay.tpds.entity.TpdsMerchantMsg;
import com.heepay.tpds.enums.HyCode;
import com.heepay.tpds.vo.SignkeyCommon;
import com.heepay.utils.security.KKAES2;
import com.heepay.utils.security.SignRSA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.rpc.service.RpcService;
import com.heepay.rpc.tpds.service.B2CAsynNoticeService;
import com.heepay.tpds.dao.TpdsCustomChargeMapper;
import com.heepay.tpds.dao.TpdsMerchantH5Mapper;
import com.heepay.tpds.entity.TpdsCustomCharge;
import com.heepay.tpds.entity.TpdsMerchantH5;
import com.heepay.tpds.enums.Code;
import com.heepay.tpds.util.TpdsDataUtils;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.utils.http.WithoutAuthHttpsClient;
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
@RpcService(name="B2CAsynNoticeServiceImpl",processor=B2CAsynNoticeService.Processor.class)
public class B2CAsynNoticeServiceImpl implements com.heepay.rpc.tpds.service.B2CAsynNoticeService.Iface {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Resource(name = "httpsClient")
	WithoutAuthHttpsClient httpsClient;
	@Resource(name = "tpdsCommonClient")
	TpdsCommonClient tpdsCommonClient;
	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;
	@Resource(name = "signkeyCommon")
	SignkeyCommon signkeyCommon;
	
	@Autowired
	private TpdsMerchantH5Mapper tpdsMerchantH5DaoImpl;
	
	@Autowired
	private TpdsCustomChargeMapper tpdsCustomChargeDaoImpl;

	@Autowired
	private TpdsCustomerAccountMapper tpdsCustomerAccountMapper;

	@Autowired
	private TpdsMerchantMsgMapper tpdsMerchantMsgDaoImpl;

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
			String accNo = "";
			String secBankaccNo = "";
			TreeMap<String,String> inBodyMap = new TreeMap<>(new Comparator<String>(){
				@Override
				public int compare(String o1, String o2) {
					//指定排序器按照key升序排列
					return o1.compareTo(o2);
				}
			});
			if(StringUtils.isNotBlank(requestBodyMap.get("result")) && StringUtils.equals("1000", requestBodyMap.get("result"))){
				inBodyMap.put("respCode", Code.C0.getValue());//原交易响应码
				inBodyMap.put("respMsg", Code.C0.getContent());//原交易响应信息
			}else if(StringUtils.isNotBlank(requestBodyMap.get("result")) && StringUtils.equals("1002", requestBodyMap.get("result"))){
				inBodyMap.put("respCode", HyCode.FAIL00000.getValue());//原交易响应码
				inBodyMap.put("respMsg", HyCode.FAIL00000.getContent());//原交易响应信息
			}

			//更新客户公共信息
			TpdsMerchantMsg updateTpdsMerchantMsg = new TpdsMerchantMsg();
			if(null != requestBodyMap && requestBodyMap.size() > 0){
				if(StringUtils.isNotBlank(requestBodyMap.get("merchantOrderNo"))){
					updateTpdsMerchantMsg.setBusinessSeqNo(requestBodyMap.get("merchantOrderNo"));
				}
				if(StringUtils.isNotBlank(requestBodyMap.get("result")) && StringUtils.equals("1000", requestBodyMap.get("result"))) {
					updateTpdsMerchantMsg.setRespCode(Code.C0.getValue());
					updateTpdsMerchantMsg.setRespMsg(Code.C0.getContent());
				}else{
					updateTpdsMerchantMsg.setRespCode(HyCode.FAIL00000.getValue());
					updateTpdsMerchantMsg.setRespMsg(HyCode.FAIL00000.getContent());

				}
				int count = tpdsMerchantMsgDaoImpl.updateRespCodeByBusinessSeqNo(updateTpdsMerchantMsg);
				logger.info("B2C客户充值异步通知的更新客户公共信息条数-->{}", count);
			}


			logger.info("B2C客户充值异步通知的报文体-->{}", requestBody);
			if(StringUtils.isNotBlank(requestBodyMap.get("result")) && StringUtils.equals("1000", requestBodyMap.get("result"))){
				//给银行和玖财通的异步通知
				JSONObject jSONObject_Input_bank = new JSONObject();
				Map<String, String> map = new HashMap<>();
				if(null != requestBodyMap.get("merchantOrderNo") && StringUtils.isNotBlank(requestBodyMap.get("merchantOrderNo")+"")){
					inBodyMap.put("oldbusinessSeqNo", requestBodyMap.get("merchantOrderNo")+"");
					map.put("businessSeqNo", requestBodyMap.get("merchantOrderNo")+"");
					logger.info("查询客户信息H5的信息参数-->{}", map);
					TpdsMerchantH5 h5 = tpdsMerchantH5DaoImpl.queryMerchantBybusinessSeqNo(map);
					logger.info("返回客户信息H5的信息参数-->{}", h5);
					if(null != h5){
						inBodyMap.put("businessSeqNo", TpdsDataUtils.businessSeqNo(h5.getSystemNo()));
					}
					List<TpdsCustomCharge> chargeList = tpdsCustomChargeDaoImpl.selectByBusinessSeqNo(requestBodyMap.get("merchantOrderNo")+"");
					if(chargeList != null && chargeList.size() > 0){
						TpdsCustomCharge charge = chargeList.get(0);
						logger.info("返回客户充值信息-->{}", charge);
						if(null != charge){
							if(null != charge.getCebitAccountNo()){
								inBodyMap.put("accNo", charge.getCebitAccountNo());
								accNo = charge.getCebitAccountNo();
							}else{
								inBodyMap.put("accNo","");
							}
							TpdsCustomerAccount record = new TpdsCustomerAccount();
							record.setBankAccNo(charge.getCebitAccountNo());
							TpdsCustomerAccount account = tpdsCustomerAccountMapper.selectCustomerNoByMerchantNo(record);
							if(null != account){
								inBodyMap.put("secBankaccNo", account.getSecBankAccNo());
								secBankaccNo = account.getSecBankAccNo();
							}else{
								inBodyMap.put("secBankaccNo","");
							}
						}
					}
				}
				inBodyMap.put("oldTxnType", "KPCZ01");
				inBodyMap.put("dealStatus", "1");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				inBodyMap.put("settleDate", sdf.format(DateUtils.getNextDate()).toString());
				inBodyMap.put("busiBranchNo", tpdsCommonClient.getBusiBranchNo());
				inBodyMap.put("dealAmount", requestBodyMap.get("successAmount"));
				inBodyMap.put("note", "");

				//加签
				String value = requestHeaderMap.get("signTime").toString()+"|"+ SignRSA.getTreeMap(inBodyMap);
				logger.info("B2C网银充值异步通知【玖财通】加签字符串{}",value);
				String signature = SignRSA.addSign(value,this.getClass().getResource("/").getPath()+serverBankInfo.getSignPrivateKey());
				logger.info("B2C网银充值异步通知【玖财通】加密完加签字符串{}",signature);
				requestHeaderMap.put("signature",signature);

				inBodyMap.put("accNo", KKAES2.encrypt(accNo,signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV())+"");
				inBodyMap.put("secBankaccNo", KKAES2.encrypt(secBankaccNo,signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV())+"");

				jSONObject_Input_bank.put("reqHeader", new JSONObject(requestHeaderMap));
				jSONObject_Input_bank.put("inBody", new JSONObject(inBodyMap));
				logger.info("B2C网银充值异步通知【玖财通】报文{}", jSONObject_Input_bank.toString());
				String resultBankJson = httpsClient.send(serverBankInfo.getJctHostIp()+"?ctl=dep&act=charge_back", jSONObject_Input_bank.toString(), "UTF-8");
				logger.info("B2C网银充值异步通知【玖财通】返回结果报文{}", resultBankJson);


				inBodyMap.put("accNo", accNo);
				inBodyMap.put("secBankaccNo", secBankaccNo);
				requestHeaderMap.put("txnType", "BYR001");
				inBodyMap.put("oldTxnType", "T00003");
				value = requestHeaderMap.get("signTime").toString()+"|"+ SignRSA.getTreeMap(inBodyMap);
				logger.info("B2C网银充值异步通知【宜宾】加签字符串{}",value);
				signature = SignRSA.addSign(value,this.getClass().getResource("/").getPath()+serverBankInfo.getSignPrivateKey());
				logger.info("B2C网银充值异步通知【宜宾】加密完加签字符串{}",signature);
				requestHeaderMap.put("signature",signature);

				inBodyMap.put("accNo", KKAES2.encrypt(accNo,signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV())+"");
				inBodyMap.put("secBankaccNo", KKAES2.encrypt(secBankaccNo,signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV())+"");

				jSONObject_Input_bank.put("reqHeader", new JSONObject(requestHeaderMap));
				jSONObject_Input_bank.put("inBody", new JSONObject(inBodyMap));
				logger.info("B2C网银充值异步通知【宜宾】报文{}", jSONObject_Input_bank.toString());
				resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString(), "UTF-8");
				logger.info("B2C网银充值异步通知【宜宾】返回结果报文{}", resultBankJson);
				//给交易系统的返回参数
				return "ok";
			}
		} catch (Exception e) {
			logger.error("客户充值异步通知异常-->{}", e);
		}
		return null;
	}
	
}
