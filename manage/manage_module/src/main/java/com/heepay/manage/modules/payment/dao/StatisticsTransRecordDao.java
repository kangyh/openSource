/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.StatisticsBalanceRecord;
import com.heepay.manage.modules.payment.entity.StatisticsTransRecord;

/**
 *
 * 描    述：交易数据统计DAO接口
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
public interface StatisticsTransRecordDao extends CrudDao<StatisticsTransRecord> {
	public List<StatisticsTransRecord> findTransTypeList(StatisticsTransRecord statisticsTransRecord);
	public List<StatisticsTransRecord> findPayTypeList(StatisticsTransRecord statisticsTransRecord);
	public List<StatisticsTransRecord> findProductList(StatisticsTransRecord statisticsTransRecord);
	public List<StatisticsTransRecord> findOldList(StatisticsTransRecord statisticsTransRecord);
	//获取出入金余额
	public List<StatisticsBalanceRecord> findBalanceList(StatisticsBalanceRecord statisticsBalanceRecord);
	
	
}