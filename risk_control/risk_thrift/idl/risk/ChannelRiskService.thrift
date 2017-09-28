namespace java com.heepay.rpc.risk.service



service ChannelRiskService {
 	
 	 string getChannelQuotaList(1:string paramJson);
 	 string getChannelList(1:string paramJson);
 	 string getChannelRate(1:string paramJson); 
}