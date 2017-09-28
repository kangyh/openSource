package com.heepay.prom.modules.prom.dao;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.prom.entity.PromProfitOrder;

import java.util.List;

@MyBatisDao
public interface PromProfitOrderDao  extends CrudDao<PromProfitOrder>{
    int deleteByPrimaryKey(Long profitId);
    
    int deletePromProfitByBatch(String orderBatch);
    
    int insert(PromProfitOrder record);

    PromProfitOrder selectByPrimaryKey(Long profitId);

    List<PromProfitOrder> selectAll();

    int updateByPrimaryKey(PromProfitOrder record);
    
    int deletePromProfitList(PromProfitOrder record);
    
    int insertProfitOrderList(List<PromProfitOrder> record);
}