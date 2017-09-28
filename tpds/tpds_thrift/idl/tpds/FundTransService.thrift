namespace java com.heepay.rpc.tpds.service

service FundTransService {
	
	string fundTrans(1:string reqHeader,2:string body);
}