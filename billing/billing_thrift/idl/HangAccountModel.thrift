namespace java com.heepay.rpc.billing.model

struct HangAccountModel{
	1: i64 merchantId;
    2: string transType;
    3: string paymentId;  
    4: string transNo;
    5: string channelCode;        
    6: string hangInAmount;
    7: string hangInFeeAmount;
  
}