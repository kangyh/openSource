namespace java com.heepay.rpc.risk.service



service MonitorOrderService {

 	map<string,list<map<string,string>>>  getMonitorOrderList(1:string fields,2:string param);
	string getMonitorOrderInfo(1:string fields,2:string orderId);
 	 
}