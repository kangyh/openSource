namespace java com.heepay.rpc.billing.service
include "ClearChannelRecordModel.thrift"

service ClearChannelRecordService {
	
	void saveClearChannelRecord(1:ClearChannelRecordModel.ClearChannelRecordModel clearChannelRecordModel);
	list<ClearChannelRecordModel.ClearChannelRecordModel> query(1:ClearChannelRecordModel.ClearChannelRecordModel clearChannelRecordModel);
	void update(1:ClearChannelRecordModel.ClearChannelRecordModel clearChannelRecordModel);
	void deleteBankcard(1:ClearChannelRecordModel.ClearChannelRecordModel clearChannelRecordModel);

}
