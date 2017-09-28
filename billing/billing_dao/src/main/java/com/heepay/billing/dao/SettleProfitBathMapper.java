package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleProfitBath;
import com.heepay.billing.entity.SettleProfitBathExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SettleProfitBathMapper {
    int countByExample(SettleProfitBathExample example);

    int deleteByExample(SettleProfitBathExample example);

    int deleteByPrimaryKey(Long settleProfitId);

    int insert(SettleProfitBath record);

    int insertSelective(SettleProfitBath record);

    List<SettleProfitBath> selectByExample(SettleProfitBathExample example);

    SettleProfitBath selectByPrimaryKey(Long settleProfitId);

    int updateByExampleSelective(@Param("record") SettleProfitBath record, @Param("example") SettleProfitBathExample example);

    int updateByExample(@Param("record") SettleProfitBath record, @Param("example") SettleProfitBathExample example);

    int updateByPrimaryKeySelective(SettleProfitBath record);

    int updateByPrimaryKey(SettleProfitBath record);
    
    public void updateSettleProfitBatchBySettleNo(Map<String, Object> map);
}