package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleDifferRecordHis;
import com.heepay.billing.entity.SettleDifferRecordHisExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SettleDifferRecordHisMapper {
    int countByExample(SettleDifferRecordHisExample example);

    int deleteByExample(SettleDifferRecordHisExample example);

    int deleteByPrimaryKey(Long hisId);

    int insert(SettleDifferRecordHis record);

    int insertSelective(SettleDifferRecordHis record);

    List<SettleDifferRecordHis> selectByExample(SettleDifferRecordHisExample example);

    SettleDifferRecordHis selectByPrimaryKey(Long hisId);

    int updateByExampleSelective(@Param("record") SettleDifferRecordHis record, @Param("example") SettleDifferRecordHisExample example);

    int updateByExample(@Param("record") SettleDifferRecordHis record, @Param("example") SettleDifferRecordHisExample example);

    int updateByPrimaryKeySelective(SettleDifferRecordHis record);

    int updateByPrimaryKey(SettleDifferRecordHis record);

	int insertMap(Map<String, Object> map);
}