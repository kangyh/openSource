namespace java com.heepay.rpc.billing.model

struct SettleChannelRecordModel {
    1: i64 settleId;
    2: string channelCode;
    3: string channelName;
    4: string channelType;
    5: string currency;
    6: i32 payNum;
    7: string totalAmount;
    8: string checkTime;
    9: string settleTime;
    10: string settleCyc;
    11: string settleBath;
    12: string costTime;
    13: string costAmount;
    14: string costSettleCyc;
    15: string checkStatus;
    16: string settleStatus;
}
