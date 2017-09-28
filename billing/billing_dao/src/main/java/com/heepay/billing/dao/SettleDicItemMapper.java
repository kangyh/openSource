package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleDicItem;
import com.heepay.billing.entity.SettleDicItemExample;
import com.heepay.billing.entity.SettleDicType;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SettleDicItemMapper {
    int countByExample(SettleDicItemExample example);

    int deleteByExample(SettleDicItemExample example);

    int deleteByPrimaryKey(Long itemId);

    int insert(SettleDicItem record);

    int insertSelective(SettleDicItem record);

    List<SettleDicItem> selectByExample(SettleDicItemExample example);

    SettleDicItem selectByPrimaryKey(Long itemId);

    int updateByExampleSelective(@Param("record") SettleDicItem record, @Param("example") SettleDicItemExample example);

    int updateByExample(@Param("record") SettleDicItem record, @Param("example") SettleDicItemExample example);

    int updateByPrimaryKeySelective(SettleDicItem record);

    int updateByPrimaryKey(SettleDicItem record);
    //
    SettleDicItem selectByPrimaryKey(SettleDicItem record);
    
    SettleDicItem queryItemByTypeCode(SettleDicType record);
    
    List<SettleDicItem> queryItem(SettleDicType record);
}