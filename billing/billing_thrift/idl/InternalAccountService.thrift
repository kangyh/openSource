include "InternalAccountModel.thrift"
namespace java com.heepay.rpc.payment.service

service InternalAccountService {
    string getAllInternalAccountModelList();
    string getAccountModelListFromCache(1:string key);
    map<string,string> getAccountMap();
}