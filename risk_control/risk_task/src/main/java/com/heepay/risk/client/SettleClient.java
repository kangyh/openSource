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
import com.heepay.rpc.risk.service.SettleService;
import com.heepay.rpc.risk.service.TransOrderRiskService;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月7日 上午10:04:31
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
public class SettleClient extends BaseClientDistribute{
	
private static final String SERVICENAME = "SettleServiceImpl";
	
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
  
  
  public SettleService.Client getClient(){
    this.setServiceName();
    this.setNodename();
    this.setTMultiplexedProtocol();
    return new SettleService.Client(ClientThreadLocal.getInstance().getProtocol());
  }
  /**
   * 风控限额规则
   * @param chargeId
 * @return 
   * @return
   */
  public  String pushBlackList(String requestInfo){
    try{
      return getClient().pushBlackList(requestInfo);
    } catch (TException e){
      log.error(e);
    } finally {
      this.close();
    }
    return null;
  }
  public  String pushriskInfo(String requestInfo){
	    try{
	      return getClient().pushriskInfo(requestInfo);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	  }
  public  String pushMerchantDiffInfo(String requestInfo){
	    try{
	      return getClient().pushMerchantDiffInfo(requestInfo);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	  }
}


 