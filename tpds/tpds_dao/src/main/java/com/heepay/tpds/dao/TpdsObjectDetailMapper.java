package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsObjectDetail;
import com.heepay.tpds.entity.TpdsObjectDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsObjectDetailMapper {
    int countByExample(TpdsObjectDetailExample example);

    int deleteByExample(TpdsObjectDetailExample example);

    int deleteByPrimaryKey(Long retId);

    int insert(TpdsObjectDetail record);

    int insertSelective(TpdsObjectDetail record);

    List<TpdsObjectDetail> selectByExample(TpdsObjectDetailExample example);

    TpdsObjectDetail selectByPrimaryKey(Long retId);

    int updateByExampleSelective(@Param("record") TpdsObjectDetail record, @Param("example") TpdsObjectDetailExample example);

    int updateByExample(@Param("record") TpdsObjectDetail record, @Param("example") TpdsObjectDetailExample example);

    int updateByPrimaryKeySelective(TpdsObjectDetail record);

    int updateByPrimaryKey(TpdsObjectDetail record);
}