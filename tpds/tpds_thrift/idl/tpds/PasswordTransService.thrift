namespace java com.heepay.rpc.tpds.service

service PasswordTransService {
	
	string passwordSetting(1:string reqHeader, 2:string body);	
	string passwordModify(1:string reqHeader, 2:string body);
	string passwordResetting(1:string reqHeader, 2:string body);
	string passwordVerify(1:string reqHeader, 2:string body);
	string passwordVerifyBack(1:string reqHeader, 2:string body);	
	string authCodeVerify(1:string reqHeader, 2:string body);
}