package com.heepay.manage.modules.settle.web.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.heepay.rpc.billing.service.OldRecordService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
/**
 * 
 *
 * 描    述：
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月29日 下午6:36:26
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
public class BillingDateSynClearingDateClient extends BaseClientDistribute {

	private static final String SERVICENAME = "DataSysServiceImpl";
	private static final String NODENAME = "billing_rpc";

	private static final Logger log = LogManager.getLogger();
	
	//配置文件中的id名称
	@Resource(name = "paymentbillapiClient")
	private ThriftClientProxy clientProxy;
	
	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	private OldRecordService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new OldRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
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
	 * @方法说明：清结算数据同步
	 * @时间：Nov 23, 2016
	 * @创建人：wangl
	 */
	public String alldataSynchronize() {
		
		OldRecordService.Client client = this.getClient();
		
		String returnStr =null;
		try {
			log.info("RPC服务   清结算数据同步{dataSynchronize}");
			
			returnStr = client.alldataSynchronize();
			 
			log.info("RPC服务   清结算数据同步{dataSynchronize}返回值",returnStr);
			
		} catch (Exception e) {
			log.error("RPC服务   清结算数据同步！{dataSynchronize}异常", e);
		} finally {
			this.close();
		}
		return returnStr;
	}
	
	
	
}
