namespace java com.heepay.rpc.payment.model

struct FundsRecordModel {
    1: i64 recordId;
    2: i64 accountId;
    3: string accountName;
    4: i64 merchantId;
    5: string merchantName;
    6: string type;
    7: string transNo;
    8: string paymentId;
    9: string balanceAmount;
    10: string balanceFreezedAmount;
    11: string balanceAvailableAmount;
    12: string balanceAvailableWithdrawAmount;
    13: string totalInAmount;
    14: string totalOutAmount;
    15: string createTime;
    16: string updateTime;
    17: string description;
    18: string remark;
}