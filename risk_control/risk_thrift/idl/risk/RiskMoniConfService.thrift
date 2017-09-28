namespace java com.heepay.rpc.risk.service

service RiskMoniConfService {
   string  addChannelMonitorConfig(1:string configStr);
   string  editChannelMonitorConfig(1:string configStr);
   string  delChannelMonitorConfig(1:string configStr);
   string  addMerchantMonitorConfig(1:string configStr);
   string  editMerchantMonitorConfig(1:string configStr);
   string  delMerchantMonitorConfig(1:string configStr);
 }