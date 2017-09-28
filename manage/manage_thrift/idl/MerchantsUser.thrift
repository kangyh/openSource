namespace java com.heepay.manage.rpc.service


struct MerchantsUserThrift {
        1:string id
		2:string loginName,
		3:string loginNickname,
		4:string userType,
		5:string loginPassword,
		6:string payPassword,
		7:string secretQuestion,
		8:string answerSecretQuestion,
		9:string phone,
		10:string mobile,
		11:string qq,
		12:string linkAddress,
		13:string status,
		14:string remarks,
		15:string source,
		16:string merchantId,
		17:string idNo,
		18:string realnameAuthSign,
        19:string bankcardAuthSign,
        20:string faceAuth,
        21:string lifeAuth,
        22:string userLevel,
        23:string realName,
        24:string createTime
}

struct MerchantsUserReturnThrift {
        1:i32 code,
        2:string msg,
        3:i64 userId
}

service MerchantsUserService {
        MerchantsUserReturnThrift saveMerchantsUser2(1:string merchantId,2:string loginName,3:string loginPassword,4:string source),
        MerchantsUserReturnThrift saveMerchantsUser(1:string merchantId,2:string loginName,3:string source),
       	MerchantsUserReturnThrift saveMerchantsUserLeve1(1:string merchantId,2:string loginName,3:string idNo,4:string realName,5:string loginPassword),
       
        MerchantsUserReturnThrift setPayPassword(1:string merchantId,2:string userId,3:string payPassword),
        MerchantsUserReturnThrift setLoginPassword(1:string merchantId,2:string userId,3:string loginPassword),
        MerchantsUserReturnThrift resetLoginNameAndPhone(1:string merchantId,2:string userId,3:string phone),
        MerchantsUserReturnThrift verifyPayPassword(1:string merchantId,2:string userId,3:string payPassword),
        MerchantsUserReturnThrift setUserInfoLeve(1:string merchantId,2:string userId,3:string idNo,4:string realName,5:i32 leve),
        MerchantsUserThrift getMerchantsUserThrift(1:string merchantId,2:string userId),
        MerchantsUserThrift queryByLoginNameAndMerchantId(1:string loginName,2:string merchantId),
        MerchantsUserThrift getMerchantsUserThriftByUserId(1:string userId),
        WalletQueryUsersResult queryMerchantsUser(1:WalletUsersWhere walletUsersWhere),
        i32 queryMerchantsUserCounts(1:WalletUsersWhere walletUsersWhere),
}

struct WalletUsersWhere{
	1: string timeFrom;
	2: string timeTo;
	3: string userId;
	4: i32 curPage;
	5: i32 pageSize;
	6: i64 merchantId;
	7: i32 pageNum;
	8: string phone;
	9: string realName;
}

struct WalletQueryUsersResult{
	1: list<MerchantsUserThrift> queryList;
	2: i32 counts;
}

