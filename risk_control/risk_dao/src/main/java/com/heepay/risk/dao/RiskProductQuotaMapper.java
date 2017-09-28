package com.heepay.risk.dao;

import java.util.List;

import com.heepay.risk.entity.RiskMerchantProductQuota;
import com.heepay.risk.entity.RiskProductQuota;



public interface RiskProductQuotaMapper {
   
    RiskProductQuota selectByProductCode(com.heepay.risk.entity.RiskMerchantProductQuota model);
    Integer insert(RiskProductQuota entity);
    Integer update(RiskProductQuota entity);
    List<RiskProductQuota> selectProductQuotaList();
}