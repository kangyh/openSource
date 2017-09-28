package com.heepay.risk.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.heepay.risk.service.IpQueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.risk.cache.RedisOperator;
import com.heepay.risk.cache.RuleRedisCache;
import com.heepay.risk.common.Constants;
import com.heepay.risk.dao.BlackorwhiteItemListMapper;
import com.heepay.risk.dao.BlackorwhiteListMapper;
import com.heepay.risk.dao.RiskIncomeQuotaMapper;
import com.heepay.risk.dao.RiskLoginBlacklistMapper;
import com.heepay.risk.dao.RiskMerchantProductQuotaMapper;
import com.heepay.risk.dao.RiskMonitorMapper;
import com.heepay.risk.dao.RiskProductQuotaMapper;
import com.heepay.risk.dao.SettleIncomeInfoMapper;
import com.heepay.risk.entity.BlackorwhiteItemList;
import com.heepay.risk.entity.BlackorwhiteList;
import com.heepay.risk.entity.RiskIncomeQuota;
import com.heepay.risk.entity.RiskLoginBlacklist;
import com.heepay.risk.entity.RiskMerchantProductQuota;
import com.heepay.risk.entity.RiskMonitorObj;
import com.heepay.risk.entity.RiskProductQuota;
import com.heepay.risk.entity.SettleIncomeInfo;
import com.heepay.risk.vo.BlackItemModel;
import com.heepay.risk.vo.BlackModel;
import com.heepay.risk.vo.RiskIncomeVo;
import com.heepay.risk.vo.RiskLoginBlacklistVo;
import com.heepay.risk.vo.RiskMerchantProductQuotaVO;
import com.heepay.risk.vo.RiskProductQuotaVO;
import com.heepay.risk.vo.SettleIncomeInfoVo;
import com.heepay.rpc.risk.service.RiskService;
import com.heepay.rpc.service.RpcService;

/**
 * @author 王英雷  E-mail:wangyl@9186.com
 * @version 创建时间：2016年12月10日 下午6:11:58
 * 类说明 风控类监控
 */
@Component
@RpcService(name="RiskServiceImpl",processor=com.heepay.rpc.risk.service.RiskService.Processor.class)
public class RiskServiceImpl implements RiskService.Iface {
	@Autowired
	private RiskMonitorMapper riskMonitorMapper;
	@Autowired
	private RiskMerchantProductQuotaMapper riskMerchantProductQuotaMapper;
	@Autowired
	private RiskProductQuotaMapper riskProductQuotaMapper;
	@Autowired
	private BlackorwhiteItemListMapper blackorwhiteItemListMapper;
	@Autowired
	private BlackorwhiteListMapper blackorwhiteListMapper;
	@Autowired
	private RiskIncomeQuotaMapper riskIncomeQuotaMapper;
	@Autowired
	private SettleIncomeInfoMapper settleIncomeInfoMapper;
	@Autowired
	private RiskLoginBlacklistMapper riskLoginBlacklistMapper;
	private static final Logger logger = LogManager.getLogger();
	/**
	 * 监控服务是否在线
	 */
	@Override
	public String getRiskRunStatus() throws TException {
		// TODO Auto-generated method stub
		RiskMonitorObj riskMonitorObj = riskMonitorMapper.queryMonitorInfo();
		JsonMapperUtil JsonMapperUtil = new JsonMapperUtil();
		return JsonMapperUtil.toJson(riskMonitorObj);
	}
	/**
	 * 添加商户产品限额
	 */
	@Override
	public String addRiskMerchantProductQuota(String merchantProductQuotaEntity) throws TException {
		// TODO Auto-generated method stub
		String rtn = "";
		try
		{
			RiskMerchantProductQuota riskMerchantProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(merchantProductQuotaEntity, RiskMerchantProductQuota.class);
			riskMerchantProductQuotaMapper.insert(riskMerchantProductQuota);
			RiskMerchantProductQuotaVO riskMerchantProductQuotaVO = new RiskMerchantProductQuotaVO();
			riskMerchantProductQuotaVO.setAccountType(riskMerchantProductQuota.getAccountType());
			riskMerchantProductQuotaVO.setBankcardType(riskMerchantProductQuota.getBankcardType());
			riskMerchantProductQuotaVO.setMerchantCode(riskMerchantProductQuota.getMerchantId());
			riskMerchantProductQuotaVO.setPerDay(riskMerchantProductQuota.getPerDay());
			riskMerchantProductQuotaVO.setPerItem(riskMerchantProductQuota.getPerItem());
			riskMerchantProductQuotaVO.setPerMonth(riskMerchantProductQuota.getPerMonth());
			riskMerchantProductQuotaVO.setProductCode(riskMerchantProductQuota.getProductCode());
			riskMerchantProductQuotaVO.setStatus(riskMerchantProductQuota.getStatus());
			riskMerchantProductQuotaVO.setMerProId(riskMerchantProductQuota.getMerProId());
			RuleRedisCache.setQuotaMerchantRuleCache(riskMerchantProductQuotaVO); //缓存添加
			logger.info("risk_message:{} 添加成功  , method:addRiskMerchantProductQuota",merchantProductQuotaEntity);
		}catch(Exception e)
		{
			logger.error("risk_message:{},method:addRiskMerchantProductQuota",e.getMessage());
			rtn = e.getMessage();
		}
		return rtn;
	}
	/**
	 * 添加产品大类限额
	 */
	@Override
	public String addRiskProductQuota(String productQuotaEntity) throws TException {
		String rtn = "";
		try
		{
			RiskProductQuota riskProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(productQuotaEntity, RiskProductQuota.class);
			riskProductQuotaMapper.insert(riskProductQuota);
			RiskProductQuotaVO riskProductQuotaVO = new RiskProductQuotaVO();
			riskProductQuotaVO.setAccountType(riskProductQuota.getAccountType());
			riskProductQuotaVO.setBankcardType(riskProductQuota.getBankcardType());
			riskProductQuotaVO.setPerDay(riskProductQuota.getPerDay());
			riskProductQuotaVO.setPerItem(riskProductQuota.getPerItem());
			riskProductQuotaVO.setPerMonth(riskProductQuota.getPerMonth());
			riskProductQuotaVO.setStatus(riskProductQuota.getStatus());
			riskProductQuotaVO.setProductCode(riskProductQuota.getProductCode());
			riskProductQuotaVO.setProId(riskProductQuota.getProId());
			RuleRedisCache.setQuotaProductRuleCache(riskProductQuotaVO); ////缓存添加
			logger.info("risk_message:{} 添加成功  method:addRiskProductQuota",productQuotaEntity);
		}catch(Exception e)
		{
			logger.error("risk_message:{},method:addRiskProductQuota",e.getMessage());
			rtn = e.getMessage();
		}
		return rtn;
	}
	/**
	 * 修改商户产品限额
	 */
	@Override
	public String editRiskMerchantProductQuota(String merchantProductQuotaEntity) throws TException {
		String rtn = "";
		try
		{
			RiskMerchantProductQuota riskMerchantProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(merchantProductQuotaEntity, RiskMerchantProductQuota.class);
			riskMerchantProductQuotaMapper.update(riskMerchantProductQuota);
			RiskMerchantProductQuotaVO riskMerchantProductQuotaVO = new RiskMerchantProductQuotaVO();

			riskMerchantProductQuotaVO.setAccountType(riskMerchantProductQuota.getAccountType());
			riskMerchantProductQuotaVO.setBankcardType(riskMerchantProductQuota.getBankcardType());
			riskMerchantProductQuotaVO.setMerchantCode(riskMerchantProductQuota.getMerchantId());
			riskMerchantProductQuotaVO.setPerDay(riskMerchantProductQuota.getPerDay());
			riskMerchantProductQuotaVO.setPerItem(riskMerchantProductQuota.getPerItem());
			riskMerchantProductQuotaVO.setPerMonth(riskMerchantProductQuota.getPerMonth());
			riskMerchantProductQuotaVO.setProductCode(riskMerchantProductQuota.getProductCode());
			riskMerchantProductQuotaVO.setStatus(riskMerchantProductQuota.getStatus());
			riskMerchantProductQuotaVO.setMerProId(riskMerchantProductQuota.getMerProId());
			RuleRedisCache.setQuotaMerchantRuleCache(riskMerchantProductQuotaVO); //缓存变更
			logger.info("risk_message:{} 修改成功  method:editRiskMerchantProductQuota",merchantProductQuotaEntity);
		}catch(Exception e)
		{
			logger.error("risk_message:{},method:editRiskMerchantProductQuota",e.getMessage());
			rtn = e.getMessage();
		}
		return rtn;
	}
	/**
	 * 修改产品大类限额
	 */
	@Override
	public String editRiskProductQuota(String productQuotaEntity) throws TException {
		String rtn = "";
		try
		{
			RiskProductQuota riskProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(productQuotaEntity, RiskProductQuota.class);
			riskProductQuotaMapper.update(riskProductQuota);
			RiskProductQuotaVO riskProductQuotaVO = new RiskProductQuotaVO();
			riskProductQuotaVO.setAccountType(riskProductQuota.getAccountType());
			riskProductQuotaVO.setBankcardType(riskProductQuota.getBankcardType());
			riskProductQuotaVO.setPerDay(riskProductQuota.getPerDay());
			riskProductQuotaVO.setPerItem(riskProductQuota.getPerItem());
			riskProductQuotaVO.setPerMonth(riskProductQuota.getPerMonth());
			riskProductQuotaVO.setStatus(riskProductQuota.getStatus());
			riskProductQuotaVO.setProductCode(riskProductQuota.getProductCode());
			riskProductQuotaVO.setProId(riskProductQuota.getProId());
			RuleRedisCache.setQuotaProductRuleCache(riskProductQuotaVO); ////缓存变更
			logger.info("risk_message:{} 修改成功  method:editRiskProductQuota",productQuotaEntity);
		}catch(Exception e)
		{
			logger.error("risk_message:{},method:editRiskProductQuota",e.getMessage());
			rtn = e.getMessage();
		}
		return rtn;
	}
	/**
	 * 初始化缓存接口
	 */
	@PostConstruct
	private void initRuleCache()
	{
		List<RiskProductQuota> riskProductQuotaList =  riskProductQuotaMapper.selectProductQuotaList();
		List<RiskMerchantProductQuota> riskMerchantProductQuotaList =  riskMerchantProductQuotaMapper.selectMerchantProductQuotaList();
		RiskProductQuotaVO riskProductQuotaVO = null;
		RiskMerchantProductQuotaVO riskMerchantProductQuotaVO = null;
		//清空缓存
		RuleRedisCache.delQuotaRule();
		//重新载入缓存
		for(RiskProductQuota item:riskProductQuotaList)
		{
			riskProductQuotaVO = new RiskProductQuotaVO();
			riskProductQuotaVO.setAccountType(item.getAccountType());
			riskProductQuotaVO.setBankcardType(item.getBankcardType());
			riskProductQuotaVO.setPerDay(item.getPerDay());
			riskProductQuotaVO.setPerItem(item.getPerItem());
			riskProductQuotaVO.setPerMonth(item.getPerMonth());
			riskProductQuotaVO.setStatus(item.getStatus());
			riskProductQuotaVO.setProductCode(item.getProductCode());
			riskProductQuotaVO.setProId(item.getProId());
			RuleRedisCache.setQuotaProductRuleCache(riskProductQuotaVO);
		}
		for(RiskMerchantProductQuota item:riskMerchantProductQuotaList)
		{
			riskMerchantProductQuotaVO = new RiskMerchantProductQuotaVO();
			riskMerchantProductQuotaVO.setAccountType(item.getAccountType());
			riskMerchantProductQuotaVO.setBankcardType(item.getBankcardType());
			riskMerchantProductQuotaVO.setMerchantCode(item.getMerchantId());
			riskMerchantProductQuotaVO.setPerDay(item.getPerDay());
			riskMerchantProductQuotaVO.setPerItem(item.getPerItem());
			riskMerchantProductQuotaVO.setPerMonth(item.getPerMonth());
			riskMerchantProductQuotaVO.setProductCode(item.getProductCode());
			riskMerchantProductQuotaVO.setStatus(item.getStatus());
			riskMerchantProductQuotaVO.setMerProId(item.getMerProId());
			RuleRedisCache.setQuotaMerchantRuleCache(riskMerchantProductQuotaVO);
		}
		//刷新黑名单缓存
		this.refreshBlackCache();
		//刷新商户出入金限额缓存
		this.refreshRiskIncomeQuotaCache();
		//刷新出入金配置缓存
		this.refreshSettleIncomeInfoCache();
		//刷新风控黑名单商户缓存
		this.refreshRiskLoginBlacklistCache();
	}
	@Override
	public String addBlackorwhiteItemList(String blackorwhiteItemListEntity) throws TException {
		logger.info("添加黑白名单值:{}",blackorwhiteItemListEntity);
		BlackItemModel riskProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(blackorwhiteItemListEntity, BlackItemModel.class);
		BlackorwhiteItemList black=new BlackorwhiteItemList();
		black.setBlackId(riskProductQuota.getBlackId());
		black.setBlackItemValue(riskProductQuota.getBlackItemValue());
		black.setCreateAuthor(riskProductQuota.getCreateAuthor());
		black.setUpdateAuthor(riskProductQuota.getUpdateAuthor());
		black.setStatus(riskProductQuota.getStatus());
		black.setCreateTime(riskProductQuota.getCreateTime()==null?new Date():DateUtil.stringToDate(riskProductQuota.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		black.setUpdateTime(riskProductQuota.getUpdateTime()==null?new Date():DateUtil.stringToDate(riskProductQuota.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
		int i=blackorwhiteItemListMapper.insert(black);
		if(i==1)
			JedisClusterUtil.getJedisCluster().set(Constants.getBlackorwhiteItemListKey(String.valueOf(riskProductQuota.getBlackId()), riskProductQuota.getBlackItemValue()), JsonMapperUtil.nonEmptyMapper().toJson(black));
		return String.valueOf(i);
	}
	@Override
	public String addBlackorwhiteList(String blackorwhiteListEntity) throws TException {
		logger.info("添加黑白名单:{}",blackorwhiteListEntity);
		BlackModel riskProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(blackorwhiteListEntity, BlackModel.class);
		BlackorwhiteList entity=new BlackorwhiteList();
		entity.setCategory(riskProductQuota.getCategory());
		entity.setName(riskProductQuota.getName());
		entity.setDesc(riskProductQuota.getDesc());
		entity.setProductCode(riskProductQuota.getProductCode());
		entity.setStatus(riskProductQuota.getStatus());
		entity.setType(riskProductQuota.getType());
		entity.setCreateAuthor(riskProductQuota.getCreateAuthor());
		entity.setUpdateAuthor(riskProductQuota.getUpdateAuthor());
		entity.setCreateTime(riskProductQuota.getCreateTime()==null?new Date():DateUtil.stringToDate(riskProductQuota.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		entity.setUpdateTime(riskProductQuota.getUpdateTime()==null?new Date():DateUtil.stringToDate(riskProductQuota.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
		int i=blackorwhiteListMapper.insert(entity);
		if(i==1)
			JedisClusterUtil.getJedisCluster().set(Constants.getBlackorwhiteListKey(riskProductQuota.getProductCode(), riskProductQuota.getType(), riskProductQuota.getCategory()), JsonMapperUtil.nonEmptyMapper().toJson(entity));
		return String.valueOf(i);
	}
	@Override
	public String editBlackorwhiteItemList(String blackorwhiteItemListEntity) throws TException {
		logger.info("编辑黑白名单值:{}",blackorwhiteItemListEntity);
		BlackItemModel riskProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(blackorwhiteItemListEntity, BlackItemModel.class);
		BlackorwhiteItemList temp=blackorwhiteItemListMapper.get(riskProductQuota.getBlackItemId());
		if(temp!=null)
		{
			JedisClusterUtil.getJedisCluster().del(Constants.getBlackorwhiteItemListKey(String.valueOf(temp.getBlackId()), temp.getBlackItemValue()));
		}
		if(riskProductQuota.getStatus().equals("DISABLE"))
		{
			return delBlackorwhiteItemList(blackorwhiteItemListEntity);
		}
		BlackorwhiteItemList black=new BlackorwhiteItemList();
		black.setBlackId(riskProductQuota.getBlackId());
		black.setBlackItemValue(riskProductQuota.getBlackItemValue());
		black.setBlackItemId(riskProductQuota.getBlackItemId());
		black.setStatus(riskProductQuota.getStatus());
		//black.setCreateAutor(riskProductQuota.getCreateAutor());
		black.setUpdateAuthor(riskProductQuota.getUpdateAuthor());
		//black.setCreateTime(riskProductQuota.getCreateTime()==null?new Date():DateUtil.stringToDate(riskProductQuota.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		black.setUpdateTime(riskProductQuota.getUpdateTime()==null?new Date():DateUtil.stringToDate(riskProductQuota.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
		int i=blackorwhiteItemListMapper.update(black);
		if(i==1)
			JedisClusterUtil.getJedisCluster().set(Constants.getBlackorwhiteItemListKey(String.valueOf(riskProductQuota.getBlackId()), riskProductQuota.getBlackItemValue()), blackorwhiteItemListEntity);
		return String.valueOf(i);
	}
	@Override
	public String editBlackorwhiteList(String blackorwhiteListEntity) throws TException {
		logger.info("编辑黑白名单:{}",blackorwhiteListEntity);
		BlackModel riskProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(blackorwhiteListEntity, BlackModel.class);
		BlackorwhiteList temp=blackorwhiteListMapper.get(riskProductQuota.getBlackId());
		if(temp!=null)
		{
			JedisClusterUtil.getJedisCluster().del(Constants.getBlackorwhiteListKey(temp.getProductCode(), temp.getType(), temp.getCategory()));
		}
		if(riskProductQuota.getStatus().equals("DISABLE"))
		{
			return delBlackorwhiteList(blackorwhiteListEntity);
		}
		BlackorwhiteList entity=new BlackorwhiteList();
		entity.setCategory(riskProductQuota.getCategory());
		entity.setName(riskProductQuota.getName());
		entity.setDesc(riskProductQuota.getDesc());
		entity.setProductCode(riskProductQuota.getProductCode());
		entity.setStatus(riskProductQuota.getStatus());
		entity.setType(riskProductQuota.getType());
		entity.setBlackId(riskProductQuota.getBlackId());
		//entity.setCreateAuthor(riskProductQuota.getCreateAuthor());
		entity.setUpdateAuthor(riskProductQuota.getUpdateAuthor());
		//entity.setCreateTime(riskProductQuota.getCreateTime()==null?new Date():DateUtil.stringToDate(riskProductQuota.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		entity.setUpdateTime(riskProductQuota.getUpdateTime()==null?new Date():DateUtil.stringToDate(riskProductQuota.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
		int i=blackorwhiteListMapper.update(entity);
		if(i==1)
			JedisClusterUtil.getJedisCluster().set(Constants.getBlackorwhiteListKey(riskProductQuota.getProductCode(), riskProductQuota.getType(), riskProductQuota.getCategory()), blackorwhiteListEntity);
		List<BlackorwhiteItemList> list=blackorwhiteItemListMapper.selectByBlackId(entity);
		if(list!=null)
		{
			for(BlackorwhiteItemList itemEntity:list)
			{
				if(itemEntity.getStatus().equals("DISABLE"))
				{
					itemEntity.setStatus("ENABLE");
					blackorwhiteItemListMapper.update(itemEntity);
					JedisClusterUtil.getJedisCluster().set(Constants.getBlackorwhiteItemListKey(String.valueOf(itemEntity.getBlackId()), itemEntity.getBlackItemValue()), JsonMapperUtil.nonEmptyMapper().toJson(itemEntity));
				}
			}

		}
		return String.valueOf(i);
	}
	private void refreshBlackCache()
	{
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
		Set<String> keys=redisOperator.keys("RISK_BALCK*");
		Set<String> itemKeys=redisOperator.keys("RISK_BLACKITEM*");
		List<BlackorwhiteList> list=blackorwhiteListMapper.getlist();
		List<BlackorwhiteItemList> itemList=blackorwhiteItemListMapper.getlist();
		for (String merchantkey:keys)
		{
			logger.info("删除RISK_BALCK:"+merchantkey);
			JedisClusterUtil.getJedisCluster().del(merchantkey);
		}
		for (String merchantkey:itemKeys)
		{
			logger.info("删除RISK_BLACKITEM:"+merchantkey);
			JedisClusterUtil.getJedisCluster().del(merchantkey);
		}
		if(list!=null)
		{
			for(BlackorwhiteList entity:list)
			{
				logger.info("添加RISK_BALCK:"+Constants.getBlackorwhiteListKey(entity.getProductCode(), entity.getType(), entity.getCategory()));
				JedisClusterUtil.getJedisCluster().set(Constants.getBlackorwhiteListKey(entity.getProductCode(), entity.getType(), entity.getCategory()), JsonMapperUtil.nonEmptyMapper().toJson(entity));
			}
		}
		if(itemList!=null)
		{
			for(BlackorwhiteItemList itemEntity:itemList)
			{
				logger.info("添加RISK_BLACKITEM:"+Constants.getBlackorwhiteItemListKey(String.valueOf(itemEntity.getBlackId()), itemEntity.getBlackItemValue()));
				JedisClusterUtil.getJedisCluster().set(Constants.getBlackorwhiteItemListKey(String.valueOf(itemEntity.getBlackId()), itemEntity.getBlackItemValue()), JsonMapperUtil.nonEmptyMapper().toJson(itemEntity));
			}
		}
	}
	@Override
	public String delBlackorwhiteItemList(String blackorwhiteItemListEntity) throws TException {
		logger.info("删除黑白名单值:{}",blackorwhiteItemListEntity);
		BlackItemModel riskProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(blackorwhiteItemListEntity, BlackItemModel.class);
		BlackorwhiteItemList entity=new BlackorwhiteItemList();
		entity.setBlackItemId(riskProductQuota.getBlackItemId());
		int i=blackorwhiteItemListMapper.delete(entity);
		if(i==1)
			JedisClusterUtil.getJedisCluster().del(Constants.getBlackorwhiteItemListKey(String.valueOf(riskProductQuota.getBlackId()), riskProductQuota.getBlackItemValue()));
		return String.valueOf(i);
	}
	@Override
	public String delBlackorwhiteList(String blackorwhiteListEntity) throws TException {
		logger.info("删除黑白名单:{}",blackorwhiteListEntity);
		BlackModel riskProductQuota = JsonMapperUtil.nonEmptyMapper().fromJson(blackorwhiteListEntity, BlackModel.class);
		BlackorwhiteList entity=new BlackorwhiteList();
		entity.setBlackId(riskProductQuota.getBlackId());
		BlackorwhiteItemList itemEntity=new BlackorwhiteItemList();
		itemEntity.setBlackId(riskProductQuota.getBlackId());
		List<BlackorwhiteItemList> list=blackorwhiteItemListMapper.selectByBlackId(entity);
		int i=blackorwhiteListMapper.delete(entity);
		int j=blackorwhiteItemListMapper.deleteByBlackId(itemEntity);
		if(i>=1)
		{
			JedisClusterUtil.getJedisCluster().del(Constants.getBlackorwhiteListKey(entity.getProductCode(), entity.getType(), entity.getCategory()));
			if(list!=null&&j>=1)
			{
				for(BlackorwhiteItemList temp:list)
				{
					JedisClusterUtil.getJedisCluster().del(Constants.getBlackorwhiteItemListKey(String.valueOf(temp.getBlackId()), temp.getBlackItemValue()));
				}
			}
		}
		return String.valueOf(i);
	}
	/**
	 * 添加商户出入金限额
	 */
	@Override
	public String addRiskIncomeQuota(String arg0) throws TException {
		logger.info("添加商户出入金限额:{}",arg0);
		RiskIncomeVo vo=JsonMapperUtil.nonEmptyMapper().fromJson(arg0,RiskIncomeVo.class);
		RiskIncomeQuota  entity=new RiskIncomeQuota();
		entity.setIncomeDirection(vo.getIncomeDirection());
		entity.setMerchantId(vo.getMerchantId());
		entity.setCreateAuthor(vo.getCreateAuthor());
		entity.setCreateTime(DateUtil.stringToDate(vo.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		entity.setDayIncomeQuotaAmount(vo.getDayIncomeQuotaAmount());
		entity.setStatus(vo.getStatus());
		//entity.setUpdateAuthor(vo.getUpdateAuthor());
		//entity.setUpdateTime(DateUtil.stringToDate(vo.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		int result=riskIncomeQuotaMapper.insert(entity);
		if(result>0)
		{
			JedisClusterUtil.getJedisCluster().set(Constants.getRiskIncomeQuotaKey(String.valueOf(entity.getMerchantId()), entity.getIncomeDirection()), arg0);
		}
		return String.valueOf(result);
	}
	@Override
/**
 * 删除商户出入金限额
 */
	public String delRiskIncomeQuota(String arg0) throws TException {
		logger.info("删除商户出入金限额:{}",arg0);
		RiskIncomeVo vo=JsonMapperUtil.nonEmptyMapper().fromJson(arg0,RiskIncomeVo.class);
		RiskIncomeQuota  entity=new RiskIncomeQuota();
		entity.setIncomeDirection(vo.getIncomeDirection());
		entity.setMerchantId(vo.getMerchantId());
		entity.setQuotaId(vo.getQuotaId());
		entity.setCreateAuthor(vo.getCreateAuthor());
		entity.setCreateTime(DateUtil.stringToDate(vo.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		entity.setDayIncomeQuotaAmount(vo.getDayIncomeQuotaAmount());
		entity.setStatus(vo.getStatus());
		entity.setUpdateAuthor(vo.getUpdateAuthor());
		entity.setUpdateTime(DateUtil.stringToDate(vo.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
		entity.setStatus("N");
		int result=riskIncomeQuotaMapper.update(entity);
		if(result>0)
		{
			JedisClusterUtil.getJedisCluster().del(Constants.getRiskIncomeQuotaKey(String.valueOf(entity.getMerchantId()), entity.getIncomeDirection()));
		}
		return String.valueOf(result);
	}
	/**
	 * 编辑商户出入金限额
	 */
	@Override
	public String editRiskIncomeQuota(String arg0) throws TException {
		logger.info("编辑商户出入金限额:{}",arg0);
		RiskIncomeVo vo=JsonMapperUtil.nonEmptyMapper().fromJson(arg0,RiskIncomeVo.class);
		RiskIncomeQuota temp=riskIncomeQuotaMapper.get(vo.getQuotaId());
		if(temp!=null)
		{
			JedisClusterUtil.getJedisCluster().del(Constants.getRiskIncomeQuotaKey(String.valueOf(temp.getMerchantId()), temp.getIncomeDirection()));
		}
		RiskIncomeQuota  entity=new RiskIncomeQuota();
		entity.setIncomeDirection(vo.getIncomeDirection());
		entity.setMerchantId(vo.getMerchantId());
		entity.setQuotaId(vo.getQuotaId());
		entity.setCreateAuthor(vo.getCreateAuthor());
		entity.setCreateTime(DateUtil.stringToDate(vo.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
		entity.setDayIncomeQuotaAmount(vo.getDayIncomeQuotaAmount());
		entity.setStatus(vo.getStatus());
		entity.setUpdateAuthor(vo.getUpdateAuthor());
		entity.setUpdateTime(DateUtil.stringToDate(vo.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
		int result=riskIncomeQuotaMapper.update(entity);
		if(result>0)
		{
			JedisClusterUtil.getJedisCluster().set(Constants.getRiskIncomeQuotaKey(String.valueOf(entity.getMerchantId()), entity.getIncomeDirection()), arg0);
		}
		return String.valueOf(result);
	}
	/**
	 * 刷新商户出入金限额
	 */
	public void refreshRiskIncomeQuotaCache()
	{
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
		Set<String> keys=redisOperator.keys("RISK_MERCHANT_INCOME_QUOTA*");
		List<RiskIncomeQuota> list=riskIncomeQuotaMapper.getlist();
		for (String merchantkey:keys)
		{
			logger.info("删除RISK_MERCHANT_INCOME_QUOTA:"+merchantkey);
			JedisClusterUtil.getJedisCluster().del(merchantkey);
		}

		if(list!=null)
		{
			for(RiskIncomeQuota entity:list)
			{
				logger.info("添加RISK_MERCHANT_INCOME_QUOTA:"+Constants.getRiskIncomeQuotaKey(String.valueOf(entity.getMerchantId()), entity.getIncomeDirection()));
				JedisClusterUtil.getJedisCluster().set(Constants.getRiskIncomeQuotaKey(String.valueOf(entity.getMerchantId()), entity.getIncomeDirection()), JsonMapperUtil.nonEmptyMapper().toJson(entity));
			}
		}
	}
	/**
	 * 刷新出入金配置缓存
	 */
	public void refreshSettleIncomeInfoCache()
	{
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
		Set<String> keys=redisOperator.keys("RISK_INCOME_INFO*");
		List<SettleIncomeInfo> list=settleIncomeInfoMapper.getlist();
		for (String merchantkey:keys)
		{
			logger.info("删除RISK_INCOME_INFO:"+merchantkey);
			JedisClusterUtil.getJedisCluster().del(merchantkey);
		}

		if(list!=null)
		{
			for(SettleIncomeInfo entity:list)
			{
				logger.info("添加RISK_INCOME_INFO:"+Constants.getSettleIncomeInfoKey(entity.getIncomeDirection(),entity.getTransType(), entity.getProductCode()));
				JedisClusterUtil.getJedisCluster().set(Constants.getSettleIncomeInfoKey(entity.getIncomeDirection(),entity.getTransType(), entity.getProductCode()), JsonMapperUtil.nonEmptyMapper().toJson(entity));
			}
		}
	}
	/**
	 * 添加出入金配置限额
	 */
	@Override
	public String addSettleIncomeInfo(String SettleIncomeInfoEntity) throws TException {
		logger.info("添加出入金配置限额:{}",SettleIncomeInfoEntity);
		SettleIncomeInfoVo vo=JsonMapperUtil.nonEmptyMapper().fromJson(SettleIncomeInfoEntity,SettleIncomeInfoVo.class);
		SettleIncomeInfo entity=new SettleIncomeInfo();
		entity.setBusinessType(vo.getBusinessType());
		entity.setCreateTime(DateUtil.stringToDate(vo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		entity.setCreator(vo.getCreator());
		entity.setIncomeDirection(vo.getIncomeDirection());
		//entity.setModifier(vo.getModifier());
		entity.setProductCode(vo.getProductCode());
		entity.setRemark(vo.getRemark());
		entity.setSettleStatus(vo.getSettleStatus());
		entity.setStatus(vo.getStatus());
		entity.setTransType(vo.getTransType());
		//entity.setUpdateTime(DateUtil.stringToDate(vo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
		int result=settleIncomeInfoMapper.insert(entity);
		if(result>0)
		{
			JedisClusterUtil.getJedisCluster().set(Constants.getSettleIncomeInfoKey(entity.getIncomeDirection(),entity.getTransType(), entity.getProductCode()), SettleIncomeInfoEntity);
		}
		return String.valueOf(result);
	}
	/**
	 *编辑出入金配置限额
	 */
	@Override
	public String editSettleIncomeInfo(String SettleIncomeInfoEntity) throws TException {
		logger.info("编辑出入金配置限额:{}",SettleIncomeInfoEntity);
		SettleIncomeInfoVo vo=JsonMapperUtil.nonEmptyMapper().fromJson(SettleIncomeInfoEntity,SettleIncomeInfoVo.class);
		SettleIncomeInfo temp=settleIncomeInfoMapper.get(vo.getIncomeId());
		if(temp!=null)
		{
			JedisClusterUtil.getJedisCluster().del(Constants.getSettleIncomeInfoKey(temp.getIncomeDirection(),temp.getTransType(), temp.getProductCode()));
		}
		SettleIncomeInfo entity=new SettleIncomeInfo();
		entity.setBusinessType(vo.getBusinessType());
		entity.setCreateTime(DateUtil.stringToDate(vo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		entity.setCreator(vo.getCreator());
		entity.setIncomeDirection(vo.getIncomeDirection());
		entity.setIncomeId(vo.getIncomeId());
		entity.setModifier(vo.getModifier());
		entity.setProductCode(vo.getProductCode());
		entity.setRemark(vo.getRemark());
		entity.setSettleStatus(vo.getSettleStatus());
		entity.setStatus(vo.getStatus());
		entity.setTransType(vo.getTransType());
		entity.setUpdateTime(DateUtil.stringToDate(vo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
		int result=settleIncomeInfoMapper.update(entity);
		if(result>0)
		{
			JedisClusterUtil.getJedisCluster().set(Constants.getSettleIncomeInfoKey(entity.getIncomeDirection(),entity.getTransType(), entity.getProductCode()), SettleIncomeInfoEntity);
		}
		return String.valueOf(result);
	}
	/**
	 * 删除出入金配置限额
	 */
	@Override
	public String delSettleIncomeInfo(String SettleIncomeInfoEntity) throws TException {
		logger.info("删除出入金配置限额:{}",SettleIncomeInfoEntity);
		SettleIncomeInfoVo vo=JsonMapperUtil.nonEmptyMapper().fromJson(SettleIncomeInfoEntity,SettleIncomeInfoVo.class);
		SettleIncomeInfo entity=new SettleIncomeInfo();
		entity.setBusinessType(vo.getBusinessType());
		entity.setCreateTime(DateUtil.stringToDate(vo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		entity.setCreator(vo.getCreator());
		entity.setIncomeDirection(vo.getIncomeDirection());
		entity.setIncomeId(vo.getIncomeId());
		entity.setModifier(vo.getModifier());
		entity.setProductCode(vo.getProductCode());
		entity.setRemark(vo.getRemark());
		entity.setSettleStatus(vo.getSettleStatus());
		entity.setStatus("N");
		entity.setTransType(vo.getTransType());
		entity.setUpdateTime(DateUtil.stringToDate(vo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
		int result=settleIncomeInfoMapper.update(entity);
		if(result>0)
		{
			JedisClusterUtil.getJedisCluster().del(Constants.getSettleIncomeInfoKey(entity.getIncomeDirection(),entity.getTransType(), entity.getProductCode()));
		}
		return String.valueOf(result);
	}

	/**
	 * 刷新风控黑名单商户列表
	 */
	public void refreshRiskLoginBlacklistCache()
	{
		RedisOperator redisOperator = new RedisOperator();
		redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
		Set<String> keys=redisOperator.keys("RISK_LOGIN_BLACK_*");
		List<RiskLoginBlacklist> list=riskLoginBlacklistMapper.selectAll();
		for (String merchantkey:keys)
		{
			logger.info("删除RISK_LOGIN_BLACK_:"+merchantkey);
			JedisClusterUtil.getJedisCluster().del(merchantkey);
		}

		if(list!=null)
		{
			for(RiskLoginBlacklist entity:list)
			{
				logger.info("添加RISK_INCOME_INFO:"+Constants.getRiskLoginBlacklistKey(entity.getCompanyName(),entity.getBuziCode(), entity.getOwnerName(), entity.getOwnerId()));
				JedisClusterUtil.getJedisCluster().set(Constants.getRiskLoginBlacklistKey(entity.getCompanyName(),entity.getBuziCode(), entity.getOwnerName(), entity.getOwnerId()), JsonMapperUtil.nonEmptyMapper().toJson(entity));
			}
		}
	}

	/**
	 * 添加风控黑名单商户
	 */
	@Override
	public String addRiskLoginBlacklistInfo(String riskLoginBlacklist) throws TException {
		logger.info("添加风控黑名单商户:{}",riskLoginBlacklist);
		RiskLoginBlacklistVo vo=JsonMapperUtil.nonEmptyMapper().fromJson(riskLoginBlacklist,RiskLoginBlacklistVo.class);
		RiskLoginBlacklist entity=new RiskLoginBlacklist();
		entity.setCreateTime(new Date(Long.parseLong(vo.getCreateTime())));
		entity.setBuziCode(vo.getBuziCode());
		entity.setCompanyName(vo.getCompanyName());
		entity.setOwnerId(vo.getOwnerId());
		entity.setOwnerName(vo.getOwnerName());
		if( riskLoginBlacklistMapper.selectCount(entity)>0 ) {
			logger.info("添加风控黑名单商户失败,记录已经存在:{}",riskLoginBlacklist);
			return "0";
		}
		int result=riskLoginBlacklistMapper.insert(entity);
		if(result>0)
		{
			JedisClusterUtil.getJedisCluster().set(Constants.getRiskLoginBlacklistKey(entity.getCompanyName(),entity.getBuziCode(), entity.getOwnerName(), entity.getOwnerId()), riskLoginBlacklist);
		}
		return String.valueOf(result);
	}
	/**
	 *编辑风控黑名单商户
	 */
	@Override
	public String editRiskLoginBlacklistInfo(String riskLoginBlacklist) throws TException {
		logger.info("编辑风控黑名单商户:{}",riskLoginBlacklist);
		RiskLoginBlacklistVo vo=JsonMapperUtil.nonEmptyMapper().fromJson(riskLoginBlacklist,RiskLoginBlacklistVo.class);
		RiskLoginBlacklist temp=riskLoginBlacklistMapper.selectByPrimaryKey(vo.getBlackId());
		if(temp==null) {
			logger.info("更新风控黑名单商户失败,数据库不存在此记录:{}",riskLoginBlacklist);
			return "0";
		}
		RiskLoginBlacklist entity=new RiskLoginBlacklist();
		entity.setCreateTime(new Date(Long.parseLong(vo.getCreateTime())));
		entity.setBuziCode(vo.getBuziCode());
		entity.setCompanyName(vo.getCompanyName());
		entity.setOwnerId(vo.getOwnerId());
		entity.setOwnerName(vo.getOwnerName());
		entity.setBlackId(vo.getBlackId());

		if( !vo.getBuziCode().equals(temp.getBuziCode())  || !vo.getCompanyName().equals(temp.getCompanyName())
				||  !vo.getOwnerId().equals(temp.getOwnerId()) ){
			if( riskLoginBlacklistMapper.selectCount(entity)>0 ) {
				logger.info("更新风控黑名单商户失败,待更新的记录已经存在:{}",riskLoginBlacklist);
				return "0";
			}
			JedisClusterUtil.getJedisCluster().del(Constants.getRiskLoginBlacklistKey(temp.getCompanyName(),temp.getBuziCode(), temp.getOwnerName(), temp.getOwnerId()));

		}
		int result=riskLoginBlacklistMapper.updateByPrimaryKey(entity);
		if(result>0)
		{
			JedisClusterUtil.getJedisCluster().set(Constants.getRiskLoginBlacklistKey(entity.getCompanyName(),entity.getBuziCode(), entity.getOwnerName(), entity.getOwnerId()), riskLoginBlacklist);
		}
		return String.valueOf(result);
	}
	/**
	 * 删除风控黑名单商户
	 */
	@Override
	public String delRiskLoginBlacklistInfo(String riskLoginBlacklist) throws TException {
		logger.info("删除风控黑名单商户:{}",riskLoginBlacklist);
		RiskLoginBlacklistVo vo=JsonMapperUtil.nonEmptyMapper().fromJson(riskLoginBlacklist,RiskLoginBlacklistVo.class);
		RiskLoginBlacklist entity=new RiskLoginBlacklist();
		entity.setCreateTime(new Date(Long.parseLong(vo.getCreateTime())));
		entity.setBuziCode(vo.getBuziCode());
		entity.setCompanyName(vo.getCompanyName());
		entity.setOwnerId(vo.getOwnerId());
		entity.setOwnerName(vo.getOwnerName());
		entity.setBlackId(vo.getBlackId());
		int result=riskLoginBlacklistMapper.deleteByPrimaryKey(vo.getBlackId());
		if(result>0)
		{
			JedisClusterUtil.getJedisCluster().del(Constants.getRiskLoginBlacklistKey(entity.getCompanyName(),entity.getBuziCode(), entity.getOwnerName(), entity.getOwnerId()));
		}
		return String.valueOf(result);
	}
}