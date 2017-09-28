/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantInfo;

/**
 * 描    述：资金归集商户DAO接口
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间：
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
public interface HgmsMerchantInfoDao extends CrudDao<HgmsMerchantInfo> {

    /**
     * 保存用户信息
     *
     * @param hgmsMerchantInfo 用户对象
     * @return 用户的id
     */
    int insert(HgmsMerchantInfo hgmsMerchantInfo);

    /**
     * 保存用户信息
     *
     * @param hgmsMerchantInfo 用户对象
     * @return 用户的id
     */
    int update(HgmsMerchantInfo hgmsMerchantInfo);

    /**
     * 法务审核
     *
     * @param hgmsMerchantInfo 用户对象
     * @return 用户的id
     */
    void legalAuditStatus(HgmsMerchantInfo hgmsMerchantInfo);

    /**
     * 风控审核
     *
     * @param hgmsMerchantInfo 用户对象
     * @return 用户的id
     */
    void rcAuditStatus(HgmsMerchantInfo hgmsMerchantInfo);

    /**
     * 验证用户的唯一性
     *
     * @param email
     * @return
     */
    HgmsMerchantInfo queryEmailExist(String email);

    /**
     * 禁用和启用商户
     *
     * @param hgmsMerchantInfo
     */
    void status(HgmsMerchantInfo hgmsMerchantInfo);

    /**
     * 根据companyId查询商户
     *
     * @param companyId
     * @return
     */
    HgmsMerchantInfo getByCompanyId(String companyId);
}