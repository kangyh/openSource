package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleMerchantRecordHis;
import com.heepay.billing.entity.SettleMerchantRecordHisExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SettleMerchantRecordHisMapper {
    int countByExample(SettleMerchantRecordHisExample example);

    int deleteByExample(SettleMerchantRecordHisExample example);

    int deleteByPrimaryKey(Long hisId);

    int insert(SettleMerchantRecordHis record);

    int insertSelective(SettleMerchantRecordHis record);

    List<SettleMerchantRecordHis> selectByExample(SettleMerchantRecordHisExample example);

    SettleMerchantRecordHis selectByPrimaryKey(Long hisId);

    int updateByExampleSelective(@Param("record") SettleMerchantRecordHis record, @Param("example") SettleMerchantRecordHisExample example);

    int updateByExample(@Param("record") SettleMerchantRecordHis record, @Param("example") SettleMerchantRecordHisExample example);

    int updateByPrimaryKeySelective(SettleMerchantRecordHis record);

    int updateByPrimaryKey(SettleMerchantRecordHis record);

	int insertMap(Map<String, Object> map);
}