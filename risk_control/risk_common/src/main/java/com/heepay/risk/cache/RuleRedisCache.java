package com.heepay.risk.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.heepay.risk.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.risk.RiskMerChantStatus;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.risk.common.Constants;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月16日 上午11:19:23
 * 创建描述：  通过spring 注入 默认以单例加载
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
@Component
public class RuleRedisCache implements IRuleCache {

	private static final Logger logger = LogManager.getLogger();
	public static boolean isRuleExists(String ruleKey)
	{
		return JedisClusterUtil.getJedisCluster().exists(ruleKey);
	}
	public static boolean isBlackRuleExists(String ruleKey,String value)
	{
		String blackList=JedisClusterUtil.getJedisCluster().get(ruleKey);
		if(StringUtil.isBlank(blackList))
			return false;
		else
		{
			BlackModel model=JsonMapperUtil.nonEmptyMapper().fromJson(blackList, BlackModel.class);
			logger.info("黑名单缓存:{}",JsonMapperUtil.nonEmptyMapper().toJson(model));
			logger.info("黑名单值key:{}",Constants.getBlackorwhiteItemListKey(String.valueOf(model.getBlackId()), value));
			String blackItemEntity=JedisClusterUtil.getJedisCluster().get(Constants.getBlackorwhiteItemListKey(String.valueOf(model.getBlackId()), value));
			if(StringUtil.isBlank(blackItemEntity))
				return false;
			else
				return true;
		}
	}
	public  static  RiskMerchantProductQuotaVO getQuotaMerchantRuleCache(String merchantQuotakey) {
		// TODO Auto-generated method stub
		String merchantJSON = JedisClusterUtil.getValue(merchantQuotakey);
		if (merchantJSON == null)
		{
			return null;
		}
		else
		{
			return JsonMapperUtil.nonEmptyMapper().fromJson(merchantJSON, RiskMerchantProductQuotaVO.class);
		}
	}
	
	
	
	public  static  RiskProductQuotaVO getQuotaProductRuleCache(String productQuotakey) {
		// TODO Auto-generated method stub
		String productJSON = JedisClusterUtil.getValue(productQuotakey);
		if (productJSON == null)
		{
			return null;
		}
		else
		{
			return JsonMapperUtil.nonEmptyMapper().fromJson(productJSON, RiskProductQuotaVO.class);
		}
	}
	
	public static Boolean setQuotaMerchantRuleCache(RiskMerchantProductQuotaVO riskMerchantProductQuotaVO) {
		// TODO Auto-generated method stub
		String merchantKey = Constants.Quota_MerchantKey(String.valueOf(riskMerchantProductQuotaVO.getMerchantId()),
				riskMerchantProductQuotaVO.getAccountType(), 
				riskMerchantProductQuotaVO.getProductCode(), 
				riskMerchantProductQuotaVO.getBankcardType());
		logger.info("merchantKey:{},method:setQuotaMerchantRuleCache",merchantKey);
		System.out.println("merchantKey:"+merchantKey);
		JedisClusterUtil.setValue(merchantKey, JsonMapperUtil.nonEmptyMapper().toJson(riskMerchantProductQuotaVO));
		return null;
	}
	/**
	 * 删除全部商户限额和产品限额
	 */
	public static void delQuotaRule()
	{
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
		Set<String> merchantKeys = redisOperator.keys(Constants.Quota_MerchantId_AccountType_ProductId_BankCardType_Key+"*");
		for (String merchantkey:merchantKeys)
		{
			logger.info("删除"+Constants.Quota_MerchantId_AccountType_ProductId_BankCardType_Key+":"+merchantkey);
			JedisClusterUtil.getJedisCluster().del(merchantkey);
		}
		Set<String> productKeys = redisOperator.keys(Constants.Quata_ProductId_AccountType_BankCardType_Key+"*");
		for (String productKey:productKeys)
		{
			logger.info("删除"+Constants.Quata_ProductId_AccountType_BankCardType_Key+":"+productKey);
			JedisClusterUtil.getJedisCluster().del(productKey);
		}
	}
	public static void delQuotaRule(String pattern)
	{
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
		Set<String> merchantKeys = redisOperator.keys(pattern);
		for (String merchantkey:merchantKeys)
		{
			logger.info("删除"+pattern+":"+merchantkey);
			JedisClusterUtil.getJedisCluster().del(merchantkey);
		}
		
	}
	public static void getAllQuotaRule()
	{
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
		Set<String> merchantKeys = redisOperator.keys(Constants.Quota_MerchantId_AccountType_ProductId_BankCardType_Key+"*");
		for (String merchantkey:merchantKeys)
		{
			logger.info(Constants.Quota_MerchantId_AccountType_ProductId_BankCardType_Key+":"+merchantkey);
			
		}
		Set<String> productKeys = redisOperator.keys(Constants.Quata_ProductId_AccountType_BankCardType_Key+"*");
		for (String productKey:productKeys)
		{
			logger.info(Constants.Quata_ProductId_AccountType_BankCardType_Key+":"+productKey);
			
		}
	}
	
	public static Boolean setQuotaProductRuleCache(RiskProductQuotaVO riskProductQuotaVO) {
				String productQuotaKey = Constants.Quota_ProductKey(String.valueOf(riskProductQuotaVO.getProductCode()),
						riskProductQuotaVO.getAccountType(),riskProductQuotaVO.getBankcardType());
				logger.info("productQuotaKey:{},method:setQuotaProductRuleCache",productQuotaKey);
				System.out.println("productQuotaKey:"+productQuotaKey);
				JedisClusterUtil.setValue(productQuotaKey, JsonMapperUtil.nonEmptyMapper().toJson(riskProductQuotaVO));
				return null;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public   RiskMerchantProductQuotaVO getQuotaMerchantCache(String merchantkey) {
		// TODO Auto-generated method stub
		String merchantJSON = JedisClusterUtil.getValue(merchantkey);
		if (merchantJSON == null)
		{
			return null;
		}
		else
		{
			return JsonMapperUtil.nonEmptyMapper().fromJson(merchantJSON, RiskMerchantProductQuotaVO.class);
		}
	}

	
	
	
	
	@Override
	public  Boolean setQuotaMerchantCache(RiskMerchantProductQuotaVO riskMerchantProductQuotaVO) {
		// TODO Auto-generated method stub
		String merchantKey = Constants.Quota_MerchantKey(String.valueOf(riskMerchantProductQuotaVO.getMerchantId()),
				riskMerchantProductQuotaVO.getAccountType(), 
				riskMerchantProductQuotaVO.getProductCode(), 
				riskMerchantProductQuotaVO.getBankcardType());
		logger.info("merchantKey:{},method:setQuotaMerchantCache",merchantKey);
		System.out.println("merchantKey:"+merchantKey);
		JedisClusterUtil.setValue(merchantKey, JsonMapperUtil.nonEmptyMapper().toJson(riskMerchantProductQuotaVO));
		return null;
	}

	@Override
	public  Boolean delQuotaMerchantCache(RiskMerchantProductQuotaVO riskMerchantProductQuotaVO) {
		
		String merchantKey = Constants.Quota_MerchantKey(String.valueOf(riskMerchantProductQuotaVO.getMerchantId()),
				riskMerchantProductQuotaVO.getAccountType(), 
				riskMerchantProductQuotaVO.getProductCode(), 
				riskMerchantProductQuotaVO.getBankcardType());
		JedisClusterUtil.getJedisCluster().del(merchantKey);
		return null;
	}

	@Override
	public  RiskProductQuotaVO getQuotaProductCache(String productQuotakey) {
		
		String productJSON = JedisClusterUtil.getValue(productQuotakey);
		if (productJSON == null)
		{
			return null;
		}
		else
		{
			return JsonMapperUtil.nonEmptyMapper().fromJson(productJSON, RiskProductQuotaVO.class);
		}
	}

	@Override
	public  Boolean setQuotaProductCache(RiskProductQuotaVO riskProductQuotaVO) {
		
				String productQuotaKey = Constants.Quota_ProductKey(String.valueOf(riskProductQuotaVO.getProductCode()),
						riskProductQuotaVO.getAccountType(),riskProductQuotaVO.getBankcardType());
				logger.info("productQuotaKey:{},method:setQuotaProductCache",productQuotaKey);
				System.out.println("productQuotaKey:"+productQuotaKey);
				JedisClusterUtil.setValue(productQuotaKey, JsonMapperUtil.nonEmptyMapper().toJson(riskProductQuotaVO));
				return null;
	}

	@Override
	public  Boolean delQuotaProductCache(RiskProductQuotaVO riskProductQuotaVO) {
		String productQuotaKey = Constants.Quota_ProductKey(String.valueOf(riskProductQuotaVO.getProductCode()),
				riskProductQuotaVO.getAccountType(),riskProductQuotaVO.getBankcardType());
				JedisClusterUtil.getJedisCluster().del(productQuotaKey);
				return null;
	}

	/**
	 *
	 * @param productCode：产品编号
	 * @param type：BLACK:黑名单 WHITE:白名单 GRAY:灰名单
	 * @param category：名单分类
	 * @param value：黑名单值
	 * @return 返回redis中黑名单值
	 */
	public static BlackItemModel getBlackItemCache(String productCode, String type, String category, String value) {
		logger.info("黑名单key:{}",Constants.getBlackorwhiteListKey(productCode, type, category));
		String blackList=JedisClusterUtil.getJedisCluster().get(Constants.getBlackorwhiteListKey(productCode, type, category));
		if(StringUtil.isBlank(blackList))
			return null;
		else
		{
			BlackModel model=JsonMapperUtil.nonEmptyMapper().fromJson(blackList, BlackModel.class);
			logger.info("黑名单缓存:{}",JsonMapperUtil.nonEmptyMapper().toJson(model));
			logger.info("黑名单值key:{}",Constants.getBlackorwhiteItemListKey(String.valueOf(model.getBlackId()), value));
			String blackItemEntity=JedisClusterUtil.getJedisCluster().get(Constants.getBlackorwhiteItemListKey(String.valueOf(model.getBlackId()), value));
			logger.info("黑名单值value:{}",blackItemEntity);
			if(StringUtil.isBlank(blackItemEntity))
				return null;
			else
				return JsonMapperUtil.nonEmptyMapper().fromJson(blackItemEntity, BlackItemModel.class);
		}
		
	}

	/**
	 * 获取所有的入金交易类型
	 * @return
	 */
	public static Map<String,List> getInComeTransType()
	{   
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
		Set<String> merchantKeys = redisOperator.keys("RISK_INCOME_INFO_in*");
		List<String> list=new ArrayList<String>();
		List<String> list2=new ArrayList<String>();
		Map<String,List> map=new HashMap<String,List>();
		for(String key:merchantKeys)
		{
		String value=JedisClusterUtil.getJedisCluster().get(key);
		if(value!=null)
		{
			SettleIncomeInfoVo info=JsonMapperUtil.nonEmptyMapper().fromJson(value,SettleIncomeInfoVo.class);
			if(!list.contains(info.getTransType().toLowerCase()))
			list.add(info.getTransType().toLowerCase());
			if(!list2.contains(info.getProductCode().toLowerCase()))
				list2.add(info.getProductCode().toLowerCase());
		}
		}
		if(!list.isEmpty())
		{
			map.put("trans_type", list);
		}
		if(!list2.isEmpty())
		{
			map.put("product_code", list2);
		}
		return map;
	}

	/**
	 *
	 * @param merchantId:商户编号
	 * @param transType：交易类型
	 * @param productCode：产品编码
	 * @param directionType：入金还是出金
	 * @return
	 */
    public static RiskIncomeVo getRiskIncomeCache(String merchantId,String transType,String productCode,String directionType)
    {
    	String direction=JedisClusterUtil.getJedisCluster().get(Constants.getSettleIncomeInfoKey(directionType,transType, productCode));
    	if(direction!=null)
    	{
    		SettleIncomeInfoVo info=JsonMapperUtil.nonEmptyMapper().fromJson(direction,SettleIncomeInfoVo.class);
    		if(!info.getIncomeDirection().equals(directionType)||info.getStatus().equals("N"))
    			return null;
    		String riskIncome=JedisClusterUtil.getJedisCluster().get(Constants.getRiskIncomeQuotaKey(merchantId, info.getIncomeDirection()));
    		if(riskIncome!=null)
    		{     			
    			RiskIncomeVo result=JsonMapperUtil.nonEmptyMapper().fromJson(riskIncome,RiskIncomeVo.class);
    			if(result.getStatus().equals("Y")&&result.getDayIncomeQuotaAmount().compareTo(BigDecimal.ZERO)!=0)
    			return result;
    			else
    				return null;
    		}
    		else
    			return null;
    	}
    	else 
    		return null;
    }

    /**
     *
     * @param companyName:商户名称
     * @param buziCode：营业执照编号
     * @param ownerIdcard：法人身份证号
     * @return
     */
	public static String getRegisterAndLoginCache(String companyName,String buziCode,/*String ownerName,*/String ownerIdcard)
	{
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
		Set<String> keys=redisOperator.keys("RISK_LOGIN_BLACK*");
		List<RegisterAndLoginRequestModel> list=new ArrayList<RegisterAndLoginRequestModel>();
		for(String key:keys)
		{
			String value=JedisClusterUtil.getJedisCluster().get(key);
			RegisterAndLoginRequestModel model=JsonMapperUtil.nonEmptyMapper().fromJson(value, RegisterAndLoginRequestModel.class);
			if(model!=null)
				list.add(model);
		}
		if(list.size()==0)
			return null;
//        Long length=list.stream().filter(p->(p.getCompanyName()==null||p.getCompanyName().equals(companyName))
//                             &&(p.getBuziCode()==null||p.getBuziCode().equals(buziCode))
//                             /*&&(p.getOwnerName()==null||p.getOwnerName().equals(ownerName))*/
//                             &&(p.getOwnerId()==null||p.getOwnerId().equals(ownerIdcard))).count();
		Long length=list.stream().filter(  p->
				(p.getCompanyName()!=null&&p.getCompanyName().equals(companyName))||
						(p.getBuziCode()!=null&&p.getBuziCode().equals(buziCode))||
						(p.getOwnerId()!=null&&p.getOwnerId().equals(ownerIdcard))
		).count();
		if(length>0)
			return "1";
		return null;
	}
    
	public  static Boolean setRiskRuleList(RiskRuleListVo riskRulelist) {
				String productQuotaKey = Constants.getRiskRuleKey(riskRulelist.getRuleId(),riskRulelist.getMonitorObject()) ;
				logger.info("productQuotaKey:{},method:setQuotaProductCache",productQuotaKey);
				System.out.println("productQuotaKey:"+productQuotaKey);
				JedisClusterUtil.setValue(productQuotaKey, JsonMapperUtil.nonEmptyMapper().toJson(riskRulelist));
				return true;
	}
	public static boolean isExistEnableRule( String ruleid , String mobj ) {
		logger.info("判断某条规则是否有缓存，key="+ Constants.getRiskRuleKey( ruleid,mobj ) );
		String obj =  JedisClusterUtil.getValue(  Constants.getRiskRuleKey( ruleid,mobj )  );
		if(obj==null || "".equals(obj)) {
			logger.info("判断某条规则是否有缓存，无缓存!" );
			return false;
		}else {
			RiskRuleListVo vo = JsonMapperUtil.nonEmptyMapper().fromJson(obj, RiskRuleListVo.class);
			if(vo!=null) {
				logger.info("判断某条规则是否有缓存，RiskRuleListVo="+vo.toString() );
			}
			if( RiskMerChantStatus.RISK_MER_STATUS_ENABLE.getValue().equals(  vo.getStatus()  ) ){
				logger.info("判断某条规则是否有缓存，有缓存!" );
				return true;
			}
		}
		return false;
	}
	public static void delRiskRuleList() {
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster(JedisClusterUtil.getJedisCluster());
		TreeSet merchantKeys = redisOperator.keys("RISK_RULE_*");
		Iterator keys = merchantKeys.iterator();
		while (keys.hasNext()) {
			String rulekey = (String) keys.next();
			logger.info("删除RISK_RULE_* key:" + rulekey);
			JedisClusterUtil.getJedisCluster().del(rulekey);
		}

	}
	
	public static Boolean setRiskInternalMonitorMerchant(RiskMonitorMerchantVo merchant) {
		String key = Constants.getRiskMonitorMerchantKey(merchant.getMerchantId(), merchant.getProductCode()) ;
		logger.info("setRiskInternalMonitorMerchant key:"+key);
		JedisClusterUtil.setValue(key, JsonMapperUtil.nonEmptyMapper().toJson(merchant));
		return true;
	}
	
	public static Boolean setRiskInternalMonitorChannel(RiskMonitorChannelVo channel) {

		String key = Constants.getRiskMonitorChannelKey(channel.getChannelPartnerCode(), channel.getPayTypeCode()) ;
		logger.info("setRiskInternalMonitorChannel key :"+key);
		JedisClusterUtil.setValue(key, JsonMapperUtil.nonEmptyMapper().toJson(channel));
		return true;
	}
	
	public static Boolean delRiskInternalMonitorMerchant(RiskMonitorMerchantVo merchant) {
		String key = Constants.getRiskMonitorMerchantKey(merchant.getMerchantId(), merchant.getProductCode()) ;
		logger.info("setRiskInternalMonitorMerchant key:"+key);
		JedisClusterUtil.getJedisCluster().del(key) ;
		return true;
	}
	
	public static Boolean delRiskInternalMonitorChannel(RiskMonitorChannelVo channel) {

		String key = Constants.getRiskMonitorChannelKey(channel.getChannelPartnerCode(), channel.getPayTypeCode()) ;
		logger.info("setRiskInternalMonitorChannel key :"+key);
		JedisClusterUtil.getJedisCluster().del(key) ;
		return true;
	}
    public static List<RiskMonitorMerchantVo> getMonitorMerchantCache()
    {
			RedisOperator redisOperator = new RedisOperator();
			redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
			Set<String> keys=redisOperator.keys("RISK_MON_MER_*");
			List<RiskMonitorMerchantVo> list=new ArrayList<RiskMonitorMerchantVo>();
			for(String key:keys)
			{
            String value=JedisClusterUtil.getJedisCluster().get(key);
			logger.info("MonitorMerchantCache:"+value);
            RiskMonitorMerchantVo model=JsonMapperUtil.nonEmptyMapper().fromJson(value, RiskMonitorMerchantVo.class);
            if(model!=null)
                list.add(model);
        }
        return list;
    }
    public static List<RiskMonitorChannelVo> getMonitorChannelCache()
    {
        RedisOperator redisOperator = new RedisOperator();
        redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
        Set<String> keys=redisOperator.keys("RISK_MON_CHA_*");
        List<RiskMonitorChannelVo> list=new ArrayList<RiskMonitorChannelVo>();
        for(String key:keys)
        {
            String value=JedisClusterUtil.getJedisCluster().get(key);
            RiskMonitorChannelVo model=JsonMapperUtil.nonEmptyMapper().fromJson(value, RiskMonitorChannelVo.class);
            if(model!=null)
                list.add(model);
        }
        return list;
    }
	public static void delMonitorMerchantList() {
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster(JedisClusterUtil.getJedisCluster());
		TreeSet merchantKeys = redisOperator.keys("RISK_MON_MER_*");
		Iterator keys = merchantKeys.iterator();
		while (keys.hasNext()) {
			String rulekey = (String) keys.next();
			logger.info("删除RISK_MON_MER_* key:" + rulekey);
			JedisClusterUtil.getJedisCluster().del(rulekey);
		}
	}
	public static void delMonitorChannelList() {
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster(JedisClusterUtil.getJedisCluster());
		TreeSet merchantKeys = redisOperator.keys("RISK_MON_CHA_*");
		Iterator keys = merchantKeys.iterator();
		while (keys.hasNext()) {
			String rulekey = (String) keys.next();
			logger.info("删除RISK_MON_CHA_* key:" + rulekey);
			JedisClusterUtil.getJedisCluster().del(rulekey);
		}

	}
}


