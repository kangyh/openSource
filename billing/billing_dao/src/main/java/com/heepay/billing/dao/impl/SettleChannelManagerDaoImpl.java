package com.heepay.billing.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleChannelManagerMapper;
import com.heepay.billing.entity.SettleChannelManager;
import com.heepay.billing.entity.SettleChannelManagerExample;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingChannelManageCheckFlg;

/**
 **
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2016年9月25日下午3:36:15
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

@Repository
public class SettleChannelManagerDaoImpl extends BaseDaoImpl<SettleChannelManager, SettleChannelManagerExample> implements SettleChannelManagerMapper{
	
	private static final Logger logger = LoggerFactory.getLogger(SettleChannelManagerDaoImpl.class);	
	
	//默认构造方法设置命名空间
	public SettleChannelManagerDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleChannelManagerMapper");
	}

	/**
	 * 
	 * @方法说明： 检查是否符合通道对账规则
	 * @author chenyanming
	 * @param channelCode
	 * @param channelType
	 * @return
	 * @时间：2016年9月26日上午9:10:54
	 */
	@Override
	public List<SettleChannelManager> checkSettleChannelManagerRule(String channelCode, String channelType, String settleWay, String ruleType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelCode", channelCode);
		map.put("channelType", channelType);
		map.put("settleWay", settleWay);
		map.put("ruleType", ruleType);
		map.put("effectFlg", BillingBecomeEffect.BBEY.getValue());
		return super.getSqlSession().selectList(this.getNs()+".checkSettleChannelManagerRule",map);
	}

	
	/**
	 * 获取通道管理数据
	 */
	@Override
	public List<SettleChannelManager> querySettleChannelManagerDetail(String checkFlg, String effectFlg) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkFlg", checkFlg);
		map.put("effectFlg", effectFlg);
		return super.getSqlSession().selectList(this.getNs()+".querySettleChannelManagerDetail",map);
		
	}

	/*
	 * 根据通道编码获取通道名称
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.SettleChannelManagerMapper#findChannelNameBySettleChannelManageChannelCode(java.lang.String)
	 */
	@Override
	public SettleChannelManager findChannelNameBySettleChannelManageChannelCode(String channelCode) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channelCode", channelCode);
			List<SettleChannelManager> settleChannelManagersList = super.getSqlSession().selectList(this.getNs()+".findChannelNameBySettleChannelManageChannelCode",map);
			if(null != settleChannelManagersList && settleChannelManagersList.size()>0){
				return settleChannelManagersList.get(0);
			}
		} catch (Exception e) {
			logger.error("SettleChannelManagerDaoImpl findChannelNameBySettleChannelManageChannelCode has a error {根据通道编码获取通道名称错误！}", e);
		}
		return null;
	}

	@Override
	public List<SettleChannelManager> querySettleChannelManagerDetail(String effectFlg) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("effectFlg", effectFlg);
		return super.getSqlSession().selectList(this.getNs()+".queryEffectSettleChannel",map);
	}

	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年1月17日上午10:48:48
	 */
	public List<SettleChannelManager> querySettleChannelManagerDetail(Map map){
		
		return super.getSqlSession().selectList(this.getNs()+".querySettleChannelManager", map);
	}

	@Override
	public List<SettleChannelManager> querySettleChannelManagerWarnDetail(String effectFlg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("effectFlg", effectFlg);
		return super.getSqlSession().selectList(this.getNs()+".queryEffectSettleChannelWarn",map);
	}

	@Override
	public SettleChannelManager getSettleChannelManager(String channelCode, String channelType, String remoteAdress) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkFlg", BillingChannelManageCheckFlg.BCMCFF.getValue());
		map.put("effectFlg", BillingBillStatus.BBSTATUSY.getValue());
		map.put("channelCode", channelCode);
		map.put("channelType", channelType);
		map.put("remoteAdress", remoteAdress);
		return super.getSqlSession().selectOne(this.getNs()+".getSettleChannelManager",map);
	}
	
	
}
