package com.heepay.risk.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.model.FreezeResponse;
import com.heepay.rpc.risk.service.TransOrderRiskService;
import com.heepay.rpc.risk.service.TransactionService;

public class TransactionClient extends BaseClientDistribute {
private static final String SERVICENAME = "TransactionServiceImpl";
	
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
  
  
  public TransactionService.Client getClient(){
    this.setServiceName();
    this.setNodename();
    this.setTMultiplexedProtocol();
    return new TransactionService.Client(ClientThreadLocal.getInstance().getProtocol());
  }
  /**
   * 风控限额规则
   * @param chargeId
 * @return 
   * @return
   */
  public  FreezeResponse getTransactionList(String whereCause, int pageIndex,int  pageSize){
    try{
      return getClient().getTransactionList(whereCause, pageIndex, pageSize);
    } catch (TException e){
      log.error(e);
    } finally {
      this.close();
    }
    return null;
  }
}
