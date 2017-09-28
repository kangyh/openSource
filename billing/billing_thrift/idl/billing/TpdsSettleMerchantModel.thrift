namespace java com.heepay.rpc.billing.model

struct SettleMerchantDetail{
	1: string merchantId;
	2: string merchantType;
	3: string productCode;
	4: string currency;
	5: string transNo;
	6: string transNoOld;
	7: string requestAmount;
	8: string successTime;
	9: string settleTime;
	10: string settleNo;
	11: string settleBath;
	12: string fee;
	13: string feeWay;
	14: string feeSettleCyc;
	15: string checkBath;
	16: string transType;
	17: string merchantOrderNo;
}

struct TpdsSettleMerchantModel{
    1: string merchantId;
    2: string merchantType;
    3: string transType;
    4: string currency;
    5: i64 payNum;
    6: string totalAmount;
    7: string settleTime;
    8: string settleBath;
    9: string totalFee
	10: list<SettleMerchantDetail> detail;	
}

struct querySettleMerchantModel{
    1: string merchantId;
    2: string transType;
    3: string settleTime;
    4: string settleStatus;
    5: string settleBath; 
}