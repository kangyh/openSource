package com.heepay.manage.modules.merchant.rpc.client;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.pagehelper.Constant;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.service.MerchantAccountDailyService;

/**
 * 
* 
* 描    述：
*
* 创 建 者： 杨春龙  
* 创建时间： 2017年1月22日 上午10:56:34 
* 创建描述：试算平衡数据重新生成
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
public class MerchantAccountDailyClient extends BaseClientDistribute {

	private static final String SERVICENAME = "MerchantAccountDailyServiceImpl";


	@Resource(name = "paymentClient")
	private ThriftClientProxy clientProxy;
	
	
	@Override
	public ThriftClientProxy getClientProxy() {

		return this.clientProxy;
	}
	
	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);
	}

	public MerchantAccountDailyService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new MerchantAccountDailyService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}
	public int statisticsOnedayMerchantLogByBetweenCreateTime(String beginDate,
		String endDate,String nowDate) {
		MerchantAccountDailyService.Client client = this.getClient();
		try {
			return client.statisticsOnedayMerchantLogByBetweenCreateTime(beginDate,endDate,nowDate);
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return 0;
	}

}
