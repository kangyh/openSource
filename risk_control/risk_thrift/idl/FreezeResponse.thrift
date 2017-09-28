namespace java com.heepay.rpc.risk.model
include "TransactionModel.thrift"

struct FreezeResponse {
	1:i64 totalCount;
    2:list<TransactionModel.TransactionModel> transList;
}