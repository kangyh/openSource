package com.heepay.boss.util;
/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月16日 上午10:57:40
 * 创建描述：
 * 
 * 修 改 者：  
 * 修改时间：
 * 修改描述： 
 * 
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class Constants {
	//商户限额规则key缓存前缀   MerchantId:商户编号 AccountType：对公对私 ProductId：产品编号 bankCardType：银行卡类型（储蓄卡，信用卡）
	public static final String Quota_MerchantId_AccountType_ProductId_BankCardType_Key = "quota_merchant_key";
	//产品限额规则key缓存前缀  productId：产品编号 AccountType:对公对私 BankCardType:银行卡类型(储蓄卡，信用卡)
	public static final String Quata_ProductId_AccountType_BankCardType_Key = "quota_product_key";
	
	/**
	 * 按 商户编号，账户类型（对公对私），产口编号,银行卡类型（储蓄卡，信用卡） 制造 商户限额 RedisKey
	 * @param MerchantId
	 * @param AccountType
	 * @param ProductId
	 * @param BankCardType
	 * @return
	 */
	public static String Quota_MerchantKey(String MerchantId,String AccountType,String ProductId ,String BankCardType)
	{
		if (MerchantId==null && "".equals(MerchantId))
		{
			MerchantId = "null";
		}
		if (AccountType==null && "".equals(AccountType))
		{
			AccountType = "null";
		}
		if (ProductId==null && "".equals(ProductId))
		{
			ProductId = "null";
		}
		if (BankCardType==null && "".equals(BankCardType))
		{
			BankCardType = "null";
		}
		return Quota_MerchantId_AccountType_ProductId_BankCardType_Key+"_"+MerchantId+"_"+AccountType+"_"+ProductId+"_"+BankCardType;
	}
	/**
	 * 按产品编号，账户类型（对公对私），银行卡类型（储蓄卡，信用卡） 制造产品限额 RedisKey
	 * @param ProductId
	 * @param AccountType
	 * @param BankCardType
	 * @return
	 */
	 public static String Quota_ProductKey(String ProductCode,String AccountType,String BankCardType)
	  {
		  if (ProductCode==null && "".equals(ProductCode))
			{
			  ProductCode = "null";
			}
		  if (AccountType==null && "".equals(AccountType))
			{
			  AccountType = "null";
			}
		  if (BankCardType==null && "".equals(BankCardType))
			{
			  BankCardType = "null";
			}
		  return Quata_ProductId_AccountType_BankCardType_Key+"_"+ProductCode+"_"+AccountType+"_"+BankCardType;
	  }
	 public static String getBlackorwhiteListKey(String productCode,String type,String category)
	 {
		 return "RISK_BLACK_"+productCode+"_"+type+"_"+category;
	 }
	 public static String getBlackorwhiteItemListKey(String refId,String value)
	 {
		 return "RISK_BLACKITEM_"+refId+"_"+value;
	 }
	 public static String getRiskIncomeQuotaKey(String merchantId,String type)
	 {
		 return "RISK_MERCHANT_INCOME_QUOTA_"+merchantId+"_"+type;
	 }
	 public static String getSettleIncomeInfoKey(String direction,String transType,String productType)
	 {
		 return "RISK_INCOME_INFO_"+direction+"_"+transType+"_"+productType;
	 }
	 public static String getBossRuleKey(String ruleId,String jobStatus)
	 {
		 return "RISK_BOSS_RULE_"+ruleId+"_"+jobStatus;
	 }
}


