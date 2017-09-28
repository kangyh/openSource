namespace java com.heepay.rpc.payment.service
include "MerchantBankcardModel.thrift"

service MerchantBankcardService{
	MerchantBankcardModel.MerchantBankcardModel queryByCardId(1:i64 cardId);
	i32 getBankCardCount();
	list<MerchantBankcardModel.MerchantBankcardModel> getAllBankCard();
	list<MerchantBankcardModel.MerchantBankcardModel> getPage(1:i32 start, 2:i32 count);
	bool insertBankCard(1:MerchantBankcardModel.MerchantBankcardModel bankcard);
	bool deleteByCardId(1:i64 cardId);
	list<MerchantBankcardModel.MerchantBankcardModel> queryByUserId(1:i64 merchantId);
	i32 setDefault(1:i64 cardId);
}