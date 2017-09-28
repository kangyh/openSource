namespace java com.heepay.rpc.warning.service

service WarningService {
   string  prepareMsg();
   string  sendMsg();
   string  sendWaringMsg(1:string msgEntity);
   string getInfoGroup(1:string groupCode);
 }