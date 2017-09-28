package com.heepay.risk.dao;

import com.heepay.risk.entity.PcacRiskHintInfo;

public interface PcacRiskHintInfoMapper {
   

    int insert(PcacRiskHintInfo record);
    PcacRiskHintInfo selectByBatchNo(String batchNo);


}