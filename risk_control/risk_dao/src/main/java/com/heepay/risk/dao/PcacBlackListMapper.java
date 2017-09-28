package com.heepay.risk.dao;

import com.heepay.risk.entity.PcacBlackList;

public interface PcacBlackListMapper {
     
    int insert(PcacBlackList record);
    PcacBlackList selectByBatchNo(String batchNo);
    PcacBlackList selectByCondition(PcacBlackList entity);
    int update(PcacBlackList entity);
}