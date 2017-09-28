namespace java com.heepay.rpc.payment.model

struct MerchantAccountModel {
    1: i64 accountId;
    2: string accountName;
    3: i64 merchantId;
    4: string merchantLoginName;
    5: string type;
    6: string balanceAmount;
    7: string balanceFreezedAmount;
    8: string balanceAvailableAmount;
    9: string balanceAvailableWithdrawAmount;
    10: string totalInAmount;
    11: string totalOutAmount;
    12: string createTime;
    13: string updateTime;
    14: string status;
    15: string description;
    16: string remark;
}
