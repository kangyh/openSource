/**     
*   Copyright © since 2008. All Rights Reserved 
*   汇元银通（北京）在线支付技术有限公司   www.heepay.com    
*/
    
package com.heepay.manage.modules.merchant.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.route.thrift.quickpay.SingleQueryReq;
import com.heepay.route.thrift.quickpay.SingleQueryRes;
import com.heepay.route.thrift.quickpay.TNewQuickPayService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;


/**          
* 
* 描    述：网关快捷支付接口客户端
*
* 创 建 者： 刘栋  
* 创建时间： 2017年7月19日
* 创建描述：调用网关快捷支付接口
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
public class AdvQuickPayGatewayClient extends BaseClientDistribute {

    private static final String SERVICENAME = "newQuickPayService";

    private static final Logger log = LogManager.getLogger();
  
    @Resource(name = "gatewayClient")
    private ThriftClientProxy clientProxy;

    @Override
    public ThriftClientProxy getClientProxy() {
  	    return clientProxy;
    }
  
    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.GATEWAYRPC);
    }
  
    private TNewQuickPayService.Client getClient() {
        this.setNodename();
        this.setServiceName();
        this.setTMultiplexedProtocol();
        return new TNewQuickPayService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

//    /**
//     * 网关路由接口：获取单次快捷支付的路由通道，如果是快钱类三方，则同时调用其快捷支付请求接口
//     */
//    public SignContractRes gatewayRoute(SignContractReq req){
//        try{
//            req.setReqType("3");
//            return getClient().sign(req);
//        } catch (TException e) {
//            log.error("快捷支付、调用网关路由接口异常：{}", e);
//            return new SignContractRes();
//        } finally {
//            this.close();
//        }
//    }
//
//    /**
//     * 网关签约请求接口：只有当路由到直连银行且未签约的时候调用
//     */
//    public SignContractRes gatewaySignReq(SignContractReq req){
//        try{
//            req.setReqType("1");
//            return getClient().sign(req);
//        } catch (TException e) {
//            log.error("快捷支付、调用网关签约请求接口异常：{}", e);
//            return new SignContractRes();
//        } finally {
//            this.close();
//        }
//    }
//
//    /**
//     * 网关签约确认接口：对签约请求接口的确认
//     */
//    public SignContractRes gatewaySignConfirm(SignContractReq req){
//        try{
//            req.setReqType("2");
//            return getClient().sign(req);
//        } catch (TException e) {
//            log.error("快捷支付、调用网关签约确认接口异常：{}", e);
//            return new SignContractRes();
//        } finally {
//            this.close();
//        }
//    }
//
//    /**
//     * 网关支付接口：发起快捷支付
//     */
//    public NewQuickPayRes gatewayQuickPay(NewQuickPayReq req) {
//        try {
//            return getClient().quickPay(req);
//        } catch (TException e) {
//            log.error("快捷支付、调用网关支付接口异常：{}", e);
//            return new NewQuickPayRes();
//        } finally {
//            this.close();
//        }
//    }

    /**
     * 网关查询接口：快捷支付/退款单笔查询
     */
    public SingleQueryRes gatewayQueryOne(SingleQueryReq req) {
        try {
            return getClient().singleQueryReq(req);
        } catch (TException e) {
            log.error("快捷支付、调用网关单笔查询接口异常：{}", e);
            return new SingleQueryRes();
        } finally {
            this.close();
        }
    }

//    /**
//     * 网关退款接口：发起快捷支付退款
//     */
//    public RefundRes gatewayRefund(RefundReq req) {
//        try {
//            return getClient().refund(req);
//        } catch (TException e) {
//            log.error("快捷支付、调用网关退款接口异常：{}", e);
//            return new RefundRes();
//        } finally {
//            this.close();
//        }
//    }

}
