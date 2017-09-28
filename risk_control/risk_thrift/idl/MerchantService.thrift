include "MerchantProductModel.thrift"

namespace java com.heepay.rpc.risk.service

service MerchantService {
   string  getMerchantProduct(1:string merchantId);
   string getMerchantVO(1:string merchantId);
   string getProductList(1:string trxType);
}