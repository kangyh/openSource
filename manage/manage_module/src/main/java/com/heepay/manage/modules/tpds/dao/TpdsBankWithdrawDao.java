/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.tpds.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.tpds.entity.TpdsBankWithdraw;

import java.util.Map;

/**
 *
 * 描    述：存管银行提现记录DAO接口
 *
 * 创 建 者： @author wangdong
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
public interface TpdsBankWithdrawDao extends CrudDao<TpdsBankWithdraw> {

    Map<String,Object> findCountAndSum(TpdsBankWithdraw tpdsBankWithdraw);
}