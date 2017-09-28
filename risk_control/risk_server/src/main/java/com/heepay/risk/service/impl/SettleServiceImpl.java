package com.heepay.risk.service.impl;

import java.util.Date;
import java.util.List;

import com.heepay.common.util.StringUtil;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.risk.common.Constants;
import com.heepay.risk.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.risk.dao.PcacBlackListMapper;
import com.heepay.risk.dao.PcacMerchantComparedInvestigationDetailMapper;
import com.heepay.risk.dao.PcacMerchantComparedInvestigationMapper;
import com.heepay.risk.dao.PcacRiskHintInfoMapper;
import com.heepay.risk.entity.PcacBlackList;
import com.heepay.risk.entity.PcacMerchantComparedInvestigation;
import com.heepay.risk.entity.PcacMerchantComparedInvestigationDetail;
import com.heepay.risk.entity.PcacRiskHintInfo;
import com.heepay.risk.enums.SettleResponseStatus;
import com.heepay.rpc.risk.service.BankFraudService;
import com.heepay.rpc.risk.service.SettleService;
import com.heepay.rpc.service.RpcService;

/**
 * 
 * 
 * 描 述：清結算協會推送信息接口
 *
 * 创 建 者：dongzhengjiang 创建时间： 2017年3月3日 下午2:32:27 
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
@RpcService(name = "SettleServiceImpl", processor = SettleService.Processor.class)
public class SettleServiceImpl implements SettleService.Iface {
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private PcacBlackListMapper pcacBlackListMapper;
	@Autowired
	private PcacRiskHintInfoMapper pcacRiskHintInfoMapper;
	@Autowired
	private PcacMerchantComparedInvestigationMapper pcacMerchantComparedInvestigationMapper;
	@Autowired
	private PcacMerchantComparedInvestigationDetailMapper pcacMerchantComparedInvestigationDetailMapper;
     
	/**
	 * 
	  * 
	  * @方法说明：
	  * @author dongzhengjiang
	  * @param 黑名單請求字符串 json格式
	  * @return
	  * @时间：2017年3月3日 下午4:03:35
	 */
	@Override
	public String pushBlackList(String requestInfo) throws TException {
		logger.info("推送黑名单信息:{}",requestInfo);
		SettleBalckListMainVo vo = JsonMapperUtil.buildNonDefaultMapper().fromJson(requestInfo,
				SettleBalckListMainVo.class);
		if (vo == null)
			return SettleResponseStatus.PARAM_ERROR.getValue();
		SettleRequestHeader head = vo.getHead();
		SettleBlackListRequestBody body = vo.getBody();
		PcacBlackList tempblackList=pcacBlackListMapper.selectByBatchNo(head.getIdentification());
		if(tempblackList!=null)
		{   
			logger.info("推送黑名单信息:已经存在批次为{}的黑名单",head.getIdentification());
			return SettleResponseStatus.SUCCESS.getValue();
		}
		List<SettleBlackListVo> list = body.getPcacList();
		if (list != null) {
			for (SettleBlackListVo entity : list) {			
				PcacBlackList blackList = new PcacBlackList();
				blackList.setBachNo(head.getIdentification());
				blackList.setCusName(entity.getCusName());
				blackList.setDocCode(entity.getDocCode());
				blackList.setDocName(entity.getDocName());
				blackList.setDocType(entity.getDocType());
				blackList.setLegDocCode(entity.getLegDocCode());
				blackList.setLegDocType(entity.getLegDocType());
				blackList.setLevel(entity.getLevel());
				blackList.setRegName(entity.getRegName());
				blackList.setRiskType(entity.getRiskType());
				blackList.setValidStatus(entity.getValidStatus());
				blackList.setValidDate(DateUtil.stringToDate(entity.getValidDate(), "yyyyMMdd"));
				blackList.setCreatetime(new Date());
				PcacBlackList exitBlackList=pcacBlackListMapper.selectByCondition(blackList);
				if(exitBlackList!=null)
				{   
					blackList.setBlackId(exitBlackList.getBlackId());
					pcacBlackListMapper.update(blackList);
				}
				else
				{
				    pcacBlackListMapper.insert(blackList);
				}
				if(blackList.getLevel().equals("一级")) {
					RiskLoginBlacklistVo riskLoginBlacklistVo=new RiskLoginBlacklistVo();
					riskLoginBlacklistVo.setCompanyName(blackList.getRegName());
					riskLoginBlacklistVo.setBuziCode(blackList.getDocType().equals("01")?blackList.getDocCode():null);
					riskLoginBlacklistVo.setOwnerId(blackList.getLegDocType().equals("01")?blackList.getLegDocCode():null);
					JedisClusterUtil.getJedisCluster().set(Constants.getPcacMerchantNameRiskLoginBlackKey(blackList.getRegName()), JsonMapperUtil.buildNonDefaultMapper().toJson(riskLoginBlacklistVo));
					if(!StringUtil.isBlank(riskLoginBlacklistVo.getBuziCode()))
						JedisClusterUtil.getJedisCluster().set(Constants.getPcacBuziCodeRiskLoginBlackKey(riskLoginBlacklistVo.getBuziCode()), JsonMapperUtil.buildNonDefaultMapper().toJson(riskLoginBlacklistVo));
					if(!StringUtil.isBlank(riskLoginBlacklistVo.getOwnerId()))
						JedisClusterUtil.getJedisCluster().set(Constants.getPcacOwenerIdRiskLoginBlackKey(riskLoginBlacklistVo.getOwnerId()), JsonMapperUtil.buildNonDefaultMapper().toJson(riskLoginBlacklistVo));
				}
			}
		}
		return SettleResponseStatus.SUCCESS.getValue();
	}
    /**
     * 
      * 
      * @方法说明：商户信息比对协查推送
      * @author dongzhengjiang
      * @param 请求实体json字符串
      * @return
      * @时间：2017年3月6日 上午10:32:51
     */
	@Override
	public String pushMerchantDiffInfo(String requestInfo) throws TException {
		logger.info("推送商户信息比对协查:{}",requestInfo);
		SettleMerchantDifferentRequestVo vo = JsonMapperUtil.buildNonDefaultMapper().fromJson(requestInfo,
				SettleMerchantDifferentRequestVo.class);
		if (vo == null)
			return SettleResponseStatus.PARAM_ERROR.getValue();
		SettleRequestHeader head = vo.getHead();
		SettleMerchantDifferentRequestBodyVo body = vo.getBody();
		PcacMerchantComparedInvestigation tempEntity=pcacMerchantComparedInvestigationMapper.selectByBatchNo(head.getIdentification());
		if(tempEntity!=null)
		{
			logger.info("推送商户信息比对协查:已经存在批次为{}的商户信息比对协查",head.getIdentification());
			return SettleResponseStatus.SUCCESS.getValue();
		}
		List<SettleEntInfo> list=body.getPcacList();
		if(list!=null)
		{
			for(SettleEntInfo entInfo:list)
			{
				
				PcacMerchantComparedInvestigation entity=new PcacMerchantComparedInvestigation();
				entity.setBatchNo(head.getIdentification());
				entity.setCode(entInfo.getCusCode());
				entity.setLegalName(entInfo.getLegDocName());
				entity.setName(entInfo.getRegName());
				entity.setCreateTime(new Date());
				pcacMerchantComparedInvestigationMapper.insert(entity);
				List<SettleMerchantInfoVo> differList=entInfo.getDifferList();
				if(differList!=null)
				{
					for(SettleMerchantInfoVo diffInfo:differList)
					{
						PcacMerchantComparedInvestigationDetail detailEntity=new PcacMerchantComparedInvestigationDetail();
						detailEntity.setBatchNo(head.getIdentification());
						detailEntity.setCode(diffInfo.getCusCode());
						detailEntity.setName(diffInfo.getRegName());
						detailEntity.setLegalName(diffInfo.getLegDocName());
						detailEntity.setInvestigationId(entity.getInvestigationId());					
						pcacMerchantComparedInvestigationDetailMapper.insert(detailEntity);
					}
				}			
			}
		}
		return SettleResponseStatus.SUCCESS.getValue();
	}
     /**
      * 
       * 
       * @方法说明：
       * @author dongzhengjiang
       * @param requestInfo：風險信息json字符串
       * @return
       * @时间：2017年3月3日 下午4:04:59
      */
	@Override
	public String pushriskInfo(String requestInfo) throws TException {
		logger.info("推送风险提示信息:{}",requestInfo);
		SettleBalckListMainVo vo = JsonMapperUtil.buildNonDefaultMapper().fromJson(requestInfo,
				SettleBalckListMainVo.class);
		if (vo == null)
			return SettleResponseStatus.PARAM_ERROR.getValue();
		SettleRequestHeader head = vo.getHead();
		SettleBlackListRequestBody body = vo.getBody();
		PcacRiskHintInfo tempRiskHintInfo=pcacRiskHintInfoMapper.selectByBatchNo(head.getIdentification());
		if(tempRiskHintInfo!=null)
		{
			logger.info("推送风险提示信息:已经存在批次为{}的风险提示信息",head.getIdentification());
			return SettleResponseStatus.SUCCESS.getValue();
		}
		List<SettleBlackListVo> list = body.getPcacList();
		if (list != null) {
			for (SettleBlackListVo entity : list) {
				
				PcacRiskHintInfo riskHintInfo = new PcacRiskHintInfo();
				riskHintInfo.setBatchNo(head.getIdentification());
				riskHintInfo.setCusName(entity.getCusName());
				riskHintInfo.setDocCode(entity.getDocCode());
				riskHintInfo.setDocName(entity.getDocName());
				riskHintInfo.setDocType(entity.getDocType());
				riskHintInfo.setLegDocCode(entity.getLegDocCode());
				riskHintInfo.setLegDocType(entity.getLegDocType());
				riskHintInfo.setLevel(entity.getLevel());
				riskHintInfo.setRegName(entity.getRegName());
				riskHintInfo.setRiskType(entity.getRiskType());
				riskHintInfo.setValidStatus(entity.getValidStatus());
				riskHintInfo.setValidDate(DateUtil.stringToDate(entity.getValidDate(), "yyyyMMdd"));
				riskHintInfo.setCreateTime(new Date());
				pcacRiskHintInfoMapper.insert(riskHintInfo);
			}
		}
		return SettleResponseStatus.SUCCESS.getValue();
	}

}
