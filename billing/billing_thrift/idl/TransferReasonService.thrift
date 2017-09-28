namespace java com.heepay.rpc.payment.service
include "TransferReasonModel.thrift"

service TransferReasonService {
	TransferReasonModel.TransferReasonModel insertTransferReason(1:TransferReasonModel.TransferReasonModel trm,2:string creator);
	list<TransferReasonModel.TransferReasonModel> queryTransferReasonListByMerchantId(1:string merchantId);
}