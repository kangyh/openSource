package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleBillRecordHis;
import com.heepay.billing.entity.SettleBillRecordHisExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SettleBillRecordHisMapper {
    int countByExample(SettleBillRecordHisExample example);

    int deleteByExample(SettleBillRecordHisExample example);

    int deleteByPrimaryKey(Long hisId);

    int insert(SettleBillRecordHis record);

    int insertSelective(SettleBillRecordHis record);

    List<SettleBillRecordHis> selectByExample(SettleBillRecordHisExample example);

    SettleBillRecordHis selectByPrimaryKey(Long hisId);

    int updateByExampleSelective(@Param("record") SettleBillRecordHis record, @Param("example") SettleBillRecordHisExample example);

    int updateByExample(@Param("record") SettleBillRecordHis record, @Param("example") SettleBillRecordHisExample example);

    int updateByPrimaryKeySelective(SettleBillRecordHis record);

    int updateByPrimaryKey(SettleBillRecordHis record);

	int insertMap(Map<String, Object> map);
}