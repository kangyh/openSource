/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月15日下午5:06:15
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
package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.tpds.service.TransStatusService;

/***
 * 
 * 
 * 描 述：
 *
 * 创 建 者： xuangang 创建时间： 2017年2月15日下午5:06:15 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
public class TransStatusQueryClient extends BaseClientDistribute {

	private static final String SERVICENAME = "TransStatusServiceImpl";

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

	public TransStatusService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new TransStatusService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}
/**
 * 
 * @param channelCode
 * @return
 */
	public String transStatusQuery(String reqHeader, String body) {
		TransStatusService.Client client = this.getClient();
		try {
			 return client.transStatusQuery(reqHeader, body);
			 
		} catch (TException e) {
			e.printStackTrace();
			log.info("交易状态查询异常---->{}", e);
		} finally {
			this.close();
		}
		return null;
	}

	/**
	 * 
	 * @方法说明：实名认证获取路由信息
	 * @author xuangang
	 * @param channelCode
	 * @return
	 * @时间：2016年11月22日上午10:17:35
	 */
//	public String queryCertifyChannelByChannelCode(String channelCode) {
//		PayChannelCacheService.Client client = this.getClient();
//		try {
//			return client.queryCertifyChannelByChannelCode(channelCode);
//		} catch (TException e) {
//			e.printStackTrace();
//			log.info("实名认证获取通道侧路由信息异常{}", channelCode, e);
//		} finally {
//			this.close();
//		}
//		return null;
//	}

}
