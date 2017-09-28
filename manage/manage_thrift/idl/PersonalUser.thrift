namespace java com.heepay.manage.rpc.service


struct PersonalUserThrift {
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
		28:string allowSystem
}

service PersonalUserService {
        string save(1:PersonalUserThrift arg0),
	 	void update(1:PersonalUserThrift arg0),
        PersonalUserThrift queryByLoginName(1:string loginName),
        PersonalUserThrift resetLoginPassword(1:PersonalUserThrift arg0),
        PersonalUserThrift getCertificationStatus(1:PersonalUserThrift arg0),
        PersonalUserThrift resetPayPassword(1:PersonalUserThrift arg0),
        PersonalUserThrift changeLoginName(1:string oldLoginName,2:string newLoginName),

}