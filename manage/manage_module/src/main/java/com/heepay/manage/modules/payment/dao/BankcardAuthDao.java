/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.dao;

import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.BankcardAuth;

/**
 *
 * 描    述：协议代扣审批DAO接口
 *
 * 创 建 者： @author ld
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
public interface BankcardAuthDao extends CrudDao<BankcardAuth> {
    BankcardAuth getAuthId();

    int entrustedCollectionAuth(Map<String, Object> param);
    int setEnable(Map<String, Object> param);
    int setLimitAmount(Map<String, Object> param);

}