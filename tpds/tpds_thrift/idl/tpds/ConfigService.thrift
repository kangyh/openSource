namespace java com.heepay.rpc.tpds.service

service ConfigService
{
string addMerchantAccount(1:string MerchantAccountEntity);
string editMerchantAccount(1:string MerchantAccountEntity);
string addBindInterface(1:string BindInterfaceEntity);
string editBindInterface(1:string BindInterfaceEntity);
string addproductKey(1:string productKeyEntity);
string editproductKey(1:string productKeyEntity);
string addBankCer(1:string BankCerEntity);
string editBankCer(1:string BankCerEntity);
string delBankCer(1:string bankNo);
string getBankCerByBankNo(1:string bankNo);
string getMerchantCerByMerchantNo(1:string merchantNo);
string getProductKey(1:string merchantNo,2:string productCode);
string addMerchantCer(1:string MerchantCerEntity);
string editMerchantCer(1:string MerchantCerEntity);
string delMerchantCer(1:string merchantNo);
string addCutDay(1:string CutDayEntity);
string editCutDay(1:string CutDayEntity);
string checksysNo(1:string sysNo);
string addMerchantH5(1:string merchantH5Entity);
string getMerchantBySeqNo(1:string businessSeqNo);
}