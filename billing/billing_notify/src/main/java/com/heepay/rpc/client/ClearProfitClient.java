package com.heepay.rpc.client;

import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.model.ClearShareProfitModel;
import com.heepay.rpc.billing.service.ClearShareProfitService;


/***
 * 
* 
* 描    述：商户侧分润
*
* 创 建 者： xuangang
* 创建时间：  2016年11月3日下午2:54:35
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
public class ClearProfitClient extends BaseClient{

	private static final String SERVICENAME = "ClearingProfitDetailServiceImpl";

	private static final String NODENAME = Constants.Clear.BILLINGRPC;

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
	}

	public ClearShareProfitService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new ClearShareProfitService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
	
	/**
	 * @描述 接收交易系统分润数据，发送到server端
	 * @param ClearShareProfitModel
	 */
	public void saveShareProfitRecord(ClearShareProfitModel clearShareProfitModel){
		ClearShareProfitService.Client client = this.getClient();
		try {
			 client.saveClearShareProfitRecord(clearShareProfitModel);
			 
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}

}
