package com.heepay.billing.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.service.SettleChannelRecordService;
import com.heepay.rpc.billing.service.SettleDifferBathService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;

/**
 * 
 * 
 * 描    述：生成差错批次客户端
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年10月27日下午3:46:48 
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
public class SettleDifferBathClient extends BaseClient {
	
	private static final String SERVICENAME = "GetSettleDifferBathServiceImpl";

	private static final String NODENAME = Constants.Clear.BILLINGRPC;

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
	}
	
	public SettleDifferBathService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new SettleDifferBathService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
	
	/**
	 * 
	 * @方法说明：汇总差错批次数据，入款类撤补账处理，出款类只汇总，撤补账需审核
	 * @author chenyanming
	 * @时间：2016年11月15日上午10:58:36
	 */
	public void getSettleDifferBath() {
		try {
			this.getClient().getSettleDifferBath();
			logger.info("汇总差错批次数据任务结束！");
		} catch (TException e) {
			logger.error("汇总差错批次数据出错！",e);
		}
	}

	/**
	 * 
	 * @方法说明：出款类撤补账处理
	 * @author chenyanming
	 * @时间：2016年11月15日上午10:58:14
	 */
	public void doExpendDifferBath() {
		try {
			this.getClient().doExpendDifferBath();
			logger.info("出款类差错批次数据处理结束");
		} catch (Exception e) {
			logger.error("出款类差错批次数据处理出错" + e);
		}
	}

}
