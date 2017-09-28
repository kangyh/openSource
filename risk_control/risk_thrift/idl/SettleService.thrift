namespace java com.heepay.rpc.risk.service

service SettleService {
   string  pushBlackList(1:string requestInfo);
   string  pushriskInfo(1:string requestInfo);
   string  pushMerchantDiffInfo(1:string requestInfo);
 }