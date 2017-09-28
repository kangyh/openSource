namespace java com.heepay.rpc.risk.service

service GateWayDataService {
	list<map<string,string>>  getGatewayRecordList(1:string fieldss,2:string param);
	string getBossDataRecordInfo(1:string fields,2:string tn);
	string getBossAggregationInfo(1:string condition,2:string params);
}