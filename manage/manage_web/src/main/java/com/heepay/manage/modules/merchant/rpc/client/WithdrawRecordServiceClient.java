package com.heepay.manage.modules.merchant.rpc.client;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.payment.service.WithdrawRecordService;

/**
 * 名称：商户client
 * <p>
 * 创建者  yanxb
 * 创建时间 2016-10-19 16:58:32
 * 创建描述：查询商户信息用
 */
@Service
public class WithdrawRecordServiceClient extends BaseClient {
  
    private static final String SERVICENAME = "WithdrawServiceImpl";
    private static final String NODENAME = "payment_rpc";	
    
    private WithdrawRecordService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new WithdrawRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
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
    * @discription 提现审核失败记录风控日志
    * @author yanxb       
    * @created 2016年12月28日 上午9:30:56     
    * @param merchantId
    * @param withdrawId
    * @return
     * @throws TException 
     */
		public String recordRiskLog(String merchantId, String withdrawId) throws TException {
			try{
				return getClient().recordRiskLog(merchantId,withdrawId);
			} catch(TException e) {
				e.printStackTrace();
			} finally {
				this.close();
			}
			return null;
			
		}
}