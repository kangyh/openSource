package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleDifferChannel;
import com.heepay.billing.entity.SettleDifferChannelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SettleDifferChannelMapper {
    int countByExample(SettleDifferChannelExample example);

    int deleteByExample(SettleDifferChannelExample example);

    int deleteByPrimaryKey(Long differChanbillId);

    int insert(SettleDifferChannel record);

    int insertSelective(SettleDifferChannel record);

    List<SettleDifferChannel> selectByExample(SettleDifferChannelExample example);

    SettleDifferChannel selectByPrimaryKey(Long differChanbillId);

    int updateByExampleSelective(@Param("record") SettleDifferChannel record, @Param("example") SettleDifferChannelExample example);

    int updateByExample(@Param("record") SettleDifferChannel record, @Param("example") SettleDifferChannelExample example);

    int updateByPrimaryKeySelective(SettleDifferChannel record);

    int updateByPrimaryKey(SettleDifferChannel record);

    /**
     * 
     * @方法说明：撤账或补账完成，更新批次记录状态为已处理
     * @author chenyanming
     * @param errorBath
     * @时间：2016年11月3日下午3:27:33
     */
	void updateErrorStatusByErrorBath(String errorBath);

	/**
	 * 
	 * @方法说明：根据支付单号查询通道侧差错批次数据
	 * @author chenyanming
	 * @param paymentId
	 * @return
	 * @时间：2016年11月15日下午6:07:00
	 */
	SettleDifferChannel querySettleDifferChannelByPaymentId(String paymentId);
	
	/**
	 * 
	 * @param value
	 * @param value2
	 * @return
	 */
	
	List<SettleDifferChannel> querySuccessSettleDifferChannelData(String checkStatus, String errorStatus);

	/**
     * 
     * @方法说明：撤账或补账完成，更新批次记录状态为已处理
     * @author chenyanming
     * @param errorBath
     * @时间：2016年11月3日下午3:27:33
     */
	void updateErrorStatusAndCheckStatusByErrorBath(String errorBath);
	
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param transNo
	 * @return
	 * @时间：2017年5月26日下午3:32:58
	 */
	public SettleDifferChannel querySettleDifferChannelByTransNo(String transNo);
}