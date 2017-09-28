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
import com.heepay.manage.modules.payment.entity.StatisticsBalanceRecord;
import com.heepay.manage.modules.payment.dao.StatisticsBalanceRecordDao;

/**
 *
 * 描    述：余额统计Service
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
public class StatisticsBalanceRecordService extends CrudService<StatisticsBalanceRecordDao, StatisticsBalanceRecord> {

	public StatisticsBalanceRecord get(String id) {
		return super.get(id);
	}
	
	public List<StatisticsBalanceRecord> findList(StatisticsBalanceRecord statisticsBalanceRecord) {
		return super.findList(statisticsBalanceRecord);
	}
	
	public Page<StatisticsBalanceRecord> findPage(Page<StatisticsBalanceRecord> page, StatisticsBalanceRecord statisticsBalanceRecord) {
		return super.findPage(page, statisticsBalanceRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(StatisticsBalanceRecord statisticsBalanceRecord) {
		super.save(statisticsBalanceRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(StatisticsBalanceRecord statisticsBalanceRecord) {
		super.delete(statisticsBalanceRecord);
	}
	
}