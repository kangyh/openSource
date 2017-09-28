package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsObjectTransDetail;
import com.heepay.tpds.entity.TpdsObjectTransDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsObjectTransDetailMapper {
    int countByExample(TpdsObjectTransDetailExample example);

    int deleteByExample(TpdsObjectTransDetailExample example);

    int deleteByPrimaryKey(Long accId);

    int insert(TpdsObjectTransDetail record);

    int insertSelective(TpdsObjectTransDetail record);

    List<TpdsObjectTransDetail> selectByExample(TpdsObjectTransDetailExample example);

    TpdsObjectTransDetail selectByPrimaryKey(Long accId);

    int updateByExampleSelective(@Param("record") TpdsObjectTransDetail record, @Param("example") TpdsObjectTransDetailExample example);

    int updateByExample(@Param("record") TpdsObjectTransDetail record, @Param("example") TpdsObjectTransDetailExample example);

    int updateByPrimaryKeySelective(TpdsObjectTransDetail record);

    int updateByPrimaryKey(TpdsObjectTransDetail record);
}