package com.heepay.tpds.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.Encodes;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.CommonStatus;
import com.heepay.tpds.util.FastDFSUtils;
import com.heepay.rpc.service.RpcService;
import com.heepay.rpc.tpds.service.DataInfoSyncService;
import com.heepay.tpds.dao.TpdsCustomerAccountMapper;
import com.heepay.tpds.dao.TpdsCustomerMapper;
import com.heepay.tpds.dao.TpdsObjectDetailMapper;
import com.heepay.tpds.dao.TpdsObjectMakeMapper;
import com.heepay.tpds.entity.TpdsCustomer;
import com.heepay.tpds.entity.TpdsCustomerAccount;
import com.heepay.tpds.entity.TpdsObjectDetail;
import com.heepay.tpds.entity.TpdsObjectMake;
import com.heepay.tpds.enums.InfaceType;
import com.heepay.tpds.service.SysNo;
import com.heepay.tpds.util.HttpClientUtils;
import com.heepay.tpds.util.TpdsDataUtils;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.tpds.vo.SignkeyCommon;
import com.heepay.utils.http.WithoutAuthHttpsClient;
import com.heepay.utils.security.KKAES2;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月15日 下午5:56:10
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
@RpcService(name="DataInfoSyncServiceImpl",processor=DataInfoSyncService.Processor.class)
public class DataInfoSyncServiceImpl implements com.heepay.rpc.tpds.service.DataInfoSyncService.Iface {
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	TpdsCustomerMapper tpdsCustomerDao;
	@Autowired
	TpdsCustomerAccountMapper tpdsCustomerAccountDao;
	@Autowired
	TpdsObjectMakeMapper tpdsObjectMakeDao;
	@Autowired
	TpdsObjectDetailMapper tpdsObjectDetailDao;
	@Autowired
	ConfigServiceImpl configServiceImpl;
	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;
	@Resource(name = "signkeyCommon")
	SignkeyCommon signkeyCommon;
	@Resource(name="httpsClient")
	WithoutAuthHttpsClient httpsClient;
	/**
	 * 标的信息同步
	 */
	@SysNo
	@Transactional
	@Override
	public String setBidInfoSync(String reqHeader, String inBody) throws TException {
		// TODO Auto-generated method stub
		//校验 return_type oder_no
		JSONObject jSONObject_Input_InBody = new JSONObject(inBody);
		JSONObject jSONObject_output = null;
		JSONObject jSONObject_Input_bank = new JSONObject();
		jSONObject_Input_bank.put("reqHeader", new JSONObject(reqHeader));
		jSONObject_Input_bank.put("inBody", new JSONObject(inBody));
		TpdsObjectMake paramTpdsObjectMake= new TpdsObjectMake();
		List<TpdsObjectDetail> paramTpdsObjectDetailList = new ArrayList<TpdsObjectDetail>();	
		String resultBankJson="";
		Boolean error = false;
		setTpdsObjectMake(paramTpdsObjectMake,paramTpdsObjectDetailList,jSONObject_Input_InBody);
		//调用银行接口
		try {
			bidInfoAESEncrypt(jSONObject_Input_bank.getJSONObject("inBody")); //相关字段加密处理
			jSONObject_Input_bank.getJSONObject("reqHeader").put("txnType", "O00001");
			resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString(), "UTF-8");
			jSONObject_output = new JSONObject(resultBankJson);
			//解密处理
			if (jSONObject_output.getJSONObject("respHeader").getString("respCode").toString().equals("P2P0000")){
				jSONObject_output.getJSONObject("outBody").put("objectaccNo", KKAES2.decrypt(String.valueOf(jSONObject_output.getJSONObject("outBody").get("objectaccNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
				paramTpdsObjectMake.setObjectaccNo(String.valueOf(((JSONObject)jSONObject_output.get("outBody")).get("objectaccNo")));
			}
		} catch (Exception e) {
			error = true;
			logger.error("method:setBidInfoSync,errorinfo:"+e.getMessage());
		}finally
		{
			tpdsObjectMakeDao.insert(paramTpdsObjectMake);
			for (int index=0;index<paramTpdsObjectDetailList.size();index++)
			{
				paramTpdsObjectDetailList.get(index).setObjectId(String.valueOf(paramTpdsObjectMake.getObjId()));
				tpdsObjectDetailDao.insert(paramTpdsObjectDetailList.get(index));
			}
		}
		return error==false?jSONObject_output.toString():"{}";
	}
	/**
	 * 客户信息同步
	 */
	@SysNo
	@Override
	public String setCustomerInfoSync(String reqHeader, String inBody) throws TException {
		// TODO Auto-generated method stub
		//对type role cert_type bind_flag bind_type national 做数据校验
		JSONObject jSONObject_Input_reqHeader = new JSONObject(reqHeader);
		JSONObject jSONObject_Input_InBody = new JSONObject(inBody);
		JSONObject jSONObject_Input_bank = new JSONObject();
		jSONObject_Input_bank.put("reqHeader", jSONObject_Input_reqHeader);
		jSONObject_Input_bank.put("inBody", jSONObject_Input_InBody);
		JSONObject jSONObject_output =null;
		TpdsCustomer tpdsCustomer = null;
		String resultBankJson="";
		TpdsCustomerAccount paramTpdsCustomerAccount = new TpdsCustomerAccount();
		//图片转存
		String certFImage = null;
		String certBImage = null;
		tpdsCustomer = new TpdsCustomer();
		try {
			setTpdsCustomerObject(tpdsCustomer,jSONObject_Input_InBody,jSONObject_Input_reqHeader); //设置实体信息
			String fdfs_client = getClass().getResource("/").getFile().toString()+"fdfs_client.conf";
			if(jSONObject_Input_InBody.get("certFImage")!=null && StringUtil.notBlank(jSONObject_Input_InBody.get("certFImage").toString()))
			{
				certFImage = FastDFSUtils.uploadPic(Encodes.decodeBase64(jSONObject_Input_InBody.getString("certFImage")), "certFImage.jpg", (long) 50000,fdfs_client);
			}
			if (jSONObject_Input_InBody.get("certBImage") !=null && StringUtil.notBlank(jSONObject_Input_InBody.get("certBImage").toString()))
			{
				certBImage = FastDFSUtils.uploadPic(Encodes.decodeBase64(jSONObject_Input_InBody.getString("certBImage")), "certBImage.jpg", (long)50000,fdfs_client);
			}
			tpdsCustomer.setCertFimage(certFImage); 
			tpdsCustomer.setCertBimage(certBImage);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("method:setCustomerInfoSync,errorinfo:"+e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("method:setCustomerInfoSync,errorinfo:"+e.getMessage());
		}
		
		//调用银行接口
		try {
			jSONObject_Input_reqHeader.put("txnType", "U00001");
			customerInfoAESEncrypt(jSONObject_Input_InBody); //相关字段加密处理
			tpdsCustomerDao.insert(tpdsCustomer); //插入客户表	
			String merchantNo = new JSONObject(configServiceImpl.checksysNo(jSONObject_Input_reqHeader.getString("merchantCode"))).getString("merchantNo").toString();
			paramTpdsCustomerAccount.setMerchantNo(merchantNo);
			paramTpdsCustomerAccount.setCustomerNo(tpdsCustomer.getCustomerNo());
			paramTpdsCustomerAccount.setAccNo(tpdsCustomer.getAccNo());
			paramTpdsCustomerAccount.setCertNo(tpdsCustomer.getCertNo());
			paramTpdsCustomerAccount.setCreateTime(new Date());
			paramTpdsCustomerAccount.setUpdateTime(new Date());
			paramTpdsCustomerAccount.setStatus(CommonStatus.DISABLE.getValue());
			tpdsCustomerAccountDao.insert(paramTpdsCustomerAccount)	; //插入客户-台账表
			resultBankJson = httpsClient.send(serverBankInfo.getBankServerAddr(), jSONObject_Input_bank.toString(), "UTF-8");
			jSONObject_output = new JSONObject(resultBankJson);   //出错校验
			if (jSONObject_output.getJSONObject("respHeader").getString("respCode").toString().equals("P2P0000")){
				//分别对字段 accNo ，secBankaccNo进行解密操作
				TpdsCustomerAccount paramTpdsCustomerAccountForUpdate = new TpdsCustomerAccount();
				jSONObject_output.getJSONObject("outBody").put("accNo", KKAES2.decrypt(String.valueOf(jSONObject_output.getJSONObject("outBody").get("accNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
				paramTpdsCustomerAccountForUpdate.setBankAccNo(String.valueOf(((JSONObject)jSONObject_output.get("outBody")).get("accNo")));
				paramTpdsCustomerAccountForUpdate.setStatus(CommonStatus.ENABLE.getValue());
				paramTpdsCustomerAccountForUpdate.setUpdateTime(new Date());
				paramTpdsCustomerAccountForUpdate.setId(paramTpdsCustomerAccount.getId());
				tpdsCustomerAccountDao.updateByPrimaryKeySelective(paramTpdsCustomerAccountForUpdate);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("method:setCustomerInfoSync,note:调用银行接口,errorinfo:"+e.getMessage());
		}
		return jSONObject_output==null?"":jSONObject_output.toString();
	}
	/**
	 * 标的信息同步向银行传输时的加密字段
	 * @param inBody
	 */
	public void bidInfoAESEncrypt(JSONObject inBody){
		try {
			if ((inBody.get("objectId") != null)&&(String.valueOf(inBody.get("objectId")).length()>0)){
				inBody.put("objectId",KKAES2.encrypt(String.valueOf(inBody.get("objectId")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("objectName") != null)&&(String.valueOf(inBody.get("objectName")).length()>0)){
				inBody.put("objectName",KKAES2.encrypt(String.valueOf(inBody.get("objectName")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("customerId") != null)&&(String.valueOf(inBody.get("customerId")).length()>0)){
				inBody.put("customerId",KKAES2.encrypt(String.valueOf(inBody.get("customerId")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 客户信息同步向银行传输时的 加密字段
	 * @param inBody
	 */
	public void customerInfoAESEncrypt(JSONObject inBody){
		try {
			if ((inBody.get("customerId") != null)&&(String.valueOf(inBody.get("customerId")).length()>0)){
				inBody.put("customerId",KKAES2.encrypt(String.valueOf(inBody.get("customerId")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("username") != null)&&(String.valueOf(inBody.get("username")).length()>0)){
				inBody.put("username",KKAES2.encrypt(String.valueOf(inBody.get("username")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("certNo") != null)&&(String.valueOf(inBody.get("certNo")).length()>0)){
				inBody.put("certNo",KKAES2.encrypt(String.valueOf(inBody.get("certNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("certInfo") != null)&&(String.valueOf(inBody.get("certInfo")).length()>0)){
				inBody.put("certInfo",KKAES2.encrypt(String.valueOf(inBody.get("certInfo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("address") != null)&&(String.valueOf(inBody.get("address")).length()>0)){
				inBody.put("address",KKAES2.encrypt(String.valueOf(inBody.get("address")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("phoneNo") != null)&&(String.valueOf(inBody.get("phoneNo")).length()>0)){
				inBody.put("phoneNo",KKAES2.encrypt(String.valueOf(inBody.get("phoneNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("busiLiceNo") != null)&&(String.valueOf(inBody.get("busiLiceNo")).length()>0)){
				inBody.put("busiLiceNo",KKAES2.encrypt(String.valueOf(inBody.get("busiLiceNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("busiLiceDir") != null)&&(String.valueOf(inBody.get("busiLiceDir")).length()>0)){
				inBody.put("busiLiceDir",KKAES2.encrypt(String.valueOf(inBody.get("busiLiceDir")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("orgCodeNo") != null)&&(String.valueOf(inBody.get("orgCodeNo")).length()>0)){
				inBody.put("orgCodeNo",KKAES2.encrypt(String.valueOf(inBody.get("orgCodeNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("orgCodeDir") != null)&&(String.valueOf(inBody.get("orgCodeDir")).length()>0)){
				inBody.put("orgCodeDir",KKAES2.encrypt(String.valueOf(inBody.get("orgCodeDir")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("taxRegisNo") != null)&&(String.valueOf(inBody.get("taxRegisNo")).length()>0)){
				inBody.put("taxRegisNo",KKAES2.encrypt(String.valueOf(inBody.get("taxRegisNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("taxRegisDir") != null)&&(String.valueOf(inBody.get("taxRegisDir")).length()>0)){
				inBody.put("taxRegisDir",KKAES2.encrypt(String.valueOf(inBody.get("taxRegisDir")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("uniSocCreCode") != null)&&(String.valueOf(inBody.get("uniSocCreCode")).length()>0)){
				inBody.put("uniSocCreCode",KKAES2.encrypt(String.valueOf(inBody.get("uniSocCreCode")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("uniSocCreDir") != null)&&(String.valueOf(inBody.get("uniSocCreDir")).length()>0)){
				inBody.put("uniSocCreDir",KKAES2.encrypt(String.valueOf(inBody.get("uniSocCreDir")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("oldbankAccountNo") != null)&&(String.valueOf(inBody.get("oldbankAccountNo")).length()>0)){
				inBody.put("oldbankAccountNo",KKAES2.encrypt(String.valueOf(inBody.get("oldbankAccountNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("bankAccountNo") != null)&&(String.valueOf(inBody.get("bankAccountNo")).length()>0)){
				inBody.put("bankAccountNo",KKAES2.encrypt(String.valueOf(inBody.get("bankAccountNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			if ((inBody.get("bankAccountName") != null)&&(String.valueOf(inBody.get("bankAccountName")).length()>0)){
				inBody.put("bankAccountName",KKAES2.encrypt(String.valueOf(inBody.get("bankAccountName")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}
			
			if ((inBody.get("bankAccountTelNo") != null)&&(String.valueOf(inBody.get("bankAccountTelNo")).length()>0)){
				inBody.put("bankAccountTelNo",KKAES2.encrypt(String.valueOf(inBody.get("bankAccountTelNo")), signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV()));
			}		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	/**
	 * 设置标的信息实体变量
	 * @param paramTpdsObjectMake
	 * @param paramTpdsObjectDetailList
	 * @param jSONObject_Input_InBody
	 */
	public void setTpdsObjectMake(TpdsObjectMake paramTpdsObjectMake,List<TpdsObjectDetail> paramTpdsObjectDetailList,JSONObject jSONObject_Input_InBody)
	{
		try
		{
		//校验 totalAmount 为数值型
		paramTpdsObjectMake.setBusinessSeqNo(jSONObject_Input_InBody.get("businessSeqNo")==null?"":jSONObject_Input_InBody.getString("businessSeqNo"));
		paramTpdsObjectMake.setBusiTradeType(jSONObject_Input_InBody.get("busiTradeType")==null?"":jSONObject_Input_InBody.getString("busiTradeType"));
		paramTpdsObjectMake.setObjectId(jSONObject_Input_InBody.get("objectId")==null?"":jSONObject_Input_InBody.getString("objectId"));
		paramTpdsObjectMake.setObjectName(jSONObject_Input_InBody.get("objectName")==null?"":jSONObject_Input_InBody.getString("objectName"));
		paramTpdsObjectMake.setStatus(jSONObject_Input_InBody.get("projectStatus")==null?"":jSONObject_Input_InBody.getString("projectStatus")); 
		paramTpdsObjectMake.setTotalAmount(new BigDecimal(jSONObject_Input_InBody.getDouble("totalAmount")));
		paramTpdsObjectMake.setInterestRate(jSONObject_Input_InBody.getString("interestRate"));
		paramTpdsObjectMake.setReturnType(jSONObject_Input_InBody.get("returnType")==null?"":jSONObject_Input_InBody.getString("returnType"));  //发布方式
		JSONArray jsonArray = null;
		try{
			jsonArray = jSONObject_Input_InBody.getJSONArray("returnInfoList");
		}catch(Exception ex)
		{
			logger.info("returnInfoList 为空 或不正确 所以不解析");
		}	
		TpdsObjectDetail paramTpdsObjectDetail = null;
		for (int index=0;jsonArray !=null && index<jsonArray.length();index++)
		{
			paramTpdsObjectDetail = new TpdsObjectDetail();
			paramTpdsObjectDetail.setObjectId(jSONObject_Input_InBody.getString("objectId"));
			paramTpdsObjectDetail.setOderNo(jsonArray.getJSONObject(index).getString("oderNo"));
			paramTpdsObjectDetail.setReturnNo(jsonArray.getJSONObject(index).getString("returnNo"));
			paramTpdsObjectDetail.setReturnDate(new Date());
			paramTpdsObjectDetailList.add(paramTpdsObjectDetail);
		}
	
		paramTpdsObjectMake.setCustomerId(jSONObject_Input_InBody.getString("customerId"));
		paramTpdsObjectMake.setNote(jSONObject_Input_InBody.getString("note"));
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	/**
	 * 设置客户信息同步的实体变量
	 * @param tpdsCustomer
	 * @param jSONObject_Input_InBody
	 * @param jSONObject_Input_reqHeader
	 */
	public void setTpdsCustomerObject(TpdsCustomer tpdsCustomer,JSONObject jSONObject_Input_InBody,JSONObject jSONObject_Input_reqHeader)
	{
		
		tpdsCustomer.setCustomerNo(jSONObject_Input_InBody.get("customerId")==null?"":jSONObject_Input_InBody.getString("customerId")); //会员编号
		tpdsCustomer.setType(jSONObject_Input_InBody.get("ctype")==null?"":jSONObject_Input_InBody.getString("ctype"));
		tpdsCustomer.setRole(jSONObject_Input_InBody.get("crole")==null?"":jSONObject_Input_InBody.getString("crole"));
		tpdsCustomer.setUseName(jSONObject_Input_InBody.get("username")==null?""
				:Aes.encryptStr(jSONObject_Input_InBody.getString("username"), Constants.QuickPay.SYSTEM_KEY));//add by wyl 20170712 修改加密
		tpdsCustomer.setCertType(jSONObject_Input_InBody.get("certType")==null?"":jSONObject_Input_InBody.getString("certType")); 
		tpdsCustomer.setCertNo(jSONObject_Input_InBody.get("certNo")==null?""
				:Aes.encryptStr(jSONObject_Input_InBody.getString("certNo"), Constants.QuickPay.SYSTEM_KEY));//add by wyl 20170712 修改加密
		tpdsCustomer.setCertInfo(jSONObject_Input_InBody.get("certInfo")==null?"":jSONObject_Input_InBody.get("certInfo").toString()); 
		tpdsCustomer.setValiDate(jSONObject_Input_InBody.get("idvalidDate")==null?"":jSONObject_Input_InBody.getString("idvalidDate")); 
		tpdsCustomer.setExpDate(jSONObject_Input_InBody.get("idexpiryDate")==null?"":jSONObject_Input_InBody.getString("idexpiryDate"));
		tpdsCustomer.setJobType(jSONObject_Input_InBody.get("jobType")==null?"":jSONObject_Input_InBody.getString("jobType"));
		tpdsCustomer.setJob(jSONObject_Input_InBody.get("job")==null?"":jSONObject_Input_InBody.getString("job"));
		tpdsCustomer.setPostCode(jSONObject_Input_InBody.get("postcode")==null?"":jSONObject_Input_InBody.getString("postcode"));
		tpdsCustomer.setAddress(jSONObject_Input_InBody.get("address")==null?"":jSONObject_Input_InBody.getString("address"));
		tpdsCustomer.setNational(jSONObject_Input_InBody.get("national")==null?"":jSONObject_Input_InBody.getString("national")); 
		tpdsCustomer.setPhoneNo(jSONObject_Input_InBody.get("phoneNo")==null?""
				:Aes.encryptStr(jSONObject_Input_InBody.getString("phoneNo"), Constants.QuickPay.SYSTEM_KEY));//add by wyl 20170712 修改加密 手机号
		tpdsCustomer.setBusiliceNo(jSONObject_Input_InBody.get("busiLiceNo")==null?"":jSONObject_Input_InBody.getString("busiLiceNo"));
		tpdsCustomer.setBusiliceDir(jSONObject_Input_InBody.get("busiLiceDir")==null?"":jSONObject_Input_InBody.getString("busiLiceDir"));
		tpdsCustomer.setOrgcodeNo(jSONObject_Input_InBody.get("orgCodeNo")==null?"":jSONObject_Input_InBody.getString("orgCodeNo"));
		tpdsCustomer.setOrgcodeDir(jSONObject_Input_InBody.get("orgCodeDir")==null?"":jSONObject_Input_InBody.getString("orgCodeDir"));
		tpdsCustomer.setTaxregisNo(jSONObject_Input_InBody.get("taxRegisNo")==null?"":jSONObject_Input_InBody.getString("taxRegisNo"));
		tpdsCustomer.setTaxregisDir(jSONObject_Input_InBody.get("taxRegisDir")==null?"":jSONObject_Input_InBody.getString("taxRegisDir")); 		
		tpdsCustomer.setUnisoccreCode(jSONObject_Input_InBody.get("uniSocCreCode")==null?"":jSONObject_Input_InBody.getString("uniSocCreCode"));
		tpdsCustomer.setUnisoccreDir(jSONObject_Input_InBody.get("uniSocCreDir")==null?"":jSONObject_Input_InBody.getString("uniSocCreDir"));
		tpdsCustomer.setBindFlag(jSONObject_Input_InBody.get("bindFlag")==null?"":jSONObject_Input_InBody.getString("bindFlag"));
		tpdsCustomer.setBindType(jSONObject_Input_InBody.get("bindType")==null?"":jSONObject_Input_InBody.getString("bindType"));
		tpdsCustomer.setAccNo(jSONObject_Input_reqHeader.get("merchantCode")==null?"":TpdsDataUtils.customAccountNo(jSONObject_Input_reqHeader.getString("merchantCode")));//生成客户台账
		tpdsCustomer.setAccType(jSONObject_Input_InBody.get("acctype")==null?"":jSONObject_Input_InBody.getString("acctype"));
		tpdsCustomer.setOldbankaccountNo(jSONObject_Input_InBody.get("oldbankAccountNo")==null?""
				:Aes.encryptStr(jSONObject_Input_InBody.getString("oldbankAccountNo"), Constants.QuickPay.SYSTEM_KEY));//add by wyl 20170712 修改加密
		tpdsCustomer.setBankaccountNo(jSONObject_Input_InBody.get("bankAccountNo")==null?""
				:Aes.encryptStr(jSONObject_Input_InBody.getString("bankAccountNo"), Constants.QuickPay.SYSTEM_KEY));//add by wyl 20170712 修改加密
		tpdsCustomer.setBankaccountName(jSONObject_Input_InBody.get("bankAccountName")==null?""
				:Aes.encryptStr(jSONObject_Input_InBody.getString("bankAccountName"), Constants.QuickPay.SYSTEM_KEY));//add by wyl 20170712
		tpdsCustomer.setBankaccountTelno(jSONObject_Input_InBody.get("bankAccountTelNo")==null?""
				:Aes.encryptStr(jSONObject_Input_InBody.getString("bankAccountTelNo"), Constants.QuickPay.SYSTEM_KEY));//add by wyl 20170712 修改加密 手机号
		tpdsCustomer.setNote(jSONObject_Input_InBody.get("note")==null?"":jSONObject_Input_InBody.getString("note"));
	}
	
	
}
