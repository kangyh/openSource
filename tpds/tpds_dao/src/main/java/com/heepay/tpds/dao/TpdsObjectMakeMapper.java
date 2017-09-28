package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsObjectMake;
import com.heepay.tpds.entity.TpdsObjectMakeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsObjectMakeMapper {
    int countByExample(TpdsObjectMakeExample example);

    int deleteByExample(TpdsObjectMakeExample example);

    int deleteByPrimaryKey(Long objId);

    int insert(TpdsObjectMake record);

    int insertSelective(TpdsObjectMake record);

    List<TpdsObjectMake> selectByExample(TpdsObjectMakeExample example);

    TpdsObjectMake selectByPrimaryKey(Long objId);

    int updateByExampleSelective(@Param("record") TpdsObjectMake record, @Param("example") TpdsObjectMakeExample example);

    int updateByExample(@Param("record") TpdsObjectMake record, @Param("example") TpdsObjectMakeExample example);

    int updateByPrimaryKeySelective(TpdsObjectMake record);

    int updateByPrimaryKey(TpdsObjectMake record);
}