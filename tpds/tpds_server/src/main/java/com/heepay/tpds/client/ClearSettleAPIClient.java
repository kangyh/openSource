package com.heepay.tpds.client;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.billing.model.TpdsSettleMerchantModel;
import com.heepay.rpc.billing.model.querySettleMerchantModel;
import com.heepay.rpc.billing.service.TpdsSettleMerchantService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;


@Service
public class ClearSettleAPIClient extends BaseClientDistribute{
	
private static final String SERVICENAME = "TpdsSettleMerchantServiceImpl";
	
	private static final String NODENAME = "billing_rpc";
	
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "billServerClient")
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
  
  
  public TpdsSettleMerchantService.Client getClient(){
    this.setServiceName();
    this.setNodename();
    this.setTMultiplexedProtocol();
    return new TpdsSettleMerchantService.Client(ClientThreadLocal.getInstance().getProtocol());
  }
  
  
  /**
   * 结算单查询
   * @param chargeId
   * @return
   */
  
  public List<TpdsSettleMerchantModel> getTpdsSettleMerchantModel(querySettleMerchantModel model){
	  TpdsSettleMerchantService.Client client = this.getClient();
	  try {
		     List<TpdsSettleMerchantModel> list  = client.querySettleMerchantRecord(model);
			 //log.info("结算单查询成功！{}", list);
			 return list;
		} catch (TException e) {				
			log.error("结算单查询异常！{}，异常{}", e);
		} finally {
			this.close();
		}
		return null;
  }
 
}
