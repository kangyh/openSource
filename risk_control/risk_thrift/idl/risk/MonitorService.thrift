namespace java com.heepay.rpc.risk.service



service MonitorService {

 	 string getNoOrderWarning();
 	 string getOrderSuccessRateWarning();
 	 string getChannelSuccessRateWarning();
 	 string getPaymentNoLogWarning();
}