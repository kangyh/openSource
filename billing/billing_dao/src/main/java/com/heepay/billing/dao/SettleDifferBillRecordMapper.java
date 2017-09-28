package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleDifferBillRecord;
import com.heepay.billing.entity.SettleDifferBillRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleDifferBillRecordMapper {
    int countByExample(SettleDifferBillRecordExample example);

    int deleteByExample(SettleDifferBillRecordExample example);

    int deleteByPrimaryKey(Long billDifferId);

    int insert(SettleDifferBillRecord record);

    int insertSelective(SettleDifferBillRecord record);

    List<SettleDifferBillRecord> selectByExample(SettleDifferBillRecordExample example);

    SettleDifferBillRecord selectByPrimaryKey(Long billDifferId);

    int updateByExampleSelective(@Param("record") SettleDifferBillRecord record, @Param("example") SettleDifferBillRecordExample example);

    int updateByExample(@Param("record") SettleDifferBillRecord record, @Param("example") SettleDifferBillRecordExample example);

    int updateByPrimaryKeySelective(SettleDifferBillRecord record);

    int updateByPrimaryKey(SettleDifferBillRecord record);

	SettleDifferBillRecord selectByPaymentId(String paymentId);
}