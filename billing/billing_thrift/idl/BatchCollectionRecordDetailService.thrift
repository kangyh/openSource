include "BatchCollectionRecordDetailModel.thrift"

namespace java com.heepay.rpc.payment.service

service BatchCollectionRecordDetailService{
	BatchCollectionRecordDetailModel.CollectionRecordRes insertBatchCollectionRecordDetail(1:string jsonStr,2:map<string,string> paramMap);
	list<BatchCollectionRecordDetailModel.BatchCollectionRecordDetailModel> uploadAndParseExcel(1:string filePath);
	bool sendBatchCollectionReocrdsToGateway();
	bool settleAccountToMerchant();
	list<BatchCollectionRecordDetailModel.BatchCollectionRecordModel> queryByConditions(1:string merchantId,2:string beginDate,3:string endDate,4:string status,5:string batchId,6:i32 start,7:i32 count);
	BatchCollectionRecordDetailModel.BatchCollectionQueryResult batchCollectionQuery(1:BatchCollectionRecordDetailModel.BatchCollectionQueryWhere where);
	string getFeeAmount(1:i64 merchantId, 2:string prodectCode, 3:string requestAmount);
}