package com.heepay.boss.client;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.boss.model.BillingClearModel;
import com.heepay.rpc.boss.service.BillingClearService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;

/**
 * *
 * 
* 
* 描    述：清结算收集数据客户端
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日上午11:44:48
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
@Service
public class BillingAPIClient extends BaseClientDistribute {

	private static final String SERVICENAME = "BigDataBillingServiceImpl";

	private static final String NODENAME = "billing_rpc";

	private static final Logger logger = LogManager.getLogger();

	@Resource(name = "billingClient")
	private ThriftClientProxy clientProxy;

	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);

	}

	public BillingClearService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new BillingClearService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	public List<BillingClearModel> queryDateFromBilling() {
		BillingClearService.Client client = this.getClient();
		try {
			return client.queryDateFromBilling();
		} catch (TException e) {
			logger.error("从清结算查找数据定时任务出现异常{}", e);
		} finally {
			this.close();
		}
		
		return null;

	}

}
