namespace java com.heepay.rpc.billing.service
include "ClearMerchantRecordModel.thrift"

service ClearMerchantRecordService {
	
	i32 saveClearMerchantRecord(1:ClearMerchantRecordModel.ClearMerchantRecordModel clearMerchantRecordModel);
	list<ClearMerchantRecordModel.ClearMerchantRecordModel> queryByConditions(1:ClearMerchantRecordModel.ClearMerchantRecordModel clearMerchantRecordModel);
	i32 updateClearMerchantRecord(1:ClearMerchantRecordModel.ClearMerchantRecordModel clearMerchantRecordModel);
	void deleClearMerchantRecord(1:ClearMerchantRecordModel.ClearMerchantRecordModel clearMerchantRecordModel);
	string getClearMerchantRecordMessage(1:ClearMerchantRecordModel.ClearMerchantRecordModel clearMerchantRecordModel);
	void getClearMerchantRecord();
	void settleDataSave(1:string message);
	void saveClearExceptionData(1:string message);
}
