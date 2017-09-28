namespace java com.heepay.rpc.payment.model

struct WithdrawRecordModel {
    1: i64 withdrawId;
    2: i64 merchantId;
    3: string merchantLoginName;
    4: string merchantCompany;
    5: i64 accountId;
    6: string accountName;
    7: string createTime;
    8: string updateTime;
    9: string withdrawAmount;
    10: string status;
    11: i64 authId;
    12: string requestIp;
    13: string successTime;
    14: i32 processId;
    15: string processName;
    16: string processTime;
    17: string paymentId;
    18: string bankcardOwnerName;
    19: string bankcardOwnerMobile;
    20: string bankcardOwnerIdcard;
    21: byte bankcardOwnerType;
    22: string bankcardNo;
    23: string type;
    24:i32 feeId;
    25:string feeRatio;
    26:string feeAmount;
}


struct WithdrawQuerySelect{
	1: string withdrawId;
    2: string createTime;
    3: string withdrawAmount;
    4: string feeAmount;
    5: string bankcardNo;
    6: string withdrawStatus;
}
struct WithdrawQueryResult{
	1: list<WithdrawQuerySelect> queryList;
	2: i32 counts;
}
struct WithdrawQueryWhere{
	1: string timeFrom;
	2: string timeTo;
	3: string withdrawStatus;
	4: i32 curPage;
	5: i32 pageSize;
	6: i64 merchantId;
}