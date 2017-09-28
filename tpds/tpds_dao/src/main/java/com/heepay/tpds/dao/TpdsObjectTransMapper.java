package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsObjectTrans;
import com.heepay.tpds.entity.TpdsObjectTransExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsObjectTransMapper {
    int countByExample(TpdsObjectTransExample example);

    int deleteByExample(TpdsObjectTransExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TpdsObjectTrans record);

    int insertSelective(TpdsObjectTrans record);

    List<TpdsObjectTrans> selectByExample(TpdsObjectTransExample example);

    TpdsObjectTrans selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TpdsObjectTrans record, @Param("example") TpdsObjectTransExample example);

    int updateByExample(@Param("record") TpdsObjectTrans record, @Param("example") TpdsObjectTransExample example);

    int updateByPrimaryKeySelective(TpdsObjectTrans record);

    int updateByPrimaryKey(TpdsObjectTrans record);
}