/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.StatisticsTransRecordDao;
import com.heepay.manage.modules.payment.entity.StatisticsBalanceRecord;
import com.heepay.manage.modules.payment.entity.StatisticsTransRecord;

/**
 *
 * 描    述：交易数据统计Service
 *
 * 创 建 者： @author id
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
public class StatisticsTransRecordService extends CrudService<StatisticsTransRecordDao, StatisticsTransRecord> {

	@Autowired
	protected StatisticsTransRecordDao dao;
	
	
	public List<StatisticsTransRecord> findTransTypeList(StatisticsTransRecord statisticsTransRecord){
		return dao.findTransTypeList(statisticsTransRecord);
	}
	public List<StatisticsTransRecord> findPayTypeList(StatisticsTransRecord statisticsTransRecord){
		return dao.findPayTypeList(statisticsTransRecord);
	}
	public List<StatisticsTransRecord> findProductList(StatisticsTransRecord statisticsTransRecord){
		return dao.findProductList(statisticsTransRecord);
	}
	
	public StatisticsTransRecord get(String id) {
		return super.get(id);
	}
	
	public List<StatisticsTransRecord> findList(StatisticsTransRecord statisticsTransRecord) {
		return super.findList(statisticsTransRecord);
	}

	public List<StatisticsTransRecord> findOldList(StatisticsTransRecord statisticsTransRecord) {
		return dao.findOldList(statisticsTransRecord);
	}
	
	public Page<StatisticsTransRecord> findPage(Page<StatisticsTransRecord> page, StatisticsTransRecord statisticsTransRecord) {
		return super.findPage(page, statisticsTransRecord);
	}
	
	/**
	 * 
	 * @description分页统计出入金
	 * @author TianYanqing      
	 * @created 2017年5月27日 上午11:26:47     
	 * @param page
	 * @param statisticsBalanceRecord
	 * @return
	 */
	public Page<StatisticsBalanceRecord> findBalancePage(Page<StatisticsBalanceRecord> page, StatisticsBalanceRecord statisticsBalanceRecord) {
		statisticsBalanceRecord.setPage(page);
		page.setList(dao.findBalanceList(statisticsBalanceRecord));
		return page;
	}
	
	/**
	 * @description统计出入金
	 * @author TianYanqing      
	 * @created 2017年5月27日 上午11:27:00     
	 * @param page
	 * @param statisticsBalanceRecord
	 * @return
	 */
	public List<StatisticsBalanceRecord> findBalanceList(StatisticsBalanceRecord statisticsBalanceRecord) {
		return dao.findBalanceList(statisticsBalanceRecord); 
	}
	
	/**
	 * 
	 * @description分页查询原始数据
	 * @author TianYanqing      
	 * @created 2017年5月25日 上午9:41:19     
	 * @param page
	 * @param statisticsTransRecord
	 * @return
	 */
	public Page<StatisticsTransRecord> findOldPage(Page<StatisticsTransRecord> page, StatisticsTransRecord statisticsTransRecord) {
			statisticsTransRecord.setPage(page);
			page.setList(dao.findOldList(statisticsTransRecord));
			return page;
	}
	
	@Transactional(readOnly = false)
	public void save(StatisticsTransRecord statisticsTransRecord) {
		super.save(statisticsTransRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(StatisticsTransRecord statisticsTransRecord) {
		super.delete(statisticsTransRecord);
	}
	
}