/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.cbms.entity.CbmsCountrysetting;

/**
 *
 * 描    述：国家代码和编号DAO接口
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月10日 14:03:25
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
public interface CbmsCountrysettingDao extends CrudDao<CbmsCountrysetting> {

    /**
     *根据CountryName获取CbmsCountrysetting对象
     * @return CbmsCountrysetting
     */
    CbmsCountrysetting getCbmsCountrysettingByCountryName(String countryName);
}