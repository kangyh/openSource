include "MerchantAccountModel.thrift"

namespace java com.heepay.rpc.payment.service

service MerchantAccountService {
  bool insertMerchantAccount(1:MerchantAccountModel.MerchantAccountModel mam);
  MerchantAccountModel.MerchantAccountModel queryById(1:i64 accountId); 
  bool updateMerchantAccount(1:i64 accountId,2:string payAmout);
  string getBalanceAmount(1:i64 merchantId);
  string getMerchantAccountAttr(1:i64 merchantId,2:i32 attrIndex);
  MerchantAccountModel.MerchantAccountModel queryMerchantAccountById(1:i64 merchantId,2:string accountType);
}