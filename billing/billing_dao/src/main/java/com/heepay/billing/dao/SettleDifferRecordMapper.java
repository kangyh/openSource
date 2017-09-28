package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.billing.entity.SettleDifferRecordExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SettleDifferRecordMapper {
    int countByExample(SettleDifferRecordExample example);

    int deleteByExample(SettleDifferRecordExample example);

    int deleteByPrimaryKey(Long differId);

    int insert(SettleDifferRecord record);
    int updateSettleDifferRecordByDifferId(SettleDifferRecord sdr) ;
	/**
     * @方法说明：插入差异记录表
     * @时间：2016年9月25日
     * @创建人：wangdong
     */
    int insertSettleDifferRecord(SettleDifferRecord record);

    int insertSelective(SettleDifferRecord record);

    List<SettleDifferRecord> selectByExample(SettleDifferRecordExample example);

    SettleDifferRecord selectByPrimaryKey(Long differId);

    int updateByExampleSelective(@Param("record") SettleDifferRecord record, @Param("example") SettleDifferRecordExample example);

    int updateByExample(@Param("record") SettleDifferRecord record, @Param("example") SettleDifferRecordExample example);

    int updateByPrimaryKeySelective(SettleDifferRecord record);

    int updateByPrimaryKey(SettleDifferRecord record);
	
	List<SettleDifferRecord> selectByDifferType(String differType);

	int updateSettleDifferRecord(SettleDifferRecord sdr);
	
	void updateSettleDifferRecordByPaymentId(String paymentId, String handleResult);

	/**
	 * 
	 * @方法说明：查询所有未记账的差异记录
	 * @author chenyanming
	 * @param value
	 * @return
	 * @时间：2016年10月28日上午11:27:03
	 */
	List<SettleDifferRecord> querySettleDifferRecordData(String value);

	/**
	 * 
	 * @方法说明：汇总差错记录完成，更新记账状态
	 * @author chenyanming
	 * @param differId
	 * @时间：2016年11月3日下午3:05:46
	 */
	void updateIsBillStatus(String paymentId);

	/**
	 * 
	 * @方法说明：根据交易单号查询差错记录
	 * @author chenyanming
	 * @param transNo
	 * @return
	 * @时间：2016年11月15日下午5:22:08
	 */
	SettleDifferRecord querySettleDifferRecordByTransNo(String transNo);
	
	
	/**
	 * 根据差异类型和处理结果查找差异记录
	 * @param differType
	 * @param handleResult
	 * @return 
	 * wangjie
	 */
	List<SettleDifferRecord> selectByDifferTypeAndHandleResult(String differType, String handleResult);
	
	
	/**
	 * 根据结算批次更改挂账状态Date date
	 * wangjie
	 */
	void updateStatusBySettleBath(String settleBath, String handleResult);
	
	/**
	 * 其他差异插入差异表
	 * wangjie
	 */
	 int insertOtherSettleDifferRecord(SettleDifferRecord record);

	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年6月26日下午4:46:14
	 */
	public int countDifferRecordNum(Map map);
	
	/**
	 * 
	 * @方法说明：通过支付单号统计差异数据总数
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年6月28日下午3:37:43
	 */
	public int countDifferRecordNumByPaymentId(Map map);
}