/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsBusiness;

/**
 * 描    述：业务管理DAO接口
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-10 15:57:46
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
public interface HgmsBusinessDao extends CrudDao<HgmsBusiness> {

    /**
     * 根据服务项名称查询是否重复
     *
     * @param businessName
     * @return
     */
    HgmsBusiness selectByName(String businessName);
}