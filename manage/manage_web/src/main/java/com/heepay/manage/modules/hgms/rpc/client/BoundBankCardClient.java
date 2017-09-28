package com.heepay.manage.modules.hgms.rpc.client;

import com.heepay.common.util.Constants;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.BankcardAuthModel;
import com.heepay.rpc.payment.service.BankcardAuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @description   绑定银行卡接口--当商户和用户签约之后，需要将签约银行卡信息提交给汇付宝 -- 代收(子商户的银行卡待收到总商户)。
 *
 * @classname      BoundBankCardClient
 * @author         guozx<guozx@9186.com>
 * @date           2017年4月11日   11:57:49
 * @version         1.0
 */
@Service
public class BoundBankCardClient extends BaseClientDistribute {

    private static final String SERVICENAME = "BankcardAuthServiceImpl";
    private static final Logger logger = LogManager.getLogger();


    @Resource(name = "paymentClient")
    private ThriftClientProxy clientProxy;

    @Override
    public ThriftClientProxy getClientProxy() {
        return clientProxy;
    }

    @Override
    public void setServiceName(){
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);
    }

    public BankcardAuthService.Client getClient(){
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new BankcardAuthService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    public int saveBankCardCommon(BankcardAuthModel bankcardAuthModel, String bankCardType){
        BankcardAuthService.Client client = this.getClient();
        try{
            logger.info("绑定银行卡开始,参数为:bankcardAuthModel:{},bankCardType:{}",bankcardAuthModel,bankCardType);
            int i=client.saveBankCardCommon(bankcardAuthModel, bankCardType);
            logger.info("绑定银行卡返回消息为:{}",i);
            return i;
        }catch(Exception e){
            logger.info("插入银行卡信息报错,异常：{}", e);
        }finally{
            this.close();
        }
        return 0;
    }
}
