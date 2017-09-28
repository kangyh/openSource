/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsBusiness;
import com.heepay.manage.modules.hgms.entity.HgmsServiceItem;
import com.heepay.manage.modules.hgms.entity.HgmsValidContract;

/**
 * 描    述：有效合约DAO接口
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间：2017-06-03 16:49:03
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
public interface HgmsValidContractDao extends CrudDao<HgmsValidContract> {

    /**
     * 修改合约状态
     *
     * @param hgmsValidContract
     */
    void status(HgmsValidContract hgmsValidContract);

    /**
     * 合约的法务审核
     *
     * @param hgmsValidContract
     * @return
     */
    int legalAudit(HgmsValidContract hgmsValidContract);

    /**
     * 合约的法务审核修改
     *
     * @param hgmsValidContract
     * @return
     */
    int legalUpdate(HgmsValidContract hgmsValidContract);

    /**
     * 合约的风控审核
     *
     * @param hgmsValidContract
     * @return
     */
    int rcAudit(HgmsValidContract hgmsValidContract);

    /**
     * 合约的风控审核修改
     *
     * @param hgmsValidContract
     * @return
     */
    int rcUpdate(HgmsValidContract hgmsValidContract);

    /**
     * 根据businessId修改合约中的业务名称
     *
     * @param hgmsBusiness
     */
    void updateBusiName(HgmsBusiness hgmsBusiness);

    /**
     * 根据businessId修改合约中的业务名称
     *
     * @param hgmsServiceItem
     */
    void updateServiceName(HgmsServiceItem hgmsServiceItem);
}