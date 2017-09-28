/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantFuctions;

/**
 * 描    述：商户权限DAO接口
 * <p>
 * 创 建 者： guozx@9186.com
 * 创建时间： 2017-07-31 15:12:16
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@MyBatisDao
public interface HgmsMerchantFuctionsDao extends CrudDao<HgmsMerchantFuctions> {

    /**
     * 通过主键id查询权限代码
     *
     * @param id id
     * @return 权限代码
     */
    String queryPermissionCodeById(Integer id);
}