namespace java com.heepay.rpc.billing.model

struct DoneSettleDifferRecordModel{
	1: i64 differId;
	2: i32 merchantId;
	3: string paymentId;
	4: string transNo;
    5: string channelCode;  
    6: string channelName;
    7: string successAmount; 
    8: string requestAmountAmount      
    9: string checkBathno;
    10: string settleBath;
    11: string channelType;
    12: string productCode;
    13: string productName;
    14: string channleNo;
    15: string differType;
    16: string handleResult;
    17: string handleMessage;
    18: string errorDate;
    19: string operationDate;
    20: string feeAmount
    21: string costAmount
    22: string settleAmountPlan;
    23: string transType;
    24: string transStatus;
    25: string isBill;
    
}

  