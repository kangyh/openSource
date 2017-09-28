package com.heepay.manage.modules.tpds.web.rpc;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.BankcardAuthModel;
import com.heepay.rpc.payment.service.BankcardAuthService;
import com.heepay.rpc.payment.service.BankcardAuthService.Client;

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
public class TpdsClient extends BaseClientDistribute {

	private static final String SERVICENAME = "BankcardAuthServiceImpl";
	private static final String NODENAME = "payment_rpc";

	
	private static final Logger log = LogManager.getLogger();
	
	@Resource(name = "paymentClient")
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

	public Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new BankcardAuthService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 
	 * @方法说明：用户绑卡
	 * @时间：13 Feb 201714:20:16
	 * @创建人：wangl
	 */
	public int saveTpdsTxBankCard(BankcardAuthModel bankcardAuthModel) {
		
		BankcardAuthService.Client client = this.getClient();
		
		log.info("RPC服务   资金存管商户绑卡----->{}");
		try {
			int num= client.saveTpdsTxBankCard(bankcardAuthModel);
			log.info("RPC服务   资金存管商户绑卡----->{}");
			return num;
		} catch (TException e) {
			log.info("RPC服务   资金存管商户绑卡----->{连接异常}"+e.getMessage());
			return 0;
		} finally {
			this.close();
		}
	}
}
