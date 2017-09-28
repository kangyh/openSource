package com.heepay.manage.modules.merchant.rpc.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.enums.InterfaceStatus;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.payment.model.ComplainProcessRecordModel;
import com.heepay.rpc.payment.service.ComplainProcessService;

/**
 * 名称：商户client
 * <p>
 * 创建者  yanxb
 * 创建时间 2016-10-19 16:58:32
 * 创建描述：查询商户信息用
 */
@Service
public class ExceptionRecordClient extends BaseClient {
  
    private static final String SERVICENAME = "ComplainProcessRecordService";

    private static final Logger log = LogManager.getLogger();
    
    private ComplainProcessService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new ComplainProcessService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);
    }
    
    /**
     * 
    * @discription 插入投诉处理表
    * @author yanxb       
    * @created 2016年11月15日 下午3:05:58     
    * @param complainProcessRecordModel
    * @return
     */
    public int insertIntoComplainProcess(ComplainProcessRecordModel complainProcessRecordModel){
        try {
            return getClient().insertIntoComplainProcess(complainProcessRecordModel);
        } catch (TException e) {
      	    log.error("异常处理，插入投诉处理表出错！{}",e.getMessage());
        } finally {
            this.close();
        }
        return InterfaceStatus.FAILED.getValue();
    }
   
    /**
     * 
    * @discription 更新交易单状态
    * @author yanxb       
    * @created 2016年11月16日 下午2:04:53     
    * @param paymentId
    * @param responseCode
    * @param exceptionId
    * @param transType
    * @return
     */
    public int updateTransTable(String paymentId,String responseCode, String exceptionId, String transType,String operator) {
        try {
            return getClient().updateTransTable(paymentId,responseCode, exceptionId, transType,operator);
        } catch (TException e) {
      	    log.error("异常处理，更新交易状态出错！{}",e.getMessage());
        } finally {
            this.close();
        }
        return InterfaceStatus.FAILED.getValue();
    }

    /**
     * 
    * @discription 充值异常处理
    * @author yanxb       
    * @created 2017年1月13日 下午3:42:09     
    * @param exceptionId
    * @param processDesc
    * @param processResult
    * @param operator
    * @return
    * @throws TException
     */
    public String processChargeException(String exceptionId, String processDesc, String processResult, String operator) throws TException {
        try{
            return getClient().processChargeException(exceptionId, processDesc, processResult, operator);
        } catch (TException e){
            log.error("充值投诉异常处理错误！错误原因:{}", e.getMessage());
            return "充值投诉异常处理错误！错误原因：" + e.getMessage();
        } finally {
            this.close();
        }
    }

    /**
     * 
    * @discription 提现异常处理
    * @author yanxb       
    * @created 2017年1月13日 下午3:42:23     
    * @param exceptionId
    * @param processDesc
    * @param processResult
    * @param operator
    * @return
    * @throws TException
     */
    public String processWithdrawException(String exceptionId, String processDesc, String processResult, String operator) throws TException {
        try{
            return getClient().processWithdrawException(exceptionId, processDesc, processResult, operator);
        } catch (TException e){
            log.error("提现投诉异常处理错误！错误原因:{}", e.getMessage());
            return "提现投诉异常处理错误！错误原因：" + e.getMessage();
        } finally {
            this.close();
        }
    }

    /**
     * 
    * @discription 转账异常处理
    * @author yanxb       
    * @created 2017年1月13日 下午3:42:36     
    * @param exceptionId
    * @param processDesc
    * @param processResult
    * @param operator
    * @return
    * @throws TException
     */
    public String processBatchPayException(String exceptionId, String processDesc, String processResult, String operator) throws TException {
        try{
            return getClient().processBatchPayException(exceptionId, processDesc, processResult, operator);
        } catch (TException e){
            log.error("转账投诉异常处理错误！错误原因:{}", e.getMessage());
            return "转账投诉异常处理错误！错误原因：" + e.getMessage();
        } finally {
            this.close();
        }
    }

    /**
     * 
    * @discription 消费异常处理
    * @author yanxb       
    * @created 2017年1月13日 下午3:43:23     
    * @param exceptionId
    * @param processDesc
    * @param processResult
    * @param operator
    * @return
    * @throws TException
     */
    public String processPayException(String exceptionId, String processDesc, String processResult, String operator) throws TException {
        try{
            return getClient().processPayException(exceptionId, processDesc, processResult, operator);
        } catch (TException e){
            log.error("支付投诉异常处理错误！错误原因:{}", e.getMessage());
            return "支付投诉异常处理错误！错误原因：" + e.getMessage();
        } finally {
            this.close();
        }
    }

    public String processRefundException(String exceptionId, String processDesc, String processResult, String operator) throws TException {
        try{
            return getClient().processRefundException(exceptionId, processDesc, processResult, operator);
        } catch (TException e){
            log.error("退款投诉异常处理错误！错误原因:{}", e.getMessage());
            return "退款投诉异常处理错误！错误原因：" + e.getMessage();
        } finally {
            this.close();
        }
    }


}