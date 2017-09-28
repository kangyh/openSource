namespace java com.heepay.rpc.payment.service
include "FundsRecordModel.thrift"

service FundsRecordService{
	list<FundsRecordModel.FundsRecordModel> getCapitalFlows(1:map<string,string> params);
	i32 getCapitalFlowsCount();
	i32 insertFundsRecord(1:FundsRecordModel.FundsRecordModel fundsRecordModel);
	i32 batchInsertFundsRecords(1:list<FundsRecordModel.FundsRecordModel> fundsRecordModel);
}