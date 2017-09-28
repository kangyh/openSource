package com.heepay.manage.modules.payment.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.StatisticsQueryWhere;
import com.heepay.rpc.payment.service.StatisticsService;

@Service
public class StatiServiceClient extends BaseClientDistribute {

	private static final String SERVICENAME = "StatisticsServiceImpl";
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "paymentClient")
	private ThriftClientProxy clientProxy;

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	private StatisticsService.Client  getClient(){
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new StatisticsService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 *
	 * @description 统计功能
	 * @author TianYanqing
	 * @created 2017年1月20日 上午11:19:08     
	 * @param where
	 */
	public void statistics(StatisticsQueryWhere where)throws TException{
		try {
			 getClient().saveStatisticsRecord(where);
		} catch (TException e) {
			log.error(e);
			throw new TException();
		}finally{
			this.close();
		}
	}
	/**
	 *
	 * @description财务数据统计
	 * @author TianYanqing
	 * @created 2017年6月29日 下午1:46:48     
	 * @param where
	 * @throws TException
	 */
	public void financeStatistics(StatisticsQueryWhere where)throws TException{
		try {
			getClient().financeStatistics(where, "reStatistics");
		} catch (TException e) {
			log.error(e);
			throw new TException();
		}finally{
			this.close();
		}
	}

	/**
	 *
	 * @description统计出入金数据
	 * @author TianYanqing
	 * @created 2017年5月27日 下午5:25:25     
	 * @param where
	 * @return
	 * @throws TException
	 */
	public int statisticsBalance(StatisticsQueryWhere where) throws TException{
		try {
			 return getClient().statisticsBalance(where);
		} catch (TException e) {
			log.error(e);
			throw new TException();
		}finally{
			this.close();
		}
	}

	/* (non-Javadoc)
	 * @see com.heepay.rpc.client.BaseClient#setNodename()
	 */
	@Override
	public void setNodename() {
	  ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);

	}

	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

}
