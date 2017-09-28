namespace java com.heepay.rpc.payment.service
include "ChargeRecordModel.thrift"
include "BankcardAuthModel.thrift"

service ChargeRecordService {
	ChargeRecordModel.ChargeRecordRes insertChargeRecord(1:ChargeRecordModel.ChargeRecordModel crm);
	list<BankcardAuthModel.BankcardAuthModel> getBankcardListByMerchantId(1:string merchantId);
	bool acceptResult(1:string chargeId,2:string status,3:string chargeAmount);
	string getFeeAmount(1:i64 merchantId,2:string productCode,3:string amount);
	
	ChargeRecordModel.ChargeQueryResult chargeQuery(1:ChargeRecordModel.ChargeQueryWhere where);
}