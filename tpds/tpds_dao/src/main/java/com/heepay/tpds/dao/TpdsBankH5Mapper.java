package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsBankH5;
import com.heepay.tpds.entity.TpdsBankH5Example;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TpdsBankH5Mapper {
    int countByExample(TpdsBankH5Example example);

    int deleteByExample(TpdsBankH5Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(TpdsBankH5 record);

    int insertSelective(TpdsBankH5 record);

    List<TpdsBankH5> selectByExample(TpdsBankH5Example example);

    TpdsBankH5 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TpdsBankH5 record, @Param("example") TpdsBankH5Example example);

    int updateByExample(@Param("record") TpdsBankH5 record, @Param("example") TpdsBankH5Example example);

    int updateByPrimaryKeySelective(TpdsBankH5 record);

    int updateByPrimaryKey(TpdsBankH5 record);
    
    void updateBybusinessSeqNo(Map map);
}