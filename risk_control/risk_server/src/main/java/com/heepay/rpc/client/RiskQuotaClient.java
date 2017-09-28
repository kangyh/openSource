package com.heepay.rpc.client;



import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.service.TransOrderRiskService;

@Service
public class RiskQuotaClient extends BaseClientDistribute{
	
private static final String SERVICENAME = "ProductQuotaServiceImpl";
	
	private static final String NODENAME = "risk_rpc";
	
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "riskClient")
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
  
  
  public TransOrderRiskService.Client getClient(){
    this.setServiceName();
    this.setNodename();
    this.setTMultiplexedProtocol();
    return new TransOrderRiskService.Client(ClientThreadLocal.getInstance().getProtocol());
  }
  /**
   * 风控限额规则
   * @param chargeId
 * @return 
   * @return
   */
  public  AsyncMsgVO ExecuteRule(com.heepay.rpc.risk.model.TransOrderRiskModel vo){
    try{
      return getClient().ExecuteRule(vo);
    } catch (TException e){
      log.error(e);
    } finally {
      this.close();
    }
    return null;
  }
 
  
  
  
  
}
