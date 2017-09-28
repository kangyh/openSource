namespace java com.heepay.rpc.payment.model

struct MerchantBankcardModel {
    1: i64 bankcardId;
    2: i32 bankId;
    3: string bankName;
    4: string bankInfo;
    5: string bankcardNo;
    6: string bankcardOwnerName;
    7: string bankcardOwnerIdcard;
    8: string bankcardOwnerMobile;
    9: byte bankcardOwnerType;
    10: string bankcardExpiredDate;
    11: string createTime;
    12: string updateTime;
    13: string defaultTag;
    14: string type;
    15: string status;
    16: i64 merchantId;
    17: string merchantLoginName;
    18: string merchantCompany;
}
