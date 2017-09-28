package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleWarnRecord;
import com.heepay.billing.entity.SettleWarnRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleWarnRecordMapper {
    int countByExample(SettleWarnRecordExample example);

    int deleteByExample(SettleWarnRecordExample example);

    int deleteByPrimaryKey(Long warnId);

    int insert(SettleWarnRecord record);

    int insertSelective(SettleWarnRecord record);

    List<SettleWarnRecord> selectByExample(SettleWarnRecordExample example);

    SettleWarnRecord selectByPrimaryKey(Long warnId);

    int updateByExampleSelective(@Param("record") SettleWarnRecord record, @Param("example") SettleWarnRecordExample example);

    int updateByExample(@Param("record") SettleWarnRecord record, @Param("example") SettleWarnRecordExample example);

    int updateByPrimaryKeySelective(SettleWarnRecord record);

    int updateByPrimaryKey(SettleWarnRecord record);

	void updateSettleWarnRecordByWarnContext(String warnContext);

}