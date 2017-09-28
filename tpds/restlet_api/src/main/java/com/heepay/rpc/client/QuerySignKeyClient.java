package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.tpds.service.AsynNoticeService;
import com.heepay.rpc.tpds.service.QuerySignKeyService;

/**
 * 
 * 
 * 描    述：查询商户与汇付宝签约key
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年3月3日上午10:40:25 
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
public class QuerySignKeyClient extends BaseClientDistribute {

	private static final String SERVICENAME = "QuerySignKeyServiceImpl";

	private static final String NODENAME = "tpds_rpc";

	private static final Logger log = LogManager.getLogger();
	
	@Resource(name = "tpdsClient")
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

	public QuerySignKeyService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new QuerySignKeyService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}
	
	/**
	 * 
	 * @方法说明：查询商户与汇付宝签约key
	 * @author chenyanming
	 * @param merchantNo
	 * @param productCode
	 * @return
	 * @时间：2017年3月3日上午10:59:40
	 */
	public String querySignKey(String merchantNo, String productCode) {
		QuerySignKeyService.Client client = this.getClient();
		try {
			String signKey = client.querySignKey(merchantNo, productCode);
			 log.info("B2C网银充值查询签约key成功:{},{}", merchantNo, productCode);
			 return signKey;
		} catch (TException e) {
			e.printStackTrace();
			log.info("B2C网银充值查询签约key服务异常,报文头：{},{}", merchantNo, productCode, e);
		} finally {
			this.close();
		}
		return null;
	}

}
