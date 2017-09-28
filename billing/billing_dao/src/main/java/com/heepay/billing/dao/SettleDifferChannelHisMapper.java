package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleDifferChannelHis;
import com.heepay.billing.entity.SettleDifferChannelHisExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SettleDifferChannelHisMapper {
    int countByExample(SettleDifferChannelHisExample example);

    int deleteByExample(SettleDifferChannelHisExample example);

    int deleteByPrimaryKey(Long hisId);

    int insert(SettleDifferChannelHis record);

    int insertSelective(SettleDifferChannelHis record);

    List<SettleDifferChannelHis> selectByExample(SettleDifferChannelHisExample example);

    SettleDifferChannelHis selectByPrimaryKey(Long hisId);

    int updateByExampleSelective(@Param("record") SettleDifferChannelHis record, @Param("example") SettleDifferChannelHisExample example);

    int updateByExample(@Param("record") SettleDifferChannelHis record, @Param("example") SettleDifferChannelHisExample example);

    int updateByPrimaryKeySelective(SettleDifferChannelHis record);

    int updateByPrimaryKey(SettleDifferChannelHis record);

	int insertMap(Map<String, Object> map);
}