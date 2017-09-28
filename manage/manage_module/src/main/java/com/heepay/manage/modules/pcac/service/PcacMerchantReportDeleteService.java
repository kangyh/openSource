package com.heepay.manage.modules.pcac.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pcac.dao.PcacMerchantReportDeleteDao;
import com.heepay.manage.modules.pcac.entity.PcacMerchantReportDelete;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月13日上午11:02:51
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
@Service
@Transactional(readOnly = true)
public class PcacMerchantReportDeleteService extends CrudService<PcacMerchantReportDeleteDao, PcacMerchantReportDelete>{

}
