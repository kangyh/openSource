namespace java com.heepay.rpc.billing.service
include "DoneSettleDifferRecordModel.thrift"

service DoneSettleDifferRecordService {
	
	list<DoneSettleDifferRecordModel.DoneSettleDifferRecordModel> getSettleDifferRecord(1:string differType);
	i32 updateSettleDifferRecor(1:DoneSettleDifferRecordModel.DoneSettleDifferRecordModel doneSettleDifferRecordModel);
	 
}