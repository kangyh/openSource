namespace java com.heepay.rpc.payment.service
include "RefundModel.thrift"

service RefundService{
	list<RefundModel.RefundApplyRes> refundApply(1: list<RefundModel.RefundApplyReq> req);
	RefundModel.RefundQueryRes refundQuery(1: RefundModel.RefundQueryReq req);
	i32 refundAuth(1: RefundModel.RefundAuthReq req);
	string getFeeAmount(1:i64 merchantId, 2:string productCode, 3:string requestAmount);
}