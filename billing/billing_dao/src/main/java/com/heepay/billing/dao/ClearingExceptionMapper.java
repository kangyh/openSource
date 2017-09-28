package com.heepay.billing.dao;

import com.heepay.billing.entity.ClearingException;
import com.heepay.billing.entity.ClearingExceptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClearingExceptionMapper {
    int countByExample(ClearingExceptionExample example);

    int deleteByExample(ClearingExceptionExample example);

    int deleteByPrimaryKey(Long clearId);

    int insert(ClearingException record);

    int insertSelective(ClearingException record);

    List<ClearingException> selectByExample(ClearingExceptionExample example);

    ClearingException selectByPrimaryKey(Long clearId);

    int updateByExampleSelective(@Param("record") ClearingException record, @Param("example") ClearingExceptionExample example);

    int updateByExample(@Param("record") ClearingException record, @Param("example") ClearingExceptionExample example);

    int updateByPrimaryKeySelective(ClearingException record);

    int updateByPrimaryKey(ClearingException record);
}