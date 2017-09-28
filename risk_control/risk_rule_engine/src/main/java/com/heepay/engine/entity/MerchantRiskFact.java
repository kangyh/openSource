package com.heepay.engine.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.heepay.engine.AbstractRiskFact;
import com.heepay.risk.cache.RuleRedisCache;
import com.heepay.risk_es_engine.ESearchService;


/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月12日 下午3:59:16
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
public  class MerchantRiskFact extends AbstractRiskFact{
	private static final Logger log = LogManager.getLogger();
	private String merchantId;
	private String accountType;
	private String bankCardType;
	private String productCode; //产品类型
	private String merchantCompany;
	private String bankCardNo;
	private String bankCardOwnerMobile;
	private String bankCardOwnerIdCard;
	private BigDecimal batPayAmount; //批量转账总金额
	private BigDecimal perItemAmount;  //单笔金额
	private BigDecimal perDayAmount; //单日金额
	private BigDecimal perMonthAmount; //单月金额
	private BigDecimal perDayIncomeAmount;//单日入金金额
	private String trans_type=""; //为批量转账添加此字段
	private String tradeIp;
	private List<String> ruleIdList=new ArrayList<String>();
	//银行卡归属省份
	private String provinceInBankCard;
	//手机号码归属省份
	private String provinceInMobile;
	//ip归属省份
	private String provinceInIp;
    private boolean isForeignIp;
    private String userType;//用户类型 USER:用户 MERCHANT:商户
	public String getTradeIp() {
		return tradeIp;
	}

	public void setTradeIp(String tradeIp) {
		this.tradeIp = tradeIp;
	}

	ESearchService essearch;
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public BigDecimal getPerItemAmount() {
		return perItemAmount;
	}

	public void setPerItemAmount(BigDecimal perItemAmount) {
		this.perItemAmount = perItemAmount;
	}

	public BigDecimal getPerDayAmount() {
		
		return perDayAmount;
	}
	public BigDecimal getPerMonthAmount() {
		return perMonthAmount;
	}
	/**
	 * 校验批付规则
	 */
    public String checkBatchPayRule()
    {
    	if (essearch == null)
    	{
    		essearch = new ESearchService();	
    		essearch.initESClient();
    	}
    	Map<String,String> conditionMap = new HashMap<String,String>();
    	conditionMap.put("merchant_id", this.getMerchantId());
    	conditionMap.put("bankcard_no", this.getBankCardNo());
    	log.info(  "MerchantRiskFact校验批付规则，参数："+conditionMap.toString() ) ;
    	String result = essearch.checkBatchPayRule(conditionMap);
    	log.info(  "MerchantRiskFact校验批付规则，结果："+result ) ;
    	essearch.closeESClient();
    	return result;

    	
    }
	/**
	 * 得到日限额 和 月限额
	 */
    public void getBatchAmount()
    {
    	if (essearch == null)
    	{
    		essearch = new ESearchService();	
    		essearch.initESClient();
    	}
    	String accountTypeParam="0";
    	if (accountType.toUpperCase().equals("PUBLIC")){ //对公是1
    		accountTypeParam="1";
		}
    	
    	Map map = essearch.getDayAndMonthLimitAmount(getMerchantId(), getProductCode(),accountTypeParam,getBankCardType());
    	perDayAmount = new BigDecimal(map.get("day").toString());
    	perMonthAmount = new BigDecimal(map.get("month").toString());
    	essearch.closeESClient();
    }
    /**
	 * 得到入金日限额 
	 */
    public void getIncomeDayAmount(int dayOff)
    {
    	//if (essearch == null)
    	//{
    		essearch = new ESearchService();	
    		essearch.initESClient();
    	//}
    	Map<String,List> paramMap=RuleRedisCache.getInComeTransType();
    	Map map = essearch.getDayLimitAmount(getMerchantId(),paramMap,dayOff );
    	perDayIncomeAmount = new BigDecimal(map.get("day").toString());
    	essearch.closeESClient();
    }
	public BigDecimal getBatPayAmount() {
		return batPayAmount;
	}

	public void setBatPayAmount(BigDecimal batPayAmount) {
		this.batPayAmount = batPayAmount;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getTrans_type() {
		return trans_type;
	}

	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}

	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}

	public String getBankCardOwnerMobile() {
		return bankCardOwnerMobile;
	}

	public void setBankCardOwnerMobile(String bankCardOwnerMobile) {
		this.bankCardOwnerMobile = bankCardOwnerMobile;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankCardOwnerIdCard() {
		return bankCardOwnerIdCard;
	}

	public void setBankCardOwnerIdCard(String bankCardOwnerIdCard) {
		this.bankCardOwnerIdCard = bankCardOwnerIdCard;
	}

	public BigDecimal getPerDayIncomeAmount() {
		return perDayIncomeAmount;
	}

	public void setPerDayIncomeAmount(BigDecimal perDayIncomeAmount) {
		this.perDayIncomeAmount = perDayIncomeAmount;
	}


	public String getProvinceInBankCard() {
		return provinceInBankCard;
	}

	public void setProvinceInBankCard(String provinceInBankCard) {
		this.provinceInBankCard = provinceInBankCard;
	}

	public String getProvinceInMobile() {
		return provinceInMobile;
	}

	public void setProvinceInMobile(String provinceInMobile) {
		this.provinceInMobile = provinceInMobile;
	}

	public String getProvinceInIp() {
		return provinceInIp;
	}

	public void setProvinceInIp(String provinceInIp) {
		this.provinceInIp = provinceInIp;
	}

    public List<String> getRuleIdList() {
        return ruleIdList;
    }

    public void setRuleIdList(List<String> ruleIdList) {
        this.ruleIdList = ruleIdList;
    }

    public boolean isForeignIp() {
        return isForeignIp;
    }

    public void setForeignIp(boolean foreignIp) {
        isForeignIp = foreignIp;
    }

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}


