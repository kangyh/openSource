package com.heepay.risk.dao;

import com.heepay.risk.entity.PcacMerchantComparedInvestigation;


public interface PcacMerchantComparedInvestigationMapper {
   
    int insert(PcacMerchantComparedInvestigation record);
    PcacMerchantComparedInvestigation selectByBatchNo(String batchNo);
    
}