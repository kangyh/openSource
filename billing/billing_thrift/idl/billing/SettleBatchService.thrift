namespace java com.heepay.rpc.billing.service

include "SettleBatchMsgModel.thrift"


service SettleBatchService {
  SettleBatchMsgModel.SettleBatchMsgModel queryMerchantSettleBatch(1:string settleBatch, 2:i32 pageNum, 3:i32 pageSize);
  
  SettleBatchMsgModel.SettleBatchMsgModel queryChannelSettleBatch(1:string settleBatch, 2:i32 pageNum, 3:i32 pageSize);
}