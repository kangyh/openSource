package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsMerchant;
import com.heepay.tpds.entity.TpdsMerchantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsMerchantMapper {
    int countByExample(TpdsMerchantExample example);

    int deleteByExample(TpdsMerchantExample example);

    int deleteByPrimaryKey(Integer merchantId);

    int insert(TpdsMerchant record);

    int insertSelective(TpdsMerchant record);

    List<TpdsMerchant> selectByExampleWithBLOBs(TpdsMerchantExample example);

    List<TpdsMerchant> selectByExample(TpdsMerchantExample example);

    TpdsMerchant selectByPrimaryKey(Integer merchantId);

    int updateByExampleSelective(@Param("record") TpdsMerchant record, @Param("example") TpdsMerchantExample example);

    int updateByExampleWithBLOBs(@Param("record") TpdsMerchant record, @Param("example") TpdsMerchantExample example);

    int updateByExample(@Param("record") TpdsMerchant record, @Param("example") TpdsMerchantExample example);

    int updateByPrimaryKeySelective(TpdsMerchant record);

    int updateByPrimaryKeyWithBLOBs(TpdsMerchant record);

    int updateByPrimaryKey(TpdsMerchant record);
}