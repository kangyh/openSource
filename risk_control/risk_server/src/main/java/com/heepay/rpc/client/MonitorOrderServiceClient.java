/**
 * 
 */
package com.heepay.rpc.client;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.risk.service.MonitorOrderService;

/**
 * @author Administrator
 *
 */
@Service
public class MonitorOrderServiceClient  extends BaseClientDistribute {
	
	private static final String SERVICENAME = "MonitorOrderServiceImpl";
    private static final String NODENAME = "risk_rpc";
    private static final Logger log = LogManager.getLogger();
    @Resource(name = "riskClient")
    private ThriftClientProxy clientProxy;
	
	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}
	@Override
	public void setNodename() {
		 ClientThreadLocal.getInstance().setNodename(NODENAME);
	}
	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}
	public MonitorOrderService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new MonitorOrderService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

	public String getMonitorOrderInfo(String fields,String orderId) throws TException {
		try {
    		log.info("获取商户监控订单详情------>{}",orderId);
    		String returnStr = getClient().getMonitorOrderInfo( fields , orderId );
    		log.info("获取商户监控订单详情---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
	}
	public  Map<String, List<Map<String, String>>> getMonitorOrderList(String fields, String param) throws TException {
		 Map<String, List<Map<String, String>>> map = null;
		try {
    		log.info("获取商户监控订单列表------>{}",fields,param);
    		map = getClient().getMonitorOrderList(fields, param);
    		log.info("获取商户监控订单列表---返回信息--->{}",map);
    		return map;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
	}

}
