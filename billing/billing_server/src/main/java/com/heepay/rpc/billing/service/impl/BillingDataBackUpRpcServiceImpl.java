package com.heepay.rpc.billing.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.heepay.rpc.billing.service.IBillingDataBackUpService;
import com.heepay.rpc.service.RpcService;

/**
 * 
 *
 * 描    述：Rpc清结算数据库备份
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月23日 上午10:44:17
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
@Repository         
@RpcService(name = "BillingDataBackUpServiceImpl", processor = com.heepay.rpc.billing.service.BillingDataBackUpService.Processor.class)
public class BillingDataBackUpRpcServiceImpl implements com.heepay.rpc.billing.service.BillingDataBackUpService.Iface {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private IBillingDataBackUpService billingDataBackUpServiceImpl;

	/*
	 * 清结算数据库备份
	 * (non-Javadoc)
	 * @see com.heepay.rpc.billing.service.BillingDataBackUpService.Iface#billingDataBackUp()
	 */
	@Override
	public void billingDataBackUp() throws TException {
		try {
			billingDataBackUpServiceImpl.billingDataBackUp();
		} catch (Exception e) {
			logger.error("清结算数据库备份Rpc实现类异常----->", e);
		}
	}
	
}
