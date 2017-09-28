package com.heepay.manage.modules.payment.rpc.client;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.model.SettleBatchMsgModel;
import com.heepay.rpc.billing.service.SettleBatchService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.StatisticsQueryWhere;
import com.heepay.rpc.payment.service.StatisticsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SettleBatchMsgClient extends BaseClientDistribute {

	private static final String SERVICENAME = "SettleBatchMsgServiceImpl";

	private static final Logger log = LogManager.getLogger();

	@Resource(name = "paymentbillapiClient")
	private ThriftClientProxy clientProxy;

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}
	
	private SettleBatchService.Client getClient(){
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new SettleBatchService.Client(ClientThreadLocal.getInstance().getProtocol());
	}


	/**
	 *
	 * @param settleBatch
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SettleBatchMsgModel queryMerchantSettleBatch(String settleBatch, int pageNum, int pageSize){
		try {
			log.info("查询商户明细,批次号:{},页数:{},每页:{}",settleBatch,pageNum,pageSize);
			 return getClient().queryMerchantSettleBatch(settleBatch,pageNum,pageSize);
		} catch (TException e) {
			log.error(e);
		}finally{
			this.close();
		}
		return null;
	}

	/**
	 *
	 * @param settleBatch
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SettleBatchMsgModel queryChannelSettleBatch(String settleBatch, int pageNum, int pageSize){
		try {
			log.info("查询通道明细,批次号:{},页数:{},每页:{}",settleBatch,pageNum,pageSize);
			return getClient().queryChannelSettleBatch(settleBatch,pageNum,pageSize);
		} catch (TException e) {
			log.error(e);
		}finally{
			this.close();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.heepay.rpc.client.BaseClient#setNodename()
	 */
	@Override
	public void setNodename() {
	  ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.BILLINGRPC);
		
	}

	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

}
