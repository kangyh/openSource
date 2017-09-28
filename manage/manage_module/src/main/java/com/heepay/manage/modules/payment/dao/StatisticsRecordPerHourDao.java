/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.StatisticsRecord;

/**
 * 
* 
* 描    述：按小时统计
*
* 创 建 者： TianYanqing  
* 创建时间： 2017年8月11日 下午3:23:08 
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
public interface StatisticsRecordPerHourDao extends CrudDao<StatisticsRecord> {

	List<StatisticsRecord> findInTimeList(StatisticsRecord statisticsRecord);

  
}