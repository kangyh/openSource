namespace java com.heepay.rpc.payment.service
include "TransManagerModel.thrift"

service TransManagerService{
	TransManagerModel.TransQueryResult transQuery(1:TransManagerModel.TransQueryWhere where);
	TransManagerModel.TransDetailsSelect transDetails(1:string gatewayId);
	TransManagerModel.RefundQueryResult refundQuery(1:TransManagerModel.RefundQueryWhere where);
}