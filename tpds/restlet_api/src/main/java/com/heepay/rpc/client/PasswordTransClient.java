/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月15日下午5:06:15
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
package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.tpds.service.PasswordTransService;
import com.heepay.rpc.tpds.service.TransStatusService;

/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月18日下午1:50:42
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
public class PasswordTransClient extends BaseClientDistribute {

	private static final String SERVICENAME = "PasswordTransServiceImpl";

	private static final String NODENAME = "tpds_rpc";

	private static final Logger log = LogManager.getLogger();
	
	@Resource(name = "tpdsClient")
	private ThriftClientProxy clientProxy;

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
	}

	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	public PasswordTransService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new PasswordTransService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}

	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月18日下午1:51:12
	 */
    public String passwordSetting(String reqHeader, String body) {
		PasswordTransService.Client client = this.getClient();
		try {
			 return client.passwordSetting(reqHeader, body);
			 
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户设置交易密码---->{}", e);
		} finally {
			this.close();
		}
		return null;
	}
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月18日下午1:51:20
	 */
	public String passwordModify(String reqHeader, String body){
		PasswordTransService.Client client = this.getClient();
		try {
			 return client.passwordModify(reqHeader, body);
			 
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户修改交易密码---->{}", e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 		
	 * @方法说明：
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月18日下午1:51:26
	 */
	public String passwordResetting(String reqHeader, String body){
		PasswordTransService.Client client = this.getClient();
		try {
			 return client.passwordResetting(reqHeader, body);
			 
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户重置交易密码{}，{}", reqHeader, body, e);
		} finally {
			this.close();
		}
		return null;
	}
	/**
	 * 		
	 * @方法说明：
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月18日下午1:51:26
	 */
	public String passwordVerifyBack(String reqHeader, String body){
		PasswordTransService.Client client = this.getClient();
		try {
			 return client.passwordVerifyBack(reqHeader, body);
			 
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户交易密码校验,银行回调：{}，{}", reqHeader, body, e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 		
	 * @方法说明：
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月18日下午1:51:26
	 */
	public String passwordVerify(String reqHeader, String body){
		PasswordTransService.Client client = this.getClient();
		try {
			 return client.passwordVerify(reqHeader, body);
			 
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户交易密码校验,银行回调：{}，{}", reqHeader, body, e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 		
	 * @方法说明：
	 * @author xuangang
	 * @param reqHeader
	 * @param body
	 * @return
	 * @时间：2017年2月18日下午1:51:26
	 */
	public String authCodeVerify(String reqHeader, String body){
		PasswordTransService.Client client = this.getClient();
		try {
			 return client.authCodeVerify(reqHeader, body);
			 
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户校验验证码：{}，{}", reqHeader, body, e);
		} finally {
			this.close();
		}
		return null;
	}

}
