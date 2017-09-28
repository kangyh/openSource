package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleRegexControl;
import com.heepay.billing.entity.SettleRegexControlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleRegexControlMapper {
    int countByExample(SettleRegexControlExample example);

    int deleteByExample(SettleRegexControlExample example);

    int deleteByPrimaryKey(Long regexControlId);

    int insert(SettleRegexControl record);

    int insertSelective(SettleRegexControl record);

    List<SettleRegexControl> selectByExample(SettleRegexControlExample example);

    SettleRegexControl selectByPrimaryKey(Long regexControlId);

    int updateByExampleSelective(@Param("record") SettleRegexControl record, @Param("example") SettleRegexControlExample example);

    int updateByExample(@Param("record") SettleRegexControl record, @Param("example") SettleRegexControlExample example);

    int updateByPrimaryKeySelective(SettleRegexControl record);

    int updateByPrimaryKey(SettleRegexControl record);

    /**
     * 
     * @方法说明：根据ruleId和ruleKey获取正则表达式
     * @author chenyanming
     * @param ruleType 
     * @param paymentId
     * @param ruleControlId
     * @return
     * @时间：2017年1月17日下午6:38:51
     */
	List<SettleRegexControl> queryRegex(Byte ruleKey, Long ruleId, String ruleType);
}