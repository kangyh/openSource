package com.heepay.risk.dao;

import java.util.List;

import com.heepay.risk.entity.RiskIncomeQuota;


public interface RiskIncomeQuotaMapper {
   


    int insert(RiskIncomeQuota record);

    int update(RiskIncomeQuota record);
    List<RiskIncomeQuota> getlist();
    RiskIncomeQuota get(Long id);
}