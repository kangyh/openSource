package com.heepay.manage.modules.merchant.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantProductRedis;

@MyBatisDao
public interface MerchantProductRedisDao extends CrudDao<MerchantProductRedis>{
    List<MerchantProductRedis> findList(MerchantProductRedis merchantProductRedis);
    
    List<MerchantProductRedis> findOther(MerchantProductRedis merchantProductRedis);
}