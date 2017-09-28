/**
 * 
 */
package com.heepay.risk.service.impl;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.risk_es_engine.EsMerchantService;
import com.heepay.rpc.risk.service.MonitorOrderService;
import com.heepay.rpc.service.RpcService;
/**
 * @author Administrator
 *
 */
@Component
@RpcService(name="MonitorOrderServiceImpl",processor=MonitorOrderService.Processor.class)
public class MonitorOrderServiceImpl implements MonitorOrderService.Iface {

	@Autowired
	EsMerchantService esMerchantService;
	@Override
	public String getMonitorOrderInfo(String fields , String orderId) throws TException {
		return esMerchantService.queryMerMoniInfoDetail(fields, orderId);
	}
	
	@Override
	public  Map<String, List<Map<String, String>>> getMonitorOrderList(String fields, String param) throws TException {
		return esMerchantService.queryMerMoniInfoList(fields,param);
	}

}
