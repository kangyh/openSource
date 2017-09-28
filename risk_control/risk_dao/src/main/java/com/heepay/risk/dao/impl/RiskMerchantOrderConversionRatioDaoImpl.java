package com.heepay.risk.dao.impl;

import com.heepay.risk.dao.RiskMerchantOrderConversionRatioMapper;
import com.heepay.risk.dao.RiskMerchantProductQuotaMapper;
import com.heepay.risk.entity.RiskMerchantOrderConversionRatio;
import com.heepay.risk.entity.RiskMerchantProductQuota;
import com.heepay.risk.vo.MerchantProductSelectVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-08-14 13:03
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
@Repository
public class RiskMerchantOrderConversionRatioDaoImpl  extends BaseDaoImpl<RiskMerchantOrderConversionRatio> implements RiskMerchantOrderConversionRatioMapper {

    // 默认构造方法设置命名空间
    public RiskMerchantOrderConversionRatioDaoImpl() {
        super.setNs("com.heepay.risk.dao.RiskMerchantOrderConversionRatioMapper");
    }


    @Override
    public int insert(RiskMerchantOrderConversionRatio record) {
        return super.getSqlSession().insert(this.getNs()+".insert",record);
    }
}
