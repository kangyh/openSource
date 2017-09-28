namespace java com.heepay.rpc.boss.service
include "BillingClearModel.thrift"

service BillingClearService {
	
	list<BillingClearModel.BillingClearModel> queryDateFromBilling();
}