package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleChannelRecordHis;
import com.heepay.billing.entity.SettleChannelRecordHisExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SettleChannelRecordHisMapper {
    int countByExample(SettleChannelRecordHisExample example);

    int deleteByExample(SettleChannelRecordHisExample example);

    int deleteByPrimaryKey(Long hisId);

    int insert(SettleChannelRecordHis record);

    int insertSelective(SettleChannelRecordHis record);

    List<SettleChannelRecordHis> selectByExample(SettleChannelRecordHisExample example);

    SettleChannelRecordHis selectByPrimaryKey(Long hisId);

    int updateByExampleSelective(@Param("record") SettleChannelRecordHis record, @Param("example") SettleChannelRecordHisExample example);

    int updateByExample(@Param("record") SettleChannelRecordHis record, @Param("example") SettleChannelRecordHisExample example);

    int updateByPrimaryKeySelective(SettleChannelRecordHis record);

    int updateByPrimaryKey(SettleChannelRecordHis record);

	int insertMap(Map<String, Object> map);
}