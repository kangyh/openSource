package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleDicType;
import com.heepay.billing.entity.SettleDicTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleDicTypeMapper {
    int countByExample(SettleDicTypeExample example);

    int deleteByExample(SettleDicTypeExample example);

    int deleteByPrimaryKey(Integer typeId);

    int insert(SettleDicType record);

    int insertSelective(SettleDicType record);

    List<SettleDicType> selectByExample(SettleDicTypeExample example);

    SettleDicType selectByPrimaryKey(Integer typeId);

    int updateByExampleSelective(@Param("record") SettleDicType record, @Param("example") SettleDicTypeExample example);

    int updateByExample(@Param("record") SettleDicType record, @Param("example") SettleDicTypeExample example);

    int updateByPrimaryKeySelective(SettleDicType record);

    int updateByPrimaryKey(SettleDicType record);
    //查询结账区间右侧时间L
    SettleDicType selectByPrimaryKey(SettleDicType record);
    
    
}