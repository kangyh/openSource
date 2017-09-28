namespace java com.heepay.rpc.billing.service

include "ClearingMerchantRecordModel.thrift"


service ClearingMerchantRecordService {
  string queryMerchantSettleStatusByTransNo(1:string transNo);
}