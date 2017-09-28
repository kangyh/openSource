namespace java com.heepay.rpc.tpds.service

service BankWQService {
	
	string bankWithdraw(1:string requestHeader,2:string requestBody);
	string bankStatusQuery(1:string requestHeader,2:string requestBody);
}