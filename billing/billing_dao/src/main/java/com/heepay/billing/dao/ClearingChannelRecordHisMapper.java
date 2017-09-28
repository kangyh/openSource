package com.heepay.billing.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.heepay.billing.entity.ClearingChannelRecordHis;
import com.heepay.billing.entity.ClearingChannelRecordHisExample;

public interface ClearingChannelRecordHisMapper {
    int countByExample(ClearingChannelRecordHisExample example);

    int deleteByExample(ClearingChannelRecordHisExample example);

    int deleteByPrimaryKey(Long hisId);

    int insert(ClearingChannelRecordHis record);

    int insertSelective(ClearingChannelRecordHis record);

    List<ClearingChannelRecordHis> selectByExample(ClearingChannelRecordHisExample example);

    ClearingChannelRecordHis selectByPrimaryKey(Long hisId);

    int updateByExampleSelective(@Param("record") ClearingChannelRecordHis record, @Param("example") ClearingChannelRecordHisExample example);

    int updateByExample(@Param("record") ClearingChannelRecordHis record, @Param("example") ClearingChannelRecordHisExample example);

    int updateByPrimaryKeySelective(ClearingChannelRecordHis record);

    int updateByPrimaryKey(ClearingChannelRecordHis record);
    
    int insertMap(Map<String,Object> map);
}