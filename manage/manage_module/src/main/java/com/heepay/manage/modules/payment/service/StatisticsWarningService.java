/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.entity.StatisticsWarning;
import com.heepay.manage.modules.payment.dao.StatisticsWarningDao;

/**
 *
 * 描    述：商户成功率监控Service
 *
 * 创 建 者： @author tyq
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
@Transactional(readOnly = true)
public class StatisticsWarningService extends CrudService<StatisticsWarningDao, StatisticsWarning> {

	public StatisticsWarning get(String id) {
		return super.get(id);
	}
	
	public List<StatisticsWarning> findList(StatisticsWarning statisticsWarning) {
		return super.findList(statisticsWarning);
	}
	
	public Page<StatisticsWarning> findPage(Page<StatisticsWarning> page, StatisticsWarning statisticsWarning) {
		return super.findPage(page, statisticsWarning);
	}
	
	@Transactional(readOnly = false)
	public void save(StatisticsWarning statisticsWarning) {
		super.save(statisticsWarning,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(StatisticsWarning statisticsWarning) {
		super.delete(statisticsWarning);
	}
	
}