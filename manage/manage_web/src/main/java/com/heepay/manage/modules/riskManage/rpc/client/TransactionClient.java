package com.heepay.manage.modules.riskManage.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.model.FreezeResponse;
import com.heepay.rpc.risk.service.TransactionService;

/***
 * 
* 
* 描    述：调用风控系统的获取商户交易成功订单的接口
*
* 创 建 者：wangl
* 创建时间：  Dec 5, 201610:52:31 AM
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
public class TransactionClient extends BaseClientDistribute {

	private static final String SERVICENAME = "TransactionServiceImpl";
	private static final String NODENAME = "risk_rpc";

	private static final Logger log = LogManager.getLogger();
	@Resource(name = "riskMerchantInfoClient")
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

	public TransactionService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new TransactionService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 风控限额规则
	 * 
	 * @param chargeId
	 * @return
	 * @return
	 */
	public FreezeResponse getTransactionList(String whereCause, int pageIndex, int pageSize) {

		log.info("RPC服务   调取风控系统服务查询商户成功的交易订单信息----->{}");
		try {
			return getClient().getTransactionList(whereCause, pageIndex, pageSize);
		} catch (TException e) {
			log.error("RPC服务   调取风控系统服务查询商户成功的交易订单信息出错！{FIND_ERROR}", e.getMessage());
		} finally {
			this.close();
		}
		return null;
	}
}
