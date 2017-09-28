package com.heepay.risk.dao;

import com.heepay.risk.entity.RiskLoginBlacklist;
import java.util.List;

public interface RiskLoginBlacklistMapper {
    int deleteByPrimaryKey(Integer blackId);

    int insert(RiskLoginBlacklist record);

    RiskLoginBlacklist selectByPrimaryKey(Integer blackId);

    List<RiskLoginBlacklist> selectAll();

    
    int selectCount(RiskLoginBlacklist record);

    int updateByPrimaryKey(RiskLoginBlacklist record);
}