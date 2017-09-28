package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsCustomer;
import com.heepay.tpds.entity.TpdsCustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsCustomerMapper {
    int countByExample(TpdsCustomerExample example);

    int deleteByExample(TpdsCustomerExample example);

    int deleteByPrimaryKey(Integer customerId);

    int insert(TpdsCustomer record);

    int insertSelective(TpdsCustomer record);

    List<TpdsCustomer> selectByExample(TpdsCustomerExample example);

    TpdsCustomer selectByPrimaryKey(Integer customerId);

    int updateByExampleSelective(@Param("record") TpdsCustomer record, @Param("example") TpdsCustomerExample example);

    int updateByExample(@Param("record") TpdsCustomer record, @Param("example") TpdsCustomerExample example);

    int updateByPrimaryKeySelective(TpdsCustomer record);

    int updateByPrimaryKey(TpdsCustomer record);
}