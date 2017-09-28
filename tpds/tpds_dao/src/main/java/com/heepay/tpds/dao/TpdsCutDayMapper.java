package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsCutDay;

public interface TpdsCutDayMapper {
   

    TpdsCutDay selectByCutType(String CutType);
    int insert(TpdsCutDay entity);
    int update(TpdsCutDay entity);
    TpdsCutDay selectCutTypeByCutType(TpdsCutDay tpdsCutDay);
    TpdsCutDay getCutTimeByBusiNo(String merchantId, String status);
}