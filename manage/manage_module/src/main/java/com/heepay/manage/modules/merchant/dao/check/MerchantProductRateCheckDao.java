/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.dao.check;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * 描 述：商户产品费率DAO接口
 *
 * 创 建 者： @author wangl
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
public interface MerchantProductRateCheckDao extends CrudDao<MerchantProductRate> {

    List<MerchantRateNew> findPageNotDefaul(MerchantRateNew merchantRateNew);

    List<MerchantRateNew> findPageDefaul(MerchantRateNew merchantRateNew);

    MerchantProductRate exist(MerchantProductRate merchantProductRate);

    void status(MerchantProductRate merchantProductRate);

    void resetKey(MerchantProductRate merchantProductRate);

    List<MerchantProductRate> findMerchantProduct(String merchantId);

    /**
     * @方法说明：费率审核
     * @时间： 2017-06-05 02:05 PM
     * @创建人：wangl
     */
    List<MerchantProductRate> getListByPage(MerchantProductRate merchantProductRate);

    void uodateRateAudit(@Param("id") String id, @Param("rateAudit") String rateAudit);
}