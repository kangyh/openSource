
namespace java com.heepay.rpc.payment.model

struct QuickPaySignReq {
	1: required i64 merchantId;
	2: i32 bankId;
	3: string bankName;
	4: required string merchantOrderNo; 
	5: required string merchantUserId;
	6: required string ownerIdcard;
	7: required string ownerName;
	8: required string ownerMobile;
	9: required string bankcardNo;
	10: required string amount;
	11: string bankcardType;
	12: string expiredDate;
	13: string cvv2;
	16: string requestUserIp;
	17: string requestTime;
	18: string version;
	19: string signInfo;
	20: string paymentType;
	21: string transType;
	22: string productCode;
	23: string merchantLoginName;
	24: string merchantCompany;
	25: string feeAmount;
}
struct QuickPaySignRes {
  	1: string paymentId;
  	2: string merchantOrderNo; 
  	3: string token;
  	4: i32 retCode;
  	5: string retMsg;
}

struct QuickPaySignConfirmReq {
	1: required i64 merchantId;
	2: i32 bankId;
	3: string bankName;
	4: required string merchantOrderNo; 
	5: required string merchantUserId;
	6: required string ownerIdcard;
	7: required string ownerName;
	8: required string ownerMobile;
	9: required string bankcardNo;
	10: required string amount;
	11: string bankcardType;
	12: string expiredDate;
	13: string cvv2;
	14: string notifyUrl;
	15: string requestUserIp;
	16: string requestTime;
	17: string version;
	18: required string SMSVerify;
	19: string signInfo;
	20: string token;
	21: required string paymentId;
}
struct QuickPaySignConfirmRes {
  	1: string paymentId;
  	2: string merchantOrderNo; 
  	3: string authorizationCode;
  	4: string amount;
  	5: i32 retCode;
  	6: string retMsg;
}

struct QuickPayReq {
	1: required i64 merchantId;
	2: required string merchantOrderNo; 
	3: required string merchantUserId;
	4: required string authorizationCode;
	5: required string amount;
	6: string requestTime;
	7: string version;
	8: string requestUserIp;
	9: string signInfo;
	10: string paymentType;
	11: string transType;
	12: string productCode;
	13: string merchantLoginName;
	14: string merchantCompany;
	15: string feeAmount;
}
struct QuickPayRes {
  	1: string paymentId;
  	2: string merchantOrderNo; 
  	3: string token;
  	4: i32 retCode;
  	5: string retMsg;
}

struct QuickPayConfirmReq {
	1: required i64 merchantId;
	2: required string merchantOrderNo; 
	3: required string merchantUserId;
	4: required string authorizationCode;
	5: required string amount;
	6: string requestTime;
	7: string version;
	8: required string SMSVerify;
	9: string notifyUrl;
	10: string requestUserIp;
	11: string signInfo;
	12: string token;
	13: string paymentId;
}
struct QuickPayConfirmRes {
  	1: string paymentId;
  	2: string merchantOrderNo; 
  	3: string amount;
  	4: i32 retCode;
  	5: string retMsg;
}

struct SignQueryReq{
	1: required i64 merchantId;
	2: required string merchantUserId;
	3: string requestTime;
	4: string version;
	5: string signInfo;
}
struct SignQueryRes{
	1: i32 bankId;
	2: string bankName;
	3: string bankcardNo;
	4: i32 bankcardType;
	5: string authorizationCode;
}

struct TransQueryReq{
	1: required i64 merchantId;
	2: required string merchantOrderNo; 
	3: string requestTime;
	4: string version;
	5: string signInfo;
}
struct TransQueryRes{
	1: i64 merchantId;
	2: string paymentId;
  	3: string merchantOrderNo; 
  	4: string totalAmount;
  	5: string requestTime;
  	6: string successAmount;
  	7: string successTime;
  	8: string status;

}
