package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsMerchantCer;
import com.heepay.tpds.entity.TpdsMerchantCerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsMerchantCerMapper {
    int insert(TpdsMerchantCer record);
    int update(TpdsMerchantCer record);
    List<TpdsMerchantCer> getlist();
    int deleteByMerchantNo(String merchantNo);
    TpdsMerchantCer selectTpdsMerchantCerByMerchantNo(String merchantNo);
}