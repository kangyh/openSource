namespace java com.heepay.rpc.payment.model

struct InnerTransRecordModel {
    1: string innerTransferId;
    2: string merchantOrderNo;
    3: i64 outAccountId;
    4: string outAccountName;
    5: i64 outMerchantId;
    6: string outMerchantLoginName;
    7: string transferAmount;
    8: i64 inAccountId;
    9: string inAccountName;
    10: i64 inMerchantId;
    11: string inMerchantLoginName;
    12: string createTime;
    13: string updateTime;
    14: string status;
}