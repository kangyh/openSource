package com.heepay.tpds.util;

import com.heepay.common.util.StringUtil;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月17日 下午3:40:31
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
	
	public static String TPDS_MERCHANTACCOUNT_LIST="TPDS_MERCHANTACCOUNT_LIST";
	
     public static String getMerchantAccountKey(String merchantNo,String loginName,String systemNo,String accNo
    		 ,String certNo,String bankCard,String bankCardBranch,String bankAccount,String bankAccountBranch)
     {
    	 if(StringUtil.isBlank(merchantNo))
    	 {
    		 merchantNo=null;
    	 }
    	 if(StringUtil.isBlank(loginName))
    	 {
    		 loginName=null;
    	 }
    	 if(StringUtil.isBlank(systemNo))
    	 {
    		 systemNo=null;
    	 }
    	 if(StringUtil.isBlank(accNo))
    	 {
    		 accNo=null;
    	 }
    	 if(StringUtil.isBlank(certNo))
    	 {
    		 certNo=null;
    	 }
    	 if(StringUtil.isBlank(bankCard))
    	 {
    		 bankCard=null;
    	 }
    	 if(StringUtil.isBlank(bankCardBranch))
    	 {
    		 bankCardBranch=null;
    	 }
    	 if(StringUtil.isBlank(bankAccount))
    	 {
    		 bankAccount=null;
    	 }
    	 if(StringUtil.isBlank(bankAccountBranch))
    	 {
    		 bankAccountBranch=null;
    	 }
    	 return String.format("tpds_MERCHANTACCOUNT_%s_%s_%s_%s_%s_%s_%s_%s_%s",
    			 merchantNo,loginName,systemNo,accNo,certNo,bankCard,bankCardBranch,bankAccount,bankAccountBranch );
     }
     public static String getMerchantAccountKey(String sysNo)
     {
    	 return String.format("tpds_MERCHANTACCOUNT_%s",sysNo);
     }
     public static String getMerchantCertKey(String merchantNo)
     {
    	 return String.format("tpds_MERCHANTCERT_%s",merchantNo);
     }
     public static String getBankCertKey(String bankNo)
     {
    	 return String.format("tpds_BANKCERT_%s",bankNo);
     }
     public static String getBindInterfaceKey(String merchantNo,String bankNo,String bankCode,String bankName)
     {   
    	 
    	 return String.format("tpds_BANDINTERFACE_%s_%s_%s_%s",merchantNo,bankNo,bankCode,bankName);
     }
     public static String getCutDayKey(String busiNo,String cutType,String cutTime)
     {
    	 return String.format("tpds_CUTDAY_%s_%s_%s",busiNo,cutType,cutTime);
     }
     public static String getMerchantH5Key(String businessSeqNo)
     {
    	 return String.format("tpds_MERCHANTH5_%s",businessSeqNo);
     }
     public static String getProductKey(String merchantId,String productCode)
     {
    	 return String.format("tpds_PRODUCTKEY_%s_%s",merchantId,productCode);
     }
     
}

 