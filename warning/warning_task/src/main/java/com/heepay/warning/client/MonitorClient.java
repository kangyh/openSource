package com.heepay.warning.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.warning.service.WarningService;

@Service
public class MonitorClient extends BaseClientDistribute  {
    private static final String NODENAME = "warning_rpc";
    private static final String SERVICENAME = "WarningServiceImpl";
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "warningClient")
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
  
  
  public WarningService.Client getClient(){
    this.setServiceName();
    this.setNodename();
    this.setTMultiplexedProtocol();
    return new WarningService.Client(ClientThreadLocal.getInstance().getProtocol());
  }
  /**
   * 风控限额规则
   * @param chargeId
 * @return 
   * @return
   */
  public  String prepareMsg(){
    try{
      return getClient().prepareMsg();
    } catch (TException e){
      log.error(e);
    } finally {
      this.close();
    }
    return null;
}
  public  String sendMsg(){
	    try{
	      return getClient().sendMsg();
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	}
  public  String sendWaringMsg(String msgEntity){
	    try{
	      return getClient().sendWaringMsg(msgEntity);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	}
    public  String getInfoGroup(String groupCode){
        try{
            return getClient().getInfoGroup(groupCode);
        } catch (TException e){
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
  
}