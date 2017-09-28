
namespace java com.heepay.rpc.billing.model

struct ClearChannelRecordModel {
	1: required i64 clearingId;
	2: string channelCode;
	3: string channelName;
	4: string channelType; 
	5: string payTime;
	6: string currency;
	7: string paymentId;
	8: string paymentIdOld;
	9: string transNo;
	10: string transNoOld;
	11: string successAmount;
	12: string channelTime;
	13: string checkTime;
	16: string settleTime;
	17: string settleNo;
	18: string settleTimePlan;
	19: string settleCyc;
	20: string settleBath;
	21: string costTime;
	22: string costAmount;
	23: string costWay;
	24: string costSettleCyc;
	25: string checkStatus;
	26: string checkFlg;
	27: string settleStatus;
	28: string finishTime;
	29: string transType;
	30: string bankSeq;
	31: string bankName;
	32: string bankCode;
	33: string channelProvider;
}