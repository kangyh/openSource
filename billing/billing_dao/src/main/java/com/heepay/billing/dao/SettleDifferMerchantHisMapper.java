package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleDifferMerchantHis;
import com.heepay.billing.entity.SettleDifferMerchantHisExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SettleDifferMerchantHisMapper {
    int countByExample(SettleDifferMerchantHisExample example);

    int deleteByExample(SettleDifferMerchantHisExample example);

    int deleteByPrimaryKey(Long hisId);

    int insert(SettleDifferMerchantHis record);

    int insertSelective(SettleDifferMerchantHis record);

    List<SettleDifferMerchantHis> selectByExample(SettleDifferMerchantHisExample example);

    SettleDifferMerchantHis selectByPrimaryKey(Long hisId);

    int updateByExampleSelective(@Param("record") SettleDifferMerchantHis record, @Param("example") SettleDifferMerchantHisExample example);

    int updateByExample(@Param("record") SettleDifferMerchantHis record, @Param("example") SettleDifferMerchantHisExample example);

    int updateByPrimaryKeySelective(SettleDifferMerchantHis record);

    int updateByPrimaryKey(SettleDifferMerchantHis record);

	int insertMap(Map<String, Object> map);
}