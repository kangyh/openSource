/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskChannelOrderConversionRatio;

import java.util.List;

/**
 *
 * 描    述：风控通道转化率DAO接口
 *
 * 创 建 者： @author xch
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
public interface RiskChannelOrderConversionRatioDao extends CrudDao<RiskChannelOrderConversionRatio> {
    List<RiskChannelOrderConversionRatio> findInTimeList(RiskChannelOrderConversionRatio statisticsRecord);
    List<RiskChannelOrderConversionRatio> findListByDay(RiskChannelOrderConversionRatio statisticsRecord);
    List<RiskChannelOrderConversionRatio> findListByMonth(RiskChannelOrderConversionRatio statisticsRecord);
}