package com.heepay.risk.dao.impl;

import com.heepay.risk.dao.PcacRiskHintInfoMapper;
import com.heepay.risk.dao.RiskChannelOrderConversionRatioMapper;
import com.heepay.risk.entity.PcacRiskHintInfo;
import com.heepay.risk.entity.RiskChannelOrderConversionRatio;
import org.springframework.stereotype.Repository;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-08-17 13:29
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
public class RiskChannelOrderConversionRatioDaoImpl extends BaseDaoImpl<RiskChannelOrderConversionRatio> implements RiskChannelOrderConversionRatioMapper {

    // 默认构造方法设置命名空间
    public RiskChannelOrderConversionRatioDaoImpl() {
        super.setNs("com.heepay.risk.dao.RiskChannelOrderConversionRatioMapper");
    }

    @Override
    public int insert(RiskChannelOrderConversionRatio record) {
        return super.getSqlSession().insert(this.getNs()+".insert",record);
    }
}
