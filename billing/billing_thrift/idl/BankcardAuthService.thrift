namespace java com.heepay.rpc.payment.service
include "BankcardAuthModel.thrift"

service BankcardAuthService {
	list<BankcardAuthModel.BankcardAuthModel> query(1:string cardNo, 2:string ownerName, 3:string ownerCertno, 4:string cardMobile, 5:string type, 6:i32 start, 7:i32 count);
	list<BankcardAuthModel.BankcardAuthModel> selectBankcardsByMerchantId(1:i64 merchantId,2:string status);
	list<BankcardAuthModel.BankcardAuthModel> selectByExample(1:map<string, string> param);
	BankcardAuthModel.BankcardAuthModel selectByPrimaryKey(1:i64 id);
	i32 getCounts();

	list<BankcardAuthModel.BankcardAuthModel> getBankcardAuthListByBankcardAuthModel(1:BankcardAuthModel.BankcardAuthModel bankcardAuthModel);

	BankcardAuthModel.BankcardAuthModel getBankcardAuthByAuthIdAndStatusAndTypeAndMerchantUserId(1:i64 authId,2:string status,3:string type,4:string merchantUserId);
    BankcardAuthModel.BankcardAuthModel getBankcardAuthByMerchantUserIdAndMerchantId(1:string status,2:string type,3:string merchantUserId,4:string merchantId);

	i32 saveBankcardAuth(1:BankcardAuthModel.BankcardAuthModel bankcardAuthModel);
	
	i32 deleteBankcard(1:i64 merchantId,2:string bankCardNo);

	i32 setDefaultBankcard(1:i64 merchantId,2:string bankCardNo);

}
