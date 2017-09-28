namespace java com.heepay.rpc.payment.model

struct GatewayRecordModel {
    1: string gatewayId;
    2: i64 merchantId;
    3: string merchantLoginName;
    4: string merchantCompany;
    5: string merchantUserId;
    6: string merchantUserName;
    7: string merchantOrderNo;
    8: string version;
    9: string requestDate;
    10: string requestIp;
    11: string requestUserIp;
    12: string notifyUrl;
    13: string callbackUrl;
    14: byte requestType;
    15: string createTime;
    16: string requestAmount;
    17: string successTime;
    18: string successAmount;
    19: string type;
    20: string status;
    21: string note;
    22: string feeId;
    23: string feeRadio;
    24: string feeAmount;
    25: string riskcontrolType;
    26: string riskcontrolReason;
    27: string authorizationCode;
}

struct GatewayQueryParam{
	1: string transNo;
	2: string merchantOrderNo;
	3: string timeBegin;
	4: string timeEnd;
	5: string requestAmount;
	6: string requestAmountTo;
	7: byte type;
	8: byte status;
}
