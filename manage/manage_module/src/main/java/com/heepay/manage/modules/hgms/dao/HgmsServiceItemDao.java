/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsBusiness;
import com.heepay.manage.modules.hgms.entity.HgmsServiceItem;
import org.apache.ibatis.annotations.Param;

/**
 * 描    述：业务服务项DAO接口
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-10 15:57:55
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
public interface HgmsServiceItemDao extends CrudDao<HgmsServiceItem> {

    int businessChangeStatus(@Param("businessId") String businessId, @Param("businessStatus") String businessStatus);

    /**
     * 查询businessId下面是否有对应的serviceName的服务类型
     *
     * @param hgmsServiceItem
     * @return
     */
    HgmsServiceItem selectByName(HgmsServiceItem hgmsServiceItem);

    /**
     * 根据businessId修改服务类型中的业务名称
     *
     * @param hgmsBusiness
     */
    void updateBusiName(HgmsBusiness hgmsBusiness);
}