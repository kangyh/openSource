package com.heepay.manage.modules.pbc.web.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.report.antifraud.thrift.TSuspiciousListService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;

/**
 * 
 *
 * 描 述：调用风控系统的获取商户信息和产品信息的接口
 *
 * 创 建 者： wangl
 * 创建时间：2016年11月23日 上午9:24:57 
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
public class PbcPaymentAccountRpcClient extends BaseClientDistribute {

	private static final String SERVICENAME = "SuspiciousServiceImpl";

	private static final String NODENAME = "antifraud_rpc";

	private static final Logger log = LogManager.getLogger();
	
	//配置文件中的id名称
	@Resource(name = "PbcPaymentAccountClient")
	private ThriftClientProxy clientProxy;
	
	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	private TSuspiciousListService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new TSuspiciousListService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
	}

	
	/**
	 * 
	 * @方法说明：可疑名单上报-异常消费支付账户-关联全支付账号  上报
	 * @时间：Dec 23, 2016
	 * @创建人：wangl
	 */
	public String pbcPaymentAccount(String str) {

		TSuspiciousListService.Client client = this.getClient();
		log.info("可疑名单上报-异常消费支付账户-关联全支付账号上报----->{开始}----->{参数}"+str);
		String value="";
		try {
			value = client.reportExceptionPayAccount(str);
			log.info("可疑名单上报-异常消费支付账户-关联全支付账号上报----->{结束}----->{参数}"+str);
		} catch (TException e) {
			log.info("可疑名单上报-异常消费支付账户-关联全支付账号上报----->{出现异常}   "+e.getMessage());
		}finally {
			this.close();
		}
		return value;
	}
	
	/**
	 * 
	 * @方法说明：涉案账户上报
	 * @时间：Dec 23, 2016
	 * @创建人：wangl
	 */
	public String reportCaseAccount(String str) {

		TSuspiciousListService.Client client = this.getClient();
		log.info("涉案账户上报----->{开始}----->{参数}"+str);
		String value="";
		try {
			value = client.reportCaseAccount(str);
			log.info("涉案账户上报----->{结束}----->{参数}"+str);
		} catch (TException e) {
			log.info("涉案账户上报----->{出现异常}   "+e.getMessage());
		}finally {
			this.close();
		}
		return value;
	}
	
	
	/**
	 * 
	 * @方法说明：涉案账户历史明细
	 * @时间：Dec 23, 2016
	 * @创建人：wangl
	 */
	public String reportAccountHistoryTrans(String str) {

		TSuspiciousListService.Client client = this.getClient();
		log.info("涉案账户历史明细----->{开始}----->{参数}"+str);
		String value="";
		try {
			value = client.reportAccountHistoryTrans(str);
			log.info("涉案账户历史明细----->{结束}----->{参数}"+str);
		} catch (TException e) {
			log.info("涉案账户历史明细----->{出现异常}   "+e.getMessage());
		}finally {
			this.close();
		}
		return value;
	}
	
	/**
	 * 
	 * @方法说明：可疑交易上报-单笔交易上报
	 * @时间：Dec 23, 2016
	 * @创建人：wangl
	 */
	public String reportSingleTransQuery(String str) {

		TSuspiciousListService.Client client = this.getClient();
		log.info("可疑交易上报-单笔交易上报----->{开始}----->{参数}"+str);
		String value="";
		try {
			value = client.reportSingleTransQuery(str);
			log.info("可疑交易上报-单笔交易上报----->{结束}----->{参数}"+str);
		} catch (TException e) {
			log.info("可疑交易上报-单笔交易上报----->{出现异常}   "+e.getMessage());
		}finally {
			this.close();
		}
		return value;
	}
}
