namespace java com.heepay.manage.rpc.service

struct MerchantOperationLogThrift{
		1:string id,
		2:string merchantId,
		3:string employeeId,
		4:string operateFunction,
		5:string operateBehavior,
		6:string currentIp,
		7:string operateOldData,
		8:string operateNewData,
		9:string headerUserAgent,
		10:string requestUrl
}

service MerchantOperationLogService {
	 	MerchantOperationLogThrift get(1:string arg0),		        	
	 	MerchantOperationLogThrift save(1:MerchantOperationLogThrift arg0),		        	
	 	list<MerchantOperationLogThrift> findList(1:MerchantOperationLogThrift arg0)		        	
}
