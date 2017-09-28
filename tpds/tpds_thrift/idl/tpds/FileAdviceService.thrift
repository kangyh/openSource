namespace java com.heepay.rpc.tpds.service

service FileAdviceService {
	
	string adviceHttp(1:string adviceUrl,2:string systemNo,3:string fileName);
}