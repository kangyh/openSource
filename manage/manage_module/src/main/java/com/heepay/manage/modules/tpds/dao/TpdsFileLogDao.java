package com.heepay.manage.modules.tpds.dao;

/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.tpds.entity.TpdsFileLog;
import com.heepay.manage.modules.tpds.entity.TpdsMerchantAccount;

/**
 *
 * 描    述：存管日记DAO接口
 *
 * 创 建 者： @author wj
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
public interface TpdsFileLogDao extends CrudDao<TpdsFileLog> {
	
	int updateEntity(TpdsFileLog record);
}