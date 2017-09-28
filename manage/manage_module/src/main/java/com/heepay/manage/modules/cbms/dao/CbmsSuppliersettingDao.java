/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.cbms.entity.CbmsSuppliersetting;

import java.util.List;

/**
 *
 * 描    述：商户信息显示DAO接口
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月9日 9:51:25
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
public interface CbmsSuppliersettingDao extends CrudDao<CbmsSuppliersetting> {

    /**
     *根据merchantNo获取CbmsCbmsSuppliersetting对象
     * @param merchantNo
     * @return
     */
    CbmsSuppliersetting getMerchantByNo(Integer merchantNo);

    /**
     *根据email获取CbmsCbmsSuppliersetting对象
     * @param email
     * @return
     */
    CbmsSuppliersetting queryEmailExist(String email);

    /**
     * 商户管理
     * @param cbmsSuppliersetting
     * @return
     */
    List<CbmsSuppliersetting> findCbmsSuppliersettingList(CbmsSuppliersetting cbmsSuppliersetting);
    void updatecbmsSuppliersetting(CbmsSuppliersetting cbmsSuppliersetting);
}