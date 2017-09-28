package com.heepay.manage.modules.merchant.rpc.client;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.AuthRes;
import com.heepay.rpc.payment.model.InternalAuthReq;
import com.heepay.rpc.payment.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/***
 * 
* 
* 描    述：手机实名认证
*
* 创 建 者： wangl
* 创建时间： 24/07/2017 10:50
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
public class MobileAuthenticationClient extends BaseClientDistribute {

    private static final String SERVICENAME = "AuthenticationServiceImpl";

    private static final String NODENAME = "payment_rpc";

    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "paymentClient")
    private ThriftClientProxy clientProxy;

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }

    @Override
    public ThriftClientProxy getClientProxy() {
        return clientProxy;
    }


    public AuthenticationService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new AuthenticationService.Client(ClientThreadLocal.getInstance().getProtocol());
    }



    /**
     * @方法说明：岂安手机实名认证
     * @时间： 24/07/2017 10:52
     * @创建人：wangl
     */
    public AuthRes internalMobileAuthQiAn(InternalAuthReq req) {
        AuthenticationService.Client client = this.getClient();
        try {
            AuthRes authRes = client.internalMobileAuthQiAn(req);
            logger.info("岂安手机实名认证--->{调取交易接口}--->{返回结果}",authRes);
            return authRes;
        } catch (Exception e) {
            logger.error("岂安手机实名认证--->{异常}--->{}",e);
            return null;
        } finally {
            this.close();
        }
    }


   /**
    * @方法说明：考拉手机二三四要素手机认证
    * @时间： 2017-08-11 10:29
    * @创建人：wangl
    */
    public AuthRes mobileAuthKaola(InternalAuthReq req) {
        AuthenticationService.Client client = this.getClient();
        try {
            AuthRes authRes = client.mobileAuthKaola(req);
            logger.info("考拉手机二三四要素手机认证--->{调取交易接口}--->{返回结果}",authRes);
            return authRes;
        } catch (Exception e) {
            logger.error("考拉手机二三四要素手机认证--->{异常}--->{}",e);
            return null;
        } finally {
            this.close();
        }
    }
}