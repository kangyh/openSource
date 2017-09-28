package com.heepay.tpds.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.tpds.enums.HyCode;
import com.heepay.utils.security.EncryptUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.rpc.service.RpcService;
import com.heepay.rpc.tpds.model.DepositMsgVO;
import com.heepay.rpc.tpds.service.BankWQService;
import com.heepay.tpds.dao.TpdsBankMsgMapper;
import com.heepay.tpds.dao.TpdsBankWithdrawMapper;
import com.heepay.tpds.dao.TpdsCutDayMapper;
import com.heepay.tpds.dao.TpdsMerchantAccountMapper;
import com.heepay.tpds.dao.TpdsMerchantH5Mapper;
import com.heepay.tpds.entity.TpdsBankMsg;
import com.heepay.tpds.entity.TpdsBankWithdraw;
import com.heepay.tpds.entity.TpdsCutDay;
import com.heepay.tpds.entity.TpdsMerchantAccount;
import com.heepay.tpds.enums.BankId;
import com.heepay.tpds.enums.Code;
import com.heepay.tpds.enums.DepositStatus;
import com.heepay.tpds.util.HttpClientUtils;
import com.heepay.tpds.vo.DepositWithdrawVo;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.tpds.vo.SignkeyCommon;
import com.heepay.utils.security.KKAES2;
/**
 * 
 *
 * 描    述：监管银行的提现和交易状态查询
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
@RpcService(name="BankWQServiceImpl",processor=BankWQService.Processor.class)
public class BankWQServiceImpl implements com.heepay.rpc.tpds.service.BankWQService.Iface {

	@Autowired
	private TpdsBankMsgMapper tpdsBankMsgDaoImpl;
	
	@Autowired
	private TpdsBankWithdrawMapper tpdsBankWithdrawDaoImpl;
	
	@Autowired
	private WithDrawServiceImpl withDrawServiceImpl;
	
	@Autowired
	private TpdsCutDayMapper tpdsCutDayDaoImpl;
	
	@Autowired
	private TpdsMerchantAccountMapper tpdsMerchantAccountDaoImpl;

	private static final Logger logger = LogManager.getLogger();
	
	/*
	 * 监管银行提现
	 * (non-Javadoc)
	 * @see com.heepay.rpc.tpds.service.BankWQService.Iface#bankWithdraw(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String bankWithdraw(String requestHeader, String requestBody) throws TException {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> requestHeaderMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestHeader, Map.class);
			Map<String, Object> requestBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestBody, Map.class);
			logger.info("监管银行提现的报文头-->{}",requestHeader);
			requestBody = requestBody.replaceAll(requestBodyMap.get("bankAccountNo").toString(),Aes.encryptStr(requestBodyMap.get("bankAccountNo").toString(), Constants.QuickPay.SYSTEM_KEY));
			requestBody = requestBody.replaceAll(requestBodyMap.get("certNo").toString(),Aes.encryptStr(requestBodyMap.get("certNo").toString(), Constants.QuickPay.SYSTEM_KEY));
			logger.info("监管银行提现的报文体-->{}", EncryptUtil.fieldEncrypt(requestBody));

			/*
			 * 1.生成商户的台账记录
			 */
			//1).首先生成银行的公共信息
			saveTpdsBankMsg(requestHeaderMap, requestBodyMap);

			//2).监管银行资金清算（提现）
			TpdsBankWithdraw withdraw = saveTpdsBankWithdraw(requestBodyMap);
			//2.监管银行提现
			requestBodyMap.put("systemNo", requestHeaderMap.get("merchantCode"));
			DepositWithdrawVo withdrawVo=new DepositWithdrawVo();
			withdrawVo.setBusinessSeqNo(withdraw.getBusinessSeqNo());
			withdrawVo.setAmount(withdraw.getAmount().toString());
			withdrawVo.setSystemNo(requestHeaderMap.get("merchantCode").toString());
			withdrawVo.setBankAccountNo(withdraw.getBankAccountNo());
			DepositMsgVO result = withDrawServiceImpl.depositWithDraw(JsonMapperUtil.nonDefaultMapper().toJson(withdrawVo));
			String withDraw=JsonMapperUtil.nonDefaultMapper().toJson(result);
			logger.info("监管银行提现调用内部接口返回参数->{}",withDraw);
			JSONObject jSONObject_Input_bank = new JSONObject();
			if(StringUtils.isNotBlank(withDraw)){
				Map<String, Object> withDrawMap = JsonMapperUtil.nonEmptyMapper().fromJson(withDraw, Map.class);
				//  requestHeader : 银行的地址    requestBody : 发送给银行的报文
				Map<String, Object> bodyMap = new HashMap<>();
				if(StringUtils.equals(withDrawMap.get("code").toString(), DepositStatus.SUCCESS.getValue()+"")){
					bodyMap.put("dealAmount", requestBodyMap.get("amount").toString());
					//bodyMap.put("clientSn", requestBodyMap.get("msg").toString());
					TpdsMerchantAccount entity = new TpdsMerchantAccount();
					entity.setSystemNo(requestHeaderMap.get("merchantCode").toString());
					entity = tpdsMerchantAccountDaoImpl.selectBySysNoAndBankNo(entity);
					TpdsCutDay tpdsCutDay = new TpdsCutDay();
					tpdsCutDay.setBusiNo(entity.getBankAccountBranch());
					tpdsCutDay = tpdsCutDayDaoImpl.selectCutTypeByCutType(tpdsCutDay);
					if(null != tpdsCutDay){
						//比较日切点
						if(Integer.valueOf(tpdsCutDay.getCutTime()).compareTo(Integer.valueOf(requestHeaderMap.get("clientTime")+""))>0){
							bodyMap.put("settleDate", DateUtils.getDayAdd(new Date(), 1));
						}else{
							bodyMap.put("settleDate", DateUtils.getDayAdd(new Date(), 2));
						}
					}
					requestHeaderMap.put("respCode", Code.C0.getValue());
					requestHeaderMap.put("respMsg", Code.C0.getContent());
					logger.info("监管银行提现-->{}",bodyMap);
				}else{
					if(StringUtils.isNotBlank(withDrawMap.get("code").toString())){
						if(StringUtils.equals("2000",withDrawMap.get("code").toString())
								|| StringUtils.equals("3001",withDrawMap.get("code").toString())
								|| StringUtils.equals("4000",withDrawMap.get("code").toString())){
							requestHeaderMap.put("respCode", HyCode.FAIL44000.getValue());
							requestHeaderMap.put("respMsg", HyCode.FAIL44000.getContent());
						}else if(StringUtils.equals("1000",withDrawMap.get("code").toString())
								|| StringUtils.equals("4000",withDrawMap.get("code").toString())
								|| StringUtils.equals("4001",withDrawMap.get("code").toString())
								|| StringUtils.equals("4002",withDrawMap.get("code").toString())
								|| StringUtils.equals("4003",withDrawMap.get("code").toString())
								|| StringUtils.equals("4004",withDrawMap.get("code").toString())){
							requestHeaderMap.put("respCode", withDrawMap.get("code").toString());
							requestHeaderMap.put("respMsg", withDrawMap.get("msg").toString());
						}else if(StringUtils.equals("-1",withDrawMap.get("code").toString())){
							requestHeaderMap.put("respCode", HyCode.SUCC10000.getValue());
							requestHeaderMap.put("respMsg", HyCode.SUCC10000.getContent());
						}else{
							requestHeaderMap.put("respCode", HyCode.DING10000.getValue());
							requestHeaderMap.put("respMsg", HyCode.DING10000.getContent());
						}
					}
					logger.info("监管银行提现失败！-->{}",requestHeaderMap);
				}
				//更新提现信息
				updateBankWithdraw(withdraw,withDrawMap);
				jSONObject_Input_bank.put("respHeader", new JSONObject(requestHeaderMap));
				jSONObject_Input_bank.put("outBody", new JSONObject(bodyMap));
				logger.info("监管银行提现返回报文-->{}",jSONObject_Input_bank.toString());
			}
			return jSONObject_Input_bank.toString();
		} catch (Exception e) {
			logger.error("监管银行提现异常-->{}",e);
		}
		return "";
	}

	/**
	 * @方法说明：更新提现信息
	 * @时间： 2017/8/7 11:44
	 * @创建人：wangdong
	 */
	private void updateBankWithdraw(TpdsBankWithdraw withdraw, Map<String, Object> withDrawMap) {
		try{
			TpdsBankWithdraw updateBankWithdraw = new TpdsBankWithdraw();
			updateBankWithdraw.setCusId(withdraw.getCusId());
			if(null != withDrawMap && StringUtils.isNotBlank(withDrawMap.get("code").toString())
					&& StringUtils.equals(withDrawMap.get("code").toString(), DepositStatus.SUCCESS.getValue()+"")){
				updateBankWithdraw.setNote("提现成功,"+withdraw.getNote());
			}else{
				updateBankWithdraw.setNote(withDrawMap.get("msg")+","+withdraw.getNote());
			}
			tpdsBankWithdrawDaoImpl.updateByPrimaryKeySelective(updateBankWithdraw);
		}catch (Exception e){
			logger.error("更新提现信息异常-->{}",e);
		}
	}

	/**
	 * 保存银行提现信息
	 * @param requestBodyMap
	 * @return
	 */
	private TpdsBankWithdraw saveTpdsBankWithdraw(Map<String, Object> requestBodyMap) {
		TpdsBankWithdraw withdraw = new TpdsBankWithdraw();
		if(requestBodyMap.get("businessSeqNo") != null && requestBodyMap.get("businessSeqNo") != ""){
			withdraw.setBusinessSeqNo(requestBodyMap.get("businessSeqNo").toString());
		}
		if(requestBodyMap.get("rType") != null && requestBodyMap.get("rType") != ""){
			withdraw.setrType(requestBodyMap.get("rType").toString());
		}
		if(requestBodyMap.get("currency") != null && requestBodyMap.get("currency") != ""){
			withdraw.setCurrency(requestBodyMap.get("currency").toString());
		}
		if(requestBodyMap.get("amount") != null && requestBodyMap.get("amount") != ""){
			withdraw.setAmount(new BigDecimal(requestBodyMap.get("amount").toString()));
		}
		if(requestBodyMap.get("bankAccountNo") != null && requestBodyMap.get("bankAccountNo") != ""){
			withdraw.setBankAccountNo(Aes.encryptStr(requestBodyMap.get("bankAccountNo").toString(), Constants.QuickPay.SYSTEM_KEY));
		}
		if(requestBodyMap.get("bankBranch") != null && requestBodyMap.get("bankBranch") != ""){
			withdraw.setBusiBranchNo(requestBodyMap.get("bankBranch").toString());
		}
		if(requestBodyMap.get("note") != null && requestBodyMap.get("note") != ""){
			withdraw.setNote(requestBodyMap.get("note").toString());
		}
		logger.info("监管银行提现【保存银行资金清算表】开始-->{}",withdraw);
		tpdsBankWithdrawDaoImpl.insert(withdraw);
		logger.info("监管银行提现【保存银行资金清算表】结束-->{}",withdraw);
		return withdraw;
	}

	/**
	 * 保存银行提现的公共信息
	 * @param requestHeaderMap
	 * @param requestBodyMap
	 * @throws ParseException
	 */
	private void saveTpdsBankMsg(Map<String, Object> requestHeaderMap, Map<String, Object> requestBodyMap) throws ParseException {
		TpdsBankMsg bankMsg = new TpdsBankMsg();
		if(requestHeaderMap.get("version") != null && requestHeaderMap.get("version") != ""){
            bankMsg.setVersionId(requestHeaderMap.get("version").toString());
        }
		bankMsg.setBankNo(BankId.B313.getValue());
		if(requestHeaderMap.get("clientSn") != null && requestHeaderMap.get("clientSn") != ""){
            bankMsg.setClientSn(requestHeaderMap.get("clientSn").toString());
        }
		if(requestHeaderMap.get("clientDate") != null && requestHeaderMap.get("clientDate") != ""){
            bankMsg.setClientDate(DateUtils.getDate(requestHeaderMap.get("clientDate").toString()));
        }
		if(requestHeaderMap.get("clientTime") != null && requestHeaderMap.get("clientTime") != ""){
            bankMsg.setClientTime(DateUtils.getStrDate(requestHeaderMap.get("clientTime").toString(),"HHmmss"));
        }
		if(requestHeaderMap.get("signTime") != null && requestHeaderMap.get("signTime") != "") {
			bankMsg.setSignTime(requestHeaderMap.get("signTime").toString());
		}
		if(requestBodyMap.get("businessSeqNo") != null && requestBodyMap.get("businessSeqNo") != "") {
			bankMsg.setBusinessSeqNo(requestBodyMap.get("businessSeqNo").toString());
		}
		logger.info("监管银行提现【保存银行的公共信息】开始-->{}",bankMsg);
		tpdsBankMsgDaoImpl.insert(bankMsg);
		logger.info("监管银行提现【保存银行的公共信息】结束-->{}",bankMsg);
	}

	/*
	 * 监管银行交易状态查询
	 * (non-Javadoc)
	 * @see com.heepay.rpc.tpds.service.BankWQService.Iface#bankStatusQuery(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String bankStatusQuery(String requestHeader, String requestBody) throws TException {
		// TODO Auto-generated method stub
		try {
			logger.info("监管银行交易状态查询报文头-->{}",requestHeader);
			logger.info("监管银行交易状态查询报文体-->{}",requestBody);
			
			Map<String, Object> requestHeaderMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestHeader, Map.class);
			Map<String, Object> requestBodyMap = JsonMapperUtil.nonEmptyMapper().fromJson(requestBody, Map.class);
			//1.调用内部接口
			DepositWithdrawVo withdrawVo=new DepositWithdrawVo();
			withdrawVo.setSystemNo(requestHeaderMap.get("merchantCode").toString());
			withdrawVo.setBusinessSeqNo(requestBodyMap.get("oldbusinessSeqNo").toString());
			DepositMsgVO depositMsgVO = withDrawServiceImpl.depositWithDrawQuery(JsonMapperUtil.nonDefaultMapper().toJson(withdrawVo));
			logger.info("监管银行交易状态查询返回结果-->{}",depositMsgVO);
			Map<String, Object> bodyMap = new HashMap<>();
			if(null != depositMsgVO && depositMsgVO.getCode() == DepositStatus.SUCCESS.getValue()){
				bodyMap.put("oldbusinessSeqNo", requestBodyMap.get("oldbusinessSeqNo").toString());
				bodyMap.put("dealStatus", 1);
				bodyMap.put("respCode", Code.C0.getValue());
				bodyMap.put("respMsg", Code.C0.getContent());
				bodyMap.put("accountNo", "");
				bodyMap.put("secBankaccNo", "");
				bodyMap.put("objectaccNo", "");
				bodyMap.put("dealAmount", "");
				bodyMap.put("settleDate", "");
			}else{
				if(StringUtils.isNotBlank(String.valueOf(depositMsgVO.getCode())) && StringUtils.equals("4000",String.valueOf(depositMsgVO.getCode()))){
					bodyMap.put("respCode", HyCode.DING10000.getValue());
					bodyMap.put("respMsg", HyCode.DING10000.getContent());
				}else if(StringUtils.equals("1000",String.valueOf(depositMsgVO.getCode()))
						|| StringUtils.equals("2000",String.valueOf(depositMsgVO.getCode()))
						|| StringUtils.equals("3000",String.valueOf(depositMsgVO.getCode()))){
					bodyMap.put("respCode", String.valueOf(depositMsgVO.getCode()));
					bodyMap.put("respMsg", String.valueOf(depositMsgVO.getMsg()));
				}else{
					bodyMap.put("respCode", HyCode.DING10000.getValue());
					bodyMap.put("respMsg", HyCode.DING10000.getContent());
				}
				bodyMap.put("dealStatus", 0);
			}
			logger.info("监管银行交易状态查询应答报文体-->{}",bodyMap);
			
			JSONObject jSONObject_Input_bank = new JSONObject();
			jSONObject_Input_bank.put("respHeader", new JSONObject(requestHeader));
			jSONObject_Input_bank.put("outBody", new JSONObject(bodyMap));
			logger.info("监管银行交易状态查询-->{}",EncryptUtil.fieldEncrypt(jSONObject_Input_bank.toString()));
			return jSONObject_Input_bank.toString();
		} catch (Exception e) {
			logger.error("监管银行交易状态查询异常-->{}",e);
		}
		return "";
	}
}
