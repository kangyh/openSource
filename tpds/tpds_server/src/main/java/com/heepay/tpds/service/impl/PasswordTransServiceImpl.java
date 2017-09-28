/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月18日上午11:48:23
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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.service.RpcService;
import com.heepay.tpds.dao.TpdsBankH5Mapper;
import com.heepay.tpds.dao.TpdsMerchantH5Mapper;
import com.heepay.tpds.dao.impl.TpdsBankH5DaoImpl;
import com.heepay.tpds.entity.TpdsBankH5;
import com.heepay.tpds.entity.TpdsMerchantH5;
import com.heepay.tpds.service.SysNo;
import com.heepay.tpds.util.HttpClientUtils;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.utils.http.WithoutAuthHttpsClient;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月18日上午11:48:23
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
@RpcService(name="PasswordTransServiceImpl",processor=com.heepay.rpc.tpds.service.PasswordTransService.Processor.class)
public class PasswordTransServiceImpl implements com.heepay.rpc.tpds.service.PasswordTransService.Iface{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	TpdsMerchantH5Mapper tpdsMerchantH5DaoImpl;
	
	@Autowired
	TpdsBankH5Mapper  tpdsBankH5DaoImpl;	
	
	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;
	
	@Resource(name="httpsClient")
	WithoutAuthHttpsClient httpsClient;
	/**
	 * 
	 * @方法说明：客户密码设置
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月18日下午1:51:12
	 */
	@Override
	public String passwordSetting(String reqHeader, String body) throws TException {
			
		logger.info("客户密码设置:{}", body);
		try {			
						
			return passWordOper(reqHeader, body, "enterpwd");			
			
		} catch (Exception e) {
			logger.error("客户设置交易密码异常: body:{}, Url:{}", body, serverBankInfo.getBankHostPasswordUrl(), e);
		}		
		return "";
	}
    
	/**
	 * 
	 * @方法说明：客户密码修改
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月18日下午1:51:12
	 */
	public String passwordModify(String reqHeader, String body) throws TException {
		
		String result = null;
		logger.info("客户客户密码修改:{}", body);
		try {				
			
			return passWordOper(reqHeader, body, "changepwd");	

		} catch (Exception e) {
			logger.error("客户修改交易密码异常:{}", body, e);
		}
		
		return "";
	}

	/**
	 * 
	 * @方法说明：重置密码
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月18日下午1:51:12
	 */
	public String passwordResetting(String reqHeader, String body) throws TException {
		
		String result = null;
		logger.info("客户重置密码:{}", body);
		try {				
			return passWordOper(reqHeader, body, "forgetpwd");			
			
		} catch (Exception e) {
			logger.error("客户重置交易密码异常:{}", body, e);
		}
		return null;
	}
	/**
	 * 
	 * @方法说明：客户交易密码校验
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月21日下午1:51:12
	 */
	public String passwordVerify(String reqHeader, String body) throws TException {
		
		logger.info("客户交易密码校验:{}", body);
		//入库
		return passWordOper(reqHeader, body, "checkpwd");
		
	}

	/**
	 * 
	 * @方法说明：客户交易密码校验,银行回调
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月21日下午1:51:12
	 */
	@Override
	public String passwordVerifyBack(String reqHeader, String body)
			throws TException {
		logger.info("密码操作结果，银行回调，{}", body);
		String result = null;
		try {		
			
			TpdsMerchantH5 tpdsMerchantH5 = new TpdsMerchantH5();
			
			
			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(body, Map.class);
			
			tpdsMerchantH5.setBusinessSeqNo(map.get("businessSeqNo"));  //业务流水号
			tpdsMerchantH5.setFlag(map.get("flag"));	                //银行处理结果				
			
			tpdsMerchantH5DaoImpl.updateBybusinessSeqNo(tpdsMerchantH5);	//商户h5调用登记表		
			
			Map<String, String> bankMap = new HashMap<String, String>();
			
			bankMap.put("businessSeqNo", map.get("businessSeqNo"));
			bankMap.put("flag", map.get("flag"));			
	
			tpdsBankH5DaoImpl.updateBybusinessSeqNo(bankMap);    //更新银行h5调用登记表
			
			logger.info("银行回调,更新验证结果成功， {}", body);
			
			Map<String, String> paraMap = new HashMap<String, String>();
			
			paraMap.put("businessSeqNo", tpdsMerchantH5.getBusinessSeqNo());
						
			TpdsMerchantH5 tpdsmer = tpdsMerchantH5DaoImpl.queryMerchantBybusinessSeqNo(paraMap);
			

			return tpdsmer.getBackUrl();  //返回银行回调地址
			
		} catch (Exception e) {
			logger.error("客户交易密码校验, 银行回调处理异常:{}", body, e);
		}
		return "";
	}

	@Override
	public String authCodeVerify(String reqHeader, String body) throws TException {
		String result = null;
		logger.info("客户校验验证码:{}", body);
		try {				
			result = HttpClientUtils.httpGet(body);
			return result;
		} catch (Exception e) {
			logger.error("客户校验验证码异常:{}", body, e);
		}
		return null;
	}
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @param operType
	 * @return
	 * @时间：2017年4月20日下午1:51:09
	 */
	@SuppressWarnings("unchecked")
	private String passWordOper(String reqHeader, String body, String operType){
		
		try {			
						
			TpdsMerchantH5 tpdsMerchantH5 = new TpdsMerchantH5();
			
			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(body, Map.class);
			
			tpdsMerchantH5.setSystemNo(map.get("systemCode"));  
			tpdsMerchantH5.setCustomerNo(map.get("userId"));
			tpdsMerchantH5.setBackUrl(map.get("backURL"));
			tpdsMerchantH5.setBusinessSeqNo(map.get("businessSeqNo"));			
			
			tpdsMerchantH5.setFlag("3");       //结果码 ，处理中 	
			tpdsMerchantH5.setOpType(operType); //操作类型：
			
			Map<String, String> paraMap = new HashMap<String, String>();
			 
			paraMap.put("businessSeqNo", tpdsMerchantH5.getBusinessSeqNo());   //业务流水号
			 			
			Integer num = tpdsMerchantH5DaoImpl.countMerchantH5BybusinessSeqNo(paraMap);
			 
			 if(num > 0){
				 return "0";  //业务流水号重复
			 }				 
			
			tpdsMerchantH5DaoImpl.insert(tpdsMerchantH5);	
			
			TpdsBankH5 tpdsBankH5 = new TpdsBankH5();
			
			tpdsBankH5.setSystemNo(map.get("systemCode"));
			tpdsBankH5.setCustomerNo(map.get("userId"));
			tpdsBankH5.setBackUrl(map.get("backURL"));
			tpdsBankH5.setFlag("3");
			tpdsBankH5.setOpType(operType);
			tpdsBankH5.setBusinessSeqNo(map.get("businessSeqNo"));	
			
			tpdsBankH5DaoImpl.insertSelective(tpdsBankH5);
			
		
			return "1";              //入库成功
		} catch (Exception e) {
			logger.error("客户交易密码校验:{}", body, e);
		}
		return "2";   //异常
		
	}

}
