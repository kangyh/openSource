namespace java com.heepay.rpc.billing.service

include "ClearingChannelRecordModel.thrift"


service ClearingChannelRecordService {
  string queryChannelSettleStatusByTransNo(1:string transNo);
}