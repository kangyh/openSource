namespace java com.heepay.manage.rpc.service

service BankCardBinRedisService {
		string getNameOfBank(1:string cardNo),
		string intercept(1:string cardBinNo,2:string bankcardNote)	 	
}
