package com.heepay.risk.dao;

import com.heepay.risk.entity.SettleIncomeInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettleIncomeInfoMapper {
    

    int insert(SettleIncomeInfo record);
    int update(SettleIncomeInfo record);
    List<SettleIncomeInfo> getlist();
    SettleIncomeInfo get(Long id);
}