namespace java com.heepay.manage.rpc.service

struct MerchantFuctionsThrift {
		1:string id,
		2:string functionCode,
		3:string functionName,
		4:string functionUrl,
		5:string functionStatus,
		6:string parentFunctionCode
}

service MerchantFuctionsService {
	 	MerchantFuctionsThrift get(1:string arg0),
	 	MerchantFuctionsThrift save(1:MerchantFuctionsThrift arg0),
	 	list<MerchantFuctionsThrift> findList(1:MerchantFuctionsThrift arg0)
}