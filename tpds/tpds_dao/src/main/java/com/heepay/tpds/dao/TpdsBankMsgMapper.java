package com.heepay.tpds.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.heepay.tpds.entity.TpdsBankMsg;
import com.heepay.tpds.entity.TpdsBankMsgExample;

public interface TpdsBankMsgMapper {
    
	int countByExample(TpdsBankMsgExample example);
	
    int deleteByExample(TpdsBankMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TpdsBankMsg record);

    int insertSelective(TpdsBankMsg record);

    List<TpdsBankMsg> selectByExample(TpdsBankMsgExample example);

    TpdsBankMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TpdsBankMsg record, @Param("example") TpdsBankMsgExample example);

    int updateByExample(@Param("record") TpdsBankMsg record, @Param("example") TpdsBankMsgExample example);

    int updateByPrimaryKeySelective(TpdsBankMsg record);

    int updateByPrimaryKey(TpdsBankMsg record);
}