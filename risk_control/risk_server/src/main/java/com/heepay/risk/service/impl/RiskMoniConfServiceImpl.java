/**
 * 
 */
package com.heepay.risk.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.risk.RiskMerChantStatus;
import com.heepay.risk.cache.RuleRedisCache;
import com.heepay.risk.common.Constants;
import com.heepay.risk.dao.RiskInternalMonitorChannelDao;
import com.heepay.risk.dao.RiskInternalMonitorMerchantDao;
import com.heepay.risk.entity.RiskInternalMonitorChannel;
import com.heepay.risk.entity.RiskInternalMonitorMerchant;
import com.heepay.risk.entity.RiskRulelist;
import com.heepay.risk.vo.RiskMonitorChannelVo;
import com.heepay.risk.vo.RiskMonitorMerchantVo;
import com.heepay.risk.vo.RiskRuleListVo;
import com.heepay.rpc.risk.service.RiskMoniConfService;
import com.heepay.rpc.service.RpcService;

/**
 * @author Administrator
 *
 */
@Component
@RpcService(name="RiskMoniConfServiceImpl",processor=com.heepay.rpc.risk.service.RiskMoniConfService.Processor.class)
public class RiskMoniConfServiceImpl  implements RiskMoniConfService.Iface{
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private RiskInternalMonitorChannelDao riskInternalMonitorChannelDao;//RiskInternalMonitorChannel
	@Autowired
	private RiskInternalMonitorMerchantDao riskInternalMonitorMerchantDao ;//RiskInternalMonitorMerchant
	
	/**
	 * 初始化缓存接口
	 */
	@PostConstruct
	private void initRuleCache()
	{
		RiskInternalMonitorChannel record = null; 
		record = new RiskInternalMonitorChannel();
		List<RiskInternalMonitorChannel> mcList =  riskInternalMonitorChannelDao.selectByCondition( record );
		//清空缓存
		if(mcList!=null && mcList.size()>0) {
			RuleRedisCache.delMonitorChannelList();
		}
		//重新载入缓存
		for(RiskInternalMonitorChannel cha:mcList)
		{
			RiskMonitorChannelVo  vo = new RiskMonitorChannelVo();
			vo.setBeginTime(cha.getBeginTime());
			vo.setCreateTime(cha.getCreateTime());
			vo.setEndTime(cha.getEndTime());
			vo.setFrequency(cha.getFrequency());
			vo.setInternalChannelId(cha.getInternalChannelId());
			vo.setOperator(cha.getOperator());
			vo.setChannelPartnerCode(cha.getChannelPartnerCode());
			vo.setPayTypeCode(cha.getChannelTypeCode());
			vo.setThreshold(cha.getThreshold());
			vo.setChannelPartnerName(cha.getChannelPartnerName());
			vo.setChannelTypeName(cha.getChannelTypeName());
			vo.setChangeRate(cha.getChangeRate());
			RuleRedisCache.setRiskInternalMonitorChannel(vo);
			logger.info( "更新通道监控配置缓存" +Constants.getRiskMonitorChannelKey(cha.getChannelPartnerCode(), cha.getChannelTypeCode()));
		}
		
		
		RiskInternalMonitorMerchant mer = null; 
		mer = new RiskInternalMonitorMerchant();
		List<RiskInternalMonitorMerchant> mmList =  riskInternalMonitorMerchantDao.selectByCondition( mer );
		//清空缓存
		if(mmList!=null && mmList.size()>0) {
			RuleRedisCache.delMonitorMerchantList();
		}
		//重新载入缓存
		for(RiskInternalMonitorMerchant cha:mmList)
		{
			RiskMonitorMerchantVo  vo = new RiskMonitorMerchantVo();
			vo.setBeginTime(cha.getBeginTime());
			vo.setCreateTime(cha.getCreateTime());
			vo.setEndTime(cha.getEndTime());
			vo.setFrequency(cha.getFrequency());
			vo.setInternalMerchantId(cha.getInternalMerchantId());
			vo.setMerchantId(cha.getMerchantId());
			vo.setMerchantName(cha.getMerchantName());
			vo.setOperator(cha.getOperator());
			vo.setProductCode(cha.getProductCode());
			vo.setProductName(cha.getProductName());
			vo.setThreshold(cha.getThreshold());
			vo.setChangeRate(cha.getChangeRate());
			RuleRedisCache.setRiskInternalMonitorMerchant(vo);
			logger.info( "更新商户监控配置缓存" +Constants.getRiskMonitorMerchantKey(cha.getMerchantId(), cha.getProductCode()));
		}
		
	}
	
	@Override
	public String addChannelMonitorConfig(String configStr) throws TException {
		String result = "success";
		logger.info( "调用添加通道监控配置接口开始" );
		try{
			RiskInternalMonitorChannel record = (RiskInternalMonitorChannel) JsonMapperUtil.nonEmptyMapper().fromJson(configStr, RiskInternalMonitorChannel.class);
			riskInternalMonitorChannelDao.insert(record);
			RiskMonitorChannelVo vo = new RiskMonitorChannelVo();
			vo.setBeginTime(record.getBeginTime());
			vo.setCreateTime(record.getCreateTime());
			vo.setEndTime(record.getEndTime());
			vo.setFrequency(record.getFrequency());
//			vo.setInternalChannelId(record.getInternalChannelId());
			vo.setOperator(record.getOperator());
			vo.setChannelPartnerCode(record.getChannelPartnerCode());
			vo.setPayTypeCode(record.getChannelTypeCode());
			vo.setThreshold(record.getThreshold());
			vo.setChannelPartnerName(record.getChannelPartnerName());
			vo.setChannelTypeName(record.getChannelTypeName());
			vo.setChangeRate(record.getChangeRate());
			RuleRedisCache.setRiskInternalMonitorChannel(vo);
		} catch (Exception e) {
			logger.error( "调用添加通道监控配置接口异常"  );
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}

	
	@Override
	public String editChannelMonitorConfig(String configStr) throws TException {
		String result = "success";
		logger.info( "调用修改通道监控配置接口开始" );
		try{
			RiskInternalMonitorChannel record = (RiskInternalMonitorChannel) JsonMapperUtil.nonEmptyMapper().fromJson(configStr, RiskInternalMonitorChannel.class);
			RiskInternalMonitorChannel tempRecord=riskInternalMonitorChannelDao.selectByPrimary(Integer.parseInt(record.getInternalChannelId()));
			//删除原有缓存
			if(tempRecord!=null)
			{
				RiskMonitorChannelVo tempVo = new RiskMonitorChannelVo();
				tempVo.setChannelPartnerCode(tempRecord.getChannelPartnerCode());
				tempVo.setPayTypeCode(tempRecord.getChannelTypeCode());
				RuleRedisCache.delRiskInternalMonitorChannel(tempVo);
			}
			riskInternalMonitorChannelDao.updateByPrimaryKey(record);
			RiskMonitorChannelVo vo = new RiskMonitorChannelVo();
			vo.setBeginTime(record.getBeginTime());
			vo.setCreateTime(record.getCreateTime());
			vo.setEndTime(record.getEndTime());
			vo.setFrequency(record.getFrequency());
			vo.setInternalChannelId(record.getInternalChannelId());
			vo.setOperator(record.getOperator());
			vo.setChannelPartnerCode(record.getChannelPartnerCode());
			vo.setPayTypeCode(record.getChannelTypeCode());
			vo.setThreshold(record.getThreshold());
			vo.setChannelPartnerName(record.getChannelPartnerName());
			vo.setChannelTypeName(record.getChannelTypeName());
			vo.setChangeRate(record.getChangeRate());
			RuleRedisCache.setRiskInternalMonitorChannel(vo);
		} catch (Exception e) {
			logger.error( "调用修改通道监控配置接口异常"  );
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}

	
	@Override
	public String delChannelMonitorConfig(String configStr) throws TException {
		String result = "success";
		logger.info( "调用删除通道监控配置接口开始" );
		try{
			RiskInternalMonitorChannel record = (RiskInternalMonitorChannel) JsonMapperUtil.nonEmptyMapper().fromJson(configStr, RiskInternalMonitorChannel.class);
			riskInternalMonitorChannelDao.deleteByPrimaryKey(record.getInternalChannelId());
			RiskMonitorChannelVo vo = new RiskMonitorChannelVo();
			vo.setBeginTime(record.getBeginTime());
			vo.setCreateTime(record.getCreateTime());
			vo.setEndTime(record.getEndTime());
			vo.setFrequency(record.getFrequency());
			vo.setInternalChannelId(record.getInternalChannelId());
			vo.setOperator(record.getOperator());
			vo.setChannelPartnerCode(record.getChannelPartnerCode());
			vo.setPayTypeCode(record.getChannelTypeCode());
			vo.setThreshold(record.getThreshold());
			RuleRedisCache.delRiskInternalMonitorChannel(vo);
		} catch (Exception e) {
			logger.error( "调用删除通道监控配置接口异常"  );
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}

	
	@Override
	public String addMerchantMonitorConfig(String configStr) throws TException {
		String result = "success";
		logger.info( "调用添加商户监控配置接口开始" );
		try{
			RiskInternalMonitorMerchant record = (RiskInternalMonitorMerchant) JsonMapperUtil.nonEmptyMapper().fromJson(configStr, RiskInternalMonitorMerchant.class);
			riskInternalMonitorMerchantDao.insert(record);
			RiskMonitorMerchantVo vo = new RiskMonitorMerchantVo();
			vo.setBeginTime(record.getBeginTime()); 
			vo.setCreateTime(record.getCreateTime());
			vo.setEndTime(record.getEndTime());
			vo.setFrequency(record.getFrequency());
			vo.setInternalMerchantId(record.getInternalMerchantId());
			vo.setMerchantId(record.getMerchantId());
			vo.setMerchantName(record.getMerchantName());
			vo.setOperator(record.getOperator());
			vo.setProductCode(record.getProductCode());
			vo.setProductName(record.getProductName());
			vo.setThreshold(record.getThreshold());
			vo.setChangeRate(record.getChangeRate());
			RuleRedisCache.setRiskInternalMonitorMerchant(vo);
		} catch (Exception e) {
			logger.error( "调用添加商户监控配置接口异常"  );
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}

	
	@Override
	public String editMerchantMonitorConfig(String configStr) throws TException {
		String result = "success";
		logger.info( "调用修改商户监控配置接口开始" );
		try{
			RiskInternalMonitorMerchant record = (RiskInternalMonitorMerchant) JsonMapperUtil.nonEmptyMapper().fromJson(configStr, RiskInternalMonitorMerchant.class);
			RiskInternalMonitorMerchant tempRecord=riskInternalMonitorMerchantDao.selectByPrimary(Integer.parseInt(record.getInternalMerchantId()));
			//删除原有缓存
			if(tempRecord!=null)
			{
				RiskMonitorMerchantVo tempVo = new RiskMonitorMerchantVo();
				tempVo.setMerchantId(tempRecord.getMerchantId());
				tempVo.setProductCode(tempRecord.getProductCode());
				RuleRedisCache.delRiskInternalMonitorMerchant(tempVo);
			}
			riskInternalMonitorMerchantDao.updateByPrimaryKey(record);
			RiskMonitorMerchantVo vo = new RiskMonitorMerchantVo();
			vo.setBeginTime(record.getBeginTime()); 
			vo.setCreateTime(record.getCreateTime());
			vo.setEndTime(record.getEndTime());
			vo.setFrequency(record.getFrequency());
			vo.setInternalMerchantId(record.getInternalMerchantId());
			vo.setMerchantId(record.getMerchantId());
			vo.setMerchantName(record.getMerchantName());
			vo.setOperator(record.getOperator());
			vo.setProductCode(record.getProductCode());
			vo.setProductName(record.getProductName());
			vo.setThreshold(record.getThreshold());
			vo.setChangeRate(record.getChangeRate());
			RuleRedisCache.setRiskInternalMonitorMerchant(vo);
		} catch (Exception e) {
			logger.error( "调用修改商户监控配置接口异常"  );
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}

	
	@Override
	public String delMerchantMonitorConfig(String configStr) throws TException {
		String result = "success";
		logger.info( "调用删除商户监控配置接口开始" );
		try{
			RiskInternalMonitorMerchant record = (RiskInternalMonitorMerchant) JsonMapperUtil.nonEmptyMapper().fromJson(configStr, RiskInternalMonitorMerchant.class);
			riskInternalMonitorMerchantDao.deleteByPrimaryKey(record.getInternalMerchantId());
			RiskMonitorMerchantVo vo = new RiskMonitorMerchantVo();
			vo.setBeginTime(record.getBeginTime()); 
			vo.setCreateTime(record.getCreateTime());
			vo.setEndTime(record.getEndTime());
			vo.setFrequency(record.getFrequency());
			vo.setInternalMerchantId(record.getInternalMerchantId());
			vo.setMerchantId(record.getMerchantId());
			vo.setMerchantName(record.getMerchantName());
			vo.setOperator(record.getOperator());
			vo.setProductCode(record.getProductCode());
			vo.setProductName(record.getProductName());
			vo.setThreshold(record.getThreshold());
			RuleRedisCache.delRiskInternalMonitorMerchant(vo);
		} catch (Exception e) {
			logger.error( "调用删除商户监控配置接口异常"  );
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}

}
