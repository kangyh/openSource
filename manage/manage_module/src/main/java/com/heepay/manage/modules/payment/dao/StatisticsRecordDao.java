/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.StatisticsRecord;

/**
 *
 * 描    述：财务统计报表DAO接口
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
@MyBatisDao
public interface StatisticsRecordDao extends CrudDao<StatisticsRecord> {
	
	List<StatisticsRecord> findMerchantList(StatisticsRecord record);
	List<StatisticsRecord> findTransList(StatisticsRecord record);
	List<StatisticsRecord> findMerchantTransList(StatisticsRecord record);
	List<StatisticsRecord> findChannelList(StatisticsRecord record);
	List<StatisticsRecord> findInTimeList(StatisticsRecord statisticsRecord);
	List<StatisticsRecord> findListByDay(StatisticsRecord statisticsRecord);
	List<StatisticsRecord> findListByMonth(StatisticsRecord statisticsRecord);
	public List<Map<String, Object>> getGatewaySelect();
	public StatisticsRecord getByChannelCode(String channelCode);
	public List<String> findTableHead(StatisticsRecord statisticsRecord);
	//按天展示
	List<StatisticsRecord> selectByDay(StatisticsRecord statisticsRecord);
	//按月展示
	List<StatisticsRecord> selectByMonth(StatisticsRecord statisticsRecord);
	//按年展示
	List<StatisticsRecord> selectByYear(StatisticsRecord statisticsRecord);
	//运营报表
	List<StatisticsRecord> findOperationList(StatisticsRecord statisticsRecord);
	//按支付类型取表头
	public List<String> findTableHeadByPayType(StatisticsRecord statisticsRecord);
	//按产品类型取表头
	public List<String> findTableHeadByProductCode(StatisticsRecord statisticsRecord);
	//按时间的天取表头
	public List<String> findTableHeadByDay(StatisticsRecord statisticsRecord);
	//按时间的月取表头
	public List<String> findTableHeadByMonth(StatisticsRecord statisticsRecord);
	//按时间的年取表头
	public List<String> findTableHeadByYear(StatisticsRecord statisticsRecord);
  	//按天跟支付类型展示
	List<StatisticsRecord> selectByDayAndPayType(StatisticsRecord statisticsRecord);
	//按月跟支付类型展示
	List<StatisticsRecord> selectByMonthAndPayType(StatisticsRecord statisticsRecord);
	//按年跟支付类型展示
	List<StatisticsRecord> selectByYearAndPayType(StatisticsRecord statisticsRecord);
	//按天跟商户展示
	List<StatisticsRecord> findDayMerchantList(StatisticsRecord statisticsRecord);
	//按月跟商户展示
	List<StatisticsRecord> findMonthMerchantList(StatisticsRecord statisticsRecord);
	//按年跟商户展示
	List<StatisticsRecord> findYearMerchantList(StatisticsRecord statisticsRecord);
	//按天跟产品类型展示
	List<StatisticsRecord> selectByDayAndProductCode(StatisticsRecord statisticsRecord);
	//按月跟产品类型展示
	List<StatisticsRecord> selectByMonthAndProductCode(StatisticsRecord statisticsRecord);
	//按年跟产品类型展示
	List<StatisticsRecord> selectByYearAndProductCode(StatisticsRecord statisticsRecord);
}