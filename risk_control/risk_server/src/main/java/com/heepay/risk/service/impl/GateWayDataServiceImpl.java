/**
 * 
 */
package com.heepay.risk.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.risk_es_engine.ESearchService;
import com.heepay.risk_es_engine.EsBigDataService;
import com.heepay.rpc.risk.service.GateWayDataService;
import com.heepay.rpc.service.RpcService;

/**
 * @author Administrator
 *
 */
@Component
@RpcService(name="BossReportDataServiceImpl",processor=com.heepay.rpc.risk.service.GateWayDataService.Processor.class)
public class GateWayDataServiceImpl implements GateWayDataService.Iface {
	@Autowired
	EsBigDataService esearch;
	
	@Override
	public List<Map<String, String>> getGatewayRecordList(String fields,String equalParam) throws TException {
		// TODO Auto-generated method stub
		return  esearch.getBossDataRecordList(fields, equalParam) ;
	}
	@Override
	public String getBossAggregationInfo(String arg0, String arg1) throws TException {
		// TODO Auto-generated method stub
		return esearch.getBossAggregationInfoNew(arg0, arg1);
	}
	@Override
	public String getBossDataRecordInfo(String arg0, String arg1) throws TException {
		// TODO Auto-generated method stub
		return esearch.getBossDataRecordInfo(arg0, arg1);
	}
}
