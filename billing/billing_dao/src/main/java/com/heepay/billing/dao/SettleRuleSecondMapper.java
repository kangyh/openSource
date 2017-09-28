package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleRuleSecond;
import com.heepay.billing.entity.SettleRuleSecondExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleRuleSecondMapper {
    int countByExample(SettleRuleSecondExample example);

    int deleteByExample(SettleRuleSecondExample example);

    int deleteByPrimaryKey(Long ruleId);

    int insert(SettleRuleSecond record);

    int insertSelective(SettleRuleSecond record);

    List<SettleRuleSecond> selectByExample(SettleRuleSecondExample example);

    SettleRuleSecond selectByPrimaryKey(Long ruleId);

    int updateByExampleSelective(@Param("record") SettleRuleSecond record, @Param("example") SettleRuleSecondExample example);

    int updateByExample(@Param("record") SettleRuleSecond record, @Param("example") SettleRuleSecondExample example);

    int updateByPrimaryKeySelective(SettleRuleSecond record);

    int updateByPrimaryKey(SettleRuleSecond record);

    /**
     * 
     * @方法说明：获取指定二代规则表达式
     * @author chenyanming
     * @param channelCode
     * @param channelType
     * @param billType
     * @param settleWay
     * @return
     * @时间：2017年5月9日下午1:55:04
     */
	SettleRuleSecond getSetlleRuleSecond(String channelCode, String channelType, String billType, String settleWay);
}