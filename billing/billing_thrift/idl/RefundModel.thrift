namespace java com.heepay.rpc.payment.model

struct RefundApplyReq{
	1: required i64 merchantId;
	2: required string merchantOrderNo;
	3: required string requestTime;
	4: string merchantLoginName;
	5: string merchantCompany;
	6: string version;
	7: string notifyUrl;
	8: string amount;
	9: string refundDetails;
	10: string requestUserIp;
	11: string signInfo;
	12: string refundFrom;
}
struct RefundApplyRes{
	1: i64 merchantId;
	2: string merchantOrderNo;
	3: i64 refundId;
	4: string refundAmount;
	5: i32 retCode;
	6: string retMsg;
}

struct RefundQueryReq{
	1: required i64 merchantId;
	2: required string merchantOrderNo;
	3: required string requestTime;
	4: string version;
	5: string requestUserIp;
	6: string signInfo;
}
struct RefundQueryRes{
	1: i64 merchantId;
	2: string merchantOrderNo;
	3: i64 refundId;
	4: string refundAmount;
	5: string refundStatus;
	6: string refundTime;
	7: i32 retCode;
	8: string retMsg;
}

struct RefundAuthReq{
	1: i64 refundId;
	2: i32 processId;
	3: string processName;
	4: string processTime;
	5: bool authResult;
}