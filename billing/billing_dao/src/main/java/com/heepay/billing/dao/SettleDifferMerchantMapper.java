package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleDifferMerchant;
import com.heepay.billing.entity.SettleDifferMerchantExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SettleDifferMerchantMapper {
    int countByExample(SettleDifferMerchantExample example);

    int deleteByExample(SettleDifferMerchantExample example);

    int deleteByPrimaryKey(Long differMerbillId);

    int insert(SettleDifferMerchant record);

    int insertSelective(SettleDifferMerchant record);

    List<SettleDifferMerchant> selectByExample(SettleDifferMerchantExample example);

    SettleDifferMerchant selectByPrimaryKey(Long differMerbillId);

    int updateByExampleSelective(@Param("record") SettleDifferMerchant record, @Param("example") SettleDifferMerchantExample example);

    int updateByExample(@Param("record") SettleDifferMerchant record, @Param("example") SettleDifferMerchantExample example);

    int updateByPrimaryKeySelective(SettleDifferMerchant record);

    int updateByPrimaryKey(SettleDifferMerchant record);
	
	/**
     * 
     * @方法说明：汇总差错记录完成，更新记账状态
     * @author chenyanming
     * @param errorBath
     * @时间：2016年11月3日下午3:45:28
     */
	void updateErrorStatusByErrorBath(String errorBath);
	
	/**
	 * 
	 * @方法说明：查询出所有审核通过的批次记录
	 * @author chenyanming
	 * @param checkStatus
	 * @return
	 * @时间：2016年11月15日下午4:07:56
	 */
	List<SettleDifferMerchant> querySuccessSettleDifferMerchantData(String checkStatus, String errorStatus);
	
	/**
	 * 更改处理日期和记账状态
	 * @param errorBath
	 */
	void updateErrorStatusAndDateByErrorBath(String errorBath);
	
	/**
	 * 
	 * @方法说明：通过交易订单号查询商户差异记录
	 * @author xuangang
	 * @param transNo
	 * @return
	 * @时间：2017年5月26日下午3:01:51
	 */
	public SettleDifferMerchant queryDifferMerchantByTransNo(String transNo);
}