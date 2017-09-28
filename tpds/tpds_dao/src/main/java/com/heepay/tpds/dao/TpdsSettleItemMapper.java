package com.heepay.tpds.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.heepay.tpds.entity.TpdsSettleItem;
import com.heepay.tpds.entity.TpdsSettleItemExample;

public interface TpdsSettleItemMapper {
    int countByExample(TpdsSettleItemExample example);

    int deleteByExample(TpdsSettleItemExample example);

    int deleteByPrimaryKey(Long clearingId);

    int insert(TpdsSettleItem record);

    int insertSelective(TpdsSettleItem record);

    List<TpdsSettleItem> selectByExample(TpdsSettleItemExample example);

    TpdsSettleItem selectByPrimaryKey(Long clearingId);

    int updateByExampleSelective(@Param("record") TpdsSettleItem record, @Param("example") TpdsSettleItemExample example);

    int updateByExample(@Param("record") TpdsSettleItem record, @Param("example") TpdsSettleItemExample example);

    int updateByPrimaryKeySelective(TpdsSettleItem record);

    int updateByPrimaryKey(TpdsSettleItem record);
    
    List<TpdsSettleItem> getTpdsSettleItemBath(TpdsSettleItem tpdsSettleItem);
    
    void updateByTransNo(String transNo);
    
    
    
}