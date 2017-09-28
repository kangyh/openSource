package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsFileLog;
import com.heepay.tpds.entity.TpdsFileLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsFileLogMapper {
    int countByExample(TpdsFileLogExample example);

    int deleteByExample(TpdsFileLogExample example);

    int deleteByPrimaryKey(Integer logId);

    int insert(TpdsFileLog record);

    int insertSelective(TpdsFileLog record);

    List<TpdsFileLog> selectByExample(TpdsFileLogExample example);

    TpdsFileLog selectByPrimaryKey(Integer logId);

    int updateByExampleSelective(@Param("record") TpdsFileLog record, @Param("example") TpdsFileLogExample example);

    int updateByExample(@Param("record") TpdsFileLog record, @Param("example") TpdsFileLogExample example);

    int updateByPrimaryKeySelective(TpdsFileLog record);

    int updateByPrimaryKey(TpdsFileLog record);
    
    Integer selectMaxId();
}