namespace java com.heepay.manage.rpc.service

struct HgmsMerchantFuctionsThrift {
		1:string id,
		2:string functionCode,
		3:string functionName,
		4:string functionUrl,
		5:string functionStatus,
		6:string parentFunctionCode
}

service HgmsMerchantFuctionsService {
	 	HgmsMerchantFuctionsThrift get(1:string arg0),
	 	HgmsMerchantFuctionsThrift save(1:HgmsMerchantFuctionsThrift arg0),
	 	list<HgmsMerchantFuctionsThrift> findList(1:HgmsMerchantFuctionsThrift arg0)
}