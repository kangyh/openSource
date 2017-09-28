package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsSettleRecord;
import com.heepay.tpds.entity.TpdsSettleRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsSettleRecordMapper {
    int countByExample(TpdsSettleRecordExample example);

    int deleteByExample(TpdsSettleRecordExample example);

    int deleteByPrimaryKey(Long settleId);

    int insert(TpdsSettleRecord record);

    int insertSelective(TpdsSettleRecord record);

    List<TpdsSettleRecord> selectByExample(TpdsSettleRecordExample example);

    TpdsSettleRecord selectByPrimaryKey(Long settleId);

    int updateByExampleSelective(@Param("record") TpdsSettleRecord record, @Param("example") TpdsSettleRecordExample example);

    int updateByExample(@Param("record") TpdsSettleRecord record, @Param("example") TpdsSettleRecordExample example);

    int updateByPrimaryKeySelective(TpdsSettleRecord record);

    int updateByPrimaryKey(TpdsSettleRecord record);
    
    /**
     * 
     * @方法说明：根据结算单号查询
     * @author chenyanming
     * @param settleBath
     * @return
     * @时间：2017年3月15日下午2:41:53
     */
    TpdsSettleRecord queryBySettleBath(String settleBath);
}