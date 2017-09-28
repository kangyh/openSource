package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsBankCer;
import com.heepay.tpds.entity.TpdsBankCerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsBankCerMapper {
    int insert(TpdsBankCer record);
    int update(TpdsBankCer record);
    int deleteByBankNo(String bankNo);
    List<TpdsBankCer> getlist();
}