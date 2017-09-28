namespace java com.heepay.rpc.billing.service

include "TpdsClearMerchantDetailModel.thrift"

service TpdsClearMerchantService{
  list<TpdsClearMerchantDetailModel.TpdsClearMerchantDetailModel> querySettleMerchantRecord(1:TpdsClearMerchantDetailModel.tpdsQueryClearMerchantModel model);
}