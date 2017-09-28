namespace java com.heepay.rpc.payment.service
include "InnerTransRecordModel.thrift"

service InnerTransRecordService {
	InnerTransRecordModel.InnerTransRecordModel insertInnerTransRecord(1:InnerTransRecordModel.InnerTransRecordModel itrm,2:string returnUrl);
}