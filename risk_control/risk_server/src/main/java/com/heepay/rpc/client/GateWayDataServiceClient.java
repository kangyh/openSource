package com.heepay.rpc.client;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.service.GateWayDataService;

/**
 *
 */
@Service
public class GateWayDataServiceClient extends BaseClientDistribute {
    private static final String SERVICENAME = "BossReportDataServiceImpl";
    private static final String NODENAME = "gateWay_rpc";
    private static final Logger log = LogManager.getLogger();
    @Resource(name = "gateWayDataClient")
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

    public GateWayDataService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new GateWayDataService.Client(ClientThreadLocal.getInstance().getProtocol());
    }
   
    
    /**
     * 
     * 获取网关单列表
     */
    public List<Map<String,String>> getGatewayRecordList(String fields,String param){
    	try {
    		log.info("获取网关单列表------>{}");
    		List<Map<String,String>> list = getClient().getGatewayRecordList(fields, param);
    		log.info("获取网关单列表---返回信息--->{}");
    		return list;
        } catch (TException e) {
            log.error(e);
            return null;
        } finally {
            this.close();
        }
    }

}
