package com.heepay.rpc.client;
import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.tpds.service.DataInfoSyncService;
/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月15日 下午4:36:03
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
@Service
public class DataInfoSyncServiceClient extends BaseClientDistribute{
	
private static final String SERVICENAME = "DataInfoSyncServiceImpl";
	
	private static final String NODENAME = "tpds_rpc";
	
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "tpdsClient")
  private ThriftClientProxy clientProxy;
  
  @Override
  public void setServiceName(){
    ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
  }
  
  @Override
  public void setNodename() {
    ClientThreadLocal.getInstance().setNodename(NODENAME);;
  }
  
  @Override
  public ThriftClientProxy getClientProxy() {
    return clientProxy;
  }
  public DataInfoSyncService.Client getClient(){
	    this.setServiceName();
	    this.setNodename();
	    this.setTMultiplexedProtocol();
	    return new DataInfoSyncService.Client(ClientThreadLocal.getInstance().getProtocol());
	  }
  /**
   * 客户信息同步
   */
  public String setCustomerInfoSync(String requestHeader, String requestBody) 
  {
	  try{
	      return getClient().setCustomerInfoSync(requestHeader, requestBody);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
  }
  /**
   * 标的信息同步
   */
  public String setBidInfoSync(String requestHeader, String requestBody)
  {
	  try{
	      return getClient().setBidInfoSync(requestHeader, requestBody);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
  }
}
