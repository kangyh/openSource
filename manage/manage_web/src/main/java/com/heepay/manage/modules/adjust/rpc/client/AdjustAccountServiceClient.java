package com.heepay.manage.modules.adjust.rpc.client;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.payment.service.AccountRecordService;

@Service
public class AdjustAccountServiceClient extends BaseClient {
  
  private static Logger logger = LogManager.getLogger();
  
  private static final String SERVICENAME = "AccountRecordServiceImpl";
  private static final String NODENAME = "payment_rpc";
  
  private AccountRecordService.Client getClient() {
      this.setServiceName();
      this.setNodename();
      this.setTMultiplexedProtocol();
      return new AccountRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
  }

  @Override
  public void setServiceName() {
      ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
  }

  @Override
  public void setNodename() {
      ClientThreadLocal.getInstance().setNodename(NODENAME);
  }
  
  /**
   * 
  * @description 复核成功后--记账
  * @author 王亚洪       
  * @created 2016年12月15日 下午3:41:39     
  * @param adjustId
  * @param fromAccountId
  * @param toAccountId
  * @param allocationAmount
  * @return
   */
  public int adjustAccount(String adjustId, Map<Long, String> adjustAccountMap){
    try {
      return getClient().adjustAccount(adjustId, adjustAccountMap);
    } catch (TException e) {
      logger.error(e);
    } finally {
      this.close();
    }
    return 0;
  }
  
  
  public int adjustAccountTransDimension(String adjustId, String transNo, Map<Long, String> adjustAccountMap, String accountMark){
    try {
      return getClient().adjustAccountTransDimension(adjustId, transNo, adjustAccountMap, accountMark);
    } catch (TException e) {
      logger.error(e);
    } finally {
      this.close();
    }
    return 0;
  }
  
  
}
