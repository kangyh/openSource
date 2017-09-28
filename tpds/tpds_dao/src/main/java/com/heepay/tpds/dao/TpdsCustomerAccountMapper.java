package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsCustomerAccount;
import com.heepay.tpds.entity.TpdsCustomerAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsCustomerAccountMapper {
    int countByExample(TpdsCustomerAccountExample example);

    int deleteByExample(TpdsCustomerAccountExample example);

    int insert(TpdsCustomerAccount record);

    int insertSelective(TpdsCustomerAccount record);

    List<TpdsCustomerAccount> selectByExample(TpdsCustomerAccountExample example);

    int updateByExampleSelective(@Param("record") TpdsCustomerAccount record, @Param("example") TpdsCustomerAccountExample example);

    int updateByExample(@Param("record") TpdsCustomerAccount record, @Param("example") TpdsCustomerAccountExample example);

    int updateByPrimaryKeySelective(TpdsCustomerAccount record);

    int updateByPrimaryKey(TpdsCustomerAccount record);

	List<TpdsCustomerAccount> selectAccountRecordByStatus(String status);
	
	TpdsCustomerAccount selectCustomerNoByMerchantNo(TpdsCustomerAccount record);
	
	int updateEntityByAcc(TpdsCustomerAccount record);

}