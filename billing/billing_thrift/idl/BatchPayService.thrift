namespace java com.heepay.rpc.payment.service
include "BatchPayModel.thrift"

service BatchPayRecordService {
  string insertBatchPayRecordDetail(1:string data,2:map<string,string> paramMap);
  list<BatchPayModel.BatchPayRecordDetailModel> uploadAndParseExcel(1:string filePath);
  bool sendBatchPayRecordsToGateway();
  list<BatchPayModel.BatchPayRecordModel> queryByConditions(1:string merchantId,2:string beginDate,3:string endDate,4:string status,5:string batchId,6:i32 start,7:i32 count);
  i32 getPayListCountByCons(1:string merchantId,2:string beginDate,3:string endDate,4:string status,5:string batchId,6:i32 start,7:i32 count);
  bool merchantAuditBatchPay(1:string merchantId,2:string auditStatus,3:string batchIdData);
  BatchPayModel.BatchPayQueryResult queryBatchPay(1:BatchPayModel.BatchPayQueryWhere where);
  BatchPayModel.BatchPayDetailQueryResult queryBatchPayDetailsList(1:BatchPayModel.BatchPayDetailQueryWhere where);
}