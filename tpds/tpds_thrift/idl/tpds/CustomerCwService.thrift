namespace java com.heepay.rpc.tpds.service

service CustomerCwService {
	
	string customerCharge(1:string requestHeader,2:string requestBody);
	string customerWithdraw(1:string requestHeader,2:string requestBody);
}