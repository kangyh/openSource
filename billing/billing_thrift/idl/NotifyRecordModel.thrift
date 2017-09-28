namespace java com.heepay.rpc.payment.model

struct NotifyRecordModel {
    1: i64 notifyId;
    2: string transNo;
    3: i64 merchantId;
    4: string merchantLoginName;
    5: string merchantCompany;
    6: string merchantOrderNo;
    7: string notifyUrl;
    8: string status;
    9: string notifyRequestParams;
    10: string notifyResponse;
    11: byte notifyNumber;
    12: byte notifyType;
    13: string notifyDate;
    14: string successAmount;
    15: string payResult;
    16: string paymentId;
    17: string updateTime;
    18: string payAmount;
    19: string createTime;
    20: string bankSerialNo;
}