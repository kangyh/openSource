/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.dao;

import java.util.Date;
import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.trial.entity.MerchantAccountMonthDaily;

/**
 *
 * 描    述：商户出入金月汇总DAO接口
 *
 * 创 建 者： @author yangcl
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
public interface MerchantAccountMonthDailyDao extends CrudDao<MerchantAccountMonthDaily> {
	public int batchInsert(List<MerchantAccountMonthDaily> list);
	public int deleteByCreateTime(Date createTime);
}