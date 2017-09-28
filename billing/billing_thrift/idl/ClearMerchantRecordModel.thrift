
namespace java com.heepay.rpc.billing.model

struct ClearMerchantRecordModel{
	1: required i64 clearingId;
	2: string productCode;
	3: string requestAmountAmount;
	4: string merchantType; 
	5: string checkTime;
	6: string currency;
	7: string feeTime;
	8: string fee;
	9: string transNo;
	10: string transNoOld;
	11: string feeWay;
	12: string settleTime;
	13: string settleNo;
	14: string settleTimePlan;
	15: string settleCyc;
	16: string settleBath;
	17: string feeSettleCyc;
	18: string checkStatus;
	19: string checkFlg;
	20: string settleStatus;
	21: string finishTime;
	22: string settleAmountPlan;
	23: i32 merchantId;
    24: string transType;
    25: string successAmount;
    26: string successTime;
    27: string merchantOrderNo;
}