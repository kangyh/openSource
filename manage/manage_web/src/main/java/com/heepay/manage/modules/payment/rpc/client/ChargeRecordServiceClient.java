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
import com.heepay.rpc.payment.service.ChargeRecordService;

@Service
public class ChargeRecordServiceClient extends BaseClientDistribute {

	private static final String SERVICENAME = "ChargeRecordServiceImpl";
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "paymentClient")
	private ThriftClientProxy clientProxy;

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	private ChargeRecordService.Client  getClient(){
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new ChargeRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}


	/**
	 *
	 * @description 汇款充值审核功能
	 * @author TianYanqing
	 * @created 2017年6月26日 下午7:58:44
	 * @param chargeId
	 * @param status
	 * @return
	 * @throws TException
	 */
	public String auditingRemitsCharge(String chargeId, String status, String remark) throws TException{
		try {
			return getClient().auditingRemitsCharge(chargeId, status, remark);
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
