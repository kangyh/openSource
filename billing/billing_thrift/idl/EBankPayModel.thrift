
namespace java com.heepay.rpc.payment.model

struct EBankPayReq {
	1: required i64 merchantId;
	2: required string merchantOrderNo; 
	3: required string merchantUserId;
	5: required string amount;
	6: string requestTime;
	7: string version;
	8: string requestUserIp;
	9: string signInfo;
	10: string requestFrom;
	11: string backUrl;
	12: string notifyUrl;
	13: string bankId;
	14: string paymentType;
	15: string productCode;
	16: string transType;
	17: string bankName;
}
struct EBankPayRes {
  	1: string paymentId;
  	2: string merchantOrderNo; 
  	3: string bankUrl;
  	4: i32 retCode;
  	5: string retMsg;
}

