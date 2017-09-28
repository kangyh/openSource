package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.manage.rpc.service.MerchantRedisService;
import com.heepay.manage.rpc.service.PayChannelCacheService;


/***
 * 
* 
* 描    述：获取通道侧路由信息
*
* 创 建 者： xuangang
* 创建时间：  2016年10月24日下午6:35:48
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
public class PayChannelCacheServiceClient extends BaseClientDistribute{

private static final String SERVICENAME = "payChannelCacheServiceImpl";
	
	private static final String NODENAME = "manager_server";
	
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "manageServerClient")
	private ThriftClientProxy clientProxy;
  
	
	 @Override
	  public void setServiceName(){
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
	  
	  public PayChannelCacheService.Client getClient(){
		    this.setServiceName();
		    this.setNodename();
		    this.setTMultiplexedProtocol();
		    return new PayChannelCacheService.Client(ClientThreadLocal.getInstance().getProtocol());
	 }
	  

	  public String getChannelByChannelCode(String channelCode){
		  PayChannelCacheService.Client client = this.getClient();
		  try {
				return client.queryChannelByChannelCode(channelCode);
			} catch (TException e) {
				e.printStackTrace();
				log.info("获取通道侧路由信息异常{}", channelCode, e);
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
	  public String queryCertifyChannelByChannelCode(String channelCode){
		  PayChannelCacheService.Client client = this.getClient();
		  try {
				return client.queryCertifyChannelByChannelCode(channelCode);
			} catch (TException e) {
				log.info("实名认证获取通道侧路由信息异常{}", channelCode, e);
			} finally {
				this.close();
			}
			return null;
	  }
	  
	  /**
	   * 
	   * @方法说明：获取开关标识
	   * @author chenyanming
	   * @param flag
	   * @return
	   * @时间：2017年6月7日上午10:08:32
	   */
	  public String getBillFlag(String flag) {
		  PayChannelCacheService.Client client = this.getClient();
		  try {
				return client.getRouteByChannelCode(flag);
			} catch (TException e) {
				log.info("获取开关标识信息异常{}", flag, e);
			} finally {
				this.close();
			}
			return null;
	  }
	  
}








