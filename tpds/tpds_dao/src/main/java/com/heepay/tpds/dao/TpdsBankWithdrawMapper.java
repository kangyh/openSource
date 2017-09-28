package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsBankWithdraw;
import com.heepay.tpds.entity.TpdsBankWithdrawExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpdsBankWithdrawMapper {
    int countByExample(TpdsBankWithdrawExample example);

    int deleteByExample(TpdsBankWithdrawExample example);

    int deleteByPrimaryKey(Long cusId);

    int insert(TpdsBankWithdraw record);

    int insertSelective(TpdsBankWithdraw record);

    List<TpdsBankWithdraw> selectByExample(TpdsBankWithdrawExample example);

    TpdsBankWithdraw selectByPrimaryKey(Long cusId);

    int updateByExampleSelective(@Param("record") TpdsBankWithdraw record, @Param("example") TpdsBankWithdrawExample example);

    int updateByExample(@Param("record") TpdsBankWithdraw record, @Param("example") TpdsBankWithdrawExample example);

    int updateByPrimaryKeySelective(TpdsBankWithdraw record);

    int updateByPrimaryKey(TpdsBankWithdraw record);

    TpdsBankWithdraw selectByBusinessOrderNo(String businessOrderNo);
}