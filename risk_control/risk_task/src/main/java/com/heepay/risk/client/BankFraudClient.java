package com.heepay.risk.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.service.BankFraudService;

@Service
public class BankFraudClient extends BaseClientDistribute  {
    private static final String NODENAME = "risk_rpc";
    private static final String SERVICENAME = "BankFraudServiceImpl";
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
  
  
  public BankFraudService.Client getClient(){
    this.setServiceName();
    this.setNodename();
    this.setTMultiplexedProtocol();
    return new BankFraudService.Client(ClientThreadLocal.getInstance().getProtocol());
  }
  /**
   * 风控限额规则
   * @param chargeId
 * @return 
   * @return
   */
  public  AsyncMsgVO GetAccountDynamic(String jsonStr){
    try{
      return getClient().GetAccountDynamic(jsonStr);
    } catch (TException e){
      log.error(e);
    } finally {
      this.close();
    }
    return null;
}
  public  AsyncMsgVO GetTransDetailList(String jsonStr){
	    try{
	      return getClient().GetTransDetailList(jsonStr);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	}
  
  public  AsyncMsgVO GetAccountSubjectDetailList(String jsonStr){
	    try{
	      return getClient().GetAccountSubjectDetailList(jsonStr);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	}
  public  AsyncMsgVO GetAccountDynamicRelease(String jsonStr){
	    try{
	      return getClient().GetAccountDynamicRelease(jsonStr);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	}
  public  AsyncMsgVO GetPaymentAccountList(String jsonStr){
	    try{
	      return getClient().GetPaymentAccountList(jsonStr);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	}
}
