namespace java com.heepay.rpc.payment.model

struct TransQuerySelect{
	1: string gatewayId;
	2: string merchantOrderNo;
	3: string accountName;
	4: string createTime;
	5: string successTime;
	6: string requestAmount;
	7: string businessType;
	8: string transStatus;
}
struct TransQueryResult{
	1: list<TransQuerySelect> queryList;
	2: i32 counts;
}
struct TransQueryWhere{
	1: string timeFrom;
	2: string timeTo;
	3: string paymentType;
	4: string status;
	5: i32 pageSize;
	6: i32 pageNum;
	7: i64 merchantId;
}

struct TransDetailsSelect{
	1: string merchantOrderNo;
	2: string gatewayId;
	3: string accountName;
	4: string requestAmount;
	5: string createTime;
	6: string successTime;
	7: string paymentType;
	8: string bankSerialNo;
	9: string transStatus;
	10: string notifyStatus;
	11: string canRefundAmount;
}

struct RefundQuerySelect{
	1: string refundId;
	2: string gatewayId;
	3: string merchantOrderNo;
	4: string transCreateTime;
	5: string refundCreateTime;
	6: string refundAmount;
	7: string refundStatus;
}
struct RefundQueryResult{
	1: list<RefundQuerySelect> queryList;
	2: i32 counts;
}
struct RefundQueryWhere{
	1: string timeFrom;
	2: string timeTo;
	3: string timeBy;
	4: string merchantOrderNo;
	5: string refundStatus;
	6: i32 pageSize;
	7: i32 pageNum;
	8: i64 merchantId;
}
