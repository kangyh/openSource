namespace java com.heepay.rpc.tpds.service

service DataInfoService {
	
	string dataInfoQuery(1:string reqHeader, 2:string body);
}