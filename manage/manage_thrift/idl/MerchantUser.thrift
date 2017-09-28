namespace java com.heepay.manage.rpc.service


struct MerchantUserThrift {
        1:string id
		2:string loginName,
		3:string loginNickname,
		4:string userType,
		5:string loginPassword,
		6:string payPassword,
		7:string secretQuestion,
		8:string answerSecretQuestion,
		9:string loginIpsAllowed,
		10:string lastLoginIp,
		11:string phone,
		12:string mobile,
		13:string qq,
		14:string linkAddress,
		15:string macInfo,
		16:string diskInfo,
		17:string cpuInfo,
		18:string passGuardCtrlVersion,
		19:string clientType,
		20:string status,
		21:string lastLoginDate,
		22:string permission,
		23:string remarks,
		24:string infoAuthStatus,
		25:string companyName,
		26:string modifyWay,
		27:string source,
		28:string allowSystem,
		29:string merchantFlag,
		30:string inchargerId
}

service MerchantUserService {
	 	MerchantUserThrift get(1:string arg0),		        	
	 	MerchantUserThrift save(1:MerchantUserThrift arg0),		        	
	 	MerchantUserThrift update(1:MerchantUserThrift arg0),
	 	MerchantUserThrift employeeRegist(1:MerchantUserThrift arg0),
	 	list<MerchantUserThrift> findList(1:MerchantUserThrift arg0),
	 	MerchantUserThrift queryEmailExist(1:string arg0),
	 	MerchantUserThrift resetLoginPassword(1:MerchantUserThrift arg0),
	 	MerchantUserThrift getCertificationStatus(1:MerchantUserThrift arg0),
        MerchantUserThrift resetPayPassword(1:MerchantUserThrift arg0),
        MerchantUserThrift queryPhoneNo(1:string arg0),
        MerchantUserThrift changIpMsg(1:MerchantUserThrift merchantUserThrift)
}
