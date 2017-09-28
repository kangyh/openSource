package com.heepay.prom.modules.prom.dao;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.prom.entity.PromSaleOrder;
import com.heepay.prom.modules.prom.entity.PromSaleOrderBuild;

import java.util.List;

@MyBatisDao
public interface PromSaleOrderDao   extends CrudDao<PromSaleOrder>{
    int deleteByPrimaryKey(Long saleId);
    
    int deletePromSaleByBatch(String orderBatch);
    
    int deletePromSaleList(PromSaleOrder record);
    
    int insert(PromSaleOrder record);

    PromSaleOrder selectByPrimaryKey(Long saleId);

    List<PromSaleOrder> selectAll();

    int updateByPrimaryKey(PromSaleOrder record);
    
    List<PromSaleOrderBuild> selectUnProfitOrder(PromSaleOrderBuild buildVo);
    
    int insertSaleOrderList(List<PromSaleOrder> record);
    
    int updatePromOrder(List<PromSaleOrderBuild> list);
}