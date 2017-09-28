package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsMerchantMsg;
import com.heepay.tpds.entity.TpdsMerchantMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsMerchantMsgMapper {
    int countByExample(TpdsMerchantMsgExample example);

    int deleteByExample(TpdsMerchantMsgExample example);

    int insert(TpdsMerchantMsg record);

    int insertSelective(TpdsMerchantMsg record);

    List<TpdsMerchantMsg> selectByExample(TpdsMerchantMsgExample example);

    int updateByExampleSelective(@Param("record") TpdsMerchantMsg record, @Param("example") TpdsMerchantMsgExample example);

    int updateByExample(@Param("record") TpdsMerchantMsg record, @Param("example") TpdsMerchantMsgExample example);

    int updateByPrimaryKeySelective(TpdsMerchantMsg record);

    int updateByPrimaryKey(TpdsMerchantMsg record);
    
    int updateRespCodeBySystemNo(TpdsMerchantMsg record);

    int updateRespCodeByBusinessSeqNo(TpdsMerchantMsg updateTpdsMerchantMsg);
}