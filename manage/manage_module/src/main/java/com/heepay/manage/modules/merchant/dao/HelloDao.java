package com.heepay.manage.modules.merchant.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.HelloVo;

@MyBatisDao
public interface HelloDao extends CrudDao<HelloVo>{
    int deleteByPrimaryKey(String id);

    int insert(HelloVo record);

    int insertSelective(HelloVo record);

    HelloVo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HelloVo record);

    int updateByPrimaryKey(HelloVo record);
    
    List<HelloVo> selectAll();
}