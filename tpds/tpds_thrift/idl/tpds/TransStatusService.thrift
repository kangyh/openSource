namespace java com.heepay.rpc.tpds.service

service TransStatusService {
	
	string transStatusQuery(1:string reqHeader, 2:string body);
}