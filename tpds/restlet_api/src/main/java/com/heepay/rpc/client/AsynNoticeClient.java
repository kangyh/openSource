/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月16日上午9:57:56
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

import com.heepay.rpc.tpds.service.AsynNoticeService;

/**
 * 
 *
 * 描    述：异步接口客户端
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月16日 上午10:18:30
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
public class AsynNoticeClient extends BaseClientDistribute{

	private static final String SERVICENAME = "AsynNoticeServiceImpl";

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

	public AsynNoticeService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new AsynNoticeService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}
	
	/**
	 * 
	 * @方法说明：客户充值异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String customerChargeAsynNotice(String reqHeader, String body) {
		AsynNoticeService.Client client = this.getClient();
		try {
			 String chargeAsynNotice = client.customerChargeAsynNotice(reqHeader, body);
			 return chargeAsynNotice;
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户充值异步通知RPC服务异常---->{}", e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @方法说明：客户提现异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String customerWithdrawAsynNotice(String reqHeader, String body) {
		AsynNoticeService.Client client = this.getClient();
		try {
			 String withdrawAsynNotice = client.customerWithdrawAsynNotice(reqHeader, body);
			 return withdrawAsynNotice;
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户提现异步通知RPC服务异常---->{}",e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @方法说明：文件异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String fileAsynNotice(String reqHeader, String body) {
		AsynNoticeService.Client client = this.getClient();
		try {
			 String fileAsynNotice = client.fileAsynNotice(reqHeader, body);
			 return fileAsynNotice;
		} catch (TException e) {
			e.printStackTrace();
			log.info("文件异步通知RPC服务异常---->{}",e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @方法说明：银行提现异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String bankWithdrawAsynNotice(String reqHeader, String body) {
		AsynNoticeService.Client client = this.getClient();
		try {
			 String bankWithdrawAsynNotice = client.bankWithdrawAsynNotice(reqHeader, body);
			 return bankWithdrawAsynNotice;
		} catch (TException e) {
			e.printStackTrace();
			log.info("银行提现异步通知RPC服务异常---->{}",e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @方法说明：日切异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String cutDayAsynNotice(String reqHeader, String body) {
		AsynNoticeService.Client client = this.getClient();
		try {
			 String cutDayAsynNotice = client.cutDayAsynNotice(reqHeader, body);
			 return cutDayAsynNotice;
		} catch (TException e) {
			e.printStackTrace();
			log.info("日切异步通知RPC服务异常---->{}",e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @方法说明：设置密码异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String setPasswordAsynNotice(String reqHeader, String body) {
		AsynNoticeService.Client client = this.getClient();
		try {
			 String setPasswordAsynNotice = client.setPasswordAsynNotice(reqHeader, body);
			 return setPasswordAsynNotice;
		} catch (TException e) {
			e.printStackTrace();
			log.info("设置密码异步通知RPC服务异常---->{}",e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @方法说明：修改密码异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String editPasswordAsynNotice(String reqHeader, String body) {
		AsynNoticeService.Client client = this.getClient();
		try {
			 String editPasswordAsynNotice = client.editPasswordAsynNotice(reqHeader, body);
			 return editPasswordAsynNotice;
		} catch (TException e) {
			e.printStackTrace();
			log.info("修改密码异步通知RPC服务异常---->{}",e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @方法说明：重置密码异步通知
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String resetPasswordAsynNotice(String reqHeader, String body) {
		AsynNoticeService.Client client = this.getClient();
		try {
			 String resetPasswordAsynNotice = client.resetPasswordAsynNotice(reqHeader, body);
			 return resetPasswordAsynNotice;
		} catch (TException e) {
			e.printStackTrace();
			log.info("重置密码异步通知RPC服务异常---->{}",e);
		} finally {
			this.close();
		}
		return null;
	}
}
