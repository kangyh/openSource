namespace java com.heepay.rpc.billing.service

include "TpdsSettleMerchantModel.thrift"

service TpdsSettleMerchantService{
  list<TpdsSettleMerchantModel.TpdsSettleMerchantModel> querySettleMerchantRecord(1:TpdsSettleMerchantModel.querySettleMerchantModel querySettleMerchantModel);
}