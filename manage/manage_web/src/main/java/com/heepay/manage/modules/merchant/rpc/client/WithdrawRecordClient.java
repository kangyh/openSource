package com.heepay.manage.modules.merchant.rpc.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.payment.service.AccountRecordService;

/**
 * 名称：商户client
 * <p>
 * 创建者  yanxb
 * 创建时间 2016-10-19 16:58:32
 * 创建描述：查询商户信息用
 */
@Service
public class WithdrawRecordClient extends BaseClient {
  
    private static final String SERVICENAME = "AccountRecordServiceImpl";
    private static final String NODENAME = "payment_rpc";
    
    private static final Logger log = LogManager.getLogger();
    
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
    * @discription 提现撤账
    * @author yanxb       
    * @created 2016年10月21日 下午5:04:06     
    * @param merchantId
    * @param withdrawid
    * @return
     */
    public int revokeAccount(long merchantId,String withdrawId,String transType){
      try {
        return getClient().revokeAccount(merchantId,withdrawId,transType,"");
      } catch (TException e) {
      	log.error("商户{}提现{}后台审核拒绝撤账失败！{}",merchantId,withdrawId,e.getMessage());
      } finally {
        this.close();
      }
      return 0;
    }
}