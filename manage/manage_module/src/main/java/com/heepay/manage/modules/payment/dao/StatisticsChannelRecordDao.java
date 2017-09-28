/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.StatisticsChannelRecord;

/**
 *
 * 描    述：通道数据统计DAO接口
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
@MyBatisDao
public interface StatisticsChannelRecordDao extends CrudDao<StatisticsChannelRecord> {
	
	public List<StatisticsChannelRecord> findInTimeList(StatisticsChannelRecord statisticsChannelRecord);
	public List<StatisticsChannelRecord> findTotalList(StatisticsChannelRecord statisticsChannelRecord);
	public List<StatisticsChannelRecord> findweekList(StatisticsChannelRecord statisticsChannelRecord);
	public List<StatisticsChannelRecord> findOldList(StatisticsChannelRecord statisticsChannelRecord);
	public List<Map<String, Object>> getGatewaySelect();
	public StatisticsChannelRecord getByChannelCode(String channelCode);
}