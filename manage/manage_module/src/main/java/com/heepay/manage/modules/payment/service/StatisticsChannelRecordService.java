/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.entity.StatisticsChannelRecord;
import com.heepay.manage.modules.payment.dao.StatisticsChannelRecordDao;

/**
 *
 * 描    述：通道数据统计Service
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
public class StatisticsChannelRecordService extends CrudService<StatisticsChannelRecordDao, StatisticsChannelRecord> {

  @Autowired
  private StatisticsChannelRecordDao statisticsChannelRecordDao;
  
	@Autowired
	protected StatisticsChannelRecordDao dao;
	
	public StatisticsChannelRecord get(String id) {
		return super.get(id);
	}
	
	public List<StatisticsChannelRecord> findList(StatisticsChannelRecord statisticsChannelRecord) {
		return super.findList(statisticsChannelRecord);
	}

	public List<StatisticsChannelRecord> findInTimeList(StatisticsChannelRecord statisticsChannelRecord) {
		return dao.findInTimeList(statisticsChannelRecord);
	}

	public List<StatisticsChannelRecord> findTotalList(StatisticsChannelRecord statisticsChannelRecord) {
		return dao.findTotalList(statisticsChannelRecord);
	}
	public List<StatisticsChannelRecord> findweekList(StatisticsChannelRecord statisticsChannelRecord) {
		return dao.findweekList(statisticsChannelRecord);
	}
	public List<StatisticsChannelRecord> findOldList(StatisticsChannelRecord statisticsChannelRecord) {
		return dao.findOldList(statisticsChannelRecord);
	}
	
	public Page<StatisticsChannelRecord> findPage(Page<StatisticsChannelRecord> page, StatisticsChannelRecord statisticsChannelRecord) {
		return super.findPage(page, statisticsChannelRecord);
	}

	/**
	 * 
	 * @description分页查询原始数据
	 * @author TianYanqing      
	 * @created 2017年5月25日 上午9:51:34     
	 * @param page
	 * @param statisticsChannelRecord
	 * @return
	 */
	public Page<StatisticsChannelRecord> findOldPage(Page<StatisticsChannelRecord> page, StatisticsChannelRecord statisticsChannelRecord) {
		statisticsChannelRecord.setPage(page);
		page.setList(dao.findOldList(statisticsChannelRecord));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(StatisticsChannelRecord statisticsChannelRecord) {
		super.save(statisticsChannelRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(StatisticsChannelRecord statisticsChannelRecord) {
		super.delete(statisticsChannelRecord);
	}
	
	public List<Map<String, Object>> getGatewaySelect(){
	  return statisticsChannelRecordDao.getGatewaySelect();
  }
	
	public StatisticsChannelRecord getByChannelCode(String channelCode){
	  return statisticsChannelRecordDao.getByChannelCode(channelCode);
	}
	
	
}