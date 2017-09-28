package com.heepay.rpc.client;


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
import com.heepay.rpc.payment.model.ClearChannelQueryModel;
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
  
  
  /**
   * 根据transNo查出通道侧数据
   */
  public ClearChannelQueryModel channelQueryTransByTransNo(String transNo){
	    try{
	      return getClient().channelQueryTransByTransNo(transNo);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	  }
  
  /**
   * 根据上游流水号交易数据 
   */
  public ClearMerchantQueryRecordModel queryTransByUnionpaySerialNo(String bankNo){
	    try{
	      return getClient().queryTransByUnionpaySerialNo(bankNo);
	    } catch (TException e){
	      log.error(e);
	    } finally {
	      this.close();
	    }
	    return null;
	  }
  /**
   * 
   * @方法说明：财富通退款，通过上游流水号查询交易信息
   * @author xuangang
   * @param bankSeq
   * @return   ClearChannelQueryRecordModel
   * @时间：2017年6月13日下午1:45:53
   */
  public  ClearChannelQueryRecordModel queryTransByBankSerialNo(String bankSeq){
	  try{
		  log.info("财富通退款，通过上游流水号查询交易信息, 上游流水号：{}", bankSeq);
	      return getClient().channelQueryTransByBankSerialNo(bankSeq);
	    } catch (TException e){
	      log.error("财富通退款，通过上游流水号查询交易信息异常，上游流水号：{}", bankSeq, e);
	    } finally {
	      this.close();
	    }
	    return null;
	  }
  
}
