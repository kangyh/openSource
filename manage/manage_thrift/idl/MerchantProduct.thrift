namespace java com.heepay.manage.rpc.service

struct MerchantProductThrift{
	1:string merchantId,
	2:string productCode,
	3:string productName,
	4:string validityDateEnd,
	5:string validityDateBegin,
	6:string fee,
	7:string remark,
	8:string trxType,
	9:string businessType
}

service MerchantProductService {
    list<MerchantProductThrift> getMerchantProduct(1:string merchantId),
    string getProductKey(1:string merchantId,2:string productCode,3:string businessType),
    string getResetProductKey(1:string merchantId,2:string productCode,3:string type),
   	string resetProductKey(1:string merchantId,2:string productCode,3:string key,4:string businessType)
}