include "WithdrawRecordModel.thrift"

namespace java com.heepay.rpc.payment.service

service WithdrawRecordService{
	bool insertWithdraw(1:WithdrawRecordModel.WithdrawRecordModel draw,2:i64 merchantId,3:string merchantOrderNo,4:string authorizationCode,5:string password);
	bool sendWithdrawRecordsToGateway();
	
	WithdrawRecordModel.WithdrawQueryResult withdrawQuery(1:WithdrawRecordModel.WithdrawQueryWhere where);
	string getFeeAmount(1:i64 merchantId, 2:string productCode, 3:string requestAmount);
}