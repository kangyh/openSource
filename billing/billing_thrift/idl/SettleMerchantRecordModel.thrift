namespace java com.heepay.rpc.billing.model

struct SettleMerchantRecordModel {
    1: i64 settleId;
    2: i32 merchantId;
    3: string merchantType;
    4: string productCode;
    5: string currency;
    6: i32 payNum;
    7: string totalAmount;
    8: string checkTime;
    9: string settleTime;
    10: string settleCyc;
    11: string settleBath;
    12: string settleAmount;
    13: string feeTime;
    14: string totalFee;
    15: string feeWay;
    16: string feeSettleCyc;
    17: string checkStatus;
    18: string settleStatus;
}

struct ClearDetailMessageModel {
    1: string paymentId;
    2: string successAmount;
}

struct SettleMerchantMessageModel {
    1: list<ClearDetailMessageModel> clearMerchantMessage;
    2: SettleMerchantRecordModel settleMerchantRecord;
}

 