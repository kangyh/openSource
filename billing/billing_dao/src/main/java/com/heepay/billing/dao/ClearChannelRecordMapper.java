package com.heepay.billing.dao;

import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearChannelRecordExample;
import com.heepay.billing.entity.SettleChannelRecord;
import com.heepay.billing.entity.SettleChannelRecordVo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ClearChannelRecordMapper {
    int countByExample(ClearChannelRecordExample example);

    int deleteByExample(ClearChannelRecordExample example);

    int deleteByPrimaryKey(Long clearingId);

    int insert(ClearChannelRecord record);

    int insertSelective(ClearChannelRecord record);

    List<ClearChannelRecord> selectByExample(ClearChannelRecordExample example);

    ClearChannelRecord selectByPrimaryKey(Long clearingId);

    int updateByExampleSelective(@Param("record") ClearChannelRecord record, @Param("example") ClearChannelRecordExample example);

    int updateByExample(@Param("record") ClearChannelRecord record, @Param("example") ClearChannelRecordExample example);

    int updateByPrimaryKeySelective(ClearChannelRecord record);

    int updateByPrimaryKey(ClearChannelRecord record);

    /**
     * 查询清算流水数据，生成批次
     * @return
     */
	List<ClearChannelRecord> queryClearChannelRecord();

	/**
	 * 查询结算相关数据
	 * @return
	 */
	List<SettleChannelRecordVo> queryClearChannelRecordVo();

	/**
	 * 对账完成，修改对账状态
	 */
	void updateClearChannelRecordStatus();

	/**
	 * 查询清算明细，支付单号集合
	 * @param settleChannelRecord
	 * @return
	 */
	List<ClearChannelRecord> queryClearChannelRecords(SettleChannelRecord settleChannelRecord, String transType);
	
	/**
	 *  根据支付单号将对应的批次号设置到清算表中
	 * @param settleChannelRecord
	 * @param transType
	 * @param settleStatus
	 */
	void updateSettleNoToClearChannelRecord(SettleChannelRecord settleChannelRecord, String transType, String settleStatus);

	/**
	 * 日终对账开始，修改settle_status为结算中
	 */
	void UpdateChannelStatus();
	
	/**
	 * 日终对账开始，修改对账状态
	 */
	void UpdateChannelSettleStatus();

	// 核账完成，修改清算表中完成时间和结算状态
	void updateClearChannelRecord(String settleBath);

	//根据支付单号查询通道侧清算流水记录
	public ClearChannelRecord queryClearChannelRecordByPaymentId(String paymentId);
	
	//根据支付单号查询通道侧清算记录中为未对账的记录
	public ClearChannelRecord queryClearChannelRecordByPaymentIdAndCheckStatus(ClearChannelRecord record);
	
	/**
	 * 查询告警记录
	 * @param clearChannelRecord
	 * @return
	 */
	List<ClearChannelRecord> queryClearMerchantRecords(ClearChannelRecord clearChannelRecord);
	
	/**
	 * @方法说明：根据通道侧清算记录表ID（clearing_id）   更新通道侧清算记录表（clearing_channel_record）：  对账状态、通道对账日期、对账标记、对账批次号,对账次数
	 * @时间：2016年9月27日
	 * @创建人：wangdong
	 */
	void updateClearChannelRecordByPrimaryKey(ClearChannelRecord clearChannelRecord);


	/**
	 * 
	 * @方法说明：根据交易订单号（支付单号）  查询  结算状态（N：未结算，D：结算中 Y：已结算）'
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	ClearChannelRecord queryClearChannelRecordByTranNo(String tranNo);

	/**
	 * 查询三天之前未对账的通道侧数据
	 * @param checkStatus
	 * @return
	 */
	List<ClearChannelRecord> queryPayTime(String checkStatus,String channelCode,String channelType);
	
	void updateClearChannelRecordByTransNo(String tranNo);

	/**
	 * 
	 * @方法说明：判断是否重复发送的数据
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	int getBooleanExist(String paymentId);

	List<ClearChannelRecord> queryChannelRecord(String dateTimePlan);
	
	/**
	 * 
	 * @方法说明：查询字段为null的
	 * @author xuangang
	 * @return
	 * @时间：2017年3月29日上午11:34:49
	 */
	List<Map<String, String>> queryValIsNull();
	
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param map
	 * @时间：2017年3月29日下午1:51:07
	 */
	void updateOldRecord(Map map);
	
	int queryChannelByPaymentIdTransNo(Map map);
	

	void updateNumByTransNO(String transNo);
	/**
	 * 手动对账未对账的数据
	 * @param tranNo
	 */
	List<ClearChannelRecord> queryHandleBill(String checkStatus,String channelCode,String channelType,String checkNum);
	/**
	 * 根据对账批次查找结算批次
	 */
	List<String> querySettleBatch(String checkBatch);

	/**
	 * 根据结算批次号分页查询结算明细数据
	 */
	List<ClearChannelRecord> querySettleBathMsgBySettleBath(Map<String, Object> paraMap);
}