namespace java  com.heepay.rpc.billing.model

struct ClearShareProfitModel {
    1: i32 merchantId;
    2: string transNo;
    3: string isShare;
    4: string shareId;
    5: list<ClearShareProfitDetailModel> clearShareProfitDetail;
}

struct ClearShareProfitDetailModel {
    1: string merchantId;
    2: string transNo;
    3: string profitAmount;
    4: string transType;
    5: string shareDetailId;
}
