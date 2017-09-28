namespace java com.heepay.rpc.payment.service

include "NotifyRecordModel.thrift"


service NotifyRecordService {
 bool insertNotify(1:NotifyRecordModel.NotifyRecordModel notifyRecordModel);

 list<NotifyRecordModel.NotifyRecordModel> queryNotifyRecordListByNotifyStatus(1:list<string> statuslist);
 i32 updateNotifyRecordByNotifyIdPaymentId(1:NotifyRecordModel.NotifyRecordModel notifyRecordModel);

}