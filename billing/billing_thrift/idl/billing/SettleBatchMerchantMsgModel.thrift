namespace java com.heepay.rpc.billing.model

struct SettleBatchMsgModel {
    1: i64 total;
    2: string settleBatch;
    3: i32 pageSize;
    4: i32 pageNum;
    5: list<SettleMerchantModel> settleMerchantList;
    6: list<SettleChannelModel> settleChannelList;
}


struct SettleMerchantModel {
    1: string transNo;
    2: string successAmount;
    3: string transType;
    4: string feeAmount;
    5: string requestAmount;
    6: string successTime;
}

struct SettleChannelModel {
    1: string transNo;
    2: string successAmount;
    3: string transType;
    4: string costAmount;
    5: string paymentId;
}
