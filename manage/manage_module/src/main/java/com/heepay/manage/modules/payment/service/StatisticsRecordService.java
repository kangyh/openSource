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
import com.heepay.manage.modules.payment.entity.StatisticsRecord;
import com.heepay.manage.modules.payment.dao.StatisticsRecordDao;
import com.heepay.manage.modules.payment.dao.StatisticsRecordPerHourDao;

/**
 *
 * 描    述：财务统计报表Service
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
public class StatisticsRecordService extends CrudService<StatisticsRecordDao, StatisticsRecord> {
	@Autowired
	private StatisticsRecordDao statisticsRecordDao;
	@Autowired
	private StatisticsRecordPerHourDao statisticsRecordPerHourDao;
	
	public StatisticsRecord get(String id) {
		return super.get(id);
	}
	
	public List<StatisticsRecord> findList(StatisticsRecord statisticsRecord) {
		return super.findList(statisticsRecord);
	}

	/**
	 * 
	 * @description实时数据展示
	 * @author TianYanqing      
	 * @created 2017年7月12日 下午1:54:05     
	 * @param statisticsRecord
	 * @return
	 */
	public List<StatisticsRecord> findInTimeList(StatisticsRecord statisticsRecord) {
		return statisticsRecordPerHourDao.findInTimeList(statisticsRecord);
	}

	/**
	 * 
	 * @description按天查找
	 * @author TianYanqing      
	 * @created 2017年7月12日 下午1:55:23     
	 * @param statisticsRecord
	 * @return
	 */
	public List<StatisticsRecord> findListByDay(StatisticsRecord statisticsRecord) {
		return statisticsRecordDao.findListByDay(statisticsRecord);
	}

	/**
	 * 
	 * @description按月查找
	 * @author TianYanqing      
	 * @created 2017年7月12日 下午1:55:43     
	 * @param statisticsRecord
	 * @return
	 */
	public List<StatisticsRecord> findListByMonth(StatisticsRecord statisticsRecord) {
		return statisticsRecordDao.findListByMonth(statisticsRecord);
	}
	
	
	
	public Page<StatisticsRecord> findMerchantPage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.findMerchantList(entity));
		return page;
	}

	public Page<StatisticsRecord> findTransPage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.findTransList(entity));
		return page;
	}
	public Page<StatisticsRecord> findMerchantTransPage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.findMerchantTransList(entity));
		return page;
	}
	public Page<StatisticsRecord> findChannelPage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.findChannelList(entity));
		return page;
	}
	
	public Page<StatisticsRecord> findPage(Page<StatisticsRecord> page, StatisticsRecord statisticsRecord) {
		return super.findPage(page, statisticsRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(StatisticsRecord statisticsRecord) {
		super.save(statisticsRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(StatisticsRecord statisticsRecord) {
		super.delete(statisticsRecord);
	}
	
	 public List<Map<String, Object>> getGatewaySelect(){
    return statisticsRecordDao.getGatewaySelect();
  }
  
  public StatisticsRecord getByChannelCode(String channelCode){
    return statisticsRecordDao.getByChannelCode(channelCode);
  }
  
  /**
   * 
   * @description按天,月，年查
   * @author TianYanqing      
   * @created 2017年7月19日 下午1:56:56     
   * @param page
   * @param entity
   * @return
   */
	public Page<StatisticsRecord> selectByDayPage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.selectByDay(entity));
		return page;
	}
	public Page<StatisticsRecord> selectByMonthPage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.selectByMonth(entity));
		return page;
	}
	public Page<StatisticsRecord> selectByYearPage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.selectByYear(entity));
		return page;
	}
	/**
	 * 
	 * @description运营报表
	 * @author TianYanqing      
	 * @created 2017年8月7日 下午1:27:00     
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<StatisticsRecord> findOperationList(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.findOperationList(entity));
		return page;
	}

	/**
	 *
	 * @description按天,月，年加支付类型,商户,产品组合查
	 * @author tangwei
	 * @created 2017年8月7日 下午1:56:56
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<StatisticsRecord> selectByDayAndPayTypePage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.selectByDayAndPayType(entity));
		return page;
	}
	public Page<StatisticsRecord> selectByMonthAndPayTypePage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.selectByMonthAndPayType(entity));
		return page;
	}
	public Page<StatisticsRecord> selectByYearAndPayTypePage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.selectByYearAndPayType(entity));
		return page;
	}
	public Page<StatisticsRecord> findDayMerchantList(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.findDayMerchantList(entity));
		return page;
	}
	public Page<StatisticsRecord> findMonthMerchantList(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.findMonthMerchantList(entity));
		return page;
	}
	public Page<StatisticsRecord> findYearMerchantList(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.findYearMerchantList(entity));
		return page;
	}
	public Page<StatisticsRecord> selectByDayAndProductCodePage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.selectByDayAndProductCode(entity));
		return page;
	}
	public Page<StatisticsRecord> selectByMonthAndProductCodePage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.selectByMonthAndProductCode(entity));
		return page;
	}
	public Page<StatisticsRecord> selectByYearAndProductCodePage(Page<StatisticsRecord> page, StatisticsRecord entity) {
		entity.setPage(page);
		page.setList(statisticsRecordDao.selectByYearAndProductCode(entity));
		return page;
	}
}