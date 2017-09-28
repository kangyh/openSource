package com.heepay.risk.dao;


import java.util.List;

import com.heepay.risk.entity.RiskIpbase;

public interface RiskIpbaseMapper {
    int deleteByPrimaryKey(String ip);

    int insert(RiskIpbase record)  throws Exception ;

    RiskIpbase selectByPrimaryKey(long ipNum);

    List<RiskIpbase> selectAll();

    int updateByPrimaryKey(RiskIpbase record);
    
    public int insertBatch(List<RiskIpbase> list) throws Exception ;
}