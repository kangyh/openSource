namespace java com.heepay.rpc.risk.service

service RiskService {
   string  getRiskRunStatus();
   string  addRiskMerchantProductQuota(1:string merchantProductQuotaEntity);
   string  editRiskMerchantProductQuota(1:string merchantProductQuotaEntity);
   string  addRiskProductQuota(1:string productQuotaEntity);
   string  editRiskProductQuota(1:string merchantProductQuotaEntity);
   string  addBlackorwhiteItemList(1:string blackorwhiteItemListEntity);
   string  editBlackorwhiteItemList(1:string blackorwhiteItemListEntity);
   string  delBlackorwhiteItemList(1:string blackorwhiteItemListEntity);
   string  addBlackorwhiteList(1:string blackorwhiteListEntity);
   string  editBlackorwhiteList(1:string blackorwhiteListEntity);
   string  delBlackorwhiteList(1:string blackorwhiteListEntity);
   string  addRiskIncomeQuota(1:string blackorwhiteListEntity);
   string  editRiskIncomeQuota(1:string blackorwhiteListEntity);
   string  delRiskIncomeQuota(1:string blackorwhiteListEntity);
   string  addSettleIncomeInfo(1:string SettleIncomeInfoEntity);
   string  editSettleIncomeInfo(1:string SettleIncomeInfoEntity);
   string  delSettleIncomeInfo(1:string SettleIncomeInfoEntity);
   string  addRiskLoginBlacklistInfo(1:string riskLoginBlacklist);
   string  editRiskLoginBlacklistInfo(1:string riskLoginBlacklist);
   string  delRiskLoginBlacklistInfo(1:string riskLoginBlacklist);
 }