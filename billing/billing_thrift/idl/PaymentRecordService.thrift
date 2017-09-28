namespace java com.heepay.rpc.payment.service

include "PaymentRecordModel.thrift"


service PaymentRecordService {
  PaymentRecordModel.PaymentRecordModel queryById(1:string id);
  list<PaymentRecordModel.PaymentRecordModel> queryInvalidPaymentRecordListByStatusAndTimeOut(1:string status,2:i32 timeoutMinute); 
  i32 updateTimeOut(1:string paymentId);
  i32 batchInsert(1:list<PaymentRecordModel.PaymentRecordModel> paymentRecordModellist);
}