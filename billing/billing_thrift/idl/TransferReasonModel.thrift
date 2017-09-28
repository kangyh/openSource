namespace java com.heepay.rpc.payment.model

struct TransferReasonModel {
    1: i64 transferReasonId;
    2: string transferReason;
    3: string creator;
    4: i64 merchantId;
    5: string createTime;
    6: string remark;
}

