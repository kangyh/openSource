package com.heepay.manage.modules.pcac.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pcac.entity.PcacBlacklist;
import com.heepay.manage.modules.pcac.entity.PcacMerchantComparedInvestigationDetail;
import java.util.List;
import java.util.Map;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月3日下午5:14:36
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
public interface PcacMerchantComparedInvestigationDetailDao extends CrudDao<PcacMerchantComparedInvestigationDetail>{
    int deleteByPrimaryKey(Long detailId);

    int insert(PcacMerchantComparedInvestigationDetail record);

    PcacMerchantComparedInvestigationDetail selectByPrimaryKey(Long detailId);

    List<PcacMerchantComparedInvestigationDetail> selectAll();

    int updateByPrimaryKey(PcacMerchantComparedInvestigationDetail record);

	List<PcacMerchantComparedInvestigationDetail> getEntityByInvestigationId(Long investigationId);
	
	
}