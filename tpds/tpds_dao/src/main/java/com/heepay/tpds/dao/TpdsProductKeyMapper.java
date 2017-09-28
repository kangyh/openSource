package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsProductKey;
import com.heepay.tpds.entity.TpdsProductKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsProductKeyMapper {
   

    int insert(TpdsProductKey record);
    int updateByPrimaryKey(TpdsProductKey record);
    List<TpdsProductKey> getList();
    TpdsProductKey selectTpdsProductKeyByMerchantNo(String merchantNo, String productCode);
}