package com.heepay.manage.modules.pcac.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pcac.entity.PcacMerchantReportDelete;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月13日上午11:01:32
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
 */
@MyBatisDao
public interface PcacMerchantReportDeleteDao extends CrudDao<PcacMerchantReportDelete>{
    int deleteByPrimaryKey(Long deleteId);

    int insert(PcacMerchantReportDelete record);

    PcacMerchantReportDelete selectByPrimaryKey(Long deleteId);

    List<PcacMerchantReportDelete> selectAll();

    int updateByPrimaryKey(PcacMerchantReportDelete record);
}