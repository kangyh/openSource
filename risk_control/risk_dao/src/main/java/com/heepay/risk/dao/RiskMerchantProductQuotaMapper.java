package com.heepay.risk.dao;


import java.util.List;

import com.heepay.risk.entity.RiskMerchantProductQuota;
import com.heepay.risk.vo.MerchantProductSelectVO;


public interface RiskMerchantProductQuotaMapper {
 
    List<RiskMerchantProductQuota> selectProductQuotaList(MerchantProductSelectVO vo);
    Integer insert(RiskMerchantProductQuota entity);
    Integer update(RiskMerchantProductQuota entity);
    List<RiskMerchantProductQuota> selectMerchantProductQuotaList();
}