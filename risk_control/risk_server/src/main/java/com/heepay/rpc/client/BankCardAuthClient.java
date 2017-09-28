package com.heepay.rpc.client;

import com.heepay.rpc.payment.service.BankcardAuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-08-07 13:34
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Service
public class BankCardAuthClient  extends BaseClientDistribute {

    private static final String SERVICENAME = "BankcardAuthServiceImpl";

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


    public BankcardAuthService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new BankcardAuthService.Client(ClientThreadLocal.getInstance().getProtocol());
}


    /**
     * @方法说明：根据银行卡号查询联行号
     * @时间： 2017-07-28 17:34
     * @创建人：wangl
     */
    public String getLineNoByBankCardNo(String msg) {
        BankcardAuthService.Client client = this.getClient();
        try {
            String result = client.getLineNoByBankCardNo(msg);
            logger.info("根据银行卡号查询联行号--->{返回结果}" + result);
            return result;
        } catch (Exception e) {
            logger.error("根据银行卡号查询联行号--->{异常}{}", e.getMessage());
            return null;
        } finally {
            this.close();
        }
    }
}
