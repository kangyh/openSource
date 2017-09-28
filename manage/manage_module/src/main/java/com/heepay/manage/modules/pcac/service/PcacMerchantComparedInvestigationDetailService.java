package com.heepay.manage.modules.pcac.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pcac.dao.PcacMerchantComparedInvestigationDetailDao;
import com.heepay.manage.modules.pcac.entity.PcacMerchantComparedInvestigation;
import com.heepay.manage.modules.pcac.entity.PcacMerchantComparedInvestigationDetail;
import com.heepay.manage.modules.pcac.entity.PcacRiskHintInfo;
import com.heepay.manage.modules.settle.service.util.ExcelService;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月7日上午10:59:38
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
public class PcacMerchantComparedInvestigationDetailService extends CrudService<PcacMerchantComparedInvestigationDetailDao, PcacMerchantComparedInvestigationDetail>{
	
	@Autowired
	PcacMerchantComparedInvestigationDetailDao pcacMerchantComparedInvestigationDetailDao;
	
	@Autowired
	private ExcelService excelService;
	
	//根据investigation_id查询数据
	public List<PcacMerchantComparedInvestigationDetail> getEntityByInvestigationId(Long investigationId) {
		
		return pcacMerchantComparedInvestigationDetailDao.getEntityByInvestigationId(investigationId);
	}
	
}
