package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleChannelRecord;
import com.heepay.billing.entity.SettleChannelRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleChannelRecordMapper {
    int countByExample(SettleChannelRecordExample example);

    int deleteByExample(SettleChannelRecordExample example);

    int deleteByPrimaryKey(Long settleId);

    int insert(SettleChannelRecord record);

    int insertSelective(SettleChannelRecord record);

    List<SettleChannelRecord> selectByExample(SettleChannelRecordExample example);

    SettleChannelRecord selectByPrimaryKey(Long settleId);

    int updateByExampleSelective(@Param("record") SettleChannelRecord record, @Param("example") SettleChannelRecordExample example);

    int updateByExample(@Param("record") SettleChannelRecord record, @Param("example") SettleChannelRecordExample example);

    int updateByPrimaryKeySelective(SettleChannelRecord record);

    int updateByPrimaryKey(SettleChannelRecord record);

    /**
     *  核账完成，修改结算记录中结算状态为已结算（chen）
     */
	void updateSettleChannelRecordStatus(String settleBath);

	List<SettleChannelRecord> getSettleChannelRecordByStatus(String checkStatus,String settleStatus,String date);

	List<SettleChannelRecord> getSettleChannelRecordByCode(String channelCode, String channelType, String date,String settleStatus,String settleBath);

	
}