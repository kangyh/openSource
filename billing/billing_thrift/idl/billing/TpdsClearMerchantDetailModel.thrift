namespace java com.heepay.rpc.billing.model

struct TpdsClearMerchantDetailModel{
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
	11: string settleTimePlan
	12: string settleAmountPlan
	13: string settleCyc;
	14: string settleBath;	
	15: string feeTime;
	16: string fee;
	17: string feeWay;
	18: string feeSettleCyc;
	19: string checkStatus
	20: string checkFlg
	21: string settleStatus
	22: string finishTime
	23: string checkBath;
	24: string checkNum
	25: string transType;	
}

struct tpdsQueryClearMerchantModel{
    1: string merchantId;
    2: string transType;
    3: string settleStatus;
    4: string checkStatus;
    5: string settleBath;
    6: string transNo;
}