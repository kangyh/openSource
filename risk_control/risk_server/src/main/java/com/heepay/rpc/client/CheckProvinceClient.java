package com.heepay.rpc.client;

import com.heepay.manage.rpc.service.CheckProvinceService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CheckProvinceClient extends BaseClientDistribute {

    private static final String SERVICENAME = "checkProvinceServiceImpl";

    private static final String NODENAME = "manager_server";

    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "managerClient")
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


    public CheckProvinceService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new CheckProvinceService.Client(ClientThreadLocal.getInstance().getProtocol());
    }


    
    /**
     * @方法说明：根据联行号查询省份
     * @时间： 2017-07-28 17:34
     * @创建人：wangl
     */
    public String selectProvince(String msg) {
        CheckProvinceService.Client client = this.getClient();
        try {
            String result = client.selectProvince(msg);
            logger.info("根据联行号查询省份--->{返回结果}"+result);
            return result;
        } catch (Exception e) {
            logger.error("根据联行号查询省份--->{异常}{}",e.getMessage());
            return null;
        } finally {
            this.close();
        }
    }
}


