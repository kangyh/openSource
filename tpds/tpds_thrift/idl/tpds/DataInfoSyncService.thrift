namespace java com.heepay.rpc.tpds.service
service DataInfoSyncService {
	
	string setCustomerInfoSync(string requestHeader,string requestBody);
	string setBidInfoSync(string requestHeader,string requestBody);
	
}