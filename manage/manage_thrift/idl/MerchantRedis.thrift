namespace java com.heepay.manage.rpc.service

service MerchantRedisService {
		string getProductVo(1:string productCode),
	 	string getMerchantVO(1:string merchantId),
	 	string getMerchantProductVO(1:string merchantId,2:string productCode),
	 	string getProductList(1:string trxType),
	 	string getMerchantFeeVO(1:string merchantId,2:string productCode,3:string bankCardType,4:string bankNo,5:string money),
	 	string getMerchantProduct(1:string merchantId,2:string productCode,3:string businessType),
	 	string getMerchantFee(1:string merchantId,2:string productCode,3:string bankCardType,4:string bankNo,5:string money,6:string businessType)
}
