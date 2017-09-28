namespace java com.heepay.rpc.payment.model

struct BatchCollectionRecordDetailModel{
	1:required i64 collectId;
	2: i64 accountId;
	3: string accountName;
	4: i64 merchantId;
	5: string merchantLoginName;
	6: string merchantCompany;
	7: string createTime;
	8: string updateTime;
	9: i64 authId;
	10: string collectAmount;
	11: string bankcardNo;
	12: string bankcardOwnerMobile;
	13: string bankcardOwnerName;
	14: string bankcardOwnerIdcard;
	15: string bankName;
	16: string batchId;
	17: string status;
	18: string successTime;
	19: string successAmount;
	20: i32 bankId;
}

struct BatchCollectionRecordModel {
	1:required string batchId;
	2:i64 accountId;
	3:string accountName;
	4:i64 merchantId;
	5:string merchantLoginName;
	6:string merchantCompany;
	7:string totalAmount;
	8:i32 totalNum;
	9:string createTime;
	10:string updateTime;
	11:string status;
	12:string successTotalAmount;
	13:i32 successTotalNum;
	14:i32 bankOrderId;
	15:i32 bankId;
	16:i32 feeId;
    17:string feeRatio;
    18:string feeAmount;
}

struct CollectionRecordRes {
  	1: string batchId;
  	2: string merTransId;
  	3: string token;
  	4: i32 retCode;
  	5: string retMsg;
}

struct AuditRes {
  	1: string isAuditSucc;
  	2: i64 collectId;
  	3: string batchId;
}

struct PayRes {
  	1: i64 collectId;
  	2: string isSucc;
  	3: string succAmount;
  	4: string batchId;
  	5: string serialNo;
}


struct BatchCollectionQuerySelect{
	1: string createTime;
	2: string batchId;
	3: i32 totalNum;
	4: i32 successNum;
	5: string totalAmount;
	6: string successAmount;
	7: string feeAmount;
	8: string batchStatus;
}
struct BatchCollectionQueryResult{
	1: list<BatchCollectionQuerySelect> queryList;
	2: i32 counts;
}
struct BatchCollectionQueryWhere{
	1: string timeFrom;
	2: string timeTo;
	3: string batchId;
	4: string status;
	5: i32 curPage;
	6: i32 pageSize;
	7: i64 merchantId;
}


struct BatchCollectionDetailQuerySelect{
	1: string createTime;
	2: string batchId;
	3: i32 totalNum;
	4: i32 successNum;
	5: string totalAmount;
	6: string successAmount;
	7: string feeAmount;
	8: string batchStatus;
}
struct BatchCollectionDetailQueryResult{
	1: list<BatchCollectionQuerySelect> queryList;
	2: i32 counts;
}
struct BatchCollectionDetailQueryWhere{
	1: string timeFrom;
	2: string timeTo;
	3: string batchId;
	4: string status;
	5: i32 curPage;
	6: i32 pageSize;
	7: i64 merchantId;
}

