package com.heepay.billing.dao;

import com.heepay.billing.entity.ClearingMerchantRecordHis;
import com.heepay.billing.entity.ClearingMerchantRecordHisExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ClearingMerchantRecordHisMapper {
    int countByExample(ClearingMerchantRecordHisExample example);

    int deleteByExample(ClearingMerchantRecordHisExample example);

    int deleteByPrimaryKey(Long hisIs);

    int insert(ClearingMerchantRecordHis record);

    int insertSelective(ClearingMerchantRecordHis record);

    List<ClearingMerchantRecordHis> selectByExample(ClearingMerchantRecordHisExample example);

    ClearingMerchantRecordHis selectByPrimaryKey(Long hisIs);

    int updateByExampleSelective(@Param("record") ClearingMerchantRecordHis record, @Param("example") ClearingMerchantRecordHisExample example);

    int updateByExample(@Param("record") ClearingMerchantRecordHis record, @Param("example") ClearingMerchantRecordHisExample example);

    int updateByPrimaryKeySelective(ClearingMerchantRecordHis record);

    int updateByPrimaryKey(ClearingMerchantRecordHis record);

	int insertMap(Map<String, Object> map);
}