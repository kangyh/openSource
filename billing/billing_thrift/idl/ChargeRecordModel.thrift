namespace java com.heepay.rpc.payment.model

struct ChargeRecordModel {
    1: i64 chargeId;
    2: i64 accountId;
    3: string accountName;
    4: i64 merchantId;
    5: string merchantLoginName;
    6: string merchantCompany;
    7: string createTime;
    8: string updateTime;
    9: string chargeAmount;
    10: string status;
    11: i64 authId;
    12: string requestIp;
    13: string payRequestIp;
    14: string successTime;
    15: string remark;
    16: string paymentId;
    17:string bankcardNo;
    18:i32 feeId;
    19:string feeRatio;
    20:string feeAmount;
}

struct ChargeRecordRes {
	1: string chargeId;
  	2: string productId;
}


struct ChargeQuerySelect{
	1: string chargeId;
	2: string createTime;
	3: string chargeAmount;
	4: string feeAmount;
	5: string chargeStatus;
	6: string payType;
	7: string refundAmount;
}
struct ChargeQueryResult{
	1: list<ChargeQuerySelect> queryList;
	2: i32 counts;
}
struct ChargeQueryWhere{
	1: string timeFrom;
	2: string timeTo;
	3: string payType;
	4: string chargeStatus;
	5: i32 curPage;
	6: i32 pageSize;
	7: i64 merchantId;
}