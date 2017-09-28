namespace java com.heepay.rpc.payment.service
include "QuickPayModel.thrift"

service QuickPayService {
  	QuickPayModel.QuickPaySignRes quickPaySign(1:QuickPayModel.QuickPaySignReq quickPaySignReq);
  	QuickPayModel.QuickPaySignConfirmRes quickPaySignConfirm(1:QuickPayModel.QuickPaySignConfirmReq quickPaySignConfirmReq);
  	QuickPayModel.QuickPayRes quickPay(1:QuickPayModel.QuickPayReq quickPayReq);
  	QuickPayModel.QuickPayConfirmRes quickPayConfirm(1:QuickPayModel.QuickPayConfirmReq quickPayConfirmReq);
	QuickPayModel.SignQueryRes signQuery(1:QuickPayModel.SignQueryReq signQueryReq);
	QuickPayModel.TransQueryRes transQuery(1:QuickPayModel.TransQueryReq transQueryReq);
	string getFeeAmount(1:i64 merchantId, 2:string productCode, 3:string payAmount);
}