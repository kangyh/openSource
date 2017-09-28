include "FreezeResponse.thrift"

namespace java com.heepay.rpc.risk.service

service TransactionService {
   FreezeResponse.FreezeResponse  getTransactionList(1:string whereCause,2:i32 pageIndex,3:i32 pageSize);
}