namespace java com.heepay.rpc.tpds.service
service AsynNoticeService {
	
	string customerChargeAsynNotice(1:string requestHeader,2:string requestBody);
	string customerWithdrawAsynNotice(1:string requestHeader,2:string requestBody);
	string fileAsynNotice(1:string requestHeader,2:string requestBody);
	string bankWithdrawAsynNotice(1:string requestHeader,2:string requestBody);
	string cutDayAsynNotice(1:string requestHeader,2:string requestBody);
	string setPasswordAsynNotice(1:string requestHeader,2:string requestBody);
	string editPasswordAsynNotice(1:string requestHeader,2:string requestBody);
	string resetPasswordAsynNotice(1:string requestHeader,2:string requestBody);
}