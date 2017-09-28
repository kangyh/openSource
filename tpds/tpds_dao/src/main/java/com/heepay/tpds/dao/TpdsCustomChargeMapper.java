package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsCustomCharge;
import com.heepay.tpds.entity.TpdsCustomChargeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsCustomChargeMapper {
    int countByExample(TpdsCustomChargeExample example);

    int deleteByExample(TpdsCustomChargeExample example);

    int deleteByPrimaryKey(Long cusId);

    int insert(TpdsCustomCharge record);

    int insertSelective(TpdsCustomCharge record);

    List<TpdsCustomCharge> selectByExample(TpdsCustomChargeExample example);

    TpdsCustomCharge selectByPrimaryKey(Long cusId);

    int updateByExampleSelective(@Param("record") TpdsCustomCharge record, @Param("example") TpdsCustomChargeExample example);

    int updateByExample(@Param("record") TpdsCustomCharge record, @Param("example") TpdsCustomChargeExample example);

    int updateByPrimaryKeySelective(TpdsCustomCharge record);

    int updateByPrimaryKey(TpdsCustomCharge record);

	TpdsCustomCharge selectByBusinessOrderNo(String transNo);
	
	List<TpdsCustomCharge> selectByBusinessSeqNo(String transNo);
}