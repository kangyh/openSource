package com.heepay.manage.modules.merchant.rpc.client;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.payment.model.AsyncMsgModel;
import com.heepay.rpc.payment.service.PaymentAPIService;

/**
 * 名称：记账client
 * <p>
 * 创建者  yanxb
 * 创建时间 2016-10-19 16:58:32
 * 创建描述：查询商户信息用
 */
@Service
public class PayemntRpcClient extends BaseClient {
  
    private static final String SERVICENAME = "PaymentAPIServiceImpl";
    private static final String NODENAME = "payment_rpc";
    
    private PaymentAPIService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new PaymentAPIService.Client(ClientThreadLocal.getInstance().getProtocol());
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
    * @discription 补账
    * @author yanxb       
    * @created 2016年11月11日 下午3:52:05     
    * @param paymentId
    * @param payAmount
    * @param feeAmount
    * @param operator
    * @param bankSerialNo
    * @return
    * @throws TException
     */
    public AsyncMsgModel suppleChannelTrans(String paymentId, String payAmount, String feeAmount, 
    		String operator,String bankSerialNo) throws TException{
    	try{
    		return getClient().suppleTransFromManage(paymentId, payAmount, feeAmount, operator,bankSerialNo);
		} catch(TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return null;
    	
    }
    
    /**
     * 
    * @discription 撤账
    * @author yanxb       
    * @created 2016年11月11日 下午3:53:21     
    * @param paymentId
    * @param payAmount
    * @param feeAmount
    * @param operator
    * @param bankSerialNo
    * @return
    * @throws TException
     */
    public AsyncMsgModel cancelTransFromManage(String paymentId, String payAmount, String feeAmount, 
    		String operator,String bankSerialNo) throws TException{
    	try{
    		return getClient().cancelTransFromManage(paymentId, payAmount, feeAmount, operator,bankSerialNo);
		} catch(TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return null;
    	
    }
}