/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantFuctions;

/**
 *
 * 描    述：商户权限DAO接口
 *
 * 创 建 者： @author ly
 * 创建时间：2016-9-6 17:42:41
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
public interface MerchantFuctionsDao extends CrudDao<MerchantFuctions> {
    /**
     * 通过主键id查询权限代码
     * @param id            id
     * @return              权限代码
     */
    String queryPermissionCodeById(Integer id);
}