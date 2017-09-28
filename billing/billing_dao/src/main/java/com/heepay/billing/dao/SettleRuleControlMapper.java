package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleRuleControl;
import com.heepay.billing.entity.SettleRuleControlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleRuleControlMapper {
    int countByExample(SettleRuleControlExample example);

    int deleteByExample(SettleRuleControlExample example);

    int deleteByPrimaryKey(Long ruleControlId);

    int insert(SettleRuleControl record);

    int insertSelective(SettleRuleControl record);

    List<SettleRuleControl> selectByExample(SettleRuleControlExample example);

    SettleRuleControl selectByPrimaryKey(Long ruleControlId);

    int updateByExampleSelective(@Param("record") SettleRuleControl record, @Param("example") SettleRuleControlExample example);

    int updateByExample(@Param("record") SettleRuleControl record, @Param("example") SettleRuleControlExample example);

    int updateByPrimaryKeySelective(SettleRuleControl record);

    int updateByPrimaryKey(SettleRuleControl record);
	
	/**
     * 
     * @方法说明：获取账单明细对应的规则表达式
     * @author chenyanming
     * @param channelCode
     * @param channelType
     * @return
     * @时间：2016年9月23日下午6:12:02
     */
	SettleRuleControl getSettleRuleControl(String channelCode, String channelType, String billType, String settleWay);
}