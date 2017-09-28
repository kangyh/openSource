/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.entity.RiskInternalMonitorChannel;
import com.heepay.manage.modules.risk.dao.RiskInternalMonitorChannelDao;

/**
 *
 * 描    述：内部监控通道配置Service
 *
 * 创 建 者： @author wj
 * 创建时间：
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
@Service
public class RiskInternalMonitorChannelService extends CrudService<RiskInternalMonitorChannelDao, RiskInternalMonitorChannel> {

	public RiskInternalMonitorChannel get(String id) {
		return super.get(id);
	}
	
	public List<RiskInternalMonitorChannel> findList(RiskInternalMonitorChannel riskInternalMonitorChannel) {
		return super.findList(riskInternalMonitorChannel);
	}
	
	public Page<RiskInternalMonitorChannel> findPage(Page<RiskInternalMonitorChannel> page, RiskInternalMonitorChannel riskInternalMonitorChannel) {
		return super.findPage(page, riskInternalMonitorChannel);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskInternalMonitorChannel riskInternalMonitorChannel) {
		super.save(riskInternalMonitorChannel,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskInternalMonitorChannel riskInternalMonitorChannel) {
		super.delete(riskInternalMonitorChannel);
	}
	
}