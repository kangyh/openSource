package com.heepay.billing.client;


import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.enums.OperatorType;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.AsyncMsgModel;
import com.heepay.rpc.payment.model.ClearChannelQueryRecordModel;
import com.heepay.rpc.payment.model.ClearMerchantQueryRecordModel;
import com.heepay.rpc.payment.service.BillingClearAPIService;

@Service
public class BillingClearAPIClient extends BaseClientDistribute{
	
private static final String SERVICENAME = "BillingClearAPIServiceImpl";
	
	private static final String NODENAME = "payment_rpc";
	
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "paymentbillapiClient")
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
  
  
  public BillingClearAPIService.Client getClient(){
    this.setServiceName();
    this.setNodename();
    this.setTMultiplexedProtocol();
    return new BillingClearAPIService.Client(ClientThreadLocal.getInstance().getProtocol());
  }
  /**
   * 通道侧撤单
   * @param chargeId
   * @return
   */
  public AsyncMsgModel cancelChannelTrans(String paymentId,String payAmount, String feeAmount){
    try{
      return getClient().cancelTrans(paymentId, payAmount, feeAmount, OperatorType.BILLIN.getValue());
    } catch (TException e){
      log.error(e);
    } finally {
      this.close();
    }
    return null;
  }
  /**
   * 用户侧撤单
   * @param chargeId
   * @return
   */
  public AsyncMsgModel cancelMerchantTrans(String paymentId,String payAmount, String feeAmount){
    try{
      return getClient().cancelTrans(paymentId, payAmount, feeAmount, OperatorType.BILLIN.getValue());
    } catch (TException e){
      log.error(e);
    } finally {
      this.close();
    }
    return null;
  }
  
  /**
   * 通道补单
   * @param chargeId
   * @return
   */
  public AsyncMsgModel suppleChannelTrans(String paymentId, String payAmount, String feeAmount){
    try{
      return getClient().suppleTrans(paymentId, payAmount, feeAmount, OperatorType.BILLIN.getValue());
    } catch (TException e){
      log.error(e);
    } finally {
      this.close();
    }
    return null;
  }
  /**
   * 用户补单
   * @param chargeId
   * @return
   */
  public AsyncMsgModel suppleMerchantTrans(String paymentId, String payAmount, String feeAmount){
    try{
      return getClient().suppleTrans(paymentId, payAmount, feeAmount, OperatorType.BILLIN.getValue());
    } catch (TException e){
      log.error(e);
    } finally {
      this.close();
    }
    return null;
  }
  /**
   * 查询用户测交易信息
   * @param chargeId
   * @return
   */
  public ClearMerchantQueryRecordModel merchantQueryTransByPaymentId(String chargeId){
	    try{    
	      return getClient().merchantQueryTransByPaymentId(chargeId);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	  }
  /**
   * 查询通道交易信息
   * @param chargeId
   * @return
   */
  public ClearChannelQueryRecordModel channelQueryTransByPaymentId(String chargeId){
	    try{
	      return getClient().channelQueryTransByPaymentId(chargeId);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	  }
  
  
}
