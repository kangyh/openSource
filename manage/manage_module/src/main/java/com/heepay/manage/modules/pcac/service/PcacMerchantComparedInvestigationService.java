package com.heepay.manage.modules.pcac.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pcac.dao.PcacMerchantComparedInvestigationDao;
import com.heepay.manage.modules.pcac.entity.PcacMerchantComparedInvestigation;
import com.heepay.manage.modules.settle.service.util.ExcelService;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月7日上午10:08:17
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
public class PcacMerchantComparedInvestigationService extends CrudService<PcacMerchantComparedInvestigationDao, PcacMerchantComparedInvestigation>{
	
	@Autowired
	PcacMerchantComparedInvestigationDao pcacMerchantComparedInvestigationDao;
	@Autowired
	private ExcelService excelService;
	/**
	 * 
	 * @方法说明：清算协会风险提示信息导出
	 * @时间：2016年11月16日 下午2:00:58
	 * @创建人：wangdong
	 */
	public void exportpcacMerchantComparedInvestigationExcel(PcacMerchantComparedInvestigation pcacMerchantComparedInvestigation,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		//数据库查询结果
		List<Map<String,Object>> clearingCR = pcacMerchantComparedInvestigationDao.findListExcel(pcacMerchantComparedInvestigation);
		//导出Excel表格标题行
		String[] headerArray = {"商户代码","商户名称","法定代表人（负责人）姓名","对比","商户代码","商户名称","法定代表人（负责人）姓名","推送日期"};
		//导出表格对应的字段名称
		String[] showField = {"code","name","legalName","--","code","name","legalName","createTime"};
		try {
			excelService.exportCusExcel("清算协会商户信息对比协查", headerArray,clearingCR,showField,response,request,false);
		} catch (Exception e) {
			logger.error("PcacMerchantComparedInvestigationDetailService exportpcacMerchantComparedInvestigationExcel has a error:{清算协会商户信息对比协查查看信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
