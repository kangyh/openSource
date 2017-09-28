namespace java com.heepay.rpc.payment.model

struct BatchPayRecordModel {
    1: string batchId;
    2: i64 accountId;
    3: string accountName;
    4: i64 merchantId;
    5: string merchantLoginName;
    6: string merchantCompany;
    7: string totalAmount;
    8: i32 totalNum;
    9: string createTime;
    10: string updateTime;
    11: string status;
    12: string successTotalAmount;
    13: i32 successTotalNum;
    14: i32 bankOrderId;
    15:i32 feeId;
    16:string feeRatio;
    17:string feeAmount;
}

struct BatchPayRecordDetailModel {
    1: i64 batchPayId;
    2: string batchId;
    3: i64 accountId;
    4: string accountName;
    5: i64 merchantId;
    6: string merchantLoginName;
    7: string merchantCompany;
    8: string createTime;
    9: string updateTime;
    10: string payAmount;
    11: string bankcardNo;
    12: string bankcardOwnerMobile;
    13: string bankcardOwnerName;
    14: string bankcardOwnerIdcard;
    15: i32 bankId;
    16: string bankName;
    17: string status;
    18: string successTime;
    19: string successAmount;
    20: string paymentId;
    21: string bankOrderId;
    22: i16 bankcardOwnerType;
    23: string province;
    24: string city;
    25: string openbankName;
    26: string payReason;
    27: string auditReason;
}
struct BatchPayQuerySelect{
	1: string createTime;
	2: string batchId;
	3: i32 totalNum;
	4: i32 successNum;
	5: string totalAmount;
	6: string successAmount;
	7: string feeAmount;
	8: string batchStatus;
}
struct BatchPayQueryResult{
	1: list<BatchPayQuerySelect> queryList;
	2: i32 counts;
}
struct BatchPayDetailQueryResult{
	1: list<BatchPayRecordDetailModel> queryList;
	2: i32 counts;
}
struct BatchPayQueryWhere{
	1: string timeFrom;
	2: string timeTo;
	3: string batchId;
	4: string status;
	5: i32 curPage;
	6: i32 pageSize;
	7: i64 merchantId;
	8:string finishTimeBegin;
	9:string finishTimeEnd;
}


struct BatchPayDetailQuerySelect{
	1: string createTime;
	2: string batchId;
	3: i32 totalNum;
	4: i32 successNum;
	5: string totalAmount;
	6: string successAmount;
	7: string feeAmount;
	8: string batchStatus;
}

struct BatchPayDetailQueryWhere{
	1: string timeFrom;
	2: string timeTo;
	3: string batchId;
	4: string status;
	5: i32 curPage;
	6: i32 pageSize;
	7: i64 merchantId;
	8:string finishTimeBegin;
	9:string finishTimeEnd;
}