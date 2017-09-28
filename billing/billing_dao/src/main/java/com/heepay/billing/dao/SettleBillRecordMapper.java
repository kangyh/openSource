package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleBillRecord;
import com.heepay.billing.entity.SettleBillRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleBillRecordMapper {
    int countByExample(SettleBillRecordExample example);

    int deleteByExample(SettleBillRecordExample example);

    int deleteByPrimaryKey(Long billDetailId);

    int insert(SettleBillRecord record);

    int insertSelective(SettleBillRecord record);

    List<SettleBillRecord> selectByExample(SettleBillRecordExample example);

    SettleBillRecord selectByPrimaryKey(Long billDetailId);

    int updateByExampleSelective(@Param("record") SettleBillRecord record, @Param("example") SettleBillRecordExample example);

    int updateByExample(@Param("record") SettleBillRecord record, @Param("example") SettleBillRecordExample example);

    int updateByPrimaryKeySelective(SettleBillRecord record);

    int updateByPrimaryKey(SettleBillRecord record);
	
	/**
     * @方法说明：更新账单明细表（settle_bill_record）：账单状态  变为已处理
     * @时间：2016年9月25日
     * @创建人：wangdong
     */
    int updateSettleBillRecordByPrimaryKey(SettleBillRecord record);
	
	/**
     * @方法说明：根据对账批次查询对账明细
	 * @author xuangang
	 * @param
	 */
    List<SettleBillRecord> queryBillRecordDetailByBatchNo(SettleBillRecord record);

	SettleBillRecord selectByPaymentId(String paymentId);

	SettleBillRecord selectByChannelNo(String channleNo);
	
}