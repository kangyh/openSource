namespace java com.heepay.rpc.billing.service
include "ClearShareProfitModel.thrift"

service ClearShareProfitService {
	void saveClearShareProfitRecord(1:ClearShareProfitModel.ClearShareProfitModel clearShareProfitModel);
}